package com.kevadiyakrunalk.mvvmarchitecture.delegates;

import android.app.Fragment;
import android.databinding.ViewDataBinding;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import com.kevadiyakrunalk.mvvmarchitecture.common.NavigatingViewModel;
import com.kevadiyakrunalk.mvvmarchitecture.common.Navigator;

/**
 * A navigated delegate for Fragments lifecycle.
 *
 * @param <T> the type of {@link Navigator}
 * @param <S> the type of {@link ViewDataBinding}
 * @param <U> the type of binded {@link NavigatingViewModel}
 */
public class NavigatingFragmentDelegate<T extends Navigator, S extends ViewDataBinding,
        U extends NavigatingViewModel<T>> extends FragmentDelegate<S, U> {

    private final NavigatingDelegateCallback<T> navigatingCallback;

    /**
     * Create a navigated delegate for fragment.
     *
     * @param callback           the {@link DelegateCallback} for this delegate
     * @param navigatingCallback the {@link NavigatingDelegateCallback} for this delegate
     * @param delegatedFragment  {@link Fragment} for delegation
     */
    public NavigatingFragmentDelegate(@NonNull DelegateCallback<S, U> callback,
                                      @NonNull NavigatingDelegateCallback<T> navigatingCallback,
                                      @NonNull Fragment delegatedFragment) {
        super(callback, delegatedFragment);
        this.navigatingCallback = navigatingCallback;
    }

    @Override
    @CallSuper
    public void onCreate() {
        super.onCreate();
        if (viewModel != null) {
            viewModel.setNavigator(navigatingCallback.getNavigator());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (viewModel != null && viewModel.getNavigator() == null) {
            viewModel.setNavigator(navigatingCallback.getNavigator());
        }
    }

    @Override
    public void onDestroy() {
        if (viewModel != null) {
            viewModel.setNavigator(null);
        }
        super.onDestroy();
    }
}
