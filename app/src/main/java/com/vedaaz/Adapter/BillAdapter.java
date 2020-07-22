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
import java.util.Calendar;
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

        SimpleDateFormat format1=new SimpleDateFormat("yyyy/MM/dd");

        try {
            Calendar cal=Calendar.getInstance();
            SimpleDateFormat month_date = new SimpleDateFormat("MMM");
            int monthnum = Integer.parseInt(months) -1 ;
            cal.set(Calendar.MONTH,monthnum);
            String month_name = month_date.format(cal.getTime());
            Log.e("",""+month_name);
            holder.month.setText(month_name+"/"+years);

        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.amt.setText(productResponseList.get(position).getTotalAmount());
        holder.status.setText(productResponseList.get(position).getDel_status());

        String finalMonths = months;
        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BillDetails billDetails=new BillDetails();
                Bundle bundle=new Bundle();
                bundle.putString("year", productResponseList.get(position).getYear());
                bundle.putString("months", finalMonths);
                bundle.putString("date", productResponseList.get(position).getYear()+"/"+finalMonths);
                bundle.putString("planName", productResponseList.get(position).getSubsription_type());
                bundle.putString("startDate", productResponseList.get(position).getStart_date());
                bundle.putString("endDate", productResponseList.get(position).getEnd_date());
                bundle.putString("customerName", productResponseList.get(position).getFirst_name()+" "+productResponseList.get(position).getLast_name());
                bundle.putString("frequency", productResponseList.get(position).getFrequency());
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