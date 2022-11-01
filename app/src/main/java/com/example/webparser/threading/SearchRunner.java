package com.example.webparser.threading;

import com.example.webparser.WebParser;
import com.example.webparser.targets.ParserTarget;

import java.io.IOException;
import java.util.Collection;
import java.util.Vector;

/**
 * <h3>SearchRunner</h3>
 * <hr>
 * <p>
 * This Class is an idea. I intend to use this, along with a Queue DataStructure, to allow for multiple
 * ParserTargets to run simutaniously. This may take place over multiple instances of this class, but
 * that is the overview of what is intended for this.
 * </p>
 * <h4>How It works?</h4>
 * <p>
 * This will take in a reference of the WebParser and a Vector of ParserTargets. This vector will likely be
 * subset of the total amount of ParserTargets present in the WebParser.targets member.
 * </p>
 * <p>
 * This will allow for multiple ParserTargets to run at the same time with each having the ability to add JobListing's to 
 * the WebParser via a callback method WebParser.AddListing.
 * </p>
 * <p>
 * I will be looking into how to implement this with a "kill switch" method to allow a user to cancel the search after
 * it's already started. Will probably be a simple implimentation, but that will be for later when I have more time to work on it.
 * </p>
 */
public class SearchRunner extends Thread {
    Collection<ParserTarget> targets;


    public SearchRunner(Collection<ParserTarget> targets){
        this.targets = targets;
        System.out.println("SearchRunner: Instance of SearchRunner was created");
    }
    public void run(){
        System.out.println("SearchRunner: About to iterate through ParserTargets");
        for (ParserTarget target : targets) {
            System.out.println("SearchRunner: About to try ParserTarget.StartParsing() on " + target.GetName());
            try {
                target.StartParsing();
                System.out.println("SearchRunner: " + target.GetName() + " successfully finished parsing");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("SearchRunner: " + target.GetName() + " failed to finished parsing");
            }

        }
    }
}
