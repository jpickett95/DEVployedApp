package com.example.devployedapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devployedapp.databinding.ListPagesSavedBinding;
import com.example.webparser.data.JobListing;

import java.util.ArrayList;

/* reference webpages:
Android Docs Create dynamic lists with RecyclerView: https://developer.android.com/develop/ui/views/layout/recyclerview
Android Docs Example of adding RecyclerView: https://github.com/android/views-widgets-samples/commit/aea13d5dbb4b5f1844bcb7f1b330b93f90750052#diff-c26aac7ad610d30f5ab5e74a8f575f27604ae4a3ce747c87d23dbc0a94df22f6
*/
/*Reference Videos:
* RecyclerView - Everything you need to Know, Practical Coding: https://www.youtube.com/watch?v=Mc0XT58A1Z4
* */

public class SavedJobsListPage extends DrawerBaseActivity {

    ListPagesSavedBinding listPagesSavedBinding;
    ArrayList<JobListing> jobPostings = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listPagesSavedBinding = ListPagesSavedBinding.inflate(getLayoutInflater());
        setContentView(listPagesSavedBinding.getRoot());
        allocateActivityTitle("Saved Jobs");
        DBManager dbManager = new DBManager(this);
        jobPostings = dbManager.getSavedJobs();

        RecyclerView recyclerView = findViewById(R.id.Saved_Jobs_RecyclerView);
        JobPost_RecyclerViewAdapter adapter = new JobPost_RecyclerViewAdapter(this, jobPostings);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
