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


public class BillAdapter extends RecyclerView.Adapter<BillAdapter.MyViewHolder> {

    Context context;
    public static List<BillResponse> productResponseList;
    public static ArrayList<Integer> selectedList;


    public BillAdapter(Context context, List<BillResponse> productResponseList) {
        this.context = context;
        this.productResponseList = productResponseList;


    }

    @NonNull
    @Override
    public BillAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_list, parent, false);
        return new BillAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillAdapter.MyViewHolder holder, int position) {

        String months = productResponseList.get(position).getMonth();
        String years = productResponseList.get(position).getYear();
        String dat=productResponseList.get(position).getYear()+"/"+productResponseList.get(position).getMonth();
        SimpleDateFormat format1=new SimpleDateFormat("yyyy/MM/dd");
        Date dt1= null;
        try {
            dt1 = format1.parse(months);
            DateFormat format2=new SimpleDateFormat("MMMM");
            String finalDay=format2.format(dt1);
            holder.month.setText(finalDay);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.amt.setText(productResponseList.get(position).getDaily_amt());

        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BillDetails billDetails=new BillDetails();
                Bundle bundle=new Bundle();
                bundle.putString("year", years);
                bundle.putString("months", months);
                bundle.putString("dat", dat);
                billDetails.setArguments(bundle);
                ((MainPage)context).loadFragment(billDetails, true);

            }
        });
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