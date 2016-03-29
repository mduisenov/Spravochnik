/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.widget.FrameLayout
 */
package kz.test.spravochnik.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import kz.test.spravochnik.R;


public class LimitedWidthFrameLayout extends FrameLayout {
    private final int boundedWidth;

    public LimitedWidthFrameLayout(Context context) {
        super(context);
        this.boundedWidth = 0;
    }

    public LimitedWidthFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LimitedWidthFrameLayout);
        this.boundedWidth = a.getDimensionPixelSize(R.styleable.LimitedWidthFrameLayout_max_width, 0);
        a.recycle();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMeasureSpecLocal = widthMeasureSpec;
        if (this.boundedWidth > 0) {
            widthMeasureSpecLocal = widthMeasureSpec;
            if (this.boundedWidth < width) {
                widthMeasureSpec = MeasureSpec.getMode(widthMeasureSpec);
                widthMeasureSpecLocal = MeasureSpec.makeMeasureSpec(this.boundedWidth, widthMeasureSpec);
            }
        }
        super.onMeasure(widthMeasureSpecLocal, heightMeasureSpec);
    }
}

