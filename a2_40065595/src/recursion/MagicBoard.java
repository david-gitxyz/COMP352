package recursion;

//-------------------------------------------------------
//Assignment 2 
//Written by: David Xie 40065595
//-------------------------------------------------------

/*
* David Xie 40065595
* COMP 352
* Assignment #2
* Due Date: October 25 2020
*/

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class implements a board game using recursion.
 * @author David Xie
 * @version 1.0
 */
public class MagicBoard {
	
	/**
	 * This method checks if a board is solvable recursively
	 * @param board the board to solve
	 * @param d the size of the board
	 * @param previous the array to mark previously visited positions
	 * @param row the starting row
	 * @param column the starting column
	 * @param check to check if the start position is invalid
	 * @return if the board is solvable or not
	 */
	public static boolean isValid(int[][] board, int d, boolean[][] previous, int row, int column, int check) {
		if(check == 0 && board[row][column] == 0) {
			System.out.println("This start position is invalid!");
			return false;
		}
		
		if(check > 0 && board[row][column] == 0) {
			System.out.println("There is a solution!");
			return true;
		}
		
		if(previous[row][column] == true)
			return false;
		
		previous[row][column] = true;
		boolean done = false;
		int temp = 0;
		
		//going east
		if(column + board[row][column] < d) {
			temp = column + board[row][column];
			done = isValid(board, d, previous, row, temp, ++check);
			//found end
			if(done == true)
				return true;
		}
		
		//going west
		if(column - board[row][column] >= 0) {
			temp = column - board[row][column];
			done = isValid(board, d, previous, row, temp, ++check);
			//found end
			if(done == true)
				return true;
		}
		
		//going south
		if(row + board[row][column] < d) {
			temp = row + board[row][column];
			done = isValid(board, d, previous, temp, column, ++check);
			//found end
			if(done == true)
				return true;
		}
		
		//going north
		if(row - board[row][column] >= 0) {
			temp = row - board[row][column];
			done = isValid(board, d, previous, temp, column, ++check);
			//found end
			if(done == true)
				return true;
		}
		
		return false;
	}

	public static void main(String[] args) {
		
		Scanner keyboard = new Scanner(System.in);
		int d; //size of the board
		int check = 0; //check for 0
		
		int[][] testBoard = new int[5][5];
		boolean[][] previous1 = new boolean[5][5];
		//example board provided with solution
		testBoard[0][0] = 4;
		testBoard[0][1] = 2;
		testBoard[0][2] = 1;
		testBoard[0][3] = 3;
		testBoard[0][4] = 1;
		testBoard[1][0] = 2;
		testBoard[1][1] = 3;
		testBoard[1][2] = 2;
		testBoard[1][3] = 1;
		testBoard[1][4] = 4;
		testBoard[2][0] = 3;
		testBoard[2][1] = 2;
		testBoard[2][2] = 3;
		testBoard[2][3] = 1;
		testBoard[2][4] = 4;
		testBoard[3][0] = 1;
		testBoard[3][1] = 3;
		testBoard[3][2] = 4;
		testBoard[3][3] = 2;
		testBoard[3][4] = 3;
		testBoard[4][0] = 3;
		testBoard[4][1] = 3;
		testBoard[4][2] = 1;
		testBoard[4][3] = 2;
		testBoard[4][4] = 0;
		
		//display board
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				System.out.print(String.format("%3d", testBoard[i][j]));
			}
			System.out.println();
		}
		
		System.out.println(isValid(testBoard, 5, previous1, 0, 0, 0));

		//example board with no solution
		testBoard[0][0] = 1;
		testBoard[0][1] = 4;
		testBoard[0][2] = 1;
		testBoard[0][3] = 3;
		testBoard[0][4] = 1;
		testBoard[1][0] = 4;
		testBoard[1][1] = 3;
		testBoard[1][2] = 2;
		testBoard[1][3] = 1;
		testBoard[1][4] = 4;
		testBoard[2][0] = 3;
		testBoard[2][1] = 2;
		testBoard[2][2] = 3;
		testBoard[2][3] = 1;
		testBoard[2][4] = 4;
		testBoard[3][0] = 1;
		testBoard[3][1] = 3;
		testBoard[3][2] = 4;
		testBoard[3][3] = 2;
		testBoard[3][4] = 3;
		testBoard[4][0] = 3;
		testBoard[4][1] = 4;
		testBoard[4][2] = 1;
		testBoard[4][3] = 2;
		testBoard[4][4] = 0;
		
		//display board
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				System.out.print(String.format("%3d", testBoard[i][j]));
			}
			System.out.println();
		}
		
		System.out.println(isValid(testBoard, 5, previous1, 0, 0, 0));
		
		//user inputs size of the board
		System.out.println("Please input the size of the board (between 5 and 20): ");
		d = keyboard.nextInt();
		
		int[][] board = new int[d][d];
		boolean[][] previous = new boolean[d][d];
		
		//fill the board
		for(int i = 0; i < d; i++) {
			for(int j = 0; j < d; j++) {
				//generate a random number for this location in the board
				board[i][j] = ThreadLocalRandom.current().nextInt(0, d); //max is exclusive, so no need to do -1
				
				//increment if the position is a 0
				if(board[i][j] == 0)
					check++;
				//if there's already a 0, replace the number in that position
				if(check > 1)
					board[i][j] = ThreadLocalRandom.current().nextInt(1, d); //max is exclusive, so no need to do -1
			}
		}
		
		
		//display board
		for(int i = 0; i < d; i++) {
			for(int j = 0; j < d; j++) {
				System.out.print(String.format("%3d", board[i][j]));
			}
			System.out.println();
		}
		
		System.out.println(isValid(board, d, previous, 0, 0, 0)); //enter starting position wanted
		

	}

}
