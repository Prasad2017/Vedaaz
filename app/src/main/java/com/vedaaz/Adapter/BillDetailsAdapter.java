package com.vedaaz.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vedaaz.Activity.MainPage;
import com.vedaaz.Fragment.BillDetails;
import com.vedaaz.Module.BillResponse;
import com.vedaaz.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;


public class BillDetailsAdapter extends RecyclerView.Adapter<BillDetailsAdapter.MyViewHolder> {

    Context context;
    List<BillResponse> productResponseList;


    public BillDetailsAdapter(Context context, List<BillResponse> productResponseList) {
        this.context = context;
        this.productResponseList = productResponseList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bill_details_list, null);
        MyViewHolder CartListViewHolder = new MyViewHolder(view);
        return CartListViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        BillResponse billResponse = productResponseList.get(position);

        holder.textViews.get(0).setText(productResponseList.get(position).getDate());
        holder.textViews.get(1).setText(productResponseList.get(position).getSubsription_type()+" "+productResponseList.get(position).getDel_status());
        holder.textViews.get(2).setText(productResponseList.get(position).getDailyQty());
        holder.textViews.get(3).setText("");
        holder.textViews.get(4).setText(productResponseList.get(position).getAdhoc_qty());
        holder.textViews.get(5).setText(""+(Double.parseDouble(productResponseList.get(position).getDailyQty()) + Double.parseDouble(productResponseList.get(position).getAdhoc_qty())));

        double amount = Double.parseDouble(holder.textViews.get(5).getText().toString().trim()) * Double.parseDouble(productResponseList.get(position).getDaily_amt());
        holder.textViews.get(6).setText(""+amount);

    }

    @Override
    public int getItemCount() {
        return productResponseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindViews({R.id.date, R.id.narration, R.id.subscribed, R.id.pause, R.id.addhoc, R.id.totalDelivered, R.id.amount})
        List<TextView> textViews;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}