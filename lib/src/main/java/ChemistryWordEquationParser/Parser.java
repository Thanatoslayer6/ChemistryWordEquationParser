package ChemistryWordEquationParser;

import java.util.Arrays;
import java.util.Scanner;

public class Parser {
	// Initialize metals and non-metals...
	static Ions mm = new Ions();

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
		// WORKS
		
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

}
