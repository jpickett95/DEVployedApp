package com.example.devployedapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.util.Base64;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String SHARED_PREFERENCES = "sharedPreferences";
    public static final String PROFILE_IMAGE = "profileImage";
    public static final String PROFILE_NAME = "profileName";
    private String encodedImage;

    DrawerLayout drawerLayout;

    @Override
    public void setContentView(View view){
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setEnabled(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_drawer_open, R.string.menu_drawer_close);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        encodedImage = sharedPreferences.getString(PROFILE_IMAGE,"");
        String fullName = sharedPreferences.getString(PROFILE_NAME, "");
        TextView navProfileName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_profile_name);
        navProfileName.setText(fullName);
        ImageView navProfileImage = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.nav_profile_image);
        if(!encodedImage.equalsIgnoreCase("")){
            byte[] b = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            navProfileImage.setImageBitmap(bitmap);

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()){
            case R.id.nav_job_matches:
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_profile:
                startActivity(new Intent(this, ProfilePage.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_saved_jobs:
                startActivity(new Intent(this, SavedJobsListPage.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_rejected_jobs:
                startActivity(new Intent(this, RejectedJobsListPage.class));
                overridePendingTransition(0,0);
                break;
        }
        return true;
    }

    protected void allocateActivityTitle(String titleString) {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(titleString);
        }
    }
}