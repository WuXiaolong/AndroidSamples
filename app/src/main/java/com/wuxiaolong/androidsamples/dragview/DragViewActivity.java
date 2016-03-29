package com.wuxiaolong.androidsamples.dragview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import com.wuxiaolong.androidsamples.R;

public class DragViewActivity extends AppCompatActivity {
//    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_view);

        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.parentLayout);
        ScaleAnimation scaleAnimation=new ScaleAnimation(0,1,0,1);
        scaleAnimation.setDuration(2000);
        LayoutAnimationController layoutAnimationController=new LayoutAnimationController(scaleAnimation,0.5f);
        layoutAnimationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
        parentLayout.setLayoutAnimation(layoutAnimationController);
//        imageView = (ImageView) findViewById(R.id.imageMove);

//        imageView.animate()
//                .alpha(0.5f)
//                .y(300)
//                .setDuration(2000)
//                //api min is 16
//                .withStartAction(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                })
//                //api min is 16
//                .withEndAction(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                })
//                .start();
//        Animator animator = AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.set_animator);
//        animator.setTarget(imageView);
//        animator.start();
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0.5f);
//                objectAnimator1.setDuration(2000);
//                objectAnimator1.setInterpolator(new AccelerateInterpolator());
//                objectAnimator1.start();
//                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(imageView, "translationY", 300);
//                objectAnimator2.setDuration(2000);
//                objectAnimator2.start();
//                PropertyValuesHolder propertyValuesHolder1 = PropertyValuesHolder.ofFloat("translationX", 300f);
//                PropertyValuesHolder propertyValuesHolder2 = PropertyValuesHolder.ofFloat("alpha", 1f, 0.5f);
//                PropertyValuesHolder propertyValuesHolder3 = PropertyValuesHolder.ofFloat("scaleX", 1f, 0, 1f);
//                PropertyValuesHolder propertyValuesHolder4 = PropertyValuesHolder.ofFloat("scaleY", 1f, 0, 1f);
//                ObjectAnimator.ofPropertyValuesHolder(imageView, propertyValuesHolder1, propertyValuesHolder2, propertyValuesHolder3, propertyValuesHolder4)
//                        .setDuration(5000).start();
//                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(imageView, "alpha", 0.5f, 1f);
//                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(imageView, "translationY", 300);
//                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 0, 1f);
//                AnimatorSet animatorSet = new AnimatorSet();
//                animatorSet.setDuration(5000);
////                animatorSet.playTogether(objectAnimator1, objectAnimator2,objectAnimator3);
//                animatorSet.play(objectAnimator3).before(objectAnimator1);
//                animatorSet.start();
//                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(imageView, "alpha", 0.5f, 1f);
//                objectAnimator1.addListener(new Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animation) {
//
//                    }
//                });
//                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 100);
//                valueAnimator.setTarget(imageView);
//                valueAnimator.setDuration(2000).start();
//                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        Float value = (Float) animation.getAnimatedValue();
//                        //TODO use the value
//                        Toast.makeText(getApplicationContext(), "value=" + value, Toast.LENGTH_LONG).show();
//                    }
//                });


//            }
//        });

    }

}
