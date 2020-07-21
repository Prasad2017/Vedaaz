package com.vedaaz.Adapter;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.vedaaz.Activity.Login;
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

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class DilayAdapter extends RecyclerView.Adapter<DilayAdapter.MyViewHolder> {

    Context context;
    List<DailyProductResponse> productResponseList;
    public static ArrayList<Integer> selectedList;
    public AlertDialog dialogBuilder;
    int total = 0;
    public static String str_quatity="1";
    public String cart_id;
    String sec="";
    String days="";
    String selectedDay="";

    public DilayAdapter(Context context, List<DailyProductResponse> productResponseList) {
        this.context = context;
        this.productResponseList = productResponseList;


    }

    @NonNull
    @Override
    public DilayAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_product_list, parent, false);
        return new DilayAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DilayAdapter.MyViewHolder holder, int position) {

        holder.tv_subproductname_milk.setText(productResponseList.get(position).getDaily_product_name());
        holder.tv_mrpratemilk.setText("Rs. "+productResponseList.get(position).getDaily_product_rate());
        holder.tv_subproductdesc.setText(productResponseList.get(position).getDaily_product_desc());

        // Glide.with(context.getApplicationContext()).load(productResponseList.get(position).getDaily_product_image()).into(holder.img_subproduct);
        Log.e("imggggg",""+productResponseList.get(position).getDaily_product_id());

        Picasso.with(context)
                .load(""+productResponseList.get(position).getDaily_product_image())
                .placeholder(R.drawable.logo).into(holder.img_subproduct);

        holder.tv_buyoncemilkmilk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder = new AlertDialog.Builder(context).create();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View f = inflater.inflate(R.layout.dialog_dailyproduct, null);
                dialogBuilder.setCancelable(false);
                TextView servicetype = (TextView) f.findViewById(R.id.servicetype);
                final TextView serviceamount = (TextView) f.findViewById(R.id.serviceamount);
                final TextView tv_order_count = (TextView) f.findViewById(R.id.tv_order_count);
                final TextView note = (TextView) f.findViewById(R.id.note);
                final Button cancel = (Button) f.findViewById(R.id.cancel);
                Button add = (Button) f.findViewById(R.id.add);
                ImageView imgminus = (ImageView) f.findViewById(R.id.imgminus);
                ImageView imgadd = (ImageView) f.findViewById(R.id.imgadd);
                final LinearLayout addqtyLayout = (LinearLayout) f.findViewById(R.id.addqtyLayout);


                imgadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        total=total+1;
                        tv_order_count.setText(""+total);
                        str_quatity=tv_order_count.getText().toString();

                    }
                });


                imgminus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(total!=1) {
                            total = total - 1;
                        }
                        tv_order_count.setText(""+total);
                        str_quatity=tv_order_count.getText().toString();



                    }
                });



                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!MainPage.userId.equalsIgnoreCase("")) {

                            if(MainPage.cartId.equalsIgnoreCase("")){
                                cart_id="not";
                                AddToCartPost(cart_id,"0","0",productResponseList.get(position).getDaily_product_id(),
                                        productResponseList.get(position).getDaily_product_rate(), str_quatity);
                            }else {
                                cart_id=MainPage.cartId;
                                AddToCartPost(cart_id,"0","0",productResponseList.get(position).getDaily_product_id(),
                                        productResponseList.get(position).getDaily_product_rate(), str_quatity);
                            }

                        }
                    }
                });


                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();

                    }
                });



                dialogBuilder.setView(f);
                dialogBuilder.show();

            }

        });

        holder.tv_subscribesubproductmilk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Subscription subscription=new Subscription();
                Bundle bundle=new Bundle();
                bundle.putString("product_img",productResponseList.get(position).getDaily_product_image());
                bundle.putString("product_name",productResponseList.get(position).getDaily_product_name());
                bundle.putString("product_rate",productResponseList.get(position).getDaily_product_rate());
                bundle.putString("product_id",productResponseList.get(position).getDaily_product_id());
                bundle.putString("product_desc",productResponseList.get(position).getDaily_product_desc());
                subscription.setArguments(bundle);
                ((MainPage)context).loadFragment(subscription, true);

               /* dialogBuilder = new AlertDialog.Builder(context).create();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View f = inflater.inflate(R.layout.dialog_subscription, null);
                dialogBuilder.setCancelable(false);

                final Button cancel = (Button) f.findViewById(R.id.cancel);
                final Button add = (Button) f.findViewById(R.id.add);

                RadioButton daily=f.findViewById(R.id.daily);
                RadioButton weekend=f.findViewById(R.id.weekend);
                RadioButton selected=f.findViewById(R.id.selected);
                CheckBox monday=f.findViewById(R.id.monday);
                CheckBox tuesday=f.findViewById(R.id.tuesday);
                CheckBox wednsday=f.findViewById(R.id.wednsday);
                CheckBox thursday=f.findViewById(R.id.thursday);
                CheckBox friday=f.findViewById(R.id.friday);
                CheckBox saturday=f.findViewById(R.id.saturday);
                CheckBox sunday=f.findViewById(R.id.sunday);
                LinearLayout chkLayout=f.findViewById(R.id.chkLayout);

                if(monday.isChecked()){
                    days = days + "Monday";
                } if(tuesday.isChecked()){
                    days = days + "Tuesday";
                } if(wednsday.isChecked()){
                    days = days + "Wednsday";
                } if(thursday.isChecked()){
                    days = days + "Thursday";
                } if(friday.isChecked()){
                    days = days + "Friday";
                } if(saturday.isChecked()){
                    days = days + "Saturday";
                } if(sunday.isChecked()){
                    days = days + "Sunday";
                }

                if(daily.isSelected()){
                    selectedDay="All days";
                    monday.setChecked(false);
                    tuesday.setChecked(false);
                    wednsday.setChecked(false);
                    thursday.setChecked(false);
                    friday.setChecked(false);
                    saturday.setChecked(false);
                    sunday.setChecked(false);
                    sec="1";
                }
                if(weekend.isSelected()){
                    selectedDay= "Saturday, Sunday";
                    monday.setChecked(false);
                    tuesday.setChecked(false);
                    wednsday.setChecked(false);
                    thursday.setChecked(false);
                    friday.setChecked(false);
                    saturday.setChecked(false);
                    sunday.setChecked(false);
                    sec="2";
                }
                if(selected.isSelected()){
                    selectedDay=days;
                    chkLayout.setVisibility(View.VISIBLE);
                    monday.setChecked(true);
                    tuesday.setChecked(true);
                    wednsday.setChecked(true);
                    thursday.setChecked(true);
                    friday.setChecked(true);
                    saturday.setChecked(true);
                    sunday.setChecked(true);
                    sec="3";
                }

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();

                    }
                });

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                        AddSubscription(productResponseList.get(position).getDaily_product_id(), sec, productResponseList.get(position).getDaily_product_rate(), selectedDay);
                    }
                });

                dialogBuilder.setView(f);
                dialogBuilder.show();*/
            }
        });

    }


    public void AddToCartPost(String cartid, String productId, String subProductId, String  dailyProductid, String quantity, String price)
    {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Adding to cart");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(true);


        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.AddToCart(MainPage.userId, cartid,productId, subProductId, dailyProductid, quantity, cartid, price);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                String data = Objects.requireNonNull(response.body()).getSuccess();

                if (data.equalsIgnoreCase("1")) {
                    Toasty.success(context, ""+response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Common.saveUserData(context,"cartId", response.body().getCartId());


                }else if(data.equalsIgnoreCase("0")){
                    Toasty.error(context, ""+response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toasty.error(context, "Server Error", Toasty.LENGTH_SHORT).show();
            }
        });


    }

    public void AddSubscription( String productId, String subscribeId, String  amount, String day, String stDate)
    {

        /*final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Subscribing.");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(true);
*/

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.AddSubscription(MainPage.userId,"","", productId, subscribeId, amount, "", day, stDate);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                String data = Objects.requireNonNull(response.body()).getSuccess();

                if (data.equalsIgnoreCase("1")) {
                    Toasty.success(context, ""+response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                 //   progressDialog.dismiss();


                }else if(data.equalsIgnoreCase("0")){
                    Toasty.error(context, ""+response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                  //  progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
           //     progressDialog.dismiss();
                Toasty.error(context, "Server Error", Toasty.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return productResponseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_subproductname_milk, tv_subproductdesc, tv_mrpratemilk, tv_buyoncemilkmilk, tv_subscribesubproductmilk;
        ImageView img_subproduct, download;
        LinearLayout catLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_subproductname_milk=itemView.findViewById(R.id.tv_subproductname_milk);
            img_subproduct=itemView.findViewById(R.id.img_subproduct);
            tv_subproductdesc=itemView.findViewById(R.id.tv_subproductdesc);
            tv_mrpratemilk=itemView.findViewById(R.id.tv_mrpratemilk);
            tv_buyoncemilkmilk=itemView.findViewById(R.id.tv_buyoncemilkmilk);
            tv_subscribesubproductmilk=itemView.findViewById(R.id.tv_subscribesubproductmilk);


        }
    }
}