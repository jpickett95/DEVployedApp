package com.example.devployedapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.webparser.data.JobListing;

import java.sql.DatabaseMetaData;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Collection;

import kotlinx.coroutines.Job;

public class DBManager {
    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    static final String UNSEEN = "unseen";
    static final String SEEN = "seen";
    static final String APPLIED = "applied";
    static final String SAVED = "saved";
    static final String REJECTED = "rejected";

    private String[] DBColumns = new String[] {DBHelper.JOB_ID, DBHelper.JOB_URL, DBHelper.JOB_COMPANY_NAME, DBHelper.JOB_DESCRIPTION, DBHelper.JOB_TITLE,
            DBHelper.JOB_LOCATION, DBHelper.JOB_STATUS, DBHelper.JOB_TYPE, DBHelper.JOB_ADDITIONAL_INFORMATION};

    public DBManager(Context cntx) {
        context = cntx;
        try {this.open();} catch (SQLDataException sqle) {sqle.printStackTrace();}
    }

    public DBManager open() throws SQLDataException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(JobListing job){
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.JOB_TITLE, job.GetJobTitle());
            contentValues.put(DBHelper.JOB_LOCATION, job.GetJobLocation());
            contentValues.put(DBHelper.JOB_COMPANY_NAME, "Company Name");
            contentValues.put(DBHelper.JOB_ADDITIONAL_INFORMATION, "Additional Info");
            contentValues.put(DBHelper.JOB_DESCRIPTION, job.GetJobDescription());
            contentValues.put(DBHelper.JOB_TYPE, job.GetJobType());
            contentValues.put(DBHelper.JOB_STATUS, UNSEEN);
            contentValues.put(DBHelper.JOB_URL, job.GetJobListingUrl());
            database.insert(DBHelper.DATABASE_TABLE, null, contentValues);
    }
    public void insert(int job_id, String job_title, String job_status, String job_location, String job_company_name, String additional_information){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.JOB_ID, job_id);
        contentValues.put(DBHelper.JOB_TITLE, job_title);
        contentValues.put(DBHelper.JOB_STATUS, job_status);
        contentValues.put(DBHelper.JOB_LOCATION, job_location);
        contentValues.put(DBHelper.JOB_COMPANY_NAME, job_company_name);
        contentValues.put(DBHelper.JOB_ADDITIONAL_INFORMATION, additional_information);
        database.insert(DBHelper.DATABASE_TABLE, null, contentValues);
    }

    public int update(long _id, JobListing job){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.JOB_TITLE, job.GetJobTitle());
        contentValues.put(DBHelper.JOB_LOCATION, job.GetJobLocation());
        //contentValues.put(DBHelper.JOB_COMPANY_NAME, "Company Name");
        //contentValues.put(DBHelper.JOB_ADDITIONAL_INFORMATION, "Additional Info");
        contentValues.put(DBHelper.JOB_DESCRIPTION, job.GetJobDescription());
        contentValues.put(DBHelper.JOB_TYPE, job.GetJobType());
        int result = database.update(DBHelper.DATABASE_TABLE, contentValues, DBHelper.JOB_ID + "=" + _id, null);
        return result;
    }
    public boolean updateData(int job_id, String job_title, String job_status, String job_location, String job_company_name, String additional_information){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.JOB_TITLE, job_title);
        contentValues.put(DBHelper.JOB_STATUS, job_status);
        contentValues.put(DBHelper.JOB_LOCATION, job_location);
        contentValues.put(DBHelper.JOB_COMPANY_NAME, job_company_name);
        contentValues.put(DBHelper.JOB_ADDITIONAL_INFORMATION, additional_information);
        Cursor cursor = database.rawQuery("Select * from DBHelper.DATABASE_TABLE where DBHelper.JOB_ID = ?", new String[] {String.valueOf(job_id)});
        if(cursor.getCount()>0) {

            long result = database.update(DBHelper.DATABASE_TABLE, contentValues, "job_id=?", new String[]{String.valueOf(job_id)});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }
    public int updateJobStatus(long job_id, String status){
        if((status == UNSEEN) || (status == SEEN) || (status == REJECTED) || (status == SAVED) || status == APPLIED) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.JOB_STATUS, status);
            int result = database.update(DBHelper.DATABASE_TABLE, contentValues, DBHelper.JOB_ID + "=" + job_id, null);
            return result;
        } else {return -1;}
    }

    public Cursor fetch(){
        Cursor cursor = database.query(DBHelper.DATABASE_TABLE, DBColumns, null, null, null,null, null);
        if(cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void delete(long _id){
        database.delete(DBHelper.DATABASE_TABLE, DBHelper.JOB_ID + "=" + _id, null);
    }

    public JobListing getFirstJobListing(){
        Cursor cursor = fetch();
        JobListing job = new JobListing();
        job.SetJobTitle(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.JOB_TITLE)));
        job.SetJobListingUrl(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.JOB_URL)));
        job.SetJobDescription(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.JOB_DESCRIPTION)));
        job.SetJobLocation(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.JOB_LOCATION)));
        job.SetJobType(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.JOB_TYPE)));
        return job;
    }
    public JobListing getNextJobListing(){
        JobListing job = new JobListing();
        Cursor cursor = database.query(DBHelper.DATABASE_TABLE, DBColumns, null, null, null,null, null);
        if(cursor.moveToNext()){
            job.SetJobTitle(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.JOB_TITLE)));
            job.SetJobListingUrl(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.JOB_URL)));
            job.SetJobDescription(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.JOB_DESCRIPTION)));
            job.SetJobLocation(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.JOB_LOCATION)));
            job.SetJobType(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.JOB_TYPE)));
            job.SetJobID(cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.JOB_ID)));
        }
        return job;
    }
    public JobListing getNextUnseenJobListing(){
        JobListing job = new JobListing();
        Cursor cursor = database.query(DBHelper.DATABASE_TABLE, DBColumns, DBHelper.JOB_STATUS + " = ?", new String[] {"unseen"}, null, null, null);
        if(cursor.moveToNext()) {
            job = createJobListing(cursor);
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.JOB_STATUS, SEEN);
            database.update(DBHelper.DATABASE_TABLE, contentValues, DBHelper.JOB_ID + "=" + cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.JOB_ID)), null);
        }
        return job;
    }

    public boolean checkURLExists(String url){
        Cursor cursor = database.rawQuery("Select * from " + DBHelper.DATABASE_TABLE + " where " + DBHelper.JOB_URL + " = ?", new String[] {url});
        if(cursor.getCount() > 0) {return true;} else {return false;}
    }

    private JobListing createJobListing(Cursor cursor){
        JobListing job = new JobListing();
        job.SetJobTitle(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.JOB_TITLE)));
        job.SetJobListingUrl(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.JOB_URL)));
        job.SetJobDescription(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.JOB_DESCRIPTION)));
        job.SetJobLocation(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.JOB_LOCATION)));
        job.SetJobType(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.JOB_TYPE)));
        job.SetJobID(cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.JOB_ID)));
        return job;
    }

    public ArrayList<JobListing> getSavedJobs(){
        ArrayList<JobListing> savedJobs = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.DATABASE_TABLE, DBColumns, DBHelper.JOB_STATUS + " = ?", new String[] {"saved"}, null, null, null);
        if(cursor.moveToNext()) {
            //cursor.moveToPrevious();
            do {
                savedJobs.add(createJobListing(cursor));
            } while (cursor.moveToNext());
        }
        return savedJobs;
    }
    public ArrayList<JobListing> getRejectedJobs(){
        ArrayList<JobListing> rejectedJobs = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.DATABASE_TABLE, DBColumns, DBHelper.JOB_STATUS + " = ?", new String[] {"rejected"}, null, null, null);
        if(cursor.moveToNext()) {
            //cursor.moveToPrevious();
            do {
                rejectedJobs.add(createJobListing(cursor));
            } while (cursor.moveToNext());
        }
        return rejectedJobs;
    }
}
