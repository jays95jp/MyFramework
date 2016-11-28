package com.kevadiyakrunalk.myframework.viewmodels;

import android.content.Context;

import com.kevadiyakrunalk.commonutils.common.Logs;
import com.kevadiyakrunalk.mvvmarchitecture.common.BaseViewModel;


public class AdapterFragmentViewModel extends BaseViewModel {
    private Context context;
    private Logs logs;

    public AdapterFragmentViewModel(Context context, Logs logs) {
        this.context = context;
        this.logs = logs;
    }
}
