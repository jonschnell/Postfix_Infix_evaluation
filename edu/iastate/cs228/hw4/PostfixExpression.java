package edu.iastate.cs228.hw4;

import java.io.FileNotFoundException;

/**
 *  
 * @author Jonathon Schnell
 * @version 1.0
 * @since 4-6-2020
 * COM S 228
 * homework4
 *
 */

/**
 * 
 * This class evaluates a postfix expression using one stack.    
 *
 */

import java.util.HashMap;
import java.util.NoSuchElementException; 

public class PostfixExpression extends Expression 
{
	private int leftOperand;            // left operand for the current evaluation step             
	private int rightOperand;           // right operand (or the only operand in the case of 
	                                    // a unary minus) for the current evaluation step	

	private PureStack<Integer> operandStack;  // stack of operands

	

	/**
	 * Constructor stores the input postfix string and initializes the operand stack.
	 * 
	 * @param st      input postfix string. 
	 * @param varTbl  hash map that stores variables from the postfix string and their values.
	 */
	public PostfixExpression (String st, HashMap<Character, Integer> varTbl)
	{
		this.postfixExpression = removeExtraSpaces(st);
				
		this.varTable = varTbl;
	
		this.operandStack = new ArrayBasedStack<Integer>();
						
		//this.operandStack.push(6);
		
		//varTbl.put((st.charAt(1)), i);
		
	}
	
	
	/**
	 * Constructor supplies a default hash map. 
	 * 
	 * @param s
	 */
	public PostfixExpression (String s)
	{
		this.postfixExpression = removeExtraSpaces(s);
		
		HashMap<Character, Integer> varTbl = null;
		
		this.varTable = varTbl;
		
		this.operandStack = new ArrayBasedStack<Integer>();
		
	}

	
	/**
	 * Outputs the postfix expression according to the format in the project description.
	 */
	@Override 
	public String toString()
	{
		String noSpace = removeExtraSpaces(postfixExpression);
		String toReturn = "";
		
		for (int i = 0; i < noSpace.length()-1; i++) {
			toReturn += noSpace.charAt(i) + " ";
		}

		return toReturn + noSpace.charAt(noSpace.length()-1); 
	}
	

	/**
	 * Resets the postfix expression. 
	 * @param st
	 */
	public void resetPostfix (String st)
	{
		postfixExpression = removeExtraSpaces(st); 
	}


	/**
     * Scan the postfixExpression and carry out the following:  
     * 
     *    1. Whenever an integer is encountered, push it onto operandStack.
     *    2. Whenever a binary (unary) operator is encountered, invoke it on the two (one) elements popped from  
     *       operandStack,  and push the result back onto the stack.  
     *    3. On encountering a character that is not a digit, an operator, or a blank space, stop 
     *       the evaluation. 
     *       
     * @return value of the postfix expression 
     * @throws ExpressionFormatException with one of the messages below: 
     *  
     *           "Invalid character" if encountering a character that is not a digit, an operator
     *           or a whitespace (blank, tab); 
     *           "Too many operands" if operandStack is non-empty at the end of evaluation; 
     *           "Too many operators" if getOperands() throws NoSuchElementException; 
     *           "Divide by zero" if division or modulo is the current operation and rightOperand == 0;
     *           "0^0" if the current operation is "^" and leftOperand == 0 and rightOperand == 0;
     *           self-defined message if the error is not one of the above.
     *           
     *           
     *          
     *            UnassignedVariableException if the operand as a variable does not have a value stored
     *            in the hash map.  In this case, the exception is thrown with the message
     *            
     *           "Variable <name> was not assigned a value", where <name> is the name of the variable.  
     *           
     */
	public int evaluate() throws ExpressionFormatException 
    {	
		char[] tokens = postfixExpression.toCharArray();
		
		for (int i = 0; i < tokens.length; i++)
		{
			//if item is an integer
			if (tokens[i] >= '0' && tokens[i] <= '9') {
				operandStack.push(Character.getNumericValue(tokens[i]));
			}
			//if item is a variable
			else if(isVariable(tokens[i])) {
				if (varTable.containsKey(tokens[i])) {
					operandStack.push(varTable.get(tokens[i]));
				}else {
					throw new ExpressionFormatException("Variable " + tokens[i] + " was not assigned a value");
				}
			}
			//if item is an operator
			else if (isOperator(tokens[i])){
				try {
					getOperands(tokens[i]);
				} catch (NoSuchElementException e) {
					throw new ExpressionFormatException("Too many operators");
				}
				if((tokens[i] == '/' || tokens[i] == '%') && (rightOperand == 0)) {
					throw new ExpressionFormatException("Divide by zero");
				}
				if(tokens[i] == '^' && leftOperand == 0 && rightOperand == 0) {
					throw new ExpressionFormatException("0^0");
				}
				operandStack.push(compute(tokens[i]));
			//if item is a space or tab
			}else if (tokens[i] == ' ' || tokens[i] == '\t') {
				continue;
			//if item is not a valid character
			}else if (tokens[i] != ' ' || !isOperator(tokens[i]) || tokens[i] <= '0' && tokens[i] >= '9') {
				throw new ExpressionFormatException("Invalid character");
			}else if (tokens[i] == '(' || tokens[i] == ')') {
				throw new ExpressionFormatException("() not allowed in postfix expression");
			}
			else break;
		}
		//if there is more than one item left in the stack
		//TODO
		if (operandStack.size() > 1) {
			throw new ExpressionFormatException("Too many operands");
		}
		return operandStack.pop();  
    }
	

	/**
	 * For unary operator, pops the right operand from operandStack, and assign it to rightOperand. The stack must have at least
	 * one entry. Otherwise, throws NoSuchElementException.
	 * For binary operator, pops the right and left operands from operandStack, and assign them to rightOperand and leftOperand, respectively. The stack must have at least
	 * two entries. Otherwise, throws NoSuchElementException.
	 * @param op
	 * 			char operator for checking if it is binary or unary operator.
	 * @throws NoSuchElementException
	 */
	private void getOperands(char op) throws NoSuchElementException 
	{
		//if the operator is unary there must be at least 1 item in the stack
		if(op == '~') {
			if (operandStack.size() < 1) {
				throw new NoSuchElementException();
			}else {
				rightOperand = operandStack.pop();
			}
		//for binary operators there must be 2 items in the stack
		}else {
			if (operandStack.size() < 2) {
				throw new NoSuchElementException();
			}
			rightOperand = operandStack.pop();
			leftOperand = operandStack.pop(); 
		}

	}


	/**
	 * Computes "leftOperand op rightOprand" or "op rightOprand" if a unary operator. 
	 * 
	 * @param op operator that acts on leftOperand and rightOperand. 
	 * @return
	 */
	private int compute(char op)  
	{
		
		if(op == '~') {
			return -rightOperand;
		}
		else if(op == '+') {
			return leftOperand + rightOperand;
		}
		else if(op == '-') {
			return leftOperand - rightOperand;
		}
		else if(op == '+') {
			return leftOperand + rightOperand;
		}
		else if(op == '*') {
			return leftOperand * rightOperand;
		}
		else if(op == '/') {
			return leftOperand / rightOperand;
		}
		else if(op == '%') {
			return leftOperand % rightOperand;
		}
		else if(op == '^') {
			return (int) Math.pow(leftOperand, rightOperand);
		}
		return 0;
	}
	/**
	 * helper method to convert infix to postfix
	 * required to be in abstract i think this was eclipse being weird and not allowing me to call a public method in infix
	 * @return string
	 */
	public String toPostfix() throws ExpressionFormatException {
		return "do not call toPostfix on a postfix expression";
		//not used for postfix
	}
}
