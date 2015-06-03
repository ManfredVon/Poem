package com.fmf.mypoem.poem;

import com.fmf.mypoem.model.Rhythm;

import java.io.InputStream;
import java.util.List;

/**
 * Created by fmf on 15/5/31.
 */
public interface RhythmParser {
    public List<Rhythm> parse(InputStream in);
    public List<Rhythm> parse(String json);
}
