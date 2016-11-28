package com.kevadiyakrunalk.rxlocation.observables.geofence;

import android.app.PendingIntent;
import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.kevadiyakrunalk.rxlocation.observables.BaseLocationObservable;

import java.util.List;

import rx.Observable;
import rx.Observer;

/**
 * The type Remove geofence observable.
 *
 * @param <T> the type parameter
 */
public abstract class RemoveGeofenceObservable<T> extends BaseLocationObservable<T> {

    /**
     * Create observable observable.
     *
     * @param ctx           the ctx
     * @param pendingIntent the pending intent
     * @return the observable
     */
    public static Observable<Status> createObservable(
            Context ctx, PendingIntent pendingIntent) {
        return Observable.create(new RemoveGeofenceByPendingIntentObservable(ctx, pendingIntent));
    }

    /**
     * Create observable observable.
     *
     * @param ctx        the ctx
     * @param requestIds the request ids
     * @return the observable
     */
    public static Observable<Status> createObservable(
            Context ctx, List<String> requestIds) {
        return Observable.create(new RemoveGeofenceRequestIdsObservable(ctx, requestIds));
    }

    /**
     * Instantiates a new Remove geofence observable.
     *
     * @param ctx the ctx
     */
    protected RemoveGeofenceObservable(Context ctx) {
        super(ctx);
    }

    @Override
    protected void onGoogleApiClientReady(GoogleApiClient apiClient, final Observer<? super T> observer) {
        removeGeofences(apiClient, observer);
    }

    /**
     * Remove geofences.
     *
     * @param locationClient the location client
     * @param observer       the observer
     */
    protected abstract void removeGeofences(GoogleApiClient locationClient, Observer<? super T> observer);
}
