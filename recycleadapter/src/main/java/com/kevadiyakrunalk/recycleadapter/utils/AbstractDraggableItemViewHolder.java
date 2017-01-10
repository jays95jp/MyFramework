package com.kevadiyakrunalk.recycleadapter.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kevadiyakrunalk.recycleadapter.draggable.DraggableItemViewHolder;
import com.kevadiyakrunalk.recycleadapter.draggable.annotation.DraggableItemStateFlags;

public abstract class AbstractDraggableItemViewHolder extends RecyclerView.ViewHolder implements DraggableItemViewHolder {
    @DraggableItemStateFlags
    private int mDragStateFlags;

    public AbstractDraggableItemViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setDragStateFlags(@DraggableItemStateFlags int flags) {
        mDragStateFlags = flags;
    }

    @Override
    @DraggableItemStateFlags
    public int getDragStateFlags() {
        return mDragStateFlags;
    }
}
