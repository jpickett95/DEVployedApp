package com.example.devployedapp;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfilePage extends AppCompatActivity {
    //public TextView enterSkillTextview = (TextView) findViewById(R.id.skillTextView);
    //LinearLayout linearLayout = findViewById(R.id.profilePage);

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


        Button addSkillButton = (Button) findViewById(R.id.addSkillButton2);
        addSkillButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                finish();
          }
        });
        skillsDialog = new Dialog(this);
    }
    public void AddSkills(View v){
        FloatingActionButton doneAddingButton;
        skillsDialog.setContentView(R.layout.skills_experiences);
        doneAddingButton = (FloatingActionButton) skillsDialog.findViewById(R.id.floatingActionButton_doneAdding);
        doneAddingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                skillsDialog.dismiss();
            }
        });
        skillsDialog.show();
    }

 /*   public void addNew(){
        View view = getLayoutInflater().inflate(R.layout.skills_experiences,null);
        //TextView newestSkill = new TextView(enterSkillTextview.getContext());
        linearLayout.addView(view);

    }*/
}