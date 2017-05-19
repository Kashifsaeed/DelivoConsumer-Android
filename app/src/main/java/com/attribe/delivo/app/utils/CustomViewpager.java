package com.attribe.delivo.app.utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Author: Uzair Qureshi
 * Date:  5/19/17.
 * Description:
 */

public class CustomViewpager extends ViewPager {
    public CustomViewpager(Context context) {
        super(context);
    }

    public CustomViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //return super.onTouchEvent(ev);
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }


}
