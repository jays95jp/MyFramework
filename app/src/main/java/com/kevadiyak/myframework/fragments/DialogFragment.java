package com.kevadiyak.myframework.fragments;

import android.support.annotation.NonNull;

import com.kevadiyak.commonutils.common.Logs;
import com.kevadiyak.mvvmarchitecture.MvvmFragment;
import com.kevadiyak.mvvmarchitecture.common.BindingConfig;
import com.kevadiyak.myframework.R;
import com.kevadiyak.myframework.databinding.FragmentDialogBinding;
import com.kevadiyak.myframework.viewmodels.DialogFragmentViewModel;


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
