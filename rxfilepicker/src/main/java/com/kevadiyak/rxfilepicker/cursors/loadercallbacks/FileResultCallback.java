package com.kevadiyak.rxfilepicker.cursors.loadercallbacks;

import java.util.List;

/**
 * The interface File result callback.
 *
 * @param <T> the type parameter
 */
public interface FileResultCallback<T> {
    /**
     * On result callback.
     *
     * @param files the files
     */
    void onResultCallback(List<T> files);
}