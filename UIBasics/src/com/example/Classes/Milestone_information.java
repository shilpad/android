package com.example.Classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Milestone_information implements Parcelable{
	private String date;
	private String caption;
	private String notes;
	private String mediaPath;
	
	public Milestone_information () {}
	
	public Milestone_information(Parcel in) {
		// TODO Auto-generated constructor stub
	  	date = in.readString();
    	caption = in.readString();
    	notes = in.readString();
    	mediaPath = in.readString();
  }
    
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		 dest.writeString(date);
	     dest.writeString(caption);
	     dest.writeString(notes);
	     dest.writeString(mediaPath);
	}
	
    public static final Parcelable.Creator<Milestone_information> CREATOR
    	= new Parcelable.Creator<Milestone_information>() {
    		public Milestone_information createFromParcel(Parcel in) {
    		return new Milestone_information(in);
    }

    public Milestone_information[] newArray(int size) {
    		return new Milestone_information[size];
    }
    };

    public void setDate (String date) {
    	this.date = date;
    }
    
    public String getDate () {
    	return date;
    }

    public void setCaption (String caption) {
    	this.caption = caption;
    }
    
    public String getCaption () {
    	return caption;
    }
    
    public void setNotes (String notes) {
    	this.notes = notes;
    }
    
    public String getNotes () {
    	return notes;
    }
    
    public void setMediaPath (String mediaPath) {
    	this.mediaPath = mediaPath;
    }
    
    public String getMediaPath () {
    	return mediaPath;
    }  
}
