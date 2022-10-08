package com.example.devployedapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // For Job Swipe Cards
    //private ArrayList<String> al; // **** Replaced with 'rowItems'
    //private JobPostInformation[] jobPosts_data;
    private swipeCardsArrayAdapter arrayAdapter;
    private int i; // for onAdapterAboutToEmpty()

    Dialog filtersDialog; // For filters popup window on main activity

    ListView listView;
    List<JobPostInformation> rowItems;
    int[] companyLogos = {R.drawable.ic_baseline_add_24, R.drawable.ic_baseline_arrow_back_24, R.drawable.ic_launcher_background,
            R.drawable.ic_baseline_check_24, R.drawable.ic_baseline_navigate_next_24, R.drawable.ic_launcher_foreground, R.drawable.ic_baseline_add_24};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // For SwipeCards until line (110) ***EDIT***
        rowItems = new ArrayList<JobPostInformation>();
            // Each 'add' is the name of the card
        String[] companyNames = getResources().getStringArray(R.array.company_names);
        String[] jobTitles = getResources().getStringArray(R.array.job_titles);
        String[] skillsToMatch = getResources().getStringArray(R.array.skills_to_match);

        for (int i = 0; i < companyNames.length; i++){
            rowItems.add(new JobPostInformation(companyNames[i],jobTitles[i],skillsToMatch[i],companyLogos[i]));
        }

        arrayAdapter = new swipeCardsArrayAdapter(this, R.layout.swipecards_item, rowItems );
        // arrayAdapter.notifyDataSetChanged(); must be called after adding new items to the 'rowItems' list from this point

        SwipeFlingAdapterView flingContainer = findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);

        // Swipe Listener
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                Toast.makeText(MainActivity.this, "Reject!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(MainActivity.this, "Apply!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here

                // TESTING PURPOSES
                JobPostInformation extraJobPost = new JobPostInformation("ExtraCompany".concat(String.valueOf(i)), "Extra Job".concat(String.valueOf(i)), "Extra Skills".concat(String.valueOf(i)), R.drawable.ic_baseline_add_24);
                rowItems.add(extraJobPost);
                arrayAdapter.notifyDataSetChanged();
                Log.d("LIST", "notified");
                i++;
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
                Toast.makeText(MainActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();

                // potentially link to job application or job posting on company website?
                // ^^ OR pull up a popup window with more specific, detailed information
            }
        });

        //Menu to go to profile page
        Button menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfilePage.class));
            }
        });
        //Menu button to go to saved jobs page
        Button savedJobsButton = findViewById(R.id.Saved_Jobs_button);
        savedJobsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FavoritesRejectedListPages.class));
            }
        });

        //toolbar exit button to exit the app
        ImageButton backButton_main = findViewById(R.id.exitButton_main);
        backButton_main.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                finish();
            }
        });

        filtersDialog = new Dialog(this); // For filters popup window on main activity
    }

    // For filters popup window on main activity
    public void ShowFiltersPopup(View v){
        // popup is dismissed when user clicks the completed button
        FloatingActionButton completedButton;
        filtersDialog.setContentView(R.layout.popup_filters);
        completedButton = filtersDialog.findViewById(R.id.floatingActionButton_complete);
        completedButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                filtersDialog.dismiss();
            }
        });
        filtersDialog.show();
    }

    // For SwipeCards until line (166) ***EDIT***
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