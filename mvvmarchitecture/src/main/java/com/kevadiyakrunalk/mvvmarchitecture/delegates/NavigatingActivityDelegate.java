package com.kevadiyakrunalk.mvvmarchitecture.delegates;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import com.kevadiyakrunalk.mvvmarchitecture.common.NavigatingViewModel;
import com.kevadiyakrunalk.mvvmarchitecture.common.Navigator;

/**
 * A navigated delegate for Activities lifecycle.
 *
 * @param <T> the type of {@link Navigator}
 * @param <S> the type of {@link ViewDataBinding}
 * @param <U> the type of binded {@link NavigatingViewModel}
 */
public class NavigatingActivityDelegate<T extends Navigator, S extends ViewDataBinding,
        U extends NavigatingViewModel<T>> extends ActivityDelegate<S, U> {

    private final NavigatingDelegateCallback<T> navigatingCallback;

    /**
     * Create delegate for activity.
     *
     * @param callback           the {@link ActivityDelegateCallback} for this delegate
     * @param navigatingCallback the {@link NavigatingDelegateCallback} for this delegate
     * @param delegatedActivity  the {@link Activity} for delegation
     */
    public NavigatingActivityDelegate(@NonNull ActivityDelegateCallback<S, U> callback,
                                      @NonNull NavigatingDelegateCallback<T> navigatingCallback,
                                      @NonNull Activity delegatedActivity) {
        super(callback, delegatedActivity);
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
    public void onDestroy() {
        if (viewModel != null) {
            viewModel.setNavigator(null);
        }
        super.onDestroy();
    }
}
