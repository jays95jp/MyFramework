package com.kevadiyak.customfont;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.kevadiyak.customfont.factory.CustomFontActivityFactory;

/**
 * The type Custom font context wrapper.
 */
public class CustomFontContextWrapper extends ContextWrapper {

    private CustomFontLayoutInflater mInflater;

    private final int mAttributeId;

    /**
     * Wrap context wrapper.
     *
     * @param base the base
     * @return the context wrapper
     */
    public static ContextWrapper wrap(Context base) {
        return new CustomFontContextWrapper(base);
    }

    /**
     * On activity create view view.
     *
     * @param activity the activity
     * @param parent   the parent
     * @param view     the view
     * @param name     the name
     * @param context  the context
     * @param attr     the attr
     * @return the view
     */
    public static View onActivityCreateView(Activity activity, View parent, View view, String name, Context context, AttributeSet attr) {
        return get(activity).onActivityCreateView(parent, view, name, context, attr);
    }

    /**
     * Get custom font activity factory.
     *
     * @param activity the activity
     * @return the custom font activity factory
     */
    static CustomFontActivityFactory get(Activity activity) {
        if (!(activity.getLayoutInflater() instanceof CustomFontLayoutInflater)) {
            throw new RuntimeException("This activity does not wrap the Base Context! See CustomFontContextWrapper.wrap(Context)");
        }
        return (CustomFontActivityFactory) activity.getLayoutInflater();
    }

    /**
     * Instantiates a new Custom font context wrapper.
     *
     * @param base the base
     */
    CustomFontContextWrapper(Context base) {
        super(base);
        mAttributeId = CustomFontConfig.get().getAttrId();
    }

    /**
     * Instantiates a new Custom font context wrapper.
     *
     * @param base        the base
     * @param attributeId the attribute id
     */
    @Deprecated
    public CustomFontContextWrapper(Context base, int attributeId) {
        super(base);
        mAttributeId = attributeId;
    }

    @Override
    public Object getSystemService(String name) {
        if (LAYOUT_INFLATER_SERVICE.equals(name)) {
            if (mInflater == null) {
                mInflater = new CustomFontLayoutInflater(LayoutInflater.from(getBaseContext()), this, mAttributeId, false);
            }
            return mInflater;
        }
        return super.getSystemService(name);
    }
}
