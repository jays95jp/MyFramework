package com.kevadiyak.myframework.viewmodels;

import android.content.Context;
import android.databinding.Bindable;
import android.text.TextUtils;

import com.kevadiyak.commonutils.common.Logs;
import com.kevadiyak.mvvmarchitecture.common.NavigatingViewModel;
import com.kevadiyak.myframework.BR;
import com.kevadiyak.myframework.navigators.LocationNavigator;

public class LocationFragmentViewModel extends NavigatingViewModel<LocationNavigator> {
    private Context context;
    private Logs logs;

    private String lastKnownLocation;
    private String updatableLocation;
    private String addressLocation;
    private String currentActivity;

    public LocationFragmentViewModel(Context context, Logs logs) {
        this.context = context;
        this.logs = logs;
    }

    @Bindable
    public String getLastKnownLocation() {
        return lastKnownLocation;
    }

    public void setLastKnownLocation(String lastKnownLocation) {
        if (TextUtils.equals(this.lastKnownLocation, lastKnownLocation)) return;

        this.lastKnownLocation = lastKnownLocation;
        notifyPropertyChanged(BR.lastKnownLocation);
    }

    @Bindable
    public String getUpdatableLocation() {
        return updatableLocation;
    }

    public void setUpdatableLocation(String updatableLocation) {
        if (TextUtils.equals(this.updatableLocation, updatableLocation)) return;

        this.updatableLocation = updatableLocation;
        notifyPropertyChanged(BR.updatableLocation);
    }

    @Bindable
    public String getAddressLocation() {
        return addressLocation;
    }

    public void setAddressLocation(String addressLocation) {
        if (TextUtils.equals(this.addressLocation, addressLocation)) return;

        this.addressLocation = addressLocation;
        notifyPropertyChanged(BR.addressLocation);
    }

    @Bindable
    public String getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(String currentActivity) {
        if (TextUtils.equals(this.currentActivity, currentActivity)) return;

        this.currentActivity = currentActivity;
        notifyPropertyChanged(BR.currentActivity);
    }

    public void onStartGeofenceActivity() {
        logs.error("Geofence", navigator != null ? "navigator -> TRUE" : "navigator -> FALSE");
        if (navigator != null) {
            navigator.navigateToGeofence();
        }
    }

    public void onStartPlacesActivity() {
        logs.error("Places", navigator != null ? "navigator -> TRUE" : "navigator -> FALSE");
        if (navigator != null) {
            navigator.navigateToPlaces();
        }
    }

    public void onStartMockLocationActivity() {
        logs.error("MockLocation", navigator != null ? "navigator -> TRUE" : "navigator -> FALSE");
        if (navigator != null) {
            navigator.navigateToMockLocation();
        }
    }
}
