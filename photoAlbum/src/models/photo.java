package models;

import java.io.Serializable;
import java.util.ArrayList;



/**
 * The information of people.
 * @author Jiwei Chen
 * @author Pengrui Liu
 */
public class photo implements Serializable{
	private String date;
	private ArrayList<peopleTag> pts;
	private ArrayList<locationTag> lts;
	private String ImagePosition;
	private String Caption;
	public photo(String date, String i){
		this.date = date;
		ImagePosition = i;
		pts = new ArrayList<peopleTag>();
		lts = new ArrayList<locationTag>();
		Caption = "";
	}
	public void setCaption(String s){
		Caption = s;
	}
	public String getCaption(){
		return Caption;
	}
	public String getImage(){
		return ImagePosition;
	}
	public String getDate(){
		return date;
	}
	public ArrayList<peopleTag> getpeopleTag(){
		return pts;
	}
	public ArrayList<locationTag> getlocationTag(){
		return lts;
	}
	public void addLocationTag(locationTag lt){
		lts.add(lt);
	}

}
