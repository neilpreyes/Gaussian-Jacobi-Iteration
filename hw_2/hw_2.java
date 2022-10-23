package hw_2;
/**
 * Neil Patrick Reyes
 * CS 3010 Section 03
 * Professor Raheja
 * 10/19/2022
 */

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;

public class hw_2{
	
	public static boolean verify(double[][] equations, int numOfEquations) {
		boolean valid = true;
		
		for(int i = 0; i < numOfEquations; i++) {
			double sum = 0;
			for(int j = 0; j < numOfEquations; j++) {
				if(i == j) {
					
				}else{
					sum += equations[i][j];
				}
			}
			if(equations[i][i] < sum) {
				System.out.println("Row " + i + " Sum = " + sum + "Num = " + equations[i][i]);
				valid = false;
			}
		}
		
		return valid;
	}
	
	public static double[] jacobi(double[][] equations, double[] stopping, int numOfEquations){
		double[] results = new double[numOfEquations];
		
		return results;
	}
	
	public static double[] gaussian(double[][] equations, double[] stopping, int numOfEquations){
		double[] results = new double[numOfEquations];
		
		return results;
	}
	
	public static void main(String[] args) throws FileNotFoundException{
		
		//initialize scanner
		Scanner scan = new Scanner(System.in);
		
		//set number of equations
		//3 is minimum for x,y,z variables and 10 is max limit
		int numOfEquations;
		do {
			System.out.println("How many equations do you have? (Please input an integer between 3 and 10.)");
			numOfEquations = scan.nextInt();
		}while(numOfEquations > 10 || numOfEquations < 2);
		

		
		//create matrix of equations
		double[][] equations = new double[numOfEquations][numOfEquations+1];
		
		char text;
		boolean complete;
		int method;
		
		do {
			complete = false;
			System.out.println("Is the input a txt file? (Answer only in Y/N)");
			text = scan.next().charAt(0);
			if(text == 'Y' || text == 'y') {
				String file;
				System.out.println("Please input the path of the file:");
				file = scan.next();
				Scanner textWrite = new Scanner(new File(file));
				for(int i = 0; i < numOfEquations; i++) {
					for(int j = 0; j <= numOfEquations; j++) {
						equations[i][j] = textWrite.nextInt();
					}
				}
			}else if(text == 'N' || text == 'n'){
				//set values for matrix
				//double x, y, z, result;
				for(int i = 0; i < numOfEquations; i++) {
					System.out.println("Please input the cooefficients one at a time " +
				 "then follow them with the resulting number");
					for(int j = 0; j <= numOfEquations; j++) {
						if(j != numOfEquations) {
							System.out.println("Coefficient x" + j + " equals: ");
							equations[i][j] = scan.nextInt();
						}
						else {
							System.out.println("Result equals: ");
							equations[i][j] = scan.nextInt();
						}
					}
				}
			}else {
				System.out.println("Invalid input please try again.\n");
				complete = true;
			}
		}while(complete);	

		if(verify(equations, numOfEquations)) {
			
			
			double[] stopping = new double[numOfEquations];
			double[] results = new double[numOfEquations];
			System.out.println("Please input the stopping condition for your equations:");
			for(int i = 0; i < numOfEquations; i++) {
				stopping[i] = scan.nextDouble();
			}
			System.out.println("Please select a calculation method: \n(1)Jacobi\n(2)Gaussian");
			method = scan.nextInt();
			
			if(method == 1) {
				
				results = jacobi(equations, stopping, numOfEquations);
				
			}else if(method == 2) {
				
				results = gaussian(equations, stopping, numOfEquations);
				
			}else {
				System.out.println("Method Invalid.\nPlease retry program.");
			}
			
			
		}else {
			System.out.println("Diagonally Dominant verification failed.\nPlease retry program with proper inputs.");
		}
		
		scan.close();
		
	}
}