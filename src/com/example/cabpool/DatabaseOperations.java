package com.example.cabpool;

import com.example.cabpool.Database.DataInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOperations extends SQLiteOpenHelper {
	public static final int database_version=1;
	
	public DatabaseOperations(Context context) {
		super(context, DataInfo.DATABASE_NAME, null, database_version);
		Log.d("Databse Operation","DB created");
		// TODO Auto-generated constructor stub
	}
	public String CREATE_QUERY=" CREATE TABLE"+ DataInfo.TABLE_NAME+"("+DataInfo.USER_NAME+"TEXT,"
			+DataInfo.USER_DESTINATION+"TEXT,"+ DataInfo.TIME+"TEXT,"+DataInfo.SEAT_AVAILABLE+"TEXT,"+ DataInfo.VEHICLE+"TEXT);";
	

	

	@Override
	public void onCreate(SQLiteDatabase db) {
	db.execSQL(CREATE_QUERY);
	Log.d("Databse Operation", "table created");

	}

	public void putInfo(DatabaseOperations dop,String name,String destination,String time,String seats,String vehicle)
	{
		SQLiteDatabase SQ=dop.getWritableDatabase();
		ContentValues cv= new ContentValues();
		cv.put(DataInfo.USER_NAME, name);
		cv.put(DataInfo.USER_DESTINATION, destination);
		cv.put(DataInfo.TIME, time);
		cv.put(DataInfo.SEAT_AVAILABLE, seats);
		cv.put(DataInfo.VEHICLE, vehicle);
		long k=SQ.insert(DataInfo.TABLE_NAME, null, cv);
		Log.d("Databse Operation", "one row inserted");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
