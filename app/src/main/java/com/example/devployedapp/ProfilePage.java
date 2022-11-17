package com.example.devployedapp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.example.devployedapp.databinding.ActivityProfilePageBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class ProfilePage extends DrawerBaseActivity implements MySkillsDialog.MySkillsDialogListener {

    private EditText profileFullName;
    private EditText profileEmail;
    private EditText profilePhone;
    private Chip highSchoolEDUChip;
    private Chip associatesEDUChip;
    private Chip bachelorsEDUChip;
    private Chip mastersEDUChip;
    private Chip phdEDUChip;
    private Chip bootcampEDUChip;
    private ChipGroup mySkills;
    private Chip noexpEXPChip;
    private Chip earlyEXPChip;
    private Chip midEXPChip;
    private Chip seniorEXPChip;
    private Chip executiveEXPChip;
    private Chip softwareDevINDChip;
    private Chip gameDevINDChip;
    private Chip itINDChip;
    private Chip uxuiINDChip;
    private Chip frontEndINDChip;
    private Chip backEndINDChip;
    private Chip fullStackINDChip;
    private ImageView profileImage;

    // Shared Preferences Constants
    public static final String SHARED_PREFERENCES = "sharedPreferences";
    public static final String PROFILE_NAME = "profileName";
    public static final String PROFILE_EMAIL = "profileEmail";
    public static final String PROFILE_PHONE = "profilePhone";
    public static final String PROFILE_IMAGE = "profileImage";
    public static final String PROFILE_EDU_HIGHSCHOOL = "profileEduHS";
    public static final String PROFILE_EDU_ASSOCIATES = "profileEduAS";
    public static final String PROFILE_EDU_BACHELORS = "profileEduBS";
    public static final String PROFILE_EDU_MASTERS = "profileEduMS";
    public static final String PROFILE_EDU_PHD = "profileEduPHD";
    public static final String PROFILE_EDU_BOOTCAMP = "profileEduBootcamp";
    public static final String PROFILE_EXP_NOEXP = "profileExpNoExp";
    public static final String PROFILE_EXP_EARLY = "profileExpEarly";
    public static final String PROFILE_EXP_MID = "profileExpMid";
    public static final String PROFILE_EXP_SENIOR = "profileExpSenior";
    public static final String PROFILE_EXP_EXECUTIVE = "profileExpExecutive";
    public static final String PROFILE_INDUSTRY_SOFTWAREDEV = "profileIndustrySoftwareDev";
    public static final String PROFILE_INDUSTRY_GAMEDEV = "profileIndustryGameDev";
    public static final String PROFILE_INDUSTRY_IT = "profileIndustryIT";
    public static final String PROFILE_INDUSTRY_UXUI = "profileIndustryUXUI";
    public static final String PROFILE_INDUSTRY_FRONTEND = "profileIndustryFrontEnd";
    public static final String PROFILE_INDUSTRY_BACKEND = "profileIndustryBackEnd";
    public static final String PROFILE_INDUSTRY_FULLSTACK = "profileIndustryFullStack";
    public static final String PROFILE_MYSKILLS_CHIPSTRINGSET = "profileMySkills";
    private String fullName;
    private String email;
    private String phone;
    private Set<String> mySkillsChipNames;
    private boolean highSchoolOnOff;
    private boolean associatesOnOff;
    private boolean bachelorsOnOff;
    private boolean mastersOnOff;
    private boolean phdOnOff;
    private boolean bootcampOnOff;
    private boolean noexpOnOff;
    private boolean earlyexpOnOff;
    private boolean midexpOnOff;
    private boolean seniorexpOnOff;
    private boolean executiveOnOff;
    private boolean softwaredevOnOff;
    private boolean gamedevOnOff;
    private boolean itOnOff;
    private boolean uxuiOnOff;
    private boolean fullstackOnOff;
    private boolean frontendOnOff;
    private boolean backendOnOff;

    private String encodedImage;

    ActivityProfilePageBinding activityProfilePageBinding;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfilePageBinding = ActivityProfilePageBinding.inflate(getLayoutInflater());
        setContentView(activityProfilePageBinding.getRoot());
        allocateActivityTitle("My Profile");

        // Views
        profileFullName = findViewById(R.id.editTextName);
        profileEmail = findViewById(R.id.editTextEmailAddress);
        profilePhone = findViewById(R.id.editTextPhone);
        ChipGroup educationalExperience = findViewById(R.id.ChipGroup_education);
        highSchoolEDUChip = findViewById(R.id.chip_profile_highschool);
        associatesEDUChip = findViewById(R.id.chip_profile_associates);
        bachelorsEDUChip = findViewById(R.id.chip_profile_bachelors);
        mastersEDUChip = findViewById(R.id.chip_profile_masters);
        phdEDUChip = findViewById(R.id.chip_profile_phd);
        bootcampEDUChip = findViewById(R.id.chip_profile_bootcamp);
        ChipGroup experienceLevel = findViewById(R.id.ChipGroup_profile_experience);
        noexpEXPChip = findViewById(R.id.chip_profile_noexp);
        earlyEXPChip = findViewById(R.id.chip_profile_earlycareer);
        midEXPChip = findViewById(R.id.chip_profile_midcareer);
        seniorEXPChip = findViewById(R.id.chip_profile_senior);
        executiveEXPChip = findViewById(R.id.chip_profile_executive);
        ChipGroup industryPreferences = findViewById(R.id.ChipGroup_profile_industrypreferences);
        softwareDevINDChip = findViewById(R.id.chip_profile_softwaredev);
        gameDevINDChip = findViewById(R.id.chip_profile_gamedev);
        itINDChip = findViewById(R.id.chip_profile_it);
        uxuiINDChip = findViewById(R.id.chip_profile_uxui);
        frontEndINDChip = findViewById(R.id.chip_profile_frontend);
        backEndINDChip = findViewById(R.id.chip_profile_backend);
        fullStackINDChip = findViewById(R.id.chip_profile_fullstack);
        profileImage = findViewById(R.id.profileImage);
        mySkills = findViewById(R.id.ChipGroup_profile_mySkills);

        // Add new skills
        ImageButton addSkillButton = findViewById(R.id.mySkills_addButton);
        addSkillButton.setOnClickListener((View view) -> openMySkillsDialog());

        // Profile Image long-click handler
        profileImage.setOnLongClickListener((View view) -> {

                boolean pick=true;
                if(pick){
                    if(!checkCameraPermission()) {
                        requestCameraPermission();
                    } else PickImage();
                } else {
                    if(!checkStoragePermission()) {
                        requestStoragePermission();
                    } else PickImage();
                }
                return pick;
            });

        // Chip Click Handlers
        for (int i = 0; i < educationalExperience.getChildCount(); i++) {
            Chip chip = (Chip) educationalExperience.getChildAt(i);
            chip.setOnCheckedChangeListener((CompoundButton compoundButton, boolean b) -> saveData());
        }
        for (int i = 0; i < experienceLevel.getChildCount(); i++) {
            Chip chip = (Chip) experienceLevel.getChildAt(i);
            chip.setOnCheckedChangeListener((CompoundButton compoundButton, boolean b) -> saveData());
        }
        for (int i = 0; i < industryPreferences.getChildCount(); i++) {
            Chip chip = (Chip) industryPreferences.getChildAt(i);
            chip.setOnCheckedChangeListener((CompoundButton compoundButton, boolean b) -> saveData());
        }

        // Profile Name editText Listener
        profileFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                saveData();
            }
        });

        // Profile Email editText Listener
        profileEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                saveData();
            }
        });

        // Profile Phone editText Listener
        profilePhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                saveData();
            }
        });

        // Shared Preferences
        loadData();
        updateViews();
    }

    // For Shared Preferences
    private void saveData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString(PROFILE_NAME, profileFullName.getText().toString());
                    editor.putString(PROFILE_EMAIL, profileEmail.getText().toString());
                    editor.putString(PROFILE_PHONE, profilePhone.getText().toString());
                    editor.putBoolean(PROFILE_EDU_HIGHSCHOOL, highSchoolEDUChip.isChecked());
                    editor.putBoolean(PROFILE_EDU_ASSOCIATES, associatesEDUChip.isChecked());
                    editor.putBoolean(PROFILE_EDU_BACHELORS, bachelorsEDUChip.isChecked());
                    editor.putBoolean(PROFILE_EDU_MASTERS, mastersEDUChip.isChecked());
                    editor.putBoolean(PROFILE_EDU_PHD, phdEDUChip.isChecked());
                    editor.putBoolean(PROFILE_EDU_BOOTCAMP, bootcampEDUChip.isChecked());
                    editor.putBoolean(PROFILE_EXP_NOEXP, noexpEXPChip.isChecked());
                    editor.putBoolean(PROFILE_EXP_EARLY, earlyEXPChip.isChecked());
                    editor.putBoolean(PROFILE_EXP_MID, midEXPChip.isChecked());
                    editor.putBoolean(PROFILE_EXP_SENIOR, seniorEXPChip.isChecked());
                    editor.putBoolean(PROFILE_EXP_EXECUTIVE, executiveEXPChip.isChecked());
                    editor.putBoolean(PROFILE_INDUSTRY_BACKEND, backEndINDChip.isChecked());
                    editor.putBoolean(PROFILE_INDUSTRY_SOFTWAREDEV, softwareDevINDChip.isChecked());
                    editor.putBoolean(PROFILE_INDUSTRY_GAMEDEV, gameDevINDChip.isChecked());
                    editor.putBoolean(PROFILE_INDUSTRY_FRONTEND, frontEndINDChip.isChecked());
                    editor.putBoolean(PROFILE_INDUSTRY_FULLSTACK, fullStackINDChip.isChecked());
                    editor.putBoolean(PROFILE_INDUSTRY_IT, itINDChip.isChecked());
                    editor.putBoolean(PROFILE_INDUSTRY_UXUI, uxuiINDChip.isChecked());

                    editor.putString(PROFILE_IMAGE, encodedImage);

                    // For My Skills Chips - Shared Preferences takes String Sets
                    mySkills = findViewById(R.id.ChipGroup_profile_mySkills);
                    if(mySkills.getChildCount() > 0) {
                        mySkillsChipNames = new HashSet<>();
                        for (int i = 0; i < mySkills.getChildCount(); i++) {
                            Chip chip = (Chip) mySkills.getChildAt(i);
                            String name = chip.getText().toString();
                            boolean isChecked = chip.isChecked();
                            editor.putBoolean(name, isChecked);
                            mySkillsChipNames.add(name);
                        }
                        editor.putStringSet(PROFILE_MYSKILLS_CHIPSTRINGSET, mySkillsChipNames);
                    }

                    editor.apply();
                    runOnUiThread(()-> {
                        Toast.makeText(getApplicationContext(), "Profile Saved!", Toast.LENGTH_SHORT).show();
                    });
                }catch (Exception e) {e.printStackTrace();}
            }
        }).start();

    }
    public void loadData(){

                    // (variable) = sharedPreferences.getString(CONSTANT, default value "") --- OR ...getBoolean(CONSTANT, default value)
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);

                    // Text
                    fullName = sharedPreferences.getString(PROFILE_NAME, "");
                    email = sharedPreferences.getString(PROFILE_EMAIL, "");
                    phone = sharedPreferences.getString(PROFILE_PHONE, "");

                    // Booleans
                    highSchoolOnOff = sharedPreferences.getBoolean(PROFILE_EDU_HIGHSCHOOL, false);   // Education
                    associatesOnOff = sharedPreferences.getBoolean(PROFILE_EDU_ASSOCIATES, false);
                    bachelorsOnOff = sharedPreferences.getBoolean(PROFILE_EDU_BACHELORS, false);
                    mastersOnOff = sharedPreferences.getBoolean(PROFILE_EDU_MASTERS, false);
                    phdOnOff = sharedPreferences.getBoolean(PROFILE_EDU_PHD, false);
                    bootcampOnOff = sharedPreferences.getBoolean(PROFILE_EDU_BOOTCAMP, false);
                    noexpOnOff = sharedPreferences.getBoolean(PROFILE_EXP_NOEXP, false);  // Experience
                    earlyexpOnOff = sharedPreferences.getBoolean(PROFILE_EXP_EARLY, false);
                    midexpOnOff = sharedPreferences.getBoolean(PROFILE_EXP_MID, false);
                    seniorexpOnOff = sharedPreferences.getBoolean(PROFILE_EXP_SENIOR, false);
                    executiveOnOff = sharedPreferences.getBoolean(PROFILE_EXP_EXECUTIVE, false);
                    softwaredevOnOff = sharedPreferences.getBoolean(PROFILE_INDUSTRY_SOFTWAREDEV, false);   // Industry
                    gamedevOnOff = sharedPreferences.getBoolean(PROFILE_INDUSTRY_GAMEDEV, false);
                    itOnOff = sharedPreferences.getBoolean(PROFILE_INDUSTRY_IT, false);
                    uxuiOnOff = sharedPreferences.getBoolean(PROFILE_INDUSTRY_UXUI, false);
                    fullstackOnOff = sharedPreferences.getBoolean(PROFILE_INDUSTRY_FULLSTACK, false);
                    frontendOnOff = sharedPreferences.getBoolean(PROFILE_INDUSTRY_FRONTEND, false);
                    backendOnOff = sharedPreferences.getBoolean(PROFILE_INDUSTRY_BACKEND, false);

                    // String Sets
                    mySkillsChipNames = sharedPreferences.getStringSet(PROFILE_MYSKILLS_CHIPSTRINGSET, mySkillsChipNames);

                    encodedImage = sharedPreferences.getString(PROFILE_IMAGE,"");
    }
    public void updateViews(){
        // Text
        profileFullName.setText(fullName);
        profileEmail.setText(email);
        profilePhone.setText(phone);

        // Education
        highSchoolEDUChip.setChecked(highSchoolOnOff);
        associatesEDUChip.setChecked(associatesOnOff);
        bachelorsEDUChip.setChecked(bachelorsOnOff);
        mastersEDUChip.setChecked(mastersOnOff);
        phdEDUChip.setChecked(phdOnOff);
        bootcampEDUChip.setChecked(bootcampOnOff);

        // Experience
        noexpEXPChip.setChecked(noexpOnOff);
        earlyEXPChip.setChecked(earlyexpOnOff);
        midEXPChip.setChecked(midexpOnOff);
        seniorEXPChip.setChecked(seniorexpOnOff);
        executiveEXPChip.setChecked(executiveOnOff);

        // Industries
        softwareDevINDChip.setChecked(softwaredevOnOff);
        gameDevINDChip.setChecked(gamedevOnOff);
        itINDChip.setChecked(itOnOff);
        uxuiINDChip.setChecked(uxuiOnOff);
        frontEndINDChip.setChecked(frontendOnOff);
        backEndINDChip.setChecked(backendOnOff);
        fullStackINDChip.setChecked(fullstackOnOff);

        // My Skills
        if (mySkillsChipNames != null) {
            for (String name_bool : mySkillsChipNames) {
                ChipGroup mySkills = findViewById(R.id.ChipGroup_profile_mySkills);
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
                Chip chip = createSkillChip(mySkills, name_bool);
                chip.setChecked(sharedPreferences.getBoolean(chip.getText().toString(), true));
            }
        }

        // Profile Image
        if(!encodedImage.equalsIgnoreCase("")){
            byte[] b = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            profileImage.setImageBitmap(bitmap);
        }
    }

    // Handle result of picked image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                if (result != null) {
                    Uri resultUri = result.getUri();

                    Picasso.with(this).load(resultUri).into(profileImage);
                    Bitmap bitmap;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] b = baos.toByteArray();
                        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                        saveData();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                error.printStackTrace();
            }
        }
    }

    // My Skills Dialog Box
    private void openMySkillsDialog() {
        MySkillsDialog mySkillsDialog = new MySkillsDialog();
        mySkillsDialog.show(getSupportFragmentManager(), "Skills dialog");
    }

    public Chip createSkillChip(View v, String text){
        Chip newChip = new Chip(v.getContext());
        newChip.setText(text);
        newChip.setChipBackgroundColor(ContextCompat.getColorStateList(v.getContext(),R.color.brand_Pistachio));
        newChip.setId(ViewCompat.generateViewId());
        newChip.setCheckable(true);
        newChip.setChecked(true);
        newChip.setCheckedIconVisible(true);
        newChip.setSaveEnabled(true);

        // When a chip is 'long clicked' it will be removed from the group
        newChip.setOnLongClickListener((View view) -> {
            ChipGroup chipGroup = (ChipGroup) newChip.getParent();
            chipGroup.removeView(newChip);
            saveData();
            return true;
        });

        // When a chip is 'clicked' and checked status is changed
        newChip.setOnCheckedChangeListener((CompoundButton compoundButton, boolean b) -> saveData());

        mySkills.addView(newChip);
        return newChip;
    }

    // For MySkillsDialog Java class
    @Override
    public void applySkillText(String text) {
        // Creates a new chip based off text input and dynamically adds it to the ChipGroup
        ChipGroup skills = findViewById(R.id.ChipGroup_profile_mySkills);
        createSkillChip(skills, text);
        saveData();
    }

    // For Profile Image
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission(){
        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }
    private boolean checkCameraPermission() {
        boolean res1=ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED;
        boolean res2=ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED;
        return res1 && res2;
    }
    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission() {
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
    }
    private void PickImage() {
        CropImage.activity().start(this);
    }
}