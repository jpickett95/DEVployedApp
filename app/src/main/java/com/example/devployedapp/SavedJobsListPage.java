package com.example.devployedapp;

import android.app.Dialog;
import android.content.Intent;
import android.media.Image;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.BinderThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devployedapp.databinding.ListPagesSavedBinding;
import com.example.webparser.data.JobListing;

import java.util.ArrayList;

public class SavedJobsListPage extends DrawerBaseActivity implements JobCardBlowUpInterface{

    ListPagesSavedBinding listPagesSavedBinding;
    ArrayList<JobListing> jobPostings = new ArrayList<>();
    Dialog jobCardBlowUpDialog;
    DBManager dbManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listPagesSavedBinding = ListPagesSavedBinding.inflate(getLayoutInflater());
        setContentView(listPagesSavedBinding.getRoot());
        allocateActivityTitle("Saved Jobs");
        dbManager = new DBManager(this);
        jobPostings = dbManager.getSavedJobs();
        jobCardBlowUpDialog = new Dialog(this);

        RecyclerView recyclerView = findViewById(R.id.Saved_Jobs_RecyclerView);
        JobPost_RecyclerViewAdapter adapter = new JobPost_RecyclerViewAdapter(this, jobPostings,
                this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void OnCardClick(int position) {
        TextView companyName, jobTitle, skillsMatched, fullDescription, jobType;

        jobCardBlowUpDialog.setContentView(R.layout.jobcard_blowup_item);
        Button closeButton = jobCardBlowUpDialog.findViewById(R.id.cardBlowUp_close_window);
        ImageView imageView = jobCardBlowUpDialog.findViewById(R.id.cardBlowUp_item_companyLogo);
        imageView.setImageResource(R.drawable.ic_baseline_favorite_24);

        companyName = jobCardBlowUpDialog.findViewById(R.id.cardBlowUp_item_companyName);
        jobTitle = jobCardBlowUpDialog.findViewById(R.id.cardBlowUp_item_jobTitle);
        jobTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse(jobPostings.get(position).GetJobListingUrl());
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        });
        skillsMatched = jobCardBlowUpDialog.findViewById(R.id.cardBlowUp_item_skillsMatched);
        jobType = jobCardBlowUpDialog.findViewById(R.id.cardBlowUp_item_JobType);
        fullDescription = jobCardBlowUpDialog.findViewById(R.id.cardBlowUp_item_fullDescription);
        String prettyJobDesc =
                JobDescriptionBeautifier(jobPostings.get(position).GetJobDescription());

        companyName.setText(jobPostings.get(position).GetCompanyName());
        jobTitle.setText(jobPostings.get(position).GetJobTitle());
        jobTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse(jobPostings.get(position).GetJobListingUrl());
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        });
        skillsMatched.setText(jobPostings.get(position).GetJobLocation());
        jobType.setText(jobPostings.get(position).GetJobType());
        fullDescription.setText(prettyJobDesc);


        closeButton.setOnClickListener((View v) -> jobCardBlowUpDialog.dismiss());
        jobCardBlowUpDialog.show();
        jobCardBlowUpDialog.getWindow().setLayout((15 * getResources().getDisplayMetrics().widthPixels)/16, (15 * getResources().getDisplayMetrics().heightPixels)/16);
    }
    public String JobDescriptionBeautifier(String jobDescription){
        StringBuilder stringBuilder = new StringBuilder();

        String[] splitDescription = jobDescription.split("\\.\\s|\\R|\\$", 0);
        for (int i = 0; i < splitDescription.length; i++) {
            stringBuilder.append(splitDescription[i]).append(". \n\n\t");

        }
        return stringBuilder.toString();
    }

}
