package com.example.cabpool;

import android.provider.BaseColumns;

public class Database {
	
	public Database()
	{
		
	}
	
	public static abstract class DataInfo implements BaseColumns
	{
		
		public static final String USER_NAME = "username";
		public static final String USER_DESTINATION = "destination";
		public static final String TIME = "time";
		public static final String VEHICLE = "vehicle";
		public static final String SEAT_AVAILABLE="seats";
		public static final String DATABASE_NAME="userInfo";
		public static final String TABLE_NAME="driverInfo";
	}

}
