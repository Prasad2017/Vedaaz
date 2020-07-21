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

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.vedaaz.Activity.MainPage;
import com.vedaaz.Extra.Common;
import com.vedaaz.Fragment.SubProduct;
import com.vedaaz.Fragment.Subscription;
import com.vedaaz.Module.DailyProductResponse;
import com.vedaaz.Module.LoginResponse;
import com.vedaaz.Module.ProductResponse;
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


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    Context context;
    List<ProductResponse> productResponseList;
    public static ArrayList<Integer> selectedList;
    public AlertDialog dialogBuilder;
    int total = 0;
    public static String str_quatity="1";
    public String cart_id;
    String sec="";
    String days="";
    String selectedDay="";

    public ProductAdapter(Context context, List<ProductResponse> productResponseList) {
        this.context = context;
        this.productResponseList = productResponseList;


    }

    @NonNull
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list, parent, false);
        return new ProductAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.MyViewHolder holder, int position) {

        holder.tv_categotynm.setText(productResponseList.get(position).getProduct_name());
        Picasso.with(context)
                .load(""+productResponseList.get(position).getProduct_image())
                .placeholder(R.drawable.logo).into(holder.icon);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SubProduct subProduct= new SubProduct();
                Bundle bundle = new Bundle();
                bundle.putString("productId", productResponseList.get(position).getProduct_id());
                subProduct.setArguments(bundle);
                ((MainPage)context).loadFragment(subProduct, true);

            }
        });

    }


    @Override
    public int getItemCount() {
        return productResponseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_categotynm;
        ImageView icon;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View convertView) {
            super(convertView);

            icon = (ImageView) convertView.findViewById(R.id.icon);
            tv_categotynm=(TextView)convertView.findViewById(R.id.tv_categoryname);
            linearLayout=(LinearLayout) convertView.findViewById(R.id.linearLayout);


        }
    }
}