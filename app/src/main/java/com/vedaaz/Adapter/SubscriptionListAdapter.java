package com.vedaaz.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vedaaz.Module.SubscriptionResponse;
import com.vedaaz.R;

import java.util.ArrayList;
import java.util.List;


public class SubscriptionListAdapter extends RecyclerView.Adapter<SubscriptionListAdapter.MyViewHolder> {

    Context context;
    List<SubscriptionResponse> productResponseList;
    public static ArrayList<Integer> selectedList;
    public AlertDialog dialogBuilder;
    int total = 0;
    public static String str_quatity="1";
    public String cart_id;
    String sec="";
    String days="";
    String selectedDay="";

    public SubscriptionListAdapter(Context context, List<SubscriptionResponse> productResponseList) {
        this.context = context;
        this.productResponseList = productResponseList;


    }

    @NonNull
    @Override
    public SubscriptionListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscription_plan_list, parent, false);
        return new SubscriptionListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionListAdapter.MyViewHolder holder, int position) {

        holder.tv_subproductname_milk.setText(productResponseList.get(position).getDaily_product_name());

    }

    @Override
    public int getItemCount() {
        return productResponseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_subproductname_milk, tv_subproductdesc, tv_mrpratemilk, tv_buyoncemilkmilk, tv_subscribesubproductmilk, textSubFor;
        ImageView img_subproduct, download;
        LinearLayout catLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_subproductname_milk=itemView.findViewById(R.id.name);

        }
    }
}