package com.example.devployedapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;

import com.example.webparser.WebParser;
import com.example.webparser.data.JobListing;
import com.example.webparser.events.handlers.ListingAddedEventHandler;
import com.example.webparser.events.handlers.SearchCompletedEventHandler;
import com.example.webparser.events.interfaces.ListingAddedCallback;
import com.example.webparser.events.interfaces.SearchCompletedCallback;

import java.io.IOException;

public class LandingPage extends AppCompatActivity implements ListingAddedCallback {

    WebParser webparser;
    ListingAddedEventHandler<LandingPage> listingAddedEventHandler;
    //SearchCompletedEventHandler<LandingPage> searchCompletedEventHandler;
    Button jobMatchesButton;
    Button rejectedPageButton;
    Button savedPageButton;
    Button profilePageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        jobMatchesButton = findViewById(R.id.review_job_matches_button);
        rejectedPageButton = findViewById(R.id.rejected_matches_button);
        savedPageButton = findViewById(R.id.saved_matches_button);
        profilePageButton = findViewById(R.id.to_profile_page_button);
        jobMatchesButton.setEnabled(false);
        rejectedPageButton.setEnabled(false);
        savedPageButton.setEnabled(false);
        profilePageButton.setEnabled(false);

        webparser = new WebParser();
        listingAddedEventHandler = new ListingAddedEventHandler(this);
        //searchCompletedEventHandler = new SearchCompletedEventHandler(this);
        try {webparser.StartParsing();} catch (IOException io) {} catch (InterruptedException inter){}
        webparser.eventManager.RegisterEventHandler(listingAddedEventHandler);
        //webparser.eventManager.RegisterEventHandler(searchCompletedEventHandler);

        Button reviewJobMatchesButton = findViewById(R.id.review_job_matches_button);
        reviewJobMatchesButton.setOnClickListener(view -> startActivity(new Intent(LandingPage.this, MainActivity.class)));
        Button toSavedJobsList = findViewById(R.id.saved_matches_button);
        toSavedJobsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LandingPage.this, SavedJobsListPage.class));
            }
        });
        Button toRejectedJobsList = findViewById(R.id.rejected_matches_button);
        toRejectedJobsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LandingPage.this, RejectedJobsListPage.class));
            }
        });
        Button toProfilePage = findViewById(R.id.to_profile_page_button);
        toProfilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LandingPage.this, ProfilePage.class));
            }
        });
        //toolbar exit button to exit the app
        ImageButton backButton_main = findViewById(R.id.exitButton_main2);
        backButton_main.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void ListingWasAdded() {
        // Reaction Method - where you can react to the fact that a listing was added
        this.runOnUiThread(() -> {
            jobMatchesButton.setEnabled(true);
            rejectedPageButton.setEnabled(true);
            savedPageButton.setEnabled(true);
            profilePageButton.setEnabled(true);
        });

        listingAddedEventHandler.SetDisabled(); // So we don't call it again
        //Toast.makeText(LandingPage.this, "1st Listing Found!", Toast.LENGTH_SHORT);
        Log.d("ListingEventHandler", "Listing Added");
    }

    @Override
    public void ListingWasAdded(JobListing listing) {
        // Where I can access the job listing that was just added
    }

}