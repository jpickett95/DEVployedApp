package com.example.devployedapp;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfilePage extends AppCompatActivity implements LanguageDialog.LanguageDialogListener {

    // Variables
    private ImageButton addLanguageButton;

    Dialog skillsDialog; //to create popup to add Skills & Experiences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        // Add new programming languages
        addLanguageButton = findViewById(R.id.programmingLanguages_addButton);
        addLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLanguageDialog();
            }
        });

        // Toolbar 'Back Button' functionality to go 'back' a page.
        ImageButton backButton_profile = findViewById(R.id.backButton_profile);
        backButton_profile.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                finish();
            }
        });

        // Dialog Boxes
        skillsDialog = new Dialog(this);
    }

    // Programming Language Dialog Box
    private void openLanguageDialog() {
        LanguageDialog languageDialog = new LanguageDialog();
        languageDialog.show(getSupportFragmentManager(), "language dialog");
    }

    public void AddSkills(View v){
        FloatingActionButton doneAddingButton;
        skillsDialog.setContentView(R.layout.choose_skills_experience);
        doneAddingButton = skillsDialog.findViewById(R.id.floatingActionButton_doneAdding);
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



    // Create a new chip
    public Chip createChip(View v, String text){
        Chip newChip = new Chip(v.getContext());
        newChip.setText(text);
        newChip.setId(ViewCompat.generateViewId());

        // When a chip is 'long clicked' it will be removed from the group
        newChip.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ChipGroup chipGroup = (ChipGroup) newChip.getParent();
                chipGroup.removeView(newChip);
                return true;
            }
        });

        return newChip;
    }

    // For LanguageDialog Java class
    @Override
    public void applyText(String text) {
        // Creates a new chip based off text input and dynamically adds it to the ChipGroup
        ChipGroup languages = findViewById(R.id.ChipGroup_profile_programmingLanguages);
        languages .addView(createChip(languages, text));
    }
}