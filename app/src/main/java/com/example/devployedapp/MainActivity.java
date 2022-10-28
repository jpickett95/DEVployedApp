package com.example.devployedapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.devployedapp.databinding.ActivityMainBinding;
import com.example.webparser.data.JobListing;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.List;

public class MainActivity extends DrawerBaseActivity {

    ActivityMainBinding activityMainBinding;


    //region Variables called before OnCreate() method

    // Variables For Job Swipe Cards
    private swipeCardsArrayAdapter arrayAdapter;
    List<JobListing> rowItems;

    Dialog filtersDialog; // For filters popup window on main activity
    Dialog jobCardBlowUpDialog; // For enlarging the jobCard upon clicking
    DBManager dbManager;

//endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        allocateActivityTitle("Job Matches");

        dbManager = new DBManager(this);

        //region SwipeCards: Initialize and Parse
        rowItems = dbManager.getUnseenJobs();
        //endregion

        arrayAdapter = new swipeCardsArrayAdapter(this, R.layout.swipecards_item, rowItems );
        //arrayAdapter.notifyDataSetChanged(); must be called after adding new items to the 'rowItems' list from this point

        SwipeFlingAdapterView flingContainer = findViewById(R.id.frame);
        flingContainer.setAdapter(arrayAdapter);

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                //this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

    //region SwipeCards: After Swiping Functions
            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                Toast.makeText(MainActivity.this, "Reject!", Toast.LENGTH_SHORT).show();
                JobListing job = (JobListing) dataObject;
                dbManager.updateJobStatus(job.GetJobID(), dbManager.REJECTED);
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(MainActivity.this, "Apply!", Toast.LENGTH_SHORT).show();
                JobListing job = (JobListing) dataObject;
                dbManager.updateJobStatus(job.GetJobID(), dbManager.SAVED);
            }
//endregion

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here

                // TESTING PURPOSES
                rowItems.addAll(dbManager.getUnseenJobs());
                arrayAdapter.notifyDataSetChanged();
                Log.d("LIST", "notified");
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                //View view = flingContainer.getSelectedView();
                //view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                //view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                //Toast.makeText(MainActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();

                // casts dataObject to JobListing and passes to the method to create full CardView
                JobListing job = (JobListing) dataObject;
                ShowJobCardExpanded(job);
            }
        });

        //Menu to go to profile page
        Button menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener((View v) -> startActivity(new Intent(MainActivity.this, ProfilePage.class)));

        //Menu button to go to saved jobs page
        Button savedJobsButton = findViewById(R.id.Saved_Jobs_button);
        savedJobsButton.setOnClickListener((View view) -> startActivity(new Intent(MainActivity.this, SavedJobsListPage.class)));

        //Menu button to go to saved jobs page
        Button rejectedJobsButton = findViewById(R.id.rejected_Jobs_button);
        rejectedJobsButton.setOnClickListener((View v) -> startActivity(new Intent(MainActivity.this, RejectedJobsListPage.class)));

        filtersDialog = new Dialog(this); // For filters popup window on main activity
        jobCardBlowUpDialog = new Dialog(this);

    }

    // For filters popup window on main activity
    public void ShowFiltersPopup(View v){
        // popup is dismissed when user clicks the completed button
        FloatingActionButton completedButton;
        filtersDialog.setContentView(R.layout.popup_filters);
        completedButton = filtersDialog.findViewById(R.id.floatingActionButton_complete);
        completedButton.setOnClickListener((View view) -> filtersDialog.dismiss());
        filtersDialog.show();
    }

    public void ShowJobCardExpanded(JobListing job) {
        // popup to display the full job description in a scrollView
        jobCardBlowUpDialog.setContentView(R.layout.jobcard_blowup_item);
        Button closeButton;
        ImageView imageView = jobCardBlowUpDialog.findViewById(R.id.cardBlowUp_item_companyLogo);
        imageView.setImageResource(R.drawable.ic_baseline_work_24);
        TextView companyName, jobTitle, skillsMatched, fullDescription;
        companyName = jobCardBlowUpDialog.findViewById(R.id.cardBlowUp_item_companyName);
        jobTitle = jobCardBlowUpDialog.findViewById(R.id.cardBlowUp_item_jobTitle);
        skillsMatched = jobCardBlowUpDialog.findViewById(R.id.cardBlowUp_item_skillsMatched);
        fullDescription = jobCardBlowUpDialog.findViewById(R.id.cardBlowUp_item_fullDescription);
        companyName.setText(job.GetJobLocation());
        jobTitle.setText(job.GetJobTitle());
        skillsMatched.setText(job.GetJobType());
        fullDescription.setText(job.GetJobDescription());


        closeButton = jobCardBlowUpDialog.findViewById(R.id.cardBlowUp_close_window);
        closeButton.setOnClickListener((View view) -> jobCardBlowUpDialog.dismiss());
        jobCardBlowUpDialog.show();
    }



    // For SwipeCards
    /*
    static void makeToast(Context ctx, String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.right)
    public void right() {

         // Trigger the right event manually.

        flingContainer.getTopCardListener().selectRight();
    }
    @OnClick(R.id.left)
    public void left() {
        flingContainer.getTopCardListener().selectLeft();
    }
    */
}