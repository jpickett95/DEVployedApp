package com.example.webparser.events.interfaces;
import com.example.webparser.data.JobListing;

public interface ListingAddedCallback {
    void ListingWasAdded();
    void ListingWasAdded(JobListing listing);
}
