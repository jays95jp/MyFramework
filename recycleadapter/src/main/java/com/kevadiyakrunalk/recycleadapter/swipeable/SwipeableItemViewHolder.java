package com.kevadiyakrunalk.recycleadapter.swipeable;

import android.support.annotation.Nullable;
import android.view.View;

import com.kevadiyakrunalk.recycleadapter.swipeable.annotation.SwipeableItemAfterReactions;

/**
 * Interface which provides required information for swiping item.
 * <p>
 * Implement this interface on your sub-class of the {@link android.support.v7.widget.RecyclerView.ViewHolder}.
 */
public interface SwipeableItemViewHolder {
    void setSwipeStateFlags(int flags);

    int getSwipeStateFlags();

    void setSwipeResult(int result);

    int getSwipeResult();

    void setAfterSwipeReaction(@SwipeableItemAfterReactions int reaction);

    @SwipeableItemAfterReactions
    int getAfterSwipeReaction();

    void setProportionalSwipeAmountModeEnabled(boolean enabled);

    boolean isProportionalSwipeAmountModeEnabled();

    void setSwipeItemHorizontalSlideAmount(float amount);

    float getSwipeItemHorizontalSlideAmount();

    void setSwipeItemVerticalSlideAmount(float amount);

    float getSwipeItemVerticalSlideAmount();

    void setMaxLeftSwipeAmount(float amount);

    float getMaxLeftSwipeAmount();

    void setMaxUpSwipeAmount(float amount);

    float getMaxUpSwipeAmount();

    void setMaxRightSwipeAmount(float amount);

    float getMaxRightSwipeAmount();

    void setMaxDownSwipeAmount(float amount);

    float getMaxDownSwipeAmount();

    @Nullable
    View getSwipeableContainerView();

    void onSlideAmountUpdated(float horizontalAmount, float verticalAmount, boolean isSwiping);
}
