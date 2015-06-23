package com.fmf.poem.poem;

import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.QuoteSpan;

import com.fmf.poem.model.Poem;
import com.fmf.poem.model.Rhythm;
import com.fmf.poem.view.CommentQuoteSpan;

/**
 * Created by fmf on 15/6/9.
 */
public class PoemFormater {
    public static CharSequence format(Poem poem){


        return null;
    }

    public static CharSequence format(Rhythm rhythm){


        return null;
    }

    @Nullable
    public static CharSequence formatMetre(String metre){
        return MetreFormater.formatSpan(metre);
    }

    @Nullable
    public static CharSequence formatComment(String comment, int color){
        if (TextUtils.isEmpty(comment)){
            return null;
        }
        SpannableString spannableString = new SpannableString(comment);
//        final int color = getActivity().getResources().getColor(R.color.primary);
        final QuoteSpan quoteSpan = new CommentQuoteSpan(color);
        spannableString.setSpan(quoteSpan, 0, comment.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableString;
    }

}
