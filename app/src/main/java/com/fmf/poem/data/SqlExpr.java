package com.fmf.poem.data;

/**
 * Created by fmf on 15/6/3.
 */
public interface SqlExpr {
    String LIKE_QUESTION_MARK = " LIKE ?";
    String EQUAL_QUESTION_MARK = " = ?";
    String OR = " OR ";
    String AND = " AND ";
    String BRACKET_LEFT = " ( ";
    String BRACKET_RIGHT = " ) ";
    String COMMA = ", ";
    String PERCENT = "%";
}
