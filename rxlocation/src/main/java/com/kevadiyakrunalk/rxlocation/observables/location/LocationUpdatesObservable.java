package com.kevadiyakrunalk.rxlocation.observables.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.kevadiyakrunalk.rxlocation.observables.BaseLocationObservable;

import rx.Observable;
import rx.Observer;

/**
 * The type Location updates observable.
 */
public class LocationUpdatesObservable extends BaseLocationObservable<Location> {
    private Context context;
    private int count;

    /**
     * Create observable observable.
     *
     * @param ctx             the ctx
     * @param locationRequest the location request
     * @return the observable
     */
    public static Observable<Location> createObservable(Context ctx, LocationRequest locationRequest) {
        return Observable.create(new LocationUpdatesObservable(ctx, locationRequest));
    }

    private final LocationRequest locationRequest;
    private LocationListener listener;
    private LocationManager locationManager;
    private android.location.LocationListener locationListener;

    private LocationUpdatesObservable(Context ctx, LocationRequest locationRequest) {
        super(ctx);
        context = ctx;
        count = 0;
        this.locationRequest = locationRequest;
    }

    @SuppressWarnings("MissingPermission")
    @Override
    protected void onGoogleApiClientReady(GoogleApiClient apiClient, final Observer<? super Location> observer) {
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(count != locationRequest.getNumUpdates()) {
                    observer.onNext(location);
                    count++;
                }
            }
        };
        locationListener = new android.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(count != locationRequest.getNumUpdates()) {
                    observer.onNext(location);
                    count++;
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }
            @Override
            public void onProviderEnabled(String s) {
            }
            @Override
            public void onProviderDisabled(String s) {
            }
        };

        try {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isGPSEnabled)
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, locationRequest.getInterval(),
                        0, locationListener);

            LocationServices.FusedLocationApi.requestLocationUpdates(apiClient, locationRequest, listener);

            if (isNetworkEnabled)
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, locationRequest.getInterval(),
                        0, locationListener);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("MissingPermission")
    @Override
    protected void onUnsubscribed(GoogleApiClient locationClient) {
        if (locationClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(locationClient, listener);
        }
        if(locationManager != null && locationListener != null)
            locationManager.removeUpdates(locationListener);
    }
}
