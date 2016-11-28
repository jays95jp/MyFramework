package com.kevadiyakrunalk.rxlocation.observables.geofence;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.kevadiyakrunalk.rxlocation.observables.StatusException;

import java.util.List;

import rx.Observer;

/**
 * The type Remove geofence request ids observable.
 */
class RemoveGeofenceRequestIdsObservable extends RemoveGeofenceObservable<Status> {
    private final List<String> geofenceRequestIds;

    /**
     * Instantiates a new Remove geofence request ids observable.
     *
     * @param ctx                the ctx
     * @param geofenceRequestIds the geofence request ids
     */
    RemoveGeofenceRequestIdsObservable(Context ctx, List<String> geofenceRequestIds) {
        super(ctx);
        this.geofenceRequestIds = geofenceRequestIds;
    }

    @Override
    protected void removeGeofences(GoogleApiClient locationClient, final Observer<? super Status> observer) {
        LocationServices.GeofencingApi.removeGeofences(locationClient, geofenceRequestIds)
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
