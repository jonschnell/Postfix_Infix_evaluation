package edu.iastate.cs228.hw4;

/**
 *  
 * @author Jonathon Schnell
 * @version 1.0
 * @since 4-6-2020
 * COM S 228
 * homework4
 *
 */

import java.util.HashMap;

public abstract class Expression {
	protected String postfixExpression;
	protected HashMap<Character, Integer> varTable; // hash map to store variables in the

	protected Expression() {
		// no implementation needed
		// removable when you are done
		//removing this breaks my code
	}

	/**
	 * Initialization with a provided hash map.
	 * 
	 * @param st
	 * @param varTbl
	 */
	protected Expression(String st, HashMap<Character, Integer> varTbl) {
		this.postfixExpression = st;
		this.varTable = varTbl;
	}

	/**
	 * Initialization with a default hash map.
	 * 
	 * @param st
	 */
	protected Expression(String st) {
		this.postfixExpression = st;
	}

	/**
	 * Setter for instance variable varTable.
	 * 
	 * @param varTbl
	 */
	public void setVarTable(HashMap<Character, Integer> varTbl) {
		this.varTable = varTbl;
	}

	/**
	 * Evaluates the infix or postfix expression.
	 * 
	 * @return value of the expression
	 * @throws ExpressionFormatException, UnassignedVariableException
	 */
	public abstract int evaluate() throws ExpressionFormatException, UnassignedVariableException;

	// --------------------------------------------------------
	// Helper methods for InfixExpression and PostfixExpression
	// --------------------------------------------------------
	
	public abstract String toPostfix() throws ExpressionFormatException;
	
	/**
	 * Checks if a string represents an integer. You may call the static method
	 * Integer.parseInt().
	 * 
	 * @param s
	 * @return boolean
	 */
	protected static boolean isInt(String s) {
		//try to parse for integers
		try {
			Integer.parseInt(s);
			//if no int found return false
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	/**
	 * Checks if a char represents an operator, i.e., one of '~', '+', '-', '*',
	 * '/', '%', '^', '(', ')'.
	 * 
	 * @param c
	 * @return boolean
	 */
	protected static boolean isOperator(char c) {
		if (c == '~' || c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^' || c == '(' || c == ')') {
			return true;
		}
		return false;
	}

	/**
	 * Checks if a char is a variable, i.e., a lower case English letter.
	 * 
	 * @param c
	 * @return boolean
	 */
	protected static boolean isVariable(char c) {
		if (Character.isLowerCase(c) && Character.isLetter(c)) {
			return true;
		}
		return false;
	}

	/**
	 * Removes extra blank spaces in a string.
	 * 
	 * @param s
	 * @return String
	 */
	protected static String removeExtraSpaces(String s) {
		//remove spaces
		String noSpaces = s.replaceAll("\\s", "");
		//remove tabs
		noSpaces = noSpaces.replaceAll("\t", "");
		//return the new string
		return noSpaces;
	}

}
