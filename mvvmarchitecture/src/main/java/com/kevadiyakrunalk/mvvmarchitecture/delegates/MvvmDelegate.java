package com.kevadiyakrunalk.mvvmarchitecture.delegates;

import android.databinding.ViewDataBinding;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kevadiyakrunalk.mvvmarchitecture.common.BindingConfig;
import com.kevadiyakrunalk.mvvmarchitecture.common.MvvmView;
import com.kevadiyakrunalk.mvvmarchitecture.common.MvvmViewModel;

/**
 * A base delegate for Activities/Fragments lifecycle.
 * <p>
 * The following methods must be invoked from the corresponding Activities lifecycle methods:
 * <ul>
 * <li>{@link #onCreate()}
 * <li>{@link #onResume()}
 * <li>{@link #onPause()}
 * <li>{@link #onDestroy()}
 * </ul>
 *
 * @param <T> the type of {@link ViewDataBinding}
 * @param <S> the type of binded {@link MvvmViewModel}
 */
abstract class MvvmDelegate<T extends ViewDataBinding, S extends MvvmViewModel> {

    private static final String BINDING_VARIABLE_ERROR_TEXT = "Binding variable wasn't set successfully. "
            + "Probably viewModelVariableName of your BindingConfig doesn't match any variable in %s.";

    @Nullable
    protected S viewModel;

    private final DelegateCallback<T, S> callback;

    /**
     * Create a delegate.
     *
     * @param callback the {@link DelegateCallback} for this delegate
     */
    public MvvmDelegate(DelegateCallback<T, S> callback) {
        this.callback = callback;
    }

    /**
     * This method must be called from {@link android.app.Activity#onCreate(android.os.Bundle)}
     * <p>
     * Create a ViewDataBinding and a MvvmViewModel, attach them to the processed view.
     */
    @CallSuper
    public void onCreate() {
        final MvvmView<T, S> view = callback.getMvvmView();
        final BindingConfig bindingConfig = view.getBindingConfig();
        final S viewModel = initViewModel();
        final T binding = initBinding(bindingConfig);
        view.setBinding(binding);
        view.setViewModel(viewModel);
    }

    /**
     * This method must be called from {@link android.app.Activity#onResume()}
     * or {@link android.support.v4.app.Fragment#onResume()}.
     */
    @CallSuper
    public void onResume() {
        if (viewModel != null) viewModel.onResume();
    }

    /**
     * This method must be called from {@link android.app.Activity#onPause()}
     * or {@link android.support.v4.app.Fragment#onPause()}.
     */
    @CallSuper
    public void onPause() {
        if (viewModel != null) viewModel.onPause();
    }

    /**
     * This method must be called from {@link android.app.Activity#onDestroy()}
     * or {@link android.support.v4.app.Fragment#onDestroy()}.
     */
    @CallSuper
    public void onDestroy() {
        if (viewModel == null) return;

        if (isFinished()) {
            viewModel.onDestroy();
            viewModel = null;
        }
    }

    protected abstract boolean isFinished();

    @Nullable
    protected abstract S getCachedViewModel();

    @NonNull
    protected abstract T createDataBinding(@LayoutRes int layoutResource);

    protected S initViewModel() {
        S viewModel = getCachedViewModel();
        if (viewModel == null) {
            viewModel = callback.createViewModel();
            viewModel.onCreate();
        }
        this.viewModel = viewModel;
        return viewModel;
    }

    protected T initBinding(BindingConfig bindingConfig) {
        T binding = createDataBinding(bindingConfig.getLayoutResource());
        if (!binding.setVariable(bindingConfig.getViewModelVariableName(), this.viewModel)) {
            throw new IllegalArgumentException(
                    String.format(BINDING_VARIABLE_ERROR_TEXT,
                            binding.getClass().getSimpleName()
                    ));
        }
        return binding;
    }
}
