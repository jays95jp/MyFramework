package com.kevadiyakrunalk.rxdialog;

import android.app.Dialog;

/**
 * Created by admin on 10/15/2016.
 */
public interface ButtonListener {
    /**
     * The constant BUTTON_POSITIVE.
     */
    public static final int BUTTON_POSITIVE = -1;
    /**
     * The constant BUTTON_NEGATIVE.
     */
    public static final int BUTTON_NEGATIVE = -2;
    /**
     * The constant BUTTON_NEUTRAL.
     */
    public static final int BUTTON_NEUTRAL = -3;
    /**
     * The constant DIALOG_CANCEL.
     */
    public static final int DIALOG_CANCEL = -4;
    /**
     * The constant DIALOG_DISMISS.
     */
    public static final int DIALOG_DISMISS = -5;
    /**
     * The constant DIALOG_SHOW.
     */
    public static final int DIALOG_SHOW = -6;

    /**
     * On click.
     *
     * @param dialog the dialog
     * @param which  the which
     */
    void onClick(Dialog dialog, int which);
}
