package com.kevadiyakrunalk.mvvmarchitecture.delegates;

import android.databinding.ViewDataBinding;

import com.kevadiyakrunalk.mvvmarchitecture.common.MvvmViewModel;

/**
 * The MvvmDelegate callback for fragment.
 * <p>
 * This callback will be called from {@link FragmentDelegate}.
 * It must be implemented by all Fragments that you want to support library's mvvm.
 *
 * @param <T> the type of {@link ViewDataBinding}
 * @param <S> the type of binded {@link MvvmViewModel}
 */
public interface FragmentDelegateCallback<T extends ViewDataBinding, S extends MvvmViewModel>
        extends DelegateCallback<T, S> {
}
