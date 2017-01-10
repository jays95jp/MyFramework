package com.kevadiyakrunalk.recycleadapter.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

/**
 * Adapter path segment
 */
public class AdapterPathSegment {
    /**
     * Adapter
     */
    public final RecyclerView.Adapter adapter;

    /**
     * Tag object
     */
    public final Object tag;

    /**
     * Constructor.
     *
     * @param adapter The adapter
     * @param tag The tag object
     */
    public AdapterPathSegment(@NonNull RecyclerView.Adapter adapter, @Nullable Object tag) {
        this.adapter = adapter;
        this.tag = tag;
    }
}
