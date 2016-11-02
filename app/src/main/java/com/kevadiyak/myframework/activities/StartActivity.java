package com.kevadiyak.myframework.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kevadiyak.mvvmarchitecture.NavigatingMvvmActivity;
import com.kevadiyak.mvvmarchitecture.common.BindingConfig;
import com.kevadiyak.myframework.R;
import com.kevadiyak.myframework.databinding.ActivityStartBinding;
import com.kevadiyak.myframework.navigators.StartNavigator;
import com.kevadiyak.myframework.viewmodels.StartViewModel;
import com.kevadiyak.rxpreference.SecuredPreferenceStore;
import com.kevadiyak.rxpreference.rxpref.RxSharedPreferences;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class StartActivity extends NavigatingMvvmActivity<StartNavigator, ActivityStartBinding, StartViewModel> {

    @NonNull
    @Override
    public StartViewModel createViewModel() {
        return new StartViewModel(new RxSharedPreferences(SecuredPreferenceStore.getSharedInstance(this)));
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

        SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance(this);
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