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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class swipeCardsArrayAdapter  extends ArrayAdapter<JobListing> {
    //Context context;
    public static final String SHARED_PREFERENCES = "sharedPreferences";
    public static final String PROFILE_PROGRAMMINGLANGUAGES_CHIPSTRINGSET = "profileProgrammingLanguages";
    public static final String PROFILE_MYSKILLS_CHIPSTRINGSET = "profileMySkills";
    private Set<String> languages;
    private Set<String> skills;
    Set<String> tagsToDisplay;

    public swipeCardsArrayAdapter(Context context, int resourceId, List<JobListing> jobPosts) {
        super(context, resourceId, jobPosts);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        JobListing jobPost = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.swipecards_item, parent, false);
        }

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        languages = sharedPreferences.getStringSet(PROFILE_PROGRAMMINGLANGUAGES_CHIPSTRINGSET,languages);
        skills = sharedPreferences.getStringSet(PROFILE_MYSKILLS_CHIPSTRINGSET, skills);
        Set<String> tags = skills;
        tags.addAll(languages);

        // Find View IDs
        TextView companyNameView = (TextView) convertView.findViewById(R.id.swipeCards_item_companyName);
        TextView jobTitleView = (TextView) convertView.findViewById(R.id.swipeCards_item_jobTitle);
        TextView skillsMatchedView = (TextView) convertView.findViewById(R.id.swipeCards_item_skillsMatched);
        ImageView companyLogoView = (ImageView) convertView.findViewById(R.id.swipeCards_item_companyLogo);
        TextView jobLocationView = (TextView) convertView.findViewById(R.id.swipeCards_item_jobLocation);
        TextView jobTypeView = (TextView) convertView.findViewById(R.id.swipeCards_item_jobType);
        ChipGroup tagsGroup = (ChipGroup) convertView.findViewById(R.id.swipeCards_item_tags);

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

            /*if (!tags.isEmpty()) {
                tagsToDisplay = new HashSet<>();
                for (String word : jobDescription.split("\\s+")) {
                    for (String tag : tags) {
                        if (word.contains(tag)) {
                            tagsToDisplay.add(tag);
                            tags.remove(tag);
                        }
                    }
                }
            }
            if(!tagsToDisplay.isEmpty()){
                for(String tag : tagsToDisplay){
                    Chip chip = new Chip(getContext());
                    chip.setChecked(false);
                    chip.setText(tag);
                    chip.setChipBackgroundColor(ContextCompat.getColorStateList(getContext(),R.color.brand_Pistachio));
                    tagsGroup.addView(chip);
                }
            }*/
        } else {
            location = "Company Name Unavailable";
            jobTitle = "Job Title Unavailable";
            jobDescription = "Skills Unavailable";
            jobType = "Job Type Unavailable";
        }

        // Set variable data
        companyNameView.setText(companyName);
        jobTitleView.setText(jobTitle);
        jobTypeView.setText(jobType);
        jobLocationView.setText(location);
        skillsMatchedView.setText(jobDescription);
        companyLogoView.setImageResource(R.mipmap.ic_launcher);

        return convertView;
    }
}
