package com.example.webparser.targets;

import com.example.webparser.WebParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.HashMap;
import java.util.List;

public class Amazon extends ParserTarget {

    public Amazon (WebParser webParser){
        super(webParser);
        Name = "Amazon";
        ListingContainerSelector = ".section_content table tbody";
    }

    // <td data-th="Job Title"></td>           //Job Title
    public String GetJobTitle(Element element){
        return null;
    }


    //TODO: Implement this method (when I have time)
    public String GetQualifications(Element element){
        return null;
    }

    // <td data-th="Location"></td>            //Location
    public String GetLocation(Element element){
        return null;
    }

    // <td data-th="Actions"></td>             //Area of Interest (the actual one) (job type IT, Development, etc)
    public String GetJobType(Element element){
        return null;
    }

    public Element GetListingContainer(Document document){
        return null;
    }

    // <td data-th="Area of Interest"></td>    //Job Type
    public String GetDepartment(Element element){
        return null;
    }

    public String GetListingURL(Element element){
        return null;
    }

    public Element GetCompanyListing(Document document){
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
    public Elements GetJobListings(Element listingContainer){
        return null;
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


    public static final String POSTS_API_URL = "https://www.amazon.jobs/en/search.json?offset=0&result_limit=10&sort=relevant&category%5B%5D=operations-it-support-engineering&job_type%5B%5D=Full-Time&country%5B%5D=USA&distanceType=Mi&radius=24km&latitude=&longitude=&loc_group_id=&loc_query=&base_query=&city=&country=&region=&county=&query_options=&]";

    public static void main(String[] args) throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();
        Request request = Request.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(POSTS_API_URL))
                .build();
        try{Response<String> response = client.send(request, Response.BodyHandlers.ofString());}catch (IOException e){e.printStackTrace();};

        // parse JSON
        ObjectMapper mapper = new ObjectMapper();
        List<Amazon> posts = mapper.readValue(response.body(), new TypeReference<List<Amazon>>() {});

        // posts.forEach(post -> {
        //     System.out.println(post.toString());
        // });
        posts.forEach(System.out::println);
    }
}
