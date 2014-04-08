package main;

import java.io.File;
import java.util.ArrayList;

import combat.Combat;
import combat.CombatGraphics;
import equipment.Armor;
import equipment.Armor.ArmorType;
import equipment.Equipment;
import equipment.Weapon;
import party.EnemyParty;
import party.FriendlyParty;
import party.Member;
import party.Member.MemberType;
import party.Party;
import world.Grid.Direction;
import world.World;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class GraphicsControl extends Application {

	private final int width = 800;
	private final int height = 600;
	private final int sceneX = 20;
	private final int sceneY = 15;
	
	private int[] tempList = new int[5];
	
	private Scene scene;
	private boolean keyActive = true;
	private KeyCode left = KeyCode.A;
	private KeyCode up = KeyCode.W;
	private KeyCode right = KeyCode.D;
	private KeyCode down = KeyCode.S;
	private KeyCode inventoryKey = KeyCode.I;
	private KeyCode menuKey = KeyCode.ESCAPE;
	private ImageView playerImageView;
	
	private World world;
	
	private Combat combat;
	private BorderPane combatRoot;
	private Button enemy0Button = new Button();
	private Button enemy1Button = new Button();
	private Button enemy2Button = new Button();
	private Button enemy3Button = new Button();
	private Button attackButton = new Button();
	private Button surrenderButton = new Button();
	private Button heavyButton = new Button();
	private Button standardButton = new Button();
	private Button simpleButton = new Button();
	private Button player0inv = new Button();
	private Button player1inv = new Button();
	private Button player2inv = new Button();
	private Button player3inv = new Button();
	private Button swapItem = new Button();
	private Button exit = new Button();
	
	private int currentMember = 0;
	private int currentEnemy = 0;
	
	private Member currentOdd;
	
	private Equipment currentItem;
	private Equipment inventoryItem;
	
	public FriendlyParty playerParty;
	private EnemyParty enemyParty;
	
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
		playerParty.addItem(new Weapon(2));
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
		//System.out.println(world.toChar());
		createGrid();
		scene.setOnKeyPressed(keyEventHandler);
		gridChange(world.getPosX(), world.getPosY(),'@');
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
				drawInventory(playerParty.getMember(0));

				inventoryControl();
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
				tempList = world.move(direction);
				//System.out.println("templist[0]: "+tempList[0]);
				if(tempList[0]==2){
					world.changeGrid();
					gridList = world.toChar();
					grid();
				}
				else if(tempList[0]==1){
					//CombatGraphics tempComb = new CombatGraphics();
					scene.setOnKeyPressed(null);
					performCombat(playerParty,world.getEnemyParty(playerParty.getLevel()));
					
					
				}
				else if(tempList[0]==-1){
					
				}
				else{
					gridChange(tempList[1], tempList[2], gridList[tempList[2]*sceneX+tempList[1]]);
					gridChange(tempList[3], tempList[4],'@');
				}
				//System.out.println(direction);
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
	
	public void drawCombat(Member currentPlayer){
		BorderPane combatRoot = new BorderPane();
		combatRoot.setPrefHeight(600);
		combatRoot.setPrefWidth(800);
		
		//Bottom pane
		Pane bottom = new Pane();
		bottom.setPrefHeight(200);
		bottom.setPrefWidth(200);
		
		Text turnOrder = new Text("Turn: " + currentPlayer.getName());
		turnOrder.setLayoutX(20);
		turnOrder.setLayoutY(100);
		
		//surrenderButton = new Button();
		surrenderButton.setLayoutX(14);
		surrenderButton.setLayoutY(137);
		surrenderButton.setMnemonicParsing(false);
		surrenderButton.setPrefHeight(50);
		surrenderButton.setPrefWidth(125);
		surrenderButton.setText("Surrender");
		
		//attackButton = new Button();
		attackButton.setLayoutX(661);
		attackButton.setLayoutY(137);
		attackButton.setMnemonicParsing(false);
		attackButton.setPrefHeight(50);
		attackButton.setPrefWidth(125);
		attackButton.setText("Attack");
		
		//heavyButton = new Button();
		heavyButton.setLayoutX(180);
		heavyButton.setLayoutY(56);
		heavyButton.setMnemonicParsing(false);
		heavyButton.setPrefWidth(125);
		heavyButton.setText("Heavy");
		
		//standardButton = new Button();
		standardButton.setLayoutX(338);
		standardButton.setLayoutY(56);
		standardButton.setMnemonicParsing(false);
		standardButton.setPrefWidth(125);
		standardButton.setText("Standard");
		
		//simpleButton = new Button();
		simpleButton.setLayoutX(492);
		simpleButton.setLayoutY(56);
		simpleButton.setMnemonicParsing(false);
		simpleButton.setPrefWidth(125);
		simpleButton.setText("Simple");
		
		bottom.getChildren().add(turnOrder);
		bottom.getChildren().add(surrenderButton);
		bottom.getChildren().add(attackButton);
		bottom.getChildren().add(heavyButton);
		bottom.getChildren().add(standardButton);
		bottom.getChildren().add(simpleButton);
		
		
		//Left pane
		Pane left = new Pane();
		left.setPrefHeight(400);
		left.setPrefWidth(170);
		
		GridPane gridEnemy0 = new GridPane();
		if (combat.getEnemy().getMember(0).isAlive()){
			gridEnemy0.setLayoutX(0);
			gridEnemy0.setLayoutY(0);
			gridEnemy0.setPrefHeight(100);
			gridEnemy0.setPrefWidth(170);
			gridEnemy0.add(new Text("Name: " + combat.getEnemy().getMember(0).getName()), 0, 0);
			gridEnemy0.add(new Text("HP: " + combat.getEnemy().getMember(0).getHealth()), 0, 1);
			gridEnemy0.add(new Text("Power: " + combat.getEnemy().getMember(0).getPower()), 0, 2);
			gridEnemy0.add(new Text("Weakness: " + combat.getEnemy().getMember(0).getWeakness()), 0, 3);			
		}
		
		
		GridPane gridEnemy1 = new GridPane();
		if (combat.getEnemy().getSize() > 1){
			if (combat.getEnemy().getMember(1).isAlive()){
				gridEnemy1.setLayoutX(0);
				gridEnemy1.setLayoutY(100);
				gridEnemy1.setPrefHeight(100);
				gridEnemy1.setPrefWidth(170);
				gridEnemy1.add(new Text("Name: " + combat.getEnemy().getMember(1).getName()), 0, 0);
				gridEnemy1.add(new Text("HP: " + combat.getEnemy().getMember(1).getHealth()), 0, 1);
				gridEnemy1.add(new Text("Power: " + combat.getEnemy().getMember(1).getPower()), 0, 2);
				gridEnemy1.add(new Text("Weakness: " + combat.getEnemy().getMember(1).getWeakness()), 0, 3);			
			}
		}
		
		GridPane gridEnemy2 = new GridPane();
		if (combat.getEnemy().getSize() > 2){
			if (combat.getEnemy().getMember(2).isAlive()){
				gridEnemy2.setLayoutX(0);
				gridEnemy2.setLayoutY(200);
				gridEnemy2.setPrefHeight(100);
				gridEnemy2.setPrefWidth(170);
				gridEnemy2.add(new Text("Name: " + combat.getEnemy().getMember(2).getName()), 0, 0);
				gridEnemy2.add(new Text("HP: " + combat.getEnemy().getMember(2).getHealth()), 0, 1);
				gridEnemy2.add(new Text("Power: " + combat.getEnemy().getMember(2).getPower()), 0, 2);
				gridEnemy2.add(new Text("Weakness: " + combat.getEnemy().getMember(2).getWeakness()), 0, 3);						
			}
		}
		
		GridPane gridEnemy3 = new GridPane();
		if (combat.getEnemy().getSize() > 3){
			if (combat.getEnemy().getMember(3).isAlive()){
				gridEnemy3.setLayoutX(0);
				gridEnemy3.setLayoutY(300);
				gridEnemy3.setPrefHeight(100);
				gridEnemy3.setPrefWidth(170);
				gridEnemy3.add(new Text("Name: " + combat.getEnemy().getMember(3).getName()), 0, 0);
				gridEnemy3.add(new Text("HP: " + combat.getEnemy().getMember(3).getHealth()), 0, 1);
				gridEnemy3.add(new Text("Power: " + combat.getEnemy().getMember(3).getPower()), 0, 2);
				gridEnemy3.add(new Text("Weakness: " + combat.getEnemy().getMember(3).getWeakness()), 0, 3);	
			}
		}
		
		left.getChildren().add(gridEnemy0);
		left.getChildren().add(gridEnemy1);
		left.getChildren().add(gridEnemy2);
		left.getChildren().add(gridEnemy3);
		
		
		//Right pane
		Pane right = new Pane();
		right.setPrefHeight(400);
		right.setPrefWidth(170);
		
		GridPane gridPlayer0 = new GridPane();
		if (combat.getParty().getMember(0).isAlive()){
			gridPlayer0.setLayoutX(0);
			gridPlayer0.setLayoutY(0);
			gridPlayer0.setPrefHeight(100);
			gridPlayer0.setPrefWidth(170);
			gridPlayer0.add(new Text("Name: " + combat.getParty().getMember(0).getName()), 0, 0);
			gridPlayer0.add(new Text("HP: " + combat.getParty().getMember(0).getHealth()), 0, 1);
			gridPlayer0.add(new Text("Power: " + combat.getParty().getMember(0).getPower()), 0, 2);
			gridPlayer0.add(new Text("Damagetype: " + combat.getParty().getMember(0).getStringDamageType()), 0, 3);			
		}
		
		GridPane gridPlayer1 = new GridPane();
		if (combat.getParty().getSize() > 1){
			if (combat.getParty().getMember(1).isAlive()){
				gridPlayer1.setLayoutX(0);
				gridPlayer1.setLayoutY(100);
				gridPlayer1.setPrefHeight(100);
				gridPlayer1.setPrefWidth(170);
				gridPlayer1.add(new Text("Name: " + combat.getParty().getMember(1).getName()), 0, 0);
				gridPlayer1.add(new Text("HP: " + combat.getParty().getMember(1).getHealth()), 0, 1);
				gridPlayer1.add(new Text("Power: " + combat.getParty().getMember(1).getPower()), 0, 2);
				gridPlayer1.add(new Text("Damagetype: " + combat.getParty().getMember(1).getStringDamageType()), 0, 3);
			}			
		}
		
		GridPane gridPlayer2 = new GridPane();
		if (combat.getParty().getSize() > 2){
			if (combat.getParty().getMember(2).isAlive()){
				gridPlayer2.setLayoutX(0);
				gridPlayer2.setLayoutY(200);
				gridPlayer2.setPrefHeight(100);
				gridPlayer2.setPrefWidth(170);
				gridPlayer2.add(new Text("Name: " + combat.getParty().getMember(2).getName()), 0, 0);
				gridPlayer2.add(new Text("HP: " + combat.getParty().getMember(2).getHealth()), 0, 1);
				gridPlayer2.add(new Text("Power: " + combat.getParty().getMember(2).getPower()), 0, 2);
				gridPlayer2.add(new Text("Damagetype: " + combat.getParty().getMember(2).getStringDamageType()), 0, 3);
			}
		}
		
		GridPane gridPlayer3 = new GridPane();
		if (combat.getParty().getSize() > 3){			
			if (combat.getParty().getMember(3).isAlive()){
				gridPlayer3.setLayoutX(0);
				gridPlayer3.setLayoutY(300);
				gridPlayer3.setPrefHeight(100);
				gridPlayer3.setPrefWidth(170);
				gridPlayer3.add(new Text("Name: " + combat.getParty().getMember(3).getName()), 0, 0);
				gridPlayer3.add(new Text("HP: " + combat.getParty().getMember(3).getHealth()), 0, 1);
				gridPlayer3.add(new Text("Power: " + combat.getParty().getMember(3).getPower()), 0, 2);
				gridPlayer3.add(new Text("Damagetype: " + combat.getParty().getMember(3).getStringDamageType()), 0, 3);
			}
		}
		
		right.getChildren().add(gridPlayer0);
		right.getChildren().add(gridPlayer1);
		right.getChildren().add(gridPlayer2);
		right.getChildren().add(gridPlayer3);
		
		//Center pane
		GridPane center = new GridPane();
		center.setPrefHeight(400);
		center.setPrefWidth(460);
		for (int i = 0; i < 3; i++) {
			ColumnConstraints column = new ColumnConstraints(153);
			center.getColumnConstraints().add(column);
		}
		
		center.setGridLinesVisible(false);
		
		if (combat.getEnemy().getSize() > 0) {
			if (combat.getEnemy().getMember(0).isAlive()) {
				System.out.println("Image enemy 0");
				File fileEnemy0 = new File(combat.getEnemy().getMember(0).getCombatRepresentation());
				Image enemy0Im = new Image(fileEnemy0.toURI().toString());
				ImageView imageEnemy0 = new ImageView(enemy0Im);
				imageEnemy0.setFitHeight(100);
				imageEnemy0.setFitWidth(153);
				imageEnemy0.setPickOnBounds(true);
				imageEnemy0.setPreserveRatio(true);
				center.add(imageEnemy0, 0, 0);
				GridPane.setHalignment(imageEnemy0, HPos.CENTER);
			}
		}
		
		
		if (combat.getEnemy().getSize() > 1) {
			ImageView imageEnemy1;
			if (combat.getEnemy().getMember(1).isAlive()) {
				File fileEnemy1 = new File(combat.getEnemy().getMember(1).getCombatRepresentation());
				Image enemy1Im = new Image(fileEnemy1.toURI().toString());
				imageEnemy1 = new ImageView(enemy1Im);
				imageEnemy1.setFitHeight(100);
				imageEnemy1.setFitWidth(153);
				imageEnemy1.setPickOnBounds(true);
				imageEnemy1.setPreserveRatio(true);
				center.add(imageEnemy1, 0, 1);
				GridPane.setHalignment(imageEnemy1, HPos.CENTER);
			}
		}
		
		if (combat.getEnemy().getSize() > 2) {
			if (combat.getEnemy().getMember(2).isAlive()) {
				File fileEnemy2 = new File(combat.getEnemy().getMember(2).getCombatRepresentation());
				Image enemy2Im = new Image(fileEnemy2.toURI().toString());
				ImageView imageEnemy2 = new ImageView(enemy2Im);
				imageEnemy2.setFitHeight(100);
				imageEnemy2.setFitWidth(153);
				imageEnemy2.setPickOnBounds(true);
				imageEnemy2.setPreserveRatio(true);
				center.add(imageEnemy2, 0, 2);
				GridPane.setHalignment(imageEnemy2, HPos.CENTER);
			}
		}
		
		if (combat.getEnemy().getSize() > 3) {
			if (combat.getEnemy().getMember(3).isAlive()) {
				File fileEnemy3 = new File(combat.getEnemy().getMember(3).getCombatRepresentation());
				Image enemy3Im = new Image(fileEnemy3.toURI().toString());
				ImageView imageEnemy3 = new ImageView(enemy3Im);
				imageEnemy3.setFitHeight(100);
				imageEnemy3.setFitWidth(153);
				imageEnemy3.setPickOnBounds(true);
				imageEnemy3.setPreserveRatio(true);
				center.add(imageEnemy3, 0, 3);
				GridPane.setHalignment(imageEnemy3, HPos.CENTER);
			}
		}
		
		if (combat.getParty().getSize() > 0) {
			if (combat.getParty().getMember(0).isAlive()) {
				File filePlayer0 = new File(combat.getParty().getMember(0).getCombatRepresentation());
				Image Player0Im = new Image(filePlayer0.toURI().toString());
				ImageView imagePlayer0 = new ImageView(Player0Im);
				imagePlayer0.setFitHeight(100);
				imagePlayer0.setFitWidth(153);
				imagePlayer0.setPickOnBounds(true);
				imagePlayer0.setPreserveRatio(true);
				center.add(imagePlayer0, 2, 0);
				GridPane.setHalignment(imagePlayer0, HPos.CENTER);
			}
		}
		
		
		if (combat.getParty().getSize() > 1) {
			if (combat.getParty().getMember(1).isAlive()) {
				File filePlayer1 = new File(combat.getParty().getMember(1).getCombatRepresentation());
				Image Player1Im = new Image(filePlayer1.toURI().toString());
				ImageView imagePlayer1 = new ImageView(Player1Im);
				imagePlayer1.setFitHeight(100);
				imagePlayer1.setFitWidth(153);
				imagePlayer1.setPickOnBounds(true);
				imagePlayer1.setPreserveRatio(true);
				center.add(imagePlayer1, 2, 1);
				GridPane.setHalignment(imagePlayer1, HPos.CENTER);
			}
		}
		
		
		if (combat.getParty().getSize() > 2) {
			if (combat.getParty().getMember(2).isAlive()) {
				File filePlayer2 = new File(combat.getParty().getMember(2).getCombatRepresentation());
				Image Player2Im = new Image(filePlayer2.toURI().toString());
				ImageView imagePlayer2 = new ImageView(Player2Im);
				imagePlayer2.setFitHeight(100);
				imagePlayer2.setFitWidth(153);
				imagePlayer2.setPickOnBounds(true);
				imagePlayer2.setPreserveRatio(true);
				center.add(imagePlayer2, 2, 2);
				GridPane.setHalignment(imagePlayer2, HPos.CENTER);
			}
		}
		
		if (combat.getParty().getSize() > 3) {
			if (combat.getParty().getMember(3).isAlive()) {
				File filePlayer3 = new File(combat.getParty().getMember(3).getCombatRepresentation());
				Image Player3Im = new Image(filePlayer3.toURI().toString());
				ImageView imagePlayer3 = new ImageView(Player3Im);
				imagePlayer3.setFitHeight(100);
				imagePlayer3.setFitWidth(153);
				imagePlayer3.setPickOnBounds(true);
				imagePlayer3.setPreserveRatio(true);
				center.add(imagePlayer3, 2, 3);
				GridPane.setHalignment(imagePlayer3, HPos.CENTER);
			}
		}
		
		//enemy0Button = new Button();
		enemy0Button.setMnemonicParsing(false);
		enemy0Button.setOpacity(0);
		enemy0Button.setPrefHeight(69);
		enemy0Button.setPrefWidth(153);
		enemy0Button.setText("Enemy0");
		center.add(enemy0Button, 0, 0);
		
		//enemy1Button = new Button();
		enemy1Button.setMnemonicParsing(false);
		enemy1Button.setOpacity(0);
		enemy1Button.setPrefHeight(69);
		enemy1Button.setPrefWidth(153);
		enemy1Button.setText("Enemy1");
		center.add(enemy1Button, 0, 1);
		
		//enemy2Button = new Button();
		enemy2Button.setMnemonicParsing(false);
		enemy2Button.setOpacity(0);
		enemy2Button.setPrefHeight(69);
		enemy2Button.setPrefWidth(153);
		enemy2Button.setText("Enemy2");
		center.add(enemy2Button, 0, 2);
		
		//enemy3Button = new Button();
		enemy3Button.setMnemonicParsing(false);
		enemy3Button.setOpacity(0);
		enemy3Button.setPrefHeight(69);
		enemy3Button.setPrefWidth(153);
		enemy3Button.setText("Enemy3");
		center.add(enemy3Button, 0, 3);
		
		combatRoot.setBottom(bottom);
		combatRoot.setLeft(left);
		combatRoot.setRight(right);
		combatRoot.setCenter(center);
		scene.setRoot(combatRoot);
		
		Color grey = Color.GRAY;
		scene.setFill(grey);
		System.out.println("drawCombat");
	}
	
	public void performCombat(FriendlyParty party, EnemyParty enemy){
		
				
		enemyParty = enemy;
		playerParty = party;
		currentEnemy = 0;
		currentMember = 0;
		combat = new Combat(party,enemy);
		
		
		
		
		drawCombat(party.getMember(currentMember));
		combat.performTurn(party.getMember(currentMember));

		enemy0Button.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent arg0) {
    			System.out.println("enemy0");
    			combat.setTargetEnemy0();
    		}
    	});
    	
    	enemy1Button.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent arg0) {
    			System.out.println("enemy1");
    			combat.setTargetEnemy1();
    		}
    	});
    	
    	enemy2Button.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent arg0) {
    			System.out.println("enemy2");
    			combat.setTargetEnemy2();
    		}
    	});
    	
    	enemy3Button.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent arg0) {
    			System.out.println("enemy3");
    			combat.setTargetEnemy3();
    		}
    	});
    	
    	attackButton.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent arg0) {
    			
    			System.out.println("attack");
    			Member currentPlayer = combat.getParty().getMember(currentMember);
    			combat.performAttack(currentPlayer);
    			if(currentEnemy < enemyParty.getAlive()){
    				combat.performAITurn(enemyParty.getMember(currentEnemy));
    			}
    			if(enemyParty.isDead()){
    				scene.setRoot(gridPane);
    				scene.setOnKeyPressed(keyEventHandler);
    				grid();
    				gridChange(tempList[1], tempList[2], gridList[tempList[2]*sceneX+tempList[1]]);
    				int[] tempBackgroundList = world.getEnemyPlacement();
    				world.clearProximity();
    				gridList = world.toChar();
    				gridChange(tempList[3], tempList[4],'@');
    				gridChange(tempBackgroundList[0], tempBackgroundList[1],gridList[tempBackgroundList[1]*sceneX+tempBackgroundList[0]]);
    			}
    			else if(playerParty.isDead()){
    				//endgame
    			}
    			currentEnemy++;
    			currentMember++;
    			if(currentMember >= playerParty.getAlive()){
    				while(currentEnemy < enemyParty.getAlive()){
    					combat.performAITurn(enemyParty.getMember(currentEnemy));
    					currentEnemy++;
    				}
    				if(enemyParty.isDead()){
    					scene.setRoot(gridPane);
    					scene.setOnKeyPressed(keyEventHandler);
    					grid();
    					gridChange(tempList[1], tempList[2], gridList[tempList[2]*sceneX+tempList[1]]);
    					int[] tempBackgroundList = world.getEnemyPlacement();
    					world.clearProximity();
    					gridList = world.toChar();
    					gridChange(tempList[3], tempList[4],'@');
    					gridChange(tempBackgroundList[0], tempBackgroundList[1],gridList[tempBackgroundList[1]*sceneX+tempBackgroundList[0]]);
    				}
    				else if(playerParty.isDead()){
    					//endgame
    				}
    				currentEnemy = 0;
    				currentMember = 0;
    			}
    			drawCombat(playerParty.getMember(currentMember));
    			
    		}
    	});
    	
    	surrenderButton.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent arg0) {
    			System.out.println("surrender");
    			combat.surrender();
    		}
    	});
    	
    	heavyButton.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent arg0) {
    			System.out.println("heavy");
    			Member currentPlayer = combat.getParty().getMember(currentMember);
    			combat.setAttackHeavy(currentPlayer);
    		}
    	});
    	
    	standardButton.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent arg0) {
    			System.out.println("standard");
    			Member currentPlayer = combat.getParty().getMember(currentMember);
    			combat.setAttackStandard(currentPlayer);
    		}
    	});
    	
    	simpleButton.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent arg0) {
    			System.out.println("simple");
    			Member currentPlayer = combat.getParty().getMember(currentMember);
    			combat.setAttackSimple(currentPlayer);
    		}
    	});
		
    	
    	
		
		//drawCombat(enemy.getMember(currentEnemy));
		//combat.performAITurn(enemy.getMember(currentEnemy));
		//return returnBool;
		//while(true){
			
//			for (int i = 0; i < 8; i++){
//				if (i % 2 == 0){
//					if (party.getSize() > (i/2)){
//						drawCombat(combat,party.getMember(i/2));
//						
//						
//						enemy0Button.setOnAction(new EventHandler<ActionEvent>() {
//				    		@Override
//				    		public void handle(ActionEvent arg0) {
//				    			System.out.println("enemy0");
//				    			combat.setTargetEnemy0();
//				    		}
//				    	});
//				    	
//				    	enemy1Button.setOnAction(new EventHandler<ActionEvent>() {
//				    		@Override
//				    		public void handle(ActionEvent arg0) {
//				    			System.out.println("enemy1");
//				    			combat.setTargetEnemy1();
//				    		}
//				    	});
//				    	
//				    	enemy2Button.setOnAction(new EventHandler<ActionEvent>() {
//				    		@Override
//				    		public void handle(ActionEvent arg0) {
//				    			System.out.println("enemy2");
//				    			combat.setTargetEnemy2();
//				    		}
//				    	});
//				    	
//				    	enemy3Button.setOnAction(new EventHandler<ActionEvent>() {
//				    		@Override
//				    		public void handle(ActionEvent arg0) {
//				    			System.out.println("enemy3");
//				    			combat.setTargetEnemy3();
//				    		}
//				    	});
//				    	
//				    	attackButton.setOnAction(new EventHandler<ActionEvent>() {
//				    		@Override
//				    		public void handle(ActionEvent arg0) {
//				    			System.out.println("attack");
//				    			combat.performAttack();
//				    		}
//				    	});
//				    	
//				    	surrenderButton.setOnAction(new EventHandler<ActionEvent>() {
//				    		@Override
//				    		public void handle(ActionEvent arg0) {
//				    			System.out.println("surrender");
//				    			combat.surrender();
//				    		}
//				    	});
//				    	
//				    	heavyButton.setOnAction(new EventHandler<ActionEvent>() {
//				    		@Override
//				    		public void handle(ActionEvent arg0) {
//				    			System.out.println("heavy");
//				    			combat.setAttackHeavy();
//				    		}
//				    	});
//				    	
//				    	standardButton.setOnAction(new EventHandler<ActionEvent>() {
//				    		@Override
//				    		public void handle(ActionEvent arg0) {
//				    			System.out.println("standard");
//				    			combat.setAttackStandard();
//				    		}
//				    	});
//				    	
//				    	simpleButton.setOnAction(new EventHandler<ActionEvent>() {
//				    		@Override
//				    		public void handle(ActionEvent arg0) {
//				    			System.out.println("simple");
//				    			combat.setAttackSimple();
//				    		}
//				    	});
//						
//						
//						combat.performTurn(party.getMember(i/2));
//					}
//				}
//				if(2<1){
//					int k = (int) ((i/2));
//					if (enemy.getSize() > k){
//						drawCombat(combat,enemy.getMember(k));
//						
//						
//						enemy0Button.setOnAction(new EventHandler<ActionEvent>() {
//				    		@Override
//				    		public void handle(ActionEvent arg0) {
//				    			System.out.println("enemy0");
//				    			combat.setTargetEnemy0();
//				    		}
//				    	});
//				    	
//				    	enemy1Button.setOnAction(new EventHandler<ActionEvent>() {
//				    		@Override
//				    		public void handle(ActionEvent arg0) {
//				    			System.out.println("enemy1");
//				    			combat.setTargetEnemy1();
//				    		}
//				    	});
//				    	
//				    	enemy2Button.setOnAction(new EventHandler<ActionEvent>() {
//				    		@Override
//				    		public void handle(ActionEvent arg0) {
//				    			System.out.println("enemy2");
//				    			combat.setTargetEnemy2();
//				    		}
//				    	});
//				    	
//				    	enemy3Button.setOnAction(new EventHandler<ActionEvent>() {
//				    		@Override
//				    		public void handle(ActionEvent arg0) {
//				    			System.out.println("enemy3");
//				    			combat.setTargetEnemy3();
//				    		}
//				    	});
//				    	
//				    	attackButton.setOnAction(new EventHandler<ActionEvent>() {
//				    		@Override
//				    		public void handle(ActionEvent arg0) {
//				    			System.out.println("attack");
//				    			combat.performAttack();
//				    		}
//				    	});
//				    	
//				    	surrenderButton.setOnAction(new EventHandler<ActionEvent>() {
//				    		@Override
//				    		public void handle(ActionEvent arg0) {
//				    			System.out.println("surrender");
//				    			combat.surrender();
//				    		}
//				    	});
//				    	
//				    	heavyButton.setOnAction(new EventHandler<ActionEvent>() {
//				    		@Override
//				    		public void handle(ActionEvent arg0) {
//				    			System.out.println("heavy");
//				    			combat.setAttackHeavy();
//				    		}
//				    	});
//				    	
//				    	standardButton.setOnAction(new EventHandler<ActionEvent>() {
//				    		@Override
//				    		public void handle(ActionEvent arg0) {
//				    			System.out.println("standard");
//				    			combat.setAttackStandard();
//				    		}
//				    	});
//				    	
//				    	simpleButton.setOnAction(new EventHandler<ActionEvent>() {
//				    		@Override
//				    		public void handle(ActionEvent arg0) {
//				    			System.out.println("simple");
//				    			combat.setAttackSimple();
//				    		}
//				    	});
//						
//						
//						combat.performAITurn(enemy.getMember(k));
//					}
//				}
//				if (party.isEmpty()){
//					return false;
//				}
//				if (enemy.isEmpty()){
//					return true;
//				}
//			}
//		}
	}
	
	public void drawInventory(Member currentMember) {
		currentOdd = currentMember;
		BorderPane inventoryRoot = new BorderPane();
		inventoryRoot.setPrefHeight(600);
		inventoryRoot.setPrefWidth(800);
		
		Pane bottom = new Pane();
		bottom.setPrefHeight(200);
		bottom.setPrefWidth(200);
		
		Pane left = new Pane();
		Pane right = new Pane();
		exit = new Button();
		
		//The party inventory
		ListView<Equipment> inventory = new ListView<Equipment>();
		System.out.println(playerParty.getInventory());
		ObservableList<Equipment> invItems =FXCollections.observableArrayList (
		    playerParty.getInventory());
		inventory.setItems(invItems);
		right.getChildren().add(inventory);
		
		inventory.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>(){
	        @Override public void changed(ObservableValue o,Object oldVal, 
	                 Object newVal){
	        	System.out.println(o + " + " + oldVal + " + " + newVal);
	        	inventoryItem = (Equipment) newVal;
	        }
	        });
		
		//Individual player inventory
		ListView<Equipment> currentInventory = new ListView<Equipment>();
		ObservableList<Equipment> items =FXCollections.observableArrayList (
		    currentMember.getInventory());
		currentInventory.setItems(items);
		left.getChildren().add(currentInventory);
		
		currentInventory.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>(){
	        @Override public void changed(ObservableValue o, Object oldVal, 
	                 Object newVal){
	        	currentItem = (Equipment) newVal;
	        }
	        });
		
		
		player0inv.setLayoutX(164);
		player0inv.setLayoutY(130);
		player0inv.setMnemonicParsing(false);
		player0inv.setPrefHeight(50);
		player0inv.setPrefWidth(125);
		player0inv.setText(playerParty.getMember(0).getName());
		
		player1inv.setLayoutX(314);
		player1inv.setLayoutY(130);
		player1inv.setMnemonicParsing(false);
		player1inv.setPrefHeight(50);
		player1inv.setPrefWidth(125);
		player1inv.setText(playerParty.getMember(1).getName());
		
		player2inv.setLayoutX(464);
		player2inv.setLayoutY(130);
		player2inv.setMnemonicParsing(false);
		player2inv.setPrefHeight(50);
		player2inv.setPrefWidth(125);
		player2inv.setText(playerParty.getMember(2).getName());
		
		player3inv.setLayoutX(614);
		player3inv.setLayoutY(130);
		player3inv.setMnemonicParsing(false);
		player3inv.setPrefHeight(50);
		player3inv.setPrefWidth(125);
		player3inv.setText(playerParty.getMember(3).getName());
		
		swapItem.setLayoutX(340);
		swapItem.setLayoutY(150);
		swapItem.setMnemonicParsing(false);
		swapItem.setPrefHeight(50);
		swapItem.setPrefWidth(125);
		swapItem.setText("<->");
	
		
		exit.setLayoutX(614);
		exit.setLayoutY(60);
		exit.setMnemonicParsing(false);
		exit.setPrefHeight(50);
		exit.setPrefWidth(125);
		exit.setText("Exit");
		
		inventoryRoot.setBottom(bottom);
		inventoryRoot.setLeft(left);
		inventoryRoot.setRight(right);
		
		bottom.getChildren().add(player0inv);
		bottom.getChildren().add(player1inv);
		bottom.getChildren().add(player2inv);
		bottom.getChildren().add(player3inv);
		bottom.getChildren().add(exit);
		
		left.getChildren().add(swapItem);
		
		
		scene.setRoot(inventoryRoot);
		Color grey = Color.GRAY;
		scene.setFill(grey);
		inventoryControl();
	}
	
	public void inventoryControl() {
		
		player0inv.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent arg0) {
    			System.out.println("player0");
    			drawInventory(playerParty.getMember(0));
    			
    		}
    	});
		
		player1inv.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("player1");
    			drawInventory(playerParty.getMember(1));
    			

			}
		});
		
		player2inv.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("player2");
    			drawInventory(playerParty.getMember(2));

			}
		});
		
		player3inv.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("player3");
    			drawInventory(playerParty.getMember(3));

			}
		});
		
		exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("exit");
				grid();
			}
		});
		
		swapItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (currentItem != null && inventoryItem != null) {
					System.out.println("swapping" + currentItem + " with " + inventoryItem);
					Armor armor = new Armor(ArmorType.HEADGEAR, 1);
					Weapon weapon = new Weapon(1);
					if (currentItem.getClass() == armor.getClass() && inventoryItem.getClass() == armor.getClass()) {
						currentOdd.changeEquipment((Armor) inventoryItem);
						playerParty.removeItem(inventoryItem);
						playerParty.addItem((Armor) currentItem);
					} else if (currentItem.getClass() == weapon.getClass() && inventoryItem.getClass() == weapon.getClass()) {
						currentOdd.changeEquipment((Weapon) inventoryItem);
						playerParty.removeItem(inventoryItem);
						playerParty.addItem((Weapon) currentItem);
					} else {
						System.out.println("Items must be of same type");
					}
					currentItem = null;
					inventoryItem = null;
					drawInventory(currentOdd);					
				}
			}
		});
	}
	
    @Override
    public void start(Stage stage) {
    	
    	System.out.println("preInit!");
    	scene = new Scene(new Region(), width, height);
    	this.init();
    	System.out.println("start");
        stage.setScene(scene);
        stage.setTitle("The Final Outfall");
      	stage.show();
      	scene.setRoot(new BorderPane());
      	playGame();
      	//scene.setOnKeyReleased(keyReleaseHandler);
      	
    } 
    
    public static void main(String[] args) {
    	launch(args);
        System.out.println("1");
    }

	
}
