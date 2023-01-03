package iteration;

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

import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class implements a board game using iteration
 * @author David Xie
 * @version 2.0
 */
public class MagicBoard {
	
	/**
	 * This method checks if a board is solvable iteratively
	 * @param board the board to solve
	 * @param d the size of the board
	 * @param previous the array to mark previously visited positions
	 * @param row the starting row
	 * @param column the starting column
	 * @return if the board is solvable or not
	 */
	public static boolean isValid(int[][] board, int d, boolean[][] previous, int row, int column) {
		if(board[row][column] == 0) {
			System.out.println("This start position is invalid!");
			return false;
		}
		
		boolean done = false;
		Stack<Integer> rows = new Stack(); //create empty stack for rows
		Stack<Integer> cols = new Stack(); //create empty stack for columns 
		rows.push(row); //load current row
		cols.push(column); //load current column
		int temp = 0; //for while loop
		int currentRow = row; //for while loop
		int currentCol = column; //for while loop
		
		while(!done) {
			if(rows.isEmpty()) { //if one of the stacks is empty
				System.out.println("No solution");
				break;
			}
			//pop the current position
			currentRow = rows.pop();
			currentCol = cols.pop();
			
			//if already visited, skip this loop
			if(previous[currentRow][currentCol] == true)
				continue;
			previous[currentRow][currentCol] = true; //mark this position as true
			
			//if the current position's value is 0, it is true, break out of loop
			if(board[currentRow][currentCol] == 0) {
				done = true;
				break;
			}
			else {				
				//going east
				if(currentCol + board[currentRow][currentCol] < d) {
					temp = currentCol + board[currentRow][currentCol];
					
					//push in new position
					rows.push(currentRow);
					cols.push(temp);
				}
				
				//going west
				if(currentCol - board[currentRow][currentCol] >= 0) {
					temp = currentCol - board[currentRow][currentCol];
					
					//push in new position
					rows.push(currentRow);
					cols.push(temp);
				}
				
				//going south
				if(currentRow + board[currentRow][currentCol] < d) {
					temp = currentRow + board[currentRow][currentCol];
					
					//push in new position
					rows.push(temp);
					cols.push(currentCol);
				}
				
				//going north
				if(currentRow - board[currentRow][currentCol] >= 0) {
					temp = currentRow - board[currentRow][currentCol];
					
					//push in new position
					rows.push(temp);
					cols.push(currentCol);
				}
			}
		}
		
		if(done == true) {
			System.out.println("There is a solution!");
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
		
		System.out.println(isValid(testBoard, 5, previous1, 0, 0));
		System.out.println();
		
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
		
		System.out.println(isValid(testBoard, 5, previous1, 0, 0));
		System.out.println();
		
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

		System.out.println(isValid(board, d, previous, 0, 0));
		
	}

}
