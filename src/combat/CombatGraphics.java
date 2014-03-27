package combat;


import java.net.URL;

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

	@Override
	public void start(Stage stage) throws Exception {
		
		URL resource = getClass().getResource(this.getClass().getSimpleName() + ".fxml");
	    Parent root = FXMLLoader.load(resource);
	    
	    
		
    }
	
	public static void main(String[] args){
		launch(CombatGraphics.class,args);
	}
	
	

}
