package com.fmf.mypoem.data;

/**
 * Created by fmf on 15/6/3.
 */
public interface PoemSqlExpr extends SqlExpr {
    String TYPE_TEXT = " TEXT";
    String TYPE_INTEGER = " INTEGER";
    String TYPE_DATE = " DATE";
    String TYPE_DATETIME = " DATETIME";

    String SQL_CREATE_POEM = new StringBuffer()
            .append("CREATE TABLE ").append(MyPoem.Poem.TABLE_NAME).append(BRACKET_LEFT)
            .append(MyPoem.Poem._ID).append(" INTEGER PRIMARY KEY,")
            .append(MyPoem.Poem.TITLE).append(TYPE_TEXT).append(COMMA)
            .append(MyPoem.Poem.SUBTITLE).append(TYPE_TEXT).append(COMMA)
            .append(MyPoem.Poem.AUTHOR).append(TYPE_TEXT).append(COMMA)
            .append(MyPoem.Poem.CREATED).append(TYPE_DATE).append(COMMA)
            .append(MyPoem.Poem.UPDATED).append(TYPE_DATETIME).append(COMMA)
            .append(MyPoem.Poem.CONTENT).append(TYPE_TEXT).append(COMMA)
            .append(MyPoem.Poem.STATUS).append(TYPE_TEXT).append(COMMA)
            .append(MyPoem.Poem.TYPE).append(TYPE_TEXT).append(COMMA)
            .append(MyPoem.Poem.RHYTHM_ID).append(TYPE_INTEGER)
            .append(BRACKET_RIGHT).toString();
    
    String SQL_INSERT_POEM = new StringBuffer()
            .append("INSERT INTO ").append(MyPoem.Poem.TABLE_NAME).append(BRACKET_LEFT)
            .append(MyPoem.Poem.TITLE).append(COMMA)
            .append(MyPoem.Poem.SUBTITLE).append(COMMA)
            .append(MyPoem.Poem.AUTHOR).append(COMMA)
            .append(MyPoem.Poem.CREATED).append(COMMA)
            .append(MyPoem.Poem.UPDATED).append(COMMA)
            .append(MyPoem.Poem.CONTENT).append(COMMA)
            .append(MyPoem.Poem.STATUS).append(COMMA)
            .append(MyPoem.Poem.TYPE).append(COMMA)
            .append(MyPoem.Poem.RHYTHM_ID)
            .append(BRACKET_RIGHT)
            .append("VALUES (?,?,?,?,?,?,?,?,?)").toString();

    String SQL_DROP_POEM =
            "DROP TABLE IF EXISTS " + MyPoem.Poem.TABLE_NAME;

    String SQL_CREATE_RHYTHM = new StringBuffer()
            .append("CREATE TABLE ").append(MyPoem.Rhythm.TABLE_NAME).append(BRACKET_LEFT)
            .append(MyPoem.Poem._ID).append(" INTEGER PRIMARY KEY,")
            .append(MyPoem.Rhythm.NAME).append(TYPE_TEXT).append(COMMA)
            .append(MyPoem.Rhythm.ALIAS).append(TYPE_TEXT).append(COMMA)
            .append(MyPoem.Rhythm.INTRO).append(TYPE_TEXT).append(COMMA)
            .append(MyPoem.Rhythm.COUNT).append(TYPE_INTEGER).append(COMMA)
            .append(MyPoem.Rhythm.METRE).append(TYPE_DATE).append(COMMA)
            .append(MyPoem.Rhythm.SAMPLE).append(TYPE_DATETIME).append(COMMA)
            .append(MyPoem.Rhythm.COMMENT).append(TYPE_TEXT).append(COMMA)
            .append(MyPoem.Rhythm.TYPE).append(TYPE_TEXT)
            .append(BRACKET_RIGHT).toString();

    String SQL_INSERT_RHYTHM = new StringBuffer()
            .append("INSERT INTO ").append(MyPoem.Rhythm.TABLE_NAME).append(BRACKET_LEFT)
            .append(MyPoem.Rhythm.NAME).append(COMMA)
            .append(MyPoem.Rhythm.ALIAS).append(COMMA)
            .append(MyPoem.Rhythm.INTRO).append(COMMA)
            .append(MyPoem.Rhythm.COUNT).append(COMMA)
            .append(MyPoem.Rhythm.METRE).append(COMMA)
            .append(MyPoem.Rhythm.SAMPLE).append(COMMA)
            .append(MyPoem.Rhythm.COMMENT).append(COMMA)
            .append(MyPoem.Rhythm.TYPE)
            .append(BRACKET_RIGHT)
            .append("VALUES (?,?,?,?,?,?,?,?)").toString();

    String SQL_DELETE_RHYTHM =
            "DROP TABLE IF EXISTS " + MyPoem.Rhythm.TABLE_NAME;
}
