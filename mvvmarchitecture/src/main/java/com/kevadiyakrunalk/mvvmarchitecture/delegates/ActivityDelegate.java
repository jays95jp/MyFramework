package com.kevadiyakrunalk.mvvmarchitecture.delegates;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kevadiyakrunalk.mvvmarchitecture.common.MvvmViewModel;

/**
 * A delegate for Activities lifecycle.
 * <p>
 * The following methods must be invoked from the corresponding Activities lifecycle methods:
 * <ul>
 * <li>{@link #onCreate()}
 * <li>{@link #onResume()}
 * <li>{@link #onPause()}
 * <li>{@link #onDestroy()}
 * <li>{@link #onRetainCustomNonConfigurationInstance()}
 * </ul>
 *
 * @param <T> the type of {@link ViewDataBinding}
 * @param <S> the type of binded {@link MvvmViewModel}
 */
public class ActivityDelegate<T extends ViewDataBinding, S extends MvvmViewModel>
        extends MvvmDelegate<T, S> {

    private final ActivityDelegateCallback<T, S> callback;
    private final Activity delegatedActivity;

    /**
     * Create delegate for activity.
     *
     * @param callback          the {@link ActivityDelegateCallback} for this delegate
     * @param delegatedActivity the {@link Activity} for delegation
     */
    public ActivityDelegate(@NonNull ActivityDelegateCallback<T, S> callback, @NonNull Activity delegatedActivity) {
        super(callback);
        this.callback = callback;
        this.delegatedActivity = delegatedActivity;
    }

    /**
     * This method must be called from
     * {@link android.support.v4.app.FragmentActivity#onRetainCustomNonConfigurationInstance()}.
     * Don't forget to return the value returned by this delegate method.
     *
     * @return the {@link MvvmViewModel} for bind it in restored activity
     */
    @Nullable
    public Object onRetainCustomNonConfigurationInstance() {
        if (!delegatedActivity.isChangingConfigurations() && viewModel != null) {
            viewModel.onDestroy();
            viewModel = null;
        }
        return viewModel;
    }

    @Override
    protected boolean isFinished() {
        return !delegatedActivity.isChangingConfigurations();
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    protected S getCachedViewModel() {
        return (S) callback.getLastCustomNonConfigurationInstance();
    }

    @NonNull
    @Override
    protected T createDataBinding(@LayoutRes int layoutResource) {
        return DataBindingUtil.setContentView(delegatedActivity, layoutResource);
    }
}
