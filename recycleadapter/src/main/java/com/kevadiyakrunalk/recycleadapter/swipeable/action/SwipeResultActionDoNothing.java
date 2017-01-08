package com.kevadiyakrunalk.recycleadapter.swipeable.action;

import com.kevadiyakrunalk.recycleadapter.swipeable.RecyclerViewSwipeManager;

public class SwipeResultActionDoNothing extends SwipeResultAction {
    public SwipeResultActionDoNothing() {
        super(RecyclerViewSwipeManager.AFTER_SWIPE_REACTION_DO_NOTHING);
    }
}
