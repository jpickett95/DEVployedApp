package com.example.webparser.events.handlers;

import com.example.webparser.events.interfaces.SearchCompletedCallback;

public class SearchCompletedEventHandler <T extends SearchCompletedCallback> extends EventHandler {
    T target;
    public SearchCompletedEventHandler(T target){ this.target = target; }
    public void EventHasCompleted() { target.SearchHasCompleted(); }
}
