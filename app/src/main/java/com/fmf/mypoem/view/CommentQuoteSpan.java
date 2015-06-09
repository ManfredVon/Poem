package com.fmf.mypoem.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.text.Layout;
import android.text.style.QuoteSpan;

/**
 * Created by fmf on 15/6/9.
 */
public class CommentQuoteSpan extends QuoteSpan {
    private int stripeWidth = 5;
    private int gapWidth = 5;

    public CommentQuoteSpan() {
         super();
    }

    public CommentQuoteSpan(int color) {
        super(color);
    }

    public CommentQuoteSpan(int color, int stripeWidth, int gapWidth) {
        super(color);
        this.stripeWidth = stripeWidth;
        this.gapWidth = gapWidth;
    }

    public CommentQuoteSpan(Parcel src) {
        super(src);
        stripeWidth = src.readInt();
        gapWidth = src.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(stripeWidth);
        dest.writeInt(gapWidth);
    }

    public int getLeadingMargin(boolean first) {
        return stripeWidth + gapWidth;
    }

    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir,
                                  int top, int baseline, int bottom,
                                  CharSequence text, int start, int end,
                                  boolean first, Layout layout) {
        Paint.Style style = p.getStyle();
        int color = p.getColor();

        p.setStyle(Paint.Style.FILL);
        p.setColor(getColor());

        c.drawRect(x, top, x + dir * stripeWidth, bottom, p);

        p.setStyle(style);
        p.setColor(color);
    }
}
