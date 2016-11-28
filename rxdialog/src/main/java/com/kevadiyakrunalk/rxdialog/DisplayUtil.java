package com.kevadiyakrunalk.rxdialog;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

/**
 * The type Display util.
 */
public class DisplayUtil {
    private Context context;
    private static DisplayUtil sSingleton;

    private DisplayUtil(Context ctx) {
        context = ctx;
    }

    /**
     * Gets instance.
     *
     * @param ctx the ctx
     * @return the instance
     */
    public static DisplayUtil getInstance(Context ctx) {
        if (sSingleton == null) {
            synchronized (DisplayUtil.class) {
                sSingleton = new DisplayUtil(ctx);
            }
        }
        return sSingleton;
    }

    /**
     * Dp 2 px int.
     *
     * @param dpValue the dp value
     * @return the int
     */
    public int dp2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * Px 2 dp int.
     *
     * @param pxValue the px value
     * @return the int
     */
    public int px2dp(float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * Gets screen size.
     *
     * @return the screen size
     */
    public Point getScreenSize() {
        Point point = new Point();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(point);
        return point;
    }
}