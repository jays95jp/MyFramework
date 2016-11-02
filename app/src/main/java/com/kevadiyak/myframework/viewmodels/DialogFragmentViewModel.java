package com.kevadiyak.myframework.viewmodels;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.kevadiyak.commonutils.common.Logs;
import com.kevadiyak.mvvmarchitecture.common.BaseViewModel;
import com.kevadiyak.mvvmarchitecture.lifecycle.FragmentEvent;
import com.kevadiyak.myframework.R;
import com.kevadiyak.myframework.fragments.DialogFragment;
import com.kevadiyak.rxdialog.DialogType;
import com.kevadiyak.rxdialog.RxAlertDialog;
import com.kevadiyak.rxdialog.RxProgressDialog;
import com.kevadiyak.rxdialog.RxPromptDialog;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.internal.util.SubscriptionList;

public class DialogFragmentViewModel extends BaseViewModel {
    private Context context;
    private Logs logs;
    private DialogFragment dialogFragment;
    private SubscriptionList subscriptionList;

    public DialogFragmentViewModel(Context context, DialogFragment dialogFragment, Logs logs) {
        this.context = context;
        this.logs = logs;
        this.dialogFragment = dialogFragment;
        subscriptionList = new SubscriptionList();
    }

    public void onDestroy() {
        subscriptionList.unsubscribe();
        super.onDestroy();
    }

    public void onPromptInfo(View view) {
        subscriptionList.add(
                new RxPromptDialog.Builder(context)
                        .title(R.string.dialog_title)
                        .message(R.string.dialog_message)
                        .singleButton(R.string.dialog_cancel)
                        .cancellable(Boolean.TRUE)
                        .type(DialogType.INFO)
                        .canceledOnTouchOutside(Boolean.FALSE)
                        .setDefaultAnimation(true)
                        .toObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer which) {
                                logs.error("Alert", "Which->" + which);
                            }
                        }));
    }

    public void onPromptHelp(View view) {
        subscriptionList.add(
                new RxPromptDialog.Builder(context)
                        .title(R.string.dialog_title)
                        .message(R.string.dialog_message)
                        .singleButton(R.string.dialog_cancel)
                        .cancellable(Boolean.TRUE)
                        .type(DialogType.HELP)
                        .canceledOnTouchOutside(Boolean.FALSE)
                        .setDefaultAnimation(false)
                        .toObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer which) {
                                logs.error("Alert", "Which->" + which);
                            }
                        }));
    }

    public void onPromptSuccess(View view) {
        subscriptionList.add(
                new RxPromptDialog.Builder(context)
                        .title(context.getString(R.string.dialog_title))
                        .message(context.getString(R.string.dialog_message))
                        .doubleButton(context.getString(R.string.dialog_ok), context.getString(R.string.dialog_cancel))
                        .cancellable(Boolean.FALSE)
                        .type(DialogType.SUCCESS)
                        .canceledOnTouchOutside(Boolean.FALSE)
                        .setDefaultAnimation(false)
                        .toObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer which) {
                                logs.error("Alert", "Which->" + which);
                            }
                        }));
    }

    public void onPromptWrong(View view) {
        subscriptionList.add(
                new RxPromptDialog.Builder(context)
                        .title(context.getString(R.string.dialog_title))
                        .message(context.getString(R.string.dialog_message))
                        .doubleButton(context.getString(R.string.dialog_ok), context.getString(R.string.dialog_cancel))
                        .cancellable(Boolean.FALSE)
                        .type(DialogType.WRONG)
                        .canceledOnTouchOutside(Boolean.FALSE)
                        .setDefaultAnimation(false)
                        .toObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer which) {
                                logs.error("Alert", "Which->" + which);
                            }
                        }));
    }

    public void onAlertMsg(View view) {
        subscriptionList.add(
                new RxAlertDialog.Builder(context)
                        .title(R.string.dialog_title)
                        .message(R.string.dialog_message)
                        .singleButton(R.string.dialog_cancel)
                        .cancellable(Boolean.FALSE)
                        .canceledOnTouchOutside(Boolean.FALSE)
                        .setDefaultAnimation(true)
                        .toObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer which) {
                                logs.error("Alert", "Which->" + which);
                            }
                        }));
    }

    public void onAlertImage(View view) {
        subscriptionList.add(
                new RxAlertDialog.Builder(context)
                        .title(R.string.dialog_title)
                        .image(R.drawable.sample_img)
                        .singleButton(R.string.dialog_cancel)
                        .cancellable(Boolean.FALSE)
                        .canceledOnTouchOutside(Boolean.FALSE)
                        .setDefaultAnimation(true)
                        .toObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer which) {
                                logs.error("Alert", "Which->" + which);
                            }
                        }));
    }

    public void onAlertMsgImage(View view) {
        subscriptionList.add(
                new RxAlertDialog.Builder(context)
                        .title(context.getString(R.string.dialog_title))
                        .message(context.getString(R.string.dialog_message))
                        .image(ContextCompat.getDrawable(context, R.drawable.sample_img))
                        .doubleButton(context.getString(R.string.dialog_ok), context.getString(R.string.dialog_cancel))
                        .cancellable(Boolean.TRUE)
                        .canceledOnTouchOutside(Boolean.FALSE)
                        .setDefaultAnimation(false)
                        .toObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer which) {
                                logs.error("Alert", "Which->" + which);
                            }
                        }));
    }

    public void onAlertView(View view) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null);
        subscriptionList.add(
                new RxAlertDialog.Builder(context)
                        .title(context.getString(R.string.dialog_title))
                        .view(view1)
                        .singleButton(context.getString(R.string.dialog_cancel))
                        .cancellable(Boolean.TRUE)
                        .canceledOnTouchOutside(Boolean.FALSE)
                        .setDefaultAnimation(false)
                        .toObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer which) {
                                logs.error("Alert", "Which->" + which);
                            }
                        }));
    }

    public void onAlertViewRes(View view) {
        subscriptionList.add(
                new RxAlertDialog.Builder(context)
                        .title(R.string.dialog_title)
                        .view(R.layout.custom_dialog)
                        .doubleButton(R.string.dialog_ok, R.string.dialog_cancel)
                        .cancellable(Boolean.TRUE)
                        .canceledOnTouchOutside(Boolean.TRUE)
                        .setDefaultAnimation(false)
                        .toObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer which) {
                                logs.error("Alert", "Which->" + which);
                            }
                        }));
    }

    public void onProgress(View view) {

        Observable<String> zipObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        subscriber.onNext("Dismiss dialog");
                        subscriber.unsubscribe();
                    }
                }, 5000);
            }
        });

        new RxProgressDialog.Builder(context)
                .message("Please Wait…")
                .cancellable(false)
                .toObservable(zipObservable)
                .compose(dialogFragment.<String>bindUntilEvent(FragmentEvent.PAUSE))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        logs.error("RESULT", e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        logs.error("RESULT", s);
                    }
                });
    }
}
