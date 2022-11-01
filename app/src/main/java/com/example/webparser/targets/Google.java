package com.example.webparser.targets;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.example.webparser.data.JobListing;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import com.example.webparser.data.APIRequest;

import kotlinx.coroutines.Job;

public class Google extends ParserTarget
{
    public Google(){
        Name = "Google";
        base_url = "https://careers.google.com/api/v3/search/?distance=50&location=Canada&location=United%20States&q=&skills=Development%2C%20IT";
    }
    public String GetJobTitle(Element element){ return null; }
    public String GetListingURL(Element element){ return null; }
    public Elements GetJobListings(Element listingContainer){ return null; }
    public String GetJobType(Element element){ return null; }
    public Element GetListingContainer(Document document){ return  null; }
    public String GetLocation(Element element){ return null; }
    public String GetQualifications(Element element){
        String qualification_string = new String();
        Vector<String> qualifications = new Vector<>();

        Element ul = element.getElementsByTag("ul").first();

        Elements lis = ul.getElementsByTag("li");

        for (Element li:lis){
            qualifications.add(li.text().trim());
        }

        qualification_string = String.join(";", qualifications);


        return qualification_string;
    }
    public HashMap GetAdditionalInformation(Element element){ return  null; }
    public String MakeSearchUrl() {return base_url + search_term;}

    public void Main() throws IOException {

        base_url = "https://careers.google.com/api/v3/search/?distance=50&location=Canada&location=United%20States&q=&skills=Development%2C%20IT&page=";
        /*
        //Only listing the items that we will be needing for our side of the parsing.
        {
          "count":[total count INT],
          "next_page":[Next page num INT],
          "page_size":[number of postings on page INT],
          "jobs": [
                   {
                      "title": [job title],
                      "company_name": [company name]
                      "
                      "locations": [
                                    // There are multiple, so will have to string concatination
                                    // of some sort.
                                    #: {
                                         "display": [actual name of city EX: Lake Charles, LA, USA],
                                         "is_remote" [boolean to say if this specific location is offering the position as remote]
                                       }
                                   ]
                      "description": [the description of the job (embedded html)]
                   }
                  ]
        }
         */

        boolean jobsAreEmpty = false;
        APIRequest request = new APIRequest();

        try {

            for (int page = 1; jobsAreEmpty; page++) {
                request.SetURL(base_url + page);
                JSONObject response = request.getJson();

                JSONArray jobs = response.getJSONArray("jobs");

                if (jobs.length() > 0) {
                    for (int i = 0; jobs.length() != 0; i++) {
                        JSONObject job_info = jobs.getJSONObject(i);
                        JSONArray locations = job_info.getJSONArray("locations");
                        String title = job_info.getString("title");
                        String listingUrl = job_info.getString("apply_url");

                        String raw_qualifications_html = job_info.getString("qualifications");
                        Element jsoup_qualifications_html = new Element(raw_qualifications_html);
                        String qualifications = GetQualifications(jsoup_qualifications_html);

                        String JobDescription = new Element(job_info.getString("description")).text();

                        Vector<String> location_names = new Vector<>();

                        JSONObject location;
                        for (int location_num = 0; location_num < locations.length(); location_num++){
                            location = locations.getJSONObject(location_num);
                            String location_name = location.getString("display");
                            if (job_info.getBoolean("has_remote")) {
                                location_name += (location.getBoolean("is_remote") ? " (remote)" : "");
                            }
                            location_names.add(location_name);

                        }
                        String FullLocationString = String.join("\n", location_names);

                        JobListing newJobListing = new JobListing();

                        newJobListing.SetJobTitle(title);
                        newJobListing.SetCompanyName(job_info.getString("company_name"));
                        newJobListing.SetJobLocation(FullLocationString);
                        newJobListing.SetJobType("Job Type Unavailable");
                        newJobListing.SetJobQualifications(qualifications);
                        newJobListing.SetJobListingUrl(listingUrl);
                        newJobListing.SetJobDescription(JobDescription);

                        //parser.AddListing(newJobListing);
                        parser.PrintListingInformation(newJobListing);
                    }
                } else {
                    jobsAreEmpty = true;
                }
            }
        } catch (JSONException e){e.printStackTrace();}
    }
}
