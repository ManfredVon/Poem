package com.fmf.mypoem.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.fmf.mypoem.model.Model;
import com.fmf.mypoem.model.Poem;
import com.fmf.mypoem.util.PoemLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fmf on 15/4/7.
 */
public abstract class MyPoemDao<T extends Model> {
    protected MyPoemDbHelper dbHelper;

    public MyPoemDbHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(MyPoemDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public MyPoemDao(Context context) {
        this.dbHelper = new MyPoemDbHelper(context);
    }

    protected abstract String getTableName();

    protected abstract T getModel(Cursor cursor);

    protected abstract ContentValues getContentValues(T model, boolean allowNull);

    protected String[] getProjection(){
        return null; //return all columns
    }

    protected String getSortOrder(){
        final String sortOrder = BaseColumns._ID + " ASC";
        return sortOrder;
    }

    public long save(T model) {
        ContentValues values = getContentValues(model, false);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            return db.insert(getTableName(), null, values);
        } finally {
            db.close();
        }
    }

    public int update(T model) {
        final String selection = BaseColumns._ID + " = ?";
        final String[] selectionArgs = {String.valueOf(model.getId())};
        ContentValues values = getContentValues(model, false);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            return db.update(getTableName(), values, selection, selectionArgs);
        } finally {
            db.close();
        }
    }

    public T get(long id) {
        final String selection = BaseColumns._ID + " = ?";
        final String[] selectionArgs = {String.valueOf(id)};

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor =  db.query(
                getTableName(),
                getProjection(),
                selection,
                selectionArgs,
                null,
                null,
                getSortOrder()
        );

        try {
            if (cursor.moveToFirst()) {
                T model = getModel(cursor);

                return model;
            }

            return null;

        } finally {
            cursor.close();
            db.close();
        }
    }

    public Cursor query(String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        return db.query(
                getTableName(),
                getProjection(),
                selection,
                selectionArgs,
                null,
                null,
                getSortOrder()
        );
    }

    @NonNull
    public List<T> list(String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor =  db.query(
                getTableName(),
                getProjection(),
                selection,
                selectionArgs,
                null,
                null,
                getSortOrder()
        );

        try {
            List<T> list = new ArrayList<>();
            while (cursor.moveToNext()){
                T item = getModel(cursor);
                list.add(item);
            }

            return list;

        } finally {
            cursor.close();
            db.close();
        }
    }

    public int delete(long id) {
        final String selection = BaseColumns._ID + " = ?";
        final String[] selectionArgs = {String.valueOf(id)};

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            return db.delete(getTableName(), selection, selectionArgs);
        } finally {
            db.close();
        }
    }

}
