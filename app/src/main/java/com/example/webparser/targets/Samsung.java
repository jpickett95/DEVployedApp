package com.example.webparser.targets;

import com.example.jsoup.nodes.Document;
import com.example.jsoup.nodes.Element;

import java.util.HashMap;

/*
        When doing a element.select("...") you can use css-selection strings (tagName.className)
        When doing a element.getElementsByClass("...") you can use ("className")
        element.get
        JobTitle = element.getElementsByAttributeValue("data-automation-id", "jobTitle").text();
       */
public class Samsung extends ParserTarget{

    public String GetJobTitle(Element element){
        String JobTitle;
        JobTitle = element.getElementsByAttributeValue("data-automation-id", "jobTitle").text();
        return JobTitle;
    };

    // <td data-th="Location"></td>            //Location
    public String GetLocation(Element element){
        String location;
        location = element.getElementsByAttributeValue("data-automation-id", "locations").text();
        return location;
    }

    public String GetListingURL(Element element){
        String JobListingURL;
        JobListingURL = element.getElementsByTag("a").attr("href");
        return JobListingURL;
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

    public  Samsung (){
        Name = "Samsung";
        base_url = "https://sec.wd3.myworkdayjobs.com/Samsung_Careers?Location_Country=bc33aa3152ec42d4995f4791a106ed09";
        LogoSelector = "https://sec.wd3.myworkdayjobs.com/Samsung_Careers/assets/logo";
    }
    public String MakeSearchUrl() {return base_url + search_term;};
}
