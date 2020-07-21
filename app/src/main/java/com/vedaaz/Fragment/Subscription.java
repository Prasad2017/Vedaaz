package com.vedaaz.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;
import com.vedaaz.Activity.MainPage;
import com.vedaaz.Extra.DetectConnection;
import com.vedaaz.Module.LoginResponse;
import com.vedaaz.R;
import com.vedaaz.Retrofit.Api;
import com.vedaaz.Retrofit.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Subscription extends Fragment {

    View view;
    @BindView(R.id.productimg)
    ImageView productimg;
    @BindView(R.id.minus)
    ImageView minus;
    @BindViews({R.id.product_name, R.id.product_price, R.id.textRepeat, R.id.StartDate, R.id.qty, R.id.monady, R.id.tuesday, R.id.wesdnsday, R.id.thursday, R.id.friday, R.id.saturday, R.id.sunday})
    List<TextView> textViews;

    String product_id, product_name, product_img, product_rate, quantity, subId, days, product_desc;
    int total=0;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    public AlertDialog dialogBuilder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_subscription, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("Create Subscription");

        Bundle bundle=getArguments();
        product_img =bundle.getString("product_img");
        product_name =bundle.getString("product_name");
        product_rate =bundle.getString("product_rate");
        product_id =bundle.getString("product_id");
        product_desc =bundle.getString("product_desc");

        textViews.get(0).setText(product_name);
        textViews.get(1).setText(product_desc+"\nMrp: "+product_rate);
        Picasso.with(getActivity())
                .load(""+product_img)
                .placeholder(R.drawable.logo).into(productimg);


        return view;
    }
    @OnClick({R.id.cancel, R.id.subscribe, R.id.dateLayout, R.id.add, R.id.minus, R.id.repeatLayout})
    public void onClick(View view){
        switch (view.getId()) {

            case R.id.cancel:

                break;

            case R.id.subscribe:

                    int amt=Integer.parseInt(product_rate)* Integer.parseInt(quantity);
                    AddSubscription(product_id, subId, String.valueOf(amt), quantity, days, textViews.get(3).getText().toString());
//                ((MainPage)getActivity()).loadFragment(new ViewTask(), true);

                break;

            case R.id.dateLayout:
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                                calendar.set(year, month, day);

                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String strDate = format.format(calendar.getTime());

                                Log.e("date", "" + strDate);
                                textViews.get(3).setText(strDate);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();

                break;

            case R.id.add:
                total=total+1;
                textViews.get(4).setText(""+total);
                quantity=textViews.get(4).getText().toString();

                break;

            case R.id.minus:
                if(total!=1) {
                    total = total - 1;
                }else {
                    minus.setClickable(false);
                }
                textViews.get(4).setText(""+total);
                quantity=textViews.get(4).getText().toString();


                break;

            case R.id.repeatLayout:

                dialogBuilder = new AlertDialog.Builder(getActivity()).create();
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
                View f = inflater.inflate(R.layout.dialog_subscription_box, null);
                dialogBuilder.setCancelable(true);

                TextView mon,tue,wed,thru,fri,sat,sun,daily,weekdays,weekend,con;
                LinearLayout dayslayout;

                mon=f.findViewById(R.id.monady);
                tue=f.findViewById(R.id.tuesday);
                wed=f.findViewById(R.id.wesdnsday);
                thru=f.findViewById(R.id.thursday);
                fri=f.findViewById(R.id.friday);
                sat=f.findViewById(R.id.saturday);
                sun=f.findViewById(R.id.sunday);
                daily=f.findViewById(R.id.daily);
                weekdays=f.findViewById(R.id.weekdays);
                weekend=f.findViewById(R.id.weekend);
                con=f.findViewById(R.id.conitune);
                dayslayout=f.findViewById(R.id.dayslayout);


                daily.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dayslayout.setVisibility(View.GONE);

                        daily.setBackgroundResource(R.color.yellow_300);
                        weekdays.setBackgroundResource(R.drawable.btn_backgroud_transperent);
                        weekend.setBackgroundResource(R.drawable.btn_backgroud_transperent);

                        mon.setBackgroundResource(R.drawable.rount_shape);
                        tue.setBackgroundResource(R.drawable.rount_shape);
                        wed.setBackgroundResource(R.drawable.rount_shape);
                        thru.setBackgroundResource(R.drawable.rount_shape);
                        fri.setBackgroundResource(R.drawable.rount_shape);
                        sat.setBackgroundResource(R.drawable.rount_shape);
                        sun.setBackgroundResource(R.drawable.rount_shape);
                        subId="1";
                        days="Daily";

                        textViews.get(5).setBackgroundResource(R.drawable.rount_shape);
                        textViews.get(6).setBackgroundResource(R.drawable.rount_shape);
                        textViews.get(7).setBackgroundResource(R.drawable.rount_shape);
                        textViews.get(8).setBackgroundResource(R.drawable.rount_shape);
                        textViews.get(9).setBackgroundResource(R.drawable.rount_shape);
                        textViews.get(10).setBackgroundResource(R.drawable.rount_shape);
                        textViews.get(11).setBackgroundResource(R.drawable.rount_shape);
                    }
                });

                weekdays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dayslayout.setVisibility(View.GONE);
                        daily.setBackgroundResource(R.drawable.btn_backgroud_transperent);
                        weekend.setBackgroundResource(R.drawable.btn_backgroud_transperent);
                        weekdays.setBackgroundResource(R.color.yellow_300);

                        mon.setBackgroundResource(R.drawable.rount_shape);
                        tue.setBackgroundResource(R.drawable.btn_round_grey);
                        wed.setBackgroundResource(R.drawable.rount_shape);
                        thru.setBackgroundResource(R.drawable.btn_round_grey);
                        fri.setBackgroundResource(R.drawable.rount_shape);
                        sat.setBackgroundResource(R.drawable.btn_round_grey);
                        sun.setBackgroundResource(R.drawable.rount_shape);

                        textViews.get(10).setBackgroundResource(R.drawable.btn_round_grey);
                        textViews.get(11).setBackgroundResource(R.drawable.rount_shape);
                        textViews.get(5).setBackgroundResource(R.drawable.rount_shape);
                        textViews.get(6).setBackgroundResource(R.drawable.btn_round_grey);
                        textViews.get(7).setBackgroundResource(R.drawable.rount_shape);
                        textViews.get(8).setBackgroundResource(R.drawable.btn_round_grey);
                        textViews.get(9).setBackgroundResource(R.drawable.rount_shape);

                        subId="2";
                        days="Alternate";
                    }
                });

                weekend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dayslayout.setVisibility(View.VISIBLE);
                        weekend.setBackgroundResource(R.color.yellow_300);
                        weekdays.setBackgroundResource(R.drawable.btn_backgroud_transperent);
                        daily.setBackgroundResource(R.drawable.btn_backgroud_transperent);

                        mon.setBackgroundResource(R.drawable.btn_round_grey);
                        tue.setBackgroundResource(R.drawable.btn_round_grey);
                        wed.setBackgroundResource(R.drawable.btn_round_grey);
                        thru.setBackgroundResource(R.drawable.btn_round_grey);
                        fri.setBackgroundResource(R.drawable.btn_round_grey);
                        sat.setBackgroundResource(R.drawable.rount_shape);
                        sun.setBackgroundResource(R.drawable.rount_shape);

                        textViews.get(5).setBackgroundResource(R.drawable.btn_round_grey);
                        textViews.get(6).setBackgroundResource(R.drawable.btn_round_grey);
                        textViews.get(7).setBackgroundResource(R.drawable.btn_round_grey);
                        textViews.get(8).setBackgroundResource(R.drawable.btn_round_grey);
                        textViews.get(9).setBackgroundResource(R.drawable.btn_round_grey);
                        textViews.get(10).setBackgroundResource(R.drawable.rount_shape);
                        textViews.get(11).setBackgroundResource(R.drawable.rount_shape);

                        subId="3";
                        days="Weekend";
                       /* sat.setBackgroundResource(R.color.gray);
                        sun.setBackgroundResource(R.color.gray);*/
                    }
                });
                con.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                });

                dialogBuilder.setView(f);
                dialogBuilder.show();
                dialogBuilder.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                break;
        }
    }


    public void AddSubscription( String productId, String subscribeId, String  amount, String qty, String day, String stdate)
    {

       /* ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Subscribing.");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(true);
*/
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.AddSubscription(MainPage.userId, MainPage.userCityId, MainPage.userAreaId ,productId, subscribeId, amount, qty, day, stdate);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                String data = Objects.requireNonNull(response.body()).getSuccess();

                if (data.equalsIgnoreCase("1")) {
                    Toasty.success(getActivity(), ""+response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                      // progressDialog.dismiss();
                    ((MainPage)getActivity()).removeCurrentFragmentAndMoveBack();


                }else if(data.equalsIgnoreCase("0")){
                    Toasty.error(getActivity(), ""+response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                    //  progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                    // progressDialog.dismiss();
                Toasty.error(getActivity(), "Server Error", Toasty.LENGTH_SHORT).show();
            }
        });


    }
    public void onStart() {
        super.onStart();
        Log.d("onStart", "called");
        MainPage.logo.setVisibility(View.GONE);
        MainPage.title.setVisibility(View.VISIBLE);
        MainPage.back.setVisibility(View.VISIBLE);
        MainPage.menu.setVisibility(View.GONE);
        MainPage.searchLayout.setVisibility(View.GONE);
        MainPage.bottomNavigationView.setVisibility(View.GONE);

        if (DetectConnection.checkInternetConnection(getActivity())){
        }else {
            TastyToast.makeText(getActivity(), "No Internet Connection", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT).show();
        }
    }
}
