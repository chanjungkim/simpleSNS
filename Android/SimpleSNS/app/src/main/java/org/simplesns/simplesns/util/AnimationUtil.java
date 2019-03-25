package org.simplesns.simplesns.util;

import android.animation.Animator;

import android.animation.AnimatorSet;

import android.animation.ObjectAnimator;

import android.content.Context;

import android.view.View;

import android.view.animation.BounceInterpolator;



public class AnimationUtil {

    /**

     * @param v - your view

     * @param durationMillis - milliseconds

     * @param listener - listener if an

     */

    public static ObjectAnimator fideIn(View v,long durationMillis,Animator.AnimatorListener listener) {

        return ofFloat(v, "alpha",0.0F, 1.0F, durationMillis,listener);

    }

    public static ObjectAnimator fideOut(View v,long durationMillis,Animator.AnimatorListener listener) {

        return ofFloat(v, "alpha",1.0F, 0.0F, durationMillis,listener);

    }

    public static ObjectAnimator rotateY(View v,float toDegrees,long durationMillis,Animator.AnimatorListener listener) {

        return ofFloat(v, "rotationY", 0.0f, toDegrees, durationMillis,listener);

    }

    public static ObjectAnimator rotateX(View v,float toDegrees,long durationMillis,Animator.AnimatorListener listener) {

        return ofFloat(v, "rotationX", 0.0f, toDegrees, durationMillis,listener);

    }

    public static ObjectAnimator rotate(View v,float toDegrees,long durationMillis,Animator.AnimatorListener listener) {

        return ofFloat(v, "rotation", 0.0f, toDegrees, durationMillis,listener);

    }

    /**

     * @param fromWidth - 1.0f is the original size of the view

     */

    public static ObjectAnimator scaleWidth(View v,float fromWidth,float toWidth,long durationMillis,Animator.AnimatorListener listener) {

        return ofFloat(v, "scaleX", fromWidth, toWidth, durationMillis,listener);

    }

    public static ObjectAnimator scaleHeight(View v,float fromHeight,float toHeight,long durationMillis,Animator.AnimatorListener listener) {

        return ofFloat(v, "scaleY", fromHeight, toHeight, durationMillis,listener);

    }

    // https://developer.android.com/guide/topics/graphics/prop-animation.html

    public static void scaleXY(View v, float fromWidth,float toWidth,float fromHeight,float toHeight,long durationMillis,Animator.AnimatorListener listener) {

        ObjectAnimator animationX = ofFloat(v, "scaleX", fromWidth, toWidth, durationMillis,listener);

        ObjectAnimator animationY = ofFloat(v, "scaleY", fromHeight, toHeight, durationMillis,listener);

        AnimatorSet animSetXY = new AnimatorSet();

        animSetXY.playTogether(animationX, animationY);

        animSetXY.start();

    }

    public static ObjectAnimator moveY(View v,float fromY,float toY,long durationMillis,Animator.AnimatorListener listener) {

        return ofFloat(v, "translationY", fromY, toY, durationMillis,listener);

    }

    public static ObjectAnimator moveX(View v,float fromX,float toX,long durationMillis,Animator.AnimatorListener listener) {

        return ofFloat(v, "translationX", fromX, toX, durationMillis,listener);

    }

    public static ObjectAnimator moveYBounce(View v,float fromY,float toY,long durationMillis,Animator.AnimatorListener listener) {

        ObjectAnimator animation =ofFloat(v, "translationY", fromY, toY, durationMillis,listener);

        animation.setInterpolator(new BounceInterpolator());

        return animation;

    }

    public static ObjectAnimator moveXBounce(View v,float fromX,float toX,long durationMillis,Animator.AnimatorListener listener) {

        ObjectAnimator animation = ofFloat(v, "translationX", fromX, toX, durationMillis, listener);

        animation.setInterpolator(new BounceInterpolator());

        return animation;

    }

    public static ObjectAnimator ofFloat(View v,String propertyName,float from,float to,long durationMillis,Animator.AnimatorListener listener) {

        ObjectAnimator animation = ObjectAnimator.ofFloat(v, propertyName, from, to);

        animation.setDuration(durationMillis);

        if(listener != null)animation.addListener(listener);

        animation.start();

        return animation;

    }

    public static int dip2px(Context context, float dipValue){

        final float scale = context.getResources().getDisplayMetrics().density;

        return (int)(dipValue * scale + 0.5f);

    }

    public static int px2dip(Context context, float pxValue){

        final float scale = context.getResources().getDisplayMetrics().density;

        return (int)(pxValue / scale + 0.5f);

    }

}







