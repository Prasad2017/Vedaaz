package com.vedaaz.Adapter;

import android.content.Context;
import android.os.Bundle;
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


public class BillDetailsAdapter extends RecyclerView.Adapter<BillDetailsAdapter.MyViewHolder> {

    Context context;
    List<BillResponse> productResponseList;
    public static ArrayList<Integer> selectedList;


    public BillDetailsAdapter(Context context, List<BillResponse> productResponseList) {
        this.context = context;
        this.productResponseList = productResponseList;


    }

    @NonNull
    @Override
    public BillDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_details_list, parent, false);
        return new BillDetailsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillDetailsAdapter.MyViewHolder holder, int position) {

        holder.month.setText(productResponseList.get(position).getStart_date());
        holder.amt.setText(productResponseList.get(position).getDaily_product_name());
        holder.status.setText(productResponseList.get(position).getDailyQty());
        holder.action.setText(productResponseList.get(position).getDaily_amt());


    }

    @Override
    public int getItemCount() {
        return productResponseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView month, amt, status, action;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            month=itemView.findViewById(R.id.month);
            amt=itemView.findViewById(R.id.amt);
            status=itemView.findViewById(R.id.status);
            action=itemView.findViewById(R.id.action);

        }
    }
}