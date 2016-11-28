package com.kevadiyakrunalk.customfont.util;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;

import com.kevadiyakrunalk.customfont.CustomFontTypefaceSpan;

import java.util.HashMap;
import java.util.Map;


/**
 * The type Typeface utils.
 */
public final class TypefaceUtils {

    private static final Map<String, Typeface> sCachedFonts = new HashMap<String, Typeface>();
    private static final Map<Typeface, CustomFontTypefaceSpan> sCachedSpans = new HashMap<Typeface, CustomFontTypefaceSpan>();

    /**
     * Load typeface.
     *
     * @param assetManager the asset manager
     * @param filePath     the file path
     * @return the typeface
     */
    public static Typeface load(final AssetManager assetManager, final String filePath) {
        synchronized (sCachedFonts) {
            try {
                if (!sCachedFonts.containsKey(filePath)) {
                    final Typeface typeface = Typeface.createFromAsset(assetManager, filePath);
                    sCachedFonts.put(filePath, typeface);
                    return typeface;
                }
            } catch (Exception e) {
                Log.w("Calligraphy", "Can't create asset from " + filePath + ". Make sure you have passed in the correct path and file name.", e);
                sCachedFonts.put(filePath, null);
                return null;
            }
            return sCachedFonts.get(filePath);
        }
    }

    /**
     * Gets span.
     *
     * @param typeface the typeface
     * @return the span
     */
    public static CustomFontTypefaceSpan getSpan(final Typeface typeface) {
        if (typeface == null) return null;
        synchronized (sCachedSpans) {
            if (!sCachedSpans.containsKey(typeface)) {
                final CustomFontTypefaceSpan span = new CustomFontTypefaceSpan(typeface);
                sCachedSpans.put(typeface, span);
                return span;
            }
            return sCachedSpans.get(typeface);
        }
    }

    /**
     * Is loaded boolean.
     *
     * @param typeface the typeface
     * @return the boolean
     */
    public static boolean isLoaded(Typeface typeface) {
        return typeface != null && sCachedFonts.containsValue(typeface);
    }

    private TypefaceUtils() {
    }
}
