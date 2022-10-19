package com.example.webparser.targets;

import com.example.webparser.WebParser;
import com.example.webparser.data.JobListing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;


/*
Cisco Job Search:
Job Search URL: https://jobs.cisco.com/jobs/SearchJobs/[insert job title/search term]

Job Listings:
Kept in table.

<table...summary="Search Results" id>
    <tbody>
        //Each position is located in a table row <tr></tr> as shown below
        <tr>
            <a href="..."> // The anchor tag surrounding the job title
                <td data-th="Job Title"></td>           //Job Title
            <\a>
            <td data-th="Actions"></td>             //Area of Interest (the actual one) (job type IT, Development, etc)
            <td data-th="Area of Interest"></td>    //Job Type
            <td data-th="Location"></td>            //Location
            <td data-th="Alternate Location"></td>  //Alternate Location
        </tr>
    </tbody>
</table>


*/


public class Cisco extends ParserTarget{

    public  Cisco (WebParser webParser){
        super(webParser);
        Name = "Cisco";
        base_url = "https://jobs.cisco.com/jobs/SearchJobs/?21178=%5B169482%2C207800%5D&21178_format=6020&listFilterMode=1";
        ListingContainerSelector = ".section_content table tbody";
        LogoSelector = "https://jobs.cisco.com/portal/4/images/logo.svg";
    }

    // <td data-th="Job Title"></td>           //Job Title
    public String GetJobTitle(Element element){
        String JobTitle;
        JobTitle = element.getElementsByAttributeValue("data-th", "Job Title").text();
        return JobTitle;
    };


    //TODO: Implement this method (when I have time)
    public String GetQualifications(Element element){
        return  element.getElementsByAttributeValue("","").text();
    }

    // <td data-th="Location"></td>            //Location
    public String GetLocation(Element element){
        String location;
        location = element.getElementsByAttributeValue("data-th", "Location").text();
        return location;
    }

    // <td data-th="Actions"></td>             //Area of Interest (the actual one) (job type IT, Development, etc)
    public String GetJobType(Element element){
        String jobType;
        jobType = element.getElementsByAttributeValue("data-th", "Area of Interest").text();
        return jobType;
    }

    public Element GetListingContainer(Document document){
        Element container = document.select(ListingContainerSelector).first();
        return  container;
    }

    // <td data-th="Area of Interest"></td>    //Job Type
    public String GetDepartment(Element element){
        String Department;
        Department = element.getElementsByAttributeValue("data-th", "Area of Interest").text();
        return Department;
    }

    public String GetListingURL(Element element){
        String JobListingURL;
        JobListingURL = element.getElementsByTag("a").attr("href");
        return JobListingURL;
    }

    public Element GetCompanyListing(Document document){
        Element CompanyListing;
        CompanyListing = document.select("").first();
        return CompanyListing;
    }

    /*
     * <table>
     *     <tbody>
     *         <tr>
     *             //each listing will be contained (as shown in the example at the top of this document)
     *             //inside a <tr></tr> tag. This is where we get each listing from the Listing container
     *         </tr>
     *     </tbody>
     * </table>
     */
    public Elements GetJobListings(Element listingContainer){
        return listingContainer.select("tr");
    }

    public HashMap GetAdditionalInformation(Element element){
        HashMap<String, String> additionalInformation = new HashMap();

        String AdditionalLocation = element.getElementsByAttributeValue("data-th","Additional Location").text();
        // <td data-th="Alternate Location"></td>  //Alternate Location
        // if the "Additional Location" field is not Empty
        if (!AdditionalLocation.isEmpty()){
            additionalInformation.put("Additional Location", AdditionalLocation);
        }

       return  additionalInformation;
    }

    //TODO: Implement this with a URL class, in order to make creating these much easier.
    //      The URL class would handle the adding and removing of arguments (Country, City, Job Search Terms, etc)
    //      and would make making search urls easier (for the most part)
    public String MakeSearchUrl(){
        return  base_url + search_term;
    };

    public Vector<JobListing> Main() throws IOException {
        Vector<JobListing> jobListings = new Vector<>();

        // This url with the "SearchJobResultsAJAX" (along with the same url args as with the regular search url)
        // gives us the listing-count of the total jobs that the site has for the args we've provided
        String resp = Jsoup.connect("https://jobs.cisco.com/jobs/SearchJobsResultsAJAX/?21178=%5B169482,207800%5D&21178_format=6020&21181=%5B186,194,187,191,185%5D&21181_format=6023&listFilterMode=1&projectOffset=25").get().getElementsByTag("body").text();
        //I will use this variable to setup a
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
