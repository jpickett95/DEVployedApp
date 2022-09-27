package com.example.devployedapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    Dialog filtersDialog; // For filters popup window on main activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Menu button opens popup_filters
        Button menuButton = (Button) findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfilePage.class));
            }
        });

        // toolbar Back Button to exit the app
        ImageButton backButton_main = (ImageButton) findViewById(R.id.backButton_main);
        backButton_main.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                finish();
            }
        });

        filtersDialog = new Dialog(this); // For filters popup window on main activity
    }

    // For filters popup window on main activity
    public void ShowFiltersPopup(View v){
        // popup is dismissed when user clicks the completed button
        FloatingActionButton completedButton;
        filtersDialog.setContentView(R.layout.popup_filters);
        completedButton = (FloatingActionButton) filtersDialog.findViewById(R.id.floatingActionButton_complete);
        completedButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                filtersDialog.dismiss();
            }
        });
        filtersDialog.show();
    }
}