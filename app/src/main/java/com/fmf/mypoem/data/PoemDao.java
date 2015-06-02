package com.fmf.mypoem.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.fmf.mypoem.model.Model;
import com.fmf.mypoem.model.Poem;
import com.fmf.mypoem.util.PoemLog;
import com.fmf.mypoem.util.StringUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by fmf on 15/4/7.
 */
public class PoemDao extends MyPoemDao<Poem> {
    private static final String[] PROJECTION = {
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

    private static final String SORT_ORDER = MyPoem.Poem.COLUMN_NAME_UPDATE_TIME + " DESC";

    public PoemDao(Context context) {
        super(context);
    }

    @Override
    protected String getTableName() {
        return MyPoem.Poem.TABLE_NAME;
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
    protected Poem getModel(Cursor cursor) {
        return getPoem(cursor);
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

    protected ContentValues getContentValues(Poem poem, boolean allowNull) {
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

    private List<Poem> listByStatus(String status) {
        final String selection = MyPoem.Poem.COLUMN_NAME_STATUS + EXPR_EQUAL;
        final String[] selectionArgs = {status};

        return list(selection, selectionArgs);
    }

    public List<Poem> listDraft() {
        return listByStatus(MyPoem.Poem.STATUS_DRAFT);
    }

    public List<Poem> listPoem() {
        return listByStatus(MyPoem.Poem.STATUS_FINISHED);
    }

    private List<Poem> listByType(String type) {
        final String selection = MyPoem.Poem.COLUMN_NAME_TYPE + EXPR_EQUAL;
        final String[] selectionArgs = {type};

        return list(selection, selectionArgs);
    }

    public List<Poem> listShi() {
        return listByType(MyPoem.TYPE_SHI);
    }

    public List<Poem> listCi() {
        return listByType(MyPoem.TYPE_CI);
    }

    private Cursor queryByStatus(String status) {
        final String selection = MyPoem.Poem.COLUMN_NAME_STATUS + EXPR_EQUAL;
        final String[] selectionArgs = {status};

        return query(selection, selectionArgs);
    }

    public Cursor queryDraft() {
        return queryByStatus(MyPoem.Poem.STATUS_DRAFT);
    }

    public Cursor queryPoem() {
        return queryByStatus(MyPoem.Poem.STATUS_FINISHED);
    }

    private Cursor queryByType(String type) {
        final String selection = MyPoem.Poem.COLUMN_NAME_TYPE + EXPR_EQUAL;
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
        StringBuilder sb = new StringBuilder();
        sb.append(MyPoem.Poem.COLUMN_NAME_SUBTITLE).append(EXPR_LIKE);
        sb.append(EXPR_OR);
        sb.append(MyPoem.Poem.COLUMN_NAME_SUBTITLE).append(EXPR_LIKE);
        sb.append(EXPR_OR);
        sb.append(MyPoem.Poem.COLUMN_NAME_CONTENT).append(EXPR_LIKE);
        sb.append(EXPR_OR);
        sb.append(MyPoem.Poem.COLUMN_NAME_CREATE_DATE).append(EXPR_LIKE);

        final String selection = sb.toString();
        final String[] selectionArgs = {text, text, text, text};

        return query(selection, selectionArgs);
    }

    public Cursor query(String status, String text) {
        boolean noStatus = TextUtils.isEmpty(status);
        boolean noText = TextUtils.isEmpty(text);

        if (noStatus && noText) {
            return queryAll();
        }

        if (!noStatus && noText) {
            return queryByStatus(status);
        }

        if (noStatus && !noText) {
            return query(text);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(MyPoem.Poem.COLUMN_NAME_STATUS).append(EXPR_EQUAL);
        sb.append(EXPR_AND);
        sb.append(EXPR_BRACKET_LEFT);
        sb.append(MyPoem.Poem.COLUMN_NAME_TITLE).append(EXPR_LIKE);
        sb.append(EXPR_OR);
        sb.append(MyPoem.Poem.COLUMN_NAME_SUBTITLE).append(EXPR_LIKE);
        sb.append(EXPR_OR);
        sb.append(MyPoem.Poem.COLUMN_NAME_CONTENT).append(EXPR_LIKE);
        sb.append(EXPR_OR);
        sb.append(MyPoem.Poem.COLUMN_NAME_CREATE_DATE).append(EXPR_LIKE);
        sb.append(EXPR_BRACKET_RIGHT);

        final String selection = sb.toString();
        PoemLog.i(selection);
        text  = StringUtil.wrap(text, "%");
        final String[] selectionArgs = {status, text, text, text, text};

        return query(selection, selectionArgs);
    }

    public Cursor queryDraft(String text) {
        return query(MyPoem.Poem.STATUS_DRAFT, text);
    }

    public Cursor queryPoem(String text) {
        return query(MyPoem.Poem.STATUS_FINISHED, text);
    }

}
