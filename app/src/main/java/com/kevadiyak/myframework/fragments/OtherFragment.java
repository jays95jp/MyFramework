package com.kevadiyak.myframework.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kevadiyak.commonutils.common.Logs;
import com.kevadiyak.mvvmarchitecture.MvvmFragment;
import com.kevadiyak.mvvmarchitecture.common.BindingConfig;
import com.kevadiyak.myframework.R;
import com.kevadiyak.myframework.databinding.FragmentOtherBinding;
import com.kevadiyak.myframework.viewmodels.OtherFragmentViewModel;

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
