package com.kevadiyakrunalk.recycleadapter.expandable;

import com.kevadiyakrunalk.recycleadapter.draggable.ItemDraggableRange;

public class GroupPositionItemDraggableRange extends ItemDraggableRange {
    public GroupPositionItemDraggableRange(int start, int end) {
        super(start, end);
    }

    protected String getClassName() {
        return "GroupPositionItemDraggableRange";
    }
}
