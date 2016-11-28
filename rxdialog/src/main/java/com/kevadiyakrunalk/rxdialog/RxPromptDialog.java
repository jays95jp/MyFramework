package com.kevadiyakrunalk.rxdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kevadiyakrunalk.rxdialog.R;

import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.Subscriptions;

/**
 * The type Prompt dialog.
 */
public class RxPromptDialog extends Dialog {
    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int DEFAULT_RADIUS = 6;

    private View mMainView;
    private ImageView logoIv;
    private View mDialogView, llBtnSingle, llBtnGroup;
    private TextView mTitleTv, mContentTv, mNeutralBtn, mPositiveBtn, mNegativeBtn;

    private boolean isDismiss = false;
    private DialogsController data;

    /**
     * Instantiates a new Prompt dialog.
     *
     * @param ctx  the context
     * @param data the data
     */
    public RxPromptDialog(Context ctx, DialogsController data) {
        this(ctx, R.style.color_dialog, data);
    }

    /**
     * Instantiates a new Prompt dialog.
     *
     * @param ctx   the context
     * @param theme the theme
     * @param data  the data
     */
    public RxPromptDialog(Context ctx, int theme, DialogsController data) {
        super(ctx, theme);
        this.data = data;
        initAnimListener();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = View.inflate(getContext(), R.layout.promptdialog, null);
        setContentView(contentView);

        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        mMainView = contentView.findViewById(R.id.loading);
        mTitleTv = (TextView) contentView.findViewById(R.id.tvTitle);
        mContentTv = (TextView) contentView.findViewById(R.id.tvContent);
        mNeutralBtn = (TextView) contentView.findViewById(R.id.btnNeutral);
        mPositiveBtn = (TextView) contentView.findViewById(R.id.btnPositive);
        mNegativeBtn = (TextView) contentView.findViewById(R.id.btnNegative);

        llBtnSingle = findViewById(R.id.llBtnSingle);
        llBtnGroup = findViewById(R.id.llBtnGroup);
        logoIv = (ImageView) contentView.findViewById(R.id.logoIv);

        LinearLayout topLayout = (LinearLayout) contentView.findViewById(R.id.topLayout);
        ImageView triangleIv = new ImageView(getContext());
        triangleIv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.getInstance(getContext()).dp2px(10)));
        triangleIv.setImageBitmap(createTriangel((int) (DisplayUtil.getInstance(getContext()).getScreenSize().x * 0.7), DisplayUtil.getInstance(getContext()).dp2px(10)));
        topLayout.addView(triangleIv);

        int radius = DisplayUtil.getInstance(getContext()).dp2px(DEFAULT_RADIUS);
        float[] outerRadii = new float[]{radius, radius, radius, radius, 0, 0, 0, 0};
        RoundRectShape roundRectShape = new RoundRectShape(outerRadii, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable.getPaint().setColor(ContextCompat.getColor(getContext(), data.getColorResId()));
        LinearLayout llTop = (LinearLayout) findViewById(R.id.llTop);
        llTop.setBackground(shapeDrawable);

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
        logoIv.setBackgroundResource(data.getLogoResId());

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

        int radius = DisplayUtil.getInstance(getContext()).dp2px(DEFAULT_RADIUS);
        float[] outerRadii = new float[]{0, 0, 0, 0, radius, radius, radius, radius};
        RoundRectShape roundRectShape = new RoundRectShape(outerRadii, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
        shapeDrawable.getPaint().setColor(Color.WHITE);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);

        if (data.isSingleButton()) {
            if (data.getNeutralButtonRes() != null)
                mNeutralBtn.setText(data.getNeutralButtonRes());
            else if (data.getNeutralButton() != null)
                mNeutralBtn.setText(data.getNeutralButton());

            mNeutralBtn.setTextColor(createColorStateList(ContextCompat.getColor(getContext(), data.getColorResId()),
                    ContextCompat.getColor(getContext(), R.color.color_dialog_gray)));
            mNeutralBtn.setBackground(ContextCompat.getDrawable(getContext(), data.getSelBtn()));

            llBtnSingle.setBackground(shapeDrawable);
            llBtnSingle.setVisibility(View.VISIBLE);
            llBtnGroup.setVisibility(View.GONE);
        } else if (data.isDoubleButton()) {
            if (data.getPositiveButtonRes() != null)
                mPositiveBtn.setText(data.getPositiveButtonRes());
            else if (data.getPositiveButton() != null)
                mPositiveBtn.setText(data.getPositiveButton());

            if (data.getNegativeButtonRes() != null)
                mNegativeBtn.setText(data.getNegativeButtonRes());
            else if (data.getNegativeButton() != null)
                mNegativeBtn.setText(data.getNegativeButton());

            mPositiveBtn.setTextColor(createColorStateList(ContextCompat.getColor(getContext(), data.getColorResId()),
                    ContextCompat.getColor(getContext(), R.color.color_dialog_gray)));
            mPositiveBtn.setBackground(ContextCompat.getDrawable(getContext(), data.getSelBtn()));

            mNegativeBtn.setTextColor(createColorStateList(ContextCompat.getColor(getContext(), data.getColorResId()),
                    ContextCompat.getColor(getContext(), R.color.color_dialog_gray)));
            mNegativeBtn.setBackground(ContextCompat.getDrawable(getContext(), data.getSelBtn()));

            llBtnGroup.setBackground(shapeDrawable);
            llBtnGroup.setVisibility(View.VISIBLE);
            llBtnSingle.setVisibility(View.GONE);
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
        mNeutralBtn.setOnClickListener(v -> data.getButtonListener().onClick(RxPromptDialog.this, ButtonListener.BUTTON_NEUTRAL));

        mPositiveBtn.setOnClickListener(v -> data.getButtonListener().onClick(RxPromptDialog.this, ButtonListener.BUTTON_POSITIVE));

        mNegativeBtn.setOnClickListener(v -> data.getButtonListener().onClick(RxPromptDialog.this, ButtonListener.BUTTON_NEGATIVE));

        setOnCancelListener(anInterface -> data.getButtonListener().onClick(RxPromptDialog.this, ButtonListener.DIALOG_CANCEL));

        setOnDismissListener(anInterface -> data.getButtonListener().onClick(RxPromptDialog.this, ButtonListener.DIALOG_DISMISS));

        setOnShowListener(anInterface -> data.getButtonListener().onClick(RxPromptDialog.this, ButtonListener.DIALOG_SHOW));

        /*setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface anInterface, int keyCode, KeyEvent event) {
                data.getButtonListener().onKey(keyCode, event);
                return false;
            }
        });*/
    }

    private Bitmap createTriangel(int width, int height) {
        if (width <= 0 || height <= 0) {
            return null;
        }
        return getBitmap(width, height, ContextCompat.getColor(getContext(), data.getColorResId()));
    }

    private Bitmap getBitmap(int width, int height, int backgroundColor) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, BITMAP_CONFIG);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(backgroundColor);
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(width, 0);
        path.lineTo(width / 2, height);
        path.close();

        canvas.drawPath(path, paint);
        return bitmap;
    }

    private ColorStateList createColorStateList(int normal, int pressed) {
        return createColorStateList(normal, pressed, Color.BLACK, Color.BLACK);
    }

    private ColorStateList createColorStateList(int normal, int pressed, int focused, int unable) {
        int[] colors = new int[]{pressed, focused, normal, focused, unable, normal};
        int[][] states = new int[6][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[2] = new int[]{android.R.attr.state_enabled};
        states[3] = new int[]{android.R.attr.state_focused};
        states[4] = new int[]{android.R.attr.state_window_focused};
        states[5] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
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
         * Title rx prompt dialog . builder.
         *
         * @param title the title
         * @return the rx prompt dialog . builder
         */
        public Builder title(String title) {
            controller.title(title);
            return this;
        }

        /**
         * Title rx prompt dialog . builder.
         *
         * @param titleRes the title res
         * @return the rx prompt dialog . builder
         */
        public Builder title(@StringRes Integer titleRes) {
            controller.title(titleRes);
            return this;
        }

        /**
         * Message rx prompt dialog . builder.
         *
         * @param message the message
         * @return the rx prompt dialog . builder
         */
        public Builder message(String message) {
            controller.message(message);
            return this;
        }

        /**
         * Message rx prompt dialog . builder.
         *
         * @param messageRes the message res
         * @return the rx prompt dialog . builder
         */
        public Builder message(@StringRes Integer messageRes) {
            controller.message(messageRes);
            return this;
        }

        /**
         * Type rx prompt dialog . builder.
         *
         * @param dialogType the dialog type
         * @return the rx prompt dialog . builder
         */
        public Builder type(DialogType dialogType) {
            controller.setDialogType(dialogType);
            return this;
        }

        /**
         * Double button rx prompt dialog . builder.
         *
         * @param positiveButton the positive button
         * @param negativeButton the negative button
         * @return the rx prompt dialog . builder
         */
        public Builder doubleButton(String positiveButton, String negativeButton) {
            controller.doubleButton(positiveButton, negativeButton);
            return this;
        }

        /**
         * Double button rx prompt dialog . builder.
         *
         * @param positiveButtonRes the positive button res
         * @param negativeButtonRes the negative button res
         * @return the rx prompt dialog . builder
         */
        public Builder doubleButton(@StringRes Integer positiveButtonRes, @StringRes Integer negativeButtonRes) {
            controller.doubleButton(positiveButtonRes, negativeButtonRes);
            return this;
        }

        /**
         * Single button rx prompt dialog . builder.
         *
         * @param neutralButton the neutral button
         * @return the rx prompt dialog . builder
         */
        public Builder singleButton(String neutralButton) {
            controller.singleButton(neutralButton);
            return this;
        }

        /**
         * Single button rx prompt dialog . builder.
         *
         * @param neutralButtonRes the neutral button res
         * @return the rx prompt dialog . builder
         */
        public Builder singleButton(@StringRes Integer neutralButtonRes) {
            controller.singleButton(neutralButtonRes);
            return this;
        }

        /**
         * Cancellable rx prompt dialog . builder.
         *
         * @param cancellable the cancellable
         * @return the rx prompt dialog . builder
         */
        public Builder cancellable(Boolean cancellable) {
            controller.cancellable(cancellable);
            return this;
        }

        /**
         * Canceled on touch outside rx prompt dialog . builder.
         *
         * @param canceledOnTouchOutside the canceled on touch outside
         * @return the rx prompt dialog . builder
         */
        public Builder canceledOnTouchOutside(Boolean canceledOnTouchOutside) {
            controller.canceledOnTouchOutside(canceledOnTouchOutside);
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
         * Create rx prompt dialog.
         *
         * @return the rx prompt dialog
         */
        public RxPromptDialog create() {
            return new RxPromptDialog(context, themeResId, controller);
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
                        final RxPromptDialog dialog = create();
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
