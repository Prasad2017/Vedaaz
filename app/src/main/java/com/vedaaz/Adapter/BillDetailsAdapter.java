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
    double totalAmount = 0f, amountPayable;
    public static String totalAmountPayable;


    public BillDetailsAdapter(Context context, List<BillResponse> productResponseList) {

        this.context = context;
        this.productResponseList = productResponseList;
        for(int position = 0 ;position<productResponseList.size();position++){
            totalAmount = totalAmount + ((Double.parseDouble(productResponseList.get(position).getDailyQty()) + Double.parseDouble(productResponseList.get(position).getAdhoc_qty())) * Double.parseDouble(productResponseList.get(position).getDaily_amt()));
        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_details_list, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        BillResponse billResponse = productResponseList.get(position);

        if (position==productResponseList.size()-1) {
            holder.textViews.get(7).setVisibility(View.VISIBLE);
            holder.textViews.get(7).setText("Total Amount : "+MainPage.currency+" "+totalAmount);
        } else

        holder.textViews.get(7).setVisibility(View.GONE);
        holder.textViews.get(0).setText(productResponseList.get(position).getDate());
        holder.textViews.get(1).setText(productResponseList.get(position).getDaily_product_name());
        holder.textViews.get(2).setText(productResponseList.get(position).getDailyQty());
        holder.textViews.get(3).setText("");
        holder.textViews.get(4).setText(productResponseList.get(position).getAdhoc_qty());
        holder.textViews.get(5).setText(""+(Double.parseDouble(productResponseList.get(position).getDailyQty()) + Double.parseDouble(productResponseList.get(position).getAdhoc_qty())));

        double amount = Double.parseDouble(productResponseList.get(position).getDaily_amt());
        holder.textViews.get(6).setText(""+amount);

    }

    @Override
    public int getItemCount() {
        return productResponseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindViews({R.id.date, R.id.narration, R.id.subscribed, R.id.pause, R.id.addhoc, R.id.totalDelivered, R.id.amount, R.id.totalAmount})
        List<TextView> textViews;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}