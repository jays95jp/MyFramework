package com.kevadiyakrunalk.recycleadapter.expandable;

import com.kevadiyakrunalk.recycleadapter.draggable.ItemDraggableRange;

public class ChildPositionItemDraggableRange extends ItemDraggableRange {
    public ChildPositionItemDraggableRange(int start, int end) {
        super(start, end);
    }

    protected String getClassName() {
        return "ChildPositionItemDraggableRange";
    }
}
