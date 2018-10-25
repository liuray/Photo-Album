package views;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import models.Album;
import models.User;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The user controller
 * @author Jiwei Chen
 * @author Pengrui Liu
 *
 */
public class UserController {
	@FXML
	Button Add, Delete, Rename, PhotoSearcher, Exit;
	@FXML
	ListView<String> AlbumList;

	protected User user;
	private ObservableList<String> obsList;
	List<String> list;
	ArrayList<Album> albums;

	/**
	 * TO load albums in list view
	 */
	public void start() {
		albums = user.getAlbums();
		list = new ArrayList<String>();
		if (albums != null){
			for (int i = 0; i < albums.size(); i++) {
				list.add(albums.get(i).getName());
			}
				
			obsList = FXCollections.observableArrayList(list);
			FXCollections.sort(obsList);
			AlbumList.setItems(obsList);
			
		}
		Delete.setDisable(true);
		Rename.setDisable(true);
	}
	//if donot click, the button is on
	/**
	 * The mouse event
	 * @param mouseEvent Mouse event
	 * @throws Exception
	 */
	public void setDisability(MouseEvent mouseEvent) throws Exception {
		if(AlbumList.getSelectionModel().getSelectedIndex() != -1 ){
		Delete.setDisable(false);
		Rename.setDisable(false);
		}else{
			Delete.setDisable(true);
			Rename.setDisable(true);
		}
		if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
			if(mouseEvent.getClickCount() == 2){
				if (AlbumList.getSelectionModel().getSelectedIndex()==-1){
					return;
				}
				Stage s = new Stage();
				AlbumController ac = new AlbumController();
				Album a = new Album("");
				for (int i = 0; i < user.getAlbums().size(); i ++){
					if (user.getAlbums().get(i).
							getName().
							equals(list.get(AlbumList.getSelectionModel().getSelectedIndex()))){
						a = user.getAlbums().get(i);
						break;
					}
				}
				AlbumController.u = user;
				AlbumController.album = a;
				ac.albumName = list.get(AlbumList.getSelectionModel().getSelectedIndex());
				AlbumController.ab = ac;

				ac.start(s);
				
			}
		}
		
	}

	/**
	 * The action is to add album.
	 * @param e Action event
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void add(ActionEvent e) throws FileNotFoundException, IOException {
		if (e.getSource() instanceof Button) {
			Button b = (Button) e.getSource();
			if (b == Add) {
				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Album Creation");
				dialog.setHeaderText("Please name the new album: ");
				dialog.setContentText("Album name :");
				Optional<String> result = dialog.showAndWait();
				if (!result.isPresent()){
					return;
				}
				if (isExisted(result.get()) ) {					
					do{
						dialog = new TextInputDialog();
					    dialog.setTitle("Album Creation");
					    dialog.setHeaderText("Sorry, it's existing in your account "
					    + "please try another one: ");
					    dialog.setContentText("New name :");
					    result = dialog.showAndWait();
					}
					while (result.get().isEmpty() || isExisted(result.get()));					
				}
				if (result.isPresent()) {
					File f = new File("./data/UserPhotos/" + user.getID() + "/" + result.get());
					f.mkdirs();
					Album a = new Album(result.get());
					user.getAlbums().add(a);
					list.add(albums.get(user.getAlbums().size() - 1).getName());
					obsList = FXCollections.observableArrayList(list);
					FXCollections.sort(obsList);
					AlbumList.setItems(obsList);
					AlbumList.getSelectionModel().select(selectItem(result.get()));
					Delete.setDisable(false);
					Rename.setDisable(false);
					User.StoreUser(user);
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Added");
					alert.setContentText("The album has been added.");
					alert.showAndWait();
					return;
				}
			}
			return;
		}
	}

	/**
	 * The action is to delete album.
	 * @param e Action event
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void delete(ActionEvent e) throws FileNotFoundException, IOException {
		if (e.getSource() instanceof Button) {
			Button b = (Button) e.getSource();
			if (b == Delete) {
				int i = AlbumList.getSelectionModel().getSelectedIndex();
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText("Are you sure to delete this album? ");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					
					for (int j = 0; j < user.getAlbums().size(); j++) {
						if (user.getAlbums().get(j).getName().equals(obsList.get(i))) {
							File f = new File("./data/UserPhotos/" + user.getID() +"/" + obsList.get(i));
							f.delete();
							user.getAlbums().remove(j);							
							break;
						}
					}
					for (int k = 0; k < list.size(); k ++){
						if (list.get(k).equals(obsList.get(i))){
							list.remove(k);
						}
					}
					
					obsList = FXCollections.observableArrayList(list);
					FXCollections.sort(obsList);
					AlbumList.setItems(obsList);
					Delete.setDisable(true);
					Rename.setDisable(true);
					User.StoreUser(user);
					
					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Deleted");
					alert.setContentText("The selected album has been deleted.");
					alert.showAndWait();
					return;
				} else {
					return;
				}
			}
		}
	}

	/**
	 * The action is to rename album.
	 * @param e Action even
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void Rename(ActionEvent e) throws FileNotFoundException, IOException {
		if (e.getSource() instanceof Button) {
			Button b = (Button) e.getSource();
			if (b == Rename) {
				int i = AlbumList.getSelectionModel().getSelectedIndex();
				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Album Rename");
				dialog.setHeaderText("Please enter the new name of the album: ");
				dialog.setContentText("New name :");
				Optional<String> result = dialog.showAndWait();
				if (isExisted(result.get())) {					
					do{
						dialog = new TextInputDialog();
					    dialog.setTitle("Album Creation");
					    dialog.setHeaderText("Sorry, it's existing in your account "
					    + "please try another one: ");
					    dialog.setContentText("New name :");
					    result = dialog.showAndWait();
					}
					while (result.get().isEmpty() || isExisted(result.get()));					
				}
				if (result.isPresent()) {
					
					for (int k = 0; k < user.getAlbums().size(); k++) {
						if (user.getAlbums().get(k).getName().equals(obsList.get(i))) {
							File oldName = 
									new File("./data/UserPhotos/" + user.getID() +"/" + obsList.get(i));
							File newName = 
									new File("./data/UserPhotos/" + user.getID() +"/" + result.get());
							oldName.renameTo(newName);
							user.getAlbums().get(k).setAlbumName(result.get());
							list.set(k, result.get());
							break;
						}
					}

					obsList = FXCollections.observableArrayList(list);
					FXCollections.sort(obsList);
					AlbumList.setItems(obsList);
					AlbumList.getSelectionModel().select(i);
					Delete.setDisable(false);
					Rename.setDisable(false);
					User.StoreUser(user);
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Renamed");
					alert.setContentText("The selected album's name has been edited.");
					alert.showAndWait();
					return;
				} else {
					return;
				}
			}
		}
	}

	/**
	 * To close the user view.
	 * @param actionEvent Action event
	 * @throws IOException
	 */
	public void exit(ActionEvent actionEvent) throws IOException{
		Button b = (Button) actionEvent.getSource();
		if (b == Exit){
		Stage adminstage = (Stage) b.getScene().getWindow();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/LogInView.fxml"));
		Pane root = (Pane) loader.load();
		Scene scene = new Scene(root, 320, 200);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login");
		primaryStage.setResizable(false);
		adminstage.close();
		primaryStage.show();
		}
	}
	/**
	 * The mouse event
	 * @param mouseEvent Mouse event
	 */
	public void handle(MouseEvent mouseEvent){
		
	}
	//get the selectIte Index 
	private int selectItem(String s) {
		for (int i = 0; i < obsList.size(); i++) {
			if (obsList.get(i).equals(s)) {
				return i;
			}
		}
		return 0;
	}
	// check add same name or not 
	/**
	 * 
	 * @param s The string of data
	 * @return	true or false
	 */
	private boolean isExisted(String s) {
		if (user.getAlbums() == null){
			return false;
		}
		for (int i = 0; i < user.getAlbums().size(); i++) {
			if (s.equals(user.getAlbums().get(i).getName())) {
				return true;
			}
		}
		return false;
	}
}