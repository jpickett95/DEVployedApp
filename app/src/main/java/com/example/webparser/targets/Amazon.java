package com.example.webparser.targets;
public class Amazon extends ParserTarget {
    public Amazon()
    {


    }
    public String MakeSearchUrl() {return base_url + search_term;};
}
