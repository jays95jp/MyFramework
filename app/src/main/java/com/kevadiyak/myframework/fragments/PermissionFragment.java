package com.kevadiyak.myframework.fragments;

import android.support.annotation.NonNull;

import com.kevadiyak.commonutils.common.Logs;
import com.kevadiyak.mvvmarchitecture.MvvmFragment;
import com.kevadiyak.mvvmarchitecture.common.BindingConfig;
import com.kevadiyak.myframework.R;
import com.kevadiyak.myframework.databinding.FragmentPermissionBinding;
import com.kevadiyak.myframework.viewmodels.PermissionFragmentViewModel;

public class PermissionFragment extends MvvmFragment<FragmentPermissionBinding, PermissionFragmentViewModel> {

    @NonNull
    @Override
    public PermissionFragmentViewModel createViewModel() {
        return new PermissionFragmentViewModel(getActivity(), Logs.getInstance(getActivity()));
    }

    @NonNull
    @Override
    public BindingConfig getBindingConfig() {
        return new BindingConfig(R.layout.fragment_permission);
    }
}
