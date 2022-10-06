package com.example.devployedapp;

// **** NOT IN USE ****
// **** Decided to use the JobPostInformation.java class instead *****

public class jobCard {
    // Member Variables
    private String companyName;
    private String jobTitle;
    private String skillsMatched;
    private int companyLogo;

    // Constructor
    public jobCard(String name, String title, String skills) { // (..., int logo) - extra parameter for logo
        this.companyName = name;
        this.jobTitle = title;
        this.skillsMatched = skills;
        // this.companyLogo = logo;
    }

    // Getters
    public String getCompanyName(){return companyName;}
    public String getJobTitle(){return jobTitle;}
    public String getSkillsMatched(){return skillsMatched;}
    // public int getCompanyLogo(){return companyLogo;}
}
