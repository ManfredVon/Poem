package com.fmf.mypoem.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;

import com.fmf.mypoem.model.Rhythm;
import com.fmf.mypoem.util.StringUtil;

import java.util.List;

/**
 * Created by fmf on 15/4/7.
 */
public class RhythmDao extends MyPoemDao<Rhythm> {
    private static final String[] PROJECTION = {
            MyPoem.Rhythm._ID,
            MyPoem.Rhythm.NAME,
            MyPoem.Rhythm.ALIAS,
            MyPoem.Rhythm.INTRO,
            MyPoem.Rhythm.COUNT,
            MyPoem.Rhythm.METRE,
            MyPoem.Rhythm.SAMPLE,
            MyPoem.Rhythm.COMMENT,
            MyPoem.Rhythm.TYPE
    };

    private static final String SORT_ORDER = MyPoem.Rhythm.TYPE + " DESC";

    public RhythmDao(Context context) {
        super(context);
    }

    @Override
    protected String getTableName() {
        return MyPoem.Rhythm.TABLE_NAME;
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

    private Rhythm getRhythm(Cursor cursor) {
        Rhythm rhythm = new Rhythm();
        rhythm.setId(cursor.getLong(cursor.getColumnIndex(MyPoem.Poem._ID)));
        rhythm.setName(cursor.getString(cursor.getColumnIndex(MyPoem.Rhythm.NAME)));
        rhythm.setAlias(cursor.getString(cursor.getColumnIndex(MyPoem.Rhythm.ALIAS)));
        rhythm.setIntro(cursor.getString(cursor.getColumnIndex(MyPoem.Rhythm.INTRO)));
        rhythm.setCount(cursor.getInt(cursor.getColumnIndex(MyPoem.Rhythm.COUNT)));
        rhythm.setMetre(cursor.getString(cursor.getColumnIndex(MyPoem.Rhythm.METRE)));
        rhythm.setSample(cursor.getString(cursor.getColumnIndex(MyPoem.Rhythm.SAMPLE)));
        rhythm.setComment(cursor.getString(cursor.getColumnIndex(MyPoem.Rhythm.COMMENT)));
        rhythm.setType(cursor.getString(cursor.getColumnIndex(MyPoem.Rhythm.TYPE)));
        return rhythm;
    }

    protected ContentValues getContentValues(Rhythm rhythm) {
        ContentValues values = new ContentValues();
        values.put(MyPoem.Rhythm.NAME, rhythm.getName());
        values.put(MyPoem.Rhythm.ALIAS, rhythm.getAlias());
        values.put(MyPoem.Rhythm.INTRO, rhythm.getIntro());
        values.put(MyPoem.Rhythm.COUNT, rhythm.getCount());
        values.put(MyPoem.Rhythm.METRE, rhythm.getMetre());
        values.put(MyPoem.Rhythm.SAMPLE, rhythm.getSample());
        values.put(MyPoem.Rhythm.COMMENT, rhythm.getComment());
        values.put(MyPoem.Rhythm.TYPE, rhythm.getType());
        return values;
    }


    private List<Rhythm> listByType(String type) {
        final String selection = MyPoem.Rhythm.TYPE + EQUAL_QUESTION_MARK;
        final String[] selectionArgs = {type};

        return list(selection, selectionArgs);
    }

    public List<Rhythm> listShi() {
        return listByType(MyPoem.TYPE_SHI);
    }

    public List<Rhythm> listCi() {
        return listByType(MyPoem.TYPE_CI);
    }

    private Cursor queryByType(String type) {
        final String selection = MyPoem.Rhythm.TYPE + EQUAL_QUESTION_MARK;
        final String[] selectionArgs = {type};

        return query(selection, selectionArgs);
    }

    public Cursor queryShi() {
        return queryByType(MyPoem.TYPE_SHI);
    }

    public Cursor queryCi() {
        return queryByType(MyPoem.TYPE_CI);
    }

    public Cursor query(String text) {
        if (TextUtils.isEmpty(text)) {
            return queryAll();
        }

        if (MyPoem.TYPE_SHI.equalsIgnoreCase(text) || MyPoem.TYPE_SHI_ZH.equals(text)) {
            return queryShi();
        }

        if (MyPoem.TYPE_CI.equalsIgnoreCase(text) || MyPoem.TYPE_CI_ZH.equals(text)) {
            return queryCi();
        }

        StringBuilder sb = new StringBuilder();
        sb.append(MyPoem.Rhythm.NAME).append(LIKE_QUESTION_MARK);
        sb.append(OR);
        sb.append(MyPoem.Rhythm.ALIAS).append(LIKE_QUESTION_MARK);

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
