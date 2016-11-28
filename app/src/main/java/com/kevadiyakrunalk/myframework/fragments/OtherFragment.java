package com.kevadiyakrunalk.myframework.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kevadiyakrunalk.commonutils.common.Logs;
import com.kevadiyakrunalk.mvvmarchitecture.MvvmFragment;
import com.kevadiyakrunalk.mvvmarchitecture.common.BindingConfig;
import com.kevadiyakrunalk.myframework.R;
import com.kevadiyakrunalk.myframework.databinding.FragmentOtherBinding;
import com.kevadiyakrunalk.myframework.viewmodels.OtherFragmentViewModel;

/**
 * Created by Krunal.Kevadiya on 24/10/16.
 */
public class OtherFragment extends MvvmFragment<FragmentOtherBinding, OtherFragmentViewModel> {

    @NonNull
    @Override
    public BindingConfig getBindingConfig() {
        return new BindingConfig(R.layout.fragment_other);
    }

    @NonNull
    @Override
    public OtherFragmentViewModel createViewModel() {
        return new OtherFragmentViewModel(getActivity(), Logs.getInstance(getActivity()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getViewModel().setImageViewCha(getBinding().imgColorCha);
    }
}
