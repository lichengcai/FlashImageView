package com.flashimageview;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by lichengcai on 2016/12/7.
 */

public class FImageView extends ImageView{
    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private float xFirst;
    private float yFirst;
    private float xSecond;
    private float ySecond;
    private float xThird;
    private float yThird;
    private int mPaintColor;
    float d1 = 1;
    float d2= 1;
    float d3 = 1;


    public FImageView(Context context) {
        this(context,null);
    }

    public FImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        TypedArray array = context.getTheme().obtainStyledAttributes(attrs,R.styleable.FImageView,defStyleAttr,0);
        int n = array.getIndexCount();
        for (int i=0; i<n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.FImageView_flashColor:
                    mPaintColor = array.getColor(attr,getResources().getColor(R.color.colorAccent));
                    break;
            }
        }
        array.recycle();

        mPaint = new Paint();
        mPaint.setColor(mPaintColor);
        mPaint.setStrokeWidth(2);
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
            mWidth = getDrawable().getIntrinsicWidth();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }else {
            mHeight = getDrawable().getIntrinsicHeight();
        }
        xFirst = -100;
        yFirst = -100;

        xSecond = mWidth/3 - 600 ;
        ySecond = -600;

        xThird =mWidth/3*2-1100;
        yThird = -1100;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(xFirst,yFirst,getEnd(xFirst),getEnd(yFirst),mPaint);
        canvas.drawLine(xSecond,ySecond,getEnd(xSecond),getEnd(ySecond),mPaint);
        canvas.drawLine(xThird,yThird,getEnd(xThird),getEnd(yThird),mPaint);

        float height = 1920;
        double speed = (float) 0.6;

        d1 = (float) (d1+speed);
        d2 = (float) (d2+speed);
        d3 = (float) (d3+speed);
        xFirst = xFirst+d1;
        yFirst = yFirst+d1;

        xSecond = xSecond+d2;
        ySecond = ySecond+d2;

        xThird = xThird+d3;
        yThird = yThird+d3;

        if (yFirst>=-100+height) {
            yFirst =-100;
            xFirst = -100;
            d1 =1;
        }
        if (ySecond>=-600+height) {
            xSecond = mWidth/3-600;
            ySecond = -600;
            d2 = 1;
        }
        if (yThird >= -1100+height) {
            xThird = mWidth/3*2-1100;
            yThird = -1100;
            d3 = 1;
        }
        postInvalidate();

    }

    private float getEnd(float start) {
        return start + 100;
    }

}
