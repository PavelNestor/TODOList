package com.nestor.app.todolist.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Contract class for TODOList app
 * Created by Chief on 11.11.2017.
 */

public final class TaskContract {

    public static final String AUTHORITY = "com.nestor.app.todolist";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_TASKS = "tasks";

    /**
     * TaskEntry is an inner class that defines the contents of the task table
     */
    public static final class TaskEntry implements BaseColumns {

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_TASKS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_TASKS;

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();

        // Task table and column names
        public static final String TABLE_NAME = "tasks";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PRIORITY = "priority";
        public static final String COLUMN_STATE = "state";

        public static final String DEFAULT_SORT_ORDER =
                COLUMN_STATE + " ASC, " + COLUMN_PRIORITY + " ASC";


        /**
         * Possible values for the priority of the task.
         */
        public static final int PRIORITY_HIGH = 1;
        public static final int PRIORITY_MEDIUM = 2;
        public static final int PRIORITY_LOW = 3;

        /**
         * Possible values for the checked column
         */
        public static final int TASK_COMPLETED = 1;
        public static final int TASK_NOT_COMPLETED = 0;


        /**
         * Returns whether or not the given priority is {@link #PRIORITY_HIGH}, {@link #PRIORITY_MEDIUM},
         * or {@link #PRIORITY_LOW}.
         */
        public static boolean isValidPriority(int priority) {
            if (priority == PRIORITY_HIGH || priority == PRIORITY_MEDIUM || priority == PRIORITY_LOW) {
                return true;
            }
            return false;
        }
    }
}
