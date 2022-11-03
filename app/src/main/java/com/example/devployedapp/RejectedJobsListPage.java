package com.example.devployedapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devployedapp.databinding.ListPagesRejectedBinding;
import com.example.webparser.data.JobListing;

import java.util.ArrayList;

/* reference webpages:
Android Docs Create dynamic lists with RecyclerView: https://developer.android.com/develop/ui/views/layout/recyclerview
Android Docs Example of adding RecyclerView: https://github.com/android/views-widgets-samples/commit/aea13d5dbb4b5f1844bcb7f1b330b93f90750052#diff-c26aac7ad610d30f5ab5e74a8f575f27604ae4a3ce747c87d23dbc0a94df22f6
*/
/*Reference Videos:
* RecyclerView - Everything you need to Know, Practical Coding: https://www.youtube.com/watch?v=Mc0XT58A1Z4
* */

public class RejectedJobsListPage extends DrawerBaseActivity implements JobCardBlowUpInterface {

    ListPagesRejectedBinding listPagesRejectedBinding;
    ArrayList<JobListing> jobPostings = new ArrayList<>();
    Dialog jobCardBlowUpDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listPagesRejectedBinding = ListPagesRejectedBinding.inflate(getLayoutInflater());
        setContentView(listPagesRejectedBinding.getRoot());
        allocateActivityTitle("Rejected Jobs");
        DBManager dbManager = new DBManager(this);
        jobPostings = dbManager.getRejectedJobs();
        jobCardBlowUpDialog = new Dialog(this);

        RecyclerView recyclerView = findViewById(R.id.Rejected_Jobs_RecyclerView);
        JobPost_RecyclerViewAdapter adapter = new JobPost_RecyclerViewAdapter(this, jobPostings,
                this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void OnCardClick(int position) {
        TextView companyName, jobTitle, skillsMatched, fullDescription;

        jobCardBlowUpDialog.setContentView(R.layout.jobcard_blowup_item);
        Button closeButton = jobCardBlowUpDialog.findViewById(R.id.cardBlowUp_close_window);
        ImageView imageView = jobCardBlowUpDialog.findViewById(R.id.cardBlowUp_item_companyLogo);
        imageView.setImageResource(R.drawable.custom_rejected_cross_icon);

        companyName = jobCardBlowUpDialog.findViewById(R.id.cardBlowUp_item_companyName);
        jobTitle = jobCardBlowUpDialog.findViewById(R.id.cardBlowUp_item_jobTitle);
        skillsMatched = jobCardBlowUpDialog.findViewById(R.id.cardBlowUp_item_skillsMatched);
        fullDescription = jobCardBlowUpDialog.findViewById(R.id.cardBlowUp_item_fullDescription);
        String prettyJobDesc =
                JobDescriptionBeautifier(jobPostings.get(position).GetJobDescription());

        companyName.setText(jobPostings.get(position).GetJobLocation());
        jobTitle.setText(jobPostings.get(position).GetJobTitle());
        jobTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse(jobPostings.get(position).GetJobListingUrl());
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        });
        skillsMatched.setText(jobPostings.get(position).GetJobType());
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
