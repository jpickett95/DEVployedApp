package com.example.devployedapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LandingPage extends AppCompatActivity {





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);



        Button reviewJobMatchesButton = findViewById(R.id.review_job_matches_button);
        reviewJobMatchesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(LandingPage.this, MainActivity.class));
            }});

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
    }

}