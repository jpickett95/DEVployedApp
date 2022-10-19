package com.example.webparser.events.handlers;
import com.example.webparser.data.JobListing;
import com.example.webparser.events.interfaces.ListingAddedCallback;

public class ListingAddedEventHandler <T extends ListingAddedCallback> extends SearchEventHandler {
    T target;
    public ListingAddedEventHandler (T target){ this.target = target; }
    public void EventHasCompleted(){
        this.target.ListingWasAdded();
    }
    public void EventHasCompleted(JobListing jobListing){
        this.target.ListingWasAdded();
        this.target.ListingWasAdded(jobListing);
    }
}
