package com.example.devployedapp;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfilePage extends AppCompatActivity {

    Dialog skillsDialog; //to create popup to add Skills & Experiences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);


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
                ////code here to 'refresh' the profile page to display selected items - another method?
                /*ChipGroup skillsChipGroup = (ChipGroup) findViewById(R.id.profile_skills_chipGroup);
                setVisible(true);
                ChipGroup expChipGroup = (ChipGroup) findViewById(R.id.profile_exp_chipGroup);
                setVisible(true);*/
            }
        });
        skillsDialog.show();
    }
    public void refreshProfile(){
        ChipGroup skillsChipGroup;
        ChipGroup expChipGroup;
        skillsChipGroup = findViewById(R.id.profile_skills_chipGroup);
    }

}