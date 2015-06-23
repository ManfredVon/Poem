package com.fmf.poem.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.fmf.poem.R;
import com.fmf.poem.model.Poem;
import com.fmf.poem.model.Rhythm;
import com.fmf.poem.poem.GsonPoemParser;
import com.fmf.poem.poem.GsonRhythmParser;
import com.fmf.poem.poem.PoemParser;
import com.fmf.poem.poem.RhythmParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by fmf on 15/4/2.
 */
public class PoemDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MyPoem.db";

    private Context context;

    public PoemDbHelper(Context context) {
        this(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public PoemDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PoemSqlExpr.SQL_CREATE_POEM);
        db.execSQL(PoemSqlExpr.SQL_CREATE_RHYTHM);
//        db.execSQL(SQL_CREATE_POEM); // another table

        insertRhythmDataFromJsonFile(context, db);

        // TODO: delete when product
        insertPoemDataFromJsonFile(context, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 升级数据库，删掉原来的表，重新建表
        db.execSQL(PoemSqlExpr.SQL_DROP_POEM);
//        db.execSQL(PoemSqlExpr.SQL_DROP_RHYTHM);

        onCreate(db);
    }

    private void insertRhythmDataFromJsonFile(Context context, SQLiteDatabase db) {
        InputStream in = context.getResources().openRawResource(R.raw.rhythms);
        List<Rhythm> rhythms = null;
        try {
            RhythmParser parser = new GsonRhythmParser();
            rhythms = parser.parse(in);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        SQLiteStatement stat = db.compileStatement(PoemSqlExpr.SQL_INSERT_RHYTHM);
        db.beginTransaction();
        for (Rhythm rhythm : rhythms) {
            // name, alias, intro, count, metre, sample, comment, type
            stat.bindString(1, rhythm.getName());
            stat.bindString(2, rhythm.getAlias());
            stat.bindString(3, rhythm.getIntro());
            stat.bindLong(4, rhythm.getCount());
            stat.bindString(5, rhythm.getMetre());
            stat.bindString(6, rhythm.getSample());
            stat.bindString(7, rhythm.getComment());
            stat.bindString(8, rhythm.getType());

            stat.executeInsert();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    // TODO: delete this method and R.raw.poems file when product
    private void insertPoemDataFromJsonFile(Context context, SQLiteDatabase db) {
        InputStream in = context.getResources().openRawResource(R.raw.poems);
        List<Poem> poems = null;
        try {
            PoemParser parser = new GsonPoemParser();
            poems = parser.parse(in);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        SQLiteStatement stat = db.compileStatement(PoemSqlExpr.SQL_INSERT_POEM);
        db.beginTransaction();
        for (Poem poem : poems) {
            // title, subtitle, author, created, updated, content, status, type
            stat.bindString(1, poem.getTitle());
            stat.bindString(2, poem.getSubtitle());
            stat.bindString(3, poem.getAuthor());
            stat.bindString(4, poem.getCreated());
            stat.bindString(5, poem.getUpdated());
            stat.bindString(6, poem.getContent());
            stat.bindString(7, poem.getStatus());
            stat.bindString(8, poem.getType());
            stat.bindLong(9, poem.getRhythmId());

            stat.executeInsert();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }
}
