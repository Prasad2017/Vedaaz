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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;
import com.vedaaz.Activity.MainPage;
import com.vedaaz.Adapter.DilayAdapter;
import com.vedaaz.Adapter.SubscriptionAdapter;
import com.vedaaz.Extra.DetectConnection;
import com.vedaaz.Module.AllList;
import com.vedaaz.Module.DailyProductResponse;
import com.vedaaz.Module.SubscriptionResponse;
import com.vedaaz.R;
import com.vedaaz.Retrofit.Api;
import com.vedaaz.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewSubscription extends Fragment {

    View view;
    @BindView(R.id.recyclerSubsciber)
    RecyclerView recyclerSubsciber;
    @BindView(R.id.noData)
    TextView noData;
    List<SubscriptionResponse> dailyProductResponseList = new ArrayList<>();
    SubscriptionAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_subscription, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("Subscription");

        return view;
    }

    private void getSubscription() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Data Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);


        recyclerSubsciber.clearOnScrollListeners();
        dailyProductResponseList.clear();

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<AllList> call = apiInterface.getSubscriptionList(MainPage.userId);
        call.enqueue(new Callback<AllList>() {
            @Override
            public void onResponse(Call<AllList> call, Response<AllList> response) {

                AllList allList = response.body();
                dailyProductResponseList =(allList).getSubscriptionResponseList();

                if (dailyProductResponseList.size()==0){
                    progressDialog.dismiss();
                    recyclerSubsciber.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);

                }else {

                    for (int i=0;i<dailyProductResponseList.size();i++){


                        adapter = new SubscriptionAdapter(getActivity(),dailyProductResponseList);
                        recyclerSubsciber.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        recyclerSubsciber.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        adapter.notifyItemInserted(dailyProductResponseList.size() - 1);
                        recyclerSubsciber.setHasFixedSize(true);
                        recyclerSubsciber.setVisibility(View.VISIBLE);
                        noData.setVisibility(View.GONE);

                        progressDialog.dismiss();
                    }

                }

            }

            @Override
            public void onFailure(Call<AllList> call, Throwable t) {
                progressDialog.dismiss();
                recyclerSubsciber.setVisibility(View.GONE);
            }
        });


    }


    public void onStart() {
        super.onStart();
        Log.d("onStart", "called");
        MainPage.logo.setVisibility(View.GONE);
        MainPage.title.setVisibility(View.VISIBLE);
        ((MainPage) getActivity()).lockUnlockDrawer(1);
        MainPage.searchLayout.setVisibility(View.GONE);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)MainPage.title.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.addRule(RelativeLayout.RIGHT_OF, R.id.back);
        MainPage.title.setLayoutParams(params);
        if (DetectConnection.checkInternetConnection(getActivity())){
            getSubscription();
        }else {
            TastyToast.makeText(getActivity(), "No Internet Connection", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT).show();
        }
    }
}
