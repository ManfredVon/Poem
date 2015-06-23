package com.fmf.poem.data;

/**
 * Created by fmf on 15/6/3.
 */
public interface PoemSqlExpr extends SqlExpr {
    String TYPE_TEXT = " TEXT";
    String TYPE_INTEGER = " INTEGER";
    String TYPE_DATE = " DATE";
    String TYPE_DATETIME = " DATETIME";

    String SQL_CREATE_POEM = new StringBuffer()
            .append("CREATE TABLE ").append(PoemContract.Poem.TABLE_NAME).append(BRACKET_LEFT)
            .append(PoemContract.Poem._ID).append(" INTEGER PRIMARY KEY,")
            .append(PoemContract.Poem.TITLE).append(TYPE_TEXT).append(COMMA)
            .append(PoemContract.Poem.SUBTITLE).append(TYPE_TEXT).append(COMMA)
            .append(PoemContract.Poem.AUTHOR).append(TYPE_TEXT).append(COMMA)
            .append(PoemContract.Poem.CREATED).append(TYPE_DATE).append(COMMA)
            .append(PoemContract.Poem.UPDATED).append(TYPE_DATETIME).append(COMMA)
            .append(PoemContract.Poem.CONTENT).append(TYPE_TEXT).append(COMMA)
            .append(PoemContract.Poem.STATUS).append(TYPE_TEXT).append(COMMA)
            .append(PoemContract.Poem.TYPE).append(TYPE_TEXT).append(COMMA)
            .append(PoemContract.Poem.RHYTHM_ID).append(TYPE_INTEGER)
            .append(BRACKET_RIGHT).toString();
    
    String SQL_INSERT_POEM = new StringBuffer()
            .append("INSERT INTO ").append(PoemContract.Poem.TABLE_NAME).append(BRACKET_LEFT)
            .append(PoemContract.Poem.TITLE).append(COMMA)
            .append(PoemContract.Poem.SUBTITLE).append(COMMA)
            .append(PoemContract.Poem.AUTHOR).append(COMMA)
            .append(PoemContract.Poem.CREATED).append(COMMA)
            .append(PoemContract.Poem.UPDATED).append(COMMA)
            .append(PoemContract.Poem.CONTENT).append(COMMA)
            .append(PoemContract.Poem.STATUS).append(COMMA)
            .append(PoemContract.Poem.TYPE).append(COMMA)
            .append(PoemContract.Poem.RHYTHM_ID)
            .append(BRACKET_RIGHT)
            .append("VALUES (?,?,?,?,?,?,?,?,?)").toString();

    String SQL_DROP_POEM =
            "DROP TABLE IF EXISTS " + PoemContract.Poem.TABLE_NAME;

    String SQL_CREATE_RHYTHM = new StringBuffer()
            .append("CREATE TABLE ").append(PoemContract.Rhythm.TABLE_NAME).append(BRACKET_LEFT)
            .append(PoemContract.Poem._ID).append(" INTEGER PRIMARY KEY,")
            .append(PoemContract.Rhythm.NAME).append(TYPE_TEXT).append(COMMA)
            .append(PoemContract.Rhythm.ALIAS).append(TYPE_TEXT).append(COMMA)
            .append(PoemContract.Rhythm.INTRO).append(TYPE_TEXT).append(COMMA)
            .append(PoemContract.Rhythm.COUNT).append(TYPE_INTEGER).append(COMMA)
            .append(PoemContract.Rhythm.METRE).append(TYPE_DATE).append(COMMA)
            .append(PoemContract.Rhythm.SAMPLE).append(TYPE_DATETIME).append(COMMA)
            .append(PoemContract.Rhythm.COMMENT).append(TYPE_TEXT).append(COMMA)
            .append(PoemContract.Rhythm.TYPE).append(TYPE_TEXT)
            .append(BRACKET_RIGHT).toString();

    String SQL_INSERT_RHYTHM = new StringBuffer()
            .append("INSERT INTO ").append(PoemContract.Rhythm.TABLE_NAME).append(BRACKET_LEFT)
            .append(PoemContract.Rhythm.NAME).append(COMMA)
            .append(PoemContract.Rhythm.ALIAS).append(COMMA)
            .append(PoemContract.Rhythm.INTRO).append(COMMA)
            .append(PoemContract.Rhythm.COUNT).append(COMMA)
            .append(PoemContract.Rhythm.METRE).append(COMMA)
            .append(PoemContract.Rhythm.SAMPLE).append(COMMA)
            .append(PoemContract.Rhythm.COMMENT).append(COMMA)
            .append(PoemContract.Rhythm.TYPE)
            .append(BRACKET_RIGHT)
            .append("VALUES (?,?,?,?,?,?,?,?)").toString();

    String SQL_DELETE_RHYTHM =
            "DROP TABLE IF EXISTS " + PoemContract.Rhythm.TABLE_NAME;
}
