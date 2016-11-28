package com.kevadiyakrunalk.rxdialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationSet;

import com.kevadiyakrunalk.rxdialog.R;

/**
 * Created by admin on 10/14/2016.
 */
public class DialogsController {
    @Nullable
    private String title;
    @Nullable
    @StringRes
    private Integer titleRes;
    @Nullable
    private String message;
    @Nullable
    @StringRes
    private Integer messageRes;
    @Nullable
    private String positiveButton;
    @Nullable
    @StringRes
    private Integer positiveButtonRes;
    @Nullable
    private String negativeButton;
    @Nullable
    @StringRes
    private Integer negativeButtonRes;
    @Nullable
    private String neutralButton;
    @Nullable
    @StringRes
    private Integer neutralButtonRes;
    @Nullable
    private Boolean cancellable;
    @Nullable
    private Boolean canceledOnTouchOutside;
    @Nullable
    private View view;
    @Nullable
    @LayoutRes
    private Integer viewRes;
    @Nullable
    @DrawableRes
    private Integer imageRes;
    @Nullable
    private Drawable imageDrawable;
    @Nullable
    private Bitmap imageBitmap;
    @Nullable
    private Boolean isAnimation;
    @Nullable
    private AnimationSet animIn;
    @Nullable
    private AnimationSet animOut;
    @Nullable
    private ButtonListener listener;
    @Nullable
    private DialogType dialogType;

    /**
     * Instantiates a new Dialogs controller.
     *
     * @param context the context
     */
    public DialogsController(Context context) {
        animIn = AnimationLoader.getInstance(context).getInAnimation();
        animOut = AnimationLoader.getInstance(context).getOutAnimation();
    }

    /**
     * Title.
     *
     * @param title the title
     */
    @NonNull
    public void title(@Nullable String title) {
        this.title = title;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    @Nullable
    protected String getTitle() {
        return title;
    }

    /**
     * Title.
     *
     * @param titleRes the title res
     */
    @NonNull
    public void title(@StringRes @Nullable Integer titleRes) {
        this.titleRes = titleRes;
    }

    /**
     * Gets title res.
     *
     * @return the title res
     */
    @Nullable
    @StringRes
    public Integer getTitleRes() {
        return titleRes;
    }

    /**
     * Message.
     *
     * @param message the message
     */
    @NonNull
    public void message(@Nullable String message) {
        this.message = message;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    @Nullable
    public String getMessage() {
        return message;
    }

    /**
     * Message.
     *
     * @param messageRes the message res
     */
    @NonNull
    public void message(@StringRes @Nullable Integer messageRes) {
        this.messageRes = messageRes;
    }

    /**
     * Gets message res.
     *
     * @return the message res
     */
    @Nullable
    @StringRes
    public Integer getMessageRes() {
        return messageRes;
    }

    /**
     * Double button.
     *
     * @param positiveButton the positive button
     * @param negativeButton the negative button
     */
    @NonNull
    public void doubleButton(@Nullable String positiveButton, @Nullable String negativeButton) {
        this.positiveButton = positiveButton;
        this.negativeButton = negativeButton;
    }

    /**
     * Gets positive button.
     *
     * @return the positive button
     */
    @Nullable
    protected String getPositiveButton() {
        return positiveButton;
    }

    /**
     * Gets negative button.
     *
     * @return the negative button
     */
    @Nullable
    protected String getNegativeButton() {
        return negativeButton;
    }

    /**
     * Double button.
     *
     * @param positiveButtonRes the positive button res
     * @param negativeButtonRes the negative button res
     */
    @NonNull
    public void doubleButton(@StringRes @Nullable Integer positiveButtonRes, @StringRes @Nullable Integer negativeButtonRes) {
        this.positiveButtonRes = positiveButtonRes;
        this.negativeButtonRes = negativeButtonRes;
    }

    /**
     * Gets positive button res.
     *
     * @return the positive button res
     */
    @Nullable
    @StringRes
    public Integer getPositiveButtonRes() {
        return positiveButtonRes;
    }

    /**
     * Gets negative button res.
     *
     * @return the negative button res
     */
    @Nullable
    @StringRes
    public Integer getNegativeButtonRes() {
        return negativeButtonRes;
    }

    /**
     * Single button.
     *
     * @param neutralButton the neutral button
     */
    @NonNull
    public void singleButton(@Nullable String neutralButton) {
        this.neutralButton = neutralButton;
    }

    /**
     * Gets neutral button.
     *
     * @return the neutral button
     */
    @Nullable
    public String getNeutralButton() {
        return neutralButton;
    }

    /**
     * Single button.
     *
     * @param neutralButtonRes the neutral button res
     */
    @NonNull
    public void singleButton(@StringRes @Nullable Integer neutralButtonRes) {
        this.neutralButtonRes = neutralButtonRes;
    }

    /**
     * Gets neutral button res.
     *
     * @return the neutral button res
     */
    @Nullable
    @StringRes
    public Integer getNeutralButtonRes() {
        return neutralButtonRes;
    }

    /**
     * Cancellable.
     *
     * @param cancellable the cancellable
     */
    @NonNull
    public void cancellable(@Nullable Boolean cancellable) {
        this.cancellable = cancellable;
    }

    /**
     * Gets cancellable.
     *
     * @return the cancellable
     */
    @Nullable
    public Boolean getCancellable() {
        return cancellable;
    }

    /**
     * Canceled on touch outside.
     *
     * @param canceledOnTouchOutside the canceled on touch outside
     */
    @NonNull
    public void canceledOnTouchOutside(@Nullable Boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
    }

    /**
     * Gets canceled on touch outside.
     *
     * @return the canceled on touch outside
     */
    @Nullable
    public Boolean getCanceledOnTouchOutside() {
        return canceledOnTouchOutside;
    }

    /**
     * View.
     *
     * @param viewRes the view res
     */
    @NonNull
    public void view(@LayoutRes @Nullable Integer viewRes) {
        this.viewRes = viewRes;
    }

    /**
     * Gets view res.
     *
     * @return the view res
     */
    @Nullable
    @LayoutRes
    public Integer getViewRes() {
        return viewRes;
    }

    /**
     * View.
     *
     * @param view the view
     */
    @NonNull
    public void view(@Nullable View view) {
        this.view = view;
    }

    /**
     * Gets view.
     *
     * @return the view
     */
    @Nullable
    public View getView() {
        return view;
    }

    /**
     * Image.
     *
     * @param imageRes the image res
     */
    @NonNull
    public void image(@DrawableRes @Nullable Integer imageRes) {
        this.imageRes = imageRes;
    }

    /**
     * Gets image res.
     *
     * @return the image res
     */
    @Nullable
    @DrawableRes
    public Integer getImageRes() {
        return imageRes;
    }

    /**
     * Image.
     *
     * @param imageDrawable the image drawable
     */
    @NonNull
    public void image(@Nullable Drawable imageDrawable) {
        this.imageDrawable = imageDrawable;
    }

    /**
     * Gets image drawable.
     *
     * @return the image drawable
     */
    @Nullable
    public Drawable getImageDrawable() {
        return imageDrawable;
    }

    /**
     * Image.
     *
     * @param imageBitmap the image bitmap
     */
    @NonNull
    public void image(@Nullable Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    /**
     * Gets image bitmap.
     *
     * @return the image bitmap
     */
    @Nullable
    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    /**
     * Sets default animation.
     *
     * @param isAnimation the is animation
     */
    @NonNull
    public void setDefaultAnimation(@Nullable Boolean isAnimation) {
        this.isAnimation = isAnimation;
    }

    /**
     * Gets default animation.
     *
     * @return the default animation
     */
    @Nullable
    public Boolean getDefaultAnimation() {
        return isAnimation;
    }

    /**
     * Sets in animation.
     *
     * @param animIn the anim in
     */
    @NonNull
    public void setInAnimation(@Nullable AnimationSet animIn) {
        this.animIn = animIn;
    }

    /**
     * Gets in animation.
     *
     * @return the in animation
     */
    @Nullable
    public AnimationSet getInAnimation() {
        return animIn;
    }

    /**
     * Sets out animation.
     *
     * @param animOut the anim out
     */
    @NonNull
    public void setOutAnimation(@Nullable AnimationSet animOut) {
        this.animOut = animOut;
    }

    /**
     * Gets out animation.
     *
     * @return the out animation
     */
    @Nullable
    public AnimationSet getOutAnimation() {
        return animOut;
    }

    /**
     * Sets button listener.
     *
     * @param listener the listener
     */
    @NonNull
    public void setButtonListener(ButtonListener listener) {
        this.listener = listener;
    }

    /**
     * Gets button listener.
     *
     * @return the button listener
     */
    @NonNull
    public ButtonListener getButtonListener() {
        return listener;
    }

    /**
     * Sets dialog type.
     *
     * @param dialogType the dialog type
     */
    @NonNull
    public void setDialogType(DialogType dialogType) {
        this.dialogType = dialogType;
    }

    /**
     * Gets dialog type.
     *
     * @return the dialog type
     */
    @NonNull
    public DialogType getDialogType() {
        return dialogType;
    }

    /**
     * Is single button boolean.
     *
     * @return the boolean
     */
    public boolean isSingleButton() {
        return (neutralButtonRes != null && neutralButtonRes != 0) || (neutralButton != null && !TextUtils.isEmpty(neutralButton));
    }

    /**
     * Is double button boolean.
     *
     * @return the boolean
     */
    public boolean isDoubleButton() {
        return (positiveButtonRes != null && positiveButtonRes != 0 && negativeButtonRes != null && negativeButtonRes != 0) || (positiveButton != null && !TextUtils.isEmpty(positiveButton) && negativeButton != null && !TextUtils.isEmpty(negativeButton));
    }

    /**
     * Is text mode boolean.
     *
     * @return the boolean
     */
    public boolean isTextMode() {
        return (messageRes != null && messageRes != 0) || (message != null && !TextUtils.isEmpty(message));
    }

    /**
     * Is view mode boolean.
     *
     * @return the boolean
     */
    public boolean isViewMode() {
        return (viewRes != null && viewRes != 0) || (view != null);
    }

    /**
     * Is image mode boolean.
     *
     * @return the boolean
     */
    public boolean isImageMode() {
        return (imageRes != null && imageRes != 0) || (imageDrawable != null) || (imageBitmap != null);
    }

    /**
     * Gets logo res id.
     *
     * @return the logo res id
     */
    public int getLogoResId() {
        if (DialogType.INFO == dialogType)
            return R.drawable.ic_info;
        else if (DialogType.HELP == dialogType)
            return R.drawable.ic_help;
        else if (DialogType.WRONG == dialogType)
            return R.drawable.ic_wrong;
        else if (DialogType.SUCCESS == dialogType)
            return R.drawable.ic_success;
        else if (DialogType.WARNING == dialogType)
            return R.drawable.icon_warning;
        else
            return R.drawable.ic_info;
    }

    /**
     * Gets color res id.
     *
     * @return the color res id
     */
    public int getColorResId() {
        if (DialogType.INFO == dialogType)
            return R.color.color_type_info;
        else if (DialogType.HELP == dialogType)
            return R.color.color_type_help;
        else if (DialogType.WRONG == dialogType)
            return R.color.color_type_wrong;
        else if (DialogType.SUCCESS == dialogType)
            return R.color.color_type_success;
        else if (DialogType.WARNING == dialogType)
            return R.color.color_type_warning;
        else
            return R.color.color_type_info;
    }

    /**
     * Gets sel btn.
     *
     * @return the sel btn
     */
    public int getSelBtn() {
        if (DialogType.INFO == dialogType)
            return R.drawable.sel_btn_info;
        else if (DialogType.HELP == dialogType)
            return R.drawable.sel_btn_help;
        else if (DialogType.WRONG == dialogType)
            return R.drawable.sel_btn_wrong;
        else if (DialogType.SUCCESS == dialogType)
            return R.drawable.sel_btn_success;
        else if (DialogType.WARNING == dialogType)
            return R.drawable.sel_btn_warning;
        else
            return R.drawable.sel_btn_info;
    }
}
