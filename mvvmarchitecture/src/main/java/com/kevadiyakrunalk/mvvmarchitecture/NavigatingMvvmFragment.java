package com.kevadiyakrunalk.mvvmarchitecture;

import android.databinding.ViewDataBinding;

import com.kevadiyakrunalk.mvvmarchitecture.common.NavigatingViewModel;
import com.kevadiyakrunalk.mvvmarchitecture.common.Navigator;
import com.kevadiyakrunalk.mvvmarchitecture.delegates.FragmentDelegate;
import com.kevadiyakrunalk.mvvmarchitecture.delegates.FragmentDelegateCallback;
import com.kevadiyakrunalk.mvvmarchitecture.delegates.NavigatingDelegateCallback;
import com.kevadiyakrunalk.mvvmarchitecture.delegates.NavigatingFragmentDelegate;

/**
 * MvvmFragment that supports Navigator
 *
 * @param <T> the type of {@link Navigator}
 * @param <S> the type of {@link ViewDataBinding}
 * @param <U> the type of binded {@link NavigatingViewModel}
 */
public abstract class NavigatingMvvmFragment<T extends Navigator, S extends ViewDataBinding,
        U extends NavigatingViewModel<T>>
        extends MvvmFragment<S, U> implements FragmentDelegateCallback<S, U>, NavigatingDelegateCallback<T> {

    private NavigatingFragmentDelegate<T, S, U> delegate;

    @Override
    protected FragmentDelegate getMvvmDelegate() {
        if (delegate == null) {
            delegate = new NavigatingFragmentDelegate<>(this, this, this);
        }
        return delegate;
    }
}
