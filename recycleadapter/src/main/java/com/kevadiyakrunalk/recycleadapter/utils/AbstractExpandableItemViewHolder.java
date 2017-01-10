package com.kevadiyakrunalk.recycleadapter.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kevadiyakrunalk.recycleadapter.expandable.ExpandableItemViewHolder;
import com.kevadiyakrunalk.recycleadapter.expandable.annotation.ExpandableItemStateFlags;

public abstract class AbstractExpandableItemViewHolder extends RecyclerView.ViewHolder implements ExpandableItemViewHolder {
    @ExpandableItemStateFlags
    private int mExpandStateFlags;

    public AbstractExpandableItemViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setExpandStateFlags(@ExpandableItemStateFlags int flags) {
        mExpandStateFlags = flags;
    }

    @Override
    @ExpandableItemStateFlags
    public int getExpandStateFlags() {
        return mExpandStateFlags;
    }
}
