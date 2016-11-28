package com.kevadiyakrunalk.mvvmarchitecture.delegates;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;

import com.kevadiyakrunalk.mvvmarchitecture.common.MvvmViewModel;

/**
 * A internal class responsible to save ViewModel instances during screen orientation changes.
 */
class ViewModelCache {

    private static final String FRAGMENT_TAG = "io.github.azaiats.androidmvvm.core.delegates.ViewModelCacheFragment";
    private ViewModelCacheFragment cacheFragment = null;

    /**
     * Get a spare key for cache
     *
     * @param context the context
     * @return a spare key
     */
    int getSpareKey(@NonNull Context context) {
        return getFragment(context).getSpareKey();
    }

    /**
     * Get a ViewModel from cache
     *
     * @param key     the key
     * @param context the context
     * @return the cached ViewModel or <code>null</code> if no one found for the given key.
     */
    public MvvmViewModel getViewModel(int key, @NonNull Context context) {
        return getFragment(context).get(key);
    }

    /**
     * Put a ViewModel into the cache
     *
     * @param key            the key
     * @param cacheViewModel the cache ViewModel
     * @param context        the context
     */
    public void putViewModel(int key, MvvmViewModel cacheViewModel, Context context) {
        getFragment(context).put(key, cacheViewModel);
    }

    /**
     * Remove a ViewModel from the specified key
     *
     * @param key     the key
     * @param context the context
     */
    public void removeViewModel(int key, Context context) {
        getFragment(context).remove(key);
    }

    /**
     * Call on destroy to avoid memory leaks
     */
    public void cleanUp() {
        cacheFragment = null;
    }

    private ViewModelCacheFragment getFragment(Context context) {
        if (cacheFragment != null) return cacheFragment;

        final Activity activity = getActivity(context);

        final ViewModelCacheFragment fragment = (ViewModelCacheFragment) activity.getFragmentManager()
                .findFragmentByTag(FRAGMENT_TAG);

        // Existing Fragment found
        if (fragment != null) {
            cacheFragment = fragment;
            return fragment;
        }

        // No Fragment found, so create a new one
        cacheFragment = new ViewModelCacheFragment();
        activity.getFragmentManager().beginTransaction().add(cacheFragment, FRAGMENT_TAG).commit();
        return cacheFragment;
    }

    private Activity getActivity(Context context) {
        Context wrapper = context;
        while (wrapper instanceof ContextWrapper) {
            if (wrapper instanceof Activity) return (Activity) wrapper;
            wrapper = ((ContextWrapper) wrapper).getBaseContext();
        }
        throw new IllegalStateException("Could not find the surrounding FragmentActivity. Does your activity extends "
                + "from android.support.v4.app.FragmentActivity like android.support.v7.app.AppCompatActivity ?");
    }

    /**
     * Fragment used to cache ViewModel on screen orientation changes
     *
     * @author Andrei Zaiats
     * @since 0.2.0
     */
    public static final class ViewModelCacheFragment extends Fragment {
        private SparseArrayCompat<MvvmViewModel> cache = new SparseArrayCompat<>();
        private int currentKey = 0;

        /**
         * Get a ViewModel from cache
         *
         * @param key the key
         * @return the cached ViewModel or <code>null</code> if no one found for the given key.
         */
        @Nullable
        MvvmViewModel get(int key) {
            return cache.get(key);
        }

        /**
         * Put a ViewModel into the cache
         *
         * @param key            the key
         * @param cacheViewModel the cache ViewModel
         */
        void put(int key, MvvmViewModel cacheViewModel) {
            cache.put(key, cacheViewModel);
        }

        /**
         * Remove a ViewModel from the specified key
         *
         * @param key the key
         */
        void remove(int key) {
            if (cache != null) {
                cache.remove(key);
            }
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        @Override
        public void onDestroy() {
            cache.clear();
            cache = null;
            super.onDestroy();
        }

        /**
         * Get a spare key for cache
         *
         * @return a spare key
         */
        int getSpareKey() {
            while (cache.get(++currentKey) != null) {
            }
            return currentKey;
        }
    }
}
