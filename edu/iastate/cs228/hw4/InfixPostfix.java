package edu.iastate.cs228.hw4;

import java.io.File;
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
 * This class evaluates input infix and postfix expressions. 
 *
 */

import java.util.HashMap;
import java.util.Scanner;

public class InfixPostfix {

	/**
	 * Repeatedly evaluates input infix and postfix expressions. See the project
	 * description for the input description. It constructs a HashMap object for
	 * each expression and passes it to the created InfixExpression or
	 * PostfixExpression object.
	 * 
	 * @param args
	 * @throws ExpressionFormatException
	 * @throws UnassignedVariableException
	 * @throws FileNotFoundException
	 **/
	public static void main(String[] args)
			throws ExpressionFormatException, UnassignedVariableException, FileNotFoundException {

		Scanner scanner = new Scanner(System.in);

		System.out.print("Evaluation of Infix and Postfix Expressions\nkeys: 1 (standard input) 2 (file input) 3 (exit)\n(Enter I before an infix expression, P before a postfix expression)\n");

		// master for allows for 99 trials
		for (int trial = 1; trial < 100; trial++) {
			// keeps track of number of trials
			System.out.print("Trail " + trial + ": ");
			// scans user selection 1, 2, or 3
			int selection = scanner.nextInt();

			boolean hasVar = false;

			// standard input
			if (selection == 1) {
				System.out.print("Expression: ");
				String input = scanner.next();
				String iExpression = input.substring(1);
				if (input.charAt(0) == 'I') {
					Expression ie = new InfixExpression(input.substring(1));

					System.out.print("Infix Form: " + ie.toString() + "\n");
					System.out.print("Postfix Form: ");
					System.out.print(ie.toPostfix() + "\n");
					for (int i = 0; i < iExpression.length(); i++) {
						if (Character.isLowerCase(iExpression.charAt(i)) && Character.isLetter(iExpression.charAt(i))) {
							hasVar = true;
						}

					}
					if (hasVar == true) {
						HashMap<Character, Integer> hashMap = new HashMap<Character, Integer>();
						Expression ie2 = new InfixExpression(input.substring(1), hashMap);
						System.out.println("where");
						for (int i = 0; i < iExpression.length(); i++) {
							if (Character.isLowerCase(iExpression.charAt(i))
									&& Character.isLetter(iExpression.charAt(i))) {
								System.out.print(iExpression.charAt(i) + " = ");
								int var = scanner.nextInt();
								hashMap.put(iExpression.charAt(i), var);
							}
						}
						System.out.println("Expression Value: " + ie2.evaluate() + "\n");
					} else {
						System.out.println("Expression Value: " + ie.evaluate() + "\n");
					}

				} else if (input.charAt(0) == 'P') {
					Expression pe = new InfixExpression(input.substring(1));
					System.out.println("Postfix Form: " + pe.toString());
					for (int i = 0; i < iExpression.length(); i++) {
						if (Character.isLowerCase(iExpression.charAt(i)) && Character.isLetter(iExpression.charAt(i))) {
							hasVar = true;
						}

					}
					if (hasVar == true) {
						HashMap<Character, Integer> hashMap = new HashMap<Character, Integer>();
						Expression pe2 = new InfixExpression(input.substring(1), hashMap);
						System.out.println("where");
						for (int i = 0; i < iExpression.length(); i++) {
							if (Character.isLowerCase(iExpression.charAt(i))
									&& Character.isLetter(iExpression.charAt(i))) {
								System.out.print(iExpression.charAt(i) + " = ");
								int var = scanner.nextInt();
								hashMap.put(iExpression.charAt(i), var);
							}
						}
						System.out.println("Expression Value: " + pe2.evaluate() + "\n");
					} else {
						System.out.println("Expression Value: " + pe.evaluate() + "\n");

					}
				}

				// file in
			} else if (selection == 2) {
				System.out.println("Input from a file");
				System.out.print("Enter file name: ");
				String fileName = scanner.next();
				File f = new File(fileName);
				Scanner sc = new Scanner(f);
				String scString = null;
				boolean hasVar2 = false;
				String iExpression = null;
				while (sc.hasNext()) {
					// current line
					scString = sc.nextLine();
					hasVar2 = false;

					while (scString.isBlank() || scString.isEmpty()) {
						scString = sc.nextLine();
					}
					// if a new expression is encountered store it in iExpression
					if (scString.charAt(0) == 'P' || scString.charAt(0) == 'I') {
						iExpression = scString.substring(1);
					}

					if (scString.charAt(0) == 'I') {
						Expression ie = new InfixExpression(scString.substring(1));
						System.out.println("Infix Form: " + ie.toString());
						System.out.print("Postfix Form: ");
						System.out.print(ie.toPostfix() + "\n");
						for (int i = 0; i < iExpression.length(); i++) {
							if (Character.isLowerCase(iExpression.charAt(i))
									&& Character.isLetter(iExpression.charAt(i))) {
								hasVar = true;
								System.out.println("where");
							}

						}
						if (hasVar == true) {
							HashMap<Character, Integer> hashMap = new HashMap<Character, Integer>();
							Expression ie2 = new InfixExpression(scString.substring(1), hashMap);
							// System.out.println("where");
							for (int i = 0; i < iExpression.length(); i++) {
								if (Character.isLowerCase(iExpression.charAt(i))
										&& Character.isLetter(iExpression.charAt(i))) {
									System.out.print(iExpression.charAt(i) + " = ");
									String temp = removeExtraSpaces(sc.nextLine());

									System.out.println(temp.substring(2));

									hashMap.put(temp.charAt(0), Integer.parseInt(temp.substring(2)));
								}
							}
							System.out.println("Expression Value: " + ie2.evaluate() + "\n");
						} else {
							System.out.println("Expression Value: " + ie.evaluate() + "\n");
						}
					} else if (scString.charAt(0) == 'P') {
						Expression pe = new PostfixExpression(scString.substring(1));
						// System.out.println("Infix Form: " + pe.toString());
						System.out.print("Postfix Form: ");
						System.out.print(pe.toString() + "\n");
						for (int i = 0; i < iExpression.length(); i++) {
							if (Character.isLowerCase(iExpression.charAt(i))
									&& Character.isLetter(iExpression.charAt(i))) {
								hasVar = true;
								System.out.println("where");
							}

						}
						if (hasVar == true) {
							HashMap<Character, Integer> hashMap = new HashMap<Character, Integer>();
							Expression pe2 = new PostfixExpression(scString.substring(1), hashMap);
							// System.out.println("where");
							for (int i = 0; i < iExpression.length(); i++) {
								if (Character.isLowerCase(iExpression.charAt(i))
										&& Character.isLetter(iExpression.charAt(i))) {
									System.out.print(iExpression.charAt(i) + " = ");
									String temp = removeExtraSpaces(sc.nextLine());

									System.out.println(temp.substring(2));

									hashMap.put(temp.charAt(0), Integer.parseInt(temp.substring(2)));
								}
							}
							System.out.println("Expression Value: " + pe2.evaluate() + "\n");
						} else {
							System.out.println("Expression Value: " + pe.evaluate() + "\n");
						}
					}
				}

				// exit program
			} else if (selection == 3) {
				scanner.close();
				System.exit(0);
			}
			System.out.print("\n");
		}

		// String Pexpression = "3 56+* 2-i%";
		String Pexpression = "8~7*i3+/";
		HashMap<Character, Integer> pMap = new HashMap<Character, Integer>();
		pMap.put('i', 5);
		Expression e = new PostfixExpression(Pexpression, pMap);
		System.out.println(e.toString());

		System.out.println(e.evaluate());

		// String Iexpression = "1^2/(5*3)-10";
		String Iexpression = "(4*5)";
		// String Iexpression = "(5 * 1)^(2+5)";
		Expression ie = new InfixExpression(Iexpression);
		System.out.println(ie.toString());

		System.out.println(ie.toPostfix());
		System.out.println(ie.evaluate());

	}

	// for removing extra spaces from a input file
	private static String removeExtraSpaces(String s) {
		// remove spaces
		String noSpaces = s.replaceAll("\\s", "");
		// remove tabs
		noSpaces = noSpaces.replaceAll("\t", "");
		// return the new string
		return noSpaces;
	}
	// helper methods if needed
}
