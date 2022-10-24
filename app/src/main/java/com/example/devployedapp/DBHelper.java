package com.example.devployedapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.webparser.data.JobListing;

public class DBHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "all_jobs.db";
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_TABLE = "JOBS";
    static final String JOB_ID = "_ID";
    static final String JOB_TITLE = "job_title";
    static final String JOB_STATUS = "job_status";
    static final String JOB_LOCATION = "job_location";
    static final String JOB_COMPANY_NAME = "job_company_name";
    static final String JOB_DESCRIPTION = "job_description";
    static final String JOB_ADDITIONAL_INFORMATION = "job_additional_information";
    static final String JOB_TYPE = "job_type";
    static final String JOB_URL = "job_url";

    private static final String CREATE_DB_QUERY = "CREATE TABLE " + DATABASE_TABLE + " ( " + JOB_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + JOB_TITLE + " TEXT NOT NULL, " + JOB_STATUS + " VARCHAR NOT NULL, "
            + JOB_LOCATION + " VARCHAR NOT NULL, " + JOB_COMPANY_NAME + " VARCHAR NOT NULL, " + JOB_DESCRIPTION + " TEXT NOT NULL, "
            + JOB_TYPE + " TEXT NOT NULL, " + JOB_URL + "  TEXT NOT NULL, " + JOB_ADDITIONAL_INFORMATION + " TEXT NOT NULL);";

    public DBHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL(CREATE_DB_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL( "DROP TABLE IF EXISTS " + DATABASE_TABLE);
    }
}
