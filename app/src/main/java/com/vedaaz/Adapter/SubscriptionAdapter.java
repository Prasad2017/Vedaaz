package com.vedaaz.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vedaaz.Activity.MainPage;
import com.vedaaz.Extra.Common;
import com.vedaaz.Module.DailyProductResponse;
import com.vedaaz.Module.LoginResponse;
import com.vedaaz.Module.SubscriptionResponse;
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


public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.MyViewHolder> {

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

    public SubscriptionAdapter(Context context, List<SubscriptionResponse> productResponseList) {
        this.context = context;
        this.productResponseList = productResponseList;


    }

    @NonNull
    @Override
    public SubscriptionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscription_list, parent, false);
        return new SubscriptionAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionAdapter.MyViewHolder holder, int position) {

        holder.tv_subproductname_milk.setText(productResponseList.get(position).getDaily_product_name());
        holder.tv_mrpratemilk.setText("Rs. " + productResponseList.get(position).getDaily_amt());
        holder.tv_subproductdesc.setText(productResponseList.get(position).getDaily_product_desc());

        if(productResponseList.get(position).getSubscription_type_id().equalsIgnoreCase("1")){
            holder.textSubFor.setText("Your subscribed for daily from"+productResponseList.get(position).getStart_date());
        }else  if(productResponseList.get(position).getSubscription_type_id().equalsIgnoreCase("2")){
            holder.textSubFor.setText("Your subscribed for Weekdays from"+productResponseList.get(position).getStart_date());

        }else  if(productResponseList.get(position).getSubscription_type_id().equalsIgnoreCase("3")){
            holder.textSubFor.setText("Your subscribed for Weekend from"+productResponseList.get(position).getStart_date());

        }
        // Glide.with(context.getApplicationContext()).load(productResponseList.get(position).getDaily_product_image()).into(holder.img_subproduct);
//        Log.e("imggggg", "" + productResponseList.getDaily_product_image(position).getDaily_product_id());

        Picasso.with(context)
                .load("" + productResponseList.get(position).getDaily_product_image())
                .placeholder(R.drawable.logo).into(holder.img_subproduct);
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

            tv_subproductname_milk=itemView.findViewById(R.id.tv_subproductname_milk);
            img_subproduct=itemView.findViewById(R.id.img_subproduct);
            tv_subproductdesc=itemView.findViewById(R.id.tv_subproductdesc);
            tv_mrpratemilk=itemView.findViewById(R.id.tv_mrpratemilk);
            tv_buyoncemilkmilk=itemView.findViewById(R.id.tv_buyoncemilkmilk);
            tv_subscribesubproductmilk=itemView.findViewById(R.id.tv_subscribesubproductmilk);
            textSubFor=itemView.findViewById(R.id.textSubFor);


        }
    }
}