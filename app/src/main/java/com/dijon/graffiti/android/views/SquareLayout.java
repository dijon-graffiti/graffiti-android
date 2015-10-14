package com.dijon.graffiti.android.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class SquareLayout extends LinearLayout {

    public SquareLayout(Context context) {
        super(context);
    }

    public SquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int height = View.MeasureSpec.getSize(heightMeasureSpec);
//        int mScale = 1;
//        if (width < (int) (mScale * height + 0.5)) {
//            width = (int) (mScale * height + 0.5);
//        } else {
//            height = (int) (width / mScale + 0.5);
//        }
        int size = width > height ? height : width;
        setMeasuredDimension(size, size);
        int finalMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        super.onMeasure(finalMeasureSpec, finalMeasureSpec);
    }
}