package com.kevadiyakrunalk.rxfilepicker.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.kevadiyakrunalk.rxfilepicker.R;
import com.kevadiyakrunalk.rxfilepicker.fragment.BaseFragment;

/**
 * The type Fragment util.
 */
public class FragmentUtil {

    /**
     * Had fragment boolean.
     *
     * @param activity the activity
     * @return the boolean
     */
    public static boolean hadFragment(AppCompatActivity activity) {
        return activity.getSupportFragmentManager().getBackStackEntryCount() != 0;
    }

    /**
     * Replace fragment.
     *
     * @param activity  the activity
     * @param contentId the content id
     * @param fragment  the fragment
     */
    public static void replaceFragment(AppCompatActivity activity, int contentId, BaseFragment fragment) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
        transaction.replace(contentId, fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(null);
        transaction.commit();
    }


    /**
     * Add fragment.
     *
     * @param activity  the activity
     * @param contentId the content id
     * @param fragment  the fragment
     */
    public static void addFragment(AppCompatActivity activity, int contentId, BaseFragment fragment) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
        transaction.add(contentId, fragment, fragment.getClass().getSimpleName());
        transaction.commit();
    }

    /**
     * Remove fragment.
     *
     * @param activity the activity
     * @param fragment the fragment
     */
    public static void removeFragment(AppCompatActivity activity, BaseFragment fragment) {
        activity.getSupportFragmentManager().beginTransaction()
                .remove(fragment)
                .commit();
    }

    /**
     * Show fragment.
     *
     * @param activity the activity
     * @param fragment the fragment
     */
    public static void showFragment(AppCompatActivity activity, BaseFragment fragment) {
        activity.getSupportFragmentManager().beginTransaction()
                .show(fragment)
                .commit();
    }

    /**
     * Hide fragment.
     *
     * @param activity the activity
     * @param fragment the fragment
     */
    public static void hideFragment(AppCompatActivity activity, BaseFragment fragment) {
        activity.getSupportFragmentManager().beginTransaction()
                .hide(fragment)
                .commit();
    }

    /**
     * Attach fragment.
     *
     * @param activity the activity
     * @param fragment the fragment
     */
    public static void attachFragment(AppCompatActivity activity, BaseFragment fragment) {
        activity.getSupportFragmentManager().beginTransaction()
                .attach(fragment)
                .commit();
    }

    /**
     * Detach fragment.
     *
     * @param activity the activity
     * @param fragment the fragment
     */
    public static void detachFragment(AppCompatActivity activity, BaseFragment fragment) {
        activity.getSupportFragmentManager().beginTransaction()
                .detach(fragment)
                .commit();
    }

    /**
     * Gets fragment by tag.
     *
     * @param appCompatActivity the app compat activity
     * @param tag               the tag
     * @return the fragment by tag
     */
    public static Fragment getFragmentByTag(AppCompatActivity appCompatActivity, String tag) {
        return appCompatActivity.getSupportFragmentManager().findFragmentByTag(tag);
    }
}