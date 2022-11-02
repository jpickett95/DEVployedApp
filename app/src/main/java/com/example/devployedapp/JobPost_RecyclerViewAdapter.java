package com.example.devployedapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.webparser.data.JobListing;

import java.util.ArrayList;

public class JobPost_RecyclerViewAdapter extends RecyclerView.Adapter<JobPost_RecyclerViewAdapter.MyViewHolder> {
    private final JobCardBlowUpInterface jobCardBlowUpInterface;
    Context context;
    ArrayList<JobListing> jobListArray;

    public JobPost_RecyclerViewAdapter(Context context, ArrayList<JobListing> jobListArray,
                                       JobCardBlowUpInterface jobCardBlowUpInterface) {
        this.context = context;
        this.jobListArray = jobListArray;
        this.jobCardBlowUpInterface = jobCardBlowUpInterface;
    }

    @NonNull
    @Override
    public JobPost_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //This is where you inflate the layout (giving a look to our rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_fragment, parent, false);
        return new JobPost_RecyclerViewAdapter.MyViewHolder(view, jobCardBlowUpInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull JobPost_RecyclerViewAdapter.MyViewHolder holder, int position) {
        //assign values to the views we created in the recycler_view_fragment layout file
        //dependent based on the position of the recycler view
        //this also UPDATES the views with info
        JobListing jobPost = jobListArray.get(position);
        String location;
        String jobTitle;
        String jobDescription;
        String jobType;

        // If jobPost is null, display "content unavailable" message; otherwise, display jobPost information
        if(jobPost != null) {
            location = jobPost.GetJobLocation();
            jobTitle = jobPost.GetJobTitle();
            jobDescription = jobPost.GetJobDescription();
            jobType = jobPost.GetJobType();
        } else {
            location = "Company Name Unavailable";
            jobTitle = "Job Title Unavailable";
            jobDescription = "Skills Unavailable";
            jobType = "Job Type Unavailable";
        }

        // Assign data to views
        holder.tvName.setText(jobType);
        if (jobTitle.length() > 31) {
            holder.tvTitle.setText(jobTitle.substring(0, 30) + "...");
        } else holder.tvTitle.setText(jobTitle);
        if (jobDescription.length() > 101) {
            holder.tvMatch.setText(jobDescription.substring(0, 100) + "...");
        } else holder.tvMatch.setText(jobDescription);
        // to change the image on the left of each recyclerView listing based on the activity
        if (context instanceof RejectedJobsListPage){
            holder.imageView.setImageResource(R.drawable.custom_rejected_cross_icon);
        }
        else if (context instanceof SavedJobsListPage){
            holder.imageView.setImageResource(R.drawable.custom_saved_heart_icon);
        }
    }

    @Override
    public int getItemCount() {
        //the RecyclerView wants to know the number of items to display.
        //This method assists the OnBind method.

        return jobListArray.size();
    }

    //MUST be static
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //takes views from recycler_view_fragment layout file similar to an onCreate method

        //creating variables for the items on the recyclerView fragment
        ImageView imageView;
        TextView tvName, tvTitle, tvMatch;

        //initializing the variables
        public MyViewHolder(@NonNull View itemView, JobCardBlowUpInterface jobCardBlowUpInterface) {
            super(itemView);
            imageView = itemView.findViewById(R.id.companyLogo);
            tvName = itemView.findViewById(R.id.companyName);
            tvTitle = itemView.findViewById(R.id.smallTitle);
            tvMatch = itemView.findViewById(R.id.smallSkillsMatch);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (jobCardBlowUpInterface != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            jobCardBlowUpInterface.OnCardClick(position);
                        }
                    }
                }
            });
        }
    }
}
