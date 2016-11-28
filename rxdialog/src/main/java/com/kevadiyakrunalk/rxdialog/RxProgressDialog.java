package com.kevadiyakrunalk.rxdialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kevadiyakrunalk.rxdialog.R;

import rx.Observable;
import rx.subscriptions.Subscriptions;

public class RxProgressDialog extends Dialog {
    private Context context;

    private int resId = 0;
    private int resColorId = 0;
    private Boolean cancellable;
    private CharSequence mMessage;

    private TextView mMessageView;
    private ProgressBar loading_pb;

    public RxProgressDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
        }
        setContentView(R.layout.progressdialog);

        loading_pb = (ProgressBar) findViewById(R.id.progress_pv);
        loading_pb.setProgressDrawable(new ColorDrawable(ContextCompat.getColor(context, R.color.colorAccent)));
        loading_pb.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), android.graphics.PorterDuff.Mode.MULTIPLY);

        mMessageView = (TextView) findViewById(android.R.id.message);

        if (resId > 0)
            setMessage(resId);
        else if (mMessage != null)
            setMessage(mMessage);
        if(resColorId > 0)
            setProgressColor(resColorId);
        setCancellable(cancellable);
    }

    public void setMessage(CharSequence message) {
        this.mMessage = message;
        if (mMessageView != null) {
            mMessageView.setText(message);
        }
    }

    public void setMessage(int resId) {
        this.resId = resId;
        if (mMessageView != null) {
            mMessageView.setText(resId);
        }
    }

    public void setProgressColor(int resColorId) {
        this.resColorId = resColorId;
        if (loading_pb != null) {
            loading_pb.setProgressDrawable(new ColorDrawable(ContextCompat.getColor(context, resColorId)));
            loading_pb.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, resColorId), android.graphics.PorterDuff.Mode.MULTIPLY);
        }
    }

    public void setCancellable(Boolean cancellable) {
        this.cancellable = cancellable;
        if(cancellable != null)
            super.setCancelable(cancellable);
    }

    public static class Builder {
        private Context context;
        private int messageRes = 0;
        private int colorRes = 0;
        private String message;
        private Boolean cancellable;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * Message builder.
         *
         * @param message the message
         * @return the builder
         */
        public Builder message(String message) {
            this.message = message;
            return this;
        }

        /**
         * Message builder.
         *
         * @param messageRes the message res
         * @return the builder
         */
        public Builder message(@StringRes Integer messageRes) {
            this.messageRes = messageRes;
            return this;
        }

        public Builder progressColor(@StringRes Integer colorRes) {
            this.colorRes = colorRes;
            return this;
        }

        public Builder cancellable(Boolean cancellable) {
            this.cancellable = cancellable;
            return this;
        }

        public RxProgressDialog create() {
            return new RxProgressDialog(context);
        }

        public <T> Observable<T> toObservable(final Observable<T> source) {
            Observable.OnSubscribe<T> wrappedSubscription = subscriber -> {
                RxProgressDialog dialog = create();
                dialog.setMessage(messageRes);
                dialog.setMessage(message);
                dialog.setCancellable(cancellable);
                dialog.setProgressColor(colorRes);
                dialog.setOnCancelListener(dialog1 -> subscriber.unsubscribe());

                subscriber.add(Subscriptions.create(dialog::dismiss));
                source.subscribe(subscriber);
                dialog.show();
            };
            return Observable.create(wrappedSubscription);
        }
    }
}