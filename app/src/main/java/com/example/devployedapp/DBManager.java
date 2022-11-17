package com.example.devployedapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.webparser.WebParser;
import com.example.webparser.data.JobListing;
import com.example.webparser.events.EventManager;
import com.example.webparser.events.handlers.ListingAddedEventHandler;
import com.example.webparser.events.interfaces.ListingAddedCallback;

import java.io.IOException;
import java.sql.SQLDataException;
import java.util.ArrayList;

public class DBManager implements ListingAddedCallback {
    private static DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;
    ListingAddedEventHandler<DBManager> listingAddedEventHandler;
    EventManager eventManager;
    WebParser webparser = new WebParser();

    static final String UNSEEN = "unseen";
    static final String SEEN = "seen";
    static final String APPLIED = "applied";
    static final String SAVED = "saved";
    static final String REJECTED = "rejected";

    private String[] DBColumns = new String[] {DBHelper.JOB_ID, DBHelper.JOB_URL, DBHelper.JOB_COMPANY_NAME, DBHelper.JOB_DESCRIPTION, DBHelper.JOB_TITLE,
            DBHelper.JOB_LOCATION, DBHelper.JOB_STATUS, DBHelper.JOB_TYPE, DBHelper.JOB_ADDITIONAL_INFORMATION};

    // Constructor
    public DBManager(Context cntx) {
        context = cntx;
        try {this.open();} catch (SQLDataException sqle) {sqle.printStackTrace();}

        //webparser = new WebParser();
        listingAddedEventHandler = new ListingAddedEventHandler(this);
        try {webparser.StartParsing();} catch (IOException io) { io.getStackTrace();} catch (InterruptedException inter){ inter.getStackTrace();}
        webparser.eventManager.RegisterEventHandler(listingAddedEventHandler);
    }

    // Opens database; called in constructor
    public DBManager open() throws SQLDataException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    // Inserts a JobListing object into the database
    public void insert(JobListing job){
                try {
                    Cursor cursor = null;
                    if(job.GetJobListingUrl() != null) {
                        cursor = database.query(DBHelper.DATABASE_TABLE, DBColumns, DBHelper.JOB_URL + " = ?", new String[]{job.GetJobListingUrl()}, null, null, null);
                    }
                    if(cursor.getCount() == 0) {
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
                } catch (Exception e){e.printStackTrace();}

    }
    // Inserts an entry into the database - (custom input)
    public void insertData( String job_title, String job_status, String job_location, String job_company_name, String additional_information, String job_description, String job_type, String job_url){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.JOB_TITLE, job_title);
        contentValues.put(DBHelper.JOB_STATUS, job_status);
        contentValues.put(DBHelper.JOB_LOCATION, job_location);
        contentValues.put(DBHelper.JOB_COMPANY_NAME, job_company_name);
        contentValues.put(DBHelper.JOB_ADDITIONAL_INFORMATION, additional_information);
        contentValues.put(DBHelper.JOB_DESCRIPTION, job_description);
        contentValues.put(DBHelper.JOB_TYPE, job_type);
        contentValues.put(DBHelper.JOB_URL, job_url);
        database.insert(DBHelper.DATABASE_TABLE, null, contentValues);
    }

    // Updates an entry with a new JobListing object's data
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
    // Update an entry with custom input
    public boolean updateData( long job_id, String job_title, String job_status, String job_location, String job_company_name, String additional_information, String job_description, String job_type, String job_url){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.JOB_TITLE, job_title);
        contentValues.put(DBHelper.JOB_STATUS, job_status);
        contentValues.put(DBHelper.JOB_LOCATION, job_location);
        contentValues.put(DBHelper.JOB_COMPANY_NAME, job_company_name);
        contentValues.put(DBHelper.JOB_ADDITIONAL_INFORMATION, additional_information);
        contentValues.put(DBHelper.JOB_DESCRIPTION, job_description);
        contentValues.put(DBHelper.JOB_TYPE, job_type);
        contentValues.put(DBHelper.JOB_URL, job_url);
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
    // Updates the job_status of an entry in the database
    public int updateJobStatus(long job_id, String status){
        if((status == UNSEEN) || (status == SEEN) || (status == REJECTED) || (status == SAVED) || status == APPLIED) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.JOB_STATUS, status);
            int result = database.update(DBHelper.DATABASE_TABLE, contentValues, DBHelper.JOB_ID + "=" + job_id, null);
            return result;
        } else {return -1;}
    }
    // Updates the job_title of an entry in the database
    public int updateJobTitle(long job_id, String job_title) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.JOB_TITLE, job_title);
        int result = database.update(DBHelper.DATABASE_TABLE, contentValues, DBHelper.JOB_ID + "=" + job_id, null);
        return result;
    }
    // Updates the job_type of an entry in the database
    public int updateJobType(long job_id, String job_type) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.JOB_TYPE, job_type);
        int result = database.update(DBHelper.DATABASE_TABLE, contentValues, DBHelper.JOB_ID + "=" + job_id, null);
        return result;
    }
    // Updates the job_location of an entry in the database
    public int updateJobLocation(long job_id, String job_location) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.JOB_LOCATION, job_location);
        int result = database.update(DBHelper.DATABASE_TABLE, contentValues, DBHelper.JOB_ID + "=" + job_id, null);
        return result;
    }
    // Updates the job_company_name of an entry in the database
    public int updateJobCompanyName(long job_id, String company_name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.JOB_COMPANY_NAME, company_name);
        int result = database.update(DBHelper.DATABASE_TABLE, contentValues, DBHelper.JOB_ID + "=" + job_id, null);
        return result;
    }
    // Updates the job_description of an entry in the database
    public int updateJobDescription(long job_id, String job_description) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.JOB_DESCRIPTION, job_description);
        int result = database.update(DBHelper.DATABASE_TABLE, contentValues, DBHelper.JOB_ID + "=" + job_id, null);
        return result;
    }
    // Updates the job_url of an entry in the database
    public int updateJobUrl(long job_id, String job_url) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.JOB_URL, job_url);
        int result = database.update(DBHelper.DATABASE_TABLE, contentValues, DBHelper.JOB_ID + "=" + job_id, null);
        return result;
    }
    // Updates the job_additional_information of an entry in the database
    public int updateJobAdditionalInfo(long job_id, String additional_info) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.JOB_ADDITIONAL_INFORMATION, additional_info);
        int result = database.update(DBHelper.DATABASE_TABLE, contentValues, DBHelper.JOB_ID + "=" + job_id, null);
        return result;
    }

    // Returns a cursor to the first entry of the database
    public Cursor fetch(){
        Cursor cursor = database.query(DBHelper.DATABASE_TABLE, DBColumns, null, null, null,null, null);
        if(cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    // Deletes an entry from the database
    public void delete(long _id){
        database.delete(DBHelper.DATABASE_TABLE, DBHelper.JOB_ID + "=" + _id, null);
    }

    // Returns a JobListing of the first entry in the database
    public JobListing getFirstJobListing(){
        Cursor cursor = fetch();
        JobListing job = createJobListing(cursor);
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
    // Returns a JobListing of the 1st entry that is 'unseen'
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

    // Creates a JobListing object based off a cursor's data
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

    // Returns a list of all entries in database with a 'saved' job_status
    public ArrayList<JobListing> getSavedJobs(){
        ArrayList<JobListing> savedJobs = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Cursor cursor = database.query(DBHelper.DATABASE_TABLE, DBColumns, DBHelper.JOB_STATUS + " = ?", new String[] {"saved"}, null, null, null);
                    if(cursor.moveToNext()) {
                        //cursor.moveToPrevious(); - causes index out of bounds crash?
                        do {
                            savedJobs.add(createJobListing(cursor));
                        } while (cursor.moveToNext());
                    }
                } catch (Exception e) {e.printStackTrace();}
            }
        }).start();
        return savedJobs;
    }
    // Returns a list of all entries in database with a 'rejected' job_status
    public ArrayList<JobListing> getRejectedJobs(){
        ArrayList<JobListing> rejectedJobs = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Cursor cursor = database.query(DBHelper.DATABASE_TABLE, DBColumns, DBHelper.JOB_STATUS + " = ?", new String[] {"rejected"}, null, null, null);
                    if(cursor.moveToNext()) {
                        //cursor.moveToPrevious(); - causes index out of bounds crash?
                        do {
                            rejectedJobs.add(createJobListing(cursor));
                        } while (cursor.moveToNext());
                    }
                } catch (Exception e) {e.printStackTrace();}
            }
        }).start();
        return rejectedJobs;
    }
    // Returns a list of all entries in database with an 'unseen' job_status
    public ArrayList<JobListing> getUnseenJobs(){
        ArrayList<JobListing> unseenJobs = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Cursor cursor = database.query(DBHelper.DATABASE_TABLE, DBColumns, DBHelper.JOB_STATUS + " = ?", new String[] {"unseen"}, null, null, null);
                    if(cursor.moveToNext()) {
                        //cursor.moveToPrevious(); - causes index out of bounds crash?
                        do {
                            unseenJobs.add(createJobListing(cursor));
                        } while (cursor.moveToNext());
                    }
                } catch (Exception e) {e.printStackTrace();}
            }
        }).start();
        return unseenJobs;
    }

    // For ListingAddedCallback
    @Override
    public void ListingWasAdded() {

    }
    @Override
    public void ListingWasAdded(JobListing listing) {
        // Adds JobListing to database once parsed
        if(database == null){
            try {this.open();} catch (Exception e) {e.printStackTrace();}
        }
        if(database.isOpen()) {
            this.insert(listing);
        }
    }
}
