package com.kevadiyakrunalk.mvvmarchitecture.common;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;

/**
 * The root view interface for every mvvm view.
 *
 * @param <T> generated Data Binding layout class
 * @param <S> ViewModel binded to this view
 */
public interface MvvmView<T extends ViewDataBinding, S extends MvvmViewModel> {

    /**
     * Create binging config for this view.
     *
     * @return view binding config.
     */
    @NonNull
    BindingConfig getBindingConfig();

    /**
     * Setter for Data Binding instance.
     *
     * @param binding the binding for this view
     */
    void setBinding(@NonNull T binding);

    /**
     * Setter for ViewModel instance.
     *
     * @param viewModel binded ViewModel
     */
    void setViewModel(@NonNull S viewModel);
}
