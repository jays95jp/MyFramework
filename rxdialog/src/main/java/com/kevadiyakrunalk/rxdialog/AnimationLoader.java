package com.kevadiyakrunalk.rxdialog;

import android.content.Context;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;

import com.kevadiyakrunalk.rxdialog.R;

/**
 * The type Animation loader.
 */
public class AnimationLoader {
    private Context context;
    private static AnimationLoader sSingleton;

    private AnimationLoader(Context ctx) {
        context = ctx;
    }

    /**
     * Gets instance.
     *
     * @param ctx the ctx
     * @return the instance
     */
    public static AnimationLoader getInstance(Context ctx) {
        if (sSingleton == null) {
            synchronized (AnimationLoader.class) {
                sSingleton = new AnimationLoader(ctx);
            }
        }
        return sSingleton;
    }

    /**
     * Gets in animation.
     *
     * @return the in animation
     */
    public AnimationSet getInAnimation() {
        AnimationSet in = new AnimationSet(context, null);
        in.addAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_left_in));
        return in;
    }

    /**
     * Gets out animation.
     *
     * @return the out animation
     */
    public AnimationSet getOutAnimation() {
        AnimationSet out = new AnimationSet(context, null);
        out.addAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_left_out));
        return out;
    }
}
