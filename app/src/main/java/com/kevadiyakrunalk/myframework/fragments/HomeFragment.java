package com.kevadiyakrunalk.myframework.fragments;

import android.support.annotation.NonNull;

import com.kevadiyakrunalk.commonutils.common.Logs;
import com.kevadiyakrunalk.mvvmarchitecture.MvvmFragment;
import com.kevadiyakrunalk.mvvmarchitecture.common.BindingConfig;
import com.kevadiyakrunalk.myframework.R;
import com.kevadiyakrunalk.myframework.databinding.FragmentHomeBinding;
import com.kevadiyakrunalk.myframework.viewmodels.HomeFragmentViewModel;

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
