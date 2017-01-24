package com.easygoal.achieve;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;  
public class TaskRecord extends SQLiteOpenHelper {  
	public static String DATABASE_NAME = "todo_db";  
	private final static int DATABASE_VERSION = 1;
	public final static String TABLE_NAME_TaskRecord = "task_record"; 
	public final static String RECORD_ID = "_id"; 
	public final static String TASK_ID = "_id_task";
	public final static String RECORD_NO = "record_no"; 
	public final static String RECORD_TIME = "record_time";   
	public final static String RECORD_PROGRESS = "record_progress";
	public final static String RECORD_COMMENTS = "record_comments";
	public final static String RECORD_ACHIEVED = "record_achieved"; 
	public final static String RECORD_ENJOYMENT = "record_enjoyment"; 
	public final static String RECORD_EXPERIENCE = "record_experience"; 
	public final static String RECORD_TOTALCOUNT = "record_totalcount";
	
	
	
	  public TaskRecord(Context context, String name, CursorFactory factory,   
              int version) {   
      super(context, name, factory, version); 
      DATABASE_NAME=name;
      Log.d("数据库", "成功启动"+name);
}     
	@Override  public void onCreate(SQLiteDatabase db) { 
		Log.d("create table", "start sql");  
		/* 建立table */    String sql_createTaskRecord = "CREATE TABLE  " + 
	TABLE_NAME_TaskRecord + " (" + 
	RECORD_ID       + " INTEGER primary key autoincrement, " + 
	TASK_ID         + " text, " + 
	RECORD_NO         + " text, " + 
	RECORD_TIME         + " text, " +
	RECORD_PROGRESS    + " text,"+
	RECORD_COMMENTS    + " text,"+
	RECORD_ACHIEVED        + " text,"+
	RECORD_ENJOYMENT        + " text,"+
	RECORD_EXPERIENCE     + " text,"+
	RECORD_TOTALCOUNT     + " text);";
		db.execSQL(sql_createTaskRecord ); 
		Log.d("create table", "create table");  
 }
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}	
	/*@Override  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {    
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;    
		db.execSQL(sql);    onCreate(db);  }    
	public Cursor select() {    
		SQLiteDatabase db = this.getReadableDatabase();    
		Cursor cursor = db        .
				query(TABLE_NAME, null, null, null, null, null, null);    
		return cursor;  }  
	
}
	public long insert(String text) {    
		SQLiteDatabase db = this.getWritableDatabase();    //将新增的值放入ContentValues  
		ContentValues cv = new ContentValues();    
		cv.put(FIELD_TEXT, text);    
		long row = db.insert(TABLE_NAME, null, cv);    
		return row;  }    
	public void delete(int id) {    
		SQLiteDatabase db = this.getWritableDatabase();    
		String where = FIELD_id + " = ?";    
		String[] whereValue = { Integer.toString(id) };    
		db.delete(TABLE_NAME, where, whereValue);  }    
	public void update(int id, String text) {    
		SQLiteDatabase db = this.getWritableDatabase();    
		String where = FIELD_id + " = ?";    
		String[] whereValue = { Integer.toString(id) };       
		ContentValues cv = new ContentValues();    
		cv.put(FIELD_TEXT, text);    
		db.update(TABLE_NAME, cv, where, whereValue);  }}
		*/