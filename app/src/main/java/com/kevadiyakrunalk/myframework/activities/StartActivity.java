package com.kevadiyakrunalk.myframework.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kevadiyakrunalk.mvvmarchitecture.NavigatingMvvmActivity;
import com.kevadiyakrunalk.mvvmarchitecture.common.BindingConfig;
import com.kevadiyakrunalk.myframework.R;
import com.kevadiyakrunalk.myframework.databinding.ActivityStartBinding;
import com.kevadiyakrunalk.myframework.navigators.StartNavigator;
import com.kevadiyakrunalk.myframework.viewmodels.StartViewModel;
import com.kevadiyakrunalk.rxpreference.EncryptedPreferences;
import com.kevadiyakrunalk.rxpreference.rxpref.RxSharedPreferences;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class StartActivity extends NavigatingMvvmActivity<StartNavigator, ActivityStartBinding, StartViewModel> {

    @NonNull
    @Override
    public StartViewModel createViewModel() {
        return new StartViewModel(new RxSharedPreferences(EncryptedPreferences.getInstance(this, "sample", "example")));
    }

    @NonNull
    @Override
    public BindingConfig getBindingConfig() {
        return new BindingConfig(R.layout.activity_start);
    }

    @NonNull
    @Override
    public StartNavigator getNavigator() {
        return new StartNavigator(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        EncryptedPreferences prefStore = EncryptedPreferences.getInstance(this, "sample", "example");
        RxSharedPreferences mRxSharedPreferences = new RxSharedPreferences(prefStore);
        mRxSharedPreferences.observeString("Name", "xyz")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        //getViewModel().getNavigator().navigateToMain();
                        getNavigator().navigateToMain();
                    }
                });
    }
}