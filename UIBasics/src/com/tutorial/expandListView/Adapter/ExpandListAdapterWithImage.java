package com.tutorial.expandListView.Adapter;

import java.util.ArrayList;

import com.example.uibasics.R;
import com.tutorial.expandListView.Classes.ExpandListChildWithImage;
import com.tutorial.expandListView.Classes.ExpandListGroupWithImage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandListAdapterWithImage extends BaseExpandableListAdapter{

	private Context context;
	private ArrayList<ExpandListGroupWithImage> groups;

	public ExpandListAdapterWithImage (Context context, ArrayList<ExpandListGroupWithImage> groups)
	{
		 this.context = context;
		 this.groups = groups;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		ArrayList<ExpandListChildWithImage> chList = groups.get(groupPosition).getItems();
		return chList.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		ExpandListChildWithImage child = (ExpandListChildWithImage) getChild(groupPosition, childPosition);
		if (view == null) {
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = infalInflater.inflate(R.layout.expandable_list_item_with_image, null);
		}
		
        TextView tv = (TextView)view.findViewById(R.id.name);
        tv.setText (child.getName().toString());
       
        ImageView imageView = (ImageView)view.findViewById (R.id.image);
        imageView.setImageDrawable((Drawable)child.getImage());
  
        return view;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		ArrayList<ExpandListChildWithImage> chList = groups.get(groupPosition).getItems ();
		if (chList == null)
			return 0;
		else
			return chList.size ();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return groups.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		ExpandListGroupWithImage group = (ExpandListGroupWithImage) getGroup(groupPosition);
		if (view == null) {
			LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inf.inflate(R.layout.expandlist_group_item, null);
		}
		TextView tv = (TextView) view.findViewById(R.id.tvGroup);
		tv.setText(group.getName());
		// TODO Auto-generated method stub
		return view;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
