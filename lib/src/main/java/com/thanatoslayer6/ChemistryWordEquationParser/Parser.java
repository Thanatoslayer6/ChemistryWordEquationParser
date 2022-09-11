package com.thanatoslayer6.ChemistryWordEquationParser;

public class Parser {
	// Initialize the list of metals and non-metals...
	private static Ions mm = new Ions();

	/**
	 * Returns a string that converts a word equation to its formula 
	 * @param wordEquation
	 * @return String - converted word equation
	 */
	public static String getFormula(String wordEquation) {
		String results = new String();
		String firstSplit[] = Parser.validateAndSplit(wordEquation);
		// Reactant
		String Reactant[] = firstSplit[0].split("\s\\+\s");
		// Product
		String Product[] = firstSplit[1].split("\s\\+\s");	
		// First Solve
		String ReactantFirstSolved[] = Parser.firstSolve(Reactant);
		String ProductFirstSolved[] = Parser.firstSolve(Product);
		// Second Solve
		String ReactantFinal[] = Parser.secondSolve(ReactantFirstSolved);
		String ProductFinal[] = Parser.secondSolve(ProductFirstSolved);	
		//System.out.println(Arrays.toString(ReactantFinal) + Arrays.toString(ProductFinal));
		// Reactant
		for (int i = 0; i < ReactantFinal.length; i++) {
			results += ReactantFinal[i];
			if (i < ReactantFinal.length - 1) {
				results += " + ";
			}
		}
		// Add equal sign
		results += " = ";
		// Product
		for (int i = 0; i < ProductFinal.length; i++) {
			results += ProductFinal[i];
			if (i < ProductFinal.length - 1) {
				results += " + ";
			}
		}
		System.out.println(results);
		// TODO: Give output as one string... Sodium + Chloride = Sodium Chloride .. something...
		return results;
	}
	/**
	 * First solve, tries to retrieve and replace simple ions from their symbol e.x. (Sodium - Na)
	 * @param side
	 * @return String[] returns a string array with the right formula
	 */
	private static String[] firstSolve(String side[]) {
		int indexTemp;
		for (int i = 0; i < side.length; i++) {
			indexTemp = mm.FindMetalIndex(side[i]);
			if (indexTemp != -1) {
				side[i] = mm.ions[indexTemp][1];
			}
		}
		return side;
	}
	
	/**
	 * Second solve, tries to turn ions like "Antimonic Borate" to "Sb3(BO3)5"
	 * @param side
	 * @return String[] - returns a string array with the right formula
	 */
	// Whitespaces should be eliminated by now...
	private static String[] secondSolve(String side[]) {
		Boolean leftIonEndsWithNum, rightIonEndsWithNum;
		String TwoIons[], leftIon, rightIon;
		int leftIonIndex, rightIonIndex, leftIonCharge, rightIonCharge, calculatedGcd;
		for (int i = 0; i < side.length; i++) {
			if (side[i].contains(" ")) {
				// First split where index 0 is the left ion and 1 is the right
				TwoIons = side[i].split(" ");
				// Get the charges of the ions, make sure anion is positive (see abs value *)
				leftIonIndex = mm.FindMetalIndex(TwoIons[0]);
				rightIonIndex = mm.FindMetalIndex(TwoIons[1]);
				leftIonCharge = Integer.parseInt(mm.ions[leftIonIndex][2]); // Cation charge
				rightIonCharge = Integer.parseInt(mm.ions[rightIonIndex][2]); // Anion charge
				leftIon = mm.ions[leftIonIndex][1]; // Cation
				rightIon = 	mm.ions[rightIonIndex][1]; // Anion

				/* If right ion/anion is positive, then we can say that its the user's fault
				 * TODO: Implement error check when right ion is positive or when user enters
				 * something wrong...
				 */
				/*
				 *  For now, take +1, and -3 as an example for cation and anion then we can simply
				 *  just apply the 'criss-cross' method (just replace the charges). However, if it is +2, and -4 then we must
				 *  find the gcd first then simplify or divide the values of the cation and anion by it
				 */

				// First calculate the gcd
				calculatedGcd = gcd(leftIonCharge, Math.abs(rightIonCharge));
				// Take the absolute value of the anion
				rightIonCharge = Math.abs(rightIonCharge);
				// Divide the gcd by the left and right charges
				leftIonCharge /= calculatedGcd;
				rightIonCharge /= calculatedGcd;
				// Check if ions end with a number
				leftIonEndsWithNum = Character.isDigit(leftIon.charAt(leftIon.length() - 1));
				rightIonEndsWithNum = Character.isDigit(rightIon.charAt(rightIon.length() - 1));

				// Just apply the 'criss-cross' method, simply switch the charges
				if (rightIonCharge == 1 && leftIonCharge == 1) {
					side[i] = leftIon + rightIon;
				} else if (rightIonCharge == 1 && leftIonCharge != 1) {
					if (rightIonEndsWithNum) {
						side[i] = leftIon + "(" + rightIon + ")" + leftIonCharge;
					} else {
						side[i] = leftIon + rightIon + leftIonCharge;
					}
				} else if (leftIonCharge == 1 && rightIonCharge != 1) {	
					if (leftIonEndsWithNum) {
						side[i] = "(" + leftIon + ")" + rightIonCharge + rightIon;
					} else {
						side[i] = leftIon + rightIonCharge + rightIon;
					}
				} else { 
					if (leftIonEndsWithNum && rightIonEndsWithNum) {
						// If for example the two ions have charges > 1 like Sb2Cr2O75
						side[i] = "(" + leftIon + ")" + rightIonCharge + "(" + rightIon + ")" + leftIonCharge;
					} else if (leftIonEndsWithNum && !rightIonEndsWithNum) {
						side[i] = "(" + leftIon + ")" + rightIonCharge +  rightIon + leftIonCharge;
					} else if (!leftIonEndsWithNum && rightIonEndsWithNum) {
						side[i] = leftIon + rightIonCharge + "(" +  rightIon + ")" + leftIonCharge;
					} else {
						side[i] = leftIon + rightIonCharge + rightIon + leftIonCharge;
					}
				}	
			}
		}
		return side;
	}
	/**
	 * Returns the greatest common divisor, useful for simplifying fractions...
	 * @param n1 first number
	 * @param n2 second number
	 * @return gcd
	 */
	private static int gcd(int n1, int n2) {
		int gcd = 1;
		for (int i = 1; i <= n1 && i <= n2; ++i) {
			if (n1 % i == 0 && n2 % i == 0) {
				gcd = i;
			}
		}
		return gcd;
	}
	/**
	First split the equation into two where the index of 0 is the reactant and 1
	is the product
	**/
	private static String[] validateAndSplit(String unparsedEq) {
		String temp[] = unparsedEq.split("\s=\s");
		if (temp.length != 2) {
			System.out.println("Please fix ur equation...");
			System.exit(10);
		}
		return temp;
	}

	/**
	Returns an array of integers, if an element in an array is -1 then it means its 
	not found
	**/
	/*
	private static int[] getIonIndex(String unparsedEq) {
		String temp[] = unparsedEq.split("\s\\+\s"); // Split the elements
		int results[] = new int[temp.length]; // Create array for results
		for (int i = 0; i < temp.length; i++) {
			 results[i] = mm.FindMetalIndex(temp[i]); // returns -1 if ion not found
		}
		return results;
	}
	*/

}
