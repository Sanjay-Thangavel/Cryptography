package com.example.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import androidx.appcompat.widget.AppCompatImageView;

public class CircleImageView extends AppCompatImageView {

    private GestureDetector gestureDetector;
    private boolean isAlternateColor = false;
    private int[] colors = {R.color.color1, R.color.color2, R.color.color3};
    private int currentColorIndex = 0;
    public CircleImageView(Context context) {
        super(context);
        init(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                toggleColor();
                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private void toggleColor() {
        // Change the color of the circle here when double-clicked.
        // For example, you can set a different background color or change the image source.
        int nextColorIndex = (currentColorIndex + 1) % colors.length;
        int nextColor = getResources().getColor(colors[nextColorIndex]);
//        setBackgroundColor(nextColor);
        setColorFilter(nextColor);
        currentColorIndex = nextColorIndex;

    }
}
