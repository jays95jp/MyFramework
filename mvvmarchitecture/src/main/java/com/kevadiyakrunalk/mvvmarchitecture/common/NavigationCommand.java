package com.kevadiyakrunalk.mvvmarchitecture.common;

import android.support.annotation.NonNull;

/**
 * Provides command for Navigator
 *
 * @param <T> the type of {@link Navigator}
 */
public interface NavigationCommand<T extends Navigator> {

    /**
     * Defines the method to be called when the command is invoked.
     *
     * @param navigator navigator instance
     */
    void execute(@NonNull T navigator);
}
