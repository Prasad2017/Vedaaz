package com.vedaaz.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vedaaz.Activity.MainPage;
import com.vedaaz.Extra.Common;
import com.vedaaz.Fragment.Subscription;
import com.vedaaz.Module.DailyProductResponse;
import com.vedaaz.Module.LoginResponse;
import com.vedaaz.R;
import com.vedaaz.Retrofit.Api;
import com.vedaaz.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class HomeSliderAdapter extends RecyclerView.Adapter<HomeSliderAdapter.MyViewHolder> {

    Context context;
    List<DailyProductResponse> productResponseList;
    public static ArrayList<Integer> selectedList;
    public AlertDialog dialogBuilder;
    int total = 0;
    public static String str_quatity = "1";
    public String cart_id;
    String sec = "";
    String days = "";
    String selectedDay = "";

    public HomeSliderAdapter(Context context, List<DailyProductResponse> productResponseList) {
        this.context = context;
        this.productResponseList = productResponseList;


    }

    @NonNull
    @Override
    public HomeSliderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_slider_list, parent, false);
        return new HomeSliderAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeSliderAdapter.MyViewHolder holder, int position) {


        Picasso.with(context)
                .load("" + productResponseList.get(position).getDaily_product_image())
                .placeholder(R.drawable.logo).into(holder.productimg);


    }



    @Override
    public int getItemCount() {
        return productResponseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView productimg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productimg=itemView.findViewById(R.id.productimg);


        }
    }
}