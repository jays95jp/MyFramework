package com.kevadiyakrunalk.mvvmarchitecture;

import android.databinding.Observable;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kevadiyakrunalk.mvvmarchitecture.common.MvvmView;
import com.kevadiyakrunalk.mvvmarchitecture.common.MvvmViewModel;
import com.kevadiyakrunalk.mvvmarchitecture.delegates.FragmentDelegate;
import com.kevadiyakrunalk.mvvmarchitecture.delegates.FragmentDelegateCallback;
import com.kevadiyakrunalk.mvvmarchitecture.lifecycle.RxFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Base Fragment class that uses DataBinding and implements Model-View-ViewModel architecture.
 *
 * @param <T> the type of {@link ViewDataBinding}
 * @param <S> the type of binded {@link MvvmViewModel}
 */
public abstract class MvvmFragment<T extends ViewDataBinding, S extends MvvmViewModel> extends RxFragment
        implements MvvmView<T, S>, FragmentDelegateCallback<T, S> {

    private List<Observable.OnPropertyChangedCallback> onPropertyChangedCallbacks = new ArrayList<>();
    private FragmentDelegate<T, S> delegate;
    private T binding;
    private S viewModel;

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMvvmDelegate().onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return binding.getRoot();
    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        getMvvmDelegate().onResume();
    }

    @Override
    @CallSuper
    public void onPause() {
        getMvvmDelegate().onPause();
        super.onPause();
    }

    @Override
    @CallSuper
    public void onDestroy() {
        removeOnPropertyChangedCallbacks();
        getMvvmDelegate().onDestroy();
        super.onDestroy();
    }

    @Override
    @CallSuper
    public void onSaveInstanceState(Bundle outState) {
        getMvvmDelegate().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @NonNull
    @Override
    public MvvmView<T, S> getMvvmView() {
        return this;
    }

    /**
     * Getter for view binding
     *
     * @return the {@link ViewDataBinding} for this view
     */
    @NonNull
    public T getBinding() {
        if (binding == null) {
            throw new IllegalStateException("You can't call getBinding() before fragment creating.");
        }
        return binding;
    }

    @Override
    @CallSuper
    public void setBinding(@NonNull T binding) {
        this.binding = binding;
    }

    /**
     * Getter for binded ViewModel
     *
     * @return the {@link MvvmViewModel} binded to this view
     */
    @NonNull
    public S getViewModel() {
        if (viewModel == null) {
            throw new IllegalStateException("You can't call getViewModel() before fragment creating.");
        }
        return viewModel;
    }

    @Override
    @CallSuper
    public void setViewModel(@NonNull S viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Add an Observable.OnPropertyChangedCallback to ViewModel that will be removed on View destroy
     * <p>
     * Use this method to add Observable.OnPropertyChangedCallback to ViewModel. All callbacks will be removed on
     * View destroy. It helps avoid memory leaks via callbacks.
     *
     * @param callback the callback to start listening
     */
    protected void addOnPropertyChangedCallback(Observable.OnPropertyChangedCallback callback) {
        onPropertyChangedCallbacks.add(callback);
        getViewModel().addOnPropertyChangedCallback(callback);
    }

    /**
     * Create delegate for the current fragment if not exists.
     *
     * @return the {@link FragmentDelegate} for current fragment
     */
    protected FragmentDelegate getMvvmDelegate() {
        if (delegate == null) {
            delegate = new FragmentDelegate<>(this, this);
        }
        return delegate;
    }

    private void removeOnPropertyChangedCallbacks() {
        for (Observable.OnPropertyChangedCallback callback : onPropertyChangedCallbacks) {
            getViewModel().removeOnPropertyChangedCallback(callback);
        }
    }
}
