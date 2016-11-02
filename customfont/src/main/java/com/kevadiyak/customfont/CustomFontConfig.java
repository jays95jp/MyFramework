package com.kevadiyak.customfont;

import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.kevadiyak.customfont.R;
import com.kevadiyak.customfont.util.CustomFontUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The type Custom font config.
 */
public class CustomFontConfig {

    private static final Map<Class<? extends TextView>, Integer> DEFAULT_STYLES = new HashMap<>();

    static {
        {
            DEFAULT_STYLES.put(TextView.class, android.R.attr.textViewStyle);
            DEFAULT_STYLES.put(Button.class, android.R.attr.buttonStyle);
            DEFAULT_STYLES.put(EditText.class, android.R.attr.editTextStyle);
            DEFAULT_STYLES.put(AutoCompleteTextView.class, android.R.attr.autoCompleteTextViewStyle);
            DEFAULT_STYLES.put(MultiAutoCompleteTextView.class, android.R.attr.autoCompleteTextViewStyle);
            DEFAULT_STYLES.put(CheckBox.class, android.R.attr.checkboxStyle);
            DEFAULT_STYLES.put(RadioButton.class, android.R.attr.radioButtonStyle);
            DEFAULT_STYLES.put(ToggleButton.class, android.R.attr.buttonStyleToggle);
            if (CustomFontUtils.canAddV7AppCompatViews()) {
                addAppCompatViews();
            }
        }
    }

    private static void addAppCompatViews() {
        DEFAULT_STYLES.put(android.support.v7.widget.AppCompatTextView.class, android.R.attr.textViewStyle);
        DEFAULT_STYLES.put(android.support.v7.widget.AppCompatButton.class, android.R.attr.buttonStyle);
        DEFAULT_STYLES.put(android.support.v7.widget.AppCompatEditText.class, android.R.attr.editTextStyle);
        DEFAULT_STYLES.put(android.support.v7.widget.AppCompatAutoCompleteTextView.class, android.R.attr.autoCompleteTextViewStyle);
        DEFAULT_STYLES.put(android.support.v7.widget.AppCompatMultiAutoCompleteTextView.class, android.R.attr.autoCompleteTextViewStyle);
        DEFAULT_STYLES.put(android.support.v7.widget.AppCompatCheckBox.class, android.R.attr.checkboxStyle);
        DEFAULT_STYLES.put(android.support.v7.widget.AppCompatRadioButton.class, android.R.attr.radioButtonStyle);
        DEFAULT_STYLES.put(android.support.v7.widget.AppCompatCheckedTextView.class, android.R.attr.checkedTextViewStyle);
    }

    private static CustomFontConfig sInstance;

    /**
     * Init default.
     *
     * @param customFontConfig the custom font config
     */
    public static void initDefault(CustomFontConfig customFontConfig) {
        sInstance = customFontConfig;
    }

    /**
     * Get custom font config.
     *
     * @return the custom font config
     */
    public static CustomFontConfig get() {
        if (sInstance == null)
            sInstance = new CustomFontConfig(new Builder());
        return sInstance;
    }

    private final boolean mIsFontSet;
    private final String mFontPath;
    private final int mAttrId;
    private final boolean mReflection;
    private final boolean mCustomViewCreation;
    private final boolean mCustomViewTypefaceSupport;
    private final Map<Class<? extends TextView>, Integer> mClassStyleAttributeMap;
    private final Set<Class<?>> hasTypefaceViews;

    /**
     * Instantiates a new Custom font config.
     *
     * @param builder the builder
     */
    protected CustomFontConfig(Builder builder) {
        mIsFontSet = builder.isFontSet;
        mFontPath = builder.fontAssetPath;
        mAttrId = builder.attrId;
        mReflection = builder.reflection;
        mCustomViewCreation = builder.customViewCreation;
        mCustomViewTypefaceSupport = builder.customViewTypefaceSupport;
        final Map<Class<? extends TextView>, Integer> tempMap = new HashMap<>(DEFAULT_STYLES);
        tempMap.putAll(builder.mStyleClassMap);
        mClassStyleAttributeMap = Collections.unmodifiableMap(tempMap);
        hasTypefaceViews = Collections.unmodifiableSet(builder.mHasTypefaceClasses);
    }

    /**
     * Gets font path.
     *
     * @return mFontPath for text views might be null
     */
    public String getFontPath() {
        return mFontPath;
    }

    /**
     * Is font set boolean.
     *
     * @return true if set, false if null|empty
     */
    public boolean isFontSet() {
        return mIsFontSet;
    }

    /**
     * Is reflection boolean.
     *
     * @return the boolean
     */
    public boolean isReflection() {
        return mReflection;
    }

    /**
     * Is custom view creation boolean.
     *
     * @return the boolean
     */
    public boolean isCustomViewCreation() {
        return mCustomViewCreation;
    }

    /**
     * Is custom view typeface support boolean.
     *
     * @return the boolean
     */
    public boolean isCustomViewTypefaceSupport() {
        return mCustomViewTypefaceSupport;
    }

    /**
     * Is custom view has typeface boolean.
     *
     * @param view the view
     * @return the boolean
     */
    public boolean isCustomViewHasTypeface(View view) {
        return hasTypefaceViews.contains(view.getClass());
    }

    /**
     * Gets class styles.
     *
     * @return the class styles
     */
/* default */
    public Map<Class<? extends TextView>, Integer> getClassStyles() {
        return mClassStyleAttributeMap;
    }

    /**
     * Gets attr id.
     *
     * @return the custom attrId to look for, -1 if not set.
     */
    public int getAttrId() {
        return mAttrId;
    }

    /**
     * The type Builder.
     */
    public static class Builder {
        /**
         * Default AttrID if not set.
         */
        public static final int INVALID_ATTR_ID = -1;
        /**
         * Use Reflection to inject the private factory. Doesn't exist pre HC. so defaults to false.
         */
        private boolean reflection = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
        /**
         * Use Reflection to intercept CustomView inflation with the correct Context.
         */
        private boolean customViewCreation = true;
        /**
         * Use Reflection during view creation to try change typeface via setTypeface method if it exists
         */
        private boolean customViewTypefaceSupport = false;
        /**
         * The fontAttrId to look up the font path from.
         */
        private int attrId = R.attr.fontPath;
        /**
         * Has the user set the default font path.
         */
        private boolean isFontSet = false;
        /**
         * The default fontPath
         */
        private String fontAssetPath = null;
        /**
         * Additional Class Styles. Can be empty.
         */
        private Map<Class<? extends TextView>, Integer> mStyleClassMap = new HashMap<>();

        private Set<Class<?>> mHasTypefaceClasses = new HashSet<>();

        /**
         * This defaults to R.attr.fontPath. So only override if you want to use your own attrId.
         *
         * @param fontAssetAttrId the custom attribute to look for fonts in assets.
         * @return this builder.
         */
        public Builder setFontAttrId(int fontAssetAttrId) {
            this.attrId = fontAssetAttrId;
            return this;
        }

        /**
         * Set the default font if you don't define one else where in your styles.
         *
         * @param defaultFontAssetPath a path to a font file in the assets folder, e.g. "fonts/Roboto-light.ttf",                             passing null will default to the device font-family.
         * @return this builder.
         */
        public Builder setDefaultFontPath(String defaultFontAssetPath) {
            this.isFontSet = !TextUtils.isEmpty(defaultFontAssetPath);
            this.fontAssetPath = defaultFontAssetPath;
            return this;
        }

        /**
         * Disable private factory injection builder.
         *
         * @return the builder
         */
        public Builder disablePrivateFactoryInjection() {
            this.reflection = false;
            return this;
        }

        /**
         * Disable custom view inflation builder.
         *
         * @return the builder
         */
        public Builder disableCustomViewInflation() {
            this.customViewCreation = false;
            return this;
        }

        /**
         * Add custom style builder.
         *
         * @param styleClass             the style class
         * @param styleResourceAttribute the style resource attribute
         * @return the builder
         */
        public Builder addCustomStyle(final Class<? extends TextView> styleClass, final int styleResourceAttribute) {
            if (styleClass == null || styleResourceAttribute == 0) return this;
            mStyleClassMap.put(styleClass, styleResourceAttribute);
            return this;
        }

        /**
         * Add custom view with set typeface builder.
         *
         * @param clazz the clazz
         * @return the builder
         */
        public Builder addCustomViewWithSetTypeface(Class<?> clazz) {
            customViewTypefaceSupport = true;
            mHasTypefaceClasses.add(clazz);
            return this;
        }

        /**
         * Build custom font config.
         *
         * @return the custom font config
         */
        public CustomFontConfig build() {
            this.isFontSet = !TextUtils.isEmpty(fontAssetPath);
            return new CustomFontConfig(this);
        }
    }
}
