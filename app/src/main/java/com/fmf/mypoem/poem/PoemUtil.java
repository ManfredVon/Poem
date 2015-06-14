package com.fmf.mypoem.poem;

import android.text.TextUtils;

import com.fmf.mypoem.model.Poem;
import com.fmf.mypoem.model.Rhythm;

/**
 * Created by fmf on 15/6/15.
 */
public class PoemUtil {

    public static String createShareText(Poem poem) {
        String title = poem.getTitle();
        String subtitle = poem.getSubtitle();
        String content = poem.getContent();
        String author = poem.getAuthor();
//        String created = poem.getCreated();

        StringBuilder sb = new StringBuilder();
        final String LF = "\n";
        final String DOT = "•";
        sb.append(title);
        if (!TextUtils.isEmpty(subtitle)) {
            sb.append(DOT).append(subtitle);
        }
        sb.append(DOT).append(author).append(LF);
        sb.append(content);

        return sb.toString();
    }

    public static String createShareText(Rhythm rhythm) {
        String name = rhythm.getName();
        String alias = rhythm.getAlias();
        String intro = rhythm.getIntro();
        String metre = rhythm.getMetre();
        String sample = rhythm.getSample();
        String comment = rhythm.getComment();

        final String LF = "\n";
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        if (!TextUtils.isEmpty(alias)) {
            sb.append("【").append(alias).append("】").append(LF);
        }
        if (!TextUtils.isEmpty(intro)) {
            sb.append(alias).append(LF);
        }
        sb.append(metre).append(LF).append(LF);
        sb.append(sample);
        if (!TextUtils.isEmpty(comment)) {
            sb.append(LF).append(LF).append(comment);
        }

        return sb.toString();
    }
}
