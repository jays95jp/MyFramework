package com.kevadiyak.customfont.factory;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * The interface Custom font activity factory.
 */
public interface CustomFontActivityFactory {

    /**
     * On activity create view view.
     *
     * @param parent  the parent
     * @param view    the view
     * @param name    the name
     * @param context the context
     * @param attrs   the attrs
     * @return the view
     */
    View onActivityCreateView(View parent, View view, String name, Context context, AttributeSet attrs);
}
