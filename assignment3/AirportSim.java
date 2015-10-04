package assignment3;

import java.util.LinkedList;
import java.util.Random;
import java.text.DecimalFormat;

public class AirportSim 
{
	static final double LANDING_TIME = 4;
	static final double TAKEOFF_TIME = 2;
	static final double ARRIV_PROB = .05;
	static final double DEP_PROB = .05;
	static final double FUEL = 2;
	static int simTime = 6000;
	static int landCount = 0, takeoffCount = 0, crashCount = 0;
	static double totalLandingWait = 0, totalTakeoffWait = 0, averageLandingWait, averageTakeoffWait;

	static Runway runway = new Runway();
	static LinkedList<Plane> landing = new LinkedList<Plane>();
	static LinkedList<Plane> takeoff = new LinkedList<Plane>();
	
	
	public static void main(String[] args) {

		Random r = new Random();
		for (int i = simTime; i > 0; i--) {
			// Add planes to the landing queue as they arrive
			if (r.nextDouble() < ARRIV_PROB) {
				landing.add(new Plane(FUEL));
			}
			// Add planes to the takeoff queue as they arrive
			if (r.nextDouble() < DEP_PROB) {
				takeoff.add(new Plane(FUEL));
			}
			// Always land plane if runway is free
			if (runway.isFree() && (landing.peekFirst() != null)) {
				totalLandingWait+=landing.peekFirst().wait;
				runway.setArrivPlane(landing.removeFirst());
			}
			// Only takeoff if no planes are waiting to land and the runway is free
			if (landing.isEmpty() && runway.isFree() && (takeoff.peekFirst() != null)) {
				totalTakeoffWait+=takeoff.peekFirst().wait;
				runway.setDepPlane(takeoff.removeFirst());
			}
			// Always decrement fuel of pending arrivals, increase wait time for planes, and check for crashes
			for (int j = 0; j < landing.size(); j++) {
				Plane p = landing.get(j);
				p.decFuel();
				p.wait++;
				if (p.isCrashed()) {
					landing.remove(j);
					crashCount++;
				}
			}
			for (int k = 0; k < takeoff.size(); k++) {
				Plane p = takeoff.get(k);
				p.wait++;
			}
			// Always decrement runway (takeoff or landing) time
			runway.decTime();
		}
		
		// Calculate averages
		DecimalFormat df = new DecimalFormat("##.##");
		averageLandingWait = totalLandingWait / landCount;
		averageTakeoffWait = totalTakeoffWait / takeoffCount;
		// PRINT RESULTS
		System.out.println("Number of planes landed: "+landCount+"\nNumber of planes taken off: "+takeoffCount+"\nNumber of planes crashed: "+crashCount
			+"\nAverage wait for landing: "+df.format(averageLandingWait)+" mins"+"\nAverage wait for takeoff: "+df.format(averageTakeoffWait)+" mins");
	}


	
}
