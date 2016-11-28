package com.kevadiyakrunalk.rxlocation.observables.geofence;

import android.app.PendingIntent;
import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.kevadiyakrunalk.rxlocation.observables.StatusException;

import rx.Observer;

/**
 * The type Remove geofence by pending intent observable.
 */
class RemoveGeofenceByPendingIntentObservable extends RemoveGeofenceObservable<Status> {
    private final PendingIntent pendingIntent;

    /**
     * Instantiates a new Remove geofence by pending intent observable.
     *
     * @param ctx           the ctx
     * @param pendingIntent the pending intent
     */
    RemoveGeofenceByPendingIntentObservable(Context ctx, PendingIntent pendingIntent) {
        super(ctx);
        this.pendingIntent = pendingIntent;
    }

    @Override
    protected void removeGeofences(GoogleApiClient locationClient, final Observer<? super Status> observer) {
        LocationServices.GeofencingApi.removeGeofences(locationClient, pendingIntent)
                .setResultCallback(status -> {
                    if (status.isSuccess()) {
                        observer.onNext(status);
                        observer.onCompleted();
                    } else {
                        observer.onError(new StatusException(status));
                    }
                });
    }
}
