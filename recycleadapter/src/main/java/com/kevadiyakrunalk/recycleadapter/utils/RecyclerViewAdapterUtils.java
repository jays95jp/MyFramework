package com.kevadiyakrunalk.recycleadapter.utils;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewParent;

public class RecyclerViewAdapterUtils {
    private RecyclerViewAdapterUtils() {
    }

    /**
     * Gets parent RecyclerView instance.
     * @param view Child view of the RecyclerView's item
     * @return Parent RecyclerView instance
     */
    @Nullable
    public static RecyclerView getParentRecyclerView(@Nullable View view) {
        if (view == null) {
            return null;
        }
        ViewParent parent = view.getParent();
        if (parent instanceof RecyclerView) {
            return (RecyclerView) parent;
        } else if (parent instanceof View) {
            return getParentRecyclerView((View) parent);
        } else {
            return null;
        }
    }

    /**
     * Gets directly child of RecyclerView (== {@link RecyclerView.ViewHolder#itemView}})
     * @param view Child view of the RecyclerView's item
     * @return Item view
     */
    @Nullable
    public static View getParentViewHolderItemView(@Nullable View view) {
        RecyclerView rv = getParentRecyclerView(view);
        if (rv == null) {
            return null;
        }
        return rv.findContainingItemView(view);
    }

    /**
     * Gets {@link RecyclerView.ViewHolder}.
     * @param view Child view of the RecyclerView's item
     * @return ViewHolder
     */
    @Nullable
    public static RecyclerView.ViewHolder getViewHolder(@Nullable View view) {
        RecyclerView rv = getParentRecyclerView(view);
        if (rv == null) {
            return null;
        }
        return rv.findContainingViewHolder(view);
    }
}
