package com.fmf.mypoem.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import com.fmf.mypoem.model.Poem;
import com.fmf.mypoem.model.Rhythm;

import java.util.List;

/**
 * Created by fmf on 15/4/7.
 */
public class RhythmDao extends MyPoemDao<Rhythm> {
    private static final String[] PROJECTION = {
            MyPoem.Rhythm._ID,
            MyPoem.Rhythm.COLUMN_NAME_NAME,
            MyPoem.Rhythm.COLUMN_NAME_ALIAS,
            MyPoem.Rhythm.COLUMN_NAME_INTRO,
            MyPoem.Rhythm.COLUMN_NAME_COUNT,
            MyPoem.Rhythm.COLUMN_NAME_METRE,
            MyPoem.Rhythm.COLUMN_NAME_SAMPLE,
            MyPoem.Rhythm.COLUMN_NAME_COMMENT,
            MyPoem.Poem.COLUMN_NAME_STATUS,
            MyPoem.Rhythm.COLUMN_NAME_TYPE
    };

    private static final String SORT_ORDER = MyPoem.Rhythm.COLUMN_NAME_TYPE + " DESC";

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
        rhythm.setName(cursor.getString(cursor.getColumnIndex(MyPoem.Rhythm.COLUMN_NAME_NAME)));
        rhythm.setAlias(cursor.getString(cursor.getColumnIndex(MyPoem.Rhythm.COLUMN_NAME_ALIAS)));
        rhythm.setIntro(cursor.getString(cursor.getColumnIndex(MyPoem.Rhythm.COLUMN_NAME_INTRO)));
        rhythm.setCount(cursor.getInt(cursor.getColumnIndex(MyPoem.Rhythm.COLUMN_NAME_COUNT)));
        rhythm.setMetre(cursor.getString(cursor.getColumnIndex(MyPoem.Rhythm.COLUMN_NAME_METRE)));
        rhythm.setSample(cursor.getString(cursor.getColumnIndex(MyPoem.Rhythm.COLUMN_NAME_SAMPLE)));
        rhythm.setComment(cursor.getString(cursor.getColumnIndex(MyPoem.Rhythm.COLUMN_NAME_COMMENT)));
        rhythm.setType(cursor.getString(cursor.getColumnIndex(MyPoem.Rhythm.COLUMN_NAME_TYPE)));
        return rhythm;
    }

    protected ContentValues getContentValues(Rhythm rhythm, boolean allowNull) {
        //如果poem中包含空字段，则该列会被更新为NULL，所以不添加null字段
        ContentValues values = new ContentValues();

        final String name = rhythm.getName();
        if (allowNull || !TextUtils.isEmpty(name))
            values.put(MyPoem.Rhythm.COLUMN_NAME_NAME, name);

        final String alias = rhythm.getAlias();
        if (allowNull || !TextUtils.isEmpty(alias))
            values.put(MyPoem.Rhythm.COLUMN_NAME_NAME, alias);

        final String intro = rhythm.getIntro();
        if (allowNull || !TextUtils.isEmpty(intro))
            values.put(MyPoem.Rhythm.COLUMN_NAME_INTRO, intro);

        final int count = rhythm.getCount();
        if (allowNull || count != 0)
            values.put(MyPoem.Rhythm.COLUMN_NAME_COUNT, count);

        final String metre = rhythm.getName();
        if (allowNull || !TextUtils.isEmpty(metre))
            values.put(MyPoem.Rhythm.COLUMN_NAME_METRE, metre);

        final String sample = rhythm.getName();
        if (allowNull || !TextUtils.isEmpty(sample))
            values.put(MyPoem.Rhythm.COLUMN_NAME_SAMPLE, sample);

        final String comment = rhythm.getName();
        if (allowNull || !TextUtils.isEmpty(comment))
            values.put(MyPoem.Rhythm.COLUMN_NAME_COMMENT, comment);

        final String type = rhythm.getName();
        if (allowNull || !TextUtils.isEmpty(type))
            values.put(MyPoem.Rhythm.COLUMN_NAME_TYPE, type);

        return values;
    }


    private List<Rhythm> listByType(String type) {
        final String selection = MyPoem.Rhythm.COLUMN_NAME_TYPE + " = ?";
        final String[] selectionArgs = {type};

        return list(selection, selectionArgs);
    }

    public List<Rhythm> listShi() {
        return listByType(MyPoem.Rhythm.TYPE_SHI);
    }

    public List<Rhythm> listCi() {
        return listByType(MyPoem.Rhythm.TYPE_CI);
    }

    private Cursor queryByType(String type) {
        final String selection = MyPoem.Rhythm.COLUMN_NAME_TYPE + " = ?";
        final String[] selectionArgs = {type};

        return query(selection, selectionArgs);
    }

    public Cursor queryShi() {
        return queryByType(MyPoem.Rhythm.TYPE_SHI);
    }

    public Cursor queryCi() {
        return queryByType(MyPoem.Rhythm.TYPE_CI);
    }

}
