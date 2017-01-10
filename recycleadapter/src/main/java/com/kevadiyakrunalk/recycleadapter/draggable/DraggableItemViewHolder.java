package com.kevadiyakrunalk.recycleadapter.draggable;

import com.kevadiyakrunalk.recycleadapter.draggable.annotation.DraggableItemStateFlags;

/**
 * Interface which provides required information for dragging item.
 *
 * Implement this interface on your sub-class of the {@link android.support.v7.widget.RecyclerView.ViewHolder}.
 */
public interface DraggableItemViewHolder {
    void setDragStateFlags(@DraggableItemStateFlags int flags);

    @DraggableItemStateFlags
    int getDragStateFlags();
}
