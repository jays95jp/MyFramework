package com.kevadiyak.myframework.viewmodels;

import android.content.Context;

import com.kevadiyak.commonutils.common.Logs;
import com.kevadiyak.mvvmarchitecture.common.BaseViewModel;


public class HomeFragmentViewModel extends BaseViewModel {
    private Context context;
    private Logs logs;

    public HomeFragmentViewModel(Context context, Logs logs) {
        this.context = context;
        this.logs = logs;
    }
}
