package com.kevadiyakrunalk.mvvmarchitecture.common;

import android.databinding.BaseObservable;
import android.support.annotation.CallSuper;

/**
 * ViewModel base class. Every ViewModel must extend this class.
 */
public abstract class BaseViewModel extends BaseObservable implements MvvmViewModel {

    private boolean running;

    /**
     * Called when this ViewModel instance was created.
     * <p>
     * This is a place to do any initialisation.
     */
    @Override
    @CallSuper
    public void onCreate() {
    }

    /**
     * Called when this ViewModel was binded to a view and the view is visible.
     */
    @Override
    @CallSuper
    public void onResume() {
        running = true;
    }

    /**
     * Called when this ViewModel was unbinded from a view or view was paused.
     * <p>
     * Don't interact with view after this method was called
     * (e.g. show toast or start new activity).
     */
    @Override
    @CallSuper
    public void onPause() {
        running = false;
    }

    /**
     * Called when this ViewModel instance was destroyed and removed from cache.
     * <p>
     * This is a place to do any cleanup to avoid memory leaks.
     */
    @Override
    @CallSuper
    public void onDestroy() {
    }

    /**
     * Returns true if this ViewModel is attached to view end the view is in running state (not paused).
     *
     * @return true if running
     */
    protected boolean isRunning() {
        return running;
    }
}
