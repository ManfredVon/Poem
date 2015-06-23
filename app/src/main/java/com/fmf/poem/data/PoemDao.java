package com.fmf.poem.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;

import com.fmf.poem.model.Poem;
import com.fmf.poem.poem.PoemLog;
import com.fmf.poem.util.StringUtil;

import java.util.List;

/**
 * Created by fmf on 15/4/7.
 */
public class PoemDao extends BasePoemDao<Poem> {
    private static final String[] PROJECTION = {
            PoemContract.Poem._ID,
            PoemContract.Poem.TITLE,
            PoemContract.Poem.SUBTITLE,
            PoemContract.Poem.AUTHOR,
            PoemContract.Poem.CREATED,
            PoemContract.Poem.UPDATED,
            PoemContract.Poem.CONTENT,
            PoemContract.Poem.STATUS,
            PoemContract.Poem.TYPE,
            PoemContract.Poem.RHYTHM_ID
    };

    private static final String SORT_ORDER = PoemContract.Poem.UPDATED + " DESC";

    public PoemDao(Context context) {
        super(context);
    }

    @Override
    protected String getTableName() {
        return PoemContract.Poem.TABLE_NAME;
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

    public static Poem getPoem(Cursor cursor) {
        Poem poem = new Poem();
        poem.setId(cursor.getLong(cursor.getColumnIndex(PoemContract.Poem._ID)));
        poem.setTitle(cursor.getString(cursor.getColumnIndex(PoemContract.Poem.TITLE)));
        poem.setSubtitle(cursor.getString(cursor.getColumnIndex(PoemContract.Poem.SUBTITLE)));
        poem.setAuthor(cursor.getString(cursor.getColumnIndex(PoemContract.Poem.AUTHOR)));
        poem.setCreated(cursor.getString(cursor.getColumnIndex(PoemContract.Poem.CREATED)));
        poem.setUpdated(cursor.getString(cursor.getColumnIndex(PoemContract.Poem.UPDATED)));
        poem.setContent(cursor.getString(cursor.getColumnIndex(PoemContract.Poem.CONTENT)));
        poem.setStatus(cursor.getString(cursor.getColumnIndex(PoemContract.Poem.STATUS)));
        poem.setType(cursor.getString(cursor.getColumnIndex(PoemContract.Poem.TYPE)));
        poem.setRhythmId(cursor.getLong(cursor.getColumnIndex(PoemContract.Poem.RHYTHM_ID)));
        return poem;
    }

    protected ContentValues getContentValues(Poem poem) {
        ContentValues values = new ContentValues();
        values.put(PoemContract.Poem.TITLE, poem.getTitle());
        values.put(PoemContract.Poem.SUBTITLE, poem.getSubtitle());
        values.put(PoemContract.Poem.AUTHOR, poem.getAuthor());
        values.put(PoemContract.Poem.CREATED, poem.getCreated());
        values.put(PoemContract.Poem.UPDATED, poem.getUpdated());
        values.put(PoemContract.Poem.CONTENT, poem.getContent());
        values.put(PoemContract.Poem.STATUS, poem.getStatus());
        values.put(PoemContract.Poem.TYPE, poem.getType());
        values.put(PoemContract.Poem.TYPE, poem.getRhythmId());
        return values;
    }

    private List<Poem> listByStatus(String status) {
        final String selection = PoemContract.Poem.STATUS + EQUAL_QUESTION_MARK;
        final String[] selectionArgs = {status};

        return list(selection, selectionArgs);
    }

    public List<Poem> listDraft() {
        return listByStatus(PoemContract.Poem.STATUS_DRAFT);
    }

    public List<Poem> listPoem() {
        return listByStatus(PoemContract.Poem.STATUS_FINISHED);
    }

    private List<Poem> listByType(String type) {
        final String selection = PoemContract.Poem.TYPE + EQUAL_QUESTION_MARK;
        final String[] selectionArgs = {type};

        return list(selection, selectionArgs);
    }

    public List<Poem> listShi() {
        return listByType(PoemContract.TYPE_SHI);
    }

    public List<Poem> listCi() {
        return listByType(PoemContract.TYPE_CI);
    }

    private Cursor queryByStatus(String status) {
        final String selection = PoemContract.Poem.STATUS + EQUAL_QUESTION_MARK;
        final String[] selectionArgs = {status};

        return query(selection, selectionArgs);
    }

    public Cursor queryDraft() {
        return queryByStatus(PoemContract.Poem.STATUS_DRAFT);
    }

    public Cursor queryPoem() {
        return queryByStatus(PoemContract.Poem.STATUS_FINISHED);
    }

    private Cursor queryByType(String type) {
        final String selection = PoemContract.Poem.TYPE + EQUAL_QUESTION_MARK;
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
        StringBuilder sb = new StringBuilder();
        sb.append(PoemContract.Poem.SUBTITLE).append(LIKE_QUESTION_MARK);
        sb.append(OR);
        sb.append(PoemContract.Poem.SUBTITLE).append(LIKE_QUESTION_MARK);
        sb.append(OR);
        sb.append(PoemContract.Poem.CONTENT).append(LIKE_QUESTION_MARK);
        sb.append(OR);
        sb.append(PoemContract.Poem.CREATED).append(LIKE_QUESTION_MARK);

        final String selection = sb.toString();
        text = StringUtil.wrap(text, PERCENT);
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
        sb.append(PoemContract.Poem.STATUS).append(EQUAL_QUESTION_MARK);
        sb.append(AND);
        sb.append(BRACKET_LEFT);
        sb.append(PoemContract.Poem.TITLE).append(LIKE_QUESTION_MARK);
        sb.append(OR);
        sb.append(PoemContract.Poem.SUBTITLE).append(LIKE_QUESTION_MARK);
        sb.append(OR);
        sb.append(PoemContract.Poem.CONTENT).append(LIKE_QUESTION_MARK);
        sb.append(OR);
        sb.append(PoemContract.Poem.CREATED).append(LIKE_QUESTION_MARK);
        sb.append(BRACKET_RIGHT);

        final String selection = sb.toString();
        PoemLog.i(selection);
        text = StringUtil.wrap(text, PERCENT);
        final String[] selectionArgs = {status, text, text, text, text};

        return query(selection, selectionArgs);
    }

    public Cursor queryDraft(String text) {
        return query(PoemContract.Poem.STATUS_DRAFT, text);
    }

    public Cursor queryPoem(String text) {
        return query(PoemContract.Poem.STATUS_FINISHED, text);
    }

    public void save(List<Poem> poems) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            SQLiteStatement stat = db.compileStatement(SQL_INSERT_POEM);
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
        } finally {
            db.close();
        }
    }

}
