package com.kevadiyakrunalk.mvvmarchitecture.delegates;

import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;

import com.kevadiyakrunalk.mvvmarchitecture.common.MvvmViewModel;

/**
 * The MvvmDelegate callback for activity.
 * <p>
 * This callback will be called from {@link ActivityDelegate}.
 * It must be implemented by all Activities that you want to support library's mvvm.
 *
 * @param <T> the type of {@link ViewDataBinding}
 * @param <S> the type of binded {@link MvvmViewModel}
 */
public interface ActivityDelegateCallback<T extends ViewDataBinding, S extends MvvmViewModel>
        extends DelegateCallback<T, S> {

    /**
     * This method should invoke {@link android.support.v4.app.FragmentActivity#getLastCustomNonConfigurationInstance()}
     *
     * @return the value previously returned from
     * {@link android.support.v4.app.FragmentActivity#onRetainCustomNonConfigurationInstance()}
     */
    @Nullable
    Object getLastCustomNonConfigurationInstance();
}
