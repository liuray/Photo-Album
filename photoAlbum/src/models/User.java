package models;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The user information.
 * @author Jiwei Chen
 * @author Pengrui Liu
 *
 */
public class User implements Serializable {
	/**
	 * Use arrayList to store albums.
	 */
	private ArrayList<Album> albums;
	/**
	 * The user ID.
	 */
	private String ID;


	/**
	 * The user information.
	 * @param ID The user ID.
	 */
	public User(String ID) {
		this.ID = ID;
		albums = new ArrayList<Album>();
	}

	/**
	 * @return ID
	 */
	public String getID() {
		return ID;
	}

	/**
	 * @return Albums
	 */
	public ArrayList<Album> getAlbums() {
		return albums;
	}

	/**
	 * To add the albums
	 * @param a The Album.
	 */
	public void addAlbums(Album a) {
		albums.add(a);
	}

	/**
	 * The store  information.
	 * @param u The user 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void StoreUser(User u) throws FileNotFoundException, IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./data/file/" + u.ID + ".txt"));
		oos.writeObject(u);
	}

	/**
	 * The read information.
	 * @param ID The user ID.
	 * @return The user.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static User ReadUser(String ID) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./data/file/" + ID + ".txt"));
		User u = (User) ois.readObject();
		return u;
	}

}
