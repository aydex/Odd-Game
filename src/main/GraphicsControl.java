package main;

import java.io.File;

import party.EnemyParty;
import party.Member.MemberType;
import party.Party;
import world.Grid.Direction;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;


public class GraphicsControl extends Application {

	private final int width = 800;
	private final int height = 600;
	private final int sceneX = 20;
	private final int sceneY = 15;
	
	private int sceneType = 0;
	
	private KeyCode left = KeyCode.getKeyCode("A");
	private KeyCode up = KeyCode.W;
	private KeyCode right = KeyCode.D;
	private KeyCode down = KeyCode.S;
	private KeyCode inventoryKey = KeyCode.I;
	private KeyCode menuKey = KeyCode.ESCAPE;
	
	public Party playerParty;
	
	private Image[] imageList = new Image[52];
	private char[] gridTypeList = new char[52];
	
	public GridPane grid = new GridPane();
	private String fileLocation = "image/";
	File file;
	
	/**
	 * change one gridImage at given coordinates to the type given from char
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 * @param scene the scene to change
	 * @param gridType determine what type of image to change to
	 */
	public void gridChange(int x, int y, char gridType){
		if(x < 0 || x>=sceneX || y < 0 || y >= sceneY){
			throw new IllegalArgumentException("gridIndex was wrong, it was x: "+x+" and y: "+y);
		}
		int charIndex = 0;
		while(this.gridTypeList[charIndex]!=gridType){
			charIndex++;
		}
		ImageView tempView = new ImageView(imageList[charIndex]);
		
		
      	GridPane.setConstraints(tempView, x, y);
      	grid.getChildren().set(y*sceneX+x, tempView);
	}
	
	/**
	 * sets the grid-graphics
	 * @param scene where the graphics are added
	 */
	public void grid(){
		char c = 'a';
		for(int i = 0 ; i < 26 ; i++){
			file = new File(fileLocation+"gridImage-"+c+".png");
			imageList[i] = new Image(file.toURI().toString());
			gridTypeList[i] = c;
			c++;
		}
		c = 'A';
		char c2 = 'a';
		for(int i = 26 ; i < 52 ; i++){
			file = new File(fileLocation+"gridImage2-"+c2+".png");
			imageList[i] = new Image(file.toURI().toString());
			gridTypeList[i] = c;
			c++;
			c2++;
		}
		
		char[] gridList = new char[sceneX*sceneY];
		c = 'a';
		for(int i = 0; i < sceneX*sceneY ; i++){
			gridList[i] = c;
			if(c=='z'){
				c = 'A';
			}
			else if(c=='Z'){
				c = 'a';
			}
			else{
				c++;				
			}
		}
		
		
		grid.setPadding(new Insets(0, 0, 0, 0));
		grid.setVgap(0);
		grid.setHgap(0);
		
		ImageView tempView = new ImageView(imageList[0]);
		for(int i = 0; i < sceneX;i++){
			for(int j = 0 ; j < sceneY ; j++){
				for(int k = 0 ; k < 52 ; k++){
					if(gridTypeList[k] == gridList[i*sceneY+j]){
						tempView = new ImageView(imageList[k]);
					}
				}
				GridPane.setConstraints(tempView, i, j);
				grid.getChildren().add(tempView);
			} 
		}
	}
	
	EventHandler<KeyEvent> keyEventHandler = new EventHandler<KeyEvent>() {
	            public void handle(final KeyEvent keyEvent) {
	            	Direction direction = null;
	            	switch(keyEvent.getCode()){
	            	case A:
						direction = Direction.LEFT;
	            	case D:
	            		direction = Direction.RIGHT;
	            	case W:
	            		direction = Direction.UP;
	            	case S:
	            		direction = Direction.DOWN;
	            	case I:
	            		sceneType = 2;
	            	case ESCAPE:
	            		sceneType = 3;
	            	default:
						break;
	            	}
	            	if(direction!=null){
	            		//grid.mo
	            	}
	 				
	            }
	        };
	
    @Override
    public void start(Stage stage) {
    	Scene scene = new Scene(new Region(), width, height);
        stage.setScene(scene);
        stage.setTitle("The Final Outfall");
      	stage.show();
      	scene.setRoot(grid);
      	scene.setOnKeyPressed(keyEventHandler);
      	grid();
      	gridChange(2, 4, 'F');
    } 
    
    public static void main(String[] args) {
        launch(args);
        System.out.println("1");
    }

	
}
