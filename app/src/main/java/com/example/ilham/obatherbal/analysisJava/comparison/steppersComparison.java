package com.example.ilham.obatherbal.analysisJava.comparison;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ilham.obatherbal.MainActivity;
import com.example.ilham.obatherbal.R;
import com.example.ilham.obatherbal.analysisJava.comparison.chooseJamu.chooseJamu;

public class steppersComparison extends AppCompatActivity {
    public static View viewCircleFinishStep1, viewCircleCurrentStep1;
    public static View viewHorizontalOn1, viewHorizontalOff1;
    public static View viewCircleFinishStep2, viewCircleCurrentStep2;
    public static View viewHorizontalOn2, viewHorizontalOff2;
    public static View viewCircleFinishStep3, viewCircleCurrentStep3;
    public static int width = 0;
    public static int position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steppers_comparison);
        loadComponent();
        loadFragment();
    }

    private void loadFragment() {
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layoutstepperComparison,new chooseJamu()).commit();
    }

    private void loadComponent() {
        viewCircleFinishStep1 = (View) findViewById(R.id.view_circle_finish_step_1_Comparison);
        viewCircleCurrentStep1 = (View) findViewById(R.id.view_circle_current_step_1_comparison);
        viewHorizontalOn1 = (View) findViewById(R.id.view_horizontal_on_1_comparison);
        viewHorizontalOff1 = (View) findViewById(R.id.view_horizontal_off_1_comparison);
        viewCircleFinishStep2 = (View) findViewById(R.id.view_circle_finish_step_2_comparison);
        viewCircleCurrentStep2 = (View) findViewById(R.id.view_circle_current_step_2_comparison);
        viewHorizontalOn2 = (View) findViewById(R.id.view_horizontal_on_2_comparison);
        viewHorizontalOff2 = (View) findViewById(R.id.view_horizontal_off_2_comparison);
        viewCircleFinishStep3 = (View) findViewById(R.id.view_circle_finish_step_3_comparison);
        viewCircleCurrentStep3 = (View) findViewById(R.id.view_circle_current_step_3_comparison);

        viewHorizontalOff1.post(new Runnable() {
            @Override
            public void run() {
                width = viewHorizontalOff1.getWidth();
            }
        });
    }

    public static void goToStepMethodComparison() {
        position = 2;
        ObjectAnimator objectAnimatorCircleFinish = ObjectAnimator.ofFloat(viewCircleFinishStep1, "alpha", 0, 1);
        objectAnimatorCircleFinish.setDuration(500);
        objectAnimatorCircleFinish.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                ObjectAnimator objectAnimatorHorizontal = ObjectAnimator.ofFloat(viewHorizontalOff1, "translationX", 0, width);
                objectAnimatorHorizontal.setDuration(500);
                objectAnimatorHorizontal.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        ObjectAnimator objectAnimatorCircle = ObjectAnimator.ofFloat(viewCircleCurrentStep2, "alpha", 0, 1);
                        objectAnimatorCircle.setDuration(500);
                        objectAnimatorCircle.start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                objectAnimatorHorizontal.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        objectAnimatorCircleFinish.start();
    }

    public static void goToStepConfirm() {
        position = 3;
        ObjectAnimator objectAnimatorCircleFinish = ObjectAnimator.ofFloat(viewCircleFinishStep2, "alpha", 0, 1);
        objectAnimatorCircleFinish.setDuration(500);
        objectAnimatorCircleFinish.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                ObjectAnimator objectAnimatorHorizontal = ObjectAnimator.ofFloat(viewHorizontalOff2, "translationX", 0, width);
                objectAnimatorHorizontal.setDuration(500);
                objectAnimatorHorizontal.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        ObjectAnimator objectAnimatorCircle = ObjectAnimator.ofFloat(viewCircleCurrentStep3, "alpha", 0, 1);
                        objectAnimatorCircle.setDuration(500);
                        objectAnimatorCircle.start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                objectAnimatorHorizontal.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        objectAnimatorCircleFinish.start();

    }

    public static void backToStepMethodComparison(){
        final ObjectAnimator objectAnimatorCircleCurrent = ObjectAnimator.ofFloat(viewCircleCurrentStep2, "alpha", 1, 0);
        objectAnimatorCircleCurrent.setDuration(500);
        objectAnimatorCircleCurrent.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                ObjectAnimator objectAnimatorHorizontal = ObjectAnimator.ofFloat(viewHorizontalOff1, "translationX", width, 0);
                objectAnimatorHorizontal.setDuration(500);
                objectAnimatorHorizontal.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        ObjectAnimator objectAnimatorCircleFinish = ObjectAnimator.ofFloat(viewCircleFinishStep1, "alpha", 1, 0);
                        objectAnimatorCircleFinish.setDuration(500);
                        objectAnimatorCircleFinish.start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                objectAnimatorHorizontal.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        objectAnimatorCircleCurrent.start();

    }

    public static void backToStepConfirm(){
        ObjectAnimator objectAnimatorCurrent = ObjectAnimator.ofFloat(viewCircleCurrentStep3, "alpha", 1, 0);
        objectAnimatorCurrent.setDuration(500);
        objectAnimatorCurrent.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                ObjectAnimator objectAnimatorHorizontal = ObjectAnimator.ofFloat(viewHorizontalOff2, "translationX", width, 0);
                objectAnimatorHorizontal.setDuration(500);
                objectAnimatorHorizontal.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        ObjectAnimator objectAnimatorCircleFinish = ObjectAnimator.ofFloat(viewCircleFinishStep2, "alpha", 1, 0);
                        objectAnimatorCircleFinish.setDuration(500);
                        objectAnimatorCircleFinish.start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                objectAnimatorHorizontal.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        objectAnimatorCurrent.start();

    }

    @Override
    public void onBackPressed() {
        position--;
        if (position == 1) {
            backToStepMethodComparison();
        } else if (position == 2) {
            Intent main = new Intent(this, MainActivity.class);
//                ethnic.putExtra("daerah","Sumatra utara");
            startActivity(main);
            finish();
//            backToStepConfirm();
        }

        super.onBackPressed();
    }
}
