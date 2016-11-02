package com.kevadiyak.myframework.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kevadiyak.commonutils.common.Logs;
import com.kevadiyak.mvvmarchitecture.MvvmFragment;
import com.kevadiyak.mvvmarchitecture.common.BindingConfig;
import com.kevadiyak.myframework.R;
import com.kevadiyak.myframework.databinding.FragmentValidationBinding;
import com.kevadiyak.myframework.viewmodels.ValidationFragmentViewModel;

public class ValidationFragment extends MvvmFragment<FragmentValidationBinding, ValidationFragmentViewModel> {
    Logs logs;
    @NonNull
    @Override
    public ValidationFragmentViewModel createViewModel() {
        logs = Logs.getInstance(getActivity());
        return new ValidationFragmentViewModel(getActivity(), logs);
    }

    @NonNull
    @Override
    public BindingConfig getBindingConfig() {
        return new BindingConfig(R.layout.fragment_validation);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getViewModel().initBinder(getBinding());
        getViewModel().initValue();
    }
}
