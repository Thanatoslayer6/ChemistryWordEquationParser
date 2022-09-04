package ChemistryWordEquationParser;

import java.util.Arrays;
import java.util.Scanner;

public class Parser {
	// Initialize metals and non-metals...
	public static Ions mm = new Ions();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter an equation: ");
		String UnparsedEquation = sc.nextLine();
		sc.close();
		// For instance ( Calcium Chloride + Oxygen = Calcium Chlorate ) 
		// Validate input and split the formula (reactant and product)
		String firstSplit[] = Parser.validateAndSplit(UnparsedEquation);
		// It becomes ( Calcium Chloride + Oxygen, Calcium Chlorate )
		
		System.out.println(Arrays.toString(firstSplit));
		
		// Reactant
		String Reactant[] = firstSplit[0].split("\s\\+\s");
		// Product
		String Product[] = firstSplit[1].split("\s\\+\s");
		
		System.out.println(Arrays.toString(Reactant));
		System.out.println(Arrays.toString(Product));
		
		// First scan the array, replace the solvable stuff with their index as 
		// integer strings...
		String ReactantFirstSolved[] = Parser.firstSolve(Reactant);
		String ProductFirstSolved[] = Parser.firstSolve(Product);
		
		System.out.println(Arrays.toString(ReactantFirstSolved));
		System.out.println(Arrays.toString(ProductFirstSolved));

		// Second check if there are ions needed to be fixed, check for spaces
		

		/*
		int info[] = Parser.getIonIndex(UnparsedEquation);
		System.out.println("The indexes are:" + Arrays.toString(info));
		// Output the elements
		for (int i = 0; i < info.length; i++) {
			System.out.println("The name of the element is: " + mm.ions[info[i]][0]);
			System.out.println("The symbol of the element is: " + mm.ions[info[i]][1]);
			System.out.println("The charge of the element is: " + mm.ions[info[i]][2]);
		}
		*/
	}

	public static String[] firstSolve(String side[]) {
		int indexTemp;
		for (int i = 0; i < side.length; i++) {
			indexTemp = mm.FindMetalIndex(side[i]);
			if (indexTemp != -1) {
				side[i] = mm.ions[indexTemp][1];
			}
		}
		return side;
	}
	
	// Whitespaces should be eliminated by now...
	public static String[] secondSolve(String side[]) {
		String TwoIons[];
		int leftIonIndex, rightIonIndex, leftIonCharge, rightIonCharge;
		for (int i = 0; i < side.length; i++) {
			if (side[i].contains(" ")) {
				// First split where index 0 is the left ion and 1 is the right
				TwoIons = side[i].split(" ");
				// Get the charges of the ions
				leftIonIndex = mm.FindMetalIndex(TwoIons[0]);
				rightIonIndex = mm.FindMetalIndex(TwoIons[1]);
				leftIonCharge = Integer.parseInt(mm.ions[leftIonIndex][2]);
				rightIonCharge = Integer.parseInt(mm.ions[rightIonIndex][2]);
				// 
				//+1, -3
				if (leftIonCharge )
			}
		}
	}
	/**
	First split the equation into two where the index of 0 is the reactant and 1
	is the product
	**/
	public static String[] validateAndSplit(String unparsedEq) {
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
	public static int[] getIonIndex(String unparsedEq) {
		String temp[] = unparsedEq.split("\s\\+\s"); // Split the elements
		int results[] = new int[temp.length]; // Create array for results
		for (int i = 0; i < temp.length; i++) {
			 results[i] = mm.FindMetalIndex(temp[i]); // returns -1 if ion not found
		}
		return results;
	}
/*
	public static int getIonIndex2(String ion) {
		return mm.FindMetalIndex(ion);
	}
	*/

}
