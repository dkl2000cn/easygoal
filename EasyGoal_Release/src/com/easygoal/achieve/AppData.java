package com.easygoal.achieve;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import org.apache.http.util.TextUtils;
import org.litepal.LitePalApplication;
import org.litepal.tablemanager.Connector;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class AppData extends Application {
	//private TaskData taskdata;  
	
	 //SQLiteDatabase db;
	
	 //private Boolean alarmclock;
	 //private Boolean alarmRing;
	 //private Boolean vibration;
	 //private Boolean notification;
     //private int sound;
   //public static ArrayList<EventBean> eventlist=new ArrayList<EventBean>();
  //public static ArrayList<MemoBean> memolist=new ArrayList<MemoBean>(); 
	 
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		
			TaskData.TdDB = new ToDoDB(getApplicationContext(), TaskData.db_TdDBname,null, 2);
			TaskData.db_TdDB = TaskData.TdDB.getWritableDatabase();
			//TaskData.TdDB.onCreate(TaskData.db_TdDB);
			LitePalApplication.initialize(getApplicationContext());
		    TaskData.db = Connector.getDatabase();
		    TaskData.hostname="http://123.206.229.72:8080/EasyTest/";   
		    init();
		    TaskData.user=TaskTool.getPhoneNumber(getApplicationContext());
		    TaskData.adapterInit();
		    //initphone(getApplicationContext());
		    
		    //TaskData.hostname="http://192.168.1.104:8080/Easy/";
		    //TaskData.hostname="http://123.206.229.72:8080/EasyTest/";
		   
			//TaskData.performanceflag=0;
			//TaskData.valuelistflag=0;
			//TaskData.privilege=0;
			//TaskData.overduetextcolor=getApplicationContext().getResources().getColor(R.color.overduetextcolor);
			//TaskData.tag_taskhistory="("+TaskTool.getCurTime()+"-"+TaskTool.getPhoneNumber(getApplicationContext())+")"+"\n";
			//TaskData.tag_taskhistory="("+TaskTool.getCurTime()+")"+"\n";
			//TaskData.tag_tasklesson="("+TaskTool.getCurTime()+")";
			
			//TaskData.ls1=new ArrayList<RecordBean>();
			//TaskData.valuelist=null;
			//TaskData.alarmlist=new ArrayList<PendingIntent>();
			//TaskData.bcreceiverlist=new ArrayList<BroadcastReceiver>() ;
			//TaskData.alarmReceiverSet=new HashSet<BroadcastReceiver>();
			//TaskData.subItemlist=new ArrayList<RecordBean>();
			//TaskData.subdellist=new ArrayList<String>();	
			//TaskData.clickedlist=new ArrayList<String>();
			//TaskData.subItems_idno=new ArrayList();
			//TaskData.subItems_deadline=new ArrayList();
			//TaskData.subItems_comments=new ArrayList();
			//TaskData.subItems_weight=new ArrayList();
			//TaskData.subItems_del=new ArrayList();
			//notiSendini();
			//TaskTool.notiSend(getApplicationContext(), "目标达", "欢迎您到来!", System.currentTimeMillis());	
			
	   super.onCreate();
	}
	
	/*
	public void login(){
	SharedPreferences preference = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
	int times=preference.getInt("logintimes",1);
	Editor editor = preference.edit(); //获取编辑器
	String userPhone = getPhoneNumber();
	String lastlogintime=new Timestamp(new Date().getTime()).toString();
    editor.putString("userPhone",userPhone);
    editor.putString("lastlogintime",lastlogintime);
    times++;
    editor.putInt("logintimes",times);
    editor.commit();//提交修改
   
	Log.d("login data",userPhone+" lastloginat:"+lastlogintime+" times:"+times);
	}
	*/
 
	
	public void notiSendini(){
	//创建一个NotificationManager的引用
	String ns = Context.NOTIFICATION_SERVICE;
	NotificationManager mNotificationManager = (NotificationManager)getSystemService(ns);

	//定义Notification的各种属性
	Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.user);
			//getResources().getDrawable(R.drawable.ic_launcher); //通知图标
	//CharSequence tickerText = "Hello"; //状态栏显示的通知文本提示
	//long when = System.currentTimeMillis(); //通知产生的时间，会在通知信息里显示
	//用上面的属性初始化Nofification
	
	//Notification notification = new Notification(icon,tickerText,when);
	Notification notification = new Notification.Builder(getBaseContext()).
			setContentText("i love you")
			.setContentTitle("ok")
			.setTicker("目标达")
			.setLargeIcon(icon)
			.setSmallIcon(R.drawable.ic_launcher)
			.getNotification();
			
	
	
	/*
	* 添加声音
	* notification.defaults |=Notification.DEFAULT_SOUND;
	* 或者使用以下几种方式
	* notification.sound = Uri.parse("file:///sdcard/notification/ringer.mp3");
	* notification.sound = Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6");
	* 如果想要让声音持续重复直到用户对通知做出反应，则可以在notification的flags字段增加"FLAG_INSISTENT"
	* 如果notification的defaults字段包括了"DEFAULT_SOUND"属性，则这个属性将覆盖sound字段中定义的声音
	
	* 添加振动

	* notification.defaults |= Notification.DEFAULT_VIBRATE;

	* 或者可以定义自己的振动模式：

	* long[] vibrate = {0,100,200,300}; //0毫秒后开始振动，振动100毫秒后停止，再过200毫秒后再次振动300毫秒

	* notification.vibrate = vibrate;

	* long数组可以定义成想要的任何长度

	* 如果notification的defaults字段包括了"DEFAULT_VIBRATE",则这个属性将覆盖vibrate字段中定义的振动

	*/

	/*

	* 添加LED灯提醒

	* notification.defaults |= Notification.DEFAULT_LIGHTS;

	* 或者可以自己的LED提醒模式:

	* notification.ledARGB = 0xff00ff00;

	* notification.ledOnMS = 300; //亮的时间

	* notification.ledOffMS = 1000; //灭的时间

	* notification.flags |= Notification.FLAG_SHOW_LIGHTS;

	*/

	/*

	* 更多的特征属性

	* notification.flags |= FLAG_AUTO_CANCEL; //在通知栏上点击此通知后自动清除此通知

	* notification.flags |= FLAG_INSISTENT; //重复发出声音，直到用户响应此通知

	* notification.flags |= FLAG_ONGOING_EVENT; //将此通知放到通知栏的"Ongoing"即"正在运行"组中

	* notification.flags |= FLAG_NO_CLEAR; //表明在点击了通知栏中的"清除通知"后，此通知不清除，

	* //经常与FLAG_ONGOING_EVENT一起使用

	* notification.number = 1; //number字段表示此通知代表的当前事件数量，它将覆盖在状态栏图标的顶部

	* //如果要使用此字段，必须从1开始

	* notification.iconLevel = ; //

	*/

	//设置通知的事件消息
	Context context = getApplicationContext(); //上下文
	CharSequence contentTitle = "EasyGoal"; //通知栏标题
	CharSequence contentText = "welcome on board!"; //通知栏内容
	Intent notificationIntent = new Intent(context,MainActivity.class); //点击该通知后要跳转的Activity
	PendingIntent contentIntent = PendingIntent.getActivity(context,0,notificationIntent,0);
	//notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
	//把Notification传递给NotificationManager
    int ID_NOTIFICATION = 0;
	mNotificationManager.notify(ID_NOTIFICATION,notification);
	Log.d("notification", "start"+contentIntent.toString());
 }		
	  public void init(){
	    	
	    	TaskData.privilege=0; 
	        TaskData.initreminderflag=1;
	 		TaskData.alarmReceiverSet=new HashSet<BroadcastReceiver>();
	 		TaskData.alarmpilist=new ArrayList<PendingIntent>();
	 		TaskData.bcreceiverlist=new ArrayList<BroadcastReceiver>();
	 		TaskData.alarmsourceidlist=new ArrayList<String>();
	 		TaskData.overduetextcolor=getApplicationContext().getResources().getColor(R.color.overduetextcolor);
	 		TaskData.color_header=getApplicationContext().getResources().getColor(R.color.orange);
	 		//TaskData.tag_taskhistory="("+TaskTool.getCurTime()+"-"+TaskTool.getPhoneNumber(getApplicationContext())+")"+"\n";
	 		TaskData.tag_taskhistory="("+TaskTool.getCurTime()+")"+"\n";
	 		TaskData.tag_tasklesson="("+TaskTool.getCurTime()+")";
	 		
	 		TaskData.ls1=new ArrayList<RecordBean>();
	 		TaskData.valuelist=null;
	 		TaskData.valuelistflag=0;  
	 		//TaskData.alarmlist=new ArrayList<PendingIntent>();
	 		//TaskData.bcreceiverlist=new ArrayList<BroadcastReceiver>() ;
	 		
	 		TaskData.subItemlist=new ArrayList<RecordBean>();
	 		TaskData.subdellist=new ArrayList<String>(); 
	 		
	    }
   
public void initphone(Context context){
		  
	  if (!TextUtils.isEmpty(TaskTool.getPhoneNumber(context))&&TaskTool.isMobileNO(TaskTool.getPhoneNumber(context))){
		    TaskData.user=TaskTool.getPhoneNumber(context);
		    TaskData.adapterInit();
	  } 	
	//Toast.makeText(getApplicationContext(), "phoneNo:"+TaskData.user, Toast.LENGTH_SHORT).show();
 }
	
  
}
