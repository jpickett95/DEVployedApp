package com.example.devployedapp;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devployedapp.databinding.AppHelpLayoutBinding;

import java.util.zip.Inflater;

public class AppHelp extends DrawerBaseActivity {

    AppHelpLayoutBinding appHelpLayoutBinding;


    String[] questionArray = {"What is DEVployed?","How do I create a profile?",
            "How do I view my job matches?", "How do I view the jobs I have saved/rejected?"};
    String[] answerArray = {"DEVployed is an app that matches users to relevant &amp; in-demand " +
                "positions in tech with the swipe of a finger! Our powerful algorithm matches your " +
                "skills and experiences to job descriptions to eliminate under-qualified positions!",
            "In the menu, select Profile. You may upload or take a photo for your profile. Enter " +
                    "your information. Under My Skills, click the plus (+) button to add specific" +
                    "coding languages (C#, Ruby, etc.) and specialized software experience (JIRA," +
                    " SQLDatabase, etc.). Under Educational Experience, Experience Level, and " +
                    "Industry Preferences, select all applicable options. \n\n Congrats! Your " +
                    "profile is complete!",
            "In the menu, select Job Matches. The jobs will be displayed as cards with the job " +
                    "title, location, and part of the job description. If the skills and experiences" +
                    "you entered in your profile match the description of the job, they will" +
                    "show up as green keywords above the job description. \n\n To view the job " +
                    "description completely, tap the card. It will blow up the card and allow you" +
                    " to scroll through the entire job description. To be taken directly to the " +
                    "URL of the job posting, click the job title. \n\n Here's the fun part! If " +
                    "you like the job post, swipe the card to the RIGHT to save it. If you don't " +
                    "like the job post, swipe the card to the LEFT to reject it. You can view all" +
                    " jobs you have saved or rejected at any time through the Rejected Matches " +
                    "and Saved Matches options in the menu.",
            "In the menu, select Rejected Matches or Saved Matches to be taken to the " +
                    "corresponding list. There you can scroll through all previous postings you " +
                    "have viewed. By tapping on the listing, you will be able to review the " +
                    "entire job post again."};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appHelpLayoutBinding = AppHelpLayoutBinding.inflate(getLayoutInflater());
        setContentView(appHelpLayoutBinding.getRoot());
        allocateActivityTitle("Help");

        TextView questionItem1, questionItem2, questionItem3, questionItem4, answerItem1,
                answerItem2, answerItem3, answerItem4;

        questionItem1 = findViewById(R.id.question_item1);
        questionItem1.setText(questionArray[0]);
        questionItem2 = findViewById(R.id.question_item2);
        questionItem2.setText(questionArray[1]);
        questionItem3 = findViewById(R.id.question_item3);
        questionItem3.setText(questionArray[2]);
        questionItem4 = findViewById(R.id.question_item4);
        questionItem4.setText(questionArray[3]);

        answerItem1 = findViewById(R.id.sub_answer_item1);
        answerItem1.setText(answerArray[0]);
        answerItem2 = findViewById(R.id.sub_answer_item2);
        answerItem2.setText(answerArray[1]);
        answerItem3 = findViewById(R.id.sub_answer_item3);
        answerItem3.setText(answerArray[2]);
        answerItem4 = findViewById(R.id.sub_answer_item4);
        answerItem4.setText(answerArray[3]);

    }
}
