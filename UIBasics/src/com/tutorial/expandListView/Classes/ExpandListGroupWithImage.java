package com.tutorial.expandListView.Classes;

import java.util.ArrayList;

public class ExpandListGroupWithImage {
	private String Name;
	private ArrayList<ExpandListChildWithImage> Items;
	
	public String getName ()
	{
		return Name;
	}
	
	public void setName (String name)
	{
		this.Name = name;
	}
	
	public ArrayList<ExpandListChildWithImage> getItems ()
	{
		return Items;
	}
	
	public void setItems (ArrayList<ExpandListChildWithImage> items)
	{
		this.Items = items;
	}
}
