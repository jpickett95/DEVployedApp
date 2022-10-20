package com.example.webparser.targets;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.example.webparser.data.JobListing;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

public class Google extends ParserTarget
{
    public Google(){
        Name = "Google";
        base_url = "https://careers.google.com/jobs/results/?location=United%20States&location=Canada";
    }
    public String GetJobTitle(Element element){
        String JobTitle;
        JobTitle = element.getElementsByClass("h2.gc-card__title").text();
        return JobTitle;
    }

    public String GetListingURL(Element element){
        String JobListingURL;
        JobListingURL = element.getElementsByTag("a").attr("href");
        return JobListingURL;
    }
    public Elements GetJobListings(Element listingContainer){
        return listingContainer.select("li");
    }

    public String GetJobType(Element element){
        String jobType;
        jobType = element.getElementsByAttributeValue("", "").text();
        return jobType;
    }
    public Element GetListingContainer(Document document){
        Element container = document.select(ListingContainerSelector).first();
        return  container;
    }
    public String GetLocation(Element element){
        String location = null;
        location.put("location", "United States");
        return location;
    }

    public String GetQualifications(Element element){
        String Qualifications;
        Qualifications = element.getElementsByClass(".gc-card__qualifications").text();
        return Qualifications;
    }

    public HashMap GetAdditionalInformation(Element element){
        HashMap<String, String> additionalInformation = new HashMap();

        String AdditionalLocation = element.getElementsByClass("li.gc-job-tags__team").text();
        // <td data-th="Alternate Location"></td>  //Alternate Location
        // if the "Additional Location" field is not Empty
        if (!AdditionalLocation.isEmpty()){
            additionalInformation.put("AdditionalLocation", AdditionalLocation);
        }

        return  additionalInformation;
    }
    public String MakeSearchUrl() {return base_url + search_term;}

    public Vector<JobListing> Main() throws IOException {
        Vector<JobListing> jobListings = new Vector<>();

        // This url with the "SearchJobResultsAJAX" (along with the same url args as with the regular search url)
        // gives us the listing-count of the total jobs that the site has for the args we've provided
        //String resp = Jsoup.connect("").txt;
        //int numberOfListings = Integer.valueOf(resp);

        //This (below) is what would allow this process (after altering the url to include the offset)
        //to repeat for each page of job listings that Cisco contains (will implement it when I have time)
        //but this is good enough to test for the moment.
        //
        //for (int offset = 0; offset < numberOfListings; offset + 25) {
        Document doc = Jsoup.connect(base_url).get();

        Element jobListingContainer = GetListingContainer(doc);

        Elements jobListingsOnCurentPage = GetJobListings(jobListingContainer);

        for (Element jobListing:jobListingsOnCurentPage){
            JobListing newJobListing = new JobListing();

            //Setting the job information that is available from the job listing screen
            newJobListing.SetJobTitle(GetJobTitle(jobListing));
            newJobListing.SetJobLocation(GetLocation(jobListing));
            newJobListing.SetJobType(GetJobType(jobListing));
            newJobListing.SetJobListingUrl(GetListingURL(jobListing));

            //requesting the individual job postings and getting the information from there (Description only so far will get others later)
            Document doc_forPos = Jsoup.connect(newJobListing.GetJobListingUrl()).get();
            //Setting the JobDescription from the actual job listing page (will implement the others later, as well as supply html to show what I'm scraping)
            newJobListing.SetJobDescription(doc_forPos.getElementsByClass("job_description").first().text());

            jobListings.add(newJobListing);
        }
        //}



        return jobListings;
    }
}
