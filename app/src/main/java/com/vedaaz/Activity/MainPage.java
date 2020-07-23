package com.vedaaz.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.vedaaz.Extra.Common;
import com.vedaaz.Fragment.Account;
import com.vedaaz.Fragment.AdHocSubscription;
import com.vedaaz.Fragment.AllCategory;
import com.vedaaz.Fragment.Basket;
import com.vedaaz.Fragment.Bill;
import com.vedaaz.Fragment.Home;
import com.vedaaz.Fragment.NeedHelp;
import com.vedaaz.Fragment.Offers;
import com.vedaaz.Fragment.PauseSubscription;
import com.vedaaz.Fragment.Subscription;
import com.vedaaz.Fragment.ViewSubscription;
import com.vedaaz.R;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class MainPage extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;
    public static ImageView menu, back, logo, notification;
    public static TextView title;
    public static RelativeLayout titleLayout;
    public static LinearLayout searchLayout;
    public static TextView notificationCount;
    public static BottomNavigationView bottomNavigationView;
    public NavigationView navigationView;
    public static DrawerLayout drawerLayout;
    public static String userId, mobileNumber,first_name, cartId, userName, userCityId, userAreaId, walletAmount, currency = "₹";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        ButterKnife.bind(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initViews();

        loadFragment(new Home(), false);
        try{

            userId = Common.getSavedUserData(MainPage.this, "userId");
            first_name = Common.getSavedUserData(MainPage.this, "first_name");
            mobileNumber = Common.getSavedUserData(MainPage.this, "mobileNumber");
            cartId = Common.getSavedUserData(MainPage.this, "cartId");
            walletAmount = Common.getSavedUserData(MainPage.this, "walletAmount");
            userName = Common.getSavedUserData(MainPage.this, "userName");
            userCityId = Common.getSavedUserData(MainPage.this, "userCityId");
            userAreaId = Common.getSavedUserData(MainPage.this, "userAreaId");
            Log.e("userId",""+userId);

        }catch (Exception e) {
            e.printStackTrace();
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){

                    case R.id.home_homepage:
                        loadFragment(new Home(), false);
                        return true;

                    case R.id.category_homepage:
                    loadFragment(new AllCategory(), false);
                        return true;

                    case R.id.offer_homepage:
                        loadFragment(new Offers(), false);
                        return true;

                    case R.id.bill_homepage:
                    loadFragment(new Bill(), false);
                        return true;

                    case R.id.basket_homepage:
                    loadFragment(new Basket(), false);
                        return true;
                }
                return false;
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_accout:
                        loadFragment(new Account(),false);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_subscription:
                        loadFragment(new ViewSubscription(),true);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_pauseSubscription:
                        loadFragment(new PauseSubscription(),true);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_adHocSubscription:
                        loadFragment(new AdHocSubscription(),true);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_notification:

                        break;

                    case R.id.nav_rate:
                        break;

                    case R.id.nav_needhelp:
                        loadFragment(new NeedHelp(),true);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_referandearn:
                        break;

                    case R.id.nav_share:

                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody = "Here is the share content body";
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share via"));

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_logout:

                        final Dialog dialog11 = new Dialog(MainPage.this);
                        dialog11.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog11.setCancelable(false);
                        dialog11.setContentView(R.layout.logout_alert_dialog);
                        dialog11.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;

                        Button btnok=(Button) dialog11.findViewById(R.id.btn_ok);
                        Button btncancel=(Button) dialog11.findViewById(R.id.btn_Cancel);
                        ImageView img_exit=(ImageView)dialog11.findViewById(R.id.img_dialogexit);

                        btnok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog11.dismiss();

                            }
                        });

                        btncancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Common.saveUserData(getApplicationContext(), "user_id", "");

                                File file1 = new File("data/data/com.example.vedaaz/shared_prefs/admin.xml");
                                if (file1.exists()) {

                                    SharedPreferences preferences =getSharedPreferences("services", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.clear();
                                    editor.commit();
                                    finish();

                                    file1.delete();
                                }

                                Intent intent = new Intent(MainPage.this, Login.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finishAffinity();

                            }
                        });

                        img_exit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog11.dismiss();
                            }
                        });

                        dialog11.show();
                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;

                }

                return false;
            }
        });

    }


    @SuppressLint("RtlHardcoded")
    @OnClick({R.id.img_menu, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_menu:
                if (!drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
                break;
            case R.id.back:
                removeCurrentFragmentAndMoveBack();
                break;

        }
    }
    private void initViews() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        menu = (ImageView) findViewById(R.id.img_menu);
        back = (ImageView) findViewById(R.id.back);
        logo = (ImageView) findViewById(R.id.logo);
        title = (TextView) findViewById(R.id.title);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        /*notification = (ImageView) findViewById(R.id.notification);
        notificationCount = (TextView) findViewById(R.id.notificationCount);*/
        titleLayout = (RelativeLayout) findViewById(R.id.titleLayout);
//        relative_showcart = (RelativeLayout) findViewById(R.id.relative_showcart);
        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);

    }
    boolean isNavigationHide = false;

    private void animateNavigation(final boolean hide) {
        if (isNavigationHide && hide || !isNavigationHide && !hide) return;
        isNavigationHide = hide;
        int moveY = hide ? (2 * bottomNavigationView.getHeight()) : 0;
        bottomNavigationView.animate().translationY(moveY).setStartDelay(100).setDuration(300).start();
    }

    boolean isSearchBarHide = false;

    private void animateSearchBar(final boolean hide) {
        if (isSearchBarHide && hide || !isSearchBarHide && !hide) return;
        isSearchBarHide = hide;
        int moveY = hide ? -(2 * titleLayout.getHeight()) : 0;
        titleLayout.animate().translationY(moveY).setStartDelay(100).setDuration(300).start();
    }

    public void removeCurrentFragmentAndMoveBack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    public void loadFragment(Fragment fragment, Boolean bool) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        if (bool) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        // double press to exit
        if (back.getVisibility() == View.GONE) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
        } else {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toasty.normal(MainPage.this, "Press back once more to exit", Toasty.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }

    public void lockUnlockDrawer(int lockMode) {
        drawerLayout.setDrawerLockMode(lockMode);
        if (lockMode == DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
            menu.setVisibility(View.GONE);
            back.setVisibility(View.VISIBLE);
            MainPage.bottomNavigationView.setVisibility(View.GONE);
        } else {
            menu.setVisibility(View.VISIBLE);
            back.setVisibility(View.GONE);
            MainPage.bottomNavigationView.setVisibility(View.VISIBLE);
        }
    }

}
