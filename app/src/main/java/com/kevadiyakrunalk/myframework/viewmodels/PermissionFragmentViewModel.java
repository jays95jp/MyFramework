package com.kevadiyakrunalk.myframework.viewmodels;

import android.Manifest;
import android.content.Context;
import android.databinding.Bindable;
import android.text.TextUtils;
import android.view.View;

import com.kevadiyakrunalk.commonutils.common.Logs;
import com.kevadiyakrunalk.mvvmarchitecture.common.BaseViewModel;
import com.kevadiyakrunalk.myframework.BR;
import com.kevadiyakrunalk.rxpermissions.PermissionResult;
import com.kevadiyakrunalk.rxpermissions.RxPermissions;

public class PermissionFragmentViewModel extends BaseViewModel {
    private Context context;
    private Logs logs;
    private String message;
    private String msg;

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
        msg = "";
        RxPermissions.getInstance(context)
            .checkMPermission(new PermissionResult() {
                @Override
                public void onPermissionResult(String permission, boolean granted) {
                    msg += ("Permission -> " + permission + ", granted - > " + granted + "\n");
                    setMessage(msg);
                    logs.error("Single Permission", msg);
                }
            }, Manifest.permission.CAMERA);
    }

    public void onMultiple(View view) {
        msg = "";
        RxPermissions.getInstance(context)
            .checkMPermission(new PermissionResult() {
                @Override
                public void onPermissionResult(String permission, boolean granted) {
                    msg += ("Permission -> " + permission + ", granted - > " + granted + "\n");
                    setMessage(msg);
                    logs.error("Multiple Permission", msg);
                }
            }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public void onEach(View view) {
        msg = "";
        RxPermissions.getInstance(context)
            .checkMEachPermission(new PermissionResult() {
                @Override
                public void onPermissionResult(String permission, boolean granted) {
                    msg += ("Permission -> " + permission + ", granted - > " + granted + "\n");
                    setMessage(msg);
                    logs.error("Each Permission", msg);
                }
            }, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    public void onEnsure(View view) {
        msg = "";
        RxPermissions.getInstance(context)
                .checkMEnsurePermission(new PermissionResult() {
                    @Override
                    public void onPermissionResult(String permission, boolean granted) {
                        msg += ("Permission -> " + permission + ", granted - > " + granted + "\n");
                        setMessage(msg);
                        logs.error("Ensure Permission", msg);
                    }
                }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA);
    }

    public void onEnsureEach(View view) {
        msg = "";
        RxPermissions.getInstance(context)
                .checkMEnsureEachPermission(new PermissionResult() {
                    @Override
                    public void onPermissionResult(String permission, boolean granted) {
                        msg += ("Permission -> " + permission + ", granted - > " + granted + "\n");
                        setMessage(msg);
                        logs.error("Ensure Each Permission", msg);
                    }
                }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA);
    }
}
