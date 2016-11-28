package com.kevadiyakrunalk.myframework.other.location.navigators;

import android.app.Activity;
import android.content.Intent;

import com.kevadiyakrunalk.mvvmarchitecture.common.Navigator;
import com.kevadiyakrunalk.myframework.other.location.activities.PlacesResultActivity;

/**
 * The type Places navigator.
 */
public class PlacesNavigator implements Navigator {
    private String EXTRA_PLACE_ID = "EXTRA_PLACE_ID";

    private Activity activity;

    /**
     * Create StartNavigation
     *
     * @param activity the activity for navigation delegation
     */
    public PlacesNavigator(Activity activity) {
        this.activity = activity;
    }

    /**
     * Start main app activity
     *
     * @param placeId the place id
     */
    public void navigateToPlacesResult(String placeId) {
        Intent startIntent = new Intent(activity, PlacesResultActivity.class);
        startIntent.putExtra(EXTRA_PLACE_ID, placeId);
        activity.startActivity(startIntent);
        activity.finish();
    }
}
