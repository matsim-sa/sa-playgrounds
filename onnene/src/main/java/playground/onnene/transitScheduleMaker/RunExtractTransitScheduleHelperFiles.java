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
package playground.onnene.transitScheduleMaker;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import playground.onnene.ga.DirectoryConfig;

/**
 * This class is used to run the transitSchedule helper files extractor class
 * 
 * @author Onnene
 *
 */
public class RunExtractTransitScheduleHelperFiles {

	/**
	 * @param args
	 */	 
    public static void main(String[] args) throws ParserConfigurationException, SAXException {
	
		String gZipTschedule = DirectoryConfig.COMPRESSED_GTFS_GZIP_TRANSIT_SCHEDULE_FILE;
	    String decompressedTscheduleXml = DirectoryConfig.DECOMPRESSED_TRANSIT_SCHEDULE_XML_FILE;
	    String gZipNetwork = DirectoryConfig.COMPRESSED_GTFS_GZIP_TRANSIT_NETWORK_FILE;
	    String decompressedNetworkXml = DirectoryConfig.DECOMPRESSED_TRANSIT_NETWORK_XML_FILE;
	    String gZipVehicle = DirectoryConfig.COMPRESSED_GTFS_GZIP_TRANSIT_VEHICLE_FILE;
	    String decompressedVehicleXml = DirectoryConfig.DECOMPRESSED_TRANSIT_VEHICLE_XML_FILE;
	    String xmlOutputFilePath = DirectoryConfig.SCHEDULE_STOPS_AND_LINES_HELPER_FILES_PATH;

	    FileMakerUtils fmu = new FileMakerUtils();   
	    ExtractTransitScheduleHelperFiles tsd = new ExtractTransitScheduleHelperFiles();
	    
	    try {
	    			    	
	    	fmu.unGunzipFile(gZipTschedule, decompressedTscheduleXml);
	    	fmu.unGunzipFile(gZipNetwork, decompressedNetworkXml);
	    	fmu.unGunzipFile(gZipVehicle, decompressedVehicleXml);
	    	tsd.getMatsimStops(decompressedTscheduleXml, xmlOutputFilePath);
	    	tsd.getMatsimLines(decompressedTscheduleXml, xmlOutputFilePath);	    		
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	      
 	
    }

}
