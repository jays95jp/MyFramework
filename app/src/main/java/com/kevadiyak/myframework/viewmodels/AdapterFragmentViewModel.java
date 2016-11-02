package com.kevadiyak.myframework.viewmodels;

import android.content.Context;

import com.kevadiyak.commonutils.common.Logs;
import com.kevadiyak.mvvmarchitecture.common.BaseViewModel;


public class AdapterFragmentViewModel extends BaseViewModel {
    private Context context;
    private Logs logs;

    public AdapterFragmentViewModel(Context context, Logs logs) {
        this.context = context;
        this.logs = logs;
    }
}
