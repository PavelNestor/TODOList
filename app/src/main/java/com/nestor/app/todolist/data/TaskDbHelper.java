package com.nestor.app.todolist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nestor.app.todolist.data.TaskContract.TaskEntry;

/**
 * Helper Db class for TODOList app
 * Created by Chief on 11.11.2017.
 */

public class TaskDbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "tasks.db";

    private static final int VERSION = 1;

    private String SQL_CREATE_TABLE =  "CREATE TABLE "  + TaskEntry.TABLE_NAME + " (" +
            TaskEntry._ID                + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TaskEntry.COLUMN_TITLE + " TEXT NOT NULL DEFAULT \"\", " +
            TaskEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL DEFAULT \"\", " +
            TaskEntry.COLUMN_STATE + " INTEGER NOT NULL DEFAULT 0, " +
            TaskEntry.COLUMN_PRIORITY    + " INTEGER NOT NULL);";

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskEntry.TABLE_NAME);
        onCreate(db);
    }
}
