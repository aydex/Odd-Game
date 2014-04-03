package combat;


import java.io.File;
import java.util.ArrayList;

import equipment.Weapon.DamageType;
import party.Member;
import party.Member.MemberType;
import party.Party;
import party.FriendlyParty;
import party.EnemyParty;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CombatGraphics extends Application{
	
	private BorderPane combatRoot;
	private Scene scene;
	private Button enemy0Button;
	private Button enemy1Button;
	private Button enemy2Button;
	private Button enemy3Button;
	private Button attackButton;
	private Button surrenderButton;
	private Button heavyButton;
	private Button standardButton;
	private Button simpleButton;
	/*
	private ImageView imageEnemy0;
	private ImageView imageEnemy1;
	private ImageView imageEnemy2;
	private ImageView imageEnemy3;
	private ImageView player0Image;
	private ImageView player1Image;
	private ImageView player2Image;
	private ImageView player3Image;
	*/
	
	
	public void drawCombat(Combat combat, Member currentPlayer){
		BorderPane combatRoot = new BorderPane();
		combatRoot.setPrefHeight(600);
		combatRoot.setPrefWidth(800);
		
		//Bottom pane
		Pane bottom = new Pane();
		bottom.setPrefHeight(200);
		bottom.setPrefWidth(200);
		
		Text turnOrder = new Text("Hello");
		turnOrder.setLayoutX(20);
		turnOrder.setLayoutY(100);
		
		surrenderButton = new Button();
		surrenderButton.setLayoutX(14);
		surrenderButton.setLayoutY(137);
		surrenderButton.setMnemonicParsing(false);
		surrenderButton.setPrefHeight(50);
		surrenderButton.setPrefWidth(125);
		surrenderButton.setText("Surrender");
		
		attackButton = new Button();
		attackButton.setLayoutX(661);
		attackButton.setLayoutY(137);
		attackButton.setMnemonicParsing(false);
		attackButton.setPrefHeight(50);
		attackButton.setPrefWidth(125);
		attackButton.setText("Attack");
		
		heavyButton = new Button();
		heavyButton.setLayoutX(180);
		heavyButton.setLayoutY(56);
		heavyButton.setMnemonicParsing(false);
		heavyButton.setPrefWidth(125);
		heavyButton.setText("Heavy");
		
		standardButton = new Button();
		standardButton.setLayoutX(338);
		standardButton.setLayoutY(56);
		standardButton.setMnemonicParsing(false);
		standardButton.setPrefWidth(125);
		standardButton.setText("Standard");
		
		simpleButton = new Button();
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
		if (combat.enemy.getMember(0).isAlive()){
			gridEnemy0.setLayoutX(0);
			gridEnemy0.setLayoutY(0);
			gridEnemy0.setPrefHeight(100);
			gridEnemy0.setPrefWidth(170);
			gridEnemy0.add(new Text("Name: " + combat.enemy.getMember(0).getName()), 0, 0);
			gridEnemy0.add(new Text("HP: " + combat.enemy.getMember(0).getHealth()), 0, 1);
			gridEnemy0.add(new Text("Power: " + combat.enemy.getMember(0).getPower()), 0, 2);
			gridEnemy0.add(new Text("Weakness: " + combat.enemy.getMember(0).getWeakness()), 0, 3);			
		}
		
		
		GridPane gridEnemy1 = new GridPane();
		if (combat.enemy.getSize() > 1){
			if (combat.enemy.getMember(0).isAlive()){
				gridEnemy1.setLayoutX(0);
				gridEnemy1.setLayoutY(100);
				gridEnemy1.setPrefHeight(100);
				gridEnemy1.setPrefWidth(170);
				gridEnemy1.add(new Text("Name: " + combat.enemy.getMember(1).getName()), 0, 0);
				gridEnemy1.add(new Text("HP: " + combat.enemy.getMember(1).getHealth()), 0, 1);
				gridEnemy1.add(new Text("Power: " + combat.enemy.getMember(1).getPower()), 0, 2);
				gridEnemy1.add(new Text("Weakness: " + combat.enemy.getMember(1).getWeakness()), 0, 3);			
			}
		}
		
		GridPane gridEnemy2 = new GridPane();
		if (combat.enemy.getSize() > 2){
			if (combat.enemy.getMember(0).isAlive()){
				gridEnemy2.setLayoutX(0);
				gridEnemy2.setLayoutY(200);
				gridEnemy2.setPrefHeight(100);
				gridEnemy2.setPrefWidth(170);
				gridEnemy2.add(new Text("Name: " + combat.enemy.getMember(2).getName()), 0, 0);
				gridEnemy2.add(new Text("HP: " + combat.enemy.getMember(2).getHealth()), 0, 1);
				gridEnemy2.add(new Text("Power: " + combat.enemy.getMember(2).getPower()), 0, 2);
				gridEnemy2.add(new Text("Weakness: " + combat.enemy.getMember(2).getWeakness()), 0, 3);						
			}
		}
		
		GridPane gridEnemy3 = new GridPane();
		if (combat.enemy.getSize() > 3){
			if (combat.enemy.getMember(0).isAlive()){
				gridEnemy3.setLayoutX(0);
				gridEnemy3.setLayoutY(300);
				gridEnemy3.setPrefHeight(100);
				gridEnemy3.setPrefWidth(170);
				gridEnemy3.add(new Text("Name: " + combat.enemy.getMember(3).getName()), 0, 0);
				gridEnemy3.add(new Text("HP: " + combat.enemy.getMember(3).getHealth()), 0, 1);
				gridEnemy3.add(new Text("Power: " + combat.enemy.getMember(3).getPower()), 0, 2);
				gridEnemy3.add(new Text("Weakness: " + combat.enemy.getMember(3).getWeakness()), 0, 3);	
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
		if (combat.party.getMember(0).isAlive()){
			gridPlayer0.setLayoutX(0);
			gridPlayer0.setLayoutY(0);
			gridPlayer0.setPrefHeight(100);
			gridPlayer0.setPrefWidth(170);
			gridPlayer0.add(new Text("Name: " + combat.party.getMember(0).getName()), 0, 0);
			gridPlayer0.add(new Text("HP: " + combat.party.getMember(0).getHealth()), 0, 1);
			gridPlayer0.add(new Text("Power: " + combat.party.getMember(0).getPower()), 0, 2);
			gridPlayer0.add(new Text("Damagetype: " + combat.party.getMember(0).getStringDamageType()), 0, 3);			
		}
		
		GridPane gridPlayer1 = new GridPane();
		if (combat.party.getSize() > 1){
			if (combat.party.getMember(0).isAlive()){
				gridPlayer1.setLayoutX(0);
				gridPlayer1.setLayoutY(100);
				gridPlayer1.setPrefHeight(100);
				gridPlayer1.setPrefWidth(170);
				gridPlayer1.add(new Text("Name: " + combat.party.getMember(1).getName()), 0, 0);
				gridPlayer1.add(new Text("HP: " + combat.party.getMember(1).getHealth()), 0, 1);
				gridPlayer1.add(new Text("Power: " + combat.party.getMember(1).getPower()), 0, 2);
				gridPlayer1.add(new Text("Damagetype: " + combat.party.getMember(1).getStringDamageType()), 0, 3);
			}			
		}
		
		GridPane gridPlayer2 = new GridPane();
		if (combat.party.getSize() > 2){
			if (combat.party.getMember(0).isAlive()){
				gridPlayer2.setLayoutX(0);
				gridPlayer2.setLayoutY(200);
				gridPlayer2.setPrefHeight(100);
				gridPlayer2.setPrefWidth(170);
				gridPlayer2.add(new Text("Name: " + combat.party.getMember(2).getName()), 0, 0);
				gridPlayer2.add(new Text("HP: " + combat.party.getMember(2).getHealth()), 0, 1);
				gridPlayer2.add(new Text("Power: " + combat.party.getMember(2).getPower()), 0, 2);
				gridPlayer2.add(new Text("Damagetype: " + combat.party.getMember(2).getStringDamageType()), 0, 3);
			}
		}
		
		GridPane gridPlayer3 = new GridPane();
		if (combat.party.getSize() > 3){			
			if (combat.party.getMember(0).isAlive()){
				gridPlayer3.setLayoutX(0);
				gridPlayer3.setLayoutY(300);
				gridPlayer3.setPrefHeight(100);
				gridPlayer3.setPrefWidth(170);
				gridPlayer3.add(new Text("Name: " + combat.party.getMember(3).getName()), 0, 0);
				gridPlayer3.add(new Text("HP: " + combat.party.getMember(3).getHealth()), 0, 1);
				gridPlayer3.add(new Text("Power: " + combat.party.getMember(3).getPower()), 0, 2);
				gridPlayer3.add(new Text("Damagetype: " + combat.party.getMember(3).getStringDamageType()), 0, 3);
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
		
		center.setGridLinesVisible(true);
		
		File fileEnemy0 = new File("C://Users/Odd/Pictures/Outfall/enemy0.png");
		Image enemy0Im = new Image(fileEnemy0.toURI().toString());
		ImageView imageEnemy0 = new ImageView(enemy0Im);
		imageEnemy0.setFitHeight(100);
		imageEnemy0.setFitWidth(153);
		imageEnemy0.setPickOnBounds(true);
		imageEnemy0.setPreserveRatio(true);
		center.add(imageEnemy0, 0, 0);
		GridPane.setHalignment(imageEnemy0, HPos.CENTER);
		
		File fileEnemy1 = new File("C://Users/Odd/Pictures/Outfall/enemy0.png");
		Image enemy1Im = new Image(fileEnemy1.toURI().toString());
		ImageView imageEnemy1 = new ImageView(enemy1Im);
		imageEnemy1.setFitHeight(100);
		imageEnemy1.setFitWidth(153);
		imageEnemy1.setPickOnBounds(true);
		imageEnemy1.setPreserveRatio(true);
		center.add(imageEnemy1, 0, 1);
		GridPane.setHalignment(imageEnemy1, HPos.CENTER);
		
		File fileEnemy2 = new File("C://Users/Odd/Pictures/Outfall/enemy0.png");
		Image enemy2Im = new Image(fileEnemy2.toURI().toString());
		ImageView imageEnemy2 = new ImageView(enemy2Im);
		imageEnemy2.setFitHeight(100);
		imageEnemy2.setFitWidth(153);
		imageEnemy2.setPickOnBounds(true);
		imageEnemy2.setPreserveRatio(true);
		center.add(imageEnemy2, 0, 2);
		GridPane.setHalignment(imageEnemy2, HPos.CENTER);
		
		File fileEnemy3 = new File("C://Users/Odd/Pictures/Outfall/enemy0.png");
		Image enemy3Im = new Image(fileEnemy3.toURI().toString());
		ImageView imageEnemy3 = new ImageView(enemy3Im);
		imageEnemy3.setFitHeight(100);
		imageEnemy3.setFitWidth(153);
		imageEnemy3.setPickOnBounds(true);
		imageEnemy3.setPreserveRatio(true);
		center.add(imageEnemy3, 0, 3);
		GridPane.setHalignment(imageEnemy3, HPos.CENTER);
		
		File filePlayer0 = new File("C://Users/Odd/Pictures/Outfall/enemy0.png");
		Image Player0Im = new Image(filePlayer0.toURI().toString());
		ImageView imagePlayer0 = new ImageView(Player0Im);
		imagePlayer0.setFitHeight(100);
		imagePlayer0.setFitWidth(153);
		imagePlayer0.setPickOnBounds(true);
		imagePlayer0.setPreserveRatio(true);
		center.add(imagePlayer0, 2, 0);
		GridPane.setHalignment(imagePlayer0, HPos.CENTER);
		
		File filePlayer1 = new File("C://Users/Odd/Pictures/Outfall/enemy0.png");
		Image Player1Im = new Image(filePlayer1.toURI().toString());
		ImageView imagePlayer1 = new ImageView(Player1Im);
		imagePlayer1.setFitHeight(100);
		imagePlayer1.setFitWidth(153);
		imagePlayer1.setPickOnBounds(true);
		imagePlayer1.setPreserveRatio(true);
		center.add(imagePlayer1, 2, 1);
		GridPane.setHalignment(imagePlayer1, HPos.CENTER);
		
		File filePlayer2 = new File("C://Users/Odd/Pictures/Outfall/enemy0.png");
		Image Player2Im = new Image(filePlayer2.toURI().toString());
		ImageView imagePlayer2 = new ImageView(Player2Im);
		imagePlayer2.setFitHeight(100);
		imagePlayer2.setFitWidth(153);
		imagePlayer2.setPickOnBounds(true);
		imagePlayer2.setPreserveRatio(true);
		center.add(imagePlayer2, 2, 2);
		GridPane.setHalignment(imagePlayer2, HPos.CENTER);
		
		File filePlayer3 = new File("C://Users/Odd/Pictures/Outfall/enemy0.png");
		Image Player3Im = new Image(filePlayer3.toURI().toString());
		ImageView imagePlayer3 = new ImageView(Player3Im);
		imagePlayer3.setFitHeight(100);
		imagePlayer3.setFitWidth(153);
		imagePlayer3.setPickOnBounds(true);
		imagePlayer3.setPreserveRatio(true);
		center.add(imagePlayer3, 2, 3);
		GridPane.setHalignment(imagePlayer3, HPos.CENTER);
		
		enemy0Button = new Button();
		enemy0Button.setMnemonicParsing(false);
		enemy0Button.setOpacity(0);
		enemy0Button.setPrefHeight(69);
		enemy0Button.setPrefWidth(153);
		enemy0Button.setText("Enemy0");
		center.add(enemy0Button, 0, 0);
		
		enemy1Button = new Button();
		enemy1Button.setMnemonicParsing(false);
		enemy1Button.setOpacity(0);
		enemy1Button.setPrefHeight(69);
		enemy1Button.setPrefWidth(153);
		enemy1Button.setText("Enemy1");
		center.add(enemy1Button, 0, 1);
		
		enemy2Button = new Button();
		enemy2Button.setMnemonicParsing(false);
		enemy2Button.setOpacity(0);
		enemy2Button.setPrefHeight(69);
		enemy2Button.setPrefWidth(153);
		enemy2Button.setText("Enemy2");
		center.add(enemy2Button, 0, 2);
		
		enemy3Button = new Button();
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
	}
	
	public boolean performCombat(FriendlyParty party, EnemyParty enemy){
		Combat combat = new Combat(party,enemy);
		while(true){
			for (int i = 0; i < 8; i++){
				if (i % 2 == 0){
					if (party.getSize() >= (i/2)){
						drawCombat(combat,party.getMember(i/2));
						combat.performTurn(party.getMember(i/2));
					}
				}
				else{
					int k = (int) ((i/2) - 0.5);
					if (party.getSize() > k){
						drawCombat(combat,enemy.getMember(k));
						combat.performAITurn(enemy.getMember(k));
					}
				}
				if (party.isEmpty()){
					return false;
				}
				if (enemy.isEmpty()){
					return true;
				}
			}
		}
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		combatRoot = new BorderPane();
		scene = new Scene(combatRoot);
		
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
		
		Member enemy0 = new Member(MemberType.HUMAN,1);
		Member enemy1 = new Member(MemberType.HUMAN,1);
		Member enemy2 = new Member(MemberType.HUMAN,1);
		Member enemy3 = new Member(MemberType.HUMAN,1);
		ArrayList<Member> enemies = new ArrayList<Member>();
		enemies.add(enemy0);
		enemies.add(enemy1);
		enemies.add(enemy2);
		enemies.add(enemy3);
		
		FriendlyParty party = new FriendlyParty(players);
		EnemyParty enemy = new EnemyParty(enemies);
		
		performCombat(party,enemy);
		
		
		//Event handlers
		enemy0Button.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent arg0) {
	    		System.out.println("enemy0");
	    		//combat.setTargetEnemy0();
	    	}
	    });
	    
	    enemy1Button.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent arg0) {
	    		System.out.println("enemy1");
	    		//combat.setTargetEnemy1();
	    	}
	    });
	    
	    enemy2Button.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent arg0) {
	    		System.out.println("enemy2");
	    		//combat.setTargetEnemy2();
	    	}
	    });
	    
	    enemy3Button.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent arg0) {
	    		System.out.println("enemy3");
	    		//combat.setTargetEnemy3();
	    	}
	    });
	    
	    attackButton.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent arg0) {
	    		System.out.println("attack");
	    		//combat.performAttack();
	    	}
	    });
	    
	    surrenderButton.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent arg0) {
	    		System.out.println("surrender");
	    		//combat.surrender();
	    	}
	    });
	    
	    heavyButton.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent arg0) {
	    		System.out.println("heavy");
	    		//combat.setAttackHeavy();
	    	}
	    });
	    
	    standardButton.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent arg0) {
	    		System.out.println("standard");
	    		//combat.setAttackStandard();
	    	}
	    });
	    
	    simpleButton.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent arg0) {
	    		System.out.println("simple");
	    		//combat.setAttackSimple();
	    	}
	    });
		
	    
		Color grey = Color.GRAY;
		scene.setFill(grey);
	    stage.setScene(scene);
	    stage.setTitle("Combat");
	    stage.show();
		
	}
	
	public static void main(String[] args){
		launch(args);
	}
	
	

}
