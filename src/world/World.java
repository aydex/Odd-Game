package world;

import utilities.FileManager;
import world.Grid.Direction;

public class World {
	private String[][] nameMatrix;
	private int height;
	private int width;
	private int x;
	private int y;
	
	/**
	 * constructor for world, it overwrites the gridsfiles from previous games, and set the matrix for the overall world
	 */
	public World(){
		//Add elements to nameMatrix, have to be made manually
		height = nameMatrix.length;
		width = nameMatrix[0].length;
		
		
		for(int i = 0; i < height ; i++){
			for(int j = 0; j < width ; j++){
				if(nameMatrix[i][j]!=""){
					FileManager.saveStringToFile(nameMatrix[i][j], FileManager.getStringFromFile("Original"+nameMatrix[i][j]));
				}
			}
		}
	}
	
	/**
	 * changes to new grid based on the grid argument
	 * @param grid the old grid
	 */
	public void changeGrid(Grid grid){
		Direction direction = grid.getDirection();
		switch(direction){
		case DOWN:
			y++;
			break;
		case LEFT:
			x++;
			break;
		case RIGHT:
			x--;
			break;
		case UP:
			y--;
			break;
		default:
			throw new IllegalArgumentException("You done fucked up, something went wrong with the grid in changeGrid");
		}
		grid.saveGrid(nameMatrix[y][x]);
		grid = new Grid(nameMatrix[y][x],direction);
	}
}
