package assignment4;

import java.util.Scanner;

public class BinarySearch 
{	
	public static int n;
	public static Scanner reader = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Please enter a number between 1 and 1,000,000: ");
			n = reader.nextInt();
		while ((n < 1) || (n > 1000000)) {
			n = reader.nextInt();
		}
		guess(1, 1000000);
	}
	
	public static void guess(int low, int high) {
		if (low == high) {
			System.out.println("Your number is "+low+". Thanks for playing!");
			System.exit(0);
		}
		String input = new String();
		int midpoint = (low + high) / 2;
		if (high - low == 1) {
			midpoint = high;
		}
		System.out.println("Is this your number: "+midpoint+"? [y/n]");
		input = reader.nextLine();
		while (!(input.toLowerCase().equals("y")) && !(input.toLowerCase().equals("n"))) {
			input = reader.nextLine();
		}
		if (input.equals("y")) {
			System.out.println("Thanks for playing!");
		} else {
			System.out.println("Is your number smaller than "+midpoint+"? [y/n]");
			input = reader.nextLine();
			while (!(input.toLowerCase().equals("y")) && !(input.toLowerCase().equals("n"))) {
				input = reader.nextLine();
			}
			if (input.equals("y")) {
				guess(low, midpoint-1);
			} else {
				guess(midpoint, high);
			}
		}
	}
}
