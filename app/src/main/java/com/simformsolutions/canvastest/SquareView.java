package com.simformsolutions.canvastest;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class SquareView extends View {

    private int centerX;
    private int centerY;
    private Rect rect = new Rect();
    private Paint paint;
    private int fillColor;
    private int strokeColor;
    private int strokeWidth;
    private int squareSize;
    private int width;
    private int height;
    private int backgroundColor;
    private boolean isWraped;

    public SquareView(Context context) {
        super(context);
        init(null);
    }

    public SquareView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SquareView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SquareView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes, int centerX) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(AttributeSet set) {
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        if (set == null) {
            return;
        }

        TypedArray attrValues = getContext().obtainStyledAttributes(set, R.styleable.SquareView);
        fillColor = attrValues.getColor(R.styleable.SquareView_square_fillColor, Color.BLUE);
        strokeColor = attrValues.getColor(R.styleable.SquareView_strokeColor, Color.BLACK);
        strokeWidth = attrValues.getInt(R.styleable.SquareView_strokeWidth, 0);
        squareSize = attrValues.getDimensionPixelSize(R.styleable.SquareView_squareSize, 100);
        backgroundColor = attrValues.getColor(R.styleable.SquareView_backgroundColor, Color.DKGRAY);
        int wrapped = attrValues.getInt(R.styleable.SquareView_layout_width, 1);
        isWraped = wrapped == 0;
        paint.setColor(fillColor);
        attrValues.recycle();

    }

    private Rect createRect() {
        rect.top = centerY - (squareSize / 2);
        rect.left = centerX - (squareSize / 2);
        rect.right = centerX + (squareSize / 2);
        rect.bottom = centerY + (squareSize / 2);
        return rect;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isWraped) {
            setMeasuredDimension(squareSize, squareSize);
        }
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        centerX = width / 2;
        centerY = height / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(backgroundColor);
        //paint.setColor(fillColor);
        //canvas.drawRect(createRect(), paint);
        //centerX = getWidth() / 2;
        //centerY = getHeight() / 2;
        paint.setColor(Color.RED);
        canvas.drawLine(centerX, 0f, centerX, height, paint);
        canvas.drawLine(0, centerY, width, centerY, paint);
        paint.setColor(Color.BLUE);
//        canvas.drawLine(200, 200, 200, 700, paint);
//        canvas.drawLine(200, 700, 700, 700, paint);
//        canvas.drawLine(700, 700, 700, 200, paint);
//        canvas.drawLine(700, 200, 200, 200, paint);
//        rect.top = 200;
//        rect.left = 200;
//        rect.bottom = 700;
//        rect.right = 700;
        canvas.drawRect(createRect(), paint);
        //canvas.drawLine(canvas.getWidth()/2,0,canvas.getWidth()/2,canvas.getHeight(),paint);

    }
}
