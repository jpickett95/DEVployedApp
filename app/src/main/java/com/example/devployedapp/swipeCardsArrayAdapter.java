package com.example.devployedapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.webparser.data.JobListing;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;
import java.util.Set;

public class swipeCardsArrayAdapter  extends ArrayAdapter<JobListing> {
    //Context context;
    public static final String SHARED_PREFERENCES = "sharedPreferences";
    public static final String PROFILE_MYSKILLS_CHIPSTRINGSET = "profileMySkills";
    private Set<String> skills;

    public swipeCardsArrayAdapter(Context context, int resourceId, List<JobListing> jobPosts) {
        super(context, resourceId, jobPosts);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        JobListing jobPost = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.swipecards_item, parent, false);
        }

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        skills = sharedPreferences.getStringSet(PROFILE_MYSKILLS_CHIPSTRINGSET, skills);

        // Find View IDs
        TextView companyNameView = convertView.findViewById(R.id.swipeCards_item_companyName);
        TextView jobTitleView = convertView.findViewById(R.id.swipeCards_item_jobTitle);
        TextView jobDescriptionView = convertView.findViewById(R.id.swipeCards_item_jobDescription);
        ImageView companyLogoView = convertView.findViewById(R.id.swipeCards_item_companyLogo);
        TextView jobLocationView = convertView.findViewById(R.id.swipeCards_item_jobLocation);
        TextView jobTypeView = convertView.findViewById(R.id.swipeCards_item_jobType);
        ChipGroup tagsGroup = convertView.findViewById(R.id.swipeCards_item_tags);

        String location;
        String jobTitle;
        String jobDescription;
        String jobType;
        String companyName = "Company Name";

        // Check is jobPost is null and display 'content unavailable' message if so
        if(jobPost != null) {
            location = jobPost.GetJobLocation();
            jobTitle = jobPost.GetJobTitle();
            jobDescription = jobPost.GetJobDescription();
            jobType = jobPost.GetJobType();
            //companyName = jobPost.GetCompanyName();

            // Create 'tags' based off profile skills
            if (skills != null && !skills.isEmpty()) {
                for (String skill: skills) {
                    if(jobDescription.toUpperCase().contains(skill.toUpperCase())){
                        Boolean isChecked = sharedPreferences.getBoolean(skill,false);
                        if (isChecked) {
                            Chip chip = new Chip(getContext());
                            chip.setChecked(false);
                            chip.setText(skill);
                            chip.setChipBackgroundColor(ContextCompat.getColorStateList(getContext(), R.color.brand_Pistachio));
                            tagsGroup.addView(chip);
                        }
                    }
                }
            }
        } else {
            companyName = "Company Name Unavailable";
            location = "Location Unavailable";
            jobTitle = "Job Title Unavailable";
            jobDescription = "Skills Unavailable";
            jobType = "Job Type Unavailable";
        }

        // Set variable data
        companyNameView.setText(companyName);
        jobTitleView.setText(jobTitle);
        jobTypeView.setText(jobType);
        jobLocationView.setText(location);
        String prettyJobDesc = JobDescriptionBeautifier(jobDescription);
        jobDescriptionView.setText(prettyJobDesc);
        companyLogoView.setImageResource(R.mipmap.ic_launcher);

        return convertView;
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
