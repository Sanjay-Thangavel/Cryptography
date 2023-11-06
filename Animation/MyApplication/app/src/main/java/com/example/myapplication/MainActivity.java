package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView circleImageView;
    private GestureDetector gestureDetector;
    private boolean isAlternateColor = false;
    private Button animateButton;
    private AnimationSet currentAnimationSet;
    private int[] colors = {R.color.color1, R.color.color2, R.color.color3};
    private int currentColorIndex = 0;
    private  int currentshapeindex=0;
    private String[] shapes={"circle","square_background","triangle_background"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleImageView = findViewById(R.id.circleImageView);
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                toggleCircleColor();
                return true;
            }
        });
        animateButton = findViewById(R.id.animateButton);
        // Initialize the animation sets
        currentAnimationSet = createCircularPathAnimation();

        animateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nextShapeIndex = (currentshapeindex+ 1) % shapes.length;
                if(nextShapeIndex==0){
                    circleImageView.setImageResource(R.drawable.square_background);
                }
                else if(nextShapeIndex==1){
                    circleImageView.setImageResource(R.drawable.triangle_background);

                }
                else{
                    circleImageView.setImageResource(R.drawable.circle);
                }
                currentshapeindex++;
//        setBackgroundColor(nextColor);

                currentAnimationSet=createCircularPathAnimation();
                animateCircle();
            }
        });

//        circleImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Change the animation path when the button is clicked
////                if (currentAnimationSet == createCircularPathAnimation()) {
////                    currentAnimationSet = createSquarePathAnimation();
////                } else if (currentAnimationSet == createSquarePathAnimation()) {
////                    currentAnimationSet = createRectangularPathAnimation();
////                } else {
////                    currentAnimationSet = createCircularPathAnimation();
////                }
//                currentAnimationSet=createCircularPathAnimation();
//                animateCircle();
//            }
//        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }
    private void animateCircle() {
        // Define the animation to make the circle smaller
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f);
        scaleAnimation.setDuration(1000); // Animation duration in milliseconds
        scaleAnimation.setFillAfter(true);

//         Define the animation to make the circle revolve around a circular path
        RotateAnimation rotateAnimation = new RotateAnimation(0, 1080, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000); // Animation duration in milliseconds
        rotateAnimation.setRepeatCount(Animation.INFINITE); // Repeat the animation indefinitely
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setInterpolator(this, android.R.interpolator.linear);

//        TranslateAnimation translateAnimation = new TranslateAnimation(0, 500, 0, 500);
//        translateAnimation.setDuration(2000);

        // Combine both animations
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(rotateAnimation);
//        animationSet.addAnimation(new TranslateAnimation(0, 250, 0, 250));

        circleImageView.startAnimation(animationSet);
//        circleImageView.startAnimation(currentAnimationSet);
    }
    private AnimationSet createCircularPathAnimation() {
        // Define an animation set for a circular path
        AnimationSet animationSet = new AnimationSet(true);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);
        animationSet.addAnimation(rotateAnimation);
        return animationSet;
    }
    private AnimationSet createSquarePathAnimation() {
        // Define an animation set for a square path
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 200, 0, 200);
        translateAnimation.setDuration(2000);
        animationSet.addAnimation(translateAnimation);
        return animationSet;
    }

    private AnimationSet createRectangularPathAnimation() {
        // Define an animation set for a rectangular path
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 300, 0, 150);
        translateAnimation.setDuration(2000);
        animationSet.addAnimation(translateAnimation);
        return animationSet;
    }
    private void toggleCircleColor() {
        int nextColorIndex = (currentColorIndex + 1) % colors.length;
        int nextColor = getResources().getColor(colors[nextColorIndex]);
//        setBackgroundColor(nextColor);
        circleImageView.setColorFilter(nextColor);
        currentColorIndex = nextColorIndex;
    }
}
