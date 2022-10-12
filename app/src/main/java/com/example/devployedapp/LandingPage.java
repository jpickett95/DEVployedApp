package com.example.devployedapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class LandingPage extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;
    private Object NavigationDrawer;



    protected void onCreate(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = Navigation.findNavController(view);
        mAppBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        NavigationUI.setupWithNavController(
                toolbar, navController, mAppBarConfiguration);

        /*//noinspection deprecation
        @SuppressWarnings("deprecation") AppBarConfiguration mAppBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .setDrawerLayout(appBarConfiguration.getDrawerLayout())
                .build();
        NavHostFragment navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        NavigationView navView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navView, navController);

        NavigationUI.setupWithNavController(
                toolbar, navController, appBarConfiguration);*/

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

    public void ShowFiltersPopup(View view) {
    }
}