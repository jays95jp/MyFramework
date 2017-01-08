package com.kevadiyakrunalk.recycleadapter.swipeable.action;

import com.kevadiyakrunalk.recycleadapter.swipeable.RecyclerViewSwipeManager;

public abstract class SwipeResultActionRemoveItem extends SwipeResultAction {
    public SwipeResultActionRemoveItem() {
        super(RecyclerViewSwipeManager.AFTER_SWIPE_REACTION_REMOVE_ITEM);
    }
}
