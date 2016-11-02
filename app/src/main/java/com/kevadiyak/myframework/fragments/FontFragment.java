package com.kevadiyak.myframework.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewStub;

import com.kevadiyak.commonutils.common.Logs;
import com.kevadiyak.mvvmarchitecture.MvvmFragment;
import com.kevadiyak.mvvmarchitecture.common.BindingConfig;
import com.kevadiyak.myframework.R;
import com.kevadiyak.myframework.databinding.FragmentFontBinding;
import com.kevadiyak.myframework.viewmodels.FontFragmentViewModel;

public class FontFragment extends MvvmFragment<FragmentFontBinding, FontFragmentViewModel> {

    @NonNull
    @Override
    public FontFragmentViewModel createViewModel() {
        return new FontFragmentViewModel(getActivity(), Logs.getInstance(getActivity()));
    }

    @NonNull
    @Override
    public BindingConfig getBindingConfig() {
        return new BindingConfig(R.layout.fragment_font);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //((ViewStub) getBinding().stub).inflate();
        //((ViewStub) getBinding().stubWithFontPath).inflate();
    }
}
