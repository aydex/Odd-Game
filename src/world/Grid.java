package world;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import party.Party;

public class Grid {
	public enum Direction{ LEFT , UP , RIGHT , DOWN };
	private int height = 21;
	private int width = 31;
	private char[][] originalBoard;
	private int[][] collisionBoard;
	private int posX;
	private int posY;
	private Direction boardDirection;
	
	
	/**
	 * checks if the current position involves a fight
	 * @return whether the current position involves a fight
	 */
	private boolean isFight(){
		for(int i = 0; i < 9 ; i ++){
			if(0 > posY-1+i/3 || posY-1+i/3 >= height || 0 > posX-1+i%3 || posX-1+i%3 >= width){
				
			}
			else{
				if(collisionBoard[posY-1+i/3][posX-1+i%3]==1){
					return true;
				}				
			}
		}
		return false;
	}
	
	
	/**
	 * checks if party can move to the new coordinates
	 * @param newX the new x coordinate
	 * @param newY the new y coordinate
	 * @return whether the move will be possible to do
	 */
	private boolean isCollision(int newX, int newY){
		if(collisionBoard[newY][newX]!=0){
			return true;
		}
		else{
			return false;
		}
	}

	
	/**
	 * return the coordinates for the direction given
	 * @param direction the direction the move is going in
	 * @return the coordinates for the move
	 */
	private int[] getCoordinates(Direction direction){
		int[] coordinates = {this.posX, this.posY};
		switch(direction){
		case DOWN:
			if(posY+1>=height){
				coordinates[0] = -1;
			}
			else{
				coordinates[0] ++;
			}
			break;
		case LEFT:
			if(posX-1<0){
				coordinates[0] = -1;
			}
			else{
				coordinates[1] --;
			}
			break;
		case RIGHT:
			if(posX+1>=width){
				coordinates[0] = -1;
			}
			else{
				coordinates[1] ++;
			}
			break;
		case UP:
			if(posY-1<0){
				coordinates[0] = -1;
			}
			else{
				coordinates[0] --;
			}
			break;
		default:
			throw new IllegalArgumentException("The direction was not recognized");		
		}
		return coordinates;
	}
	
	/**
	 * changes the posX and posY of the party from the given direction if possible, if out of board sets direction
	 * @param direction the direction the party is moving in
	 * @return whether the move was done, and whether the player is out of the board
	 */
	public boolean[] move(Direction direction){
		boolean[] returnBool = {false, false};
		int[] coordinates = this.getCoordinates(direction);
		if(coordinates[0] == -1){
			returnBool[0] = false;
			returnBool[1] = true;
			if(posX==0){
				boardDirection = Direction.RIGHT;
			}
			else if(posX==width-1){
				boardDirection = Direction.LEFT;
			}
			else if(posY==0){
				boardDirection = Direction.DOWN;
			}
			else if(posY==height-1){
				boardDirection = Direction.RIGHT;
			}
			else{
				throw new InputMismatchException("Something went wrong when setting the direction, the coordinates was ["+posY+"]["+posX+"]");
			}
		}
		if(this.isCollision(coordinates[0], coordinates[1])){
			
		}
		else{
			this.posY = coordinates[0];
			this.posX = coordinates[1];
			returnBool[0] = true;
		}
		return returnBool;
	}
	
	/**
	 * returns the string-representation from the given filename
	 * @param fileName the filename of the file
	 * @return the string-representation of the given filename
	 */
	private String getStringFromFile(String fileName){
		String fileOutput = ""; 
		Scanner in;
		 try
		 {
			 in = new Scanner(new FileReader(fileName+".txt"));
			 while(in.hasNext()){
				 String tempString = in.nextLine();
				 fileOutput+=tempString;
				 fileOutput+="\n";
			 }
			 if (fileOutput.length() > 0 && fileOutput.charAt(fileOutput.length()-1)=='n' && fileOutput.charAt(fileOutput.length()-2)=='/') {
				 fileOutput = fileOutput.substring(0, fileOutput.length()-2);
				  }
			 in.close();
			 return fileOutput;
		 }
		 catch (FileNotFoundException e)
		 {
			 System.err.println("Error: file " + fileName + " could not be opened. Does it exist?");
			 System.exit(1);
		 }
		 return fileOutput;
	}
	
	/**
	 * creates the board
	 * @param fileName the filename that the board comes from
	 */
	private void createBoard(String fileName){
		String str = getStringFromFile(fileName);
		for(int i = 0 ; i < height; i++){
			for(int j = 0 ; j < width ; j++){
				originalBoard[i][j] = str.charAt(width*i+j);
				fillCollisionBoard(i, j);
			}
		}
	}
	
	/**
	 * fills the collision-board at the given coordinates
	 * @param i the y-coordinate
	 * @param j the x-coordinate
	 */
	private void fillCollisionBoard(int i , int j){
		if(originalBoard[i][j]>='A' && originalBoard[i][j]<='J'){			
			collisionBoard[i][j] = 1;
		}
		else if(originalBoard[i][j]>'J' && originalBoard[i][j]<='T'){			
			collisionBoard[i][j] = 2;
		}
		else if(originalBoard[i][j]>'T' && originalBoard[i][j]<='Z'){			
			collisionBoard[i][j] = 3;
		}
		else{
			collisionBoard[i][j] = 0;
		}
	}
	
	/**
	 * constructor for the class, puts the party in the right starting-position
	 * @param fileName of the file the board is based on
	 * @param directon what direction the party is coming from
	 */
	public Grid(String fileName, Direction direction){
		boardDirection = null;
		originalBoard = new char[height][width];
		collisionBoard = new int[height][width];
		this.createBoard(fileName);
		switch(direction){
		case DOWN:
			posX = width/2;
			posY = height-1;
			break;
		case LEFT:
			posX = 0;
			posY = height/2;
			break;
		case RIGHT:
			posX = width-1;
			posY = height/2;
			break;
		case UP:
			posX = width/2;
			posY = 0;
			break;
		default:
			break;
		
		}
	}
	
}
