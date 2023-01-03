package programming;

//-------------------------------------------------------
//Assignment 1 
//Written by: David Xie 40065595
//-------------------------------------------------------

/*
* David Xie 40065595
* COMP 352
* Assignment #1
* Due Date: September 28 2020
*/

import java.util.Scanner;
import java.util.Stack;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

/**
 * This class gives all possible combinations of a masked string of binary characters, using iteration
 * @author David Xie
 * @version 2.0
 *
 */
public class Version2 {
	
	/**
	 * This method reveals the possible combinations of a masked string of binary numbers
	 * @param str the string of binary numbers
	 * @param outputStream the stream to the output to the txt file
	 */
	public static void RevealStr(String str, PrintWriter outputStream) {
		
		Stack<String> stack = new Stack(); //create empty stack
		stack.push(str); //push the string into stack
		int index; //index to check and replace
		
		while(!stack.empty()) { //iterate until the stack is empty
			String current = stack.pop(); //checking the current string item popped from the stack
			
			//checking for first instance of '*' characters in the current string
			if((index = current.indexOf('*')) != -1) { 
				for(char num = '0'; num <= '1'; num++) { //giving the 2 possible combinations for the '*' char
					current = current.substring(0, index) + num + current.substring(index + 1);
					stack.push(current); //push the 2 combinations into the stack
				}
			}
			else { //if no instance of '*' char, print it
				System.out.println(current);
				outputStream.println(current);
			}
		}
		
	}

	public static void main(String[] args) {
		
		Scanner keyboard = new Scanner(System.in);
		String response = "Y";
		
        PrintWriter outputStream = null;
        try
        {
            outputStream = 
                 new PrintWriter(new FileOutputStream("out_v2.txt"));
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Error opening the file out.txt.");
            System.exit(0);
        }

		while(response.toUpperCase().equals("Y")) {
			//User input of string of binary chars
			System.out.println("Please enter a string of binary characters or mask (0, 1, or *)");
			String yourStr = keyboard.next();
			
			long startTime = System.currentTimeMillis(); //record start time
			RevealStr(yourStr, outputStream);
			long endTime = System.currentTimeMillis(); //record end time
			long elapsed = endTime - startTime; //calculate elapsed time: running time
			System.out.println("Running time: " + elapsed + " ms");
			outputStream.println("Running time: " + elapsed + " ms");
			outputStream.println();
			
			System.out.println("Would you like to enter another string? (Y/N): ");
			response = keyboard.next();
		}

	}

}
