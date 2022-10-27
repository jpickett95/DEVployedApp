package com.example.webparser;

import com.example.webparser.data.JobListing;
import com.example.webparser.events.EventManager;
import com.example.webparser.targets.Amazon;
import com.example.webparser.targets.Cisco;
import com.example.webparser.targets.ParserTarget;
import com.example.webparser.threading.SearchRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;


public class WebParser {
    private static final HashMap<String, ParserTarget> targets = new HashMap<>();
    private static final HashMap<String, ParserTarget> selectedTargets = new HashMap<>();
    private static final Vector<JobListing> parsedListings = new Vector<>();
    private static final Vector<JobListing> returnedListings = new Vector<>();
    public static final EventManager eventManager = new EventManager();

    public WebParser() {
        Initialize();
    }

    public boolean IsEmpty(){
        return parsedListings.isEmpty();
    }

    /**
     * <h3>WebParser.Initialize()</h3>
     * <hr>
     * <p>This method will be what initilaizes (despite the Initializer Above) all of the other parts of the WebParser. (Database, Individual ParserTargets, etc)</p>
     */
    private void Initialize(){
        //All you have to do is (like below) add your ParserTarget via the provided AddTarget(new TargetName())
        //NOTE: make sure your ParserTarget has it's Name member set in it's class Initalizer (as shown below)
        /*
         * public TargetName(){
         * }
         */
        //If you do not, the program will likely fail at runtime due to the Name variable being set with no value (unless you set the Name).
        AddTarget(new Cisco(this));
        //AddTarget(new Google());
        AddTarget(new Amazon(this));
    }
    
    /**
     * <h3>WebParser.AddTargetSite(String sitename)</h3>
     * <hr>
     * <p>
     *  This method is intended to manage what Targets are added to the queue of selected sites for parsing
     * </p>
     * <hr>
     */
    public void AddTargetSite(String sitename) {
        if (targets.containsKey(sitename) && !selectedTargets.containsKey(sitename)) {
            selectedTargets.put(sitename, targets.get(sitename));
        }
    }

    /**
     * <h3>WebParser.AddTarget</h3>
     * <hr>
     * <p></p>
     * <p>This method is meant to add each ParserTarget subclass to the targets member
     * as long as the </p>
     * @param target
     */
    private void AddTarget(ParserTarget target){
        String targetName = target.GetName();
        //if the Name variable has been set (or if it wasn't already in the targets member) it 
        //will successfully be added to the targets member
        if (!targetName.isEmpty() && !targets.containsKey(target.GetName())){
            targets.put(targetName, target);
        }
    }

    /**
     * <h3>WebParser.RemoveTargetSite(String sitename)</h3>
     * <hr>
     * <p>
     *  This method is intended to manage what Targets are removed from the queue of selected sites for parsing
     * </p>
     * <hr>
     */
    public void RemoveTargetSite(String sitename){
        selectedTargets.remove(sitename);
    }

    /**
     * <h3>WebParser.ClearTargetSites()</h3>
     * <hr>
     * This method will completely remove al sites from the selectedSites Member.
     * <hr>
     */
    public void ClearSelectedSites(){
        selectedTargets.clear();
    }

    /**
     * <h3>WebParser.GetTargetNames()</h3>
     * <hr>
     * This will return a String Array containing the names of each configured ParserTarget.  
     * <p>
     * 
     * This is intended to give you the information needed to be able to interact with:
     * <ul>
     *     <li>WebParser.AddTargetSite(String sitename)</li>
     *     <li>WebParser.RemoveTargetSite(String sitename)</li>
     * </ul>
     * </p>
     * <hr>
     * @return String[sitenames]
     */
    public Collection<String> GetTargetNames(){
        return targets.keySet();
    }

    /**
     * WebParser.AddListing(JobListing listing)
     * 
     * This method will allow for each ParserTarget to add a listing to the main parsedListings
     * member.
     * 
     * This also allows for other events to be triggered by the adding of the listing
     * 
     * This will primarily be used to collect analytics (# of appearances of languages/technologies/etc)
     * that appear in each job listing (Primarily in the Description information)
     */
    public void AddListing(JobListing listing){
        //This isn't implemented yet, but it is an example of what this can be used for.
        //for (EventHandler handler:events) {handler.Notify;};
        parsedListings.add(listing);
        eventManager.NotifyListingWasAdded(listing);
    }

    public void StartParsing() throws IOException, InterruptedException {


        Collection<ParserTarget> Targets;
        
        // if there are no selected targets, then the parser will just itterate through 
        // every available ParserTarget that has been configured.
        if (selectedTargets.size() != 0){
            Targets = selectedTargets.values();
        } else {
            Targets = targets.values();
        }

        //This will iterate through every available ParserTarget and will add all of the returned
        //listings into the main parsedListings to be accessed in the UI
        //for (ParserTarget mTarget:Targets){
        //    mTarget.StartParsing(this);
        //}
        SearchRunner runnerThread = new SearchRunner(Targets);
        runnerThread.start();

        //runnerThread.join();
    }

    /**
     * <h3>WebParser.ClearJobListings()</h3>
     * <hr>
     * 
     * <p></p>
     * 
     * <p> This method will completely clear out and delete all of the listings that have been parsed/retrieved by the ParserTargets. </p>
     * <p> This means that in order to retrieve any more you will need to do another search/query to retrive more from the source sites </p>
     * 
     */
    public void ClearJobListings(){
        parsedListings.clear();
        returnedListings.clear();
    }

    /**
     * <h3>WebParser.RestoreJobListings()</h3>
     * <hr>
     * <p></p>
     * <p>This will re-add all of the returned jobs back to the parsedListings, if the user were to ever wish re-search through the 
     * jobs that they have already rejected/re-thought about/etc</p>
     * 
     * <p><b>THIS WILL RE-ADD ALL OF THE LISTINGS. THIS CAN BE HUNDEREDS OF JOBS IF THE USER HAS BEEN SEARCHING FOR A WHILE</b</p>
     */
    public void RestoreJobListings(){
        parsedListings.addAll(returnedListings);
        returnedListings.clear();
    }

    /**
     * <h3>JobListing.GetJobListing()</h3>
     * <hr>
     * <p>
     * This method will remove & return a random JobListing from the parsedListing member, as well as adding the JobListing to the
     * returnedListings member to keep track of which jobs have been returned to the UI. This is done to ensure that jobs arent cycled 
     * through multiple times.
     * </p>
     * 
     * <p>
     * Although if one is wanting to clear or reset the job listings you would call one of the following methods.
     * <ul>
     * <li>WebParser.ClearJobListings()</li>
     * <li>WebParser.RestoreJobListings()</li>
     * </ul>
     * </p>
     * 
     * <p>
     * It can be called individually if a single listing is wanted, but was made to be used in the WebParser.GetListingCollectionOfSize() 
     * method
     * </p>
     * @return JobListing
     */
    public JobListing GetJobListing(){
        int NumberOfJobListings = parsedListings.size();

        JobListing listing = null;

        if (NumberOfJobListings > 0) {
            Random randomSelector = new Random();

            listing = parsedListings.get(randomSelector.nextInt(parsedListings.size()));

            parsedListings.remove(listing);
            returnedListings.add(listing);
        }

        return listing;
    }


    /**
     * <h3>WebParser.GetListingCollectionOfSize(int CollectionSize)</h3>
     * <br>
     * 
     * <p>
     * This method will create a JobListing array of the size that is specified using the CollectionSize argument. However, 
     * if there aren't enough listings present in parsedListings then only the available number will be allocated.
     * </p>
     * 
     * <p>
     * You will need to account for the possiblity that there may not be as many listings as you expected, thus you will either need
     * to do a for-in loop 
     * </p>
     * <p>
     * EX:
     * </p>
     * <code>
     * for (JobListing listing:ReturnedListings) {  
     * //code here  
     * };  
     * </code>
     * 
     * <p>
     * Or simply having a check in place to ensure that the program is aware of the possible difference in the size of the
     * collection in respect to what was requested.
     * </p>
     * 
     * @param CollectionSize
     * @return JobListing[]
     */

    public ArrayList<JobListing> GetListingCollectionOfSize(int CollectionSize){
        ArrayList<JobListing> JobCollection = new ArrayList<>();

        // This is checking if the CollectionSize is larger than the size of parsedListings. 
        // If CollecionSize is less than or equal to the size of parsedListings then CollectionSize will stay the same
        // However if it is less than the size of parsedListings it will be dropped to the current size of parsedListings.
        CollectionSize = Math.min(CollectionSize, parsedListings.size());
        if (CollectionSize > 0) {

            for (int i = 0; i < CollectionSize; i++) {
                JobCollection.add(GetJobListing());
            }
        }

        return JobCollection;
    }
}
