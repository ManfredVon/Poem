package com.fmf.mypoem.poem;

import com.fmf.mypoem.model.Poem;
import com.fmf.mypoem.model.Rhythm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.xml.sax.Parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by fmf on 15/5/31.
 */
public class GsonPoemParser implements PoemParser {
    @Override
    public List<Poem> parse(InputStream in) {
        JsonReader reader = new JsonReader(new InputStreamReader(in));
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Poem>>() {}.getType();
            List<Poem> poems = gson.fromJson(reader, type);
            return poems;
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Poem> parse(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Poem>>() {}.getType();
        List<Poem> poems = gson.fromJson(json, type);
        return poems;
    }
}
