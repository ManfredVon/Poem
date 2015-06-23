package com.fmf.poem.poem;

import com.fmf.poem.model.Poem;

import java.io.InputStream;
import java.util.List;

/**
 * Created by fmf on 15/5/31.
 */
public interface PoemParser {
    public List<Poem> parse(InputStream in);
    public List<Poem> parse(String json);
}
