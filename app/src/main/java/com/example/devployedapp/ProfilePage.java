package com.example.devployedapp;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    Dialog skillsDialog; //to create popup to add Skills & Experiences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        // Views
        profileFullName = findViewById(R.id.editTextName);
        profileEmail = findViewById(R.id.editTextEmailAddress);
        profilePhone = findViewById(R.id.editTextPhone);
        educationalExperience = findViewById(R.id.ChipGroup_education);
        programminglanguages = findViewById(R.id.ChipGroup_profile_programmingLanguages);
        experienceLevel = findViewById(R.id.ChipGroup_experience);
        industryPreferences = findViewById(R.id.ChipGroup_industries);
        profileImage = findViewById(R.id.profileImage);

        // Toolbar back arrow button click handler
        ImageButton backButton_profile = (ImageButton) findViewById(R.id.backButton_profile);
        backButton_profile.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                finish();
            }
        });

        // Profile Image long-click handler
        profileImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // Check runtime permission
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        // Permission not granted, request it.
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        // Show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        // Permission is already granted
                        pickImageFromGallery();
                    }
                }
                else {
                    // System OS is less than Marshmallow
                    pickImageFromGallery();
                }
                return true;
            }
        });

        skillsDialog = new Dialog(this);
    }

    // Used for profile image long-click
    private void pickImageFromGallery() {
        // Intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        ActivityResultContracts.StartActivityForResult(intent)
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    // Handle result of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted
                    pickImageFromGallery();
                } else {
                    // Permission was denied
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // Handle result of picked image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            // Set image to image view
            profileImage.setImageURI(data.getData());
        }
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