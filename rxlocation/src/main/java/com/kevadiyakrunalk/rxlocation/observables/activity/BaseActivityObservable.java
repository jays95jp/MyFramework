package com.kevadiyakrunalk.rxlocation.observables.activity;

import android.content.Context;

import com.google.android.gms.location.ActivityRecognition;
import com.kevadiyakrunalk.rxlocation.observables.BaseObservable;

/**
 * The type Base activity observable.
 *
 * @param <T> the type parameter
 */
public abstract class BaseActivityObservable<T> extends BaseObservable<T> {
    /**
     * Instantiates a new Base activity observable.
     *
     * @param ctx the ctx
     */
    protected BaseActivityObservable(Context ctx) {
        super(ctx, ActivityRecognition.API);
    }
}
