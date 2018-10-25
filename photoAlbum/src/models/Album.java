package models;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * To add and store photo.
 * @author Jiwei Chen
 * @author Pengrui liu
 */
public class Album implements Serializable{
	private ArrayList<photo> content;
	private String AlbumName;
	/**
	 * To store the album.
	 * @param Name name of album
	 */
	public Album(String Name){
		AlbumName = Name;
		content = new ArrayList<photo>();
	}
	/**
	 * To add photo 
	 * @param i photo
	 */
	public void addPhoto(photo i){
		content.add(i);
	}
	/**
	 * To store album
	 * @param newName name of album
	 */
	public void setAlbumName(String newName){
		AlbumName = newName;
	}
	/**
	 * 
	 * @return Album
	 */
	public String getName(){
		return AlbumName;
	}
	
	public ArrayList<photo> getPhotos(){
		return content;
	}
	
}
