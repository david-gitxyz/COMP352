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
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

/**
 * This class gives all possible combinations of a masked string of binary characters, using recursion
 * @author David Xie
 * @version 1.0
 *
 */
public class Version1 {
	
	/**
	 * This method reveals all possible combinations of a masked string of binary characters, using recursion
	 * @param str the string of binary characters to reveal
	 * @param index the index from which to verify
	 * @param outputStream the stream to the output to the txt file
	 */
	public static void RevealStr(String str, int index, PrintWriter outputStream) {
		
		if(index == str.length()) { //reached end of the string
			System.out.println(str);
			outputStream.println(str);
		}
		else if(str.charAt(index) != '0' && str.charAt(index) != '1' && str.charAt(index) != '*') {
			System.out.println("This string is invalid, please only input 0s, 1s, and *s");
		}
		else if(str.charAt(index) == '*') {
			//replace * by 0, then continue in string using recursion
			String str0 = str.substring(0, index) + "0" + str.substring(index + 1);
			RevealStr(str0, index + 1, outputStream);
			
			//replace * by 1, then continue in string using recursion
			String str1 = str.substring(0, index) + "1" + str.substring(index + 1);
			RevealStr(str1, index + 1, outputStream);
		}
		
		else
			RevealStr(str, index + 1, outputStream);
	}
	
	public static void main(String[] args) {
				
		Scanner keyboard = new Scanner(System.in);
		String response = "Y";
		
        PrintWriter outputStream = null;
        try
        {
            outputStream = 
                 new PrintWriter(new FileOutputStream("out_v1.txt"));
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
			RevealStr(yourStr, 0, outputStream);
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
