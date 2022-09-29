package com.example.devployedapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

/* reference webpages:
Android Docs Create dynamic lists with RecyclerView: https://developer.android.com/develop/ui/views/layout/recyclerview
Android Docs Example of adding RecyclerView: https://github.com/android/views-widgets-samples/commit/aea13d5dbb4b5f1844bcb7f1b330b93f90750052#diff-c26aac7ad610d30f5ab5e74a8f575f27604ae4a3ce747c87d23dbc0a94df22f6
*/

public class FavoritesRejectedListPages extends AppCompatActivity  {

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }
    protected LayoutManagerType mCurrentLayoutManagerType;
    //protected CustomAdapter mAdapter;
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected String[] mDataset;

    protected JobPostInformation theJobPostInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_pages_favorites_rejected);


        // Toolbar 'Back Button' functionality to go 'back' a page.
        ImageButton backButton_profile = findViewById(R.id.backButton_SaveToMain);
        backButton_profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view_fragment, container, false);

        // BEGIN_INCLUDE(initializeRecyclerView)
        //mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        //mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        mRecyclerView.findViewById(R.id.Saved_Jobs_RecyclerView);
        //mRecyclerView.setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        //mAdapter = new CustomAdapter(mDataset);
        // Set CustomAdapter as the adapter for RecyclerView.
        //mRecyclerView.setAdapter(mAdapter);
        // END_INCLUDE(initializeRecyclerView)
        return rootView;
    }
    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        //mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    public void CustomAdapter(String[] dataSet) {
        //mDataset = dataSet;
    }
    // Create new views (invoked by the layout manager)
    //@Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_fragment, viewGroup, false);

        return new ViewHolder(view) {};
    }

    // Replace the contents of a view (invoked by the layout manager)
    //@Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        //viewHolder.getTextView().setText(mDataset[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    //@Override
    public int getItemCount() {
        return mDataset.length;
    }










}
