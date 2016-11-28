package com.kevadiyakrunalk.myframework.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kevadiyakrunalk.commonutils.common.Logs;
import com.kevadiyakrunalk.mvvmarchitecture.MvvmFragment;
import com.kevadiyakrunalk.mvvmarchitecture.common.BindingConfig;
import com.kevadiyakrunalk.myframework.R;
import com.kevadiyakrunalk.myframework.databinding.FragmentPhotoBinding;
import com.kevadiyakrunalk.myframework.viewmodels.PhotoFragmentViewModel;

public class PhotoFragment extends MvvmFragment<FragmentPhotoBinding, PhotoFragmentViewModel> {

    @NonNull
    @Override
    public PhotoFragmentViewModel createViewModel() {
        return new PhotoFragmentViewModel(getActivity(), Logs.getInstance(getActivity()));
    }

    @NonNull
    @Override
    public BindingConfig getBindingConfig() {
        return new BindingConfig(R.layout.fragment_photo);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getViewModel().setImageView(getBinding().imageView2);
    }
}
