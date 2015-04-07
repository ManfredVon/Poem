package com.fmf.mypoem.data;

import android.provider.BaseColumns;

/**
 * Created by fmf on 15/4/2.
 */
public final class MyPoem {

    private MyPoem(){}

    public static final class Poem implements BaseColumns {
        private Poem(){ }

        public static final String STATUS_DRAFT = "draft";
        public static final String STATUS_FINISHED = "finished";


        public static final String TABLE_NAME = "poem";
//        public static final String COLUMN_NAME_POEM_ID = "poemid";

        /**
         * Column name for the title of the pome
         * <P>Type: TEXT</P>
         */
        public static final String COLUMN_NAME_TITLE = "title";

        /**
         * Column name for the subtitle of the poem
         * <P>Type: TEXT</P>
         */
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";

        /**
         * Column name for the author of the poem
         * <P>Type: TEXT</P>
         */
        public static final String COLUMN_NAME_AUTHOR = "author";

        /**
         * Column name for the creation timestamp
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
         */
        public static final String COLUMN_NAME_CREATE_DATE = "created";

        /**
         * Column name for the creation timestamp
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
         */
        public static final String COLUMN_NAME_UPDATE_TIME = "updated";

        /**
         * Column name for the content of the poem
         * <P>Type: TEXT</P>
         */
        public static final String COLUMN_NAME_CONTENT = "content";

        /**
         * Column name for the subtitle of the poem
         * <P>Type: TEXT</P>
         */
        public static final String COLUMN_NAME_STATUS = "status";

        /**
         * Column name for the type of the poem
         * <P>Type: TEXT</P>
         */
        public static final String COLUMN_NAME_TYPE = "type";
//        public static final String COLUMN_NAME_ = "";

    }


}
