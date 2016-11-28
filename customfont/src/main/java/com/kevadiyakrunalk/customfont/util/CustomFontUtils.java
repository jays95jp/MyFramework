package com.kevadiyakrunalk.customfont.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.kevadiyakrunalk.customfont.CustomFontConfig;

/**
 * The type Calligraphy utils.
 */
public final class CustomFontUtils {

    /**
     * The constant ANDROID_ATTR_TEXT_APPEARANCE.
     */
    public static final int[] ANDROID_ATTR_TEXT_APPEARANCE = new int[]{android.R.attr.textAppearance};

    /**
     * Apply typeface span char sequence.
     *
     * @param s        the s
     * @param typeface the typeface
     * @return the char sequence
     */
    public static CharSequence applyTypefaceSpan(CharSequence s, Typeface typeface) {
        if (s != null && s.length() > 0) {
            if (!(s instanceof Spannable)) {
                s = new SpannableString(s);
            }
            ((Spannable) s).setSpan(TypefaceUtils.getSpan(typeface), 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return s;
    }

    /**
     * Apply font to text view boolean.
     *
     * @param textView the text view
     * @param typeface the typeface
     * @return the boolean
     */
    public static boolean applyFontToTextView(final TextView textView, final Typeface typeface) {
        return applyFontToTextView(textView, typeface, false);
    }

    /**
     * Apply font to text view boolean.
     *
     * @param textView the text view
     * @param typeface the typeface
     * @param deferred the deferred
     * @return the boolean
     */
    public static boolean applyFontToTextView(final TextView textView, final Typeface typeface, boolean deferred) {
        if (textView == null || typeface == null) return false;
        textView.setPaintFlags(textView.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        textView.setTypeface(typeface);
        if (deferred) {
            textView.setText(applyTypefaceSpan(textView.getText(), typeface), TextView.BufferType.SPANNABLE);
            textView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    applyTypefaceSpan(s, typeface);
                }
            });
        }
        return true;
    }

    /**
     * Apply font to text view boolean.
     *
     * @param context  the context
     * @param textView the text view
     * @param filePath the file path
     * @return the boolean
     */
    public static boolean applyFontToTextView(final Context context, final TextView textView, final String filePath) {
        return applyFontToTextView(context, textView, filePath, false);
    }

    /**
     * Apply font to text view boolean.
     *
     * @param context  the context
     * @param textView the text view
     * @param filePath the file path
     * @param deferred the deferred
     * @return the boolean
     */
    public static boolean applyFontToTextView(final Context context, final TextView textView, final String filePath, boolean deferred) {
        if (textView == null || context == null) return false;
        final AssetManager assetManager = context.getAssets();
        final Typeface typeface = TypefaceUtils.load(assetManager, filePath);
        return applyFontToTextView(textView, typeface, deferred);
    }

    /**
     * Apply font to text view.
     *
     * @param context  the context
     * @param textView the text view
     * @param config   the config
     */
    public static void applyFontToTextView(final Context context, final TextView textView, final CustomFontConfig config) {
        applyFontToTextView(context, textView, config, false);
    }

    /**
     * Apply font to text view.
     *
     * @param context  the context
     * @param textView the text view
     * @param config   the config
     * @param deferred the deferred
     */
    public static void applyFontToTextView(final Context context, final TextView textView, final CustomFontConfig config, boolean deferred) {
        if (context == null || textView == null || config == null) return;
        if (!config.isFontSet()) return;
        applyFontToTextView(context, textView, config.getFontPath(), deferred);
    }

    /**
     * Apply font to text view.
     *
     * @param context      the context
     * @param textView     the text view
     * @param config       the config
     * @param textViewFont the text view font
     */
    public static void applyFontToTextView(final Context context, final TextView textView, final CustomFontConfig config, final String textViewFont) {
        applyFontToTextView(context, textView, config, textViewFont, false);
    }

    /**
     * Apply font to text view.
     *
     * @param context      the context
     * @param textView     the text view
     * @param config       the config
     * @param textViewFont the text view font
     * @param deferred     the deferred
     */
    public static void applyFontToTextView(final Context context, final TextView textView, final CustomFontConfig config, final String textViewFont, boolean deferred) {
        if (context == null || textView == null || config == null) return;
        if (!TextUtils.isEmpty(textViewFont) && applyFontToTextView(context, textView, textViewFont, deferred)) {
            return;
        }
        applyFontToTextView(context, textView, config, deferred);
    }

    /**
     * Pull font path from view string.
     *
     * @param context     the context
     * @param attrs       the attrs
     * @param attributeId the attribute id
     * @return the string
     */
    public static String pullFontPathFromView(Context context, AttributeSet attrs, int[] attributeId) {
        if (attributeId == null || attrs == null)
            return null;

        final String attributeName;
        try {
            attributeName = context.getResources().getResourceEntryName(attributeId[0]);
        } catch (Resources.NotFoundException e) {
            // invalid attribute ID
            return null;
        }

        final int stringResourceId = attrs.getAttributeResourceValue(null, attributeName, -1);
        return stringResourceId > 0
                ? context.getString(stringResourceId)
                : attrs.getAttributeValue(null, attributeName);
    }

    /**
     * Pull font path from style string.
     *
     * @param context     the context
     * @param attrs       the attrs
     * @param attributeId the attribute id
     * @return the string
     */
    public static String pullFontPathFromStyle(Context context, AttributeSet attrs, int[] attributeId) {
        if (attributeId == null || attrs == null)
            return null;
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, attributeId);
        if (typedArray != null) {
            try {
                // First defined attribute
                String fontFromAttribute = typedArray.getString(0);
                if (!TextUtils.isEmpty(fontFromAttribute)) {
                    return fontFromAttribute;
                }
            } catch (Exception ignore) {
                // Failed for some reason.
            } finally {
                typedArray.recycle();
            }
        }
        return null;
    }

    /**
     * Pull font path from text appearance string.
     *
     * @param context     the context
     * @param attrs       the attrs
     * @param attributeId the attribute id
     * @return the string
     */
    public static String pullFontPathFromTextAppearance(final Context context, AttributeSet attrs, int[] attributeId) {
        if (attributeId == null || attrs == null) {
            return null;
        }

        int textAppearanceId = -1;
        final TypedArray typedArrayAttr = context.obtainStyledAttributes(attrs, ANDROID_ATTR_TEXT_APPEARANCE);
        if (typedArrayAttr != null) {
            try {
                textAppearanceId = typedArrayAttr.getResourceId(0, -1);
            } catch (Exception ignored) {
                // Failed for some reason
                return null;
            } finally {
                typedArrayAttr.recycle();
            }
        }

        final TypedArray textAppearanceAttrs = context.obtainStyledAttributes(textAppearanceId, attributeId);
        if (textAppearanceAttrs != null) {
            try {
                return textAppearanceAttrs.getString(0);
            } catch (Exception ignore) {
                // Failed for some reason.
                return null;
            } finally {
                textAppearanceAttrs.recycle();
            }
        }
        return null;
    }

    /**
     * Pull font path from theme string.
     *
     * @param context     the context
     * @param styleAttrId the style attr id
     * @param attributeId the attribute id
     * @return the string
     */
    public static String pullFontPathFromTheme(Context context, int styleAttrId, int[] attributeId) {
        if (styleAttrId == -1 || attributeId == null)
            return null;

        final Resources.Theme theme = context.getTheme();
        final TypedValue value = new TypedValue();

        theme.resolveAttribute(styleAttrId, value, true);
        final TypedArray typedArray = theme.obtainStyledAttributes(value.resourceId, attributeId);
        try {
            String font = typedArray.getString(0);
            return font;
        } catch (Exception ignore) {
            // Failed for some reason.
            return null;
        } finally {
            typedArray.recycle();
        }
    }

    /**
     * Pull font path from theme string.
     *
     * @param context        the context
     * @param styleAttrId    the style attr id
     * @param subStyleAttrId the sub style attr id
     * @param attributeId    the attribute id
     * @return the string
     */
    public static String pullFontPathFromTheme(Context context, int styleAttrId, int subStyleAttrId, int[] attributeId) {
        if (styleAttrId == -1 || attributeId == null)
            return null;

        final Resources.Theme theme = context.getTheme();
        final TypedValue value = new TypedValue();

        theme.resolveAttribute(styleAttrId, value, true);
        int subStyleResId = -1;
        final TypedArray parentTypedArray = theme.obtainStyledAttributes(value.resourceId, new int[]{subStyleAttrId});
        try {
            subStyleResId = parentTypedArray.getResourceId(0, -1);
        } catch (Exception ignore) {
            // Failed for some reason.
            return null;
        } finally {
            parentTypedArray.recycle();
        }

        if (subStyleResId == -1) return null;
        final TypedArray subTypedArray = context.obtainStyledAttributes(subStyleResId, attributeId);
        if (subTypedArray != null) {
            try {
                return subTypedArray.getString(0);
            } catch (Exception ignore) {
                // Failed for some reason.
                return null;
            } finally {
                subTypedArray.recycle();
            }
        }
        return null;
    }

    private static Boolean sToolbarCheck = null;
    private static Boolean sAppCompatViewCheck = null;

    /**
     * Can check for v 7 toolbar boolean.
     *
     * @return the boolean
     */
    public static boolean canCheckForV7Toolbar() {
        if (sToolbarCheck == null) {
            try {
                Class.forName("android.support.v7.widget.Toolbar");
                sToolbarCheck = Boolean.TRUE;
            } catch (ClassNotFoundException e) {
                sToolbarCheck = Boolean.FALSE;
            }
        }
        return sToolbarCheck;
    }

    /**
     * Can add v 7 app compat views boolean.
     *
     * @return the boolean
     */
    public static boolean canAddV7AppCompatViews() {
        if (sAppCompatViewCheck == null) {
            try {
                Class.forName("android.support.v7.widget.AppCompatTextView");
                sAppCompatViewCheck = Boolean.TRUE;
            } catch (ClassNotFoundException e) {
                sAppCompatViewCheck = Boolean.FALSE;
            }
        }
        return sAppCompatViewCheck;
    }

    private CustomFontUtils() {
    }
}
