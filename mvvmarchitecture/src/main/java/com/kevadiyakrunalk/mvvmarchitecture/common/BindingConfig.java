package com.kevadiyakrunalk.mvvmarchitecture.common;

import android.support.annotation.LayoutRes;

import com.kevadiyakrunalk.mvvmarchitecture.BR;

/**
 * Binging configuration params holder.
 * <p>
 * Use it to define a binding configuration params for a specific view.
 */
public class BindingConfig {

    @LayoutRes
    private final int layoutResource;
    private final int viewModelVariableName;

    /**
     * Create a BindingConfig object for an Activity/Fragment.
     *
     * @param layoutResource        layout resource id
     * @param viewModelVariableName data binding variable name for attached ViewModel -
     *                              must be generated id (e.g. BR.varName)
     */
    public BindingConfig(@LayoutRes int layoutResource, int viewModelVariableName) {
        this.layoutResource = layoutResource;
        this.viewModelVariableName = viewModelVariableName;
    }

    /**
     * Create a BindingConfig object for an Activity/Fragment with default variable name.
     *
     * @param layoutResource layout resource id
     */
    public BindingConfig(@LayoutRes int layoutResource) {
        this(layoutResource, BR.viewModel);
    }

    /**
     * Returns layout resource id.
     *
     * @return layout resource id.
     */
    public int getLayoutResource() {
        return layoutResource;
    }

    /**
     * Returns variable name for attached ViewModel.
     *
     * @return ViewModel variable name.
     */
    public int getViewModelVariableName() {
        return viewModelVariableName;
    }
}
