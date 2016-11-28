package com.kevadiyakrunalk.mvvmarchitecture.delegates;

import android.app.Activity;
import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import com.kevadiyakrunalk.mvvmarchitecture.common.MvvmViewModel;

/**
 * A delegate for Fragments lifecycle.
 * <p>
 * The following methods must be invoked from the corresponding Activities lifecycle methods:
 * <ul>
 * <li>{@link #onCreate(Bundle)}
 * <li>{@link #onResume()}
 * <li>{@link #onPause()}
 * <li>{@link #onDestroy()}
 * <li>{@link #onSaveInstanceState(Bundle)}
 * </ul>
 *
 * @param <T> the type of {@link ViewDataBinding}
 * @param <S> the type of binded {@link MvvmViewModel}
 */
public class FragmentDelegate<T extends ViewDataBinding, S extends MvvmViewModel> extends MvvmDelegate<T, S> {

    private static final String VIEW_MODEL_KEY_NAME
            = "io.github.azaiats.androidmvvm.core.delegates.FragmentDelegate.VIEW_MODEL_KEY_NAME";

    private final Fragment delegatedFragment;
    private ViewModelCache cache;
    private int viewModelKey = 0;
    private boolean saved = false;

    /**
     * Create a delegate.
     *
     * @param callback          the {@link DelegateCallback} for this delegate
     * @param delegatedFragment {@link Fragment} for delegation
     */
    public FragmentDelegate(DelegateCallback<T, S> callback, Fragment delegatedFragment) {
        super(callback);
        this.delegatedFragment = delegatedFragment;
        cache = new ViewModelCache();
    }

    /**
     * This method must be called from {@link Fragment#onCreate(Bundle)}
     *
     * @param savedInstanceState saved state for delegated {@link Fragment}
     */
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            viewModelKey = savedInstanceState.getInt(VIEW_MODEL_KEY_NAME, 0);
        }
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        if (isFinished()) {
            removeViewModel();
            cache.cleanUp();
        }
        super.onDestroy();
    }

    /**
     * This method must be called from {@link Fragment#onSaveInstanceState(Bundle)}
     *
     * @param bundle Bundle in which to place key for cached ViewModel.
     */
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        final Activity activity = delegatedFragment.getActivity();
        final int key = viewModelKey == 0 ? cache.getSpareKey(activity) : viewModelKey;
        cache.putViewModel(key, viewModel, activity);

        bundle.putInt(VIEW_MODEL_KEY_NAME, key);
        saved = true;
    }

    @Override
    protected boolean isFinished() {
        return !saved
                || delegatedFragment.getActivity() == null
                || !delegatedFragment.getActivity().isChangingConfigurations();
    }

    @Nullable
    @Override
    protected S getCachedViewModel() {
        return (S) cache.getViewModel(viewModelKey, delegatedFragment.getActivity());
    }

    @NonNull
    @Override
    protected T createDataBinding(@LayoutRes int layoutResource) {
        final LayoutInflater layoutInflater = LayoutInflater.from(delegatedFragment.getActivity());
        return DataBindingUtil.inflate(layoutInflater, layoutResource, null, false);
    }

    private void removeViewModel() {
        if (viewModelKey > 0) {
            cache.removeViewModel(viewModelKey, delegatedFragment.getActivity());
        }
    }
}
