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
	
	public static double[] jacobi(double[][] equations, double stopping, double[] initialValue,int numOfEquations){
		double[] results = new double[numOfEquations];
		double[] newValue = new double[numOfEquations];
		int iteration = 0;
		double sum;
		
		do {
			System.out.println("Iteration #" + iteration);
			System.out.print("[ ");
			for(int i = 0; i < numOfEquations; i++) {
				sum = 0;
				for(int j = 0; j < numOfEquations; j++) {
					if(i != j) {
						sum += equations[i][j] * initialValue[j];
					}
					newValue[i] = (equations[i][numOfEquations] - sum) / equations[i][i]; // (result - sum) / xn coefficient
				}
				System.out.print(newValue[i] + " ");
			}
			System.out.println("]T");
			
			//compare to stopping error
			double numerator = 0;
			double denominator = 0;
			for(int i = 0; i < numOfEquations; i++) {
				numerator += Math.pow((newValue[i] - initialValue[i]), 2);
				denominator += Math.pow(newValue[i], 2);
			}
			
			if((Math.sqrt(numerator)/Math.sqrt(denominator)) <= stopping) {
				iteration = 50;
			}else {
				for(int i = 0; i < numOfEquations; i++) {//resets for next iteration
					initialValue[i] = newValue[i];
				}
			}
			
			iteration++;
		}while(iteration < 50);
		
		for(int i = 0; i < numOfEquations; i++) {
			results[i] = newValue[i];
		}
		return results;
	}
	
	public static double[] gaussian(double[][] equations, double stopping, double[] initialValue,int numOfEquations){
		double[] results = new double[numOfEquations];
		double[] newValue = new double[numOfEquations];
		int iteration = 0;
		double sum;
		
		for(int i = 0; i < numOfEquations; i++) {//copy initial value onto new value to use upon gaussian iteration
			newValue[i] = initialValue[i];
		}
		
		do {
			System.out.println("Iteration #" + iteration);
			System.out.print("[ ");
			for(int i = 0; i < numOfEquations; i++) {
				sum = 0;
				for(int j = 0; j < numOfEquations; j++) {
					if(i != j) {
						sum += equations[i][j] * newValue[j];
					}
					newValue[i] = (equations[i][numOfEquations] - sum) / equations[i][i]; // (result - sum) / xn coefficient
				}
				System.out.print(newValue[i] + " ");
			}
			System.out.println("]T");
			
			//compare to stopping error
			double numerator = 0;
			double denominator = 0;
			for(int i = 0; i < numOfEquations; i++) {
				numerator += Math.pow((newValue[i] - initialValue[i]), 2);
				denominator += Math.pow(newValue[i], 2);
			}
			
			if((Math.sqrt(numerator)/Math.sqrt(denominator)) <= stopping) {
				iteration = 50;
			}else {
				for(int i = 0; i < numOfEquations; i++) {//resets for next iteration
					initialValue[i] = newValue[i];
				}
			}
			
			iteration++;
		}while(iteration < 50);
		
		for(int i = 0; i < numOfEquations; i++) {
			results[i] = newValue[i];
		}
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
			
			
			double stopping;
			double[] initial = new double[numOfEquations];
			double[] results = new double[numOfEquations];
			System.out.println("Please input the stopping error for your equations:");
			stopping = scan.nextDouble();
			System.out.println("Please input the initial solution for the iteration");
			for(int i = 0; i < numOfEquations; i++) {
				initial[i] = scan.nextDouble();
			}
			System.out.println("Please select a calculation method: \n(1)Jacobi\n(2)Gaussian");
			method = scan.nextInt();
			
			if(method == 1) {
				
				results = jacobi(equations, stopping, initial,numOfEquations);
				System.out.println("\nFinal Solution: ");
				System.out.print("[ ");
				for(int num = 0; num < numOfEquations; num++) {
					System.out.print(Math.round(results[num] * 10000.0) / 10000.0 + " "); 
				}
				System.out.println("]");
				
			}else if(method == 2) {
				
				results = gaussian(equations, stopping, initial,numOfEquations);
				System.out.println("\nFinal Solution: ");
				System.out.print("[ ");
				for(int num = 0; num < numOfEquations; num++) {
					System.out.print(Math.round(results[num] * 10000.0) / 10000.0 + " "); 
				}
				System.out.println("]");
				
			}else {
				System.out.println("Method Invalid.\nPlease retry program.");
			}
			
			
		}else {
			System.out.println("Diagonally Dominant verification failed.\nPlease retry program with proper inputs.");
		}
		
		scan.close();
		
	}
}