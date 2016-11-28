package com.kevadiyakrunalk.rxlocation.observables.location;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.kevadiyakrunalk.rxlocation.observables.BaseLocationObservable;
import com.kevadiyakrunalk.rxlocation.observables.StatusException;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * The type Mock location observable.
 */
public class MockLocationObservable extends BaseLocationObservable<Status> {
    private Observable<Location> locationObservable;
    private Subscription mockLocationSubscription;
    private Context context;

    /**
     * Create observable observable.
     *
     * @param context            the context
     * @param locationObservable the location observable
     * @return the observable
     */
    public static Observable<Status> createObservable(Context context, Observable<Location> locationObservable) {
        return Observable.create(new MockLocationObservable(context, locationObservable));
    }

    /**
     * Instantiates a new Mock location observable.
     *
     * @param ctx                the ctx
     * @param locationObservable the location observable
     */
    protected MockLocationObservable(Context ctx, Observable<Location> locationObservable) {
        super(ctx);
        context = ctx;
        this.locationObservable = locationObservable;
    }

    @Override
    protected void onGoogleApiClientReady(final GoogleApiClient apiClient, final Observer<? super Status> observer) {
        // this throws SecurityException if permissions are bad or mock locations are not enabled,
        // which is passed to observer's onError by BaseObservable
        LocationServices.FusedLocationApi.setMockMode(apiClient, true)
                .setResultCallback(status -> {
                    if (!status.isSuccess()) {
                        observer.onError(new StatusException(status));
                    } else {
                        startLocationMocking(apiClient, observer);
                    }
                });
    }

    private void startLocationMocking(final GoogleApiClient apiClient, final Observer<? super Status> observer) {
        mockLocationSubscription = locationObservable.subscribe(location -> {
            LocationServices.FusedLocationApi.setMockLocation(apiClient, location)
                    .setResultCallback(status -> {
                        if (!status.isSuccess()) {
                            observer.onError(new StatusException(status));
                        } else {
                            observer.onNext(status);
                        }
                    });
        }, throwable -> observer.onError(throwable), () -> observer.onCompleted());
    }

    @Override
    protected void onUnsubscribed(GoogleApiClient locationClient) {
        if (locationClient.isConnected()) {
            LocationServices.FusedLocationApi.setMockMode(locationClient, false);
        }
        if (mockLocationSubscription != null && !mockLocationSubscription.isUnsubscribed()) {
            mockLocationSubscription.unsubscribe();
        }
    }
}
