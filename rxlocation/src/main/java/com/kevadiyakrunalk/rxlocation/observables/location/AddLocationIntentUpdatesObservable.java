package com.kevadiyakrunalk.rxlocation.observables.location;

import android.app.PendingIntent;
import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.kevadiyakrunalk.rxlocation.observables.BaseLocationObservable;
import com.kevadiyakrunalk.rxlocation.observables.StatusException;

import rx.Observable;
import rx.Observer;

/**
 * The type Add location intent updates observable.
 */
public class AddLocationIntentUpdatesObservable extends BaseLocationObservable<Status> {
    private final LocationRequest locationRequest;
    private final PendingIntent intent;
    private Context context;

    /**
     * Create observable observable.
     *
     * @param ctx             the ctx
     * @param locationRequest the location request
     * @param intent          the intent
     * @return the observable
     */
    public static Observable<Status> createObservable(Context ctx, LocationRequest locationRequest, PendingIntent intent) {
        return Observable.create(new AddLocationIntentUpdatesObservable(ctx, locationRequest, intent));
    }

    private AddLocationIntentUpdatesObservable(Context ctx, LocationRequest locationRequest, PendingIntent intent) {
        super(ctx);
        context = ctx;
        this.locationRequest = locationRequest;
        this.intent = intent;
    }

    @Override
    protected void onGoogleApiClientReady(GoogleApiClient apiClient, final Observer<? super Status> observer) {
        LocationServices.FusedLocationApi.requestLocationUpdates(apiClient, locationRequest, intent)
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
