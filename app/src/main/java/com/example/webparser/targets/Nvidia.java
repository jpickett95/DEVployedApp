package com.example.webparser.targets;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

/*
<ul...role="list">
    <li>
    <div class="css-qiqmbt">
        <div class="css-b3pn3b"><div class="css-b3pn3b">
            <h3>
                <a aria-current="false" data-automation-id="jobTitle" class="css-19uc56f" href="/en-US/NVIDIAExternalCareerSite/job/US-CA-Santa-Clara/Silicon-Program-Milestone-Alignment-Program-Manager_JR1961899">
                    Silicon Program Milestone Alignment Program Manager
                </a></h3></div></div></div><div class="css-248241">

                <div class="css-1y87fhn"><div data-automation-id="locations" class="css-k008qs">
                    <div aria-hidden="true" class="css-kij4qr"><span class="css-wwkk48">
                        <svg xmlns="http://www.w3.com.example/2000/svg" width="24" height="24" class="wd-icon-location wd-icon" focusable="false" role="presentation" viewBox="0 0 24 24">
                            <g fill-rule="evenodd" class="wd-icon-container">
                                <path d="M11.646 21.655S4 14.418 4 10a8 8 0 1 1 16 0c0 4.418-7.635 11.645-7.635 11.645a.52.52 0 0 1-.72.01zM12 13a3 3 0 1 0 0-6 3 3 0 0 0 0 6z" class="wd-icon-background"></path><path fill-rule="nonzero" d="M11.646 21.655S4 14.418 4 10a8 8 0 1 1 16 0c0 4.418-7.635 11.645-7.635 11.645a.52.52 0 0 1-.72.01zm.468-2.592a45.53 45.53 0 0 0 2.514-2.842c.875-1.082 1.62-2.124 2.191-3.09C17.6 11.814 18 10.72 18 10a6 6 0 1 0-12 0c0 .72.404 1.818 1.186 3.14.572.964 1.316 2.005 2.192 3.086A45.646 45.646 0 0 0 12 19.181l.114-.118zM12 12a2 2 0 1 0 0-4 2 2 0 0 0 0 4zm0 2a4 4 0 1 1 0-8 4 4 0 0 1 0 8z" class="wd-icon-fill"></path></g></svg></span></div><dl><dt class="css-y8qsrx">locations</dt><dd class="css-129m7dg">3 Locations</dd></dl></div></div></div><div class="css-zoser8"><div class="css-1y87fhn"><div data-automation-id="postedOn" class="css-k008qs"><div aria-hidden="true" class="css-kij4qr"><span class="css-wwkk48"><svg xmlns="http://www.w3.com.example/2000/svg" width="24" height="24" class="wd-icon-clock wd-icon" focusable="false" role="presentation" viewBox="0 0 24 24"><g fill-rule="evenodd" class="wd-icon-container"><circle cx="12" cy="12" r="9" class="wd-icon-background"></circle><path d="M12 22C6.477 22 2 17.523 2 12S6.477 2 12 2s10 4.477 10 10-4.477 10-10 10zm0-2a8 8 0 1 0 0-16 8 8 0 0 0 0 16z" class="wd-icon-fill"></path><path d="M12 12h4.497c.278 0 .503.214.503.505v.99a.509.509 0 0 1-.503.505h-5.992a.509.509 0 0 1-.505-.503V7.503c0-.278.214-.503.505-.503h.99c.279 0 .505.233.505.503V12z" class="wd-icon-accent"></path></g></svg></span></div><dl><dt class="css-y8qsrx">posted on</dt><dd class="css-129m7dg">Posted Today</dd></dl></div></div></div>mation
                <ul data-automation-id="subtitle" class="css-14a0imc">
    <li class="css-h2nt8k">JR1961899</li></ul></li>
</ul>
*/
 /*
         When doing a element.select("...") you can use css-selection strings (tagName.className)
         When doing a element.getElementsByClass("...") you can use ("className")
         element.get
         JobTitle = element.getElementsByAttributeValue("data-automation-id", "jobTitle").text();
        */

public class Nvidia extends ParserTarget
{
    public  Nvidia (){
        Name = "Nvidia";
        base_url = "https://nvidia.wd5.myworkdayjobs.com/NVIDIAExternalCareerSite?locationHierarchy1=2fcb99c455831013ea52fb338f2932d8&locationHierarchy1=2fcb99c455831013ea529c3b93ba3236";
        ListingContainerSelector = "#search-results";
        LogoSelector = "https://nvidia.wd5.myworkdayjobs.com/NVIDIAExternalCareerSite/assets/logo";
    }
    public String GetJobTitle(Element element){
        String JobTitle;
        JobTitle = element.getElementsByAttributeValue("data-automation-id", "jobTitle").text();
        return JobTitle;
    }

    public String GetListingURL(Element element){
        String JobListingURL;
        JobListingURL = element.getElementsByTag("a").attr("href");
        return JobListingURL;
    }
    public Elements GetJobListings(Element jobListingContainer){
        return new Elements();
    }

    public String GetJobType(Element element){return "";}
    public Element GetListingContainer(Document document){
        Element CompanyListing;
        CompanyListing = document.getElementsByAttributeValue("role", "list").first();
        return CompanyListing;
    }
    public String GetDepartment(Element element){return "";}

    // <td data-th="Location"></td>            //Location
    public String GetLocation(Element element){
        String location;
        location = element.getElementsByAttributeValue("data-automation-id", "locations").text();
        return location;
    }

    public Element GetListingContainer(Document document){
        Element CompanyListing;
        CompanyListing = document.getElementsByAttributeValue("role", "list").first();
        return CompanyListing;
    }
    public HashMap GetAdditionalInformation(Element element){
        HashMap<String, String> additionalInformation = new HashMap();

        String JobCode = element.getElementsByAttributeValue("data-automation-id", "subtitle").text();
        if (!JobCode.isEmpty()){
            additionalInformation.put("Job Code", JobCode);
        }

        String PostedOn = element.getElementsByAttributeValue("data-automation-id","postedOn").text();



        return  additionalInformation;
    }

    public  Nvidia (){
        Name = "Nvidia";
        base_url = "https://nvidia.wd5.myworkdayjobs.com/NVIDIAExternalCareerSite?locationHierarchy1=2fcb99c455831013ea52fb338f2932d8&locationHierarchy1=2fcb99c455831013ea529c3b93ba3236";
        ListingContainerSelector = "#search-results";
        LogoSelector = "https://nvidia.wd5.myworkdayjobs.com/NVIDIAExternalCareerSite/assets/logo";
    }
    public String MakeSearchUrl() {return base_url + search_term;}


}
