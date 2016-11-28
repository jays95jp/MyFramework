package com.kevadiyakrunalk.myframework.navigators;

import android.app.Activity;
import android.content.Intent;

import com.kevadiyakrunalk.mvvmarchitecture.common.Navigator;
import com.kevadiyakrunalk.myframework.activities.MainActivity;

public class StartNavigator implements Navigator {

    private Activity activity;

    /**
     * Create StartNavigation
     *
     * @param activity the activity for navigation delegation
     */
    public StartNavigator(Activity activity) {
        this.activity = activity;
    }

    /**
     * Start main app activity
     */
    public void navigateToMain() {
        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();
    }
}
