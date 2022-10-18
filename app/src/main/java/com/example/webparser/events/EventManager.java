package com.example.webparser.events;

import com.example.webparser.data.JobListing;
import com.example.webparser.events.handlers.ListingAddedEventHandler;
import com.example.webparser.events.handlers.SearchCompletedEventHandler;

import java.util.Vector;

public class EventManager {
    static Vector<SearchCompletedEventHandler> searchCompletedEventHandlers = new Vector<>();
    static Vector<ListingAddedEventHandler> listingAddedEventHandlers = new Vector<>();
    public EventManager(){}

    public void RegisterEventHandler(SearchCompletedEventHandler searchCompletedEventHandler){
        searchCompletedEventHandler.SetManager(this);
        searchCompletedEventHandlers.add(searchCompletedEventHandler);
    }
    public void RegisterEventHandler(ListingAddedEventHandler listingAddedEventHandler){
        listingAddedEventHandler.SetManager(this);
       listingAddedEventHandlers.add(listingAddedEventHandler);
    }

    public void NotifySearchHasCompleted(){
         for (SearchCompletedEventHandler handler:searchCompletedEventHandlers){
            if (handler.IsActive()) {
                handler.EventHasCompleted();
            }
        }
    }

    public void NotifyListingWasAdded(JobListing jobListing){
        for (ListingAddedEventHandler handler:listingAddedEventHandlers){
            if (handler.IsActive()) {
                handler.EventHasCompleted(jobListing);
            }
        }
    }

}
