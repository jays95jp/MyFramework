package com.kevadiyakrunalk.customfont;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

/**
 * The type Custom font typeface span.
 */
public class CustomFontTypefaceSpan extends MetricAffectingSpan {
    private final Typeface typeface;

    /**
     * Instantiates a new Custom font typeface span.
     *
     * @param typeface the typeface
     */
    public CustomFontTypefaceSpan(final Typeface typeface) {
        if (typeface == null) {
            throw new IllegalArgumentException("typeface is null");
        }

        this.typeface = typeface;
    }

    @Override
    public void updateDrawState(final TextPaint drawState) {
        apply(drawState);
    }

    @Override
    public void updateMeasureState(final TextPaint paint) {
        apply(paint);
    }

    private void apply(final Paint paint) {
        final Typeface oldTypeface = paint.getTypeface();
        final int oldStyle = oldTypeface != null ? oldTypeface.getStyle() : 0;
        final int fakeStyle = oldStyle & ~typeface.getStyle();

        if ((fakeStyle & Typeface.BOLD) != 0) {
            paint.setFakeBoldText(true);
        }

        if ((fakeStyle & Typeface.ITALIC) != 0) {
            paint.setTextSkewX(-0.25f);
        }

        paint.setTypeface(typeface);
    }
}
