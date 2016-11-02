package com.kevadiyak.myframework.navigators;

import android.app.Activity;
import android.content.Intent;

import com.kevadiyak.mvvmarchitecture.common.Navigator;
import com.kevadiyak.myframework.other.location.activities.GeofenceActivity;
import com.kevadiyak.myframework.other.location.activities.MockLocationsActivity;
import com.kevadiyak.myframework.other.location.activities.PlacesActivity;


public class LocationNavigator implements Navigator {

    private Activity activity;

    /**
     * Create StartNavigation
     *
     * @param activity the activity for navigation delegation
     */
    public LocationNavigator(Activity activity) {
        this.activity = activity;
    }

    public void navigateToGeofence() {
        activity.startActivity(new Intent(activity, GeofenceActivity.class));
    }

    public void navigateToPlaces() {
        activity.startActivity(new Intent(activity, PlacesActivity.class));
    }

    public void navigateToMockLocation() {
        activity.startActivity(new Intent(activity, MockLocationsActivity.class));
    }
}
