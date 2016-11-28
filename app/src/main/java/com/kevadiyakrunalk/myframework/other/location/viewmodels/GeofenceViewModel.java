package com.kevadiyakrunalk.myframework.other.location.viewmodels;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.databinding.Bindable;
import android.location.Location;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.kevadiyakrunalk.mvvmarchitecture.common.BaseViewModel;
import com.kevadiyakrunalk.myframework.BR;
import com.kevadiyakrunalk.myframework.other.location.GeofenceBroadcastReceiver;
import com.kevadiyakrunalk.rxlocation.ReactiveLocationProvider;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by admin on 10/19/2016.
 */
public class GeofenceViewModel extends BaseViewModel {
    private Context context;

    private ReactiveLocationProvider reactiveLocationProvider;
    private Subscription lastKnownLocationSubscription;

    private String latitude;
    private String longitude;
    private String radius;
    private String lastKnownLocation;

    public GeofenceViewModel(Context context) {
        this.context = context;
        reactiveLocationProvider = ReactiveLocationProvider.getInstance(context);
    }

    @Bindable
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        if (TextUtils.equals(this.latitude, latitude)) return;

        this.latitude = latitude;
        notifyPropertyChanged(BR.latitude);
    }

    @Bindable
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        if (TextUtils.equals(this.longitude, longitude)) return;

        this.longitude = longitude;
        notifyPropertyChanged(BR.longitude);
    }

    @Bindable
    public String getRadius() {
        return radius;
    }

    @Bindable
    public void setRadius(String radius) {
        if (TextUtils.equals(this.radius, radius)) return;

        this.radius = radius;
        notifyPropertyChanged(BR.radius);
    }

    public String getLastKnownLocation() {
        return lastKnownLocation;
    }

    public void setLastKnownLocation(String lastKnownLocation) {
        if (TextUtils.equals(this.lastKnownLocation, lastKnownLocation)) return;

        this.lastKnownLocation = lastKnownLocation;
        notifyPropertyChanged(BR.lastKnownLocation);
    }

    public void onAdd(View view) {
        addGeofence();
    }

    public void onClear(View view) {
        clearGeofence();
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lastKnownLocationSubscription = reactiveLocationProvider
                        .getLastKnownLocation()
                        .map(new Func1<Location, String>() {
                            @Override
                            public String call(Location location) {
                                if (location != null)
                                    return location.getLatitude() + " " + location.getLongitude() + " (" + location.getAccuracy() + ")";
                                return "no location available";
                            }
                        })
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                setLastKnownLocation(s);
                            }
                        });
            }
        }, 1000);
    }

    @Override
    public void onPause() {
        super.onPause();
        lastKnownLocationSubscription.unsubscribe();
    }

    private void clearGeofence() {
        reactiveLocationProvider.removeGeofences(createNotificationBroadcastPendingIntent()).subscribe(new Action1<Status>() {
            @Override
            public void call(Status status) {
                toast("Geofences removed");
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                toast("Error removing geofences");
            }
        });
    }

    private void toast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    private PendingIntent createNotificationBroadcastPendingIntent() {
        return PendingIntent.getBroadcast(context, 0, new Intent(context, GeofenceBroadcastReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void addGeofence() {
        final GeofencingRequest geofencingRequest = createGeofencingRequest();
        if (geofencingRequest == null) return;

        final PendingIntent pendingIntent = createNotificationBroadcastPendingIntent();
        reactiveLocationProvider
                .removeGeofences(pendingIntent)
                .flatMap(new Func1<Status, Observable<Status>>() {
                    @Override
                    public Observable<Status> call(Status pendingIntentRemoveGeofenceResult) {
                        return reactiveLocationProvider.addGeofences(pendingIntent, geofencingRequest);
                    }
                })
                .subscribe(new Action1<Status>() {
                    @Override
                    public void call(Status addGeofenceResult) {
                        toast("Geofence added, success: " + addGeofenceResult.isSuccess());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        toast("Error adding geofence.");
                    }
                });
    }

    private GeofencingRequest createGeofencingRequest() {
        try {
            double lng = Double.parseDouble(longitude);
            double lat = Double.parseDouble(latitude);
            float r = Float.parseFloat(radius);
            Geofence geofence = new Geofence.Builder()
                    .setRequestId("GEOFENCE")
                    .setCircularRegion(lat, lng, r)
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build();
            return new GeofencingRequest.Builder().addGeofence(geofence).build();
        } catch (NumberFormatException ex) {
            toast("Error parsing input.");
            return null;
        }
    }
}
