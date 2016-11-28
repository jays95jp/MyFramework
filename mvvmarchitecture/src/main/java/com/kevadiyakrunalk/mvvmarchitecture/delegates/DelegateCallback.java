package com.kevadiyakrunalk.mvvmarchitecture.delegates;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;

import com.kevadiyakrunalk.mvvmarchitecture.common.MvvmView;
import com.kevadiyakrunalk.mvvmarchitecture.common.MvvmViewModel;

/**
 * The base callback for Activity/Fragment delegates.
 * <p>
 * The base delegate callback functionality. Don't use it by your own.
 *
 * @param <T> the type of {@link ViewDataBinding}
 * @param <S> the type of binded {@link MvvmViewModel}
 */
public interface DelegateCallback<T extends ViewDataBinding, S extends MvvmViewModel> {

    /**
     * Getter for processed view.
     *
     * @return the processed {@link MvvmView}
     */
    @NonNull
    MvvmView<T, S> getMvvmView();

    /**
     * Create a ViewModel instance.
     *
     * @return the {@link MvvmViewModel} for the view
     */
    @NonNull
    S createViewModel();
}
