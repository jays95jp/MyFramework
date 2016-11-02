package com.kevadiyak.myframework.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kevadiyak.commonutils.common.Logs;
import com.kevadiyak.mvvmarchitecture.MvvmFragment;
import com.kevadiyak.mvvmarchitecture.common.BindingConfig;
import com.kevadiyak.myframework.R;
import com.kevadiyak.myframework.databinding.FragmentPreferenceBinding;
import com.kevadiyak.myframework.viewmodels.PreferenceFragmentViewModel;
import com.kevadiyak.rxpreference.SecuredPreferenceStore;
import com.kevadiyak.rxpreference.rxpref.RxSharedPreferences;

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
        SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance(getContext());

        mRxSharedPreferences = new RxSharedPreferences(prefStore);
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
