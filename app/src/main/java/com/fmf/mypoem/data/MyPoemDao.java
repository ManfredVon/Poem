package com.fmf.mypoem.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.fmf.mypoem.model.Poem;
import com.fmf.mypoem.util.PoemLog;

/**
 * Created by fmf on 15/4/7.
 */
public class MyPoemDao {
    private MyPoemDbHelper dbHelper;
    private final String[] projection = {
            MyPoem.Poem._ID,
            MyPoem.Poem.COLUMN_NAME_TITLE,
            MyPoem.Poem.COLUMN_NAME_SUBTITLE,
            MyPoem.Poem.COLUMN_NAME_AUTHOR,
            MyPoem.Poem.COLUMN_NAME_CREATE_DATE,
            MyPoem.Poem.COLUMN_NAME_UPDATE_TIME,
            MyPoem.Poem.COLUMN_NAME_CONTENT,
            MyPoem.Poem.COLUMN_NAME_STATUS,
            MyPoem.Poem.COLUMN_NAME_TYPE
    };

    private final String sortOrder = MyPoem.Poem.COLUMN_NAME_UPDATE_TIME + " DESC";

    public MyPoemDbHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(MyPoemDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public MyPoemDao(Context context) {
        this.dbHelper = new MyPoemDbHelper(context);
    }

    public long create(Poem poem) {
        ContentValues values = getContentValues(poem, false);
//        ContentValues values = new ContentValues();
//        values.put(MyPoem.Poem.COLUMN_NAME_TITLE, poem.getTitle());
//        values.put(MyPoem.Poem.COLUMN_NAME_SUBTITLE, poem.getSubtitle());
//        values.put(MyPoem.Poem.COLUMN_NAME_AUTHOR, poem.getAuthor());
//        values.put(MyPoem.Poem.COLUMN_NAME_CREATE_DATE, poem.getCreated());
//        values.put(MyPoem.Poem.COLUMN_NAME_UPDATE_TIME, poem.getUpdated());
//        values.put(MyPoem.Poem.COLUMN_NAME_CONTENT, poem.getContent());
//        values.put(MyPoem.Poem.COLUMN_NAME_STATUS, poem.getStatus());
//        values.put(MyPoem.Poem.COLUMN_NAME_TYPE, poem.getType());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            return db.insert(MyPoem.Poem.TABLE_NAME, null, values);
        } finally {
            db.close();
        }
    }

    public int update(Poem poem) {
        final String selection = MyPoem.Poem._ID + " = ?";
        final String[] selectionArgs = {String.valueOf(poem.getId())};
        ContentValues values = getContentValues(poem, false);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            return db.update(MyPoem.Poem.TABLE_NAME, values, selection, selectionArgs);
        } finally {
            db.close();
        }
    }

    private ContentValues getContentValues(Poem poem, boolean allowNull) {
        //如果poem中包含空字段，则该列会被更新为NULL，所以不添加null字段
        ContentValues values = new ContentValues();
        if (allowNull || !TextUtils.isEmpty(poem.getTitle()))
            values.put(MyPoem.Poem.COLUMN_NAME_TITLE, poem.getTitle());
        if (allowNull || !TextUtils.isEmpty(poem.getSubtitle()))
            values.put(MyPoem.Poem.COLUMN_NAME_SUBTITLE, poem.getSubtitle());
        if (allowNull || !TextUtils.isEmpty(poem.getAuthor()))
            values.put(MyPoem.Poem.COLUMN_NAME_AUTHOR, poem.getAuthor());
        if (allowNull || !TextUtils.isEmpty(poem.getCreated()))
            values.put(MyPoem.Poem.COLUMN_NAME_CREATE_DATE, poem.getCreated());
        if (allowNull || !TextUtils.isEmpty(poem.getUpdated()))
            values.put(MyPoem.Poem.COLUMN_NAME_UPDATE_TIME, poem.getUpdated());
        if (allowNull || !TextUtils.isEmpty(poem.getContent()))
            values.put(MyPoem.Poem.COLUMN_NAME_CONTENT, poem.getContent());
        if (allowNull || !TextUtils.isEmpty(poem.getStatus()))
            values.put(MyPoem.Poem.COLUMN_NAME_STATUS, poem.getStatus());
        if (allowNull || !TextUtils.isEmpty(poem.getType()))
            values.put(MyPoem.Poem.COLUMN_NAME_TYPE, poem.getType());
        return values;
    }

    public Cursor listDrafts() {
        final String selection = MyPoem.Poem.COLUMN_NAME_STATUS + " = ?";
        final String[] selectionArgs = {MyPoem.Poem.STATUS_DRAFT};

        return query(selection, selectionArgs);
    }

    public Cursor listPoems() {
        final String selection = MyPoem.Poem.COLUMN_NAME_STATUS + " = ?";
        final String[] selectionArgs = {MyPoem.Poem.STATUS_FINISHED};

        return query(selection, selectionArgs);
    }

    public Poem get(long id) {
        final String selection = MyPoem.Poem._ID + " = ?";
        final String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = query(selection, selectionArgs);
        if (cursor.moveToFirst()) {
            Poem poem = getPoem(cursor);

            cursor.close();

            return poem;
        }

        return null;
    }

    private Poem getPoem(Cursor cursor) {
        Poem poem = new Poem();
        poem.setId(cursor.getLong(cursor.getColumnIndex(MyPoem.Poem._ID)));
        poem.setTitle(cursor.getString(cursor.getColumnIndex(MyPoem.Poem.COLUMN_NAME_TITLE)));
        poem.setSubtitle(cursor.getString(cursor.getColumnIndex(MyPoem.Poem.COLUMN_NAME_SUBTITLE)));
        poem.setAuthor(cursor.getString(cursor.getColumnIndex(MyPoem.Poem.COLUMN_NAME_AUTHOR)));
        poem.setCreated(cursor.getString(cursor.getColumnIndex(MyPoem.Poem.COLUMN_NAME_CREATE_DATE)));
        poem.setUpdated(cursor.getString(cursor.getColumnIndex(MyPoem.Poem.COLUMN_NAME_UPDATE_TIME)));
        poem.setContent(cursor.getString(cursor.getColumnIndex(MyPoem.Poem.COLUMN_NAME_CONTENT)));
        poem.setStatus(cursor.getString(cursor.getColumnIndex(MyPoem.Poem.COLUMN_NAME_STATUS)));
        poem.setType(cursor.getString(cursor.getColumnIndex(MyPoem.Poem.COLUMN_NAME_TYPE)));
        return poem;
    }

    public Cursor query(String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        return db.query(
                MyPoem.Poem.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    public int delete(long id) {
        final String selection = MyPoem.Poem._ID + " = ?";
        final String[] selectionArgs = {String.valueOf(id)};

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            return db.delete(MyPoem.Poem.TABLE_NAME, selection, selectionArgs);
        } finally {
            db.close();
        }
    }

}
