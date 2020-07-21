package com.vedaaz.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdsmdg.tastytoast.TastyToast;
import com.vedaaz.Activity.MainPage;
import com.vedaaz.Adapter.DilayAdapter;
import com.vedaaz.Adapter.HomeSliderAdapter;
import com.vedaaz.Adapter.ProductAdapter;
import com.vedaaz.Extra.DetectConnection;
import com.vedaaz.Module.AllList;
import com.vedaaz.Module.DailyProductResponse;
import com.vedaaz.Module.ProductResponse;
import com.vedaaz.R;
import com.vedaaz.Retrofit.Api;
import com.vedaaz.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment {
//mvc , singletone , library
    View view;
    @BindView(R.id.recyclerDailyProduct)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerSlider)
    RecyclerView recyclerSlider;
    @BindView(R.id.gridview_categoryHome)
    RecyclerView recyclerViewCategory;
    List<DailyProductResponse> dailyProductResponseList = new ArrayList<>();
    List<ProductResponse> productResponseList = new ArrayList<>();
    DilayAdapter adapter;
    ProductAdapter productAdapter;
    HomeSliderAdapter homeSliderAdapter;
 /*   @BindView(R.id.gridview_categoryHome)
    RecyclerView gridview_categoryHome;*/
   /* List<ProductResponse> productResponseList = new ArrayList<>();
    DilayAdapter adapter;*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        return view;
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
    private void getDailyProduct() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Data Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);


        recyclerView.clearOnScrollListeners();
        dailyProductResponseList.clear();

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<AllList> call = apiInterface.getDailyProduct();
        call.enqueue(new Callback<AllList>() {
            @Override
            public void onResponse(Call<AllList> call, Response<AllList> response) {

                AllList allList = response.body();
                dailyProductResponseList =(allList).getDailyProductResponseList();

                if (dailyProductResponseList.size()==0){
                    progressDialog.dismiss();
                    recyclerView.setVisibility(View.GONE);

                }else {

                    for (int i=0;i<dailyProductResponseList.size();i++){


                        adapter = new DilayAdapter(getActivity(),dailyProductResponseList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        adapter.notifyItemInserted(dailyProductResponseList.size() - 1);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setVisibility(View.VISIBLE);

                        progressDialog.dismiss();
                    }

                }

            }

            @Override
            public void onFailure(Call<AllList> call, Throwable t) {
                progressDialog.dismiss();
                recyclerView.setVisibility(View.GONE);
            }
        });


    }

    private void getSlider() {


        recyclerSlider.clearOnScrollListeners();
        dailyProductResponseList.clear();

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<AllList> call = apiInterface.getDailyProduct();
        call.enqueue(new Callback<AllList>() {
            @Override
            public void onResponse(Call<AllList> call, Response<AllList> response) {

                AllList allList = response.body();
                dailyProductResponseList =(allList).getDailyProductResponseList();

                if (dailyProductResponseList.size()==0){
                    recyclerSlider.setVisibility(View.GONE);

                }else {

                    for (int i=0;i<dailyProductResponseList.size();i++){


                        homeSliderAdapter = new HomeSliderAdapter(getActivity(),dailyProductResponseList);
                        recyclerSlider.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                        recyclerSlider.setAdapter(homeSliderAdapter);
                        homeSliderAdapter.notifyDataSetChanged();
                        homeSliderAdapter.notifyItemInserted(dailyProductResponseList.size() - 1);
                        recyclerSlider.setHasFixedSize(true);
                        recyclerSlider.setVisibility(View.VISIBLE);

                    }

                }

            }

            @Override
            public void onFailure(Call<AllList> call, Throwable t) {
                recyclerSlider.setVisibility(View.GONE);
                Log.e("errorlog",""+t);

            }
        });


    }


    public void onStart() {
        super.onStart();
        Log.d("onStart", "called");
        MainPage.logo.setVisibility(View.VISIBLE);
        MainPage.title.setVisibility(View.GONE);
        MainPage.searchLayout.setVisibility(View.VISIBLE);
        MainPage.back.setVisibility(View.GONE);
        MainPage.menu.setVisibility(View.VISIBLE);
        MainPage.bottomNavigationView.setVisibility(View.VISIBLE);

        if (DetectConnection.checkInternetConnection(getActivity())){
            getProduct();
            getDailyProduct();
         //   getSlider();
        }else {
            TastyToast.makeText(getActivity(), "No Internet Connection", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT).show();
        }
    }


}
