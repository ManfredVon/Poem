package com.fmf.mypoem.data;

/**
 * Created by fmf on 15/6/3.
 */
public interface PoemSqlExpr extends SqlExpr {
    String TYPE_TEXT = " TEXT";
    String TYPE_INT = " INT";
    String TYPE_DATE = " DATE";
    String TYPE_DATETIME = " DATETIME";

    String SQL_CREATE_POEM = new StringBuffer()
            .append("CREATE TABLE ").append(MyPoem.Poem.TABLE_NAME).append(BRACKET_LEFT)
            .append(MyPoem.Poem._ID).append(" INTEGER PRIMARY KEY,")
            .append(MyPoem.Poem.COLUMN_NAME_TITLE).append(TYPE_TEXT).append(COMMA)
            .append(MyPoem.Poem.COLUMN_NAME_SUBTITLE).append(TYPE_TEXT).append(COMMA)
            .append(MyPoem.Poem.COLUMN_NAME_AUTHOR).append(TYPE_TEXT).append(COMMA)
            .append(MyPoem.Poem.COLUMN_NAME_CREATE_DATE).append(TYPE_DATE).append(COMMA)
            .append(MyPoem.Poem.COLUMN_NAME_UPDATE_TIME).append(TYPE_DATETIME).append(COMMA)
            .append(MyPoem.Poem.COLUMN_NAME_CONTENT).append(TYPE_TEXT).append(COMMA)
            .append(MyPoem.Poem.COLUMN_NAME_STATUS).append(TYPE_TEXT).append(COMMA)
            .append(MyPoem.Poem.COLUMN_NAME_TYPE).append(TYPE_TEXT)
            .append(BRACKET_RIGHT).toString();
    
    String SQL_INSERT_POEM = new StringBuffer()
            .append("INSERT INTO ").append(MyPoem.Poem.TABLE_NAME).append(BRACKET_LEFT)
            .append(MyPoem.Poem.COLUMN_NAME_TITLE).append(COMMA)
            .append(MyPoem.Poem.COLUMN_NAME_SUBTITLE).append(COMMA)
            .append(MyPoem.Poem.COLUMN_NAME_AUTHOR).append(COMMA)
            .append(MyPoem.Poem.COLUMN_NAME_CREATE_DATE).append(COMMA)
            .append(MyPoem.Poem.COLUMN_NAME_UPDATE_TIME).append(COMMA)
            .append(MyPoem.Poem.COLUMN_NAME_CONTENT).append(COMMA)
            .append(MyPoem.Poem.COLUMN_NAME_STATUS).append(COMMA)
            .append(MyPoem.Poem.COLUMN_NAME_TYPE)
            .append(BRACKET_RIGHT)
            .append("VALUES (?,?,?,?,?,?,?,?)").toString();

    String SQL_DELETE_POEM =
            "DROP TABLE IF EXISTS " + MyPoem.Poem.TABLE_NAME;

    String SQL_CREATE_RHYTHM = new StringBuffer()
            .append("CREATE TABLE ").append(MyPoem.Rhythm.TABLE_NAME).append(BRACKET_LEFT)
            .append(MyPoem.Poem._ID).append(" INTEGER PRIMARY KEY,")
            .append(MyPoem.Rhythm.COLUMN_NAME_NAME).append(TYPE_TEXT).append(COMMA)
            .append(MyPoem.Rhythm.COLUMN_NAME_ALIAS).append(TYPE_TEXT).append(COMMA)
            .append(MyPoem.Rhythm.COLUMN_NAME_INTRO).append(TYPE_TEXT).append(COMMA)
            .append(MyPoem.Rhythm.COLUMN_NAME_COUNT).append(TYPE_INT).append(COMMA)
            .append(MyPoem.Rhythm.COLUMN_NAME_METRE).append(TYPE_DATE).append(COMMA)
            .append(MyPoem.Rhythm.COLUMN_NAME_SAMPLE).append(TYPE_DATETIME).append(COMMA)
            .append(MyPoem.Rhythm.COLUMN_NAME_COMMENT).append(TYPE_TEXT).append(COMMA)
            .append(MyPoem.Rhythm.COLUMN_NAME_TYPE).append(TYPE_TEXT)
            .append(BRACKET_RIGHT).toString();

    String SQL_INSERT_RHYTHM = new StringBuffer()
            .append("INSERT INTO ").append(MyPoem.Rhythm.TABLE_NAME).append(BRACKET_LEFT)
            .append(MyPoem.Rhythm.COLUMN_NAME_NAME).append(COMMA)
            .append(MyPoem.Rhythm.COLUMN_NAME_ALIAS).append(COMMA)
            .append(MyPoem.Rhythm.COLUMN_NAME_INTRO).append(COMMA)
            .append(MyPoem.Rhythm.COLUMN_NAME_COUNT).append(COMMA)
            .append(MyPoem.Rhythm.COLUMN_NAME_METRE).append(COMMA)
            .append(MyPoem.Rhythm.COLUMN_NAME_SAMPLE).append(COMMA)
            .append(MyPoem.Rhythm.COLUMN_NAME_COMMENT).append(COMMA)
            .append(MyPoem.Rhythm.COLUMN_NAME_TYPE)
            .append(BRACKET_RIGHT)
            .append("VALUES (?,?,?,?,?,?,?,?)").toString();

    String SQL_DELETE_RHYTHM =
            "DROP TABLE IF EXISTS " + MyPoem.Rhythm.TABLE_NAME;
}
