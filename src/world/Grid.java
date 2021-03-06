package world;


import java.util.InputMismatchException;

import equipment.Armor;
import equipment.Armor.ArmorType;
import equipment.Equipment;
import equipment.Weapon;
import main.GraphicsControl;
import party.EnemyParty;
import party.Party;
import party.Member.MemberType;
import utils.FileManager;
import utils.RandomOdd;


public class Grid {
	public enum Direction{ LEFT , UP , RIGHT , DOWN };
	private int height = 15;
	private int width = 20;
	private char[][] originalBoard;
	private int[][] collisionBoard;
	private int posX;
	

	private int posY;
	private Direction boardDirection;
	
	
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}
	/**
	 * changes the background in y, x with surrounding area's background
	 * @param y coordinate
	 * @param x coordinate
	 */
	private void copyProximityBackground(int y, int x){
		for(int i = 0; i < 9 ; i ++){
			if(0 > y-1+i/3 || y-1+i/3 >= height || 0 > x-1+i%3 || x-1+i%3 >= width){
				
			}
			else{
				if(collisionBoard[posY-1+i/3][posX-1+i%3]==0){
					originalBoard[y][x] = originalBoard[posY-1+i/3][posX-1+i%3];
					break;
				}				
			}
		}
	}
	
	/**
	 * clears equipment and enemy parties in the players party proximity
	 */
	public void clearProximity(){
		for(int i = 0; i < 9 ; i ++){
			if(0 > posY-1+i/3 || posY-1+i/3 >= height || 0 > posX-1+i%3 || posX-1+i%3 >= width){
				
			}
			else{
				if(collisionBoard[posY-1+i/3][posX-1+i%3]!=1 && collisionBoard[posY-1+i/3][posX-1+i%3]!=0){
					this.copyProximityBackground(posY-1+i/3, posX-1+i%3);
					collisionBoard[posY-1+i/3][posX-1+i%3] = 0;
				}				
			}
		}
	}
	
	/**
	 * return direction the party is going to spawn in the next board
	 * @return the direction the party is going to spawn in the next board
	 */
	public Direction getDirection(){
		System.out.println("Direction: "+boardDirection);
		return boardDirection;
	}
	
	/**
	 * checks if the current position involves a fight
	 * @return whether the current position involves a fight
	 */
	public boolean isFight(){
		for(int i = 0; i < 9 ; i ++){
			if(0 > posY-1+i/3 || posY-1+i/3 >= height || 0 > posX-1+i%3 || posX-1+i%3 >= width){
				
			}
			else{
				if(collisionBoard[posY-1+i/3][posX-1+i%3]==2){
					System.out.println("isFight - x: "+(posX-1+i%3)+" y: "+(posY-1+i/3));
					return true;
				}				
			}
		}
		return false;
	}
	
	public boolean isEquipment(){
		for(int i = 0; i < 9 ; i ++){
			if(0 > posY-1+i/3 || posY-1+i/3 >= height || 0 > posX-1+i%3 || posX-1+i%3 >= width){
				
			}
			else{
				if(collisionBoard[posY-1+i/3][posX-1+i%3]==3){
					System.out.println("isFight - x: "+(posX-1+i%3)+" y: "+(posY-1+i/3));
					return true;
				}				
			}
		}
		return false;
	}
	
	public int[] getEnemyPlacement(){
		int[] returnInt = new int[2];
		for(int i = 0; i < 9 ; i++){
			if(0 > posY-1+i/3 || posY-1+i/3 >= height || 0 > posX-1+i%3 || posX-1+i%3 >= width){
			}
			else{
				if(collisionBoard[posY-1+i/3][posX-1+i%3]==2){
					returnInt[0] =  posY-1+i/3;
					returnInt[1] = posX-1+i%3;
					
				}				
			}
		}
		return returnInt;
	}
	
	public boolean isWon(){
		if(originalBoard[posY][posX]=='q'){
			return true;
		}
		else{
			return false;
		}
	}
	
	public Equipment getEquipment(int level){
		int[] returnInt = new int[2];
		Equipment returnEq = new Equipment("temp");
		for(int i = 0; i < 9 ; i++){
			if(0 > posY-1+i/3 || posY-1+i/3 >= height || 0 > posX-1+i%3 || posX-1+i%3 >= width){
			}
			else{
				if(collisionBoard[posY-1+i/3][posX-1+i%3]==3){
					returnInt[0] =  posY-1+i/3;
					returnInt[1] = posX-1+i%3;

					switch(originalBoard[posY-1+i/3][posX-1+i%3]){
						case 'U':
							returnEq = new Weapon(level);
							break;
						case 'V':
							returnEq = new Armor(ArmorType.HEADGEAR, level);
							break;
						case 'W':
							returnEq = new Armor(ArmorType.CHEST, level);
							break;
						case 'X':
							returnEq = new Armor(ArmorType.HANDS, level);
							break;
						case 'Y':
							returnEq = new Armor(ArmorType.SHIELD, level);
							break;
						case 'Z':
							returnEq = new Armor(ArmorType.BOOTS, level);
							break;
					}
				}				
			}
		}
		return returnEq;
	}
	
	public EnemyParty getEnemyParty(int level){
		EnemyParty returnParty = null;
		for(int i = 0; i < 9 ; i ++){
			if(0 > posY-1+i/3 || posY-1+i/3 >= height || 0 > posX-1+i%3 || posX-1+i%3 >= width){
			}
			else{
				if(collisionBoard[posY-1+i/3][posX-1+i%3]==2){
					MemberType type;
					int numberOfEnemies = 1;
					switch(originalBoard[posY-1+i/3][posX-1+i%3]){
					case 'K':
						type = MemberType.HUMAN;
						numberOfEnemies = RandomOdd.getRandomInt(1, 2);
						break;
					case 'L':
						type = MemberType.HUMAN;
						numberOfEnemies = RandomOdd.getRandomInt(3, 4);
						break;
					case 'M':
						type = MemberType.ZOMBIE;
						numberOfEnemies = RandomOdd.getRandomInt(1, 2);
						break;
					case 'N':
						type = MemberType.ZOMBIE;
						numberOfEnemies = RandomOdd.getRandomInt(3, 4);
						break;
					case 'O':
						type = MemberType.ROBOT;
						numberOfEnemies = RandomOdd.getRandomInt(1, 2);
						break;
					case 'P':
						type = MemberType.ROBOT;
						numberOfEnemies = RandomOdd.getRandomInt(3, 4);
						break;
					case 'Q':
						type = MemberType.SUPERROBOT;
						numberOfEnemies = RandomOdd.getRandomInt(1, 2);
						break;
					case 'R':
						type = MemberType.SUPERROBOT;
						numberOfEnemies = RandomOdd.getRandomInt(3, 4);
						break;
					case 'S':
						type = MemberType.SUPERROBOT;
						numberOfEnemies = 1;
						level+=5;
						break;
					case 'T':
						type = MemberType.SUPERROBOT;
						numberOfEnemies = 1;
						level+=10;
						break;
					default:
						type = MemberType.HUMAN;
						numberOfEnemies = 1;
						break;
					} // end switch
						
						
					returnParty = new EnemyParty(numberOfEnemies, level, type);
				}	// end if(collisionBoard			
			}	// end else
		}	// end for
		return returnParty;
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
				coordinates[1] ++;
			}
			break;
		case LEFT:
			if(posX-1<0){
				coordinates[0] = -1;
			}
			else{
				coordinates[0] --;
			}
			break;
		case RIGHT:
			if(posX+1>=width){
				coordinates[0] = -1;
			}
			else{
				coordinates[0] ++;
			}
			break;
		case UP:
			if(posY-1<0){
				coordinates[0] = -1;
			}
			else{
				coordinates[1] --;
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
	 * @return whether the move instigated a fight, and whether the player is out of the board, and if the move was done
	 */
	public boolean[] move(Direction direction){
		boolean[] returnBool = {false, false, false};
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
				boardDirection = Direction.UP;
			}
			else{
				throw new InputMismatchException("Something went wrong when setting the direction, the coordinates was ["+posY+"]["+posX+"]");
			}
		}
		else{
			if(!this.isCollision(coordinates[0], coordinates[1])){
				this.posX = coordinates[0];
				this.posY = coordinates[1];
			}
			else{
				returnBool[2] = true;
			}
			if(this.isFight()){
				returnBool[0] = true;
			}
		}
		return returnBool;
	}
	
	/**
	 * creates the board
	 * @param fileName the filename that the board comes from
	 */
	private void createBoard(String fileName){
		System.out.println("CreateBoard - fileName: "+fileName);
		String str = FileManager.getStringFromFile(fileName);
		//System.out.println("Stringsize: "+str.length());
		//System.out.println("str: "+str);
		for(int i = 0 ; i < height; i++){
			for(int j = 0 ; j < width ; j++){
				//System.out.println("width*i+j: "+(width*i+j));
				//System.out.print("createboard - "+str.charAt(width*i+j)+" , ");
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
			collisionBoard[i][j] = 0;
		}
		else if(originalBoard[i][j]>'J' && originalBoard[i][j]<='T'){			
			collisionBoard[i][j] = 2;
		}
		else if(originalBoard[i][j]>'T' && originalBoard[i][j]<='Z'){			
			collisionBoard[i][j] = 3;
		}
		else{
			collisionBoard[i][j] = 1;
		}
		if(originalBoard[i][j]=='q'){
			collisionBoard[i][j] = 0;
		}
	}
	
	/**
	 * constructor for the class, puts the party in the right starting-position
	 * @param fileName of the file the board is based on
	 * @param directon what direction the party is coming from
	 */
	public Grid(String fileName, Direction direction){
		System.out.println("Grid - fileName: "+fileName);
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
			throw new IllegalArgumentException("Something went wrong with direction in Grid()");
		
		}
	}
	
	public char[] toChar(){
		char[] returnChar = new char[height*width];
		for(int i = 0 ; i < height ; i++){
			for(int j = 0 ; j < width ; j++){
				//System.out.println(originalBoard[i][j]);
				returnChar[i*width+j] = originalBoard[i][j];
			}
		}
		return returnChar;
	}
	
	/**
	 * toString method
	 */
	public String toString(){
		String returnString = "";
		for(int i = 0 ; i < height ; i++){
			for(int j = 0 ; j < width ; j++){
				returnString += originalBoard[i][j];
			}
			returnString += "\n";
		}
		return returnString.substring(0, returnString.length()-3);
	}

	/**
	 * save the grid
	 * @param fileName of where it is going to be saved
	 */
	public void saveGrid(String fileName){
		FileManager.saveStringToFile(fileName,this.toString());
	}
}
