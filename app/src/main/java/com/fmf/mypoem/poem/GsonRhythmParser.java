package com.fmf.mypoem.poem;

import com.fmf.mypoem.model.Rhythm;
import com.fmf.mypoem.poem.RhythmParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by fmf on 15/5/31.
 */
public class GsonRhythmParser implements RhythmParser {
    @Override
    public List<Rhythm> parse(InputStream in) {
        JsonReader reader = new JsonReader(new InputStreamReader(in));
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Rhythm>>() {}.getType();
            List<Rhythm> rhythms = gson.fromJson(reader, type);
            return rhythms;
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Rhythm> parse(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Rhythm>>() {}.getType();
        List<Rhythm> rhythms = gson.fromJson(json, type);
        return rhythms;
    }
}
