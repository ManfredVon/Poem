package com.fmf.mypoem.data;

import android.provider.BaseColumns;

/**
 * Created by fmf on 15/4/2.
 */
public final class MyPoem {
    public static final String TYPE_SHI = "shi";
    public static final String TYPE_CI = "ci";
    public static final String TYPE_SHI_ZH = "诗";
    public static final String TYPE_CI_ZH = "词";

    private MyPoem() {
    }

    public static final class Poem implements BaseColumns {
        private Poem() {
        }

        public static final String STATUS_DRAFT = "draft";
        public static final String STATUS_FINISHED = "finished";

        public static final String TABLE_NAME = "poem";

        public static final String TITLE = "title";

        public static final String SUBTITLE = "subtitle";

        public static final String AUTHOR = "author";

        public static final String CREATED = "created";

        public static final String UPDATED = "updated";

        public static final String CONTENT = "content";

        public static final String STATUS = "status";

        public static final String TYPE = "type";

        public static final String RHYTHM_ID = "rhythm_id";

    }

    public static final class Rhythm implements BaseColumns {
        private Rhythm() {
        }

        public static final String TABLE_NAME = "rhythm";

        public static final String NAME = "name";

        public static final String ALIAS = "alias";

        public static final String INTRO = "intro";

        public static final String COUNT = "count";

        public static final String METRE = "metre";

        public static final String SAMPLE = "sample";

        public static final String COMMENT = "comment";

        public static final String TYPE = "type";

    }


}
