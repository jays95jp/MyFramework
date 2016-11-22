package com.kevadiyak.myframework.viewmodels;

import android.Manifest;
import android.content.Context;
import android.databinding.Bindable;
import android.text.TextUtils;
import android.view.View;

import com.kevadiyak.commonutils.common.Logs;
import com.kevadiyak.mvvmarchitecture.common.BaseViewModel;
import com.kevadiyak.myframework.BR;
import com.kevadiyak.rxpermissions.PermissionResult;
import com.kevadiyak.rxpermissions.RxPermissions;

public class PermissionFragmentViewModel extends BaseViewModel {
    private Context context;
    private Logs logs;
    private String message;

    public PermissionFragmentViewModel(Context context, Logs logs) {
        this.context = context;
        this.logs = logs;
    }

    @Bindable
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        if(TextUtils.equals(this.message, message)) return;

        this.message = message;
        notifyPropertyChanged(BR.message);
    }

    public void onSingle(View view) {
        RxPermissions.getInstance(context)
            .checkMPermission(new PermissionResult() {
                @Override
                public void onPermissionResult(String permission, boolean granted) {
                    setMessage("Permission -> " + permission + ", granted - > " + granted);
                    logs.error("Single Permission", "Permission -> " + permission + ", granted - > " + granted);
                }
            }, Manifest.permission.CAMERA);
    }

    public void onMultiple(View view) {
        RxPermissions.getInstance(context)
            .checkMPermission(new PermissionResult() {
                @Override
                public void onPermissionResult(String permission, boolean granted) {
                    setMessage("Permission -> " + permission + ", granted - > " + granted);
                    logs.error("Single Permission", "Permission -> " + permission + ", granted - > " + granted);
                }
            }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public void onEach(View view) {
        RxPermissions.getInstance(context)
            .checkMEachPermission(new PermissionResult() {
                @Override
                public void onPermissionResult(String permission, boolean granted) {
                    setMessage("Permission -> " + permission + ", granted - > " + granted);
                    logs.error("Single Permission", "Permission -> " + permission + ", granted - > " + granted);
                }
            }, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    public void onEnsure(View view) {
        RxPermissions.getInstance(context)
                .checkMEnsurePermission(new PermissionResult() {
                    @Override
                    public void onPermissionResult(String permission, boolean granted) {
                        setMessage("Permission -> " + permission + ", granted - > " + granted);
                        logs.error("Ensure Permission", "Permission -> " + permission + ", granted - > " + granted);
                    }
                }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA);
    }

    public void onEnsureEach(View view) {
        RxPermissions.getInstance(context)
                .checkMEnsureEachPermission(new PermissionResult() {
                    @Override
                    public void onPermissionResult(String permission, boolean granted) {
                        setMessage("Permission -> " + permission + ", granted - > " + granted);
                        logs.error("Ensure Each Permission", "Permission -> " + permission + ", granted - > " + granted);
                    }
                }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA);
    }
}
