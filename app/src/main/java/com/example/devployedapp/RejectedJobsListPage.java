package com.example.devployedapp;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devployedapp.databinding.ListPagesRejectedBinding;
import com.example.webparser.data.JobListing;

import java.util.ArrayList;

/* reference webpages:
Android Docs Create dynamic lists with RecyclerView: https://developer.android.com/develop/ui/views/layout/recyclerview
Android Docs Example of adding RecyclerView: https://github.com/android/views-widgets-samples/commit/aea13d5dbb4b5f1844bcb7f1b330b93f90750052#diff-c26aac7ad610d30f5ab5e74a8f575f27604ae4a3ce747c87d23dbc0a94df22f6
*/
/*Reference Videos:
* RecyclerView - Everything you need to Know, Practical Coding: https://www.youtube.com/watch?v=Mc0XT58A1Z4
* */

public class RejectedJobsListPage extends DrawerBaseActivity {

    ListPagesRejectedBinding listPagesRejectedBinding;
    ArrayList<JobListing> jobPostings = new ArrayList<>();
    int[] companyLogos = {R.drawable.ic_baseline_add_24, R.drawable.ic_baseline_arrow_back_24, R.drawable.ic_launcher_background,
            R.drawable.ic_baseline_check_24, R.drawable.ic_baseline_navigate_next_24, R.drawable.ic_launcher_foreground, R.drawable.ic_baseline_add_24};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listPagesRejectedBinding = ListPagesRejectedBinding.inflate(getLayoutInflater());
        setContentView(listPagesRejectedBinding.getRoot());
        allocateActivityTitle("Rejected Jobs");
        DBManager dbManager = new DBManager(this);
        jobPostings = dbManager.getRejectedJobs();

        RecyclerView recyclerView = findViewById(R.id.Rejected_Jobs_RecyclerView);
        //setUpJobPostModels();
        JobPost_RecyclerViewAdapter adapter = new JobPost_RecyclerViewAdapter(this, jobPostings);
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
