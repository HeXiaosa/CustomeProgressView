package com.xiaosa.customprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author hexiaosa
 * @date 2018/10/30
 */

public class CustomProgressView extends View {

    private int ringColor; // 圆环颜色
    private int progressColor; // 进度条颜色
    private float ringWidth; // 圆环颜色

    private int progressPercent; // 进度百分比，50代表是50%

    public CustomProgressView(Context context) {
        this(context, null);
    }

    public CustomProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressView);
        ringColor = typedArray.getColor(R.styleable.CustomProgressView_ringColor, context.getResources().getColor(R.color.colorPrimary));
        progressColor = typedArray.getColor(R.styleable.CustomProgressView_progressColor, context.getResources().getColor(R.color.colorAccent));
        ringWidth = typedArray.getDimension(R.styleable.CustomProgressView_ringWidth, 50);
        progressPercent = typedArray.getInteger(R.styleable.CustomProgressView_progressPercent, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST || MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            // 针对 wrap_content 自定义
            int size = getSuggestedMinimumWidth() < getSuggestedMinimumHeight() ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();
            setMeasuredDimension(getDefaultSize(size, widthMeasureSpec),
                    getDefaultSize(size, widthMeasureSpec));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(ringWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(ringColor);

        // 画圆环
        canvas.drawArc(ringWidth/2, ringWidth/2, getWidth()-ringWidth/2, getHeight()-ringWidth/2, -90, 360, false, paint);

        paint.setColor(progressColor);
        // 画进度
        canvas.drawArc(ringWidth/2, ringWidth/2, getWidth()-ringWidth/2, getHeight()-ringWidth/2, -90, (float) (progressPercent*1.0/100*360), false, paint);
    }

    /**
     * @param time time's unit is millisecond
     */
    public void startProgress(long time) {
        final long everyTime = (long) (time*1.0/100);
        new Thread() {
            @Override
            public void run() {
                for (int i = 1; i <= 100; i++) {
                    progressPercent = i;
                    postInvalidate();
                    try {
                        Thread.sleep(everyTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
