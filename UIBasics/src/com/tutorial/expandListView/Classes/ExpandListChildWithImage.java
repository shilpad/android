package com.tutorial.expandListView.Classes;

public class ExpandListChildWithImage {
	private Integer id;
    private String name;
    private String gender;
	private Integer year;
	private Integer month;
	private Integer date;
	private Object image;
	     
	public String getName() {
		return name;
	}
	public void setName(String name) {
	    this.name = name;
	}
	
	public Object getImage () {
		return image;
	}
	public void setImage (Object image) {
		this.image = image;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getDate() {
		return date;
	}
	public void setDate(Integer date) {
		this.date = date;
	}
	
	/*public void clear () {
		id = -1;
		name = "";
		gender = "";
		year = 0;
		month = 0;
		date = 0;
		image = null;
	}*/
}
