package com.vedaaz.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.vedaaz.R;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RazorpayBuyNow extends AppCompatActivity implements PaymentResultListener {

    public String pendingAmount, userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay_buy_now);

        Intent intent = getIntent();
        try {
            userId = intent.getStringExtra("userId");
            pendingAmount = intent.getStringExtra("pendingAmount");
        } catch (Exception e) {
            e.printStackTrace();
        }
        startPayment(pendingAmount);

    }

    private void startPayment(String totalAmount) {

        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", getResources().getString(R.string.app_name));
            options.put("description", "Payment for SSP Chicken");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");

            JSONObject preFill = new JSONObject();
            preFill.put("email", "MainPage.email");
            preFill.put("contact", MainPage.mobileNumber);
            options.put("prefill", preFill);

            double total = Double.parseDouble(totalAmount);
            total = total * 100;
            options.put("amount", total);
            co.open(activity, options);

        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {

        finish();

    }

    @Override
    public void onPaymentError(int code, String response) {
        finish();
        try {
            Toast.makeText(this, "Payment error please try again", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("OnPaymentError", "Exception in onPaymentError", e);
        }
    }
}
