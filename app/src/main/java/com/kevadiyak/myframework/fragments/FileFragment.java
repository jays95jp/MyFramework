package com.kevadiyak.myframework.fragments;

import android.support.annotation.NonNull;

import com.kevadiyak.commonutils.common.Logs;
import com.kevadiyak.mvvmarchitecture.MvvmFragment;
import com.kevadiyak.mvvmarchitecture.common.BindingConfig;
import com.kevadiyak.myframework.R;
import com.kevadiyak.myframework.databinding.FragmentFileBinding;
import com.kevadiyak.myframework.viewmodels.FileFragmentViewModel;

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
