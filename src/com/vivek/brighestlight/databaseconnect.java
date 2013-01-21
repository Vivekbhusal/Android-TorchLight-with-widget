package com.vivek.brighestlight;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class databaseconnect extends Activity {
	
	public static final String KEY_STATUS = "lightstatus";
	private static final String DATABASE_NAME ="dblight";
	private static final String DATABASE_TABLE= "tblight";
	private static final int DATABASE_VERSION = 1;
	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	
	private static class DbHelper extends SQLiteOpenHelper{

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			
			db.execSQL("CREATE TABLE " + DATABASE_TABLE +" ("+
					KEY_STATUS+ " INTEGER );"			
				);
//			db.execSQL("Insert into tblight values(0);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {			
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
		
	}
	
	public databaseconnect(Context c)
	{
		ourContext = c;
	}
	
	public databaseconnect open() throws SQLException{
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
		
	}
	public void close()
	{
		ourHelper.close();
	}

	/************************Putting data to database**************************************************/
	public long change_status(int stat) {  
		
		ContentValues cv = new ContentValues();
		cv.put(KEY_STATUS, stat);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}
	
	/***********************Checking who is the winner of round****************************************/
	public int getstatus()
	{

		Cursor c = ourDatabase.rawQuery("select lightstatus from tblight" , null);
		c.moveToFirst();
				
		int status = c.getInt(0);	
		c.close();
	
		return status;
	}
	
	/***********************Deleting all the records from database**************************************/
	public void delete_database()
	{
			ourDatabase.delete(DATABASE_TABLE, null, null);
			Log.i("Data Deleted", "Data Successfully Deleted");
			
	}
}

