package com.example.devployedapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/* reference webpages:
Android Docs Create dynamic lists with RecyclerView: https://developer.android.com/develop/ui/views/layout/recyclerview
Android Docs Example of adding RecyclerView: https://github.com/android/views-widgets-samples/commit/aea13d5dbb4b5f1844bcb7f1b330b93f90750052#diff-c26aac7ad610d30f5ab5e74a8f575f27604ae4a3ce747c87d23dbc0a94df22f6
*/
/*Reference Videos:
* RecyclerView - Everything you need to Know, Practical Coding: https://www.youtube.com/watch?v=Mc0XT58A1Z4
* */

public class SavedJobsListPage extends AppCompatActivity {
    private final String SWIPED_RIGHT_ACTION = "com.example.devployed.SWIPED_RIGHT";
    IntentFilter filter = new IntentFilter(SWIPED_RIGHT_ACTION);

    int[] companyLogos = {R.drawable.ic_baseline_add_24, R.drawable.ic_baseline_arrow_back_24, R.drawable.ic_launcher_background,
            R.drawable.ic_baseline_check_24, R.drawable.ic_baseline_navigate_next_24, R.drawable.ic_launcher_foreground, R.drawable.ic_baseline_add_24};

    JobPost_RecyclerViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_pages_saved);

        ArrayList<JobPostInformation> jobPostings = new ArrayList<>();

        class BroadcastApp extends BroadcastReceiver {
            @Override
            public void onReceive(Context context, Intent intent) {
                jobPostings.add(new JobPostInformation(intent.getStringExtra("companyName"),intent.getStringExtra("jobTitle"),intent.getStringExtra("skills"),companyLogos[0]));
                adapter.notifyDataSetChanged();
                Log.d("LIST", "notified");
                Toast.makeText(SavedJobsListPage.this, "Swiped Right!", Toast.LENGTH_SHORT).show();

                // implement changes once swiped right
            }
        }

        this.registerReceiver(new BroadcastApp(), filter);

        // Toolbar 'Back Button' functionality to go 'back' a page.
        ImageButton backButton_profile = findViewById(R.id.backButton_SaveToMain);
        backButton_profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        adapter = new JobPost_RecyclerViewAdapter(this, jobPostings);
        RecyclerView recyclerView = findViewById(R.id.Saved_Jobs_RecyclerView);
        //setUpJobPostModels();
        //adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void setUpJobPostModels(){
        String[] companyNames = getResources().getStringArray(R.array.company_names);
        String[] jobTitles = getResources().getStringArray(R.array.job_titles);
        String[] skillsToMatch = getResources().getStringArray(R.array.skills_to_match);

        for (int i = 0; i < companyNames.length; i++){
            //jobPostings.add(new JobPostInformation(companyNames[i],jobTitles[i],skillsToMatch[i],companyLogos[i]));
        }
    }

}
