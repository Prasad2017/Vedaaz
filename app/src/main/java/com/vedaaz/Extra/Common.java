package com.vedaaz.Extra;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.vedaaz.Activity.MainPage;
import com.vedaaz.Activity.RazorpayBuyNow;
import com.vedaaz.Module.LoginResponse;
import com.vedaaz.Retrofit.Api;
import com.vedaaz.Retrofit.ApiInterface;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Common {
   // people static final String SHARED_PREF = "userData";
    public static final String SHARED_PREF = "session";

    public static void saveUserData(Context context, String key, String value) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getSavedUserData(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        return pref.getString(key, "");

    }

    public static void addWallet(Context context, String razorpayPaymentID, String amount) {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.addWallet(MainPage.userId, razorpayPaymentID, amount);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.body().getSuccess().equals("1")){
                    progressDialog.dismiss();
                    Toasty.normal(context, ""+response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, MainPage.class);
                    context.startActivity(intent);
                    ((Activity) context).finishAffinity();
                } else if(response.body().getSuccess().equals("0")){
                    progressDialog.dismiss();
                    Toasty.normal(context, ""+response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("Error", ""+t.getMessage());
            }
        });

    }

    public static void addPendingAmount(Context context, String razorpayPaymentID, String pendingAmount) {


        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.addPendingAmount(MainPage.userId, razorpayPaymentID, pendingAmount);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.body().getSuccess().equals("1")){
                    progressDialog.dismiss();
                    Toasty.normal(context, ""+response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, MainPage.class);
                    context.startActivity(intent);
                    ((Activity) context).finishAffinity();
                } else if(response.body().getSuccess().equals("0")){
                    progressDialog.dismiss();
                    Toasty.normal(context, ""+response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("Error", ""+t.getMessage());
            }
        });

    }
}
