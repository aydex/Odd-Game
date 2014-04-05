package main;

import java.io.File;
import java.util.ArrayList;

import combat.CombatGraphics;
import party.EnemyParty;
import party.FriendlyParty;
import party.Member;
import party.Member.MemberType;
import party.Party;
import world.Grid.Direction;
import world.World;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;


public class GraphicsControl extends Application {

	private final int width = 800;
	private final int height = 600;
	private final int sceneX = 20;
	private final int sceneY = 15;
	
	private Scene scene;
	private boolean keyActive = true;
	private KeyCode left = KeyCode.getKeyCode("A");
	private KeyCode up = KeyCode.W;
	private KeyCode right = KeyCode.D;
	private KeyCode down = KeyCode.S;
	private KeyCode inventoryKey = KeyCode.I;
	private KeyCode menuKey = KeyCode.ESCAPE;
	private ImageView playerImageView;
	
	private World world;
	
	
	
	
	
	
	
	public FriendlyParty playerParty;
	
	private char[] gridList = new char[sceneX*sceneY];
	private Image[] imageList = new Image[52];
	private char[] gridTypeList = new char[52];
	
	public GridPane gridPane = new GridPane();
	private String fileLocation = "image/";
	File file;
	
	public void init(){
		Member player0 = new Member(MemberType.HERO,1);
		player0.setName("Odd");
		Member player1 = new Member(MemberType.HERO,1);
		player1.setName("Martin");
		Member player2 = new Member(MemberType.HERO,1);
		player2.setName("Adrian");
		Member player3 = new Member(MemberType.HERO,1);
		player3.setName("Jan");
		ArrayList<Member> players = new ArrayList<Member>();
		players.add(player0);
		players.add(player1);
		players.add(player2);
		players.add(player3);
		playerParty = new FriendlyParty(players);
		
		world = new World();
		
		System.out.println("1");
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
		
		file = new File(fileLocation+"gridImage-player.png");
		playerImageView = new ImageView( new Image(file.toURI().toString()));
		
		//	Temporary testingmethod
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
		
		
	}
	
	public void playGame(){
		grid();
	 
	}
	
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
		ImageView tempView;
		if(gridType!='@'){
			while(this.gridTypeList[charIndex]!=gridType){
				charIndex++;
			}
			tempView = new ImageView(imageList[charIndex]);
		}
		else{
			tempView = playerImageView;
		}
		
      	GridPane.setConstraints(tempView, x, y);
      	gridPane.getChildren().set(y*sceneX+x, tempView);
	}
	
	/**
	 * sets the grid-graphics
	 * @param scene where the graphics are added
	 */
	public void grid(){
		
		
		scene.setRoot(gridPane);
		
		gridPane.setPadding(new Insets(0, 0, 0, 0));
		gridPane.setVgap(0);
		gridPane.setHgap(0);
      	gridList = world.toChar();
		System.out.println(world.toChar());
		createGrid();
		scene.setOnKeyPressed(keyEventHandler);
		//gridChange(world.getPosX(),world.getPosY(),'@');
	}

	private void createGrid() {
		System.out.println("createGrid");
		ImageView tempView = new ImageView(imageList[0]);
		for(int i = 0; i < sceneY;i++){
			for(int j = 0 ; j < sceneX ; j++){
				for(int k = 0 ; k < 52 ; k++){
					if(gridTypeList[k] == gridList[i*sceneX+j]){
						tempView = new ImageView(imageList[k]);
					}
				}
				//System.out.println("createGrid1");
				GridPane.setConstraints(tempView, j, i);
				//System.out.println("createGrid2");
				gridPane.getChildren().add(tempView);
				//System.out.println("createGrid3");
			} 
		}
	}
	
	EventHandler<KeyEvent> keyEventHandler = new EventHandler<KeyEvent>() {
		public void handle(final KeyEvent keyEvent) {
			Direction direction = null;
			switch(keyEvent.getCode()){
			case A:
				direction = Direction.LEFT;
				break;
			case D:
				direction = Direction.RIGHT;
				break;
			case W:
				direction = Direction.UP;
				break;
			case S:
				direction = Direction.DOWN;
				break;
			case I:
				System.out.println("hmm");
				break;
			case ESCAPE:
				break;
			default:
				break;
			}
			//int[] tempList = new int[5];
			//tempList = world.move(direction);
			//gridChange(tempList[1], tempList[2], gridList[tempList[2]*sceneX+tempList[1]]);
			//gridChange(0, 1,'@');
			if(direction!=null){
				int[] tempList = new int[5];
				tempList = world.move(direction);
				System.out.println("templist[0]: "+tempList[0]);
				if(tempList[0]==2){
					gridList = world.toChar();
					grid();
				}
				else if(tempList[0]==1){
					CombatGraphics tempComb = new CombatGraphics();
					if(tempComb.performCombat(playerParty,world.getEnemyParty(playerParty.getLevel()))){
						gridChange(tempList[1], tempList[2], gridList[tempList[2]*sceneX+tempList[1]]);
						int[] tempBackgroundList = world.getEnemyPlacement();
						world.clearProximity();
						gridList = world.toChar();
						gridChange(tempList[3], tempList[4],'@');
						gridChange(tempBackgroundList[0], tempBackgroundList[1],gridList[tempBackgroundList[1]*sceneX+tempBackgroundList[0]]);
					}
					else{
						//gameEnded();
					}
				}
				else if(tempList[0]==-1){
					
				}
				else{
					gridChange(tempList[1], tempList[2], gridList[tempList[2]*sceneX+tempList[1]]);
					gridChange(tempList[3], tempList[4],'@');
				}
				System.out.println(direction);
			}
			keyActive = false;
		}
	};

	EventHandler<KeyEvent> keyReleaseHandler = new EventHandler<KeyEvent>() {
		public void handle(final KeyEvent keyEvent) {
			keyActive = true;
			//System.out.println("KeyReleased");
		}
	};
	
	
	
    @Override
    public void start(Stage stage) {
    	System.out.println("preInit");
    	scene = new Scene(new Region(), width, height);
    	this.init();
    	System.out.println("start");
        stage.setScene(scene);
        stage.setTitle("The Final Outfall");
      	stage.show();
      	scene.setRoot(new BorderPane());
      	playGame();
      	scene.setOnKeyReleased(keyReleaseHandler);
      	
    } 
    
    public static void main(String[] args) {
        launch(args);
        System.out.println("1");
    }

	
}
