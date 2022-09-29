package com.example.devployedapp;

import android.media.Image;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class JobPostInformation extends RecyclerView.Adapter {
    //ideally this will be able to access the Parser to get the accurate and appropriate information
    //for now, will hard-code a set of data to run tests with the app

    protected String CompanyName;
    protected String JobTitle;
    protected String SkillsMatch;
    protected Image CompanyLogo;

    public JobPostInformation createJobPostFrag() {
        createJobPostFrag().CompanyName = "Google";
        createJobPostFrag().JobTitle = "Software Engineer";
        createJobPostFrag().SkillsMatch = "C++";
        Image image = createJobPostFrag().CompanyLogo;
        return createJobPostFrag();
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
