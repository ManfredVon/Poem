package com.fmf.mypoem.data;

import android.provider.BaseColumns;

/**
 * Created by fmf on 15/4/2.
 */
public final class MyPoem {

    private MyPoem() {
    }

    public static final class Poem implements BaseColumns {
        private Poem() {
        }

        public static final String STATUS_DRAFT = "draft";
        public static final String STATUS_FINISHED = "finished";
        public static final String TYPE_SHI = "shi";
        public static final String TYPE_CI = "ci";


        public static final String TABLE_NAME = "poem";
//        public static final String COLUMN_NAME_POEM_ID = "poemid";

        public static final String COLUMN_NAME_TITLE = "title";

        public static final String COLUMN_NAME_SUBTITLE = "subtitle";

        public static final String COLUMN_NAME_AUTHOR = "author";

        public static final String COLUMN_NAME_CREATE_DATE = "created";

        public static final String COLUMN_NAME_UPDATE_TIME = "updated";

        public static final String COLUMN_NAME_CONTENT = "content";

        public static final String COLUMN_NAME_STATUS = "status";

        public static final String COLUMN_NAME_TYPE = "type";

//        public static final String COLUMN_NAME_ = "";

    }

    public static final class Rhythm implements BaseColumns {
        private Rhythm() {
        }

        public static final String TYPE_SHI = "shi";
        public static final String TYPE_CI = "ci";
        public static final String ALIAS_SEPARATOR = "、";

        public static final String TABLE_NAME = "rhythm";

        public static final String COLUMN_NAME_NAME = "name";

        public static final String COLUMN_NAME_ALIAS = "alias";

        public static final String COLUMN_NAME_INTRO = "intro";

        public static final String COLUMN_NAME_COUNT = "count";

        public static final String COLUMN_NAME_METRE = "metre";

        public static final String COLUMN_NAME_SAMPLE = "sample";

        public static final String COLUMN_NAME_COMMENT = "comment";

        public static final String COLUMN_NAME_TYPE = "type";

//        public static final String COLUMN_NAME_ = "";

    }


}
