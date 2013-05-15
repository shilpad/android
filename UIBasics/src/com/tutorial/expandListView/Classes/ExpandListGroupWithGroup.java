package com.tutorial.expandListView.Classes;

import java.util.ArrayList;

public class ExpandListGroupWithGroup {
	private String Name;
	private ArrayList<ExpandListGroupWithImage> Items;

	public String getName ()
	{
		return Name;
	}
	
	public void setName (String name)
	{
		this.Name = name;
	}
	
	public ArrayList<ExpandListGroupWithImage> getItems ()
	{
		return Items;
	}
	
	public void setItems (ArrayList<ExpandListGroupWithImage> items)
	{
		this.Items = items;
	}	
}
