package com.fmf.poem.poem;

import com.fmf.poem.model.Rhythm;

import java.io.InputStream;
import java.util.List;

/**
 * Created by fmf on 15/5/31.
 */
public interface RhythmParser {
    public List<Rhythm> parse(InputStream in);
    public List<Rhythm> parse(String json);
}
