package com.vedaaz.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;
import com.vedaaz.Activity.MainPage;
import com.vedaaz.Activity.RazorpayBuyNow;
import com.vedaaz.Adapter.BillAdapter;
import com.vedaaz.Adapter.SubscriptionAdapter;
import com.vedaaz.Extra.DetectConnection;
import com.vedaaz.Module.AllList;
import com.vedaaz.Module.BillResponse;
import com.vedaaz.Module.SubscriptionResponse;
import com.vedaaz.R;
import com.vedaaz.Retrofit.Api;
import com.vedaaz.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Bill extends Fragment {


    View view;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    List<BillResponse> dailyProductResponseList = new ArrayList<>();
    BillAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_bill, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("All Bills");

        return view;
    }

    @OnClick({R.id.submitBill})
    public void onClick(View view){
        switch (view.getId()) {

            case R.id.submitBill:

                if (DetectConnection.checkInternetConnection(getActivity())){
                    try {

                        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage("Please Wait");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        String pendingAmount = "";
                        for (int i = 0; i < BillAdapter.productResponseList.size(); i++) {
                            if (BillAdapter.productResponseList.get(i).getDel_status().equals("pending")) {
                                try {
                                    pendingAmount = BillAdapter.productResponseList.get(i).getTotalAmount();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        progressDialog.dismiss();

                        try {
                            if (Integer.parseInt(pendingAmount) > 0) {

                                Intent intent = new Intent(getActivity(), RazorpayBuyNow.class);
                                intent.putExtra("pendingAmount", pendingAmount);
                                intent.putExtra("userId", MainPage.userId);
                                startActivity(intent);

                            } else {
                                Toasty.normal(getActivity(), "Nothing is Pending", Toasty.LENGTH_SHORT).show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                } else {
                    Toasty.warning(getActivity(), "No Internet Connection", Toasty.LENGTH_SHORT);
                }

                break;
        }}

    private void getBillList() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Data Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        recyclerView.clearOnScrollListeners();
        dailyProductResponseList.clear();

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<AllList> call = apiInterface.getBillList(MainPage.userId);
        call.enqueue(new Callback<AllList>() {
            @Override
            public void onResponse(Call<AllList> call, Response<AllList> response) {

                AllList allList = response.body();
                dailyProductResponseList =(allList).getBillResonseList();

                if (dailyProductResponseList.size()==0){
                    progressDialog.dismiss();
                    recyclerView.setVisibility(View.GONE);
                }else {

                    for (int i=0;i<dailyProductResponseList.size();i++){

                        adapter = new BillAdapter(getActivity(),dailyProductResponseList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
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
            getBillList();
        }else {
            TastyToast.makeText(getActivity(), "No Internet Connection", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT).show();
        }
    }
}
