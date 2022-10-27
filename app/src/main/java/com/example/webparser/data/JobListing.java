package com.example.webparser.data;

import java.util.HashMap;

import kotlinx.coroutines.Job;

/** JobListing (Data Class)
 * 
 * Members:
 *  private String JobTitle;
 *  private String JobLocation;
 *  private String JobType;
 *  private HashMap<String, String> AdditionalInformation;
 * 
 * 
 * This is a Data Class designed to hold the job information
 * associated to each listing scraped/queried from each website in the WebParser
 * 
 * There are 4 specific items (JobTitle, JobLocation, JobType and JobListingUrl) that have been given their own
 * class member variables (due to those bits of information being included globally across all listings).
 * 
 * All other information (Job Qualifications, Job Posting Time/Date, Etc) will be stored in the AdditionalInformation
 * class member.
 * 
 * A copy of the HashMap can be retrived via the JobListing.GetAdditionalInformation() method.
 */
public class JobListing {
    private String Company;
    private String JobTitle;
    private String JobLocation;
    private String JobListingUrl;
    private String JobType;
    private String JobDescription;
    private long JobID;
    private HashMap<String, String> AdditionalInformation;

    public JobListing(){
        Company = "";
        JobTitle = "";
        JobLocation = "";
        JobType = "";
        JobDescription = "";
        JobListingUrl = "";
        AdditionalInformation = new HashMap<>();
    }

    /**
     * This Initializer is intended to be used when creating a ListingRespose from
     * already stored information (aka saved jobs in the SQLite database), as we will already have everything in a format that can
     * be directly added from the SQLite database.
     */
    public JobListing (String jobTitle, String jobType, String jobLocation, String jobDescription, HashMap<String, String> additionalInformation){
        JobTitle = jobTitle;
        JobType = jobType;
        JobLocation = jobLocation;
        AdditionalInformation = new HashMap<>();
    }
    public void SetCompanyName (String companyName){Company = companyName;}
    public String GetCompanyName (){return Company;}

    public void SetJobListingUrl(String jobListingUrl){ JobListingUrl = jobListingUrl; }
    public String GetJobListingUrl(){ return JobListingUrl; }

    public void SetJobTitle(String jobTitle){ JobTitle = jobTitle; }
    public String GetJobTitle(){ return JobTitle; }

    public void SetJobLocation(String jobLocation){ JobLocation = jobLocation; }
    public String GetJobLocation(){ return JobLocation; }

    public void SetJobDescription(String jobDescription){JobDescription = jobDescription;}
    public String GetJobDescription(){ return JobDescription; }

    public void SetJobType(String jobType){ JobType = jobType; }
    public String GetJobType(){ return JobType; }

    public void SetJobID(long id){JobID = id;}
    public long GetJobID(){return JobID;}

    /**
    * This Method will append the:
    *   String keyName
    *   String keyValue
    *
    * if it isn't already in the HashMap. Otherwise, it will replace the value with the provided one
    */
    public void AddAdditionalInformation(String keyName, String keyValue){
        if (!AdditionalInformation.containsKey(keyName)) {
            AdditionalInformation.put(keyName, keyValue);
        } else {
            AdditionalInformation.replace(keyName, keyValue);
        }
    }

    /**
     * <h3>JobListing.GetAdditionalInformation()</h3>
     * <hr>
     * 
     * <p>
     * This method will return a copy of the AdditionalInformation HashMap, as to avoid any 
     * accidental mistakes that may overwrite the information stored within. However it is still possible
     * to change information in the HashMap via the JobListing.AddAdditionalInformation() method as it will
     * overwrite information if the associated key is provided. However you will need to do another call to 
     * this method to update/replace the previous HashMap that was retrieved.
     * </p>
     * 
     * 
     * @return HashMap<String, String>
     */
    public HashMap<String, String> GetAdditionalInformation(){
    
        HashMap<String, String> returnedHashMap = new HashMap<>();

        //Making a copy of the AdditionalInformation HashMap
        //This will itterate through each individual entry (String key, String value) 
        //and add them to the HashMap we will return to the UI.
        //the -> {...} is a lambda expression
        AdditionalInformation.forEach((key, value) -> {returnedHashMap.put(key, value);});

        return returnedHashMap;
    }

}
