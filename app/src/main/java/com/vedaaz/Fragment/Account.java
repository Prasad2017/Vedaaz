package com.vedaaz.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.sdsmdg.tastytoast.TastyToast;
import com.vedaaz.Activity.MainPage;
import com.vedaaz.Extra.DetectConnection;
import com.vedaaz.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Account extends Fragment {

    View view;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);

        ButterKnife.bind(this, view);
        MainPage.title.setText("Account");

        return view;
    }

    @OnClick({R.id.walletLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.walletLayout:

                ((MainPage) getActivity()).loadFragment(new Wallet(), true);
                break;
        }
    }

    public void onStart() {
        super.onStart();
        Log.d("onStart", "called");
        MainPage.logo.setVisibility(View.GONE);
        MainPage.title.setVisibility(View.VISIBLE);
        MainPage.searchLayout.setVisibility(View.GONE);
        ((MainPage) getActivity()).lockUnlockDrawer(0);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)MainPage.title.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
params.addRule(RelativeLayout.CENTER_VERTICAL);params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.addRule(RelativeLayout.RIGHT_OF, R.id.img_menu);
        MainPage.title.setLayoutParams(params);
        if (DetectConnection.checkInternetConnection(getActivity())){

       }else {
            TastyToast.makeText(getActivity(), "No Internet Connection", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT).show();
        }
    }
}
