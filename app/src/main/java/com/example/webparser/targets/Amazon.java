package com.example.webparser.targets;
public class Amazon extends ParserTarget {
    public Amazon()
    {
        base_url = "https://www.amazon.jobs";
        ListingContainerSelector = ".search-container";
        JobListingURLSelector = "a";
        JobTitleSelector = "div.job-tile";
        LocationSelector = "p.location-and-id";
        SubCompanySelector = "";
        QualificationsSelector = "div.description";
        LogoSelector = "";
    }
    public String MakeSearchUrl() {return base_url + search_term;};
}
