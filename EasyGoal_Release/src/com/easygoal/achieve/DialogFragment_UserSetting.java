package com.easygoal.achieve;

import java.util.HashMap;
import java.util.Map;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.litepal.crud.DataSupport;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DialogFragment_UserSetting extends DialogFragment {

	private Context context;
    String result; 
    ImageView iv_userpic;
    TextView tv_userId;
    TextView tv_phoneNo;
    TextView tv_username;
    TextView tv_usernickname;
    TextView tv_usertype;
    TextView tv_usercredit;
    TextView tv_usergrade;
    TextView tv_userlogintimes;
    TextView tv_userlastlogintime;
    TextView tv_online;
    AlertDialog alog1;
    AlertDialog alog2;
    AlertDialog alog3;
    
	public DialogFragment_UserSetting(Context context) {
		// TODO Auto-generated constructor stub
	  this.context=context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=inflater.inflate(R.layout.leftmenu_userinfo, container);
	    Button btn_closewin = (Button) v.findViewById(R.id.btn_closewin);  
	    Button btn_editpwd = (Button) v.findViewById(R.id.btn_editpwd);  
	    Button btn_editnickname = (Button) v.findViewById(R.id.btn_editnickname);  
	    Button btn_applymembership = (Button) v.findViewById(R.id.btn_applymembership);  
	    //Button btn_editpwd = (Button) v.findViewById(R.id.btn_editpwd);  
	    iv_userpic=(ImageView)v.findViewById(R.id.iv_userpic);
	    tv_phoneNo=(TextView)v.findViewById(R.id.tv_phoneNo);
	    tv_userId=(TextView)v.findViewById(R.id.tv_userId);
	    tv_username=(TextView)v.findViewById(R.id.tv_username);
	    tv_usernickname=(TextView)v.findViewById(R.id.tv_usernickname);
	    tv_usertype=(TextView)v.findViewById(R.id.tv_usertype);
	    tv_usercredit=(TextView)v.findViewById(R.id.tv_usercredit);
	    tv_usergrade=(TextView)v.findViewById(R.id.tv_usergrade);
	    tv_userlogintimes=(TextView)v.findViewById(R.id.tv_userlogintimes);
	    tv_userlastlogintime=(TextView)v.findViewById(R.id.tv_userlastlogintime);
	    tv_online = (TextView)v.findViewById(R.id.tv_online);
	  
	    iv_userpic.setImageResource(R.drawable.user);
	    // 离线登录
	    SharedPreferences sharedpreference = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
	    final Editor sfeditor = sharedpreference.edit();
	       ConnectivityManager con = (ConnectivityManager)context.getSystemService(Activity.CONNECTIVITY_SERVICE);  ;
	       boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();  
		   boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();  
		   
		   
		   String userPhone = sharedpreference.getString("phoneNo", ""); 
      	   String userId = sharedpreference.getString("userId", ""); 
           final String username = sharedpreference.getString("username", ""); 
      	   String nickname = sharedpreference.getString("nickname", ""); 
      	   String usertype = sharedpreference.getString("usertype", ""); 
      	   String lastlogintime= sharedpreference.getString("lastlogintime", "");
           int logintimes= sharedpreference.getInt("logintimes", 0);	 
		     tv_userId.setText(userId);
		     tv_username.setText(username);
		     tv_usernickname.setText(nickname);
		     tv_phoneNo.setText(userPhone);
		     tv_usertype.setText(usertype);
	         tv_userlastlogintime.setText(TimeData.TimeStamp2TimeStrYY(lastlogintime));   
	         tv_userlogintimes.setText(""+logintimes); 
	         tv_online.setText(TaskData.onlinestatus);
		   
		   
	         /*
		   if(wifi|internet){	
			   
			   Map params=new HashMap<String,String>();
       		   params.put("phoneNo",getPhoneNumber());
               UserLoginRequestPost("UserServlet",params);
               
			    tv_online.setText(TaskData.onlinestatus);    
			    
			  
		   }else{    
			    tv_online.setText("(离线使用)");
		   }    
			    
		   
		   
			    String userinfo=TaskData.userResponseJsonArrayStr;
		     if (userinfo!=null&&!userinfo.trim().isEmpty()){	
			     try {
			     	JSONArray joa=new JSONArray(userinfo);
			    	Log.d("jsonarray", joa.toString());
				    for (int i=0;i<joa.length();i++){
					JSONObject jo=joa.getJSONObject(i);
					Iterator<String> it = jo.keys();
					do{
						String key=(String)it.next();
						Log.d("jo key", key);
						
						if ((String)jo.get(key)!=null){
						Log.d("jo value", jo.get(key).toString());
						}
					
					}while(it.hasNext());
					if (jo.getString("userId")!=null){
				      tv_userId.setText(jo.getString("userId"));
				      sfeditor.putString("userId",jo.getString("userId")); 
					}
					if (jo.getString("phoneNo")!=null){
					  tv_phoneNo.setText(jo.getString("phoneNo"));
					  //sfeditor.putString("userId",jo.getString("userId")); 
						}
					if (jo.getString("username")!=null){
				      tv_username.setText(jo.getString("username"));
				      sfeditor.putString("usernane",jo.getString("username")); 
					}
					if (jo.get("lastlogintime")!=null){
					     tv_userlastlogintime.setText((String)jo.get("lastlogintime"));
					     sfeditor.putString("lastlogintime",jo.getString("lastlogintime")); 
					 }
					if (jo.get("logintimes")!=null){
					    tv_userlogintimes.setText((String)jo.get("logintimes"));
					    sfeditor.putInt("logintimes",jo.getInt("logintimes")); 
						}
					  
					if (jo.getString("usertype")!=null){
				       tv_usertype.setText(jo.getString("usertype"));
				       sfeditor.putString("usertype",jo.getString("usertype")); 
					}
					
					if (jo.get("usercredit")!=null){
				     tv_usercredit.setText(jo.get("usercredit").toString());
				      sfeditor.putString("usercredit",jo.getString("usercredit")); 
					}
					if (jo.getString("nickname")!=null){
					       tv_usernickname.setText(jo.getString("nickname"));
					       sfeditor.putString("nickname",jo.getString("nickname")); 
						}
						
					
				   }	
			   } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		      	}
			     
			      sfeditor.commit();
			      //Toast.makeText(getActivity(), "数据同步已完成", Toast.LENGTH_SHORT).show();
		         
		      }else{
			        Toast.makeText(getActivity(), "未收到数据", Toast.LENGTH_SHORT).show();
		           //Toast.makeText(getActivity(), "亲，网络连了么？", Toast.LENGTH_LONG).show();  
	        	
	        	 String userPhone = sharedpreference.getString("phoneNo", ""); 
	        	 String userId = sharedpreference.getString("userId", ""); 
	        	 String username = sharedpreference.getString("username", ""); 
	        	 String nickname = sharedpreference.getString("nickname", ""); 
	        	 String usertype = sharedpreference.getString("usertype", ""); 
	        	 String lastlogintime= sharedpreference.getString("lastlogintime", "");
	             int logintimes= sharedpreference.getInt("logintimes", 1);	 
			     tv_userId.setText(userId);
			     tv_username.setText(username);
			     tv_usernickname.setText(nickname);
			     tv_phoneNo.setText(userPhone);
			     tv_usertype.setText(usertype);
		         tv_userlastlogintime.setText(lastlogintime);   
		         tv_userlogintimes.setText(""+logintimes); 
		      
		         //Toast.makeText(getActivity(), "离线使用", Toast.LENGTH_SHORT).show();
		      }  
		  */    

	    /*  
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
				   TaskData.db_dDB.update(tbname, cv, whereAs, whereValue);
				   Log.d("udpate backdata", "ok");
				 }  
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		 }
		}
    
	    Cursor c=DataSupport.findBySQL("select * from userbean");
	    if (c!=null&&c.getCount()>0){
		    c.moveToFirst();
		   	do {	
		      
		    		   tv_userId.setText(c.getString(c.getColumnIndex("userid")));
		    		   tv_username.setText(c.getString(c.getColumnIndex("username")));
		    		   tv_usernickname.setText(c.getString(c.getColumnIndex("usernickname")));
		    		   tv_usertype.setText(c.getString(c.getColumnIndex("usertype")));
		    		   tv_userscore.setText(c.getString(c.getColumnIndex("userscore")));
		    		   tv_usergrade.setText(c.getString(c.getColumnIndex("usergrade")));	    		   
		    		   tv_userlogintimes.setText(c.getString(c.getColumnIndex("userlogintimes")));
		    		   tv_userlastlogintime.setText(c.getString(c.getColumnIndex("userlastlogintime")));
		       
		      }while(c.moveToNext());
		
		    
			}
	    
	     */
	    btn_closewin.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View arg0) {  
	                // 关闭对话框  
	                dismiss();  
	            }  
	        });  

	    
      btn_editpwd.setOnClickListener(new OnClickListener(){
    	 EditText editpwd = new EditText(getActivity());
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (alog2==null){
		  	    alog2=new AlertDialog.Builder(getActivity())
		                .setTitle("新密码")
		 		  	 	//.setIcon(android.R.drawable.ic_dialog_info)
		 		  	 	.setView(editpwd)	  	 	
		 		  	 	.setPositiveButton("确认", new DialogInterface.OnClickListener(){


		 					@Override
		 					public void onClick(DialogInterface arg0, int arg1) {
		 						// TODO Auto-generated method stub
		 						ContentValues cv = new ContentValues();
								cv.put("password", editpwd.getText().toString());
								DataSupport.update(UserBean.class, cv, 1); 
							   
							    Map params=new HashMap<String,String>();
							    params.put("username",username);
					       		params.put("phoneNo",TaskData.user);
					       		params.put("password",editpwd.getText().toString());
					       		new ConnMySQL(getActivity()).UpdateUserRequestPost(getActivity(),"UserServlet",params);
					       	    Toast.makeText(context, "密码修改成功", Toast.LENGTH_SHORT).show();
					       		/*
					       	    sfeditor.putString("username", params.get("username").toString());
				                sfeditor.putString("phoneNo",params.get("phoneNo").toString());
				                sfeditor.commit();   
				                */
					       		dismiss();
					       		alog2.dismiss();
					       	    //DialogFragment_UserSetting dg_usersetting=new DialogFragment_UserSetting(getActivity());
						        //ghjghTaskTool.showDialog(dg_usersetting);
		 					}

		 		  	 	  })
		 		  	 	.setNegativeButton("取消",  new DialogInterface.OnClickListener(){


		 					@Override
		 					public void onClick(DialogInterface arg0, int arg1) {
		 						// TODO Auto-generated method stub
		 						 alog2.dismiss();
		 					}
		 		  	 		
		 		  	 	  })
		 		  	 	.show();
			}else{
				alog2.show();
			}
		  	   
		  	   
		}
    	 
      });
      
      btn_editnickname.setOnClickListener(new OnClickListener(){
     	 EditText et_nickname = new EditText(getActivity());
 		@Override
 		public void onClick(View arg0) {
 			// TODO Auto-generated method stub
 		  if (alog1==null){
 			  alog1=new AlertDialog.Builder(getActivity())
 		  	 	.setTitle("新呢称")
 		  	 	//.setIcon(android.R.drawable.ic_dialog_info)
 		  	 	.setView(et_nickname)	  	 	
 		  	 	.setPositiveButton("确认", new DialogInterface.OnClickListener(){


 					@Override
 					public void onClick(DialogInterface arg0, int arg1) {
 						// TODO Auto-generated method stub
 						ContentValues cv = new ContentValues();
 						cv.put("nickname", et_nickname.getText().toString());
 						DataSupport.update(UserBean.class, cv, 1); 
 					    Toast.makeText(context, "昵称修改成功", Toast.LENGTH_SHORT).show();
 					    Map params=new HashMap<String,String>();
 					    params.put("username",username);
			       		params.put("phoneNo",TaskData.user);
			       		params.put("nickname",et_nickname.getText().toString());
			       		new ConnMySQL(getActivity()).UpdateUserRequestPost(getActivity(),"UserServlet",params);
			       		
		                //String lastlogintime=new Timestamp(new Date().getTime()).toString();
			       		
		                sfeditor.putString("nickname", params.get("nickname").toString());
		                sfeditor.commit();   
		                
		    		    dismiss();
		    		    alog1.dismiss();
			       	    //DialogFragment_UserSetting dg_usersetting=new DialogFragment_UserSetting(getActivity());
				        //TaskTool.showDialog(dg_usersetting);
 					}

 		  	 	  })
 		  	 	.setNegativeButton("取消",  new DialogInterface.OnClickListener(){


 					@Override
 					public void onClick(DialogInterface arg0, int arg1) {
 						// TODO Auto-generated method stub
 						 alog1.dismiss();
 					}
 		  	 		
 		  	 	  })
 		  	 	.show();
 		   }else{
 			   alog1.show();
 		   }
 		  }
     	 
       });
      
      btn_applymembership.setOnClickListener(new OnClickListener(){
      	 EditText et_membershippwd = new EditText(getActivity());
  		@Override
  		public void onClick(View arg0) {
  			// TODO Auto-generated method stub
  			 alog3=new AlertDialog.Builder(getActivity())
  		  	 	.setTitle("请输入")
  		  	 	//.setIcon(android.R.drawable.ic_dialog_info)
  		  	 	.setView(et_membershippwd)	  	 	
  		  	 	.setPositiveButton("确认", new DialogInterface.OnClickListener(){


  					@Override
  					public void onClick(DialogInterface arg0, int arg1) {
  						// TODO Auto-generated method stub
  						ContentValues cv = new ContentValues();
  						cv.put("password", et_membershippwd.getText().toString());
  						DataSupport.update(UserBean.class, cv, 1); 
  					    Toast.makeText(context, "申请成功", Toast.LENGTH_SHORT).show();
  					     Map params=new HashMap<String,String>();
 			       		params.put("phoneNo",TaskData.user);
 			       		params.put("password",et_membershippwd.getText().toString());
 			       		UpdateUserRequestPost("UserServlet",params);
 			       		
 			    
  					}

  		  	 	  })
  		  	 	.setNegativeButton("取消",  new DialogInterface.OnClickListener(){


  					@Override
  					public void onClick(DialogInterface arg0, int arg1) {
  						// TODO Auto-generated method stub
  						alog3.dismiss();
  					}
  		  	 		
  		  	 	  })
  		  	 	.show();
  		}
      	 
        });
       		    
 		return v;	
    }
	 
   
    public void UpdateUserRequestPost(String servletName,Map params){ 
		
		   // String url = "http://route.showapi.com/213-3";  
		    //String url = "http://192.168.1.100:8080/EasyTest/TaskmainServlet";  
		    //Map<String, String> params = new HashMap<String, String>();  
		    //params.put("taskname", "value1");  
		    //params.put("deadline", "value2"); 
		    String url = TaskData.hostname+servletName;  
			//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
			RequestQueue requestQueue=Volley.newRequestQueue(getActivity());
		 
				    JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject(); 
				    JsonArray jolist=new JsonArray(); 
				    jolist.add(jostring);
				
				mGsonArrayRequest gsonObjectRequest = new mGsonArrayRequest(url,jolist,"6",new Response.Listener<JSONArray>() {  
					    
					@Override  
			           public void onResponse(JSONArray response) {  
			           	if(response != null){
			           		//Log.d("json back", "ddd "+response.toString());
			           		
			           		//Toast.makeText(getActivity(), "back ok",Toast.LENGTH_SHORT).show();
			                //TaskData.adapterUpdate();  
			           	    Log.d("json back", response.toString());
			           	   EventBus.getDefault().post(new UserResponseEvent(response.toString()));
			           	   Log.d("json back", response.toString());
			           	 }else{
			               	 Toast.makeText(getActivity(), "no data",Toast.LENGTH_SHORT).show();
			               }  
			              
			            }  
			        },new Response.ErrorListener() {  
					@Override  
				        public void onErrorResponse(VolleyError error) {  
				           // responseText.setText(error.getMessage());  
				        }  
				    });  
				       	
				    requestQueue.add(gsonObjectRequest); 
				    requestQueue.start();
				    				    
	return ;			    
}
  
	
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
		getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height );
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//EventBus.getDefault().unregister(getActivity());
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//EventBus.getDefault().register(getActivity());
	}  
}
