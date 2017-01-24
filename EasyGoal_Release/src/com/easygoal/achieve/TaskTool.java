package com.easygoal.achieve;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.litepal.crud.DataSupport;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import android.Manifest;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class TaskTool{

	public static String getCurTime(){
	 SimpleDateFormat formatter = new SimpleDateFormat ("yy-MM-dd HH:mm");
	 Date curDate = new Date();//获取当前时间
	 String curTime = formatter.format(curDate);
	 return curTime;
	}
	
	public static String getCurDate(){
		 SimpleDateFormat formatter = new SimpleDateFormat ("yyMMdd");
		 String curDate = formatter.format(new Date());
		 return curDate;
		}
	
	public static void notiSend(Context context,String contentTitle,String contentText,long when){
		//创建�??个NotificationManager的引�??
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(ns);
        
		Resources res = context.getResources();
		//Notification notification = new Notification(icon,tickerText,when);
		Notification notification = new Notification.Builder(context).
				setContentText(contentText)
				.setContentTitle(contentTitle)
				.setTicker(contentTitle)
				.setWhen(when)
				.setDefaults(Notification.DEFAULT_VIBRATE)
				.setVibrate(new long[] {0,300,500,700})  
				//.setLargeIcon(BitmapFactory.decodeResource(res,R.drawable.logo))
				.setSmallIcon(R.drawable.logo)
				.getNotification();
				//.build();				
		
	    int ID_NOTIFICATION = 0;
	    Log.d("noti", "ready");
		mNotificationManager.notify(ID_NOTIFICATION,notification);
		Log.d("noti", "sent out");
		
	 }
	
	public static void  sendSMS(Context context,String smsMsg){ 
		
		if (TaskData.user!=null&&isMobileNO(TaskData.user)){
		  String smsBody = smsMsg;
		  Uri smsToUri = Uri.parse( "smsto:" );  
		  Intent sendIntent =  new  Intent(Intent.ACTION_VIEW, smsToUri);
		  sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		  sendIntent.putExtra("address", TaskData.user); // 电话号码，这行去掉的话，默认就没有电�??   
		  sendIntent.putExtra( "sms_body", smsBody);  
		  sendIntent.setType( "vnd.android-dir/mms-sms");  
		  context.startActivity(sendIntent);  
		} 
    }  
	
	public static void  FastSendSMS(Context context,String phoneNo,String smsMsg){  
		  SmsManager manager = SmsManager.getDefault();
		  ArrayList<String> list = manager.divideMessage("[目标达]您的密码为:"+smsMsg);  //因为�?条短信有字数限制，因此要将长短信拆分  
          for(String text:list){  
              manager.sendTextMessage(phoneNo, null, text, null, null);  
          }  
		  //Toast.makeText(context, "发�?�完�?", Toast.LENGTH_SHORT).show();  
	}  
	
	public static boolean requestPhonePrivilege(Context context){ 
		  if(Build.VERSION.SDK_INT>=23){  	
		    	 return false;
		    	       
		  }else{
			     return true;
		  }
	}
		 
	public static String getPhoneNumber(Context context){  
	    TelephonyManager mTelephonyMgr;  
	    mTelephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);    
	    String pn;
	    //Toast.makeText(context, "SDK:"+Build.VERSION.SDK_INT, Toast.LENGTH_SHORT).show();
	    if(Build.VERSION.SDK_INT>=23){
	    	int checkPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
	    	        if (checkPermission != PackageManager.PERMISSION_GRANTED) {
	    	            //ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.READ_PHONE_STATE}, 1); 
	    	        	 //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
	    	        	return "test";
	    	        }else {
	    	        	 if (!TextUtils.isEmpty(mTelephonyMgr.getLine1Number())){
	    	       	      pn=mTelephonyMgr.getLine1Number();
	    	       	    }else{
	    	       	    	//Toast.makeText(context, "phoneNo:empty", Toast.LENGTH_SHORT).show();
	    	       	      pn="test";
	    	       	    }
	    	       	    return pn;	
	    	        }
	    }else{
	    	   if (!TextUtils.isEmpty(mTelephonyMgr.getLine1Number())){
	       	      pn=mTelephonyMgr.getLine1Number();
	       	    }else{
	       	    	//Toast.makeText(context, "phoneNo:empty", Toast.LENGTH_SHORT).show();
	       	      pn="test";
	       	    }
	       	    return pn;
	    }
	}   

	public static boolean isMobileNO11(String mobiles){  
		 
		if (mobiles!=null&&!TextUtils.isEmpty(mobiles)){
			//Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Pattern p = Pattern.compile("^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$"); 
			Matcher m = p.matcher(mobiles);   
		    return m.matches();  
		}else{
			return false;
		}
	}  
	
	public static boolean isMobileNO(String mobiles){  
		 
		if (mobiles!=null&&!TextUtils.isEmpty(mobiles)){
			Pattern p = Pattern.compile("^.*(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");  
			Matcher m = p.matcher(mobiles);   
		    return m.matches();  
		}else{
			return false;
		}
	}  
	
	public static int getConnCode(Context context){
		
		  ConnectivityManager con = (ConnectivityManager)context.getSystemService(Activity.CONNECTIVITY_SERVICE);  ;
	       boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();  
		   boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();  
		   
		  if (wifi==true&& internet==false){
			  return 1;
		  }
		  if (wifi==false&& internet==true){
			  return 2;
		  }
		  if (wifi==true&& internet==true){
			  return 3;
		  }
		return 0;		
	}
	
	public static Fragment showFrag(FragmentManager fm,Fragment from_fg,Fragment[] frag,int i){
	    
		  FragmentTransaction ft =fm.beginTransaction();
          ft.setTransition(FragmentTransaction.TRANSIT_NONE);
	       if (from_fg==null){
	    	   ft.add(R.id.main_layout, frag[i]).commit(); 
	    	     
	       }else{
	    	   if (!frag[i].isAdded()){
	    		 
	    		// ft.hide(from_fg);
	    		 
	    		 ft.hide(from_fg).add(R.id.main_layout,frag[i]).commit(); 
	    		
	    	   }
	    	   else{
	    		   ft.hide(from_fg).show(frag[i]).commit();
	    		   
	    	   }
	         };  
	       from_fg=frag[i];   
		   return from_fg;      
	 }
	

	public static String addZero(int sourceData,int formatLength){
		
	   String newString = String.format("%0"+formatLength+"d", sourceData);
		
      return newString;
	};
	
   public static void showDialog(DialogFragment dialogFragment) {  
        
     if (TaskData.fm!=null&&dialogFragment!= null){  
          //  dialogFragment = new Fragment_Search();  
        //dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);  
        dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        TaskData.fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_NONE).commit();
        dialogFragment.show(TaskData.fm, "dialog");  
     }
   }
   
   public static void showDialog(FragmentManager fm,DialogFragment dialogFragment) {  
       
	     if (fm!=null&&dialogFragment!= null){  
	          //  dialogFragment = new Fragment_Search();  
	        //dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);  
	        dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
	        fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_NONE).commit();
	        dialogFragment.show(fm, "dialog");  
	    }
   }
   
   public static void UpdateUserRequestPost(Context context,String servletName,Map params){ 
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
		               	 Toast.makeText(mContext, "no data",Toast.LENGTH_SHORT).show();
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
			    				    
return;			    
}
   
	
	public static boolean isNumeric(String str){    
		  //Pattern pattern = Pattern.compile("[0-9]*");    
		  Pattern pattern = Pattern.compile("^[.0-9]+$"); 
		  Matcher isNum = pattern.matcher(str); 
		  
		  if( !isNum.matches() ){   
		      return false;    
		 
		        } 
		  return true;
	      
    } 
	
	public static boolean isPwdFormat(String str){    
		  //Pattern pattern = Pattern.compile("[0-9]*");    
		  Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$"); 
		  Matcher isNum = pattern.matcher(str); 
		  
		  if( !isNum.matches() ){   
		      return false;    
		 
		        } 
		  return true;
	      
  } 
	
	/*
	public static boolean isTimeFormat(String str){    
		  //Pattern pattern = Pattern.compile("[0-9]*");    
		  Pattern pattern = Pattern.compile("^[0-9]{2}+"-"+[0-9]{2}+"-"+[0-9][0-9]+"."+[0-9][0-9]+":"+[0-9][0-9]+"-"+$"); 
		  Matcher isNum = pattern.matcher(str); 
		  
		  if( !isNum.matches() ){   
		      return false;    
		 
		        }    
		        return true;    
  } */  

	public static void replaceheader(Context context,TextView tv,String text,String target){
		
		SpannableString spanString = new SpannableString(text);
 	   	AbsoluteSizeSpan span_size = new AbsoluteSizeSpan(28);
 	    ForegroundColorSpan span_color=new ForegroundColorSpan(TaskData.overduetextcolor);
		//StyleSpan span_style = new StyleSpan(Typeface.ITALIC); 
		spanString.setSpan(span_size, 0,target.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spanString.setSpan(span_color, 0,target.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);   
 	    tv.append(spanString);
	
	}
	
	public static void replace(Context context,TextView tv,String target,int resid){    
	    String temp=tv.getText().toString();    
	    //SpannableStringBuilder spannable = new SpannableStringBuilder(temp);    
	    //CharacterStyle span=null;    
	    Pattern p = Pattern.compile(target);    
	    Matcher m = p.matcher(temp);    
	    SpannableString spanString= new SpannableString(temp);
	   
	    int pos_spanstart=0;
		int pos_spanend=0;
		
	    while (m.find()) {    
	        //span = new ForegroundColorSpan(Color.RED);//�??要重复！  
	        //span = new ImageSpan(drawable,ImageSpan.XX);//设置现在图片 
	    	
	    	//list_imgspan.add(imgspan);
	    	pos_spanstart=m.start();
	    	pos_spanend=m.end();
	    	Bitmap b = BitmapFactory.decodeResource(context.getResources(),resid);
    		ImageSpan imgspan = new ImageSpan(context, b);
	    	//ForegroundColorSpan colorspan = new ForegroundColorSpan(Color.RED);
	    	spanString.setSpan(imgspan , pos_spanstart, pos_spanend,  
	                          Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	    	//i++;
	    	//SpannableString subspanString = spanString.subSequence(pos_textstart,  pos_textend);
	    	//str_start=spanString.subSequence(0, start)
	    	
	    } 
	    tv.setText(spanString);   
	}  
	
	public static void replaceTextFormat(Context context,TextView tv,String target){    
	    String temp=tv.getText().toString();    
	    //SpannableStringBuilder spannable = new SpannableStringBuilder(temp);    
	    //CharacterStyle span=null;    
	    Pattern p = Pattern.compile(target);    
	    Matcher m = p.matcher(temp);    
	    SpannableString spanString= new SpannableString(temp);
	   
	    int pos_spanstart=0;
		int pos_spanend=0;
		
	    while (m.find()) {    
	        //span = new ForegroundColorSpan(Color.RED);//�??要重复！  
	        //span = new ImageSpan(drawable,ImageSpan.XX);//设置现在图片 
	    	
	    	//list_imgspan.add(imgspan);
	    	pos_spanstart=m.start();
	    	pos_spanend=m.end();
	    	
	 	   	AbsoluteSizeSpan span_size = new AbsoluteSizeSpan(32);
	 	    ForegroundColorSpan span_color=new ForegroundColorSpan(TaskData.overduetextcolor);
			//StyleSpan span_style = new StyleSpan(Typeface.ITALIC); 
			spanString.setSpan(span_size, pos_spanstart, pos_spanend, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			spanString.setSpan(span_color, pos_spanstart, pos_spanend,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);   
	    	
	    } 
	    tv.setText(spanString);   
	}  
	
	public static void replaceTextFormat(Context context,TextView tv,String target,int size,int color,int style){    
	    String temp=tv.getText().toString();    
	    //SpannableStringBuilder spannable = new SpannableStringBuilder(temp);    
	    //CharacterStyle span=null;    
	    Pattern p = Pattern.compile(target);    
	    Matcher m = p.matcher(temp);    
	    SpannableString spanString= new SpannableString(temp);
	   
	    int pos_spanstart=0;
		int pos_spanend=0;
		
	    while (m.find()) {    
	        //span = new ForegroundColorSpan(Color.RED);//�??要重复！  
	        //span = new ImageSpan(drawable,ImageSpan.XX);//设置现在图片 
	    	
	    	//list_imgspan.add(imgspan);
	    	pos_spanstart=m.start();
	    	pos_spanend=m.end();
	    	
			StyleSpan span_style = new StyleSpan(style); 
			if (size!=0){
			  AbsoluteSizeSpan span_size = new AbsoluteSizeSpan(size);
			  spanString.setSpan(span_size, pos_spanstart, pos_spanend, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	        }
			if (color!=0){
			   ForegroundColorSpan span_color=new ForegroundColorSpan(color);
			   spanString.setSpan(span_color, pos_spanstart, pos_spanend,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			if (style!=0){
			   spanString.setSpan(span_style, pos_spanstart, pos_spanend,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);   
			}
	    } 
	    tv.setText(spanString);   
	}  
	
	public static SpannableString GetSpan(Context context,String target,int size,int color,int style){
		
		SpannableString spanString = new SpannableString(target);
 	   	AbsoluteSizeSpan span_size = new AbsoluteSizeSpan(size);
 	    ForegroundColorSpan span_color=new ForegroundColorSpan(color);
		StyleSpan span_style = new StyleSpan(style); 
		spanString.setSpan(span_size, 0,target.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spanString.setSpan(span_color, 0,target.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
		spanString.setSpan(span_style, 0,target.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);   
 	    return spanString;
	}
	
	public static SpannableString GetSpan(Context context,String target,int size,int color){
		
		SpannableString spanString = new SpannableString(target);
 	   	AbsoluteSizeSpan span_size = new AbsoluteSizeSpan(size);
 	    ForegroundColorSpan span_color=new ForegroundColorSpan(color); 
		spanString.setSpan(span_size, 0,target.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spanString.setSpan(span_color, 0,target.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);   
 	    return spanString;
	}
	
public static SpannableString GetSpan(Context context,String text){
		
		SpannableString spanString = new SpannableString(text);
 	   	AbsoluteSizeSpan span_size = new AbsoluteSizeSpan(20);
 	    ForegroundColorSpan span_color=new ForegroundColorSpan(TaskData.overduetextcolor);
		StyleSpan span_style = new StyleSpan(Typeface.ITALIC); 
		spanString.setSpan(span_size, 0,text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spanString.setSpan(span_color, 0,text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
		spanString.setSpan(span_style, 0,text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);   
 	    return spanString;
	}
	
	   public static SpannableString GetHeaderSpan(Context context,String text){
			
			SpannableString spanString = new SpannableString(text);
	 	   	AbsoluteSizeSpan span_size = new AbsoluteSizeSpan(32);
	 	    ForegroundColorSpan span_color=new ForegroundColorSpan(TaskData.overduetextcolor);
			//StyleSpan span_style = new StyleSpan(Typeface.ITALIC); 
			spanString.setSpan(span_size, 0,text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			spanString.setSpan(span_color, 0,text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
			//spanString.setSpan(span_style, 0,text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);   
	 	    return spanString;
		}
	
	
	
	public static boolean setReminder(Context context,String deadline,String sourceId,String name,String content){
		final SharedPreferences  alarmSettingSP=context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
		//int freq= alarmSettingSP.getInt("freq", 1); 
		int freq=1;
		int interval= alarmSettingSP.getInt("alarminterval", 5);
		int advance= alarmSettingSP.getInt("advance", 0); 
		int lastRowId = 0;
		SimpleDateFormat formatter = new SimpleDateFormat ("yy-MM-dd HH:mm");
		Date curDate = new Date();//获取当前时间
		String curTime = formatter.format(curDate);	
		Reminder reminder=new Reminder();
		//reminder.setAdvance(et_eventadvance.getText().toString());
	    reminder.setSourceId(sourceId);
	    reminder.setUsername(TaskData.user);
	    reminder.setName(name);
		reminder.setContent(content);
		reminder.setCreatedtime(curTime);
		reminder.setDeadlinetime(deadline);
		//reminder.setFrequency(et_reminderfreq.getText().toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
		String dead=deadline;
		long a=0;
		try {
			a=sdf.parse(dead).getTime();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//a = TimeData.changeStrToTime_YYYY(et_eventdeadlinetime.getText().toString());
		long b=new Date().getTime();
		String deadline_yyyy=TimeData.changeStrTime_YYToTime_YYYY(deadline);
		long deadlineData= TimeData.changeStrToMillisTime_YY(deadline_yyyy);
		//String timegap=TimeData.changeLongToTimeStr(a-b);
		//reminder.setRemainingtime(timegap);
		//Toast.makeText(context, deadline+"\n"+deadlineData+"\n"+new Date().getTime(), Toast.LENGTH_LONG).show();
		reminder.setAdvance(String.valueOf(advance));
		reminder.setFrequency(String.valueOf(freq));
		reminder.setAlarminterval(String.valueOf(interval));
		reminder.save();
		//TaskData.eventlist.add(reminder);
		//DataSupport.saveAll(TaskData.eventlist);
		
		 new Alarm(context,
				   sourceId,
				   name,	 
				   deadlineData,
	  		   freq,
	  		   advance,
	  		   interval).alarmset();
		
		 Cursor cur= DataSupport.findBySQL("select LAST_INSERT_ROWID() from reminder");
			   if (cur!=null&&cur.getCount()>0){
		    	cur.moveToFirst();
				lastRowId=cur.getInt(0);
			   }	
			   String t_sn=TaskData.user+"r";
			   reminder.setSn(t_sn+TaskTool.addZero(lastRowId,10));
			   reminder.save();
	 
		TaskData.cursor_reminder.requery();
		if (TaskData.adapter_reminder!=null){
		TaskData.adapter_reminder.notifyDataSetChanged();
		}
		//Toast.makeText(getActivity(),"a:"+a+"\n"+"b:"+b, Toast.LENGTH_SHORT).show();
	    //new Alarm(getActivity(),a).alarmset();
	  return true;
	}
  
	public static boolean RegisterAllReminders(Context context){
		 Cursor c_alarm=DataSupport.findBySQL("select id as _id,name,sourceId,deadlinetime,frequency,advance,alarminterval,piaction from reminder where piaction!='0' and username="+"'"+TaskData.user+"'"+";");
			
			
			if (c_alarm!=null&&c_alarm.getCount()>0){
				 c_alarm.moveToFirst();
				
			 	do {
					int alarm_recurring = 0;
			 		int alarm_interval = 0;
					int alarm_advance = 0;
					long deadlinetimeData=0;
					String deadlinetime= c_alarm.getString(c_alarm.getColumnIndex("deadlinetime"));
					SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
					        try {
					        	deadlinetimeData=sdf.parse(deadlinetime).getTime();
					        } catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
					         	e.printStackTrace();
					        }
				  
					    if ( c_alarm.getString(6)!=null){		
					    	alarm_advance=c_alarm.getInt(c_alarm.getColumnIndex("advance"));
						    alarm_recurring = Integer.parseInt(c_alarm.getString(4));
						    alarm_interval = Integer.parseInt(c_alarm.getString(6));
					        alarm_advance = Integer.parseInt(c_alarm.getString(5));
						    //t=alarm_advance*60*1000;
					    }	
				        long timegap=deadlinetimeData-new Date().getTime()-alarm_advance*60*1000;
				      
				        long reminder_id = c_alarm.getLong(0);
				       
				      /*  
				    if (timegap<-3*60*1000){
					         //alarmgr.cancel(TaskData.alarmlist.get(c_alarm.getPosition()));
					         DataSupport.delete(Reminder.class, reminder_id );
				             //TaskData.alarmlist.remove(selPos);
					         c_alarm.requery();
					         //Toast.makeText(getActivity(), "已删�?"+selId, Toast.LENGTH_SHORT).show();
					          //TaskData.adapter_alltasks.notifyDataSetChanged();
					         //TaskData.adapter_reminder.notifyDataSetChanged();
					         Log.d("time filter", TaskData.cursor_reminder.getPosition()+"filtered");
				    }else{  */
				       new Alarm(context,
				    		   c_alarm.getString(2),
				    		   c_alarm.getString(1),	 
				    		   deadlinetimeData,
				    		   alarm_recurring,
				    		   //Integer.parseInt(c_alarm.getString(c_alarm.getColumnIndex("frequency"))),
				    		   alarm_advance,
				    		   alarm_interval).alarmset();
				       Log.d("|RegisterReminder|","reminder id:"+reminder_id);
		    }while(c_alarm.moveToNext());
			 return true; 	
		}
			return false;	
	}
  
	public static void unregisterReceiver(Context context,BroadcastReceiver bcr){
		 try{
			  context.unregisterReceiver(bcr);
			  Log.d("unregit broadcastReceiver",bcr.toString());
		  }catch (IllegalArgumentException e) { 
				    if (e.getMessage().contains("Receiver not registered")) { 
				        // Ignore this exception. This is exactly what is desired 
				    	 Log.d("unregit broadcastReceiver"," error Receiver not registered");
				    } else { 
				        // unexpected, re-throw 
				    	 Log.d("unregit broadcastReceiver",e.toString());
				        throw e; 
				        
				    } 
		  }		
		  
	}
	
	public static void clearlinkedReminder(Context context,String headername){
	 Cursor cr=DataSupport.findBySQL("select id as _id,sourceId from reminder where sourceId like "+"'"+headername+"%"+"'"+" and username="+"'"+TaskData.user+"';");
		
	 if (cr.getCount()>0){
		   cr.moveToFirst();
		   do{
			   String remindersn = cr.getString(1);
			   Alarm.alarmCancel(context, remindersn);	 
		       int reminderno = cr.getInt(0);
		       DataSupport.delete(Reminder.class, reminderno);
		       Log.d("clear reminder","No."+reminderno);   
		  
		   }while(cr.moveToNext());
	   } 	
	}
	
	public static void deleteReminder(Context context,String sourceId){
		 Cursor cr=DataSupport.findBySQL("select id as _id,sourceId from reminder where sourceId = "+"'"+sourceId+"'"+" and username="+"'"+TaskData.user+"';");
			
		 if (cr.getCount()>0){
			   cr.moveToFirst();
			   do{
				   String remindersn = cr.getString(1);
				   Alarm.alarmCancel(context, remindersn);	 
			       int reminderno = cr.getInt(0);
			       DataSupport.delete(Reminder.class, reminderno);
			       Log.d("delreminder","No."+reminderno);   
			       Toast.makeText(context, "delreminder"+"No."+reminderno, Toast.LENGTH_SHORT).show();
			   }while(cr.moveToNext());
		   } 	
		}
	
	
	public static int AppendFieldText(String tbname,String keyfield,String t_sn,String fieldname,String newvalue){	
		
		ContentValues cv_tu=new ContentValues();
	    String str_field="";
	    String str_field_appended="";
	    String whereas_tu=keyfield+"=?";
		String[] whereValues_tu={t_sn};
		int task_tu = 0;
		
	    if (t_sn!=null&&!TextUtils.isEmpty(t_sn)){
		    Cursor c_d=TaskData.db_TdDB.rawQuery("select * from "+tbname+" where "+
		                  keyfield+"=?  ",
				          new String[]{t_sn});
	        c_d.moveToFirst();
	        
	      if (c_d!=null&&c_d.getCount()>0){
	    	str_field=c_d.getString(c_d.getColumnIndex(fieldname));
	        str_field_appended = str_field+newvalue;
	        cv_tu.put(fieldname, str_field_appended);
		    task_tu=TaskData.db_TdDB.update(TaskData.TdDB.TABLE_NAME_TaskMain,cv_tu,whereas_tu,whereValues_tu);
		    Log.d("Append "+fieldname,t_sn+task_tu);
	      }else{
	    	Log.d("Append "+fieldname,t_sn+"not found");
	      }
	    }  
		return task_tu;
	 }	
	
	public static Uri getResourceUri(Context context, int res) {
        try {
        Context packageContext = context.createPackageContext(context.getPackageName(),
                    Context.CONTEXT_RESTRICTED);
            Resources resources = packageContext.getResources();
            String appPkg = packageContext.getPackageName();
            String resPkg = resources.getResourcePackageName(res);
            String type = resources.getResourceTypeName(res);
            String name = resources.getResourceEntryName(res);


            Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.scheme(ContentResolver.SCHEME_ANDROID_RESOURCE);
            uriBuilder.encodedAuthority(appPkg);
            uriBuilder.appendEncodedPath(type);
            if (!appPkg.equals(resPkg)) {
                uriBuilder.appendEncodedPath(resPkg + ":" + name);
            } else {
                uriBuilder.appendEncodedPath(name);
            }
            return uriBuilder.build();
        
        } catch (Exception e) {
            return null;
        }
    }
	

  public static int GetSubtaskCount(String task_sn){	
			
		    if (TaskData.TdDB.TASK_SN!=null){
			    Cursor c_d=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskRecord+" where "+
			                  TaskData.TdDB.TASK_SN+"=?  ",
					          new String[]{task_sn});
		          c_d.moveToFirst();
		      if (c_d!=null&&c_d.getCount()>0){
		    	    return c_d.getCount();
		      }else{
			    	return 0;
		      }
		    }
			return -1;
 }
	
  public static double isWithSubtasks(){	
		
	    double sum_closeweight=0;
		
	    if (TaskData.TdDB.TASK_SN!=null){
		    Cursor c_d=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
		                  TaskData.TdDB.TASK_SN+"=?  ",
				          new String[]{TaskData.selTaskSN});
	        c_d.moveToFirst();
	      if (c_d!=null&&c_d.getCount()>0){
	    	sum_closeweight=Double.parseDouble(c_d.getString(c_d.getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));
	    	/*
	    	while (c_d.moveToNext()){
	    	 sum_closeweight=sum_closeweight+Double.parseDouble(c_d.getString(c_d.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT)));
	    	}*/
	      }
		//TaskData.totalweight=sum_openweight+sum_closeweight;
	      Log.d("sum_closeweight",sum_closeweight+" "+c_d.getCount()); 
	    }else{
	    	
	    }
		return (sum_closeweight);
	 }
  
  public static double GetTotalClosedWeight(){	
			
		    double sum_closeweight=0;
			
		    if (TaskData.TdDB.TASK_SN!=null){
			    Cursor c_d=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
			                  TaskData.TdDB.TASK_SN+"=?  ",
					          new String[]{TaskData.selTaskSN});
		        c_d.moveToFirst();
		      if (c_d!=null&&c_d.getCount()>0){
		    	sum_closeweight=Double.parseDouble(c_d.getString(c_d.getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));
		    	/*
		    	while (c_d.moveToNext()){
		    	 sum_closeweight=sum_closeweight+Double.parseDouble(c_d.getString(c_d.getColumnIndex(TaskData.TdDB.RECORD_WEIGHT)));
		    	}*/
		      }
			//TaskData.totalweight=sum_openweight+sum_closeweight;
		      Log.d("sum_closeweight",sum_closeweight+" "+c_d.getCount()); 
		    }else{
		    	
		    }
			return (sum_closeweight);
		 }
	 
    public static void connFailure(Context context,VolleyError error){
    	String tags="volly error";
    	NetworkResponse response = error.networkResponse;
		  
	      if (response != null) {
	          switch (response.statusCode) {
	            case 404:Toast.makeText(context, "网络异常，请重试或离线使用", Toast.LENGTH_SHORT).show();
	            	     break;
	            case 422:break;
	            case 401:
	                try {
	                    // server might return error like this { "error": "Some error occured" }
	                    // Use "Gson" to parse the result
	                    HashMap<String, String> result = new Gson().fromJson(new String(response.data),
	                            new TypeToken<Map<String, String>>() {
	                            }.getType());

	                    if (result != null && result.containsKey("error")) {
	                        Log.d(tags,  result.get("error"));
	                    }

	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	                // invalid request
	            
	                Log.d(tags,  error.getMessage());
	                Toast.makeText(context, "访问被拒绝，请重试", Toast.LENGTH_SHORT).show();
	                break;
	            default:
	            	Toast.makeText(context, "服务器繁忙,请重试", Toast.LENGTH_SHORT).show();
	            	Log.d(tags,context.getResources().getString(R.string.generic_server_down));
	            	break;
	            }
	      }
	          Log.d(tags, context.getResources().getString(R.string.generic_error));    
    }
  
    public static void connFailureNoWarning(Context context,VolleyError error){
    	String tags="volly error";
    	NetworkResponse response = error.networkResponse;
		  
	      if (response != null) {
	          switch (response.statusCode) {
	            case 404:
	            	     break;
	            case 422:break;
	            case 401:
	                try {
	                    // server might return error like this { "error": "Some error occured" }
	                    // Use "Gson" to parse the result
	                    HashMap<String, String> result = new Gson().fromJson(new String(response.data),
	                            new TypeToken<Map<String, String>>() {
	                            }.getType());

	                    if (result != null && result.containsKey("error")) {
	                        Log.d(tags,  result.get("error"));
	                    }

	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	                // invalid request
	            
	                Log.d(tags,  error.getMessage());
	               
	                break;
	            default:
	            	
	            	Log.d(tags,context.getResources().getString(R.string.generic_server_down));
	            	break;
	            }
	      }
	          Log.d(tags, context.getResources().getString(R.string.generic_error));    
    }
    
	
		
}
