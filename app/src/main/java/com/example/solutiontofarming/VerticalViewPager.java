package com.example.solutiontofarming;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class VerticalViewPager extends ViewPager {
    public VerticalViewPager(@NonNull Context context) {
        super(context);
    }

    public VerticalViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setPageTransformer(true, new VerticalPageTransfromer());
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    private class VerticalPageTransfromer implements ViewPager.PageTransformer{

        @Override
        public void transformPage(@NonNull View page, float position) {

            //Check Positions...
            if(position < -1){
                // [-infinity, -1]

                page.setAlpha(0);
            }
            else if(position <= 0){
                //[-1, 0]
                page.setAlpha(1);

                page.setTranslationX(page.getWidth() * -position);

                // Y Position to swipe it from top
                float yPosition = position * page.getHeight();
                page.setTranslationY(yPosition);
                page.setScaleX(1);
                page.setScaleY(1);
            }
            else if(position <= 1){
                //[0, 1]
                page.setTranslationX(page.getWidth() * -position);

                //To scale the page down
                float scale = 0.75f + (1 - 0.75f) *  (1 - Math.abs(position));
                page.setScaleX(scale);
                page.setScaleY(scale);
            }
            else{
                //[1, infinity]
                page.setAlpha(0);
            }
        }
    }

    private MotionEvent swapXYCordinates(MotionEvent event){

        float width = getWidth();
        float height = getHeight();

        float newX = (event.getY() / height) * width;
        float newY = (event.getX() / width) * height;

        event.setLocation(newX, newY);
        return event;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = super.onInterceptTouchEvent(swapXYCordinates(ev));
        swapXYCordinates(ev); // return touch coordinates to original reference frame for any child value.

        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapXYCordinates(ev));
    }
}
