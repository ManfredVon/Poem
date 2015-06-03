package com.fmf.mypoem.poem;

import com.fmf.mypoem.model.Poem;
import com.fmf.mypoem.model.Rhythm;

import java.io.InputStream;
import java.util.List;

/**
 * Created by fmf on 15/5/31.
 */
public interface PoemParser {
    public List<Poem> parse(InputStream in);
    public List<Poem> parse(String json);
}
