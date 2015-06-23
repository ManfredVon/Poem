package com.fmf.poem.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

import com.fmf.poem.R;
import com.fmf.poem.poem.PoemLog;

/**
 * Created by fmf on 15/6/9.
 */
public class LineEditText extends EditText {
    private Paint paint;
    private int lineColor;

    public LineEditText(Context context) {
        this(context, null);
    }

    public LineEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    // Called from constructors
    private void init(Context context, AttributeSet attrs) {
        PoemLog.i(this, "init");
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LineEditText);
        lineColor = a.getColor(R.styleable.LineEditText_lineColor, getResources().getColor(R.color.accent));
        a.recycle();

        this.paint = new Paint();
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setColor(lineColor);
        this.paint.setStrokeWidth(getLineHeight() / 10);
        this.paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float startX = getPaddingLeft();
        float stopX = getWidth() - getPaddingRight();
        float offsetY = getPaddingTop()
                - getPaint().getFontMetrics().top
                + paint.getStrokeWidth() / 2;
        for (int i = 0; i < getLineCount(); ++i) {
            float y = offsetY + getLineHeight() * i;
            canvas.drawLine(startX, y, stopX, y, paint);
        }

        super.onDraw(canvas);
    }
}