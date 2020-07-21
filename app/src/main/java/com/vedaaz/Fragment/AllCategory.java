package com.vedaaz.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sdsmdg.tastytoast.TastyToast;
import com.vedaaz.Activity.MainPage;
import com.vedaaz.Adapter.ProductAdapter;
import com.vedaaz.Extra.DetectConnection;
import com.vedaaz.Module.AllList;
import com.vedaaz.Module.DailyProductResponse;
import com.vedaaz.Module.ProductResponse;
import com.vedaaz.R;
import com.vedaaz.Retrofit.Api;
import com.vedaaz.Retrofit.ApiInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCategory extends Fragment {

    View view;

    @BindView(R.id.gridview_categoryHome)
    RecyclerView recyclerViewCategory;
    List<ProductResponse> productResponseList = new ArrayList<>();
    ProductAdapter productAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_category, container, false);

        ButterKnife.bind(this, view);
        MainPage.title.setText("All Category");

        return view;
    }

    public void onStart() {
        super.onStart();
        Log.d("onStart", "called");
        MainPage.logo.setVisibility(View.GONE);
        MainPage.title.setVisibility(View.VISIBLE);
        MainPage.searchLayout.setVisibility(View.GONE);
        MainPage.back.setVisibility(View.VISIBLE);
        MainPage.menu.setVisibility(View.GONE);
        MainPage.bottomNavigationView.setVisibility(View.GONE);

        if (DetectConnection.checkInternetConnection(getActivity())){
            getProduct();
        }else {
            TastyToast.makeText(getActivity(), "No Internet Connection", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT).show();
        }
    }

    private void getProduct() {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Data Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);


        recyclerViewCategory.clearOnScrollListeners();
        productResponseList.clear();

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<AllList> call = apiInterface.getProducts();
        call.enqueue(new Callback<AllList>() {
            @Override
            public void onResponse(Call<AllList> call, Response<AllList> response) {

                AllList allList = response.body();
                productResponseList = Objects.requireNonNull(allList).getProductResponseList();

                if (productResponseList.size()==0){
                    progressDialog.dismiss();
                    recyclerViewCategory.setVisibility(View.GONE);
                    Log.e("errorlog","123");

                }else {

                    for (int i=0;i<productResponseList.size();i++){


                        productAdapter = new ProductAdapter(getActivity(),productResponseList);
                        recyclerViewCategory.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                        recyclerViewCategory.setAdapter(productAdapter);
                        productAdapter.notifyDataSetChanged();
                        productAdapter.notifyItemInserted(productResponseList.size() - 1);
                        recyclerViewCategory.setHasFixedSize(true);
                        recyclerViewCategory.setVisibility(View.VISIBLE);
                        Log.e("errorlog","111");

                        progressDialog.dismiss();
                    }

                }

            }

            @Override
            public void onFailure(Call<AllList> call, Throwable t) {
                progressDialog.dismiss();
                recyclerViewCategory.setVisibility(View.GONE);
                Log.e("errorlog",""+t);
            }
        });


    }
}
