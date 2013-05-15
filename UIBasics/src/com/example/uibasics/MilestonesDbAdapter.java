package com.example.uibasics;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.Classes.Milestone_information;

import android.R.string;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/*
 * Table: profile
 * Columns: _id integer primary key autoincrement
 *          name string not null
 * 			date integer not null
 * 			month integer not null
 * 			year integer not null
 *  	    gender character 
 * 			image blob
 * */
public class MilestonesDbAdapter {
	
	public static ArrayList<Milestone_information> glstMilestoneInformation = new ArrayList<Milestone_information>();

	// profile table
	public static final String KEY_NAME = "name";
    public static final String KEY_BIRTHDATE = "date";
    public static final String KEY_BIRTHMONTH = "month";
    public static final String KEY_BIRTHYEAR = "year";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_PICTURE = "picture";
    
    //information table
    public static final String KEY_PROFILEID = "profile_id";
    public static final String KEY_DATE= "date";
    public static final String KEY_CAPTION = "caption";
    public static final String KEY_NOTES ="notes";
    public static final String KEY_MEDIAPATH = "mediapath";
    
    //tags table
    public static final String KEY_TAGID = "tag_id";
    public static final String KEY_TAGNAME = "tag_name";
    public static final String KEY_CUSTOMTAG = "custom_tag";
 
    //tagged_information table
    //public static final String KEY_PROFILEID = "profile_id";
    public static final String KEY_INFORMATIONID= "information_id";
    //public static final String KEY_TAGID = "tag_id";
    public static final String KEY_TAGVALUE= "tag_value";
 
    private static final String TAG = "MilestonesDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    
    /**
     * Database creation sql statement
     */
    
    private static final String DATABASE_NAME = "milestones";
    private static final String DATABASE_TABLE_PROFILE = "profile";
    private static final String DATABASE_TABLE_INFORMATION = "information";
    private static final String DATABASE_TABLE_TAGGED_INFORMATION = "tagged_information";
    private static final String DATABASE_TABLE_TAGS = "tags";
    
/*   private static final String DATABASE_CREATE =
    		"create table profile (_id integer primary key autoincrement, "
        + "name text not null, date integer not null, month integer not null, year integer not null,
         gender text null, picture BLOB);";*/
 
    private static final String CREATE_TABLE_PROFILE =
    		"create table profile (profile_id integer primary key autoincrement, "
        + "name text not null, date integer not null, month integer not null, year integer not null," +
        " gender text null, picture text not null);";
  
    
    private static final String CREATE_TABLE_INFORMATION =
    		"create table information (information_id integer primary key autoincrement, "
        + "profile_id integer not null, date text not null, caption text not null, notes text null, mediapath text null);";

    private static final String CREATE_TABLE_TAGGED_INFORMATION =
    		"create table " + DATABASE_TABLE_TAGGED_INFORMATION + "(_id integer primary key autoincrement, "
        + "profile_id integer not null, information_id integer not null, tag_id integer not null," +
        "tag_value text not null);";
  
    // tag_name could be the date too ! i.e actual date or 1st bday, 2nd bday etc
    private static final String CREATE_TABLE_TAGS =
    		"create table tags (tag_id integer primary key autoincrement, "
        + "tag_name text not null, custom_tag integer not null);";
    
    private static final int DATABASE_VERSION = 2;

    private Context mCtx = null; 

    private static class DatabaseHelper extends SQLiteOpenHelper {

    	
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_PROFILE);
            db.execSQL(CREATE_TABLE_INFORMATION);
            db.execSQL(CREATE_TABLE_TAGGED_INFORMATION);
            db.execSQL(CREATE_TABLE_TAGS);
            
            ContentValues initialValues = new ContentValues();
            initialValues.put(KEY_TAGNAME, "First");
            initialValues.put(KEY_CUSTOMTAG, 0);
            
            db.insert(DATABASE_TABLE_TAGS, null, initialValues); 
            initialValues.clear();
            
            initialValues.put(KEY_TAGNAME, "School");
            initialValues.put(KEY_CUSTOMTAG, 0);
            
            db.insert(DATABASE_TABLE_TAGS, null, initialValues); 
            initialValues.clear();
            
            initialValues.put(KEY_TAGNAME, "Birthdate");
            initialValues.put(KEY_CUSTOMTAG, 0);
            
            db.insert(DATABASE_TABLE_TAGS, null, initialValues); 
            initialValues.clear();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS profile");
            onCreate(db);
        }
    }
    
    /**
	 * Constructor - takes the context to allow the database to be
	 * opened/created
	 * 
	 * @param ctx the Context within which to work
	 */
	public MilestonesDbAdapter(Context ctx) {
	    this.mCtx = ctx;
	}
	
    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public MilestonesDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }
    
    /**
     * Create a new entry profile using the details provided. If the profile is
     * successfully created return the new rowId for that note, otherwise return
     * a -1 to indicate failure.
     * 
     * @param name of the person
     * @param date, month and year
     * @param gender
     * @param picturePath
     * @return rowId or -1 if failed
     */
    public long createProfile (String name, Integer date, Integer month, Integer year,
    		String gender, String picturePath) {
    	    
    	//mDb.execSQL ("drop table profile");
    	//mDb.execSQL(DATABASE_CREATE);
        
    	ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_BIRTHDATE, date);
        initialValues.put(KEY_BIRTHMONTH, month);
        initialValues.put(KEY_BIRTHYEAR, year);
        initialValues.put (KEY_GENDER, gender);
        initialValues.put (KEY_PICTURE, picturePath);
        
        long retVal = 0;
        retVal =  mDb.insert(DATABASE_TABLE_PROFILE, null, initialValues); 
        return retVal;
        
       /* try
        {
        	 byte[] byteImage1 = null;
        	 FileInputStream instream = new FileInputStream(picturePath); 
        	 BufferedInputStream bif = new BufferedInputStream(instream); 
        	 byteImage1 = new byte[bif.available()]; 
        	 bif.read(byteImage1); 
        	 initialValues.put(KEY_PICTURE, byteImage1);

        	 retVal =  mDb.insert(DATABASE_TABLE, null, initialValues); 
        	 if(retVal < 0) 
        	 {
             	Log.wtf(TAG, "\r\n!!! Error adding blob field!!!\r\n" + " while adding name " + name + " date "
                     + date);
        	 }
             	
        } catch (IOException e) 
        {
            Log.wtf(TAG, "\r\n!!! Error: " + e+"!!!\r\n" + " while adding name " + name + " date "
                    + date);
        }
        
       return retVal;*/
    }

    public long updateProfile (int profileId, String name, Integer date, Integer month, Integer year,
    		String gender, String picturePath) {
    	
    	Toast.makeText (mCtx, "ProfileId=" + profileId, Toast.LENGTH_SHORT).show();
    	
    	ContentValues newValues = new ContentValues();
    	newValues.put(KEY_NAME, name);
    	newValues.put(KEY_BIRTHDATE, date);
    	newValues.put(KEY_BIRTHMONTH, month);
    	newValues.put(KEY_BIRTHYEAR, year);
    	newValues.put (KEY_GENDER, gender);
    	
    	if (picturePath != null)
    		newValues.put (KEY_PICTURE, picturePath);
        
    	String where = "_id=?"; // The where clause to identify which columns to update.
    	String[] value = { String.valueOf(profileId) }; 
    	
    	Toast.makeText(mCtx, "Value=" + value, Toast.LENGTH_SHORT).show();
        long retVal = 0;
        retVal =  mDb.update(DATABASE_TABLE_PROFILE, newValues, where, value); 
        return retVal;
    }
    
    /**
     * Return a Cursor over the list of all profiles in the database
     * 
     * @return Cursor over all profiles
     */
    public Cursor fetchAllProfiles() {

    	/*mDb.execSQL("drop table tags");
    	mDb.execSQL(CREATE_TABLE_TAGS);
    	  
      	ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TAGNAME, "First");
        initialValues.put(KEY_CUSTOMTAG, 0);
        
        long retVal = 0;
        retVal =  mDb.insert(DATABASE_TABLE_TAGS, null, initialValues); 
        initialValues.clear();
        
        initialValues.put(KEY_TAGNAME, "School");
        initialValues.put(KEY_CUSTOMTAG, 0);
        
        retVal =  mDb.insert(DATABASE_TABLE_TAGS, null, initialValues); 
        initialValues.clear();
        
        initialValues.put(KEY_TAGNAME, "Birthdate");
        initialValues.put(KEY_CUSTOMTAG, 0);
        
        retVal =  mDb.insert(DATABASE_TABLE_TAGS, null, initialValues); 
        initialValues.clear();*/
        
         return mDb.query(DATABASE_TABLE_PROFILE, null, null, null, null, null, null);
        
    }
    
    // Information table
    /**
     * Create a new entry profile using the details provided. If the profile is
     * successfully created return the new rowId for that note, otherwise return
     * a -1 to indicate failure.
     * 
     * @param name of the person
     * @param date, month and year
     * @param gender
     * @param picturePath
     * @return rowId or -1 if failed
     */
    public void createInformation (Integer profileId, String caption, String notes,
    		 String mediaPath, String date, List<String> lstTagNames, List<String> lstTagValues) {
 
    	
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PROFILEID, profileId);
        initialValues.put(KEY_CAPTION, caption);
        initialValues.put(KEY_NOTES, notes);
        initialValues.put(KEY_MEDIAPATH, mediaPath);
        initialValues.put(KEY_DATE, date);
        
        long retVal = 0;
        retVal =  mDb.insert(DATABASE_TABLE_INFORMATION, null, initialValues); 
        
        if (retVal != -1)
        {
        	int informationId = (int) retVal;
        	//continue adding to tags table

        	for (int iTagNumber=0; iTagNumber < lstTagNames.size(); iTagNumber++)
        	{
        		// for each tag name find the corresponding tag_id from tags table and add it
        		// to tagged_information table
        		
        		String tagName = lstTagNames.get(iTagNumber);
        		//Cursor tagCursor = mDb.query(DATABASE_TABLE_TAGS, new String[] {KEY_TAGID}, KEY_TAGNAME + "=?", new String[] {tagName},
        		//		 null, null, null);
        		Cursor tagCursor = mDb.query(DATABASE_TABLE_TAGS, null, KEY_TAGNAME + "=?", new String[] {tagName},
       				 null, null, null);

        		int tagId = -1;
        		if (tagCursor.moveToFirst())
        		{
        			do
        			{
        				int columnIndex = tagCursor.getColumnIndexOrThrow(KEY_TAGID);
        				tagId = tagCursor.getInt(columnIndex);
        				Toast.makeText (mCtx, KEY_TAGID + "=" + tagId, Toast.LENGTH_SHORT).show();
        				
                		// Add this tag_id along with tag_value to the tagged_information table
                		
                		initialValues.clear();
                    	
                		initialValues.put(KEY_PROFILEID, profileId);
                		initialValues.put(KEY_INFORMATIONID, informationId);
                		initialValues.put(KEY_TAGID, tagId);
                		initialValues.put(KEY_TAGVALUE, lstTagValues.get(iTagNumber));
                		retVal = mDb.insert(DATABASE_TABLE_TAGGED_INFORMATION, null, initialValues);
                		if (retVal == -1)
                		{
                			// Log error
                			Toast.makeText(mCtx, "Error adding " + KEY_PROFILEID + " = " + profileId +
                					KEY_INFORMATIONID + " = " + informationId +
                					KEY_TAGID + " = " + tagId + KEY_TAGVALUE+ " = " + lstTagValues.get(iTagNumber) + " to " + DATABASE_TABLE_TAGGED_INFORMATION ,
                	                Toast.LENGTH_SHORT).show();
                	        
                			break;
                		}

        			}while (tagCursor.moveToNext());
        		}
        		
        		if (tagCursor != null && !tagCursor.isClosed())
        		{
        			tagCursor.close();
        		}
        	}
        }
     }
    
    private ArrayList<Integer> getTagsIdsFromTagNames (ArrayList<String> lstTagNames)
    {
    	Cursor tagCursor = mDb.query(DATABASE_TABLE_TAGS, null, null, null, null, null, null);
    	HashMap <String, Integer> mapTags = new HashMap<String, Integer> ();
   		if (tagCursor.moveToFirst())
   		{
   			do
   			{
   				int columnIndex = tagCursor.getColumnIndexOrThrow(KEY_TAGID);
   				int tagId = tagCursor.getInt(columnIndex);
   				
   				columnIndex = tagCursor.getColumnIndexOrThrow(KEY_TAGNAME);
   				String tagName = tagCursor.getString(columnIndex);
   				
   				Toast.makeText (mCtx, KEY_TAGID + "=" + tagId, Toast.LENGTH_SHORT).show();
   				mapTags.put(tagName, tagId);
   			}while (tagCursor.moveToNext());
   		}
   		
   		if (tagCursor != null && !tagCursor.isClosed())
   		{
   			tagCursor.close();
   		}
   		
    	ArrayList<Integer> lstTagIds = new ArrayList<Integer>();
   		for (String tagName : lstTagNames)
   		{
   			Integer tagId = mapTags.get(tagName);
   			if (tagId != null)
   				lstTagIds.add (tagId);
   		}
   		
   		return lstTagIds;
   	}   	
    
    String formatTagIdsForQuery(ArrayList<Integer> lstTags)
    {
    	StringBuffer sb = new StringBuffer ();
        sb.append("(");
        int len = lstTags.size();
        
        for (int index = 0; index < len; index++) {
        	sb.append (lstTags.get(index));
        	//Toast.makeText(mCtx, "TagId=" + lstTags.get(index),
            //        Toast.LENGTH_SHORT).show();
        	
        	if (index != len -1)
        		sb.append(",");
        }
        
        sb.append(")");
    	Toast.makeText(mCtx, "TagIds to String=" + sb.toString(),
                Toast.LENGTH_SHORT).show();
        return sb.toString();
    }

    // (\"1\",  \"2nd Birthday\")
    String formatTagValuesForQuery(ArrayList<String> lstTagValues)
    {
    	StringBuffer sb = new StringBuffer ();
        sb.append("(");
        int len = lstTagValues.size();
        
        for (int index = 0; index < len; index++) {
        	sb.append("\"");
        	sb.append (lstTagValues.get(index));
        	sb.append("\"");
        	
        	if (index != len -1)
        		sb.append(",");
        }
        
        sb.append(")");
    	Toast.makeText(mCtx, "Tags to String=" + sb.toString(),
                Toast.LENGTH_SHORT).show();
        return sb.toString();
    }

    public ArrayList<Milestone_information> fetchMediaPaths (int profileId, ArrayList<String> lstTagNames, ArrayList<String> lstTagValues) 
    {
    	// Select tag_id from tags where tag_name IN ("First","School","Birthdate");
    	//Select distinct information_id from tagged_information where (tag_id=1 and tag_value="1") OR (tag_id=2 and tag_value="1") OR (tag_id=3 and tag_value="2nd Birthday")
    	//SELECT distinct mediapath FROM information WHERE information_id IN (select distinct information_id FROM  tagged_information WHERE tag_id IN (Select distinct tag_id from tags where tag_name="School") AND profile_id = 1)
    	// Select * from tagged_information where tag_id IN (1,2,3) and tag_value IN ("1", "2nd Birthday") GROUP BY information_id having COUNT(information_id)=3	
    	//Select * from tagged_information where information_id IN (select information_id from information 
    	//where profile_id=1) and tag_id IN (1,2,3) and tag_value IN ("1",  "2nd Birthday") GROUP BY information_id having COUNT(information_id)=3
    
    	//Select * from information where profile_id=1 and information_id IN (Select information_id from tagged_information where
    	//tag_id IN (1,2,3) and tag_value IN ("1",  "2nd Birthday") GROUP BY information_id having COUNT(information_id)=3)
    	
    	glstMilestoneInformation.clear();
    	
    	int numberofTags = lstTagNames.size();
    	Toast.makeText(mCtx, "Number of tags=" + numberofTags,
                 Toast.LENGTH_SHORT).show();
        
    	ArrayList<Integer> lstTagIds = getTagsIdsFromTagNames (lstTagNames);
    	
    	Toast.makeText(mCtx, "Number of tag Ids=" + lstTagIds.size(),
                Toast.LENGTH_SHORT).show();
    	
    	String strTagIds = formatTagIdsForQuery(lstTagIds);
    	String strTagValues = formatTagValuesForQuery(lstTagValues);
    	
    	StringBuffer query = new StringBuffer ();
    	query.append ("Select * from information where profile_id=");
    	query.append(profileId);
    	query.append (" and information_id IN (Select information_id from tagged_information where tag_id IN " + strTagIds + " and" +
    			" tag_value IN " + strTagValues +" GROUP BY information_id having COUNT(information_id)=");
    	
    	query.append (numberofTags);
    	query.append (")");
    	Cursor informationCursor = mDb.rawQuery(query.toString(),null);
    	
     	/*Cursor informationCursor = mDb.rawQuery("Select * from information where profile_id=" + profileId + " and " +
    			"information_id IN (Select information_id from tagged_information where tag_id IN (1,2,3) and tag_value IN (\"1\",  \"2nd Birthday\")" + 
    			"GROUP BY information_id having COUNT(information_id)=" + numberofTags + ")", null);*/
    	
    	/*Cursor informationCursor = mDb.rawQuery("SELECT distinct mediapath FROM information WHERE information_id IN (select distinct information_id FROM " +
    			"tagged_information WHERE tag_id IN (Select distinct tag_id from tags where tag_name=?) AND profile_id = ?)",
    			new String[] {"School", "1"});*/
    	
    	if (informationCursor.moveToFirst())
		{
			do
			{
				Milestone_information information = new Milestone_information();
				
				int columnIndex = informationCursor.getColumnIndexOrThrow(KEY_INFORMATIONID);
				int informationId  = informationCursor.getInt(columnIndex);
			
				columnIndex = informationCursor.getColumnIndexOrThrow(KEY_DATE);
				information.setDate(informationCursor.getString(columnIndex));
			
				columnIndex = informationCursor.getColumnIndexOrThrow(KEY_CAPTION);
				information.setCaption(informationCursor.getString(columnIndex));
			
				columnIndex = informationCursor.getColumnIndexOrThrow(KEY_NOTES);
				information.setNotes(informationCursor.getString(columnIndex));
			
				columnIndex = informationCursor.getColumnIndexOrThrow(KEY_MEDIAPATH);
				String mediaPath = informationCursor.getString(columnIndex);
				information.setMediaPath(mediaPath);
			
				Toast.makeText (mCtx,KEY_INFORMATIONID + "=" + informationId +  " " + KEY_MEDIAPATH + "=" + mediaPath, Toast.LENGTH_SHORT).show();
				glstMilestoneInformation.add (information);
			}while (informationCursor.moveToNext());
		}
    
    return glstMilestoneInformation;
    		
}
}