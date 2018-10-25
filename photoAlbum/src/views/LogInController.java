package views;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.User;

/**
 * The login controller.
 * 
 * @author Jiwei Chen
 * @author Pengrui Liu
 *
 */
public class LogInController {
	@FXML
	Button login;

	@FXML
	TextField UserID;
	ArrayList<String> user = application.fileReaderWriter.readData("./data/UserProfiles/user.txt");

	/**
	 * The action is to able login admin and user view.
	 * 
	 * @param e
	 *            Action event
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void login(ActionEvent e) throws IOException, ClassNotFoundException {
		if (e.getSource() instanceof Button) {
			Button b = (Button) e.getSource();
			if (b == login) {
				if (isValidUser(UserID.getText()) == false) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText("notification");
					alert.setContentText("The user name doesn't exist");
					alert.showAndWait();
					return;
				} else {
					if (UserID.getText().equalsIgnoreCase("admin")) {
						// administrator interface.
						Stage Loginstage = (Stage) b.getScene().getWindow();

						Stage primaryStage = new Stage();
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/views/adminManagerView.fxml"));
						Pane root = (Pane) loader.load();
						Scene scene = new Scene(root, 500, 500);
						primaryStage.setScene(scene);
						primaryStage.setTitle("Admin Manager");
						primaryStage.setResizable(false);
						AdminController list = loader.getController();
						list.start(primaryStage);
						Loginstage.close();
						primaryStage.show();
					} else {
						Stage Loginstage = (Stage) b.getScene().getWindow();
						LoadUserView();
						Loginstage.close();

					}
				}
			}

		}

	}

	/**
	 * To load user view.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */

	private void LoadUserView() throws IOException, ClassNotFoundException {
		Stage s = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/UserView.fxml"));
		Pane root = (Pane) loader.load();
		Scene scene = new Scene(root, 400, 550);
		s.setScene(scene);
		s.setTitle(UserID.getText());
		s.setResizable(true);
		UserController Controller = loader.getController();
		Controller.user = User.ReadUser(UserID.getText());
		Controller.start();
		s.resizableProperty();
		s.show();
	}

	/**
	 * The action is to close application.
	 * 
	 * @param actionEvent
	 *            Action event
	 */
	public void quit(ActionEvent actionEvent) {
		Button b = (Button) actionEvent.getSource();
		Stage stage = (Stage) b.getScene().getWindow();

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Do you want to close this Application? ");
		alert.setContentText("");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			stage.close();
		} else {
			// ... user chose CANCEL or closed the dialog
		}

	}

	/**
	 * To check have same data or not 
	 * @param s String of data
	 * @return	true or false
	 * @throws IOException
	 */
	private boolean isValidUser(String s) throws IOException {
		if (s.equals("admin")) {
			return true;
		} else {
			for (int i = 0; i < user.size(); i++) {
				if (s.equals(user.get(i))) {
					return true;
				}
			}
		}
		return false;
	}
}
