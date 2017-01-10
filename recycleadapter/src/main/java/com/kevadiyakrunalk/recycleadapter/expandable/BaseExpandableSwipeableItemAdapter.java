package com.kevadiyakrunalk.recycleadapter.expandable;

import android.support.v7.widget.RecyclerView;

import com.kevadiyakrunalk.recycleadapter.swipeable.annotation.SwipeableItemDrawableTypes;
import com.kevadiyakrunalk.recycleadapter.swipeable.annotation.SwipeableItemReactions;


public interface BaseExpandableSwipeableItemAdapter<GVH extends RecyclerView.ViewHolder, CVH extends RecyclerView.ViewHolder> {
    @SwipeableItemReactions
    int onGetGroupItemSwipeReactionType(GVH holder, int groupPosition, int x, int y);

    @SwipeableItemReactions
    int onGetChildItemSwipeReactionType(CVH holder, int groupPosition, int childPosition, int x, int y);

    void onSetGroupItemSwipeBackground(GVH holder, int groupPosition, @SwipeableItemDrawableTypes int type);

    void onSetChildItemSwipeBackground(CVH holder, int groupPosition, int childPosition, @SwipeableItemDrawableTypes int type);
}
