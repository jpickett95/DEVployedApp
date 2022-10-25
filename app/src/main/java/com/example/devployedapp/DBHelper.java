package com.example.devployedapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*-- This table will record ALL of the jobs that the user rejects/saves. It will just
        -- give the ability to sort by that distinction (saved/rejected)
        CREATE TABLE IF NOT EXISTS all_jobs (
        job_id INTEGER PRIMARY KEY AUTOINCREMENT,
        job_title TEXT NOT NULL, -- As job names can often be long (this will be limited when I know the average length of a job title)
        job_status VARCHAR[8] NOT NULL, -- saved | rejected | applied | unseen | seen
        job_location VARCHAR[100] NOT NULL,
        job_company_name VARCHAR[20] NOT NULL,
        additional_information TEXT NOT NULL -- This will be a json string, that will need to be de-serialized when accessed
        );

        CREATE VIEW saved_jobs AS SELECT * FROM all_jobs WHERE job_status = 'saved';
        CREATE VIEW rejected_jobs AS SELECT * FROM all_jobs WHERE job_status = 'rejected';*/


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

// For a 'settings_table' database
/*
    CREATE TABLE IF NOT EXISTS  app_settings (
        setting_id INTEGER PRIMARY KEY AUTOINCREMENT,
        setting_name VARCHAR[20] NOT NULL,
        setting_type VARCHAR[10] NOT NULL, -- bool, integer, float (any raw data type)

    setting_value BLOB NOT NULL
    -- this is a raw save of the information given, we will
            -- require that the setting_type be filled out in order
            -- to know what type of data to return (at runtime)
            );*/
