package com.vedaaz.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.vedaaz.Adapter.ProductAdapter;
import com.vedaaz.Extra.Common;
import com.vedaaz.Extra.DetectConnection;
import com.vedaaz.Module.AddressResponse;
import com.vedaaz.Module.AllList;
import com.vedaaz.Module.DailyProductResponse;
import com.vedaaz.Module.LoginResponse;
import com.vedaaz.R;
import com.vedaaz.Retrofit.Api;
import com.vedaaz.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Welcome extends AppCompatActivity {

    @BindViews({R.id.name, R.id.surname, R.id.email})
    List<EditText> editTexts;
    @BindViews({R.id.city, R.id.area})
    List<SearchableSpinner> searchableSpinner;

    String userid;
    List<AddressResponse> activityResponceList = new ArrayList<>();
    List<AddressResponse> subActivityResponceList = new ArrayList<>();
    String activityName, subactivityName;
    String [] activityNameList, activityIdList, subactivityNameList, subactivityIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        Intent intent=getIntent();
        userid=intent.getStringExtra("userid");
        Log.e("welcome-userid",""+userid);

        getActivityList();

        searchableSpinner.get(0).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                activityName = activityIdList[position];
                try {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                    // To get Sub-ActivityList
                    getSubActivityList(activityName);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchableSpinner.get(1).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                subactivityName = subactivityIdList[position];
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


    }

    @OnClick({R.id.submit, R.id.skip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if(validate(editTexts.get(0)) && validate(editTexts.get(1)) && validate(editTexts.get(2))){
                    if (DetectConnection.checkInternetConnection(Welcome.this)) {
                        UpdateProfile(userid, activityName, subactivityName, editTexts.get(0).getText().toString().trim(), editTexts.get(1).getText().toString(), editTexts.get(2).getText().toString());
                    }else {
                        TastyToast.makeText(Welcome.this, "No Internet Connection", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT).show();
                    }
                }

                break;

            case R.id.skip:

                Intent verification = new Intent(Welcome.this, MainPage.class);
                startActivity(verification);
                finishAffinity();

                break;
        }
    }

    private void UpdateProfile(String userid, String city, String area, String name, String surname, String email) {

        final ProgressDialog progressDialog = new ProgressDialog(Welcome.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Profile Updating");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.UpdateProfile(userid, city, area, name, surname, email);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                String data = Objects.requireNonNull(response.body()).getSuccess();

                if (data.equalsIgnoreCase("1")) {
                    progressDialog.dismiss();
                    Toasty.success(Welcome.this, ""+response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                    Common.saveUserData(Welcome.this,"userName", editTexts.get(0).getText().toString()+" "+editTexts.get(1).getText().toString());
                    Common.saveUserData(Welcome.this,"userCityId", activityName);
                    Common.saveUserData(Welcome.this,"userAreaId", subactivityName);

                    Intent verification = new Intent(Welcome.this, MainPage.class);
                    startActivity(verification);
                    finishAffinity();

                } else if(data.equalsIgnoreCase("0")){
                    progressDialog.dismiss();
                    Toasty.error(Welcome.this, ""+response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toasty.error(Welcome.this, "Server Error", Toasty.LENGTH_SHORT).show();
            }
        });


    }

    private boolean validate(EditText editText) {
        if (editText.getText().toString().trim().length() > 0) {
            return true;
        }
        editText.setError("Please Fill This");
        editText.requestFocus();
        return false;
    }


    private void getActivityList() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<AllList> call = apiInterface.getCity();
        call.enqueue(new Callback<AllList>() {
            @Override
            public void onResponse(Call<AllList> call, Response<AllList> response) {

                AllList allList = response.body();
                activityResponceList = allList.getCityresponseList();

                if (activityResponceList.size()==0){
                    searchableSpinner.get(0).setVisibility(View.GONE);
                }else {

                    activityNameList = new String[activityResponceList.size()];
                    activityIdList = new String[activityResponceList.size()];

                    for (int i = 0; i< activityResponceList.size(); i++){

                        activityIdList[i] = activityResponceList.get(i).getCity_id();
                        activityNameList[i] = activityResponceList.get(i).getCity_name();

                    }

                    final ArrayAdapter arrayAdapter = new ArrayAdapter(Welcome.this, android.R.layout.simple_spinner_item, activityNameList);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
                    searchableSpinner.get(0).setAdapter(arrayAdapter);
                    searchableSpinner.get(0).setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onFailure(Call<AllList> call, Throwable t) {

                Toasty.error(Welcome.this, "Server Error", Toasty.LENGTH_SHORT).show();
            }
        });

    }

    private void getSubActivityList(String activityId) {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<AllList> call = apiInterface.getArea(activityId);
        call.enqueue(new Callback<AllList>() {
            @Override
            public void onResponse(Call<AllList> call, Response<AllList> response) {

                AllList allList = response.body();
                subActivityResponceList = allList.getAreaResponseList();

                if (subActivityResponceList.size()==0){
                    searchableSpinner.get(1).setVisibility(View.GONE);
                }else {

                    subactivityNameList = new String[subActivityResponceList.size()];
                    subactivityIdList = new String[subActivityResponceList.size()];

                    for (int i = 0; i< subActivityResponceList.size(); i++){

                        subactivityIdList[i] = subActivityResponceList.get(i).getArea_id();
                        subactivityNameList[i] = subActivityResponceList.get(i).getArea_name();

                    }

                    final ArrayAdapter arrayAdapter = new ArrayAdapter(Welcome.this, android.R.layout.simple_spinner_item, subactivityNameList);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
                    searchableSpinner.get(1).setAdapter(arrayAdapter);
                    searchableSpinner.get(1).setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<AllList> call, Throwable t) {

                Toasty.error(Welcome.this, "Server Error", Toasty.LENGTH_SHORT).show();
            }
        });

    }

}
