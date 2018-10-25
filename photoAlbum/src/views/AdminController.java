package views;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.User;

/**
 * The admin Controller  
 * @author Jiwei Chen 
 * @author Pengrui Liu
 */
public class AdminController {
	@FXML
	Button Add;
	@FXML
	Button login;
	@FXML
	Button Close;
	@FXML
	Button Delete;
	@FXML
	ListView<String> listView;

	/**
	 * The observable list  to store user name.
	 */
	private ObservableList<String> obsList;
	/**
	 * The array list to store data from txt file
	 */
	ArrayList<String> user = application.fileReaderWriter.readData("./data/UserProfiles/user.txt");
	/**
	 * The array list
	 */
	ArrayList<String> list = new ArrayList<String>();

	/**
	 * The main stage of application.
	 * @param mainStage The main stage of application
	 */
	public void start(Stage mainStage) {

		for (int i = 0; i < user.size(); i++) {
			list.add(user.get(i).trim());
		}
		obsList = FXCollections.observableArrayList(list);
		FXCollections.sort(obsList);
		listView.setItems(obsList);
	}

	/**
	 * To add the user name.
	 * @param actionEvent
	 * @throws NoSuchElementException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void add(ActionEvent actionEvent) throws NoSuchElementException, FileNotFoundException, IOException {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Add User ");
		dialog.setHeaderText("Please enter user name: ");
		dialog.setContentText("User name :");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			// check has same data or not
			if (result.get().equalsIgnoreCase("admin")){
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning Dialog");
				alert.setContentText("User name can't be 'admin', please try another one.");
				alert.showAndWait();
				return;
			}
			else if (result.get().trim().equals("") || result.get() == null){
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning Dialog");
				alert.setContentText("User name can't be nothing, please try another one.");
				alert.showAndWait();
				return;
			}
			for (int i = 0; i < list.size(); i++) {
				if (user.get(i).equals(result.get())) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning Dialog");
					alert.setContentText("User name already exists, please try another one.");
					alert.showAndWait();
					return;
				}
			}
			User u = new User(result.get());
			User.StoreUser(u);
			user.add(result.get());
			list.add(result.get());
			application.fileReaderWriter.writeData(user);
			user = application.fileReaderWriter.readData("./data/UserProfiles/user.txt");
			File f = new File("./data/UserPhotos/" + result.get());
			f.mkdirs();
			obsList = FXCollections.observableArrayList(list);
			FXCollections.sort(obsList);
			listView.setItems(obsList);
			int i = 0;
			for (int j = 0; j < obsList.size(); j ++){
				if (obsList.get(j).equals(result.get())){
					i = j;
					break;
				}
			}
			listView.getSelectionModel().select(i);
		}
	}

	/**
	 * To delete user information.
	 * @param actionEvent
	 * @throws IOException
	 */
	public void delete(ActionEvent actionEvent) throws IOException {
		Button b = (Button) actionEvent.getSource();
		if (b == Delete) {
			int i = listView.getSelectionModel().getSelectedIndex();
			if (i == -1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Alert");
				alert.setHeaderText("You haven't selected a target");

				String content = "please select one or add one to delete";
				alert.setContentText(content);
				alert.showAndWait();
				return;
			} else {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Delete Confirmation");
				alert.setHeaderText("Attention:");
				alert.setContentText("Are you sure to delete this User?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					String deleteData = obsList.get(i);
					for (int j = 0; j < list.size(); j++) {
						if (list.get(j).equals(deleteData)) {
							list.remove(j);
							application.fileReaderWriter.removeCertainLine(deleteData);
							user = application.fileReaderWriter.readData("./data/UserProfiles/user.txt");
						}
					}

					File f = new File("./data/UserProfiles/" +deleteData+ ".txt");
					f.delete();
					f = new File("./data/UserPhotos/" + deleteData);
					f.delete();
					obsList = FXCollections.observableArrayList(list);
					FXCollections.sort(obsList);
					listView.setItems(obsList);
					
				}
			}
		}
	}
	
	/**
	 * To close the admin view.
	 * @param actionEvent
	 * @throws IOException
	 */
	public void close(ActionEvent actionEvent) throws IOException{
		Button b = (Button) actionEvent.getSource();
		if (b == Close){
		Stage adminstage = (Stage) b.getScene().getWindow();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/LogInView.fxml"));
		Pane root = (Pane) loader.load();
		Scene scene = new Scene(root, 320, 200);
		//scene.getStylesheets().add("/gui/application.css");
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login");
		primaryStage.setResizable(false);
		adminstage.close();
		primaryStage.show();
		}
	}

}
