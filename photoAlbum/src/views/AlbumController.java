package views;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;

import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.BorderPane;

import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import javafx.stage.Stage;
import javafx.stage.Window;
import models.Album;
import models.User;
import models.locationTag;
import models.peopleTag;
import models.photo;

/**
 * The album controller.
 * @author Jiwei Chen
 * @author Pengrui Liu
 *
 */
public class AlbumController {
	@FXML
	Button add, delete, move, addTag, DeleteTag, Caption;
	@FXML
	ListView<String> listView;
	Stage stage;
	protected static String albumName;
	public static User u;
	public static Album album;
	public static AlbumController ab;
	private TilePane tile;
	private static ImageView iv;
	private static BorderPane bo;
	private static List<String> list1, list2;
	private static ObservableList obsList;

	/**
	 * The action of delete tag.
	 * @param e Action event
	 * @throws Exception
	 */
	public void deleteTag(ActionEvent e) throws Exception {
		if (e.getSource() instanceof Button) {

			Button b = (Button) e.getSource();
			if (b == DeleteTag) {

				if (list1.get(0).equals("Location: ") && list1.get(1).equals("People: ")) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Nothing to delete");
					alert.setContentText("There's no tag can be deleted.");
					alert.showAndWait();

					return;
				}
				List<String> choices = new ArrayList<>();
				String s1 = list1.get(0).substring(list1.get(0).indexOf(" ") + 1);
				String s2 = list1.get(1).substring(list1.get(1).indexOf(" ") + 1);
				String[] sl = s1.split(", ");
				String[] sp = s2.split(", ");
				for (String ss: sl){
					choices.add(ss);
				}
				for (String ss: sp){
					choices.add(ss);
				}
				ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
				dialog.setTitle("TagDeleting");
				dialog.setContentText("Choose a tag you'd like to delete:");
				Optional<String> result = dialog.showAndWait();
				if (!result.isPresent()) {
					return;
				}
				else {
					System.out.println(result.get());
					if (list1.get(0).contains(result.get())){
						for (int i = 0; i < album.getPhotos().size(); i ++){
							if (album.getPhotos().get(i).getImage().equals(
									iv.getImage().impl_getUrl().substring(iv.getImage().impl_getUrl().indexOf(".")))){
								for (int j = 0 ; j < album.getPhotos().get(i).getlocationTag().size(); j ++){
									if (result.get().equals(album.getPhotos().get(i).getlocationTag().get(j))){
										album.getPhotos().get(i).getlocationTag().remove(j);
										list2 = new ArrayList<>();
										if (!list1.get(0).substring(list1.get(0).indexOf(result.get())).contains(",")){
											list2.add(list1.get(0).substring(0,list1.get(0).indexOf(result.get())-2));
											list2.add(list1.get(1));
											list2.add(list1.get(2));
											list1.clear();
											list1.add(list2.get(0));
											list1.add(list2.get(1));
											list1.add(list2.get(2));
											obsList = FXCollections.observableArrayList(list1);
											break;
										}
										else {
											String st = list1.get(0).substring(0, list1.get(0).indexOf(result.get()));
											String ss = list1.get(0).substring(list1.get(0).
													indexOf(result.get())+result.get().length()+2 );
											list2.add(st+ss);
											list2.add(list1.get(1));
											list2.add(list1.get(2));
											list1.clear();
											list1.add(list2.get(0));
											list1.add(list2.get(1));
											list1.add(list2.get(2));
											obsList = FXCollections.observableArrayList(list1);
											break;
										}
									}
									
								}
								listView = new ListView();
								listView.setItems(obsList);
								listView.setPrefHeight(80);
								listView.getSelectionModel().select(0);
								bo.setBottom(listView);
								User.StoreUser(u);

								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Successfully deleted");
								alert.setContentText("The tag has been deleted.");
								alert.showAndWait();

								return;
							}
						}
						
					}
					else if (list1.get(1).contains(result.get())){
						for (int i = 0; i < album.getPhotos().size(); i ++){
							if (album.getPhotos().get(i).getImage().equals(
									iv.getImage().impl_getUrl().substring(iv.getImage().impl_getUrl().indexOf(".")))){
								for (int j = 0 ; j < album.getPhotos().get(i).getpeopleTag().size(); j ++){
									if (result.get().equals(album.getPhotos().get(i).getpeopleTag().get(j))){
										album.getPhotos().get(i).getpeopleTag().remove(j);
										list2 = new ArrayList<>();
										if (!list1.get(1).substring(list1.get(1).indexOf(result.get())).contains(",")){
											
											list2.add(list1.get(0));
											list2.add(list1.get(1).substring(0,list1.get(0).indexOf(result.get())-2));
											list2.add(list1.get(2));
											list1.clear();
											list1.add(list2.get(0));
											list1.add(list2.get(1));
											list1.add(list2.get(2));
											obsList = FXCollections.observableArrayList(list1);
											break;
										}
										else {
											String st = list1.get(1).substring(0, list1.get(1).indexOf(result.get()));
											String ss = list1.get(1).substring(list1.get(1).
													indexOf(result.get())+result.get().length()+2 );
											
											list2.add(list1.get(0));
											list2.add(st+ss);
											list2.add(list1.get(2));
											list1.clear();
											list1.add(list2.get(0));
											list1.add(list2.get(1));
											list1.add(list2.get(2));
											obsList = FXCollections.observableArrayList(list1);
											break;
										}
									}
									
								}
								listView = new ListView();
								listView.setItems(obsList);
								listView.setPrefHeight(80);
								listView.getSelectionModel().select(0);
								bo.setBottom(listView);
								User.StoreUser(u);

								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Successfully deleted");
								alert.setContentText("The tag has been deleted.");
								alert.showAndWait();

								return;
								
							}
						}
					}
				}

			}
		}
	}

	/**
	 * The action of add caption.
	 * @param e Acrion event
	 * @throws Exception
	 */
	public void Caption(ActionEvent e) throws Exception {
		if (e.getSource() instanceof Button) {
			Button b = (Button) e.getSource();
			if (b == Caption) {
				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Photo Caption");
				dialog.setHeaderText("please enter the caption");
				dialog.setContentText("Content: ");
				Optional<String> result = dialog.showAndWait();
				if (result == null) {
					return;
				}
				if (result.isPresent()) {

					for (int i = 0; i < album.getPhotos().size(); i++) {
						if (album.getPhotos().get(i).getImage().equals(
								iv.getImage().impl_getUrl().substring(iv.getImage().impl_getUrl().indexOf(".")))) {
							album.getPhotos().get(i).setCaption(result.get());
							list2 = new ArrayList<String>();
							list2.add(list1.get(0));
							list2.add(list1.get(1));
							list2.add("Caption: " + result.get());
							list1.clear();
							list1.add(list2.get(0));
							list1.add(list2.get(1));
							list1.add(list2.get(2));
							obsList = FXCollections.observableArrayList(list1);

							listView = new ListView();
							listView.setItems(obsList);
							listView.setPrefHeight(80);
							listView.getSelectionModel().select(2);
							bo.setBottom(listView);

							User.StoreUser(u);

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Successfully added");
							alert.setContentText("The tag has been added.");
							alert.showAndWait();
							return;
						}
					}
				} else
					return;
			}
			return;
		}
	}

	/**
	 * The action of add tag.
	 * @param e Action event
	 * @throws Exception
	 */
	public void addTag(ActionEvent e) throws Exception {
		if (e.getSource() instanceof Button) {
			Button b = (Button) e.getSource();
			if (b == addTag) {
				List<String> choices = new ArrayList<>();
				choices.add("LocationTag");
				choices.add("PeopleTag");
				ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
				dialog.setTitle("TagAdding");
				dialog.setContentText("Choose a type of tag you'd like to add on :");
				Optional<String> result = dialog.showAndWait();
				if (result == null) {
					return;
				}
				if (result.isPresent()) {
					if (result.get().equals("LocationTag")) {
						TextInputDialog d = new TextInputDialog("");
						d.setTitle("Add LocationTag");
						d.setHeaderText("Please enter the location: ");
						d.setContentText("Location :");
						result = d.showAndWait();
						if (result == null) {
							return;
						}
						for (int i = 0; i < album.getPhotos().size(); i++) {
							if (album.getPhotos().get(i).getImage().equals(
									iv.getImage().impl_getUrl().substring(iv.getImage().impl_getUrl().indexOf(".")))) {
								for (locationTag lt : album.getPhotos().get(i).getlocationTag()) {
									if (result.get().equals(lt.getName())) {
										Alert alert = new Alert(AlertType.INFORMATION);
										alert.setTitle("Redundant!");
										alert.setContentText("The Tag already exists, please try another one.");
										alert.showAndWait();
										return;
									}
								}
								list2 = new ArrayList<String>();
								album.getPhotos().get(i).addLocationTag(new locationTag(result.get()));
								list2.add(result.get());
								List<String> list3 = new ArrayList<String>();
								if (list1.get(0).equals("Location: ")) {
									list3.add(list1.get(0) + list2.get(0));
									list3.add(list1.get(1));
									list3.add(list1.get(2));
									list1.clear();
									list1.add(list3.get(0));
									list1.add(list3.get(1));
									list1.add(list3.get(2));
								} else {
									list3.add(list1.get(0) + ", " + list2.get(0));
									list3.add(list1.get(1));
									list3.add(list1.get(2));
									list1.clear();
									list1.add(list3.get(0));
									list1.add(list3.get(1));
									list1.add(list3.get(2));
								}

								obsList = FXCollections.observableArrayList(list1);

								listView = new ListView();
								listView.setItems(obsList);
								listView.setPrefHeight(80);
								listView.getSelectionModel().select(0);
								bo.setBottom(listView);
								User.StoreUser(u);

								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Successfully added");
								alert.setContentText("The tag has been added.");
								alert.showAndWait();

								return;
							}
						}
						return;

					} else if (result.get().equals("PeopleTag")) {
						TextInputDialog d = new TextInputDialog("");
						d.setTitle("Add PeopleTag");
						d.setHeaderText("Please enter the people name: ");
						d.setContentText("Name :");
						result = d.showAndWait();
						if (result == null) {
							return;
						}
						for (int i = 0; i < album.getPhotos().size(); i++) {
							if (album.getPhotos().get(i).getImage().equals(
									iv.getImage().impl_getUrl().substring(iv.getImage().impl_getUrl().indexOf(".")))) {
								for (peopleTag pt : album.getPhotos().get(i).getpeopleTag()) {
									if (result.get().equals(pt.getName())) {
										Alert alert = new Alert(AlertType.INFORMATION);
										alert.setTitle("Redundant!");
										alert.setContentText("The people name already exists");
										alert.showAndWait();
										return;
									}
								}
								list2 = new ArrayList<String>();
								album.getPhotos().get(i).getpeopleTag().add(new peopleTag(result.get()));
								list2.add(result.get());
								List<String> list3 = new ArrayList<String>();
								list3.add(list1.get(0));
								if (list1.get(1).equals("People: ")) {
									list3.add(list1.get(1) + list2.get(0));
									list3.add(list1.get(2));
									list1.clear();
									list1.add(list3.get(0));
									list1.add(list3.get(1));
									list1.add(list3.get(2));
								} else {
									list3.add(list1.get(1) + ", " + list2.get(0));
									list3.add(list1.get(2));
									list1.clear();
									list1.add(list3.get(0));
									list1.add(list3.get(1));
									list1.add(list3.get(2));
								}

								obsList = FXCollections.observableArrayList(list1);
								listView = new ListView();
								listView.setItems(obsList);
								listView.setPrefHeight(80);
								listView.getSelectionModel().select(1);
								bo.setBottom(listView);
								User.StoreUser(u);

								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Successfully added");
								alert.setContentText("The tag has been added.");
								alert.showAndWait();

								return;
							}
						}
						return;
					}
				}
			}
		}
	}

	/**
	 * The start primary stage of album.
	 * @param primaryStage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/albumView.fxml"));
		BorderPane bp = (BorderPane) loader.load();
		Scene scene = new Scene(bp, 500, 400);
		primaryStage.setScene(scene);
		primaryStage.setTitle(albumName);
		primaryStage.setResizable(true);
		ScrollPane root = new ScrollPane();
		tile = new TilePane();
		root.setStyle("-fx-background-color: DAE6F3;");
		tile.setPadding(new Insets(15, 15, 15, 15));
		tile.setHgap(15);
		tile.setVgap(15);

		for (final photo p : album.getPhotos()) {
			File file = new File(p.getImage());
			tile.getChildren().addAll(createImageView(file));
		}

		root.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Horizontal
		root.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Vertical
																	// scroll
																	// bar
		root.setFitToWidth(true);
		root.setContent(tile);
		tile.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent m) {
				if (m.getButton().equals(MouseButton.PRIMARY)) {
					if (m.getClickCount() == 1) {
						if (m.getTarget() instanceof ImageView) {
							iv = (ImageView) m.getTarget();
						}
					}
				}
			}

		});
		primaryStage.setWidth(530);
		primaryStage.setHeight(600);
		primaryStage.setScene(scene);
		bp.setCenter(root);
		primaryStage.show();
	}

	/**
	 * The action of to  add images
	 * @param e Action event
	 * @throws Exception
	 */
	public void add(ActionEvent e) throws Exception {
		if (e.getSource() instanceof Button) {

			Button b = (Button) e.getSource();
			if (b == add) {

				FileChooser chooser = new FileChooser();
				chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"));
				Window chooseStage = null;
				File file = chooser.showOpenDialog(chooseStage);
				if (file == null) {
					return;
				}
				File a = new File("./data/UserPhotos/" + u.getID() + "/" + albumName + "/" + file.getName());

				if (a.exists()) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Existed");
					alert.setContentText("The photo already exists in this album! please rename or pick another one");
					alert.showAndWait();
					return;
				}
				Files.copy(file.toPath(), a.toPath());
				Image image = new Image(file.toURI().toString());
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Calendar calender = Calendar.getInstance();
				calender.set(calender.MILLISECOND, 0);
				String time = dateFormat.format(calender.getTime());
				photo p = new photo(time, "./data/UserPhotos/" + u.getID() + "/" + albumName + "/" + file.getName());
				album.getPhotos().add(p);
				User.StoreUser(u);
				ab.stage.close();
				Stage s = new Stage();
				ab = new AlbumController();
				ab.start(s);
			}
		}
	}

	/**
	 * To create image view.
	 * @param imageFile image  
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private ImageView createImageView(final File imageFile) {
		// DEFAULT_THUMBNAIL_WIDTH is a constant you need to define
		// The last two arguments are: preserveRatio, and use smooth (slower)
		// resizing

		ImageView imageView = null;
		final Image image = new Image(imageFile.toURI().toString());
		imageView = new ImageView(image);
		imageView.setFitWidth(150);
		imageView.setFitHeight(150);
		imageView.fitHeightProperty();
		imageView.fitWidthProperty();
		imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@SuppressWarnings("unchecked")
			@Override
			public void handle(MouseEvent mouseEvent) {

				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

					if (mouseEvent.getClickCount() == 2) {
						try {
							FXMLLoader loader = new FXMLLoader();
							loader.setLocation(getClass().getResource("/views/PhotoViewBottom.fxml"));
							bo = (BorderPane) loader.load();
							BorderPane bp = new BorderPane();
							ImageView imageView = new ImageView();
							Image image = new Image(new FileInputStream(imageFile));

							imageView.setImage(image);
							imageView.prefHeight(50);
							imageView.prefWidth(50);
							imageView.setStyle("-fx-background-color: WHITE");
							imageView.setFitHeight(stage.getHeight() - 110);
							imageView.setPreserveRatio(true);
							imageView.setSmooth(false);
							imageView.setCache(false);
							Pane pane = new Pane();
							bp.setCenter(imageView);
							bp.setBottom(pane);
							bo.setCenter(bp);
							bo.setStyle("-fx-background-color: WHITE");

							for (int i = 0; i < album.getPhotos().size(); i++) {
								if (album.getPhotos().get(i).getImage().equals(iv.getImage().impl_getUrl()
										.substring(iv.getImage().impl_getUrl().indexOf(".")))) {
									list1 = new ArrayList<String>();
									listView = new ListView();
									ArrayList<locationTag> al = album.getPhotos().get(i).getlocationTag();
									ArrayList<peopleTag> ap = album.getPhotos().get(i).getpeopleTag();
									String str = album.getPhotos().get(i).getCaption();
									String st = "Location: ";
									if (al.size() == 0) {
										list1.add(st);
									} else if (al.size() == 1) {
										st = st + al.get(0).getName();
										list1.add(st);
									} else {
										st = st + al.get(0).getName();
										for (int j = 1; j < al.size() - 1; j++) {
											st = st + ", " + al.get(j).getName();
										}
										st = st + ", " + al.get(al.size() - 1).getName();
										list1.add(st);
									}

									st = "People: ";
									if (ap.size() == 0) {
										list1.add(st);
									} else if (ap.size() == 1) {
										st = st + ap.get(0).getName();
										list1.add(st);
									} else {
										st = st + ap.get(0).getName();
										for (int j = 1; j < ap.size() - 1; j++) {
											st = st + ", " + ap.get(j).getName();
										}
										st = st + ", " + ap.get(ap.size() - 1).getName();
										list1.add(st);
									}
									list1.add("Caption: " + str);
									obsList = FXCollections.observableArrayList(list1);
									listView.setItems(obsList);
									break;
								}
							}
							listView.setPrefHeight(80);
							listView.getSelectionModel().select(0);

							bo.setBottom(listView);

							Stage newStage = new Stage();
							newStage.setWidth(1200);
							newStage.setHeight(1000);
							newStage.setTitle(imageFile.getName());
							Scene scene = new Scene(bo, Color.BLUE);
							newStage.setScene(scene);
							newStage.setResizable(true);
							newStage.show();

						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				}
			}
		});
		return imageView;
	}

	/**
	 * The action of move photo
	 * @param e	Action even
	 * @throws Exception
	 */
	public void move(ActionEvent e) throws Exception {
		if (e.getSource() instanceof Button) {
			Button b = (Button) e.getSource();
			if (b == move) {
				List<String> choices = new ArrayList<>();
				for (int i = 0; i < u.getAlbums().size(); i++) {
					choices.add(u.getAlbums().get(i).getName());
				}
				ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
				dialog.setTitle("Album Selection");
				dialog.setContentText("Choose an album :");
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()) {
					String st = iv.getImage().impl_getUrl().substring(iv.getImage().impl_getUrl().indexOf("."));

					if (st.equals("./data/UserPhotos/" + u.getID() + "/" + result.get() + "/"
							+ new File(iv.getImage().impl_getUrl()).getName())) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Moved");
						alert.setContentText("The photo has been successfully moved!");
						User.StoreUser(u);
						((Stage) b.getScene().getWindow()).close();
						ab.stage.close();
						ab = new AlbumController();
						Stage s = new Stage();
						alert.showAndWait();
						ab.start(s);
						return;
					}
					File f = new File(st);
					File a = new File("./data/UserPhotos/" + u.getID() + "/" + result.get() + "/"
							+ new File(iv.getImage().impl_getUrl()).getName());

					Files.copy(f.toPath(), a.toPath());

					for (int i = 0; i < album.getPhotos().size(); i++) {

						if (album.getPhotos().get(i).getImage().equals(st)) {
							album.getPhotos().remove(i);
							break;
						}
					}
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Calendar calender = Calendar.getInstance();
					calender.set(calender.MILLISECOND, 0);
					String time = dateFormat.format(calender.getTime());
					photo p = new photo(time, a.toPath().toString());
					for (int i = 0; i < u.getAlbums().size(); i++) {
						if (u.getAlbums().get(i).getName().equals(result.get())) {
							u.getAlbums().get(i).addPhoto(p);
							break;
						}
					}
					f.delete();
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Moved");
					alert.setContentText("The photo has been successfully moved!");
					User.StoreUser(u);
					((Stage) b.getScene().getWindow()).close();
					ab.stage.close();
					ab = new AlbumController();
					alert.showAndWait();
					Stage s = new Stage();
					ab.start(s);
					return;
				} else {
					return;
				}
			}
		}
	}

	/**
	 * The action of to delete photo.
	 * @param e Action event
	 * @throws Exception
	 */
	public void delete(ActionEvent e) throws Exception {
		if (e.getSource() instanceof Button) {
			Button b = (Button) e.getSource();
			if (b == delete) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Confirmation");
				alert.setContentText("Are you sure to delete this photo from the album?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					ImageView c = iv;

					for (int i = 0; i < album.getPhotos().size(); i++) {
						String s = c.getImage().impl_getUrl().substring(c.getImage().impl_getUrl().indexOf("."));
						if (album.getPhotos().get(i).getImage().equals(s)) {
							File f = new File(s);
							f.delete();
							album.getPhotos().remove(i);
							break;
						}
					}

					User.StoreUser(u);
					ab.stage.close();
					ab = new AlbumController();
					Stage s = new Stage();
					((Stage) b.getScene().getWindow()).close();
					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Deleted");
					alert.setContentText("The photo has been successfully deleted!");
					alert.showAndWait();
					ab.start(s);
					return;
				}
			}
		}
	}

}
