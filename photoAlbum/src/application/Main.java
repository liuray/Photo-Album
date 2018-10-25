package application;
	
import java.io.IOException;

import views.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;


/**
 * To able run this application
 * @author Jiwei Chen
 * @author Pengrui Liu
 */
public class Main extends Application {
	
	/* 
	 * To call login view start application
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/LogInView.fxml"));
		Pane root = (Pane) loader.load();
		Scene scene = new Scene(root, 320, 200);
		//scene.getStylesheets().add("/gui/application.css");
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login");
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);

	}
}
