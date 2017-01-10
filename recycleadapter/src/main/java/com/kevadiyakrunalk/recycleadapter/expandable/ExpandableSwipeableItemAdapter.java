package com.kevadiyakrunalk.recycleadapter.expandable;

import android.support.v7.widget.RecyclerView;
import com.kevadiyakrunalk.recycleadapter.swipeable.action.SwipeResultAction;

public interface ExpandableSwipeableItemAdapter<GVH extends RecyclerView.ViewHolder, CVH extends RecyclerView.ViewHolder>
    extends BaseExpandableSwipeableItemAdapter<GVH, CVH> {

    SwipeResultAction onSwipeGroupItem(GVH holder, int groupPosition, int result);

    SwipeResultAction onSwipeChildItem(CVH holder, int groupPosition, int childPosition, int result);
}
