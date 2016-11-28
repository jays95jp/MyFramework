package com.kevadiyakrunalk.myframework.fragments;

import android.support.annotation.NonNull;

import com.kevadiyakrunalk.commonutils.common.Logs;
import com.kevadiyakrunalk.mvvmarchitecture.MvvmFragment;
import com.kevadiyakrunalk.mvvmarchitecture.common.BindingConfig;
import com.kevadiyakrunalk.myframework.R;
import com.kevadiyakrunalk.myframework.databinding.FragmentPermissionBinding;
import com.kevadiyakrunalk.myframework.viewmodels.PermissionFragmentViewModel;

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
