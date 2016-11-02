package com.kevadiyak.myframework.fragments;

import android.support.annotation.NonNull;

import com.kevadiyak.commonutils.common.Logs;
import com.kevadiyak.mvvmarchitecture.MvvmFragment;
import com.kevadiyak.mvvmarchitecture.common.BindingConfig;
import com.kevadiyak.myframework.R;
import com.kevadiyak.myframework.databinding.FragmentHomeBinding;
import com.kevadiyak.myframework.viewmodels.HomeFragmentViewModel;

public class HomeFragment extends MvvmFragment<FragmentHomeBinding, HomeFragmentViewModel> {

    @NonNull
    @Override
    public HomeFragmentViewModel createViewModel() {
        return new HomeFragmentViewModel(getActivity(), Logs.getInstance(getActivity()));
    }

    @NonNull
    @Override
    public BindingConfig getBindingConfig() {
        return new BindingConfig(R.layout.fragment_home);
    }
}
