package com.example.webparser.targets;

import com.example.webparser.data.JobListing;
import com.example.webparser.WebParser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;
public abstract class ParserTarget  {
    //this member will be shared with every other ParserTarget Subclass
    //as it is assumed that you will be wanting to search other sites for the same jobs
    //if you were going to switch to them.
    private WebParser parser;

    static Vector<String> filter_arguments = new Vector<String>();
    protected String Name;
    public void SetWebParser(WebParser parser){ this.parser = parser; }
    public String GetName(){
        return Name;
    }

    //this is the base_url of the abstract class. It is the url that would be used if you were
    //wanting to search for a job at a particular site
    protected String base_url;
    public ParserTarget (WebParser parser){ this.parser = parser; }

    //this will be the css selector for the div or element that contains the
    //job info when you request a webpage

    //this is of course the term (job title) that we search for when
    //we request a page
    protected String search_term;


    protected String JobTitleSelector;
    protected String JobListingURLSelector;
    protected String ListingContainerSelector;
    protected String LocationSelector;
    protected String LogoSelector;
    protected String SubCompanySelector;
    protected String QualificationsSelector;

    /*
    *this will contain the information of a specific arg_name:arg_value
    *that hold each specific field for our job listings
    *
    * (As shown in Cisco.java)
    * <td data-th="Job Title"></d>
    * the arg_name would be "data-th" and arg_value would be "Job Title"
    * so that the parser knows how to get the information when using the app
    * Note: This is just a "prototype" build, it will very much likely be changed/upgraded
    * as we figure out how to implement it better.zsaaaaaaaaaaaaaaaedrrrr
     */
    protected Hashtable<String, String> JobItemStorage;
    public ParserTarget(){}

    public abstract String GetJobTitle(Element element);
    public abstract String GetListingURL(Element element);
    public abstract Elements GetJobListings(Element listingContainer);
    public abstract String GetJobType(Element element);
    public abstract Element GetListingContainer(Document document);
    //public abstract String GetDepartment(Element element);
    public abstract String GetLocation(Element element);
    //public abstract String GetLogoSelector(Element element);
    //public abstract String GetSubCompany(Element element);
    public abstract String GetQualifications(Element element);
    public abstract HashMap GetAdditionalInformation(Element element);

    /*
    In this method you will serialize the search term however you need to and return it through the
    GetSearchUrl() method.
    */
    abstract String MakeSearchUrl();

    //these are interface methods that should never be changed
    public void SetSearchTerm(String _search_term){ search_term = _search_term; }
    public String GetSearchUrl(){ return MakeSearchUrl(); }
    //these arguemnts will be shared between all instances
    public void AddFilterArgument(String arg){ filter_arguments.add(arg); }
    public abstract Vector<JobListing> Main() throws IOException;

    /**
     * <h3>public void StartParsing(WebParser parser)</h3>
     * <hr>
     * 
     * <p>
     * This method is just a caller method that will call the subclass implemented Main method
     * and call the parser.AddListing() callback method for each retrieved listing to allow for any Event 
     * based notification-method (Adding the listing to an active searching page/do analytics on the job listing/etc)
     * </p>
     * 
     */
    public void StartParsing() throws IOException{
        //Note that the Main method is a abstract method that will be implemented by each subclass of the 
        //ParserTarget class.
        Vector<JobListing> listings = Main();

        for (JobListing listing:listings){
            parser.AddListing(listing);
        }
        WebParser.eventManager.NotifySearchHasCompleted();
    }
}
