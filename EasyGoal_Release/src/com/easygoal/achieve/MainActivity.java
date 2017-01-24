package com.easygoal.achieve;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.http.util.TextUtils;
import org.json.JSONArray;
import org.json.JSONException;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;  
  
public class MainActivity extends Activity implements OnPageChangeListener{  
	View dot;
	LinearLayout mNumLayout;
  //Viewpager����  
    private ViewPager viewPager;  
    //ViewPager����������  
    private mPagerAdapter adapter;  
    //SharedPreferences �����ж��Ƿ��ǵ�һ�ε�½����һ�ε�½����ʾ�������棬����ֱ�ӽ��롣  
  
    // private int[] images = { R.drawable.img_guider1, R.drawable.img_guider2,
    //		R.drawable.img_guider3, R.drawable.img_guider4 };// ����ҳ���ͼƬ
    private List<View> views = new ArrayList<View>();// ҳ��
    private int pagerPos = 0;// ��ǰ��ҳ����
    private List<View> pointViews = new ArrayList<View>();// ������
    private LinearLayout layout;
    int MSG_INIT_OK = 1;
	int MSG_INIT_INFO = 2;
	int MSG_INIT_TIMEOUT = 9;
	int splashtime= 50;
	boolean isTimeout = false;
	TextView tvInfo ;
    String Tags="MainActivity";
    
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        
       // viewInit();
     //   LinearLayout mainlayout = new LinearLayout(this);
       // mainlayout.setOrientation(1);
     //   layout.setOrientation(LinearLayout.VERTICAL);
      //  setContentView(mainlayout);
        setContentView(R.layout.splashscreen); 
        
        //TextView tv_directenter=(TextView)findViewById(R.id.tv_directenter);
       
 
        /*
        new Handler().postDelayed(new Runnable() {
            public void run() {
                // Create an Intent that will start the Main WordPress Activity. 
            	  
    		    Intent intent= new Intent(MainActivity.this,MainActivity_1.class);
    	    	startActivity(intent);
    	    	finish();  	
            }
        }, 3000); //2900 for release
        */
        
	   
		
		/*
        tv_directenter.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				    SharedPreferences sp = getApplication().getSharedPreferences("userinfo" , MODE_PRIVATE);
	                Editor logineditor = sp.edit();   
	                String usrname=sp.getString("username", "");
	                
	               if (TextUtils.isEmpty(usrname)){
	            	   Log.d(Tags,"firstrun"+sp.getBoolean("firstrun", false));
	            	   int times=sp.getInt("logintimes", 0);
	            	   String lastlogintime=new Timestamp(new Date().getTime()).toString();
	            	   logineditor.putBoolean("fisrtrun",false);
	            	   logineditor.putString("username","");
	            	   logineditor.putString("phoneNo",TaskTool.getPhoneNumber(getApplication()));
	            	   logineditor.putString("lastlogintime",lastlogintime);
	            	   times++;
	            	   logineditor.putInt("logintimes",times);
	            	   logineditor.commit();   
	            	   TaskData.privilege=0;
	           		   
	           		   Toast.makeText(getApplication(), "�ο͵�¼",Toast.LENGTH_SHORT).show(); 
	              }else{
	            	    int times = sp.getInt("logintimes", 0);
		                String lastlogintime=new Timestamp(new Date().getTime()).toString();
		    		    logineditor.putString("lastlogintime",lastlogintime);
		    		    times++;
		    		    logineditor.putInt("logintimes",times);
		    		    Log.d(Tags,"logintimes "+times);
		    		    logineditor.commit();
		    		    TaskData.onlinestatus="(����ʹ��)";     		    
		    		    TaskData.privilege=1;
		    		    Toast.makeText(getApplication(), "����ʹ��"+usrname,Toast.LENGTH_SHORT).show(); 
	              }
				
			}
        	
        });
        */
        
        
        
        TaskData.t_start=0;
        TaskData.t_start=new Date().getTime();
        int CONN_FLAG = TaskTool.getConnCode(getApplication()); 
		//String userPhone = TaskTool.getPhoneNumber(getApplication());
        SharedPreferences sp = this.getSharedPreferences("userinfo" , MODE_PRIVATE);
        Editor logineditor = sp.edit();
        String phoneNo=TaskTool.getPhoneNumber(getApplication());
    	String username=sp.getString("username", "");
    	
    	if (!TextUtils.isEmpty(phoneNo)&&TaskTool.isMobileNO(phoneNo)){
    		    TaskData.user=phoneNo;
    			
    	}else{	
    			Intent intent= new Intent(MainActivity.this,LoginActivity.class);
	    	    startActivity(intent);
	    	    finish();   
    	}     	
    	//Toast.makeText(getApplicationContext(), "phoneNo:"+TaskData.user, Toast.LENGTH_SHORT).show();
    
		Log.d(TaskData.t_tags,""+(new Date().getTime()-TaskData.t_start)+"ms");
		
        if(sp.getBoolean("firstrun", false) == true){ //idΪ�գ��û����״�ʹ��Ӧ��
        	 //Toast.makeText(getApplicationContext(),username+"\n"+password+"\n"+phoneNo, Toast.LENGTH_SHORT).show();
        	//Toast.makeText(getApplicationContext(),""+sp.getBoolean("firstrun", true), Toast.LENGTH_SHORT).show();
        	//Toast.makeText(getApplicationContext(),"��ӭ����һ�ε�¼", Toast.LENGTH_SHORT).show();
        	logineditor.putBoolean("fisrtrun",false);
        	logineditor.commit();
        	/*
        	Log.d("firstrun", ""+sp.getBoolean("firstrun", true));
            int times=0;
            String lastlogintime=new Timestamp(new Date().getTime()).toString();
            logineditor.putBoolean("fisrtrun",false);
		    logineditor.putString("phoneNo",userPhone);
		    logineditor.putString("lastlogintime",lastlogintime);
		    times++;
		    logineditor.putInt("logintimes",times);
		    logineditor.commit();	
		        
        	setContentView(R.layout.layout_openslide);  
	        viewPager = (ViewPager) findViewById(R.id.vp_pager);
	        //Log.d("viewpager", viewPager.toString());
	        adapter=new mPagerAdapter();
	        viewPager.setAdapter(adapter);
	        //LayoutInflater inflater=(LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
	        //mainlayout.addView(viewPager);
	        //View dot=inflater.inflate(R.layout.layout_circle, null);
	        mNumLayout = (LinearLayout) findViewById(R.id.ll_circles); 
	        //mainlayout.addView(mNumLayout);
	        //ViewPager viewpager = new ViewPager(this);
	        //
	        //viewpager.setAdapter(adapter);
	        //mainlayout.addView(viewpager);
	        Log.d(Tags,"layout"+mNumLayout.toString());
	        viewPager.setOnPageChangeListener(this);
	        //viewPager.setOffscreenPageLimit(images.length);	        
	        //viewPager.setCurrentItem(1);
	        */
        	//setContentView(R.layout.splashscreen); 
        	
        	 new Handler().postDelayed(new Runnable() {
 	            public void run() {
 	                /* Create an Intent that will start the Main WordPress Activity. */
 	            	  
 	    		    Intent intent= new Intent(MainActivity.this,LoginActivity.class);
 	    	    	startActivity(intent);
 	    	    	finish();  	
 	            }
 	        }, splashtime); //2900 for release
		    
        }else{
        //�������״�ʹ��
        	
        	//if (!sp.getString("username", null).contains("�ο�")&& sp.getString("phoneNo", null).equals(TaskTool.getPhoneNumber(getApplication())) ){		
          	
           //if (!sp.getString("username", null).contains("�ο�")){			
        	  //String password=sp.getString("password", "");
          	  //String phoneNo=sp.getString("phoneNo", TaskTool.getPhoneNumber(getApplication()));
          	  //String username=sp.getString("username", "");
       
          	 //Toast.makeText(getApplicationContext(),username+"\n"+password+"\n"+phoneNo, Toast.LENGTH_SHORT).show();
          	  if (CONN_FLAG>0){  
          		 //setContentView(R.layout.splashscreen); 
          		 //Toast.makeText(getApplicationContext(), "����������,����ʹ��", Toast.LENGTH_SHORT).show();
          		  //String password=sp.getString("password", "888888");
             	  //String phoneNo=sp.getString("phoneNo", TaskTool.getPhoneNumber(getApplicationContext()));
             	  //String username=sp.getString("username", TaskTool.getPhoneNumber(getApplicationContext()));
          	    if (!TextUtils.isEmpty(username.trim())){ 
          		    final Map params=new HashMap<String,String>();
  		     	  
		       	    params.put("phoneNo", TaskData.user);
  		     	
  		           new Handler().postDelayed(new Runnable() {
    	            public void run() {
    	                /* Create an Intent that will start the Main WordPress Activity. */
    	            	TaskData.onlinestatus="(����)";
		           	    //Intent intent= new Intent(MainActivity.this,MainActivity_1.class);
    	    	    	//startActivity(intent);
    	    	    	//finish();  	  
    	            	FastLoginRequestPost("UserServlet",params);
    	    	    	
    	            }
    	        }, splashtime); //2900 for release
          	   }else{
          		   //Toast.makeText(getApplication(), username, Toast.LENGTH_SHORT).show();
          		  new Handler().postDelayed(new Runnable() {
     	            public void run() {
     	                /* Create an Intent that will start the Main WordPress Activity. */
     	            	//TaskData.onlinestatus="(����)";
 		           	    Intent intent= new Intent(MainActivity.this,LoginActivity.class);
     	    	    	startActivity(intent);
     	    	    	finish();  	  
     	            }
     	        }, splashtime); 
          	   }
  		          
  		          //finish();
             }else{
          		  //Toast.makeText(getApplicationContext(), "����δ����,����ʹ��", Toast.LENGTH_SHORT).show();
          		 
          	     	int times = sp.getInt("logintimes", 0);
	                String lastlogintime=new Timestamp(new Date().getTime()).toString();
	    		    logineditor.putString("phoneNo",TaskData.user);
	    		    logineditor.putString("lastlogintime",lastlogintime);
	    		    times++;
	    		    logineditor.putInt("logintimes",times);
	    		    Log.d(Tags,"logintimes "+times);
	    		    logineditor.commit();
	    		    TaskData.onlinestatus="(����ʹ��)";     		    
	    		    new Handler().postDelayed(new Runnable() {
	    	            public void run() {
	    	                /* Create an Intent that will start the Main WordPress Activity. */
	    	            	  
	    	    		    Intent intent= new Intent(MainActivity.this,MainActivity_1.class);
	    	    	    	startActivity(intent);
	    	    	    	finish();  	
	    	            }
	    	        }, splashtime); //2900 for release
  
          	  }
  		        
          	//}else{
          	 //  Toast.makeText(getApplicationContext(), "��Ϣ�д���", Toast.LENGTH_SHORT).show();
          //	}
        	
        	
        
        }       
        
    }  
       
    public void FastLoginRequestPost(String servletName,Map params){ 
		
		   // String url = "http://route.showapi.com/213-3";  
		    //String url = "http://192.168.1.100:8090/EasyTest/TaskmainServlet";  
		    //Map<String, String> params = new HashMap<String, String>();  
		    //params.put("taskname", "value1");  
		    //params.put("deadline", "value2"); 
		    String url = TaskData.hostname+servletName;  
			//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
		    final Map usermap=params;
		    
		    final String phoneNo=usermap.get("phoneNo").toString();
			RequestQueue requestQueue=Volley.newRequestQueue(getApplication());
		 
				    JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject(); 
				    JsonArray jolist=new JsonArray(); 
				    jolist.add(jostring);
					Log.d(Tags,"login data"+jolist.toString());
				mGsonArrayRequest gsonObjectRequest = new mGsonArrayRequest(url,jolist,"5",new Response.Listener<JSONArray>() {  
					    
					@Override  
			           public void onResponse(JSONArray response) {  
			           	if(response != null){
			           		//Log.d("json back", "ddd "+response.toString());
			           		int times=0;
			           		//Toast.makeText(getActivity(), "back ok",Toast.LENGTH_SHORT).show();
			                //TaskData.adapterUpdate();  
			           	   Log.d(Tags,"json back"+response.toString());
			           	   if (response.toString().contains(phoneNo)){		   
			           		   	//Toast.makeText(getApplication(), "��¼�ɹ�",Toast.LENGTH_SHORT).show();
			           		   	String login_username = null;
			           		   	String login_nickname=null;
			           		   	String login_usertype=null;
			           		   	String login_times=null;
			           		   	String login_userid=null;
			           		    String login_userphone=null;
			           		    int login_userprivilege=0;
			           		   try {
			           			   login_username=response.getJSONObject(0).get("username").toString();
			           			   login_nickname=response.getJSONObject(0).get("nickname").toString();
			           			   login_usertype=response.getJSONObject(0).get("usertype").toString();
			           			   login_userid=response.getJSONObject(0).get("userId").toString();
			           			   login_userprivilege=response.getJSONObject(0).getInt("userprivilege");
			           			   login_times=response.getJSONObject(0).get("logintimes").toString();
			           			   login_userphone=response.getJSONObject(0).get("phoneNo").toString();
			           			   Log.d(Tags,"login info "+login_username+"\n"+login_nickname+"\n");
			           			   
			           		   } catch (JSONException e) {
								// TODO Auto-generated catch block
			           			   //Toast.makeText(getApplication(), e.toString(),Toast.LENGTH_SHORT).show();
							    	e.printStackTrace();
			           		   }
			           		   //EventBus.getDefault().post(new UserResponseEvent(response.toString()));
			           		   //Log.d("json back", response.toString());
			        		   //Toast.makeText(getApplication(), login_username+"\n"+login_nickname+"\n"+login_usertype+login_userprivilege,Toast.LENGTH_SHORT).show();
			           		   SharedPreferences loginsp = getApplication().getSharedPreferences("userinfo" , MODE_PRIVATE);
			           		   Editor editor = loginsp.edit();
			           		   int logintimes = loginsp.getInt("logintimes", 0);
			           		   String lastlogintime=new Timestamp(new Date().getTime()).toString();
			           		   editor.putString("username", login_username);
			           		   editor.putString("nickname", login_nickname);
			           		   editor.putString("usertype", login_usertype);
			           		   editor.putString("userid", login_userid);
			           		   editor.putString("phoneNo",login_userphone);
			           		   editor.putString("lastlogintime",lastlogintime);
			           		   editor.putInt("userprivilege",login_userprivilege);
			           			
			           		   logintimes++;
			           		   editor.putInt("logintimes",logintimes);
			           		   Log.d(Tags,"logintimes "+logintimes);
			           		   editor.commit();
			    		  
			           		   TaskData.onlinestatus="(����)";
			           		   
			           		   Log.d(TaskData.t_tags,""+(new Date().getTime()-TaskData.t_start)+"ms");
			           		   
			           		   HashMap lum=new HashMap();
			        		   lum.put("username", login_username);
			        		   lum.put("phoneNo",  login_userphone);
			        		   lum.put("lastlogintime",lastlogintime);
			            	   lum.put("logintimes",logintimes );
			        		   TaskTool.UpdateUserRequestPost(getApplication(),"UserServlet",lum);
			           		     
			        		   Intent intent= new Intent(MainActivity.this,MainActivity_1.class);
			           		   startActivity(intent);
			           		   Log.d(TaskData.t_tags,""+(new Date().getTime()-TaskData.t_start)+"ms");
			           		   finish();  	
			           		   
			           	   }else{
			           		  //Toast.makeText(getApplication(), "��¼ʧ��,������",Toast.LENGTH_SHORT).show(); 
			           	     //Intent intent=new Intent(LoginActivity.this,MainActivity_1.class);
			           	     //startActivity(intent);
			           		   //EventBus.getDefault().post(new UserResponseEvent(response.toString()));
			           		   //Log.d("json back", response.toString());
			           		   //TaskData.onlinestatus="(����ʹ��)";
			           		   Intent intent= new Intent(MainActivity.this,LoginActivity.class);
			           		   startActivity(intent);
			           		   finish();  	  	
			           	   }
			           	   
			           	 }else{
			               	
				           		//Toast.makeText(getApplication(), "����Ӧ,����ʹ��",Toast.LENGTH_SHORT).show(); 
				           	     //Intent intent=new Intent(LoginActivity.this,MainActivity_1.class);
				           	     //startActivity(intent);
				           	   //EventBus.getDefault().post(new UserResponseEvent(response.toString()));
				           	   //Log.d("json back", response.toString());
				           		//TaskData.onlinestatus="(����ʹ��)";
				           	    SharedPreferences loginsp = getApplication().getSharedPreferences("userinfo" , MODE_PRIVATE);
				                Editor editor = loginsp.edit();
				           		int times = loginsp.getInt("logintimes", 0);
				                String lastlogintime=new Timestamp(new Date().getTime()).toString();
				    		    editor.putString("phoneNo",TaskData.user);
				    		    editor.putString("lastlogintime",lastlogintime);
				    		    times++;
				    		    editor.putInt("logintimes",times);
				    		    Log.d(Tags,"logintimes "+times);
				    		    editor.commit();
				    		    TaskData.onlinestatus="(����ʹ��)";   
				           	    Intent intent= new Intent(MainActivity.this,MainActivity_1.class);
		    	    	    	startActivity(intent);
		    	    	    	finish();  	
			           	 
			           	 }  
			              
			            }  
			        },new Response.ErrorListener() {  
					@Override  
				        public void onErrorResponse(VolleyError error) {  
				           // responseText.setText(error.getMessage()); 
						      TaskTool.connFailure(getApplicationContext(), error);
					          Intent intent= new Intent(MainActivity.this,LoginActivity.class);
		    	    	      startActivity(intent);
		    	    	      finish();  	
				        }  
				    });  
				       	
				    requestQueue.add(gsonObjectRequest); 
				    requestQueue.start();
				    				    
	return ;			    
}
    
    public void onClick_Login(View view) {
    	Intent intent= new Intent(MainActivity.this,LoginActivity.class);
    	startActivity(intent);
    	finish();
    	}
    
	@Override  
    public boolean onCreateOptionsMenu(Menu menu) {  
        // Inflate the menu; this adds items to the action bar if it is present.  
        getMenuInflater().inflate(R.menu.main_menu, menu);  
        return true;  
    }
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		//ImageView selImg = (ImageView)mNumLayout.getChildAt(position);
		//selImg.setBackgroundColor(R.drawable.circle_selected);
		for (int i = 0; i < mNumLayout.getChildCount(); i++) {  
            Log.d(Tags,"open slide"+mNumLayout.getChildCount()+position);
            
			if (i == position) {  
                mNumLayout.getChildAt(i).setBackgroundResource(R.drawable.circle_selected);  
            } else {  
                mNumLayout.getChildAt(i).setBackgroundResource(R.drawable.circle_normal);  
            }  
		}
			
	
		
	}	
  
}

	