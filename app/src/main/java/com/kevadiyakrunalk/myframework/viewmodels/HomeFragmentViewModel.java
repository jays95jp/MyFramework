package com.kevadiyakrunalk.myframework.viewmodels;

import android.content.Context;

import com.kevadiyakrunalk.commonutils.common.Logs;
import com.kevadiyakrunalk.mvvmarchitecture.common.BaseViewModel;


public class HomeFragmentViewModel extends BaseViewModel {
    private Context context;
    private Logs logs;

    public HomeFragmentViewModel(Context context, Logs logs) {
        this.context = context;
        this.logs = logs;
    }
}
