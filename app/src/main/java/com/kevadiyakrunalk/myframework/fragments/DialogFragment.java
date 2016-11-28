package com.kevadiyakrunalk.myframework.fragments;

import android.support.annotation.NonNull;

import com.kevadiyakrunalk.commonutils.common.Logs;
import com.kevadiyakrunalk.mvvmarchitecture.MvvmFragment;
import com.kevadiyakrunalk.mvvmarchitecture.common.BindingConfig;
import com.kevadiyakrunalk.myframework.R;
import com.kevadiyakrunalk.myframework.databinding.FragmentDialogBinding;
import com.kevadiyakrunalk.myframework.viewmodels.DialogFragmentViewModel;


public class DialogFragment extends MvvmFragment<FragmentDialogBinding, DialogFragmentViewModel> {

    @NonNull
    @Override
    public DialogFragmentViewModel createViewModel() {
        return new DialogFragmentViewModel(getActivity(), this, Logs.getInstance(getActivity()));
    }

    @NonNull
    @Override
    public BindingConfig getBindingConfig() {
        return new BindingConfig(R.layout.fragment_dialog);
    }

}
