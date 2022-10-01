package com.example.devployedapp;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class JobPostInformation extends RecyclerView.Adapter {
    //ideally this will be able to access the Parser to get the accurate and appropriate information
    //for now, will hard-code a set of data to run tests with the app

    String CompanyName;
    String JobTitle;
    String SkillsMatch;
    int CompanyLogo;

    //may need to have the string arrays made here for dynamic ability since it will be fed from parser?
    public JobPostInformation(String companyName, String jobTitle, String skillsMatch, int companyLogo) {
        CompanyName = companyName;
        JobTitle = jobTitle;
        SkillsMatch = skillsMatch;
        CompanyLogo = companyLogo;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public String getSkillsMatch() {
        return SkillsMatch;
    }

    public int getCompanyLogo() {
        return CompanyLogo;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
