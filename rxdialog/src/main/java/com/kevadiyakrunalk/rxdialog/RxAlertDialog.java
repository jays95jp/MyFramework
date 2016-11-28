package com.kevadiyakrunalk.rxdialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kevadiyakrunalk.rxdialog.R;

import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.Subscriptions;

/**
 * The type Alert dialog.
 */
public class RxAlertDialog extends Dialog {
    private View mMainView;
    private ImageView mContentIv;
    private RelativeLayout mContentRl;
    private View mBtnGroupView, mBtnSingleView, mDialogView;
    private TextView mTitleTv, mContentTv, mNeutralBtn, mPositiveBtn, mNegativeBtn;

    private boolean isDismiss = false;
    private DialogsController data;

    /**
     * Instantiates a new Rx alert dialog.
     *
     * @param context the context
     * @param data    the data
     */
    public RxAlertDialog(Context context, DialogsController data) {
        this(context, R.style.color_dialog, data);
    }

    /**
     * Instantiates a new Rx alert dialog.
     *
     * @param context the context
     * @param theme   the theme
     * @param data    the data
     */
    public RxAlertDialog(Context context, int theme, DialogsController data) {
        super(context, theme);
        this.data = data;
        initAnimListener();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View contentView = View.inflate(getContext(), R.layout.alertdialog, null);
        setContentView(contentView);

        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        mMainView = contentView.findViewById(R.id.loading);
        mTitleTv = (TextView) contentView.findViewById(R.id.tvTitle);
        mContentTv = (TextView) contentView.findViewById(R.id.tvContent);
        mContentIv = (ImageView) contentView.findViewById(R.id.ivContent);
        mContentRl = (RelativeLayout) contentView.findViewById(R.id.rlContent);

        mNeutralBtn = (TextView) contentView.findViewById(R.id.btnNeutral);
        mPositiveBtn = (TextView) contentView.findViewById(R.id.btnPositive);
        mNegativeBtn = (TextView) contentView.findViewById(R.id.btnNegative);

        mBtnGroupView = contentView.findViewById(R.id.llBtnGroup);
        mBtnSingleView = contentView.findViewById(R.id.llBtnSingle);

        refreshView();
    }

    @Override
    protected void onStart() {
        if (data.getDefaultAnimation() != null)
            startWithAnimation(data.getDefaultAnimation());
        else
            super.onStart();
    }

    @Override
    public void dismiss() {
        if (data.getDefaultAnimation() != null && !isDismiss)
            dismissWithAnimation(data.getDefaultAnimation());
        else
            super.dismiss();
    }

    private void refreshView() {
        if (data.getTitleRes() != null) {
            setTitle(data.getTitleRes());
            mTitleTv.setText(data.getTitleRes());
        } else if (data.getTitle() != null) {
            setTitle(data.getTitle());
            mTitleTv.setText(data.getTitle());
        }

        if (data.getMessageRes() != null) {
            mContentTv.setText(data.getMessageRes());
        } else if (data.getMessage() != null) {
            mContentTv.setText(data.getMessage());
        }

        if (data.getViewRes() != null) {
            mContentRl.removeAllViews();
            mContentRl.addView(LayoutInflater.from(getContext()).inflate(data.getViewRes(), null), new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else if (data.getView() != null) {
            mContentRl.removeAllViews();
            mContentRl.addView(data.getView(), new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        if (data.getImageRes() != null) {
            mContentIv.setBackgroundResource(data.getImageRes());
        } else if (data.getImageDrawable() != null) {
            mContentIv.setBackground(data.getImageDrawable());
        } else if (data.getImageBitmap() != null) {
            mContentIv.setImageBitmap(data.getImageBitmap());
        }

        if (data.isSingleButton()) {
            if (data.getNeutralButtonRes() != null)
                mNeutralBtn.setText(data.getNeutralButtonRes());
            else if (data.getNeutralButton() != null)
                mNeutralBtn.setText(data.getNeutralButton());

            mBtnGroupView.setVisibility(View.GONE);
            mBtnSingleView.setVisibility(View.VISIBLE);
        } else if (data.isDoubleButton()) {
            if (data.getPositiveButtonRes() != null)
                mPositiveBtn.setText(data.getPositiveButtonRes());
            else if (data.getPositiveButton() != null)
                mPositiveBtn.setText(data.getPositiveButton());

            if (data.getNegativeButtonRes() != null)
                mNegativeBtn.setText(data.getNegativeButtonRes());
            else if (data.getNegativeButton() != null)
                mNegativeBtn.setText(data.getNegativeButton());

            mBtnSingleView.setVisibility(View.GONE);
            mBtnGroupView.setVisibility(View.VISIBLE);

            //mNegativeBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.sel_def_gray));
            //mPositiveBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.sel_def_gray));
        }

        if (data.isImageMode() && data.isTextMode()) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mContentTv.getLayoutParams();
            params.gravity = Gravity.BOTTOM;
            mContentTv.setLayoutParams(params);
            mContentTv.setBackgroundColor(Color.BLACK);
            mContentTv.getBackground().setAlpha(0x28);
            mContentTv.setVisibility(View.VISIBLE);
            mContentIv.setVisibility(View.VISIBLE);
            mContentRl.setVisibility(View.VISIBLE);
        } else if (data.isTextMode()) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mContentTv.getLayoutParams();
            params.gravity = Gravity.NO_GRAVITY;
            mContentTv.setLayoutParams(params);
            mContentIv.setVisibility(View.GONE);
            mContentRl.setVisibility(View.GONE);
            mContentTv.setVisibility(View.VISIBLE);
        } else if (data.isImageMode()) {
            mContentTv.setVisibility(View.GONE);
            mContentRl.setVisibility(View.GONE);
            mContentIv.setVisibility(View.VISIBLE);
        } else if (data.isViewMode()) {
            mContentIv.setVisibility(View.GONE);
            mContentTv.setVisibility(View.GONE);
            mContentRl.setVisibility(View.VISIBLE);
        }

        if (data.getCancellable() != null)
            setCancelable(data.getCancellable());

        if (data.getCanceledOnTouchOutside() != null)
            setCanceledOnTouchOutside(data.getCanceledOnTouchOutside());

        initListener();
    }

    private void startWithAnimation(boolean showInAnimation) {
        if (showInAnimation && data.getInAnimation() != null) {
            mDialogView.startAnimation(data.getInAnimation());
        } else
            super.onStart();
    }

    private void dismissWithAnimation(boolean showOutAnimation) {
        isDismiss = true;
        if (showOutAnimation && data.getOutAnimation() != null)
            mDialogView.startAnimation(data.getOutAnimation());
        else
            super.dismiss();
    }

    private void initAnimListener() {
        if (data.getOutAnimation() != null)
            data.getOutAnimation().setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    cancel();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
    }

    private void initListener() {
        mNeutralBtn.setOnClickListener(v -> data.getButtonListener().onClick(RxAlertDialog.this, ButtonListener.BUTTON_NEUTRAL));

        mPositiveBtn.setOnClickListener(v -> data.getButtonListener().onClick(RxAlertDialog.this, ButtonListener.BUTTON_POSITIVE));

        mNegativeBtn.setOnClickListener(v -> data.getButtonListener().onClick(RxAlertDialog.this, ButtonListener.BUTTON_NEGATIVE));

        setOnCancelListener(anInterface -> data.getButtonListener().onClick(RxAlertDialog.this, ButtonListener.DIALOG_CANCEL));

        setOnDismissListener(anInterface -> data.getButtonListener().onClick(RxAlertDialog.this, ButtonListener.DIALOG_DISMISS));

        setOnShowListener(anInterface -> data.getButtonListener().onClick(RxAlertDialog.this, ButtonListener.DIALOG_SHOW));

        /*setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface anInterface, int keyCode, KeyEvent event) {
                data.getButtonListener().onKey(keyCode, event);
                return false;
            }
        });*/
    }

    /**
     * The type Builder.
     */
    public static class Builder {
        private int themeResId;
        private Context context;
        private DialogsController controller;

        /**
         * Instantiates a new Builder.
         *
         * @param context the context
         */
        public Builder(Context context) {
            this.context = context;
            themeResId = R.style.color_dialog;
            controller = new DialogsController(context);
        }

        /**
         * Instantiates a new Builder.
         *
         * @param context    the context
         * @param themeResId the theme res id
         */
        public Builder(Context context, int themeResId) {
            this.context = context;
            this.themeResId = themeResId;
            controller = new DialogsController(context);
        }

        /**
         * Title builder.
         *
         * @param title the title
         * @return the builder
         */
        public Builder title(String title) {
            controller.title(title);
            return this;
        }

        /**
         * Title builder.
         *
         * @param titleRes the title res
         * @return the builder
         */
        public Builder title(@StringRes Integer titleRes) {
            controller.title(titleRes);
            return this;
        }

        /**
         * Message builder.
         *
         * @param message the message
         * @return the builder
         */
        public Builder message(String message) {
            controller.message(message);
            return this;
        }

        /**
         * Message builder.
         *
         * @param messageRes the message res
         * @return the builder
         */
        public Builder message(@StringRes Integer messageRes) {
            controller.message(messageRes);
            return this;
        }

        /**
         * Double button builder.
         *
         * @param positiveButton the positive button
         * @param negativeButton the negative button
         * @return the builder
         */
        public Builder doubleButton(String positiveButton, String negativeButton) {
            controller.doubleButton(positiveButton, negativeButton);
            return this;
        }

        /**
         * Double button builder.
         *
         * @param positiveButtonRes the positive button res
         * @param negativeButtonRes the negative button res
         * @return the builder
         */
        public Builder doubleButton(@StringRes Integer positiveButtonRes, @StringRes Integer negativeButtonRes) {
            controller.doubleButton(positiveButtonRes, negativeButtonRes);
            return this;
        }

        /**
         * Single button builder.
         *
         * @param neutralButton the neutral button
         * @return the builder
         */
        public Builder singleButton(String neutralButton) {
            controller.singleButton(neutralButton);
            return this;
        }

        /**
         * Single button builder.
         *
         * @param neutralButtonRes the neutral button res
         * @return the builder
         */
        public Builder singleButton(@StringRes Integer neutralButtonRes) {
            controller.singleButton(neutralButtonRes);
            return this;
        }

        /**
         * Cancellable builder.
         *
         * @param cancellable the cancellable
         * @return the builder
         */
        public Builder cancellable(Boolean cancellable) {
            controller.cancellable(cancellable);
            return this;
        }

        /**
         * Canceled on touch outside builder.
         *
         * @param canceledOnTouchOutside the canceled on touch outside
         * @return the builder
         */
        public Builder canceledOnTouchOutside(Boolean canceledOnTouchOutside) {
            controller.canceledOnTouchOutside(canceledOnTouchOutside);
            return this;
        }

        /**
         * View builder.
         *
         * @param viewRes the view res
         * @return the builder
         */
        public Builder view(@LayoutRes Integer viewRes) {
            controller.view(viewRes);
            return this;
        }

        /**
         * View builder.
         *
         * @param view the view
         * @return the builder
         */
        public Builder view(View view) {
            controller.view(view);
            return this;
        }

        /**
         * Image builder.
         *
         * @param imageRes the image res
         * @return the builder
         */
        public Builder image(@DrawableRes Integer imageRes) {
            controller.image(imageRes);
            return this;
        }

        /**
         * Image builder.
         *
         * @param imageDrawable the image drawable
         * @return the builder
         */
        public Builder image(Drawable imageDrawable) {
            controller.image(imageDrawable);
            return this;
        }

        /**
         * Image builder.
         *
         * @param imageBitmap the image bitmap
         * @return the builder
         */
        public Builder image(Bitmap imageBitmap) {
            controller.image(imageBitmap);
            return this;
        }

        /**
         * Sets default animation.
         *
         * @param isAnimation the is animation
         * @return the default animation
         */
        public Builder setDefaultAnimation(Boolean isAnimation) {
            controller.setDefaultAnimation(isAnimation);
            return this;
        }

        /**
         * Sets in animation.
         *
         * @param animIn the anim in
         * @return the in animation
         */
        public Builder setInAnimation(AnimationSet animIn) {
            controller.setInAnimation(animIn);
            return this;
        }

        /**
         * Sets out animation.
         *
         * @param animOut the anim out
         * @return the out animation
         */
        public Builder setOutAnimation(AnimationSet animOut) {
            controller.setOutAnimation(animOut);
            return this;
        }

        /**
         * Sets button listener.
         *
         * @param listener the listener
         * @return the button listener
         */
        public Builder setButtonListener(ButtonListener listener) {
            return this;
        }

        /**
         * Create rx alert dialog.
         *
         * @return the rx alert dialog
         */
        public RxAlertDialog create() {
            return new RxAlertDialog(context, themeResId, controller);
        }

        /**
         * To observable observable.
         *
         * @return the observable
         */
        public Observable<Integer> toObservable() {
            return Observable.create((Subscriber<? super Integer> subscriber) -> {
                try {
                    new Handler(Looper.getMainLooper()).post(() -> {
                        final RxAlertDialog dialog = create();
                        // cleaning up
                        subscriber.add(Subscriptions.create(dialog::dismiss));
                        dialog.show();
                        controller.setButtonListener((dialog1, which) -> {
                            subscriber.onNext(which);
                            if (which != ButtonListener.DIALOG_SHOW)
                                subscriber.onCompleted();
                        });
                        dialog.refreshView();

                    });
                } catch (Exception e) {
                    subscriber.onCompleted();
                }
            });
        }
    }
}
