package com.example.devployedapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.webparser.data.JobListing;

import java.util.List;

public class swipeCardsArrayAdapter  extends ArrayAdapter<JobListing> {
    //Context context;

    public swipeCardsArrayAdapter(Context context, int resourceId, List<JobListing> jobPosts) {
        super(context, resourceId, jobPosts);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        JobListing jobPost = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.swipecards_item, parent, false);
        }

        // Find View IDs
        TextView companyName = (TextView) convertView.findViewById(R.id.swipeCards_item_companyName);
        TextView jobTitleView = (TextView) convertView.findViewById(R.id.swipeCards_item_jobTitle);
        TextView skillsMatched = (TextView) convertView.findViewById(R.id.swipeCards_item_skillsMatched);
        ImageView companyLogo = (ImageView) convertView.findViewById(R.id.swipeCards_item_companyLogo);

        String location = jobPost.GetJobLocation();
        String jobTitle = jobPost.GetJobTitle();
        String jobDescription = jobPost.GetJobDescription();
        // Set variable data
        if(location != null) {companyName.setText(location);} else {companyName.setText("Company Name Unavailable");}
        if(jobTitle!= null) {jobTitleView.setText(jobTitle);} else {jobTitleView.setText("Job Title Unavailable");}
        if(jobDescription != null) {skillsMatched.setText(jobDescription);} else {skillsMatched.setText("Skills Unavailable");}
        companyLogo.setImageResource(R.mipmap.ic_launcher);

        return convertView;
    }
}
