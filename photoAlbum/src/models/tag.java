package models;

import java.io.Serializable;

/**
 * The information of tag.
 * @author Jiwei Chen
 * @author Pengrui Liu
 *
 */
public class tag implements Serializable{
	/**
	 * The name of tag
	 */
	private String name;
	/**
	 * To get tag 
	 * @param name The name of tag;
	 */
	public tag(String name){
		this.name = name;
	}
	/**
	 * @return tag
	 */
	public String getName(){
		return name;
	}
}
