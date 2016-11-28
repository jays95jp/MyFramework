package com.kevadiyakrunalk.myframework.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kevadiyakrunalk.commonutils.common.Logs;
import com.kevadiyakrunalk.mvvmarchitecture.MvvmFragment;
import com.kevadiyakrunalk.mvvmarchitecture.common.BindingConfig;
import com.kevadiyakrunalk.myframework.R;
import com.kevadiyakrunalk.myframework.databinding.FragmentFontBinding;
import com.kevadiyakrunalk.myframework.viewmodels.FontFragmentViewModel;

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
