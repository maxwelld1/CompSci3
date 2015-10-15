// Darrell Maxwell n02776371 
// Computer Science III - Assignment 1

package assignment1;

import java.io.*;
import java.util.*;

public class PostfixEvaluator 
{	
	public static void main(String[] args) {
		System.out.println("Postfix Expression Calculator: ");
		FileReader fr = null;
		BufferedReader br = null;
		String line;
		String[] tokens;
		Stack<String> s;
		try {
			fr = new FileReader("./in.dat");
			br = new BufferedReader(fr);
			while (((line = br.readLine()) != null) && !(line.equals(""))) {
				System.out.print("The value of "+line+" is ");
				tokens = line.split(" ");
				s = new Stack<String>();
				System.out.print(evaluateExpression(tokens, s, 0)+"\n");
			}
			System.out.println("Input Processed.");
		} catch (Exception e) {
			System.out.println("Input File Could Not Be Read!");
			System.exit(1);
		}
	}
	
	public static String evaluateExpression(String[] tokens, Stack<String> s, int i) {
		String token;
		double x;
	// Make sure stack has one value after evaluation
		if (i >= tokens.length) {
			if (s.size() > 1) 
				return "*INVALID EXPRESSION*";
			return (s.pop());
		}
		token = tokens[i];
	// Make sure there are two values in the stack to operate on
		if (!tryParse(token) && !token.equals("_") && !token.equals("#")) {
			if (s.size() < 2)
				return "*INVALID EXPRESSION*";
		}
		if (token.equals("_")) {
			x = Double.parseDouble(s.pop());
			s.push(Double.toString(-x));
		} else if (token.equals("#")) {
			x = Double.parseDouble(s.pop());
			s.push(Double.toString(Math.sqrt(x)));
		} else if (token.equals("^")) {
			x = Double.parseDouble(s.pop());
			x = Math.pow(Double.parseDouble(s.pop()), x);
			s.push(Double.toString(x));	
		} else if (token.equals("*")) {
			x = Double.parseDouble(s.pop()) * Double.parseDouble(s.pop());
			s.push(Double.toString(x));
		} else if (token.equals("/")) {
			x = Double.parseDouble(s.pop());
			x = Double.parseDouble(s.pop()) / x;
			s.push(Double.toString(x));
		} else if (token.equals("+")) {
			x = Double.parseDouble(s.pop()) + Double.parseDouble(s.pop());
			s.push(Double.toString(x));
		} else if (token.equals("-")) {
			x = Double.parseDouble(s.pop());
			x = Double.parseDouble(s.pop()) - x;
			s.push(Double.toString(x));
		} else {
			s.push(token);
		}
		return evaluateExpression(tokens, s, i+1);
	}
	
	public static boolean tryParse(String t) {
		try {
			Double.parseDouble(t);
			return true;
		} catch (NumberFormatException n) {
			return false;
		}
	}
}
