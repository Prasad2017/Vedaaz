package com.vedaaz.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.sdsmdg.tastytoast.TastyToast;
import com.vedaaz.Activity.MainPage;
import com.vedaaz.Activity.RazorPayWalletNow;
import com.vedaaz.Activity.RazorpayBuyNow;
import com.vedaaz.Extra.DetectConnection;
import com.vedaaz.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Wallet extends Fragment {


    View view;
    @BindView(R.id.amount)
    EditText editText;


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
                    intent.putExtra("pendingAmount", editText.getText().toString().trim());
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
        MainPage.back.setVisibility(View.VISIBLE);
        MainPage.menu.setVisibility(View.GONE);
        MainPage.searchLayout.setVisibility(View.GONE);
        MainPage.bottomNavigationView.setVisibility(View.GONE);
        if (DetectConnection.checkInternetConnection(getActivity())){

        }else {
            TastyToast.makeText(getActivity(), "No Internet Connection", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT).show();
        }
    }
}