package com.vedaaz.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;
import com.vedaaz.Activity.MainPage;
import com.vedaaz.Adapter.BillAdapter;
import com.vedaaz.Adapter.BillDetailsAdapter;
import com.vedaaz.Extra.DetectConnection;
import com.vedaaz.Module.AllList;
import com.vedaaz.Module.BillResponse;
import com.vedaaz.R;
import com.vedaaz.Retrofit.Api;
import com.vedaaz.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillDetails extends Fragment {

    View view;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindViews({R.id.customerName, R.id.planName, R.id.startDate, R.id.endDate, R.id.frequency})
    List<TextView> textViews;
    List<BillResponse> dailyProductResponseList = new ArrayList<>();
    BillDetailsAdapter adapter;
    String date, planName, startDate, endDate, frequency, customerName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_bill_details, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("Bill Details");

        Bundle bundle = getArguments();
        date=bundle.getString("date");
        planName=bundle.getString("planName");
        startDate=bundle.getString("startDate");
        endDate=bundle.getString("endDate");
        frequency=bundle.getString("frequency");
        customerName=bundle.getString("customerName");

        textViews.get(0).setText(customerName);
        textViews.get(1).setText(planName);
        textViews.get(2).setText(startDate);
        textViews.get(3).setText(endDate);
        textViews.get(4).setText(frequency);

        return view;
    }

    private void getBillList() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setTitle("Data Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        recyclerView.clearOnScrollListeners();
        dailyProductResponseList.clear();

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<AllList> call = apiInterface.getBillDetails(MainPage.userId, date);
        call.enqueue(new Callback<AllList>() {
            @Override
            public void onResponse(Call<AllList> call, Response<AllList> response) {

                AllList allList = response.body();
                dailyProductResponseList =(allList).getBillDetailsResonseList();

                if (dailyProductResponseList.size()==0){
                    progressDialog.dismiss();
                    recyclerView.setVisibility(View.GONE);

                }else {

                    for (int i=0;i<dailyProductResponseList.size();i++){


                        adapter = new BillDetailsAdapter(getActivity(),dailyProductResponseList);
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
