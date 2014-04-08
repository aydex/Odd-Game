package world;

import party.EnemyParty;
import party.Party;
import utils.FileManager;
import world.Grid.Direction;

public class World {
	private String fileLocation = "mapfolder/";
	private String[][] nameMatrix = {{"","","","a1","","",""},{"","","b1","b2","b3","b4",""},{"c1","c2","c3","c4","","c5","c6"},{"","","","d1","d2","d3",""}};
	private int height = 4;
	private int width = 7;
	private int x;
	private int y;
	private Grid grid;
	
	public char[] toChar(){
		return grid.toChar();
	}
	
	public int getPosX() {
		return grid.getPosX();
	}
	
	public int getPosY() {
		return grid.getPosY();
	}
	
	
	/**
	 * constructor for world, it overwrites the gridsfiles from previous games, and set the matrix for the overall world
	 */
	public World(){
		System.out.println("world()");
		//height = nameMatrix.length;
		//width = nameMatrix[0].length;
		String coordinateString = FileManager.getStringFromFile(fileLocation+"playercoordinates");
		//System.out.println("coordinateString: "+coordinateString.split(",")[0]+" , "+coordinateString.split(",")[1]+" , "+coordinateString.split(",")[2]);
		x = Integer.parseInt(coordinateString.split(",")[0]);
		y = Integer.parseInt(coordinateString.split(",")[1]);
		for(int i = 0; i < height ; i++){
			for(int j = 0; j < width ; j++){
				if(nameMatrix[i][j]!=""){
					//System.out.println("nameMatrix["+i+"]["+j+"]: "+nameMatrix[i][j]);
					//System.out.println("world - for(for(if");
					FileManager.saveStringToFile(fileLocation+"saveMaps/"+nameMatrix[i][j], FileManager.getStringFromFile(fileLocation+"Original-"+nameMatrix[i][j]));
				}
			}
		}
		//System.out.println("world - pre grid = new Grid()");
		grid = new Grid(fileLocation+"saveMaps/"+nameMatrix[y][x],Direction.DOWN);//Direction.valueOf(coordinateString.split(",")[2]));
	}
	
	/**
	 * triggers the move method in grid
	 * @param direction the direction the party moves
	 * @return -1: no moves, 0: move, 1: fight, 2: outside map, the rest is coordinates(before and after move)
	 */
	public int[] move(Direction direction){
		int[] movementList = new int[5];
		movementList[1] = grid.getPosX();
		movementList[2] = grid.getPosY();
		boolean[] moveBool = new boolean[2];
		moveBool = grid.move(direction);
		if(moveBool[0]==true){
			movementList[0] = 1;
			movementList[3] = grid.getPosX();
			movementList[4] = grid.getPosY();
		}
		else if(moveBool[1]==true){
			movementList[0] = 2;
		}
		else if(moveBool[2]==false){
			movementList[0] = 0;
			movementList[3] = grid.getPosX();
			movementList[4] = grid.getPosY();
		}
		else{
			movementList[0] = -1;
		}
		return movementList;
	}
	
	public void clearProximity(){
		grid.clearProximity();
	}
	
	public EnemyParty getEnemyParty(int level){
		return grid.getEnemyParty(level);
	}
	
	/**
	 * changes to new grid based on the grid argument
	 * @param gridPane the old grid
	 */
	public void changeGrid(){
		Direction direction = grid.getDirection();
		switch(direction){
		case DOWN:
			y--;
			break;
		case LEFT:
			x++;
			break;
		case RIGHT:
			x--;
			break;
		case UP:
			y++;
			break;
		default:
			throw new IllegalArgumentException("You done fucked up, something went wrong with the grid in changeGrid");
		}
		System.out.println("x: "+x+" y: "+y);
		grid.saveGrid(nameMatrix[y][x]);
		grid = new Grid(nameMatrix[y][x],direction);
	}
	
	public int[] getEnemyPlacement(){
		return grid.getEnemyPlacement();
	}
}
