package com.fmf.mypoem.poem;

import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.QuoteSpan;
import android.text.style.StyleSpan;

import com.fmf.mypoem.R;
import com.fmf.mypoem.model.Poem;
import com.fmf.mypoem.model.Rhythm;
import com.fmf.mypoem.view.CommentQuoteSpan;

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
    public CharSequence formatMetre(String metre){
        return MetreFormater.format(metre);
    }

    @Nullable
    public CharSequence formatComment(String comment, int color){
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
