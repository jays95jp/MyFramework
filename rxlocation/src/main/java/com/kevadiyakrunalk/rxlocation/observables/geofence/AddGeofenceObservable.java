package com.kevadiyakrunalk.rxlocation.observables.geofence;

import android.app.PendingIntent;
import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.kevadiyakrunalk.rxlocation.observables.BaseLocationObservable;
import com.kevadiyakrunalk.rxlocation.observables.StatusException;

import rx.Observable;
import rx.Observer;

/**
 * The type Add geofence observable.
 */
public class AddGeofenceObservable extends BaseLocationObservable<Status> {
    private Context context;
    private final GeofencingRequest request;
    private final PendingIntent geofenceTransitionPendingIntent;

    /**
     * Create observable observable.
     *
     * @param ctx                             the ctx
     * @param request                         the request
     * @param geofenceTransitionPendingIntent the geofence transition pending intent
     * @return the observable
     */
    public static Observable<Status> createObservable(Context ctx, GeofencingRequest request, PendingIntent geofenceTransitionPendingIntent) {
        return Observable.create(new AddGeofenceObservable(ctx, request, geofenceTransitionPendingIntent));
    }

    private AddGeofenceObservable(Context ctx, GeofencingRequest request, PendingIntent geofenceTransitionPendingIntent) {
        super(ctx);
        context = ctx;
        this.request = request;
        this.geofenceTransitionPendingIntent = geofenceTransitionPendingIntent;
    }

    @Override
    protected void onGoogleApiClientReady(GoogleApiClient apiClient, final Observer<? super Status> observer) {
        LocationServices.GeofencingApi.addGeofences(apiClient, request, geofenceTransitionPendingIntent)
                    .setResultCallback(status -> {
                        if (!status.isSuccess()) {
                            observer.onError(new StatusException(status));
                        } else {
                            observer.onNext(status);
                            observer.onCompleted();
                        }
                    });
    }
}
