package com.example.devployedapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class swipeCardsArrayAdapter  extends ArrayAdapter<JobPostInformation> {
    Context context;

    public swipeCardsArrayAdapter(Context context, int resourceId, List<JobPostInformation> jobPosts) {
        super(context, resourceId, jobPosts);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        JobPostInformation jobPost = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.swipecards_item, parent, false);
        }

        // Find View IDs
        TextView companyName = (TextView) convertView.findViewById(R.id.swipeCards_item_companyName);
        TextView jobTitle = (TextView) convertView.findViewById(R.id.swipeCards_item_jobTitle);
        TextView skillsMatched = (TextView) convertView.findViewById(R.id.swipeCards_item_skillsMatched);
        ImageView companyLogo = (ImageView) convertView.findViewById(R.id.swipeCards_item_companyLogo);

        // Set variable data
        companyName.setText(jobPost.getCompanyName());
        jobTitle.setText(jobPost.getJobTitle());
        skillsMatched.setText(jobPost.getSkillsMatch());
        companyLogo.setImageResource(R.mipmap.ic_launcher);

        return convertView;
    }
}
