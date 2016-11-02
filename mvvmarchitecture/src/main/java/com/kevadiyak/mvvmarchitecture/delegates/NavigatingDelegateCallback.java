package com.kevadiyak.mvvmarchitecture.delegates;

import android.support.annotation.NonNull;

import com.kevadiyak.mvvmarchitecture.common.Navigator;

/**
 * The base callback for Activity/Fragment delegates with navigation support.
 *
 * @param <T> the type of {@link Navigator}
 */
public interface NavigatingDelegateCallback<T extends Navigator> {

    /**
     * Getter for Navigator
     *
     * @return concrete Navigator instance
     */
    @NonNull
    T getNavigator();
}
