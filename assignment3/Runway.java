package assignment3;

public class Runway 
{
	public Plane plane;
	public double time = 0;
	public boolean arrivDep; // Arriving = true, Departing = false
	
	public boolean isFree() {
		if (time <= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setDepPlane(Plane p) {
		plane = p;
		time = AirportSim.TAKEOFF_TIME;
		arrivDep = false;
	}
	
	public void setArrivPlane(Plane p) {
		plane = p;
		time = AirportSim.LANDING_TIME;
		arrivDep = true;
	}
	
	public void decTime() {
		time--;
		if (time == 0) {	
			if (arrivDep) {	// If landing time reaches 0, increase landCount
				AirportSim.landCount++;
			} else if (!arrivDep) { // If takeoff time reaches 0, increase takeoffCount
				AirportSim.takeoffCount++;
			}
		}
	}
}
