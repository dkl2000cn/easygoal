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
			//TaskTool.notiSend(getApplicationContext(), "Ŀ���", "��ӭ������!", System.currentTimeMillis());	
			
	   super.onCreate();
	}
	
	/*
	public void login(){
	SharedPreferences preference = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
	int times=preference.getInt("logintimes",1);
	Editor editor = preference.edit(); //��ȡ�༭��
	String userPhone = getPhoneNumber();
	String lastlogintime=new Timestamp(new Date().getTime()).toString();
    editor.putString("userPhone",userPhone);
    editor.putString("lastlogintime",lastlogintime);
    times++;
    editor.putInt("logintimes",times);
    editor.commit();//�ύ�޸�
   
	Log.d("login data",userPhone+" lastloginat:"+lastlogintime+" times:"+times);
	}
	*/
 
	
	public void notiSendini(){
	//����һ��NotificationManager������
	String ns = Context.NOTIFICATION_SERVICE;
	NotificationManager mNotificationManager = (NotificationManager)getSystemService(ns);

	//����Notification�ĸ�������
	Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.user);
			//getResources().getDrawable(R.drawable.ic_launcher); //֪ͨͼ��
	//CharSequence tickerText = "Hello"; //״̬����ʾ��֪ͨ�ı���ʾ
	//long when = System.currentTimeMillis(); //֪ͨ������ʱ�䣬����֪ͨ��Ϣ����ʾ
	//����������Գ�ʼ��Nofification
	
	//Notification notification = new Notification(icon,tickerText,when);
	Notification notification = new Notification.Builder(getBaseContext()).
			setContentText("i love you")
			.setContentTitle("ok")
			.setTicker("Ŀ���")
			.setLargeIcon(icon)
			.setSmallIcon(R.drawable.ic_launcher)
			.getNotification();
			
	
	
	/*
	* �������
	* notification.defaults |=Notification.DEFAULT_SOUND;
	* ����ʹ�����¼��ַ�ʽ
	* notification.sound = Uri.parse("file:///sdcard/notification/ringer.mp3");
	* notification.sound = Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6");
	* �����Ҫ�����������ظ�ֱ���û���֪ͨ������Ӧ���������notification��flags�ֶ�����"FLAG_INSISTENT"
	* ���notification��defaults�ֶΰ�����"DEFAULT_SOUND"���ԣ���������Խ�����sound�ֶ��ж��������
	
	* �����

	* notification.defaults |= Notification.DEFAULT_VIBRATE;

	* ���߿��Զ����Լ�����ģʽ��

	* long[] vibrate = {0,100,200,300}; //0�����ʼ�񶯣���100�����ֹͣ���ٹ�200������ٴ���300����

	* notification.vibrate = vibrate;

	* long������Զ������Ҫ���κγ���

	* ���notification��defaults�ֶΰ�����"DEFAULT_VIBRATE",��������Խ�����vibrate�ֶ��ж������

	*/

	/*

	* ���LED������

	* notification.defaults |= Notification.DEFAULT_LIGHTS;

	* ���߿����Լ���LED����ģʽ:

	* notification.ledARGB = 0xff00ff00;

	* notification.ledOnMS = 300; //����ʱ��

	* notification.ledOffMS = 1000; //���ʱ��

	* notification.flags |= Notification.FLAG_SHOW_LIGHTS;

	*/

	/*

	* �������������

	* notification.flags |= FLAG_AUTO_CANCEL; //��֪ͨ���ϵ����֪ͨ���Զ������֪ͨ

	* notification.flags |= FLAG_INSISTENT; //�ظ�����������ֱ���û���Ӧ��֪ͨ

	* notification.flags |= FLAG_ONGOING_EVENT; //����֪ͨ�ŵ�֪ͨ����"Ongoing"��"��������"����

	* notification.flags |= FLAG_NO_CLEAR; //�����ڵ����֪ͨ���е�"���֪ͨ"�󣬴�֪ͨ�������

	* //������FLAG_ONGOING_EVENTһ��ʹ��

	* notification.number = 1; //number�ֶα�ʾ��֪ͨ����ĵ�ǰ�¼�����������������״̬��ͼ��Ķ���

	* //���Ҫʹ�ô��ֶΣ������1��ʼ

	* notification.iconLevel = ; //

	*/

	//����֪ͨ���¼���Ϣ
	Context context = getApplicationContext(); //������
	CharSequence contentTitle = "EasyGoal"; //֪ͨ������
	CharSequence contentText = "welcome on board!"; //֪ͨ������
	Intent notificationIntent = new Intent(context,MainActivity.class); //�����֪ͨ��Ҫ��ת��Activity
	PendingIntent contentIntent = PendingIntent.getActivity(context,0,notificationIntent,0);
	//notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
	//��Notification���ݸ�NotificationManager
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
