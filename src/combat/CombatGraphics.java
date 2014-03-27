package combat;


import java.net.URL;
import party.Party;
import party.FriendlyParty;
import party.EnemyParty;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CombatGraphics extends Application{
	
	private Button enemy0;
	private Button enemy1;
	private Button enemy2;
	private Button enemy3;
	
	CombatGraphics(Stage stage, FriendlyParty party, EnemyParty enemy){
		Combat combat = new Combat(party,enemy);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		URL resource = getClass().getResource(this.getClass().getSimpleName() + ".fxml");
	    Parent root = FXMLLoader.load(resource);
	    
	    
		
    }
	
	public static void main(String[] args){
		launch(CombatGraphics.class,args);
	}
	
	

}
