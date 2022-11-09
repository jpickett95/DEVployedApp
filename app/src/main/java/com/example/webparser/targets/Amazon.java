package com.example.webparser.targets;

import com.example.webparser.WebParser;
import com.example.webparser.data.JobListing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.webparser.data.APIRequest;
import java.io.IOException;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.Vector;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Amazon extends ParserTarget {

    public Amazon(WebParser webParser) {
        super(webParser);
        Name = "Amazon";
        base_url = "https://www.amazon.jobs/en/search.json?offset=0&result_limit=100&sort=relevant&category[]=operations-it-support-engineering&job_type[]=Full-Time&country[]=USA&distanceType=Mi&radius=24km&latitude=&longitude=&loc_group_id=&loc_query=&base_query=&city=&country=&region=&county=&query_options=&";
    }

    // <td data-th="Job Title"></td>           //Job Title
    public String GetJobTitle(Element element) {
        return null;
    }

    public String GetJobDescription(String description){
        String[] jobDescriptionItems = description.split("<ul>");
        String descriptionBeforeList = jobDescriptionItems[0].replace("<br/>", "\n");
        String list = "<ul>" + jobDescriptionItems[1];
        String responsibilityList = "\n";
        Element htmlResponsibilityList = Jsoup.parse(list);
        for (Element responsibility:htmlResponsibilityList.getElementsByTag("li")){
            responsibilityList += "- " + responsibility.text() + "\n";
        }
        return descriptionBeforeList + responsibilityList;
    }

    //TODO: Implement this method (when I have time)
    public String GetQualifications(Element element) { return null; }

    public String GetQualifications (String responsiblities){
        return responsiblities.replace("<br/>", "\n");
    }

    // <td data-th="Location"></td>            //Location
    public String GetLocation(Element element) {
        return null;
    }

    // <td data-th="Actions"></td>             //Area of Interest (the actual one) (job type IT, Development, etc)
    public String GetJobType(Element element) {
        return null;
    }

    public Element GetListingContainer(Document document) {
        return null;
    }

    // <td data-th="Area of Interest"></td>    //Job Type
    public String GetDepartment(Element element) {
        return null;
    }

    public String GetListingURL(Element element) {
        return null;
    }

    public Element GetCompanyListing(Document document) {
        return null;
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
    public Elements GetJobListings(Element listingContainer) {
        return null;
    }

    public HashMap GetAdditionalInformation(Element element) {
        return  null;
    }

    @Override
    String MakeSearchUrl() {
        return null;
    }

    @Override
    public void Main() throws IOException {

        APIRequest req = new APIRequest();
        req.SetURL(base_url);
        JSONObject json = req.getJson();
        JSONArray jobs = null;

        try {
            int totalJobs = json.getInt("hits");
            JSONObject newJson;
            for (int i = 0; i < totalJobs;) {

                req.SetURL("https://www.amazon.jobs/en/search.json?offset=" + i + "&result_limit=100&sort=relevant&category%5B%5D=operations-it-support-engineering&job_type%5B%5D=Full-Time&country%5B%5D=USA&distanceType=Mi&radius=24km&latitude=&longitude=&loc_group_id=&loc_query=&base_query=&city=&country=&region=&county=&query_options=&");

                newJson = req.getJson();
                jobs = newJson.getJSONArray("jobs");

                JobListing jobListing;
                for (int job_num = 0; job_num < jobs.length(); job_num++) {
                    JSONObject job = jobs.getJSONObject(job_num);
                    jobListing = new JobListing();
                    jobListing.SetJobTitle(job.getString("title"));
                    jobListing.SetJobDescription(GetJobDescription(job.getString("description")));
                    jobListing.SetJobQualifications(GetQualifications(job.getString("basic_qualifications")));
                    jobListing.SetJobListingUrl(job.getString("url_next_step"));
                    jobListing.SetJobLocation(job.getString("location"));
                    jobListing.SetCompanyName(job.getString("company_name"));
                    parser.PrintListingInformation(jobListing);
                    //parser.AddListing(jobListing);
                }
                i = ((i + 100) < totalJobs) ? (i + 100) : (totalJobs - i);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}
