package com.kevadiyakrunalk.rxlocation.observables.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.kevadiyakrunalk.rxlocation.observables.BaseLocationObservable;

import rx.Observable;
import rx.Observer;

/**
 * The type Last known location observable.
 */
public class LastKnownLocationObservable extends BaseLocationObservable<Location> {
    private Context context;
    private Location location = null;

    /**
     * Create observable observable.
     *
     * @param ctx the ctx
     * @return the observable
     */
    public static Observable<Location> createObservable(Context ctx) {
        return Observable.create(new LastKnownLocationObservable(ctx));
    }

    private LastKnownLocationObservable(Context ctx) {
        super(ctx);
        context = ctx;
    }

    @SuppressWarnings("MissingPermission")
    @Override
    protected void onGoogleApiClientReady(GoogleApiClient apiClient, Observer<? super Location> observer) {
        try {
            location = null;
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (location == null && isGPSEnabled)
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if(location == null)
                location = LocationServices.FusedLocationApi.getLastLocation(apiClient);

            if (location == null && isNetworkEnabled)
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (location != null)
            observer.onNext(location);
        observer.onCompleted();
    }
}
