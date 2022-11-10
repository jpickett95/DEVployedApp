package com.example.webparser.events.handlers;

import com.example.webparser.events.EventManager;

public abstract class EventHandler <T> {
    private boolean isActive;

    public EventHandler (){ isActive = true; }
    public void SetDisabled(){ isActive = false; }
    public void SetActive(){ isActive = true; }
    public boolean IsActive(){return isActive;}

    EventManager manager;
    public void SetManager(EventManager manager){this.manager = manager;}
    public abstract void EventHasCompleted();
}
