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

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy.OverwriteFileSetting;
import org.matsim.core.scenario.ScenarioUtils;

/**
 * @author Onnene
 *
 */
public class LocalMachineMatsimInstance {

	
	/**
	 * Modified version of class to make the MATSim instance run on its own virtual machine
	 * @param args
	 */
	
	public static void runInstance(String folder, String output, long seed) {
		Config config = ConfigUtils.createConfig();
		ConfigUtils.loadConfig(config, folder + "./config.xml");
		config.global().setRandomSeed(seed);
		config.global().setNumberOfThreads(2);
		config.controler().setLastIteration(LocalMachineRunSimulationBasedTransitOptimisationProblem.MATSIM_ITERATION_NUMBER);       
		config.controler().setOutputDirectory(output);       
		config.controler().setOverwriteFileSetting(OverwriteFileSetting.deleteDirectoryIfExists);
		
		config.plans().setInputFile(folder + "./plans.xml");
		config.parallelEventHandling().setNumberOfThreads(2);
		config.qsim().setNumberOfThreads(2);
		config.controler().setWriteEventsInterval(10); //FIXME
		config.network().setInputFile(folder + "./network.xml");
		config.transit().setVehiclesFile(folder + "./transitVehicles.xml");
		config.transit().setTransitScheduleFile(folder + "./transitSchedule.xml");

		Scenario scenario = ScenarioUtils.loadScenario(config);
		Controler controler = new Controler(scenario);

		controler.run();
	}
}
