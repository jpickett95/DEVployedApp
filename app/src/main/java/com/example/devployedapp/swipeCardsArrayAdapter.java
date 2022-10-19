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
        TextView jobTitle = (TextView) convertView.findViewById(R.id.swipeCards_item_jobTitle);
        TextView skillsMatched = (TextView) convertView.findViewById(R.id.swipeCards_item_skillsMatched);
        ImageView companyLogo = (ImageView) convertView.findViewById(R.id.swipeCards_item_companyLogo);

        // Set variable data
        companyName.setText(jobPost.GetJobLocation());
        jobTitle.setText(jobPost.GetJobTitle());
        skillsMatched.setText(jobPost.GetJobDescription());
        companyLogo.setImageResource(R.mipmap.ic_launcher);

        return convertView;
    }
}
