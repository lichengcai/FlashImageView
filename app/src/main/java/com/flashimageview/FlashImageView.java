package com.flashimageview;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by lichengcai on 2016/12/7.
 */

public class FlashImageView extends View {
    private Bitmap mBgBitmap;
    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private Point mStartPoint;
    private Point mEndPoint;

    public FlashImageView(Context context) {
        this(context,null);
    }

    public FlashImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlashImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        mPaint.setStrokeWidth(1);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs,R.styleable.FlashImageView,defStyleAttr,0);
        int count = array.getIndexCount();
        for (int i=0; i<count; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.FlashImageView_bgDrawable:
                    int r = array.getResourceId(attr,R.mipmap.banner);
                    mBgBitmap = BitmapFactory.decodeResource(getResources(),r);
                    break;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }else {
            mWidth = mBgBitmap.getWidth();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }else {
            mHeight = mBgBitmap.getHeight();
        }

        setMeasuredDimension(mWidth, mHeight);


        int x = mWidth/3;
        int y = mHeight;
        int length = 100;

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartPoint = new Point(mWidth/3,0);
        mEndPoint = new Point(mWidth/3+mHeight,mHeight);

        ValueAnimator valueAnimator = ObjectAnimator.ofObject(new PointEvaluator(),mStartPoint,mEndPoint);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point point = (Point) animation.getAnimatedValue();
                Log.d("Point","point--" + point.toString());
                mStartPoint.setX(point.getX());
                mStartPoint.setY(point.getY());
                postInvalidate();
            }
        });

        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
            canvas.drawBitmap(mBgBitmap,0,0,null);
            canvas.drawLine(mStartPoint.getX(),mStartPoint.getY(),getEndX(mStartPoint.getX()),getEndY(mStartPoint.getY()),mPaint);

        Log.d("onMeasure","onMeasure mWidth:--" + mWidth/3 + "  mHeight:--");
    }


    private int getEndX(int startX) {
        return startX + 100;
    }

    private int getEndY(int startY) {
        return startY + 100;
    }

    private class PointEvaluator implements TypeEvaluator<Point> {

        @Override
        public Point evaluate(float fraction, Point startValue, Point endValue) {
            Point point = new Point();
            point.setX((int) (startValue.getX()+(endValue.getX()-startValue.getX())*fraction));
            point.setY((int) (startValue.getY()+(endValue.getY()-startValue.getY())*fraction));

//            particle.x = startValue.x + (endValue.x - startValue.x) * fraction;
//            particle.y = startValue.y + (endValue.y - startValue.y) * fraction;
//            particle.r = startValue.r + (endValue.r - startValue.r) * fraction;
            return point;
        }
    }
}
