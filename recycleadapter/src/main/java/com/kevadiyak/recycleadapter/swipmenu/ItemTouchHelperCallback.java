
package com.kevadiyak.recycleadapter.swipmenu;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.kevadiyak.recycleadapter.CustomBindAdapter;

public class ItemTouchHelperCallback extends ItemTouchHelperExtension.Callback {
    private int direction;
    private int dragDirection;
    private int swipDirection;

    public ItemTouchHelperCallback(int dDirection, int sDirection) {
        dragDirection = dDirection;
        swipDirection = sDirection;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(dragDirection, swipDirection);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        if(source.getItemViewType() == target.getItemViewType()) {
            CustomBindAdapter adapter = (CustomBindAdapter) recyclerView.getAdapter();
            adapter.move(source.getAdapterPosition(), target.getAdapterPosition());
        }
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        this.direction = direction;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        if(dragDirection != 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        if(swipDirection != 0)
            return true;
        else
            return false;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        CustomBindAdapter.ViewHolder holder = null;
        if(viewHolder instanceof CustomBindAdapter.ViewHolder)
            holder = (CustomBindAdapter.ViewHolder) viewHolder;

        if (holder != null) {
            View view = null;
            if(direction == ItemTouchHelperExtension.START) {
                if(holder.getStartContentView() != null) {
                    view = holder.getStartContentView();
                    /*if(view != null && view.getVisibility() == View.INVISIBLE)
                        view.setVisibility(View.VISIBLE);*/
                }
                /*if(holder.getEndContentView() != null && holder.getEndContentView().getVisibility() == View.VISIBLE)
                    holder.getEndContentView().setVisibility(View.INVISIBLE);*/
            } else {
                if(holder.getEndContentView() != null) {
                    view = holder.getEndContentView();
                    /*if(view != null && view.getVisibility() == View.INVISIBLE)
                        view.setVisibility(View.VISIBLE);*/
                }
                /*if(holder.getStartContentView() != null && holder.getStartContentView().getVisibility() == View.VISIBLE)
                    holder.getStartContentView().setVisibility(View.INVISIBLE);*/
            }

            if (view != null && dX < -view.getWidth())
                dX = -view.getWidth();

            View contentView = holder.getContentView();
            if(contentView != null)
                contentView.setTranslationX(dX);
        }
    }
}
