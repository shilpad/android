package com.example.uibasics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*
 * Table: birthdate
 * Columns: _id integer primary key autoincrement
 *          name string not null
 * 			relationship string null
 * 			date integer not null
 * 			month integer not null
 * 			year integer not null
 * */
public class BirthdateDbAdapter {
	
	public static final String KEY_NAME = "name";
	public static final String KEY_RELATIONSHIP = "relationship";
    public static final String KEY_DATE = "date";
    public static final String KEY_MONTH = "month";
    public static final String KEY_YEAR = "year";
    
    private static final String TAG = "BirthdateDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    /**
     * Database creation sql statement
     */
    private static final String DATABASE_CREATE =
    		"create table birthdate (_id integer primary key autoincrement, "
        + "name text not null, relationship text null, date integer not null, month integer not null, year integer not null);";
    
    private static final String DATABASE_NAME = "reminder";
    private static final String DATABASE_TABLE = "birthdate";
    private static final int DATABASE_VERSION = 2;

    private final Context mCtx; 
   
    private static class DatabaseHelper extends SQLiteOpenHelper {

    	
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS notes");
            onCreate(db);
        }
    }

	/**
	 * Constructor - takes the context to allow the database to be
	 * opened/created
	 * 
	 * @param ctx the Context within which to work
	 */
	public BirthdateDbAdapter(Context ctx) {
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
    public BirthdateDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }
    
    /**
     * Create a new entry note using the details provided. If the birthdate is
     * successfully created return the new rowId for that note, otherwise return
     * a -1 to indicate failure.
     * 
     * @param name of the person
     * @param relationship 
     * @param date, month and year
     * @return rowId or -1 if failed
     */
    public long createBirthdate (String name, String relationship, Integer date, Integer month, Integer year) {
    	    
    	ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_RELATIONSHIP, relationship);
        initialValues.put(KEY_DATE, date);
        initialValues.put(KEY_MONTH, month);
        initialValues.put(KEY_YEAR, year);
        
        Log.w(TAG, "name " + name + " date "
                + date);
        
        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * Return a Cursor over the list of all notes in the database
     * 
     * @return Cursor over all notes
     */
    public Cursor fetchAllBirthdates() {

        return mDb.query(DATABASE_TABLE, null, null, null, null, null, null);
    }
}
