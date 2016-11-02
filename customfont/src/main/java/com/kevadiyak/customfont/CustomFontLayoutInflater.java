package com.kevadiyak.customfont;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kevadiyak.customfont.factory.CustomFontActivityFactory;
import com.kevadiyak.customfont.factory.CustomFontFactory;
import com.kevadiyak.customfont.util.ReflectionUtils;

import org.xmlpull.v1.XmlPullParser;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * The type Custom font layout inflater.
 */
class CustomFontLayoutInflater extends LayoutInflater implements CustomFontActivityFactory {

    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.webkit."
    };

    private final int mAttributeId;
    private final CustomFontFactory mCustomFontFactory;
    // Reflection Hax
    private boolean mSetPrivateFactory = false;
    private Field mConstructorArgs = null;

    /**
     * Instantiates a new Custom font layout inflater.
     *
     * @param context     the context
     * @param attributeId the attribute id
     */
    protected CustomFontLayoutInflater(Context context, int attributeId) {
        super(context);
        mAttributeId = attributeId;
        mCustomFontFactory = new CustomFontFactory(attributeId);
        setUpLayoutFactories(false);
    }

    /**
     * Instantiates a new Custom font layout inflater.
     *
     * @param original    the original
     * @param newContext  the new context
     * @param attributeId the attribute id
     * @param cloned      the cloned
     */
    protected CustomFontLayoutInflater(LayoutInflater original, Context newContext, int attributeId, final boolean cloned) {
        super(original, newContext);
        mAttributeId = attributeId;
        mCustomFontFactory = new CustomFontFactory(attributeId);
        setUpLayoutFactories(cloned);
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new CustomFontLayoutInflater(this, newContext, mAttributeId, true);
    }

    @Override
    public View inflate(XmlPullParser parser, ViewGroup root, boolean attachToRoot) {
        setPrivateFactoryInternal();
        return super.inflate(parser, root, attachToRoot);
    }

    private void setUpLayoutFactories(boolean cloned) {
        if (cloned) return;
        // If we are HC+ we get and set Factory2 otherwise we just wrap Factory1
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (getFactory2() != null && !(getFactory2() instanceof WrapperFactory2)) {
                // Sets both Factory/Factory2
                setFactory2(getFactory2());
            }
        }
        // We can do this as setFactory2 is used for both methods.
        if (getFactory() != null && !(getFactory() instanceof WrapperFactory)) {
            setFactory(getFactory());
        }
    }

    @Override
    public void setFactory(Factory factory) {
        // Only set our factory and wrap calls to the Factory trying to be set!
        if (!(factory instanceof WrapperFactory)) {
            super.setFactory(new WrapperFactory(factory, this, mCustomFontFactory));
        } else {
            super.setFactory(factory);
        }
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void setFactory2(Factory2 factory2) {
        // Only set our factory and wrap calls to the Factory2 trying to be set!
        if (!(factory2 instanceof WrapperFactory2)) {
//            LayoutInflaterCompat.setFactory(this, new WrapperFactory2(factory2, mCustomFontFactory));
            super.setFactory2(new WrapperFactory2(factory2, mCustomFontFactory));
        } else {
            super.setFactory2(factory2);
        }
    }

    private void setPrivateFactoryInternal() {
        // Already tried to set the factory.
        if (mSetPrivateFactory) return;
        // Reflection (Or Old Device) skip.
        if (!CustomFontConfig.get().isReflection()) return;
        // Skip if not attached to an activity.
        if (!(getContext() instanceof Factory2)) {
            mSetPrivateFactory = true;
            return;
        }

        final Method setPrivateFactoryMethod = ReflectionUtils
                .getMethod(LayoutInflater.class, "setPrivateFactory");

        if (setPrivateFactoryMethod != null) {
            ReflectionUtils.invokeMethod(this,
                    setPrivateFactoryMethod,
                    new PrivateWrapperFactory2((Factory2) getContext(), this, mCustomFontFactory));
        }
        mSetPrivateFactory = true;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public View onActivityCreateView(View parent, View view, String name, Context context, AttributeSet attrs) {
        return mCustomFontFactory.onViewCreated(createCustomViewInternal(parent, view, name, context, attrs), context, attrs);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected View onCreateView(View parent, String name, AttributeSet attrs) throws ClassNotFoundException {
        return mCustomFontFactory.onViewCreated(super.onCreateView(parent, name, attrs),
                getContext(), attrs);
    }

    @Override
    protected View onCreateView(String name, AttributeSet attrs) throws ClassNotFoundException {
        // This mimics the {@code PhoneLayoutInflater} in the way it tries to inflate the base
        // classes, if this fails its pretty certain the app will fail at this point.
        View view = null;
        for (String prefix : sClassPrefixList) {
            try {
                view = createView(name, prefix, attrs);
            } catch (ClassNotFoundException ignored) {
            }
        }
        // In this case we want to let the base class take a crack
        // at it.
        if (view == null) view = super.onCreateView(name, attrs);

        return mCustomFontFactory.onViewCreated(view, view.getContext(), attrs);
    }

    private View createCustomViewInternal(View parent, View view, String name, Context viewContext, AttributeSet attrs) {
        // If CustomViewCreation is off skip this.
        if (!CustomFontConfig.get().isCustomViewCreation()) return view;
        if (view == null && name.indexOf('.') > -1) {
            if (mConstructorArgs == null)
                mConstructorArgs = ReflectionUtils.getField(LayoutInflater.class, "mConstructorArgs");

            final Object[] mConstructorArgsArr = (Object[]) ReflectionUtils.getValue(mConstructorArgs, this);
            final Object lastContext = mConstructorArgsArr[0];
            // The LayoutInflater actually finds out the correct context to use. We just need to set
            // it on the mConstructor for the internal method.
            // Set the constructor ars up for the createView, not sure why we can't pass these in.
            mConstructorArgsArr[0] = viewContext;
            ReflectionUtils.setValue(mConstructorArgs, this, mConstructorArgsArr);
            try {
                view = createView(name, null, attrs);
            } catch (ClassNotFoundException ignored) {
            } finally {
                mConstructorArgsArr[0] = lastContext;
                ReflectionUtils.setValue(mConstructorArgs, this, mConstructorArgsArr);
            }
        }
        return view;
    }

    private static class WrapperFactory implements Factory {

        private final Factory mFactory;
        private final CustomFontLayoutInflater mInflater;
        private final CustomFontFactory mCustomFontFactory;

        /**
         * Instantiates a new Wrapper factory.
         *
         * @param factory           the factory
         * @param inflater          the inflater
         * @param customFontFactory the calligraphy factory
         */
        public WrapperFactory(Factory factory, CustomFontLayoutInflater inflater, CustomFontFactory customFontFactory) {
            mFactory = factory;
            mInflater = inflater;
            mCustomFontFactory = customFontFactory;
        }

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                return mCustomFontFactory.onViewCreated(
                        mInflater.createCustomViewInternal(
                                null, mFactory.onCreateView(name, context, attrs), name, context, attrs
                        ),
                        context, attrs
                );
            }
            return mCustomFontFactory.onViewCreated(
                    mFactory.onCreateView(name, context, attrs),
                    context, attrs
            );
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static class WrapperFactory2 implements Factory2 {
        /**
         * The M factory 2.
         */
        protected final Factory2 mFactory2;
        /**
         * The M calligraphy factory.
         */
        protected final CustomFontFactory mCustomFontFactory;

        /**
         * Instantiates a new Wrapper factory 2.
         *
         * @param factory2          the factory 2
         * @param customFontFactory the calligraphy factory
         */
        public WrapperFactory2(Factory2 factory2, CustomFontFactory customFontFactory) {
            mFactory2 = factory2;
            mCustomFontFactory = customFontFactory;
        }

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            return mCustomFontFactory.onViewCreated(
                    mFactory2.onCreateView(name, context, attrs),
                    context, attrs);
        }

        @Override
        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            return mCustomFontFactory.onViewCreated(
                    mFactory2.onCreateView(parent, name, context, attrs),
                    context, attrs);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static class PrivateWrapperFactory2 extends WrapperFactory2 {

        private final CustomFontLayoutInflater mInflater;

        /**
         * Instantiates a new Private wrapper factory 2.
         *
         * @param factory2          the factory 2
         * @param inflater          the inflater
         * @param customFontFactory the calligraphy factory
         */
        public PrivateWrapperFactory2(Factory2 factory2, CustomFontLayoutInflater inflater, CustomFontFactory customFontFactory) {
            super(factory2, customFontFactory);
            mInflater = inflater;
        }

        @Override
        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            return mCustomFontFactory.onViewCreated(
                    mInflater.createCustomViewInternal(parent,
                            mFactory2.onCreateView(parent, name, context, attrs),
                            name, context, attrs
                    ),
                    context, attrs
            );
        }
    }
}
