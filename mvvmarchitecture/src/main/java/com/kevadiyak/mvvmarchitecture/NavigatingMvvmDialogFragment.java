package com.kevadiyak.mvvmarchitecture;

import android.databinding.ViewDataBinding;

import com.kevadiyak.mvvmarchitecture.common.NavigatingViewModel;
import com.kevadiyak.mvvmarchitecture.common.Navigator;
import com.kevadiyak.mvvmarchitecture.delegates.FragmentDelegate;
import com.kevadiyak.mvvmarchitecture.delegates.FragmentDelegateCallback;
import com.kevadiyak.mvvmarchitecture.delegates.NavigatingDelegateCallback;
import com.kevadiyak.mvvmarchitecture.delegates.NavigatingFragmentDelegate;

/**
 * MvvmDialogFragment that supports Navigator
 *
 * @param <T> the type of {@link Navigator}
 * @param <S> the type of {@link ViewDataBinding}
 * @param <U> the type of binded {@link NavigatingViewModel}
 */
public abstract class NavigatingMvvmDialogFragment<T extends Navigator, S extends ViewDataBinding,
        U extends NavigatingViewModel<T>>
        extends MvvmDialogFragment<S, U> implements FragmentDelegateCallback<S, U>, NavigatingDelegateCallback<T> {

    private NavigatingFragmentDelegate<T, S, U> delegate;

    @Override
    protected FragmentDelegate getMvvmDelegate() {
        if (delegate == null) {
            delegate = new NavigatingFragmentDelegate<>(this, this, this);
        }
        return delegate;
    }
}
