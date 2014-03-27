package main;

import java.io.File;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;


public class GraphicsControl extends Application {

	public GridPane grid = new GridPane();
	private String fileLocation = "image/";
	File file;
	
	public void grid(Stage stage, Scene scene){
		Group root = new Group();
		grid.setPadding(new Insets(0, 0, 0, 0));
		grid.setVgap(0);
		grid.setHgap(0);
		scene.setRoot(grid);
		File file = new File(fileLocation+"blue.png");
		Image cloudImage = new Image(file.toURI().toString());
		
		file = new File("C://Users/Martin/Pictures/red.png");
		Image[] imageList = new Image[1200];
		Image sunImage = new Image(file.toURI().toString());
		for(int i = 0; i < imageList.length;i+=2){
			imageList[i] = cloudImage;
			imageList[i+1] = sunImage;
		}
		ImageView test;
		for(int i = 0; i < 40;i++){
			
			for(int j = 0 ; j < 30 ; j++){
				test = new ImageView(imageList[i]);
				GridPane.setConstraints(test, i, j);
				grid.getChildren().add(test);
			}
		}
		
		
	}
	
	
	
    @Override
    public void start(Stage stage) {
    	Region region = new Region();
    	Scene scene = new Scene(region, 800, 600);
        stage.setScene(scene);
        stage.setTitle("The Final Outfall");
        
      	stage.show();
    } 
    
    public static void main(String[] args) {
        launch(args);
        System.out.println("1");
    }

	
}
