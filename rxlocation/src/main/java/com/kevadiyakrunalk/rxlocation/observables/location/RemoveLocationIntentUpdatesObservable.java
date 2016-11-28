package com.kevadiyakrunalk.rxlocation.observables.location;

import android.app.PendingIntent;
import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.kevadiyakrunalk.rxlocation.observables.BaseLocationObservable;
import com.kevadiyakrunalk.rxlocation.observables.StatusException;

import rx.Observable;
import rx.Observer;

/**
 * The type Remove location intent updates observable.
 */
public class RemoveLocationIntentUpdatesObservable extends BaseLocationObservable<Status> {
    private final PendingIntent intent;

    /**
     * Create observable observable.
     *
     * @param ctx    the ctx
     * @param intent the intent
     * @return the observable
     */
    public static Observable<Status> createObservable(Context ctx, PendingIntent intent) {
        return Observable.create(new RemoveLocationIntentUpdatesObservable(ctx, intent));
    }

    private RemoveLocationIntentUpdatesObservable(Context ctx, PendingIntent intent) {
        super(ctx);
        this.intent = intent;
    }

    @Override
    protected void onGoogleApiClientReady(GoogleApiClient apiClient, final Observer<? super Status> observer) {
        LocationServices.FusedLocationApi.removeLocationUpdates(apiClient, intent)
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
