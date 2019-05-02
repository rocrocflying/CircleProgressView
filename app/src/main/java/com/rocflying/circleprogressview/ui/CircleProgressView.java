package com.rocflying.circleprogressview.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * 圆环进度条
 * Created by liupeng on 2019/5/2.
 */
public class CircleProgressView extends View {

    private Context context;
    private Paint bgPaint;
    private Paint progressPaint;

    private int bgColor;
    private int progressColor;

    private int circleWidth = 9;
    private float curSwipeAngle = 30;
    private final int MAX_SWIPE_ANGLE = 300;
    private final int START_SWIPE_ANGLE = 120;


    public CircleProgressView(Context context) {
        super(context);
        this.context = context;
        initPaint();
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initPaint();
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initPaint();
    }

    private void initPaint() {
        bgPaint = new Paint();
        bgPaint.setColor(Color.GRAY);
        bgPaint.setStrokeWidth(dip2px(context, circleWidth));
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeCap(Paint.Cap.ROUND);
        bgPaint.setAntiAlias(true);

        progressPaint = new Paint();
        progressPaint.setColor(Color.RED);
        progressPaint.setStrokeWidth(dip2px(context, circleWidth));
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setAntiAlias(true);


    }

    @Override

    protected void onDraw(Canvas canvas) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics = context.getResources().getDisplayMetrics();


        int width = getMeasuredWidth();
        int margin = (displayMetrics.widthPixels - getWidth()) / 2;
        int height = getMeasuredHeight();
        RectF rect = new RectF(getPaddingLeft() + dip2px(context, 9), getPaddingTop() + dip2px(context, 9), width - dip2px(context, 9), height - dip2px(context, 9));
        canvas.drawArc(rect, START_SWIPE_ANGLE, MAX_SWIPE_ANGLE, false, bgPaint);
        canvas.drawArc(rect, START_SWIPE_ANGLE, curSwipeAngle, false, progressPaint);
    }

    public void setValue(float percent) {

        curSwipeAngle = MAX_SWIPE_ANGLE * (percent / 100);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, curSwipeAngle);
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                curSwipeAngle = (float) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

    /**
     * 获取渐变颜色值
     */
    private int getCurColor(float frac, int startColor, int endColor) {
        int redStart = Color.red(startColor);
        int greenStart = Color.green(startColor);
        int blueStart = Color.blue(startColor);
        int alphaStart = Color.alpha(startColor);

        int redEnd = Color.red(endColor);
        int greenEnd = Color.green(endColor);
        int blueEnd = Color.blue(endColor);
        int alphaEnd = Color.alpha(endColor);

        int redDiff = redEnd - redStart;
        int greenDiff = greenEnd - greenStart;
        int blueDiff = blueEnd - blueStart;
        int alphaDiff = alphaEnd - alphaStart;

        int curRed = (int) (redStart + frac * redDiff);
        int curGreen = (int) (greenStart + frac * greenDiff);
        int curBlue = (int) (blueStart + frac * blueDiff);
        int curAlpha = (int) (alphaStart + frac * alphaDiff);

        return Color.argb(curAlpha, curRed, curGreen, curBlue);

    }

    private int getSwipeColor() {
        return 0;
    }

    /**
     * 转变dip为px
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
