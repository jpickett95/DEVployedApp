package com.example.devployedapp;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfilePage extends AppCompatActivity {
    // Variables
    private EditText profileFullName;
    private EditText profileEmail;
    private EditText profilePhone;
    private ChipGroup educationalExperience;
    private ChipGroup programminglanguages;
    private ChipGroup experienceLevel;
    private ChipGroup industryPreferences;
    private ImageView profileImage;

    Dialog skillsDialog; //to create popup to add Skills & Experiences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        profileFullName = findViewById(R.id.editTextName);
        profileEmail = findViewById(R.id.editTextEmailAddress);
        profilePhone = findViewById(R.id.editTextPhone);
        educationalExperience = findViewById(R.id.ChipGroup_education);
        programminglanguages = findViewById(R.id.ChipGroup_profile_programmingLanguages);
        experienceLevel = findViewById(R.id.ChipGroup_experience);
        industryPreferences = findViewById(R.id.ChipGroup_industries);
        profileImage = findViewById(R.id.profileImage);

        ImageButton backButton_profile = (ImageButton) findViewById(R.id.backButton_profile);
        backButton_profile.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                finish();
            }
        });

        skillsDialog = new Dialog(this);
    }
    public void AddSkills(View v){
        FloatingActionButton doneAddingButton;
        skillsDialog.setContentView(R.layout.choose_skills_experience);
        doneAddingButton = (FloatingActionButton) skillsDialog.findViewById(R.id.floatingActionButton_doneAdding);
        doneAddingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                skillsDialog.dismiss();
            }
        });
        skillsDialog.show();
    }

}