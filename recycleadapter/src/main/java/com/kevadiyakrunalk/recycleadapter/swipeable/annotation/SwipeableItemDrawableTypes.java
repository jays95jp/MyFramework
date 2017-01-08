package com.kevadiyakrunalk.recycleadapter.swipeable.annotation;

import android.support.annotation.IntDef;

import com.kevadiyakrunalk.recycleadapter.swipeable.SwipeableItemConstants;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef(flag = false, value = {
        SwipeableItemConstants.DRAWABLE_SWIPE_NEUTRAL_BACKGROUND,
        SwipeableItemConstants.DRAWABLE_SWIPE_LEFT_BACKGROUND,
        SwipeableItemConstants.DRAWABLE_SWIPE_UP_BACKGROUND,
        SwipeableItemConstants.DRAWABLE_SWIPE_RIGHT_BACKGROUND,
        SwipeableItemConstants.DRAWABLE_SWIPE_DOWN_BACKGROUND,
})
@Retention(RetentionPolicy.SOURCE)
public @interface SwipeableItemDrawableTypes {
}
