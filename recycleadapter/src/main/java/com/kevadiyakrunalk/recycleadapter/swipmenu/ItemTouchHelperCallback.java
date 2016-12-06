package com.kevadiyakrunalk.recycleadapter.swipmenu;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kevadiyakrunalk.recycleadapter.RxBinderAdapter;

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
            RxBinderAdapter adapter = (RxBinderAdapter) recyclerView.getAdapter();
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
        RxBinderAdapter.ViewHolder holder = null;
        if(viewHolder instanceof RxBinderAdapter.ViewHolder)
            holder = (RxBinderAdapter.ViewHolder) viewHolder;

        if (holder != null) {
            View view = null;
            if(direction == ItemTouchHelperExtension.START) {
                if(holder.getStartContentView() != null) {
                    view = holder.getStartContentView();
                }
            } else {
                if(holder.getEndContentView() != null) {
                    view = holder.getEndContentView();
                }
            }

            if (view != null && dX < -view.getWidth())
                dX = -view.getWidth();

            View contentView = holder.getContentView();
            if(contentView != null)
                contentView.setTranslationX(dX);
        }
    }
}
