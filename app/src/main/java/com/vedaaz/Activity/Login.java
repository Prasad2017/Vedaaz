package com.vedaaz.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;
import com.sdsmdg.tastytoast.TastyToast;
import com.vedaaz.Adapter.CircleAdapter;
import com.vedaaz.Extra.Common;
import com.vedaaz.Extra.DetectConnection;
import com.vedaaz.Module.LoginResponse;
import com.vedaaz.R;
import com.vedaaz.Retrofit.Api;
import com.vedaaz.Retrofit.ApiInterface;
import com.viewpagerindicator.LinePageIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;
import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    @BindView(R.id.loginLinearLayout)
    LinearLayout loginLinearLayout;
    @BindView(R.id.pager)
    ViewPager mPager;
    private LinePageIndicator mIndicator;
    @BindView(R.id.mobileNumber)
    EditText editText;
    @BindView(R.id.otp_view)
    OtpTextView otpTextView;
    @BindViews({R.id.loginCardview, R.id.otpCardview})
    List<CardView> cardViews;
    @BindView(R.id.txtmobileNumber)
    TextView txtmobileNumber;
    @BindView(R.id.verify)
    Button verify;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private String OTP = "", code;
    private static final int[] mDrawableResIds = {R.drawable.everydayessentials, R.drawable.healthyandfresh, R.drawable.convinientandreliable};
    private static final String[] textDataTitle = {"Everyday Essentials", "Healthy and Fresh", "Convential and Reliable"};
    private static final String[] textDataDesc = {"Get fresh fruits and vegetables along with milk by 7:00 AM everyday", "Subscribe and get Fresh milk delivered every morning", "You can order as late as 10 PM and get it delivered the next morning"};
    private CircleAdapter mAdapter;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    public static final String msgsend="http://synergytech.co.in/androidApp/Vedaaz/Customer/SMS.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestPermission();
        verify.setClickable(false);
        loginLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
            }
        });

        File file = new File("data/data/com.vedaaz/shared_prefs/user.xml");
        if (file.exists())
        {
            Intent intent = new Intent(Login.this, MainPage.class);
            startActivity(intent);
            finish();
        }

        mAdapter= new CircleAdapter(Login.this, mDrawableResIds, textDataTitle, textDataDesc);
        mPager.setAdapter(mAdapter);
        mIndicator = (LinePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        NUM_PAGES = mDrawableResIds.length;
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {

                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 20000, 20000);
        // Pager listener over indicator
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int position, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int position) {

            }
        });

        editText.setSelection(editText.getText().toString().trim().length());

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                try {
                    String number = editText.getText().toString().trim();
                    int length = number.trim().length();
                    if (length==10) {

                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        View view = getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    String number = editText.getText().toString().trim();
                    int length = number.trim().length();
                    if (length==10) {

                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        View view = getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    String number = editText.getText().toString().trim();
                    int length = number.trim().length();
                    if (length==10) {

                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        View view = getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        otpTextView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {
                otpTextView.showError();
                otpTextView.resetState();
            }
            @Override
            public void onOTPComplete(String otp) {
                try {
                    code = otpTextView.getOTP();
                    int length = code.trim().length();
                    if (length==4) {

                        verify.setClickable(true);
                        verify.setBackgroundResource(R.color.marun);
                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        View view = getCurrentFocus();
                        if (view != null) {
                            verify.setClickable(true);
                            verify.setBackgroundResource(R.color.marun);
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        }

    @OnClick({R.id.send, R.id.verify})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send:
                if(validate(editText)){
                    if (DetectConnection.checkInternetConnection(Login.this)) {
                        LoginData(editText.getText().toString().trim());
                    }else {
                        TastyToast.makeText(Login.this, "No Internet Connection", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT).show();
                    }
                }

                break;

            case R.id.verify:

                if (DetectConnection.checkInternetConnection(Login.this)) {
                    if (code.equalsIgnoreCase(OTP)){
//                        otpTextView.showSuccess();	// shows the success state to the user (can be set a bar color or drawable)
                        VerifyData(editText.getText().toString().trim());
                    }else {
//                        otpTextView.showError();	// shows the success state to the user (can be set a bar color or drawable)
//                        otpTextView.resetState();	// brings the views back to default state (the state it was at input)
                        Toasty.normal(Login.this, "Enter Valid OTP", Toasty.LENGTH_SHORT).show();
                    }

                }else {
                    TastyToast.makeText(Login.this, "No Internet Connection", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT).show();
                }

                break;

        }
    }

    @SuppressLint("DefaultLocale")
    private void LoginData(final String mobileNumber) {

        Random rand = new Random();
        System.out.printf("%04d%n", rand.nextInt(10000));
        OTP = String.format("%04d", rand.nextInt(10000));
        Log.e("OTP", "" + OTP);
        String message = "Your OTP is: " + OTP;
        String encoded_message = URLEncoder.encode(message);
        Log.e("OTP", "" + OTP);

        RequestParams requestParams = new RequestParams();
        requestParams.put("number", editText.getText().toString().trim());
        requestParams.put("message", encoded_message);


        // Log.e("encodedmessage",""+encoded_message);
        // Log.e("number",""+edt_mobileno.getText().toString());

        Log.e("asyncHttpClient1", "" + asyncHttpClient);
        // Toast.makeText(getApplicationContext(), "asyn" + asyncHttpClient, Toast.LENGTH_LONG).show();

        asyncHttpClient.get(msgsend, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String s = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(s);

                    if (jsonObject.getString("success").equals("1")) {
                        cardViews.get(0).setVisibility(View.GONE);
                        cardViews.get(1).setVisibility(View.VISIBLE);

                    } else {
                        Toasty.normal(Login.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Toasty.normal(Login.this, "No Internet Connection", Toasty.LENGTH_LONG).show();

            }
        });

    }

    private void VerifyData(String mobileNumber) {

        final ProgressDialog progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Login");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        Toast.makeText(this, ""+mobileNumber, Toast.LENGTH_SHORT).show();
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.Login(mobileNumber);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                String data = Objects.requireNonNull(response.body()).getSuccess();

                if (data.equalsIgnoreCase("1")) {
                    progressDialog.dismiss();
                    Toasty.success(Login.this, ""+response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                    pref = getSharedPreferences("user", Context.MODE_PRIVATE);
                    editor = pref.edit();
                    editor.putString("UserLogin","UserLoginSuccessful");
                    editor.apply();

                    Common.saveUserData(Login.this,"userId", response.body().getUserId());
                    Common.saveUserData(Login.this,"mobileNumber", mobileNumber);
                    Common.saveUserData(Login.this,"userMobile", editText.getText().toString().trim());

                    Intent verification = new Intent(Login.this, MainPage.class);
                    startActivity(verification);
                    finishAffinity();

                }else if (data.equalsIgnoreCase("2")){
                    progressDialog.dismiss();
                    Toasty.success(Login.this, ""+response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                    pref = getSharedPreferences("user", Context.MODE_PRIVATE);
                    editor = pref.edit();
                    editor.putString("UserLogin","UserLoginSuccessful");
                    editor.apply();

                    Common.saveUserData(Login.this,"userId", response.body().getUserId());
                    Common.saveUserData(Login.this,"mobileNumber", mobileNumber);
                    Common.saveUserData(Login.this,"userMobile", editText.getText().toString().trim());

                    Intent verification = new Intent(Login.this, Welcome.class);
                    verification.putExtra("userid", response.body().getUserId());
                    startActivity(verification);
                    finishAffinity();


                } else if(data.equalsIgnoreCase("0")){
                    progressDialog.dismiss();
                    Toasty.error(Login.this, ""+response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toasty.error(Login.this, "Server Error", Toasty.LENGTH_SHORT).show();
            }
        });

    }


    private void requestPermission() {

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {

                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();

                    }


                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        TastyToast.makeText(getApplicationContext(), "Error occurred! ", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT).show();
                    }
                })
                .onSameThread()
                .check();

    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                openSettings();
            }

        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private boolean validate(EditText editText) {
        if (editText.getText().toString().trim().length() > 0) {
            return true;
        }
        editText.setError("Please Fill This");
        editText.requestFocus();
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
