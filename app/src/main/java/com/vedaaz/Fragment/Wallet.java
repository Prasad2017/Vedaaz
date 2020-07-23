package com.vedaaz.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;
import com.vedaaz.Activity.MainPage;
import com.vedaaz.Activity.RazorPayWalletNow;
import com.vedaaz.Activity.RazorpayBuyNow;
import com.vedaaz.Extra.DetectConnection;
import com.vedaaz.Module.AllList;
import com.vedaaz.Module.ProfileResponse;
import com.vedaaz.R;
import com.vedaaz.Retrofit.Api;
import com.vedaaz.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Wallet extends Fragment {


    View view;
    @BindView(R.id.amount)
    EditText editText;
    @BindView(R.id.walletAmount)
    TextView textView;
    List<ProfileResponse> profileResponseList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wallete, container, false);

        ButterKnife.bind(this, view);
        MainPage.title.setText("My Wallet");


        return view;

    }

    @OnClick({R.id.firstAmount, R.id.secondAmount, R.id.thirdAmount, R.id.fourthAmount, R.id.addAmount})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.firstAmount:
                editText.setText("1000");
                break;

            case R.id.secondAmount:
                editText.setText("2000");
                break;

            case R.id.thirdAmount:
                editText.setText("3000");
                break;

            case R.id.fourthAmount:
                editText.setText("4000");
                break;

            case R.id.addAmount:

                if (editText.getText().toString().trim().length()>0){

                    Intent intent = new Intent(getActivity(), RazorPayWalletNow.class);
                    intent.putExtra("amount", editText.getText().toString().trim());
                    intent.putExtra("userId", MainPage.userId);
                    startActivity(intent);

                } else {
                    editText.setError("Enter Amount");
                    editText.requestFocus();
                }

                break;
            default:
                editText.setText("");
                break;
        }
    }


    public void onStart() {
        super.onStart();
        Log.d("onStart", "called");
        MainPage.logo.setVisibility(View.GONE);
        MainPage.title.setVisibility(View.VISIBLE);
        ((MainPage) getActivity()).lockUnlockDrawer(1);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)MainPage.title.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.addRule(RelativeLayout.RIGHT_OF, R.id.back);
        MainPage.title.setLayoutParams(params);
        MainPage.searchLayout.setVisibility(View.GONE);

        if (DetectConnection.checkInternetConnection(getActivity())){
            getProfile();
        }else {
            TastyToast.makeText(getActivity(), "No Internet Connection", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT).show();
        }
    }

    private void getProfile() {

            ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
            Call<AllList> call = apiInterface.getProfile(MainPage.userId);
            call.enqueue(new Callback<AllList>() {
                @Override
                public void onResponse(Call<AllList> call, Response<AllList> response) {

                    profileResponseList = response.body().getProfileResponseList();
                    if (profileResponseList.size()==0){

                    } else {

                        for (int i=0;i<profileResponseList.size();i++) {

                            MainPage.walletAmount = profileResponseList.get(i).getWalletAmount();
                            textView.setText(MainPage.currency+" "+MainPage.walletAmount);

                        }
                    }

                }

                @Override
                public void onFailure(Call<AllList> call, Throwable t) {
                    Log.e("Error", ""+t.getMessage());
                }
            });

    }
}