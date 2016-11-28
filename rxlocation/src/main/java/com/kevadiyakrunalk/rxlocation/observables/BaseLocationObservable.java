package com.kevadiyakrunalk.rxlocation.observables;

import android.content.Context;

import com.google.android.gms.location.LocationServices;

/**
 * The type Base location observable.
 *
 * @param <T> the type parameter
 */
public abstract class BaseLocationObservable<T> extends BaseObservable<T> {
    /**
     * Instantiates a new Base location observable.
     *
     * @param ctx the ctx
     */
    protected BaseLocationObservable(Context ctx) {
        super(ctx, LocationServices.API);
    }
}
