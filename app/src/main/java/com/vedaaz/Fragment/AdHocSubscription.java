package com.vedaaz.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;
import com.vedaaz.Activity.MainPage;
import com.vedaaz.Adapter.SubscriptionAdapter;
import com.vedaaz.Adapter.SubscriptionListAdapter;
import com.vedaaz.Extra.DetectConnection;
import com.vedaaz.Module.AllList;
import com.vedaaz.Module.LoginResponse;
import com.vedaaz.Module.SubscriptionResponse;
import com.vedaaz.R;
import com.vedaaz.Retrofit.Api;
import com.vedaaz.Retrofit.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class AdHocSubscription extends Fragment {


    View view;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    @BindViews({R.id.tvselectDate, R.id.tvselectPlan, R.id.tvendselectDate})
    List<TextView> textViews;
    @BindViews({R.id.selectdate, R.id.selectPlan, R.id.endselectdate})
    List<ImageView> imageViews;
    @BindView(R.id.quantity)
    EditText editText;
    RecyclerView recyclerSubsciber;
    List<SubscriptionResponse> dailyProductResponseList = new ArrayList<>();
    SubscriptionListAdapter adapter;
    @BindView(R.id.layout1)
    LinearLayout linearLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    SubscriptionAdapter adapter1;
    List<SubscriptionResponse> subList = new ArrayList<>();
    @BindView(R.id.planSpin)
    Spinner planSpin;
    String[] planIdList, planNameList;
    String planId, plantName;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ad_hoc_subscription, container, false);

        ButterKnife.bind(this, view);
        MainPage.title.setText("AdHoc Subscription");

        planSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                planId = planIdList[position];
                plantName = planNameList[position];
                Log.e("plantName", "" + plantName);
                try {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @OnClick({R.id.selectdate, R.id.tvselectDate, R.id.selectPlan, R.id.tvselectPlan, R.id.submit, R.id.endselectdate, R.id.tvendselectDate})
    public void onClick(View view){
        switch (view.getId()) {

            case R.id.selectdate:
            case R.id.tvselectDate:

                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                                calendar.set(year, month, day);

                                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                String strDate = format.format(calendar.getTime());

                                Log.e("date", "" + strDate);
                                textViews.get(0).setText(strDate);
                                imageViews.get(0).setVisibility(View.GONE);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();

                break;

            case R.id.endselectdate:
            case R.id.tvendselectDate:

                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                                calendar.set(year, month, day);

                                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                String strDate = format.format(calendar.getTime());

                                Log.e("date", "" + strDate);
                                textViews.get(2).setText(strDate);
                                imageViews.get(2).setVisibility(View.GONE);
                            }
                        }, year, month, dayOfMonth);

                datePickerDialog.show();

                break;


            case R.id.submit:

                adHocSubscription(textViews.get(0).getText().toString(),textViews.get(2).getText().toString(), editText.getText().toString(),planId);

                break;
        }
    }

    private void getSubscription() {

        dailyProductResponseList.clear();

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<AllList> call = apiInterface.getSubscriptionList(MainPage.userId);
        call.enqueue(new Callback<AllList>() {
            @Override
            public void onResponse(Call<AllList> call, Response<AllList> response) {

                AllList allList = response.body();
                dailyProductResponseList =(allList).getSubscriptionResponseList();

                if (dailyProductResponseList.size()==0){
                    recyclerSubsciber.setVisibility(View.GONE);
                }else {

                    planIdList = new String[dailyProductResponseList.size()];
                    planNameList = new String[dailyProductResponseList.size()];

                    for(int i=0;i<dailyProductResponseList.size();i++){

                        planIdList[i] = dailyProductResponseList.get(i).getSubscription_id();
                        planNameList[i] = dailyProductResponseList.get(i).getDaily_product_name();

                    }

                    ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, planNameList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    planSpin.setAdapter(adapter);

                }

            }

            @Override
            public void onFailure(Call<AllList> call, Throwable t) {
                recyclerSubsciber.setVisibility(View.GONE);
            }
        });
    }

    public void adHocSubscription( String startdate, String endate, String qty,String  subid) {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("AdHoc Subscription.");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(true);

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.adHocSubscription(MainPage.userId, startdate, endate, qty, subid);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                String data = Objects.requireNonNull(response.body()).getSuccess();

                if (data.equalsIgnoreCase("1")) {
                    Toasty.success(getActivity(), ""+response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                     progressDialog.dismiss();
                    ((MainPage)getActivity()).removeCurrentFragmentAndMoveBack();
                    ((MainPage) getActivity()).loadFragment(new AdHocSubscription(), true);
                }else if(data.equalsIgnoreCase("0")){
                    Toasty.error(getActivity(), ""+response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                     progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // progressDialog.dismiss();
                Toasty.error(getActivity(), "Server Error", Toasty.LENGTH_SHORT).show();
            }
        });


    }


    private void getSubscriptionList() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Data Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        recyclerView.clearOnScrollListeners();
        subList.clear();

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<AllList> call = apiInterface.getAdHocSub(MainPage.userId);
        call.enqueue(new Callback<AllList>() {
            @Override
            public void onResponse(Call<AllList> call, Response<AllList> response) {

                AllList allList = response.body();
                subList =(allList).getAdHocResponseList();

                if (subList.size()==0){
                    progressDialog.dismiss();
                    recyclerView.setVisibility(View.GONE);
                }else {

                    for (int i=0;i<subList.size();i++){


                        adapter1 = new SubscriptionAdapter(getActivity(),subList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(adapter1);
                        adapter1.notifyDataSetChanged();
                        adapter1.notifyItemInserted(subList.size() - 1);
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
        ((MainPage) getActivity()).lockUnlockDrawer(1);
        MainPage.searchLayout.setVisibility(View.GONE);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)MainPage.title.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.addRule(RelativeLayout.RIGHT_OF, R.id.back);
        MainPage.title.setLayoutParams(params);
        if (DetectConnection.checkInternetConnection(getActivity())){
            getSubscription();
            getSubscriptionList();
        }else {
            TastyToast.makeText(getActivity(), "No Internet Connection", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT).show();
        }
    }
}

