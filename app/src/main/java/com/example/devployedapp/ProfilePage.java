package com.example.devployedapp;

import android.Manifest;
import android.app.Dialog;
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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProfilePage extends AppCompatActivity implements LanguageDialog.LanguageDialogListener {

    // Variables
    private ImageButton addLanguageButton;
    private EditText profileFullName;
    private EditText profileEmail;
    private EditText profilePhone;
    private ChipGroup educationalExperience;
    private Chip highSchoolEDUChip;
    private Chip associatesEDUChip;
    private Chip bachelorsEDUChip;
    private Chip mastersEDUChip;
    private Chip phdEDUChip;
    private Chip bootcampEDUChip;
    private ChipGroup programmingLanguages;
    private ChipGroup experienceLevel;
    private Chip noexpEXPChip;
    private Chip earlyEXPChip;
    private Chip midEXPChip;
    private Chip seniorEXPChip;
    private Chip executiveEXPChip;
    private ChipGroup industryPreferences;
    private Chip softwareDevINDChip;
    private Chip gameDevINDChip;
    private Chip itINDChip;
    private Chip uxuiINDChip;
    private Chip frontEndINDChip;
    private Chip backEndINDChip;
    private Chip fullStackINDChip;
    private ImageView profileImage;
    private Button saveProfileButton;

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
    public static final String PROFILE_PROGRAMMINGLANGUAGES_CHIPSTRINGSET = "profileProgrammingLanguages";
    private String fullName;
    private String email;
    private String phone;
    private Set<String> programmingLanguagesChipNames;
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

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        // Views
        profileFullName = findViewById(R.id.editTextName);
        profileEmail = findViewById(R.id.editTextEmailAddress);
        profilePhone = findViewById(R.id.editTextPhone);
        educationalExperience = findViewById(R.id.ChipGroup_education);
        highSchoolEDUChip = findViewById(R.id.chip_profile_highschool);
        associatesEDUChip = findViewById(R.id.chip_profile_associates);
        bachelorsEDUChip = findViewById(R.id.chip_profile_bachelors);
        mastersEDUChip = findViewById(R.id.chip_profile_masters);
        phdEDUChip = findViewById(R.id.chip_profile_phd);
        bootcampEDUChip = findViewById(R.id.chip_profile_bootcamp);
        programmingLanguages = findViewById(R.id.ChipGroup_profile_programmingLanguages);
        experienceLevel = findViewById(R.id.ChipGroup_profile_experience);
        noexpEXPChip = findViewById(R.id.chip_profile_noexp);
        earlyEXPChip = findViewById(R.id.chip_profile_earlycareer);
        midEXPChip = findViewById(R.id.chip_profile_midcareer);
        seniorEXPChip = findViewById(R.id.chip_profile_senior);
        executiveEXPChip = findViewById(R.id.chip_profile_executive);
        industryPreferences = findViewById(R.id.ChipGroup_profile_industrypreferences);
        softwareDevINDChip = findViewById(R.id.chip_profile_softwaredev);
        gameDevINDChip = findViewById(R.id.chip_profile_gamedev);
        itINDChip = findViewById(R.id.chip_profile_it);
        uxuiINDChip = findViewById(R.id.chip_profile_uxui);
        frontEndINDChip = findViewById(R.id.chip_profile_frontend);
        backEndINDChip = findViewById(R.id.chip_profile_backend);
        fullStackINDChip = findViewById(R.id.chip_profile_fullstack);
        profileImage = findViewById(R.id.profileImage);
        saveProfileButton = findViewById(R.id.saveProfileButton);

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

        // Save Profile Button click handler
        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        // Chip Click Handlers
        for (int i = 0; i < educationalExperience.getChildCount(); i++) {
            Chip chip = (Chip)educationalExperience.getChildAt(i);
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    saveData();
                }
            });
        }
        for (int i = 0; i < experienceLevel.getChildCount(); i++) {
            Chip chip = (Chip)experienceLevel.getChildAt(i);
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    saveData();
                }
            });
        }
        for (int i = 0; i < industryPreferences.getChildCount(); i++) {
            Chip chip = (Chip)industryPreferences.getChildAt(i);
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    saveData();
                }
            });
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

        // For Programming Language Chips - Shared Preferences takes String Sets
        programmingLanguagesChipNames = new HashSet<String>();
        for (int i = 0; i < programmingLanguages.getChildCount(); i++) {
            Chip chip = (Chip)programmingLanguages.getChildAt(i);
            programmingLanguagesChipNames.add(chip.getText().toString());
            editor.putBoolean("programminglang" + i, chip.isChecked());
        }
        editor.putStringSet(PROFILE_PROGRAMMINGLANGUAGES_CHIPSTRINGSET, programmingLanguagesChipNames);

        editor.apply();

        Toast.makeText(this, "Profile Saved!", Toast.LENGTH_SHORT).show();
    }
    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        // (variable) = sharedPreferences.getString(CONSTANT, default value "") --- OR ...getBoolean(CONSTANT, default value)

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

        programmingLanguagesChipNames = sharedPreferences.getStringSet(PROFILE_PROGRAMMINGLANGUAGES_CHIPSTRINGSET,programmingLanguagesChipNames);

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

        // Programming Languages
        if (programmingLanguagesChipNames != null) {
            int i =0;
            for (String language : programmingLanguagesChipNames) {
                ChipGroup languages = findViewById(R.id.ChipGroup_profile_programmingLanguages);
                Chip chip = createChip(languages, language);
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
                String key = "programminglang" + i;
                chip.setChecked(sharedPreferences.getBoolean(key, true));
                languages .addView(chip);
                i++;
            }
        }

        // Profile Image
        if(!encodedImage.equalsIgnoreCase("")){
            byte[] b = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            profileImage.setImageBitmap(bitmap);
        }
    }

    // Used for profile image long-click
    private void pickImageFromGallery() {
        // Intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
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
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            // Set image to image view
            //profileImage.setImageURI(data.getData());
            if(data != null) {
                Uri imageUri = data.getData();
                Bitmap image = null;
                try {
                    image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                profileImage.setImageBitmap(image);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();
                encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                saveData();
            }
        }
    }

    // Programming Language Dialog Box
    private void openLanguageDialog() {
        LanguageDialog languageDialog = new LanguageDialog();
        languageDialog.show(getSupportFragmentManager(), "language dialog");
    }

    // Create a new chip
    public Chip createChip(View v, String text){
        Chip newChip = new Chip(v.getContext());
        newChip.setText(text);
        newChip.setChipBackgroundColor(ContextCompat.getColorStateList(v.getContext(),R.color.brand_Pistachio));
        newChip.setId(ViewCompat.generateViewId());
        newChip.setCheckable(true);
        newChip.setChecked(true);
        newChip.setCheckedIconVisible(true);
        newChip.setSaveEnabled(true);

        // When a chip is 'long clicked' it will be removed from the group
        newChip.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ChipGroup chipGroup = (ChipGroup) newChip.getParent();
                chipGroup.removeView(newChip);
                saveData();
                return true;
            }
        });

        // When a chip is 'clicked' and checked status is changed
        newChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                saveData();
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
        saveData();
    }
}