package com.example.devployedapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.webparser.WebParser;
import com.example.webparser.data.JobListing;
import com.example.webparser.events.handlers.ListingAddedEventHandler;
import com.example.webparser.events.interfaces.ListingAddedCallback;

import java.io.IOException;

public class LandingPage extends AppCompatActivity implements ListingAddedCallback, View.OnClickListener {

    WebParser webparser;
    ListingAddedEventHandler<LandingPage> listingAddedEventHandler;
    //SearchCompletedEventHandler<LandingPage> searchCompletedEventHandler;
    Button jobMatchesButton;
    Button rejectedPageButton;
    Button savedPageButton;
    Button profilePageButton;
    ProgressBar progressBar;
    TextView loadingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        //region Button creation and assignment by id
        jobMatchesButton = findViewById(R.id.review_job_matches_button);
        rejectedPageButton = findViewById(R.id.rejected_matches_button);
        savedPageButton = findViewById(R.id.saved_matches_button);
        profilePageButton = findViewById(R.id.to_profile_page_button);
        progressBar = (ProgressBar) findViewById(R.id.progress_Bar);
        loadingText = findViewById(R.id.textView);
        //endregion

        //setButtonsEnabled(false);
        setButtonsVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        loadingText.setVisibility(View.VISIBLE);

        webparser = new WebParser();
        listingAddedEventHandler = new ListingAddedEventHandler<>(this);
        //searchCompletedEventHandler = new SearchCompletedEventHandler(this);
        try {webparser.StartParsing();} catch (IOException io) { io.getStackTrace();} catch (InterruptedException inter){ inter.getStackTrace();}
        webparser.eventManager.RegisterEventHandler(listingAddedEventHandler);
        //webparser.eventManager.RegisterEventHandler(searchCompletedEventHandler);

    //region Assign setOnClickListeners for the buttons, which calls OnClick() method
        jobMatchesButton.setOnClickListener(this);
        rejectedPageButton.setOnClickListener(this);
        savedPageButton.setOnClickListener(this);
        profilePageButton.setOnClickListener(this);
    //endregion

    }

    //region Button Functions
    /*private void setButtonsEnabled(boolean bool) {
            jobMatchesButton.setEnabled(bool);
            savedPageButton.setEnabled(bool);
            rejectedPageButton.setEnabled(bool);
            profilePageButton.setEnabled(bool);
            exitApplicationButton.setEnabled(bool);
    }*/
    private void setButtonsVisibility(int buttonsVisibility) {
        jobMatchesButton.setVisibility(buttonsVisibility);
        savedPageButton.setVisibility(buttonsVisibility);
        rejectedPageButton.setVisibility(buttonsVisibility);
        profilePageButton.setVisibility(buttonsVisibility);
    }
    public void onClick(View v){
        Intent intent;
        if (v == rejectedPageButton) {
            intent = new Intent(LandingPage.this, RejectedJobsListPage.class);
            startActivity(intent);
        }

        else if (v == jobMatchesButton) {
            intent = new Intent(LandingPage.this, MainActivity.class);
            startActivity(intent);
        }

        else if (v == savedPageButton) {
            intent = new Intent(LandingPage.this, SavedJobsListPage.class);
            startActivity(intent);
        }

        else if (v == profilePageButton) {
            intent = new Intent(LandingPage.this, ProfilePage.class);
            startActivity(intent);
        }

    }
    //endregion

    @Override
    public void ListingWasAdded() {
        // Reaction Method - where you can react to the fact that a listing was added
        this.runOnUiThread(() -> {
            //setButtonsEnabled(true);
            setButtonsVisibility(View.VISIBLE);
            progressBar.setVisibility((View.GONE));
            loadingText.setVisibility(View.GONE);
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