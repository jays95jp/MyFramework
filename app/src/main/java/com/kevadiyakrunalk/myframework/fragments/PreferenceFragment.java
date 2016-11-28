package com.kevadiyakrunalk.myframework.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kevadiyakrunalk.commonutils.common.Logs;
import com.kevadiyakrunalk.mvvmarchitecture.MvvmFragment;
import com.kevadiyakrunalk.mvvmarchitecture.common.BindingConfig;
import com.kevadiyakrunalk.myframework.R;
import com.kevadiyakrunalk.myframework.databinding.FragmentPreferenceBinding;
import com.kevadiyakrunalk.myframework.viewmodels.PreferenceFragmentViewModel;
import com.kevadiyakrunalk.rxpreference.EncryptedPreferences;
import com.kevadiyakrunalk.rxpreference.rxpref.RxSharedPreferences;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class PreferenceFragment extends MvvmFragment<FragmentPreferenceBinding, PreferenceFragmentViewModel> {

    private RxSharedPreferences mRxSharedPreferences;

    @NonNull
    @Override
    public PreferenceFragmentViewModel createViewModel() {
        return new PreferenceFragmentViewModel(getActivity(), Logs.getInstance(getActivity()));
    }

    @NonNull
    @Override
    public BindingConfig getBindingConfig() {
        return new BindingConfig(R.layout.fragment_preference);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRxSharedPreferences = new RxSharedPreferences(EncryptedPreferences.getInstance(getActivity(), "sample", "example"));
        mRxSharedPreferences.putString("Name", "abc");

        String string = mRxSharedPreferences.getString("Name", "xyz");
        Logs.getInstance(getActivity()).error("Preference", string);

        mRxSharedPreferences.observeString("Name", "xyz")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        getViewModel().setMessage(s);
                    }
                });
    }
}
