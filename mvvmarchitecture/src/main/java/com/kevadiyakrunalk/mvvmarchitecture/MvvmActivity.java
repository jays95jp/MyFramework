package com.kevadiyakrunalk.mvvmarchitecture;

import android.databinding.Observable;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.kevadiyakrunalk.mvvmarchitecture.common.MvvmView;
import com.kevadiyakrunalk.mvvmarchitecture.common.MvvmViewModel;
import com.kevadiyakrunalk.mvvmarchitecture.delegates.ActivityDelegate;
import com.kevadiyakrunalk.mvvmarchitecture.delegates.ActivityDelegateCallback;
import com.kevadiyakrunalk.mvvmarchitecture.lifecycle.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Base Activity class that uses DataBinding and implements Model-View-ViewModel architecture.
 *
 * @param <T> the type of {@link ViewDataBinding}
 * @param <S> the type of binded {@link MvvmViewModel}
 */
public abstract class MvvmActivity<T extends ViewDataBinding, S extends MvvmViewModel> extends RxAppCompatActivity
        implements MvvmView<T, S>, ActivityDelegateCallback<T, S> {

    private List<Observable.OnPropertyChangedCallback> onPropertyChangedCallbacks = new ArrayList<>();
    private ActivityDelegate<T, S> delegate;
    private T binding;
    private S viewModel;

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMvvmDelegate().onCreate();
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        removeOnPropertyChangedCallbacks();
        getMvvmDelegate().onDestroy();
        super.onDestroy();
    }

    @Override
    @CallSuper
    protected void onPause() {
        getMvvmDelegate().onPause();
        super.onPause();
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        getMvvmDelegate().onResume();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return getMvvmDelegate().onRetainCustomNonConfigurationInstance();
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
            throw new IllegalStateException("You can't call getBinding() before activity creating.");
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
            throw new IllegalStateException("You can't call getViewModel() before activity creating.");
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
     * Create delegate for the current activity.
     *
     * @return the {@link ActivityDelegate} for current activity
     */
    @NonNull
    protected ActivityDelegate<T, S> getMvvmDelegate() {
        if (delegate == null) {
            delegate = new ActivityDelegate<>(this, this);
        }
        return delegate;
    }

    private void removeOnPropertyChangedCallbacks() {
        for (Observable.OnPropertyChangedCallback callback : onPropertyChangedCallbacks) {
            getViewModel().removeOnPropertyChangedCallback(callback);
        }
    }

    /*@Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CustomFontContextWrapper.wrap(newBase));
    }*/
}
