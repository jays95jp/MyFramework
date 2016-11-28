package com.kevadiyakrunalk.myframework.fragments;

import android.support.annotation.NonNull;

import com.kevadiyakrunalk.commonutils.common.Logs;
import com.kevadiyakrunalk.mvvmarchitecture.MvvmFragment;
import com.kevadiyakrunalk.mvvmarchitecture.common.BindingConfig;
import com.kevadiyakrunalk.myframework.R;
import com.kevadiyakrunalk.myframework.databinding.FragmentFileBinding;
import com.kevadiyakrunalk.myframework.viewmodels.FileFragmentViewModel;

public class FileFragment extends MvvmFragment<FragmentFileBinding, FileFragmentViewModel> {

    @NonNull
    @Override
    public FileFragmentViewModel createViewModel() {
        return new FileFragmentViewModel(getActivity(), Logs.getInstance(getActivity()));
    }

    @NonNull
    @Override
    public BindingConfig getBindingConfig() {
        return new BindingConfig(R.layout.fragment_file);
    }
}
