package com.kevadiyakrunalk.rxfilepicker.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * A RelativeLayout that will always be square -- same width and height,
 * where the height is based off the width.
 */
public class SquareRelativeLayout extends RelativeLayout {

    /**
     * Instantiates a new Square relative layout.
     *
     * @param context the context
     */
    public SquareRelativeLayout(Context context) {
        super(context);
    }

    /**
     * Instantiates a new Square relative layout.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public SquareRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Instantiates a new Square relative layout.
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     */
    public SquareRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Instantiates a new Square relative layout.
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     * @param defStyleRes  the def style res
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SquareRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Set getBitmap square layout.
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

}