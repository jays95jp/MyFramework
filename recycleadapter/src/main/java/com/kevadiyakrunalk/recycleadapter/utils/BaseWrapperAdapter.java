package com.kevadiyakrunalk.recycleadapter.utils;

import android.support.v7.widget.RecyclerView;

import com.kevadiyakrunalk.recycleadapter.adapter.SimpleWrapperAdapter;

/**
 * This class exists just for compatibility purpose and it will be deprecated soon. Use {@link SimpleWrapperAdapter} directly.
 * @param <VH> ViewHolder type
 */
// @Deprecated
public class BaseWrapperAdapter<VH extends RecyclerView.ViewHolder> extends SimpleWrapperAdapter<VH> {
    public BaseWrapperAdapter(RecyclerView.Adapter<VH> adapter) {
        super(adapter);
    }
}
