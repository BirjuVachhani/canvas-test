package com.simformsolutions.canvastest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class AvatarView extends android.support.v7.widget.AppCompatImageView {

    private int radius;
    private int backgroundColor;
    private Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint pressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path clipPath;
    private boolean adjustViewBound;
    private int centerX, centerY, width, height, borderWidth, borderColor, wrappedSize, pressedColor, textColor, textSize, tvId;
    private Rect textBounds = new Rect();
    private String initial;
    private TextView tvName;
    private boolean textChangeListenerEnabled = false;
    private boolean isColorsRandomized = false;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            invalidate();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private String colorRes[] = {"#F44336",
            "#673AB7",
            "#4CAF50",
            "#E91E63",
            "#2196F3",
            "#009688",
            "#8BC34A",
            "#9C27B0",
            "#FF9800",
            "#03A9F4",
            "#FFC107",
            "#3F51B5",
            "#00BCD4",
            "#FF5722",
            "#795548",
            "#607D8B",};
    private Random random = new Random();
    private int colors[];

    public AvatarView(Context context) {
        super(context);
        init(null);
    }

    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AvatarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {

        if (set == null) return;
        TypedArray attrArray = getContext().obtainStyledAttributes(set, R.styleable.AvatarView);
        radius = attrArray.getDimensionPixelSize(R.styleable.AvatarView_radius, 50);
        isColorsRandomized = attrArray.getBoolean(R.styleable.AvatarView_randomizeColor, false);

        if (isColorsRandomized)
            createColors(colorRes);

        int colorExtResId = attrArray.getResourceId(R.styleable.AvatarView_randomColorRes, 0);
        if (colorExtResId != 0) {
            int cIds[] = getContext().getResources().getIntArray(colorExtResId);
            createColors(cIds);
        }


        backgroundColor = attrArray.getColor(R.styleable.AvatarView_avatar_backgroundColor,
                isColorsRandomized ? colors[random.nextInt(colors.length)] : Color.DKGRAY);
        adjustViewBound = attrArray.getBoolean(R.styleable.AvatarView_adjustViewBounds, true);
        borderWidth = attrArray.getDimensionPixelSize(R.styleable.AvatarView_borderWidth, 0);
        borderColor = attrArray.getColor(R.styleable.AvatarView_borderColor, Color.LTGRAY);
        pressedColor = attrArray.getColor(R.styleable.AvatarView_pressedColor, backgroundColor);
        textColor = attrArray.getColor(R.styleable.AvatarView_textColor, Color.WHITE);
        textSize = attrArray.getDimensionPixelSize(R.styleable.AvatarView_textSize, 10);
        textChangeListenerEnabled = attrArray.getBoolean(R.styleable.AvatarView_textChangeListener, false);

        tvId = attrArray.getResourceId(R.styleable.AvatarView_textView, 0);

        initial = attrArray.getString(R.styleable.AvatarView_text);
        if (initial != null && !initial.equals(""))
            initial = initial.substring(0, 1).toUpperCase();
        else initial = "#";

        wrappedSize = (radius * 2) + borderWidth + 5;
        attrArray.recycle();
        setPaints();
        clipPath = new Path();
    }

    private void createColors(int[] c) {
        int len = c.length;
        colors = new int[len];
        for (int i = 0; i < c.length; i++) {
            int color = c[i];
            colors[i] = color;
        }
    }

    private void createColors(String[] c) {
        int len = c.length;
        colors = new int[len];
        for (int i = 0; i < c.length; i++) {
            int color = Color.parseColor(c[i]);
            colors[i] = color;
        }
    }

    private void setPaints() {

        //for drawing circle
        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setStyle(Paint.Style.FILL);

        //for drawing circle border
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(borderWidth);

        //for drawing circle for pressed state
        pressPaint.setStyle(Paint.Style.FILL);
        pressPaint.setColor(pressedColor);

        //for drawing initial letter
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        //getting textBounds
        textPaint.getTextBounds(initial, 0, initial.length(), textBounds);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (adjustViewBound) setMeasuredDimension(wrappedSize, wrappedSize);

        //getting height and width of layout
        width = getMeasuredWidth();
        height = getMeasuredHeight();

        //getting center point of the layout
        centerX = width / 2;
        centerY = height / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //drawing circle
        canvas.drawCircle(centerX, centerY, radius, backgroundPaint);

        //calculating bottom for letter
        int textBottom = Math.round((centerY) + (textBounds.height() / 2f));

        //drawing circle for pressed state
        if (isPressed()) canvas.drawCircle(centerX, centerY, radius, pressPaint);

        //drawing Initial Letter
        canvas.drawText(getInitial(), width / 2f, textBottom, textPaint);

        //drawing border
        canvas.drawCircle(centerX, centerY, radius, borderPaint);

        clipPath.addCircle(centerX, centerY, radius - borderWidth / 2, Path.Direction.CW);
        canvas.clipPath(clipPath);

        super.onDraw(canvas);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        //called when the view is pressed
        invalidate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        //getting initial letter from linked textView, if any linked
        if (getParent() != null && tvId != 0) {
            tvName = ((View) getParent()).findViewById(tvId);
            if (tvName != null && !tvName.getText().toString().equals("")) {
                initial = tvName.getText().toString().substring(0, 1).toUpperCase();
            }
            if (textChangeListenerEnabled) enableTextChangeListener();
        }
    }

    //return updated initial letter from the linked textView, if any linked,otherwise from text attribute
    private String getInitial() {
        if (tvName != null && !tvName.getText().toString().equals("")) {
            initial = tvName.getText().toString().substring(0, 1).toUpperCase();
        }
        return initial;
    }

    //enables textChangeListener on linked textView to listen to text changes and update initial.
    public boolean enableTextChangeListener() {
        if (tvName == null) {
            return false;
        }
        tvName.addTextChangedListener(textWatcher);
        invalidate();
        return true;
    }

    //disables textChangeListener on linked textView
    public boolean disableTextChangeListener() {
        if (tvName == null) return false;
        tvName.removeTextChangedListener(textWatcher);
        return true;
    }

    public void setBackgroundColor(int color) {
        backgroundColor = color;
        backgroundPaint.setColor(color);
        invalidate();
    }

    public void setTextColor(int color) {
        textColor = color;
        textPaint.setColor(color);
        invalidate();
    }

    public void setBorderColor(int color) {
        borderColor = color;
        borderPaint.setColor(color);
        invalidate();
    }

    public void setBorderWidth(int width) {
        borderWidth = width;
        borderPaint.setStrokeWidth(width);
        invalidate();
    }

    public void setPressedColor(int color) {
        pressedColor = color;
        pressPaint.setColor(color);
        invalidate();
    }

    public void linkTextView(TextView textView) {
        tvName = textView;
        invalidate();
    }

    public void unlinkTextView() {
        tvName = null;
    }

    public void setText(String text) {
        initial = text.substring(0, 1).toUpperCase();
    }

    public void enableRandomColors() {
        isColorsRandomized = true;
    }

}
