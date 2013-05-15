package com.tutorial.expandListView.Adapter;
import java.util.ArrayList;

import com.example.uibasics.R;
import com.tutorial.expandListView.Classes.ExpandListChild;
import com.tutorial.expandListView.Classes.ExpandListGroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


public class ExpandListAdapter extends  BaseExpandableListAdapter{

	private Context context;
	private ArrayList<ExpandListGroup> groups;
	
	public ExpandListAdapter (Context context, ArrayList<ExpandListGroup> groups)
	{
		 this.context = context;
		 this.groups = groups;
	}
	
	public long getGroupId(int groupPosition)
	{
		return groupPosition;
	}
	
	public View getGroupView(int groupPosition, boolean isLastChild, View view,
			ViewGroup parent) {
		ExpandListGroup group = (ExpandListGroup) getGroup(groupPosition);
		if (view == null) {
			LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inf.inflate(R.layout.expandlist_group_item, null);
		}
		TextView tv = (TextView) view.findViewById(R.id.tvGroup);
		tv.setText(group.getName());
		// TODO Auto-generated method stub
		return view;
	}

	public int getChildrenCount(int groupPosition)
	{
		ArrayList<ExpandListChild> chList = groups.get(groupPosition).getItems ();
		if (chList == null)
			return 0;
		else
			return chList.size ();
	}
	
	public Object getChild(int groupPosition, int childPosition)
	{
	    // TODO Auto-generated method stub
		ArrayList<ExpandListChild> chList = groups.get(groupPosition).getItems();
		return chList.get(childPosition);
	}
	
	public Object getGroup(int groupPosition)
	{
		return groups.get(groupPosition);
	}
	 
	public boolean hasStableIds()
	{
		return true;
	}
	
	public int getGroupCount()
	{
		return groups.size();
	}
	 
	public long getChildId(int groupPosition, int childPosition)
	{
		return childPosition;
	}

	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view,
			ViewGroup parent) {
		ExpandListChild child = (ExpandListChild) getChild(groupPosition, childPosition);
		if (view == null) {
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = infalInflater.inflate(R.layout.expandlist_child_item, null);
		}
		TextView tv = (TextView) view.findViewById(R.id.tvChild);
		tv.setText(child.getName().toString());
		tv.setTag(child.getTag());
		return view;
	}
		
	public boolean isChildSelectable(int arg0, int arg1)	
	{
		return true;
	}
}
