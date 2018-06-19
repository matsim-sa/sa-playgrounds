/* *********************************************************************** *
 * project: org.matsim.*
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2018 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */
  
/**
 * 
 */
package playground.onnene.ga;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import playground.onnene.transitScheduleMaker.FileMakerUtils;
import playground.onnene.transitScheduleMaker.TransitSchedule;

/**
 * Class to generate the feasible population or solutions of routes 
 * and schedules from which the initial population will be initialised.
 * 
 * 
 * @author Onnene
 *
 */
public class CreateFeasibleSolutions {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int numLines;
		CreateFeasibleSolutions cfs = new CreateFeasibleSolutions();
		
		try {
			
			numLines = FileMakerUtils.count(DirectoryConfig.SCHEDULE_LINES_HELPER_FILE);
			String InitialPopFolder = DirectoryConfig.INITIAL_POPULATION_DIRECTORY;
	    	String feasibleRoutes = DirectoryConfig.FEASIBLE_ROUTES_FILE;
	    	cfs.makeInitialPop(feasibleRoutes, numLines, InitialPopFolder);
	    	
		} catch (IOException e) {
			e.printStackTrace();
		}
    		
	}
	
	
	public List<String> makeInitialPop(String feasibleRoutes, int numLines, String outputFolder) throws IOException {
    	
		int index = 0;
		String[] arr = null;
		String finalR = null;
	    Collection<String> collection;
    	List<String> feasibleRoutesList = new ArrayList<String>();
    	
    	TransitSchedule TS = new TransitSchedule();
    	File routes = new File(feasibleRoutes);
    	
    	Scanner s = new Scanner(new FileInputStream(routes));
		s.useDelimiter("]],");

    	try {
    			    			    		
			while (s.hasNext()) {
				
				index++;
								    						
			    String r = s.next();				    
			    String firstThree = r.substring(0, 3); 				   
			    String lastThree = r.substring(r.length()-3, r.length()); 
			    
			    if (firstThree.equals("[[[")) {
			    	finalR = r.trim().substring(1, r.length()) + "]]";
			    }
			    
			    else if (lastThree.equals("]]]")) {				    	
			    	finalR = r.trim().substring(0, r.length()-2);
			    }
			    
			    else {				    	
			    	finalR = r.trim() + "]]";				    	
			    }
			    
			    arr = finalR.split("],");
			    collection = new ArrayList<String>();
		    	
		    	for (String a: arr){			    		
			    	collection.add(a);

		    	}

		    	
		    	if (collection.size() < numLines){
		    		
		    	} else if (collection.size() == numLines) {
		    				    	
		    		System.out.println("Feasible solution " + index + " has " + collection.size() + " routes");
		    		
		    	} else {
		    		
		    		System.out.println("This solution has only " + collection.size() + " so we will discard it");
		    		continue;
		    		
		    	}
		    	 
		    	
		    	String initialPopFiles = outputFolder + "transitSchedule" + index + ".xml";					    	
		    	feasibleRoutesList.add(TS.createTransitScheduleXML(collection, index, numLines, initialPopFiles));			   
			}
									
			s.close();
			
		} catch (IOException | TransformerException | ParserConfigurationException | SAXException e) {
			
			e.printStackTrace();

    	}
    	
    	return feasibleRoutesList;
    	
}

}
