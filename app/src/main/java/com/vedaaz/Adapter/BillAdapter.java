package com.vedaaz.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vedaaz.Activity.MainPage;
import com.vedaaz.Activity.RazorpayBuyNow;
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

import es.dmoral.toasty.Toasty;


public class BillAdapter extends RecyclerView.Adapter<BillAdapter.MyViewHolder> {

    Context context;
    public static List<BillResponse> productResponseList;
    double totalAmount = 0f, amountPayable;

    public BillAdapter(Context context, List<BillResponse> productResponseList) {
        this.context = context;
        this.productResponseList = productResponseList;
        for(int position = 0 ;position<productResponseList.size();position++){
            totalAmount = totalAmount + (Double.parseDouble(productResponseList.get(position).getTotalAmount()));
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_list, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        BillResponse billResponse = productResponseList.get(position);

        if (position==productResponseList.size()-1) {
            holder.submitBill.setVisibility(View.VISIBLE);
            String totalAmountPayable = String.valueOf(totalAmount);
            holder.submitBill.setText("Pay all bills ( "+MainPage.currency+" "+totalAmountPayable+" )");
            holder.submitBill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (totalAmount > 0f) {

                            Intent intent = new Intent(context, RazorpayBuyNow.class);
                            intent.putExtra("pendingAmount", totalAmountPayable);
                            intent.putExtra("userId", MainPage.userId);
                            context.startActivity(intent);

                        } else {
                            Toasty.normal(context, "Nothing is Pending", Toasty.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        } else
            holder.submitBill.setVisibility(View.GONE);
        holder.amt.setText(productResponseList.get(position).getTotalAmount());
        holder.status.setText(productResponseList.get(position).getDel_status());

        String months = productResponseList.get(position).getMonth();
        String years = productResponseList.get(position).getYear();


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


        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BillDetails billDetails=new BillDetails();
                Bundle bundle=new Bundle();
                bundle.putString("year", productResponseList.get(position).getYear());
                bundle.putString("months", months);
                bundle.putString("date", productResponseList.get(position).getYear()+"/"+months);
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

        TextView month, amt, status, action, submitBill;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            month=itemView.findViewById(R.id.month);
            amt=itemView.findViewById(R.id.amt);
            status=itemView.findViewById(R.id.status);
            action=itemView.findViewById(R.id.action);
            submitBill=itemView.findViewById(R.id.submitBill);

        }
    }
}