// Darrell Maxwell n02776371 
// Computer Science III - Assignment 2

package assignment2;

import java.util.*;
import java.awt.Point;

public class Queens 
{
	public static int n = 0, solCount = 0;
	private static ArrayList<ArrayList<Character>> board = new ArrayList<ArrayList<Character>>();
	private static Stack<Point> qStack = new Stack<Point>();
	
	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		while (n < 4) {
			System.out.println("n-Queens puzzle solver, please enter a number of Queens (cannot be less than 4): ");
			n = reader.nextInt();
			if (n < 4) {
				System.out.println("Invalid number!\n");
			}
		}
		for (int i = 0; i < n; i++) {
			board.add(new ArrayList<Character>());
			for (int j = 0; j < n; j++) {
				board.get(i).add('-');
			}
		}
		findSolutions();
	}
	
	public static void findSolutions() {
		int count = 25;
		Point temp;
		int col = 0;
		for (int y = n-1; y >= 0; y--) {
			//if (count == 0) {
			//	System.exit(0);
			//}
			if (col >= n) {		// Current Q is in position n-1
				if (y == n-1) {	// Bottom Q is in position n-1
					System.out.println("All Solutions have been found.");
					System.exit(0);
				}
				col = qStack.pop().x+1;
				board.get(y).set(col-1, '-');
				y=y+2;
				continue;
			}
			if (tryPlace(col, y)) {
				col = 0;
			} else {	// Q was not placed in row
				col = qStack.pop().x+1;
				board.get(y).set(col-1, '-');
				y=y+2;
			} 
			if (qStack.size() == n) {	// Print and backtrack when stack is full (solution found)
				printBoard();
				col = qStack.pop().x+1;
				board.get(y).set(col-1, '-');
				y=y+2;
			}
			//count--;
		}
	}
	
	public static boolean tryPlace (int col, int row) {
		System.out.println("ROW - "+row);
		Point p;
		for (int x = col; x < n; x++) {
			p = new Point(x, row);
			if (checkStack(p)) {
				board.get(p.y).set(p.x, 'Q');
				qStack.push(p);
				System.out.println("PUSH - "+qStack.peek().x+" "+qStack.peek().y);
				return true;
			}
		}
		return false;
	}
	
	public static boolean checkStack(Point p) {
		if (qStack.isEmpty()) {
			return true;
		}
		for (int i = 0; i < qStack.size(); i++) {
			if (qStack.get(i).y == p.y ) {
				return false;
			} else if (qStack.get(i).x == p.x ) {
				return false;
			} else if ((qStack.get(i).y-p.y) == (p.x-qStack.get(i).x)) {		
				return false;
			} else if ((qStack.get(i).y-p.y) == (qStack.get(i).x)-p.x) {
				return false;
			}
		}
		return true;
	}
	
	public static void printBoard() {
		System.out.println();
		for (int i = 0; i < board.size(); i++) {
			System.out.println();
			for (int j = 0; j < board.get(i).size(); j++) {
				System.out.print(board.get(i).get(j)+" ");
			}
		}
	}
}
