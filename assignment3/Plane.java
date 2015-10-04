package assignment3;

public class Plane 
{
	public double fuel;
	public double wait;
	
	public Plane(double f) {
		fuel = f;
		wait = 0;
	}
	
	public void decFuel() {
		fuel--;
	}
	
	public boolean isCrashed() {
		if (fuel <= 0) {
			return true;
		} else {
			return false;
		}
	}
}
