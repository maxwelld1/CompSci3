package assignment5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class ExpressionManipulator 
{
	public static void main(String[] args) {
		System.out.println("Expression Manipulator");
		FileReader fr = null;
		BufferedReader br = null;
		String line;
		String[] tokens, postfixExp;
		ArrayList<String> infixExp;
		BTNode<String> expTree;
		double answer;
		try {
			fr = new FileReader("./in.dat");
			br = new BufferedReader(fr);
			while (((line = br.readLine()) != null) && !(line.equals(""))) {
				System.out.print("\nExpression: \n   ");
				tokens = line.split(" ");
				postfixExp = new String[1];
				for (int i = 0; i < tokens.length; i++) {
					if (tokens[i].equals("$")) {
						postfixExp = Arrays.copyOf(tokens, i);
						break;
					}
				}
				for (int i = 0; i < postfixExp.length; i++) {
					System.out.print(postfixExp[i]+" ");
				}
				System.out.println();
				expTree = createExpressionTree(postfixExp);
				System.out.println("\nTree:");
				expTree.print(1);				
				System.out.print("\nFully Parenthesized: \n   ");
				infixExp = getInfixExpression(expTree);
				for (int i = 0; i < infixExp.size(); i++) {
						System.out.print(infixExp.get(i));
				}
				System.out.println();
				System.out.print("\nEvaluating: \n   ");
				answer = Double.parseDouble(evaluate(infixExp));
				System.out.println("   Answer = "+answer+"\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
			}
			System.out.println("\nInput Processed.");
		} catch (Exception e) {
			System.out.println("\nInput File Could Not Be Read!");
			System.exit(1);
		}
	}
	
	public static BTNode<String> createExpressionTree(String[] exp) {
		Stack<BTNode<String>> s = new Stack<BTNode<String>>();
		String[] binOperators = {"^", "*", "/", "%", "+", "-", "<", "<=", ">", ">=", "==", "!=", "&&", "||"};
		String element;
		for (int i = 0; i < exp.length; i++) {
			element = exp[i];
			BTNode<String> node = new BTNode<String>(element, null, null);
			if (element.equals("!")) {
				node.setRight(s.pop());
				s.push(node);
			} else if (Arrays.asList(binOperators).contains(element)) {
				node.setRight(s.pop());
				node.setLeft(s.pop());
				s.push(node);
			} else {
				s.push(node);
			}
		}
		return s.pop();
	}
	
	public static ArrayList<String> getInfixExpression(BTNode<String> expressionTree) {
		ArrayList<String> infixExpression = new ArrayList<String>();
		if (expressionTree.isLeaf()) {
			if (infixExpression.isEmpty()) {
				infixExpression.add(expressionTree.getData());
				return infixExpression;
			}
			int i = infixExpression.size()/2;
			if (infixExpression.get(i) == "(") {
				i++;
			}
			infixExpression.add(i, expressionTree.getData());
		} else {
				int i = (infixExpression.size()/2);
				infixExpression.add(i, "(");
				infixExpression.add(i+1, expressionTree.getData());
				infixExpression.add(i+2, ")");
		}
		if (expressionTree.getLeft() != null) {
			infixExpression.addAll(1, getInfixExpression(expressionTree.getLeft()));
		}
		if (expressionTree.getRight() != null) {
			infixExpression.addAll(infixExpression.size()-1, getInfixExpression(expressionTree.getRight()));
		}
		return infixExpression;
	}
	
	public static String evaluate(ArrayList<String> expression) {
		Double oprnd1, oprnd2;
		String operator;
		Stack<String> expStack = new Stack<String>();
		for (int i = 0; i < expression.size(); i++) {
			if (expression.get(i).equals("(")) {
				expStack.push(expression.get(i));
			} else if (!expression.get(i).equals(")")) {
				expStack.push(expression.get(i));
			} else {
				// !
				if (expStack.get(expStack.size()-2).equals("!")) {
					oprnd1 = Double.parseDouble(expStack.pop());
					operator = expStack.pop();
					expStack.pop();
					expStack.push(Double.toString(evalParen(oprnd1, 0d, operator)));
				} else {
					oprnd2 = Double.parseDouble(expStack.pop());
					operator = expStack.pop();
					oprnd1 = Double.parseDouble(expStack.pop());
					expStack.pop();
					expStack.push(Double.toString(evalParen(oprnd1, oprnd2, operator)));
				}
			}
		}
		return expStack.pop();
	}
	
	public static double evalParen(Double op1, Double op2, String operator) {
		if (operator.equals("^")) {
			return Math.pow(op1, op2);
		} else if (operator.equals("*")) {
			return op1 * op2;
		} else if (operator.equals("/")) {
			return op1 / op2;
		} else if (operator.equals("%")) {
			return op1 % op2;
		} else if (operator.equals("+")) {
			return op1 + op2;
		} else if (operator.equals("-")) {
			return op1 - op2;
		} else if (operator.equals("<")) {
			if (op1 < op2) {
				return 1;
			} else return 0;
		} else if (operator.equals("<=")) {
			if (op1 <= op2) {
				return 1;
			} else return 0;
		} else if (operator.equals(">")) {
			if (op1 > op2) {
				return 1;
			} else return 0;
		} else if (operator.equals(">=")) {
			if (op1 >= op2) {
				return 1;
			} else return 0;
		} else if (operator.equals("==")) {
			if (op1.intValue() == op2.intValue()) {
				return 1;
			} else 
				return 0;
		} else if (operator.equals("!=")) {
			if (op1.intValue() != op2.intValue()) {
				return 1;
			} else return 0;
		} else if (operator.equals("&&")) {
				return op1.intValue() & op2.intValue();
		} else if (operator.equals("||")) {
				return op1.intValue() | op2.intValue();
		} else if (operator.equals("!")) {
			return Math.abs(~op1.intValue());
		} else {
			return 1;
		}
	}
}
