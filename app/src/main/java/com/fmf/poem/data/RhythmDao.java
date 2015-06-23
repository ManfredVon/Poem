package com.fmf.poem.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;

import com.fmf.poem.model.Rhythm;
import com.fmf.poem.util.StringUtil;

import java.util.List;

/**
 * Created by fmf on 15/4/7.
 */
public class RhythmDao extends BasePoemDao<Rhythm> {
    private static final String[] PROJECTION = {
            PoemContract.Rhythm._ID,
            PoemContract.Rhythm.NAME,
            PoemContract.Rhythm.ALIAS,
            PoemContract.Rhythm.INTRO,
            PoemContract.Rhythm.COUNT,
            PoemContract.Rhythm.METRE,
            PoemContract.Rhythm.SAMPLE,
            PoemContract.Rhythm.COMMENT,
            PoemContract.Rhythm.TYPE
    };

    private static final String SORT_ORDER = PoemContract.Rhythm.TYPE + " DESC";

    public RhythmDao(Context context) {
        super(context);
    }

    @Override
    protected String getTableName() {
        return PoemContract.Rhythm.TABLE_NAME;
    }

    @Override
    protected String getSortOrder() {
        return SORT_ORDER;
    }

    @Override
    protected String[] getProjection() {
        return PROJECTION;
    }

    @Override
    protected Rhythm getModel(Cursor cursor) {
        return getRhythm(cursor);
    }

    public static Rhythm getRhythm(Cursor cursor) {
        Rhythm rhythm = new Rhythm();
        rhythm.setId(cursor.getLong(cursor.getColumnIndex(PoemContract.Poem._ID)));
        rhythm.setName(cursor.getString(cursor.getColumnIndex(PoemContract.Rhythm.NAME)));
        rhythm.setAlias(cursor.getString(cursor.getColumnIndex(PoemContract.Rhythm.ALIAS)));
        rhythm.setIntro(cursor.getString(cursor.getColumnIndex(PoemContract.Rhythm.INTRO)));
        rhythm.setCount(cursor.getInt(cursor.getColumnIndex(PoemContract.Rhythm.COUNT)));
        rhythm.setMetre(cursor.getString(cursor.getColumnIndex(PoemContract.Rhythm.METRE)));
        rhythm.setSample(cursor.getString(cursor.getColumnIndex(PoemContract.Rhythm.SAMPLE)));
        rhythm.setComment(cursor.getString(cursor.getColumnIndex(PoemContract.Rhythm.COMMENT)));
        rhythm.setType(cursor.getString(cursor.getColumnIndex(PoemContract.Rhythm.TYPE)));
        return rhythm;
    }

    protected ContentValues getContentValues(Rhythm rhythm) {
        ContentValues values = new ContentValues();
        values.put(PoemContract.Rhythm.NAME, rhythm.getName());
        values.put(PoemContract.Rhythm.ALIAS, rhythm.getAlias());
        values.put(PoemContract.Rhythm.INTRO, rhythm.getIntro());
        values.put(PoemContract.Rhythm.COUNT, rhythm.getCount());
        values.put(PoemContract.Rhythm.METRE, rhythm.getMetre());
        values.put(PoemContract.Rhythm.SAMPLE, rhythm.getSample());
        values.put(PoemContract.Rhythm.COMMENT, rhythm.getComment());
        values.put(PoemContract.Rhythm.TYPE, rhythm.getType());
        return values;
    }


    private List<Rhythm> listByType(String type) {
        final String selection = PoemContract.Rhythm.TYPE + EQUAL_QUESTION_MARK;
        final String[] selectionArgs = {type};

        return list(selection, selectionArgs);
    }

    public List<Rhythm> listShi() {
        return listByType(PoemContract.TYPE_SHI);
    }

    public List<Rhythm> listCi() {
        return listByType(PoemContract.TYPE_CI);
    }

    private Cursor queryByType(String type) {
        final String selection = PoemContract.Rhythm.TYPE + EQUAL_QUESTION_MARK;
        final String[] selectionArgs = {type};

        return query(selection, selectionArgs);
    }

    public Cursor queryShi() {
        return queryByType(PoemContract.TYPE_SHI);
    }

    public Cursor queryCi() {
        return queryByType(PoemContract.TYPE_CI);
    }

    public Cursor query(String text) {
        if (TextUtils.isEmpty(text)) {
            return queryAll();
        }

        if (PoemContract.TYPE_SHI.equalsIgnoreCase(text) || PoemContract.TYPE_SHI_ZH.equals(text)) {
            return queryShi();
        }

        if (PoemContract.TYPE_CI.equalsIgnoreCase(text) || PoemContract.TYPE_CI_ZH.equals(text)) {
            return queryCi();
        }

        StringBuilder sb = new StringBuilder();
        sb.append(PoemContract.Rhythm.NAME).append(LIKE_QUESTION_MARK);
        sb.append(OR);
        sb.append(PoemContract.Rhythm.ALIAS).append(LIKE_QUESTION_MARK);

        final String selection = sb.toString();
        text = StringUtil.wrap(text, PERCENT);
        final String[] selectionArgs = {text, text};

        return query(selection, selectionArgs);
    }

    public void save(List<Rhythm> rhythms) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            SQLiteStatement stat = db.compileStatement(SQL_INSERT_RHYTHM);
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
        } finally {
            db.close();
        }
    }


}
