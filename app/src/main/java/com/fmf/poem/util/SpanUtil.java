package com.fmf.poem.util;

import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

/**
 * Created by fmf on 15/6/10.
 */
public class SpanUtil {

    @Nullable
    public static SpannableString decorateStyle(String text, int styleId){
        if (TextUtils.isEmpty(text)){
            return null;
        }
        final SpannableString spannableString = new SpannableString(text);
        final StyleSpan styleSpan = new StyleSpan(styleId);
        spannableString.setSpan(styleSpan, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @Nullable
    public static SpannableString decorateUnderline(String text){
        if (TextUtils.isEmpty(text)){
            return null;
        }
        final SpannableString spannableString = new SpannableString(text);
        final UnderlineSpan underlineSpan = new UnderlineSpan();
        spannableString.setSpan(underlineSpan, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }


}
