package com.easygoal.achieve;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

public class ConnMySQL {

	private Context context;
	public ConnMySQL(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
	}

	void LitePalDelByGsonArrayRequestPost(String servletName,String tbname,String sn){  
		   // String url = "http://route.showapi.com/213-3";  
		    //String url = "http://192.168.1.100:8080/EasyTest/TaskmainServlet";  
		    //Map<String, String> params = new HashMap<String, String>();  
		    //params.put("taskname", "value1");  
		    //params.put("deadline", "value2"); 
		    String url = TaskData.hostname+servletName;  
			//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
		  
		   RequestQueue requestQueue=Volley.newRequestQueue(context);
		   String queryStr="select * from "+tbname+" where "+"sn"+"="+"'"+sn+"';";
		   //Toast.makeText(context, queryStr, Toast.LENGTH_SHORT).show();
		   Cursor ca=DataSupport.findBySQL(queryStr);
		if (ca!=null&&ca.getCount()>0){ 	
			
		    mGsonArrayRequest DelGsonArrayRequest; 
		    JsonArray jolist=new JsonArray(); 
				//jolist = new JSONArray();
				ca.moveToFirst();
				
				int t=0;
				do{
					Map<String, String> params = new HashMap<String, String>();
				    for (int i=0;i<ca.getColumnCount();i++){
				    	
				    	   
					     params.put(ca.getColumnName(i), ca.getString(i));  
					    
					      
				    };
				   
				    JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject(); 
				   // Log.d("JsonObject"+t, jostring.toString());
				    t++;
				    jolist.add(jostring);
			    
				} while(ca.moveToNext());  
		    //JSONObject jsonObject = new JSONObject(params);
				 Log.d("JsonArray"+t, jolist.toString());
				 //Toast.makeText(context, jolist.toString(), Toast.LENGTH_SHORT).show();
				DelGsonArrayRequest = new mGsonArrayRequest(url,jolist,"3",new Response.Listener<JSONArray>() {  
			           @Override  
			           public void onResponse(JSONArray response) {  
			           	if(response != null){
			           		Log.d("json back", response.toString());
			           		//GsonArray2Sqlite(response,TaskData.TdDB.TABLE_NAME_TaskMain);   
			          ;
			           	   if (response.toString().contains("OK")){			     		           		
			           		 Toast.makeText(context, "删除成功",Toast.LENGTH_SHORT).show();
			                 TaskData.adapterUpdate(); 
			           	   }else{
			           		 Toast.makeText(context, "删除失败",Toast.LENGTH_SHORT).show();  
			           	   }
			           	 }else{
			               	 Toast.makeText(context, "系统无响应",Toast.LENGTH_SHORT).show();
			               }  
			              
			           }  
			       },new Response.ErrorListener() {  
					@Override  
				        public void onErrorResponse(VolleyError error) {  
				           // responseText.setText(error.getMessage()); 
						   TaskTool.connFailureNoWarning(context, error);
				        }  
				    });  
				       	
				    requestQueue.add(DelGsonArrayRequest); 
				    requestQueue.start();
		   }
		   
		} 
	
	void LitePalClearByGsonArrayRequestPost(String servletName,Map params){  
		   // String url = "http://route.showapi.com/213-3";  
		    //String url = "http://192.168.1.100:8080/EasyTest/TaskmainServlet";  
		    //Map<String, String> params = new HashMap<String, String>();  
		    //params.put("taskname", "value1");  
		    //params.put("deadline", "value2"); 
		    String url = TaskData.hostname+servletName;  
			//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
		  
		   RequestQueue requestQueue=Volley.newRequestQueue(context);
		    //String clearResult = "0";
		
			//String sql = "delete from task_main ";
			//TaskData.db_TdDB.execSQL(sql);
			//Toast.makeText(context, "no data",Toast.LENGTH_SHORT).show();
		  
		    JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject(); 
		    JsonArray jolist = new JsonArray();
		    jolist.add(jostring);

				mGsonArrayRequest ClearGsonArrayRequest = new mGsonArrayRequest(url,jolist,"4",new Response.Listener<JSONArray>() {  
			           @Override  
			           public void onResponse(JSONArray response) {  
			           	if(response != null){
			           	  if (response.toString().contains("OK")){			     		           		
				           		Toast.makeText(context, "清除成功",Toast.LENGTH_SHORT).show();
				                TaskData.adapterUpdate(); 
				           	   }else{
				           		Toast.makeText(context, "清除失败",Toast.LENGTH_SHORT).show();  
				           	   }
				           	 }else{
				               	 Toast.makeText(context, "系统无响应",Toast.LENGTH_SHORT).show();
				               }  
			              
			           }  
			       },new Response.ErrorListener() {  
					@Override  
				        public void onErrorResponse(VolleyError error) {  
				           // responseText.setText(error.getMessage()); 
						    TaskTool.connFailureNoWarning(context, error);
				        }  
				    });  
				       	
				    requestQueue.add(ClearGsonArrayRequest); 
				    requestQueue.start();
		   }
	
	void DelByGsonArrayRequestPost(String servletName,String tbname,String sn){  
		   // String url = "http://route.showapi.com/213-3";  
		    //String url = "http://192.168.1.100:8080/EasyTest/TaskmainServlet";  
		    //Map<String, String> params = new HashMap<String, String>();  
		    //params.put("taskname", "value1");  
		    //params.put("deadline", "value2"); 
		    String url = TaskData.hostname+servletName;  
			//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
		  
		   RequestQueue requestQueue=Volley.newRequestQueue(context);
		   String queryStr="select * from "+tbname+" where "+TaskData.TdDB.TASK_SN+"="+"'"+sn+"'";
		   //Toast.makeText(context, queryStr, Toast.LENGTH_SHORT).show();
		   Cursor ca=TaskData.db_TdDB.rawQuery("select * from "+tbname+" where "+TaskData.TdDB.TASK_SN+"="+"'"+sn+"'", null);
		if (ca!=null&&ca.getCount()>0){ 	
			
		    mGsonArrayRequest DelGsonArrayRequest; 
		    JsonArray jolist=new JsonArray(); 
				//jolist = new JSONArray();
				ca.moveToFirst();
				
				int t=0;
				do{
					Map<String, String> params = new HashMap<String, String>();
				    for (int i=0;i<ca.getColumnCount();i++){
				    	
				    	   
					     params.put(ca.getColumnName(i), ca.getString(i));  
					    
					      
				    };
				   
				    JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject(); 
				   // Log.d("JsonObject"+t, jostring.toString());
				    t++;
				    jolist.add(jostring);
			    
				} while(ca.moveToNext());  
		    //JSONObject jsonObject = new JSONObject(params);
				 Log.d("JsonArray"+t, jolist.toString());
				 //Toast.makeText(context, jolist.toString(), Toast.LENGTH_SHORT).show();
				DelGsonArrayRequest = new mGsonArrayRequest(url,jolist,"3",new Response.Listener<JSONArray>() {  
			           @Override  
			           public void onResponse(JSONArray response) {  
			           	if(response != null){
			           		Log.d("json back", response.toString());
			           		//GsonArray2Sqlite(response,TaskData.TdDB.TABLE_NAME_TaskMain);   
			          ;
			           	   if (response.toString().contains("OK")){			     		           		
			           		Toast.makeText(context, "删除成功",Toast.LENGTH_SHORT).show();
			                TaskData.adapterUpdate(); 
			           	   }else{
			           		Toast.makeText(context, "删除失败",Toast.LENGTH_SHORT).show();  
			           	   }
			           	 }else{
			               	 Toast.makeText(context, "系统无响应",Toast.LENGTH_SHORT).show();
			               }  
			              
			           }  
			       },new Response.ErrorListener() {  
					@Override  
				        public void onErrorResponse(VolleyError error) {  
				           // responseText.setText(error.getMessage()); 
						    TaskTool.connFailureNoWarning(context, error);
				        }  
				    });  
				       	
				    requestQueue.add(DelGsonArrayRequest); 
				    requestQueue.start();
		   }
		   
		} 
	
	void ClearByGsonArrayRequestPost(String servletName,Map params){  
		   // String url = "http://route.showapi.com/213-3";  
		    //String url = "http://192.168.1.100:8080/EasyTest/TaskmainServlet";  
		    //Map<String, String> params = new HashMap<String, String>();  
		    //params.put("taskname", "value1");  
		    //params.put("deadline", "value2"); 
		    String url = TaskData.hostname+servletName;  
			//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
		  
		    RequestQueue requestQueue=Volley.newRequestQueue(context);
		   
		    JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject(); 
		    JsonArray jolist = new JsonArray();
		    jolist.add(jostring);
		  
				mGsonArrayRequest ClearGsonArrayRequest = new mGsonArrayRequest(url,jolist,"4",new Response.Listener<JSONArray>() {  
			           @Override  
			           public void onResponse(JSONArray response) {  
			           	if(response != null){
			           	  if (response.toString().contains("OK")){			     		           		
				           		Toast.makeText(context, "清除成功",Toast.LENGTH_SHORT).show();
				                TaskData.adapterUpdate(); 
				           	   }else{
				           		Toast.makeText(context, "清除失败",Toast.LENGTH_SHORT).show();  
				           	   }
				           	 }else{
				               	 Toast.makeText(context, "系统无响应",Toast.LENGTH_SHORT).show();
				               }  
			              
			           }  
			       },new Response.ErrorListener() {  
					@Override  
				        public void onErrorResponse(VolleyError error) {  
				           // responseText.setText(error.getMessage()); 
						    TaskTool.connFailureNoWarning(context, error);
				        }  
				    });  
				       	
				    requestQueue.add(ClearGsonArrayRequest); 
				    requestQueue.start();
		   }
	  
	   void UpdateUserRequestPost(Context context,String servletName,Map params){ 
			final Context mContext=context;
		   // String url = "http://route.showapi.com/213-3";  
		    //String url = "http://192.168.1.100:8080/EasyTest/TaskmainServlet";  
		    //Map<String, String> params = new HashMap<String, String>();  
		    //params.put("taskname", "value1");  
		    //params.put("deadline", "value2"); 
		    String url = TaskData.hostname+servletName;  
			//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
			RequestQueue requestQueue=Volley.newRequestQueue(mContext);
		 
				    JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject(); 
				    JsonArray jolist=new JsonArray(); 
				    jolist.add(jostring);
				
				mGsonArrayRequest gsonObjectRequest = new mGsonArrayRequest(url,jolist,"6",new Response.Listener<JSONArray>() {  
					    
					@Override  
			           public void onResponse(JSONArray response) {  
			           	if(response != null){
			           		//Log.d("json back", "ddd "+response.toString());
			           		
			           		 //Toast.makeText(mContext, "back ok",Toast.LENGTH_SHORT).show();
			                //TaskData.adapterUpdate();  

			           	   //EventBus.getDefault().post(new UserResponseEvent(response.toString()));
			           	   Log.d("json back", response.toString());
			           	 }else{
			               	 //Toast.makeText(mContext, "no data",Toast.LENGTH_SHORT).show();
			               }  
			              
			            }  
			        },new Response.ErrorListener() {  
					@Override  
				        public void onErrorResponse(VolleyError error) {  
				           // responseText.setText(error.getMessage()); 
						    TaskTool.connFailureNoWarning(mContext, error);
				        }  
				    });  
				       	
				    requestQueue.add(gsonObjectRequest); 
				    requestQueue.start();
				    				    
	return ;			    
	}
	
      void GsonArrayRequestPostSuccess(final String servletName,Cursor cursor){  
		   // String url = "http://route.showapi.com/213-3";  
		    //String url = "http://192.168.1.105:8090/EasyTest/TaskmainServlet";  
		    //Map<String, String> params = new HashMap<String, String>();  
		    //params.put("taskname", "value1");  
		    //params.put("deadline", "value2"); 
		    String url = TaskData.hostname+servletName;  
		    Log.d("json send", url );
			//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
			RequestQueue requestQueue=Volley.newRequestQueue(context);
		  if (cursor!=null&&cursor.getCount()>0){ 	
			Cursor ca = cursor;
			

		    mGsonArrayRequest gsonArrayRequest; 
		    
		    JsonArray jolist=new JsonArray(); 
				//jolist = new JSONArray();
				ca.moveToFirst();
				int t=0;
				do{
					Map<String, String> params = new HashMap<String, String>();
				      //params.put("_id", ca.getString(1));
					for (int i=1;i<ca.getColumnCount();i++){
				    	
				    	   
					     params.put(ca.getColumnName(i), ca.getString(i));  
						 Log.d("ca"+i, ca.getColumnName(i)+"       "+ca.getString(i));
					      
				    };
				   
				    JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject(); 
				   // Log.d("JsonObject"+t, jostring.toString());
				    t++;
				    jolist.add(jostring);
			    
				} while(ca.moveToNext());  
		    //JSONObject jsonObject = new JSONObject(params);
				 Log.d("JsonArray"+t, jolist.toString());
				
				gsonArrayRequest = new mGsonArrayRequest(url,jolist,"1",new Response.Listener<JSONArray>() {  
			           @Override  
			           public void onResponse(JSONArray response) {  
			           	if(response != null){
			           		Log.d("json back", response.toString());
			           		if (servletName=="TaskmainServlet"){
			           		GsonArray2Sqlite(response,TaskData.TdDB.TABLE_NAME_TaskMain,"_sn");   
			           		//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
			           		Log.d("GsonArray2Sqlite", "taskmain ok");
			           		TaskData.adapterUpdate();
			           		}
			           		if (servletName=="TaskrecServlet"){
				           		GsonArray2Sqlite(response,TaskData.TdDB.TABLE_NAME_TaskRecord,"r_sn");   
				           		//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
				           		Log.d("GsonArray2Sqlite", "taskrec ok");  	
			           		}
			           		if (servletName=="MemoServlet"){
			           			Memo newMemo = new Memo();
				           		GsonArray2LitePal(response,"sn","memo",Memo.class);   
				           		//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
				           		Log.d("GsonArray2LitePal", "memo ok");	
				           		
			           		}
			           		
			         		if (servletName=="ReminderServlet"){
			         			//Reminder newReminder=new Reiminder();
				           		GsonArray2LitePal(response,"sn","reminder",Reminder.class);   
				           		//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
				           		Log.d("GsonArray2LitePal", "reminder ok");	
				           		
			         		}
			         		if (servletName=="RatingServlet"){
			         			UserRatingBean newRating=new UserRatingBean();
				           		GsonArray2LitePal(response,"id","userratingbean",UserRatingBean.class);   
				           		//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
				           		Log.d("GsonArray2LitePal", "userratingbean ok");
				           		
			         		}
			           		TaskData.adapterUpdate();  
			           	 }else{
			               	 Toast.makeText(context, "no data",Toast.LENGTH_SHORT).show();
			               }  
			              
			           }  
			       },new Response.ErrorListener() {  
					@Override  
				        public void onErrorResponse(VolleyError error) {  
				           // responseText.setText(error.getMessage()); 
						   
					     	Log.d("Error", error.toString());
					     if (error.getMessage()!=null){
					     	Log.e("Error", error.getMessage(), error);
						    byte[] htmlBodyBytes = error.networkResponse.data;
						    Log.e("Error", new String(htmlBodyBytes), error);
						  }
				        }  
				    });  
				   
				
				    requestQueue.add(gsonArrayRequest); 
				   // requestQueue.start();
				  
		   }
		   
		}  
      
  	void GsonArray2Sqlite(JSONArray ja,String tbname,String index){
  		
  	 for (int i=0;i<ja.length();i++){	
  		JSONObject jo;
  		try {
  			jo = ja.getJSONObject(i);
  			Iterator<String> it=jo.keys();
  			//String indexkey = (String) it.next(); 
  			String indexvalue=jo.getString(index);
  			Log.d("index", indexvalue);
  			 ContentValues cv=new ContentValues();
  			it.next();
  			do{
  			 String key = (String) it.next(); 
  			 Object u=jo.get(key);	
  			 Log.d(key, u.toString());
  			 cv.put(key, u.toString());
  			}while(it.hasNext());	
  			 Cursor cursor = TaskData.db_TdDB.rawQuery("select * from "+tbname+" where "+index+"="+"'"+indexvalue+"'", null);
  			 if (cursor.getCount()==0){
  			   long insertcode=TaskData.db_TdDB.insert(tbname, null, cv);
  			   Log.d("insert backdata", "ok"+insertcode);
  			 }else{				 
  			  /*if (cursor.getCount()>0){ 
  			   cursor.moveToFirst();
  			   String whereAs=index+"=?";
  			   String[] whereValue={cursor.getString(cursor.getColumnIndex(index))};
  			   Log.d("udpate backdata whereas",""+cursor.getString(cursor.getColumnIndex(index)));
  			   int updatecode = TaskData.db_TdDB.update(tbname, cv, whereAs, whereValue);
  			   Log.d("udpate backdata", "ok"+updatecode);
  			  }*/
  			 }  
  		} catch (JSONException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  		
  	 }
  	}

  	void GsonArray2LitePal(JSONArray ja,String rowId,String tbname,Class classname){
  				 
  		 for (int i=0;i<ja.length();i++){	
  			 JSONObject jo;
  			 ContentValues cv=new ContentValues();
  			try {
  				jo = ja.getJSONObject(i);
  				Iterator<String> it=jo.keys();
  				
  				String rowIdvalue=jo.get(rowId).toString();
  				
  				 cv.put(rowId, rowIdvalue);
  				 it.next();
  				 
  				do{
  				 String key = (String) it.next(); 
  				 Object u=jo.get(key);	
  				 cv.put(key, u.toString());
  				 
  				}while(it.hasNext());
  				 
  				 Cursor cursor = DataSupport.findBySQL("select * from "+tbname+" where "+"username="+"'"+TaskData.user+"'"+" and "+rowId+"="+"'"+rowIdvalue+"';");
  				 if (cursor.getCount()==0){
  					String root=context.getFilesDir().getAbsolutePath();
  					Log.d("root", root);
  					//String path ="data/data/com.example.easygoal/"+tbname;
  					//String path=root.replace("files", "databases")+File.separator+tbname;
  					String path=root.replace("files", tbname);
  					Log.d("path", path);
  					//SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
  					//SQLiteDatabase db=this.openOrCreateDatabase("Memo.db", Context.MODE_PRIVATE, null);
  					TaskData.db.insert(tbname, null, cv); 
  				   //DataSupport.insert(classname,cv);
  				   Log.d("insert backdata", "ok");
  				 }else{	
  					/* 
  				   cursor.moveToFirst();
  				   String whereAs=rowId+"=?";
  				   String[] whereValue={cursor.getString(cursor.getColumnIndex(rowId))};
  				   DataSupport.update(classname, cv, Long.parseLong(rowIdvalue));
  				   Log.d("udpate backdata", "ok");
  				   */
  				 }  
  			} catch (JSONException e) {
  				// TODO Auto-generated catch block
  				 Log.d("GsonArray2LitePal", e.toString());
  				e.printStackTrace();
  			}
  			
  		 }
  		}
      
  
  	
  	
     /* 
      * 
      * 
      * 
      private void GsonObjectRequestPostSuccess(String servletName,Cursor cursor){  
	   // String url = "http://route.showapi.com/213-3";  
	    //String url = "http://192.168.1.105:8090/EasyTest/TaskmainServlet";  
	    //Map<String, String> params = new HashMap<String, String>();  
	    //params.put("taskname", "value1");  
	    //params.put("deadline", "value2"); 
	    String url = TaskData.hostname+servletName;  
		//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
		RequestQueue requestQueue=Volley.newRequestQueue(getApplication());
	  if (cursor!=null&&cursor.getCount()>0){ 	
		Cursor ca = cursor;
		

	    mJsonObjectRequest jsonObjectRequest; 
	    
			//jolist = new JSONArray();
			ca.moveToFirst();
			int t=0;
			do{
				Map<String, String> params = new HashMap<String, String>();
			    for (int i=0;i<ca.getColumnCount();i++){
			    	
			    	   
				     params.put(ca.getColumnName(i), ca.getString(i));  
				    
				      
			    };
			   // jolist.add(jo);
			    JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject(); 
			    Log.d(Tags,"JsonObject"+t+jostring.toString());
			    t++;
			    jsonObjectRequest = new mJsonObjectRequest(url,jostring,new Response.ErrorListener() {  
			        @Override  
			        public void onErrorResponse(VolleyError error) {  
			           // responseText.setText(error.getMessage());  
			        }  
			    });  
			       
			    requestQueue.add(jsonObjectRequest); 
		    
			} while(ca.moveToNext());  
	    //JSONObject jsonObject = new JSONObject(params);
	   
	   }
	   
	}  
	
	private void DelByGsonArrayRequestPost(String servletName,String tbname,int delId){  
		   // String url = "http://route.showapi.com/213-3";  
		    //String url = "http://192.168.1.105:8090/EasyTest/TaskmainServlet";  
		    //Map<String, String> params = new HashMap<String, String>();  
		    //params.put("taskname", "value1");  
		    //params.put("deadline", "value2"); 
		    String url = TaskData.hostname+servletName;  
			//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
		  
		   RequestQueue requestQueue=Volley.newRequestQueue(getApplication());
		   Cursor ca=TaskData.db_TdDB.rawQuery("select * from "+tbname+" where "+TaskData.TdDB.TASK_ID+"="+delId, null);
		if (ca!=null&&ca.getCount()>0){ 	
			
		    mGsonArrayRequest DelGsonArrayRequest; 
		    JsonArray jolist=new JsonArray(); 
				//jolist = new JSONArray();
				ca.moveToFirst();
				
				int t=0;
				do{
					Map<String, String> params = new HashMap<String, String>();
				    for (int i=0;i<ca.getColumnCount();i++){
				    	
				    	   
					     params.put(ca.getColumnName(i), ca.getString(i));  
					    
					      
				    };
				   
				    JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject(); 
				   // Log.d("JsonObject"+t, jostring.toString());
				    t++;
				    jolist.add(jostring);
			    
				} while(ca.moveToNext());  
		    //JSONObject jsonObject = new JSONObject(params);
				 Log.d(Tags,"JsonArray"+t+jolist.toString());
				 
				DelGsonArrayRequest = new mGsonArrayRequest(url,jolist,"3",new Response.Listener<JSONArray>() {  
			           @Override  
			           public void onResponse(JSONArray response) {  
			           	if(response != null){
			           		Log.d(Tags,"json back"+response.toString());
			           		GsonArray2Sqlite(response,TaskData.TdDB.TABLE_NAME_TaskMain,"_sn");   
			           		//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
			                TaskData.adapterUpdate();  
			           	 }else{
			               	 //Toast.makeText(getApplicationContext(), "no data",Toast.LENGTH_SHORT).show();
			               }  
			              
			           }  
			       },new Response.ErrorListener() {  
					@Override  
				        public void onErrorResponse(VolleyError error) {  
				           // responseText.setText(error.getMessage());  
				        }  
				    });  
				       	
				    requestQueue.add(DelGsonArrayRequest); 
				    requestQueue.start();
		   }
		   
		}  
	  
	  
	private void jsonArrayRequestPostSuccess(String servletName,Cursor cursor){ 
		 final JSONArray jolist = new JSONArray();
		
		String url = TaskData.hostname+servletName;  
		//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
		RequestQueue requestQueue=Volley.newRequestQueue(getApplication());
	  if (cursor!=null&&cursor.getCount()>0){ 	
		Cursor ca = cursor;
		
		ca.moveToFirst();
		int t=0;
		Gson gson=new Gson();
		List<List> lll=new ArrayList<List>();
		do{
			
			ArrayList<Map<String,String>> arraylist = new ArrayList<Map<String,String>>(); 
		    for (int i=0;i<ca.getColumnCount();i++){
			    Map<String, String> newmap =  new HashMap<String,String>();
				newmap.put(ca.getColumnName(i), ca.getString(i));
				arraylist.add(newmap); 				      
		    }
		   lll.add(arraylist);
		    //jolist.put(jo.toJson(arraylist));
		   //Log.d("jolist"+t,jolist.toString());
		   t++;
		} while(ca.moveToNext()); 
		//jolist=gson.toJsonTree(lll,new TypeToken<List<List>>(){}.getType()).getAsJsonArray();
		Log.d(Tags+"|jolist|",jolist.toString());
	  }	
		
		
		Response.Listener rl=new Response.Listener<JSONArray>() {  
           @Override  
           public void onResponse(JSONArray response) {  
           	if(response != null){
           		
                   //Toast.makeText(getApplicationContext(), response.toString(),Toast.LENGTH_SHORT).show();
               }else{
               	 Toast.makeText(getApplicationContext(), "无数据",Toast.LENGTH_SHORT).show();
               }  
              
           }  
       };
		
       
       
      Response.ErrorListener rel=new Response.ErrorListener() {  
           @Override  
           public void onErrorResponse(VolleyError error) {  
               //Log.i("res errorlistener",error.getMessage());  
           }  
       };
      
		myJsonArrayRequest jr = new myJsonArrayRequest(url,jolist,rl, rel);    
		requestQueue.add(jr); 
		requestQueue.start();
	
	}
	  
      void InsertGsonArrayRequestPost(String servletName,String tbname,String sn){  
		   // String url = "http://route.showapi.com/213-3";  
		    //String url = "http://192.168.1.100:8080/EasyTest/TaskmainServlet";  
		    //Map<String, String> params = new HashMap<String, String>();  
		    //params.put("taskname", "value1");  
		    //params.put("deadline", "value2"); 
		    String url = TaskData.hostname+servletName;  
			//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
			RequestQueue requestQueue=Volley.newRequestQueue(context);
			Cursor ca = TaskData.db_TdDB.rawQuery("select * from "+tbname+" where "+TaskData.TdDB.TASK_SN+"="+"'"+sn+"'", null);
			if (ca!=null&&ca.getCount()>0){ 	
			
			

		    mGsonArrayRequest gsonArrayRequest; 
		    JsonArray jolist=new JsonArray(); 
				//jolist = new JSONArray();
				ca.moveToFirst();
				int t=0;
				do{
					Map<String, String> params = new HashMap<String, String>();
				    for (int i=0;i<ca.getColumnCount();i++){
				    	
				    	   
					     params.put(ca.getColumnName(i), ca.getString(i));  
					    
					      
				    };
				   
				    JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject(); 
				   // Log.d("JsonObject"+t, jostring.toString());
				    t++;
				    jolist.add(jostring);
			    
				} while(ca.moveToNext());  
		    //JSONObject jsonObject = new JSONObject(params);
				 Log.d("JsonArray"+t, jolist.toString());
				 Toast.makeText(context, jolist.toString(),Toast.LENGTH_SHORT).show();
				gsonArrayRequest = new mGsonArrayRequest(url,jolist,"2",new Response.Listener<JSONArray>() {  
			           @Override  
			           public void onResponse(JSONArray response) {  
			           	if(response != null){
			           		Log.d("json back", "ddd "+response.toString());
			           		//GsonArray2Sqlite(response,TaskData.TdDB.TABLE_NAME_TaskMain);   
			           		Toast.makeText(context, response.toString(),Toast.LENGTH_SHORT).show();
			                TaskData.adapterUpdate();  
			           	 }else{
			               	 Toast.makeText(context, "no data",Toast.LENGTH_SHORT).show();
			               }  
			              
			           }  
			       },new Response.ErrorListener() {  
					@Override  
				        public void onErrorResponse(VolleyError error) {  
				           // responseText.setText(error.getMessage());  
				        }  
				    });  
				       	
				    requestQueue.add(gsonArrayRequest); 
				    requestQueue.start();
		   }
		   
		}  
     
	void GsonArray2Sqlite(JSONArray ja,String tbname){
		
	 for (int i=0;i<ja.length();i++){	
		JSONObject jo;
		try {
			jo = ja.getJSONObject(i);
			Iterator<String> it=jo.keys();
			String rowId=it.next();
			 ContentValues cv=new ContentValues();
			do{
			 String key = (String) it.next(); 
			 Object u=jo.get(key);
			
			 cv.put(key, u.toString());
			}while(it.hasNext());	
			 Cursor cursor = TaskData.db_TdDB.rawQuery("select * from "+tbname+" where "+"_id"+"="+rowId, null);
			 if (cursor.getCount()==0){
			   TaskData.db_TdDB.insert(tbname, null, cv);
			   Log.d("insert backdata", "ok");
			 }else{	 
			   cursor.moveToFirst();
			   String whereAs=TaskData.TdDB.TASK_ID+"=?";
			   String[] whereValue={cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_ID))};
			   TaskData.db_TdDB.update(tbname, cv, whereAs, whereValue);
			   Log.d("udpate backdata", "ok");
			 }  
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	 }
	}
	*/
}
