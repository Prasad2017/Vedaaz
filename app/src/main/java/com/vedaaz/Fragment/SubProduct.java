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

import butterknife.ButterKnife;

public class SubProduct extends Fragment {


    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sub_product, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("Sub Categories");

        return  view;
    }

    public void onStart() {
        super.onStart();
        Log.d("onStart", "called");
        MainPage.logo.setVisibility(View.GONE);
        MainPage.title.setVisibility(View.VISIBLE);
        ((MainPage) getActivity()).lockUnlockDrawer(1);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)MainPage.title.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.addRule(RelativeLayout.RIGHT_OF, R.id.back);
        MainPage.title.setLayoutParams(params);
        MainPage.searchLayout.setVisibility(View.GONE);

        if (DetectConnection.checkInternetConnection(getActivity())){
        }else {
            TastyToast.makeText(getActivity(), "No Internet Connection", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT).show();
        }
    }
}
