package com.kevadiyakrunalk.mvvmarchitecture.common;

import android.databinding.Observable;

/**
 * The root ViewModel interface for every mvvm ViewModel.
 */
public interface MvvmViewModel extends Observable {

    /**
     * Called when this ViewModel instance was created.
     * <p>
     * This is a place to do any initialisation.
     */
    void onCreate();

    /**
     * Called when this ViewModel was binded to a view and the view is visible.
     */
    void onResume();

    /**
     * Called when this ViewModel was unbinded from a view or view was paused.
     * <p>
     * Don't interact with view after this method was called
     * (e.g. show toast or start new activity).
     */
    void onPause();

    /**
     * Called when this ViewModel instance was destroyed and removed from cache.
     * <p>
     * This is a place to do any cleanup to avoid memory leaks.
     */
    void onDestroy();
}
