package com.easygoal.achieve;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class FragmentHome extends Fragment {
	
	
	String curTime;
	public SimpleCursorAdapter adapter;
	int selectedID=0;
	View oldview=null;
    View newview = null;
	Fragment subfg_todaytasks;
	ListView lv_todaytasks;
	String Tags="FragmentHome";
	 //final ToDoDB TdDB = new ToDoDB(getActivity(), db_name,null, 1);
	 
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {  
		View v=inflater.inflate(R.layout.rfg_bottomtab1_home, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 
		
		  final TextView tv_todaydate=(TextView)v.findViewById(R.id.tv_todaydate);
		  final TextView tv1=(TextView)v.findViewById(R.id.task_item1_name_tv);
		  final TextView tv4=(TextView)v.findViewById(R.id.task_item4_importance_tv);
		  final TextView tv5=(TextView)v.findViewById(R.id.task_item5_urgency_tv);
		  final TextView tv6=(TextView)v.findViewById(R.id.task_item6_assess_tv);
		  final TextView tv15=(TextView)v.findViewById(R.id.task_item15_deadline_tv);
		  final TextView tv16=(TextView)v.findViewById(R.id.task_item16_progress_tv);
		  final TextView tv18=(TextView)v.findViewById(R.id.task_item18_status_tv);
		  final TextView tv19=(TextView)v.findViewById(R.id.task_item19_finished_tv);
		  final TextView tv20=(TextView)v.findViewById(R.id.task_item20_delayed_tv);
		  TaskData.tv_opentaskscount_t=(TextView)v.findViewById(R.id.tv_opentaskscount);
		  TaskData.tv_todaytaskscount_t=(TextView)v.findViewById(R.id.tv_todaytaskscount);
		  TaskData.tv_overduetaskscount_t=(TextView)v.findViewById(R.id.tv_overduetaskscount);
		  TaskData.tv_opentaskscount_a=(TextView)v.findViewById(R.id.tv_opentaskscount_a);
		  TaskData.tv_todaytaskscount_a=(TextView)v.findViewById(R.id.tv_todaytaskscount_a);
		  TaskData.tv_overduetaskscount_a=(TextView)v.findViewById(R.id.tv_overduetaskscount_a);
		  TaskData.tv_opentaskscount_c=(TextView)v.findViewById(R.id.tv_opentaskscount_c);
		  TaskData.tv_todaytaskscount_c=(TextView)v.findViewById(R.id.tv_todaytaskscount_c);
		  TaskData.tv_overduetaskscount_c=(TextView)v.findViewById(R.id.tv_overduetaskscount_c);
		  TaskData.tv_opentaskscount_b=(TextView)v.findViewById(R.id.tv_opentaskscount_b);
		  TaskData.tv_todaytaskscount_b=(TextView)v.findViewById(R.id.tv_todaytaskscount_b);
		  TaskData.tv_overduetaskscount_b=(TextView)v.findViewById(R.id.tv_overduetaskscount_b);
		  TaskData.tv_opentaskscount_d=(TextView)v.findViewById(R.id.tv_opentaskscount_d);
		  TaskData.tv_todaytaskscount_d=(TextView)v.findViewById(R.id.tv_todaytaskscount_d);
		  TaskData.tv_overduetaskscount_d=(TextView)v.findViewById(R.id.tv_overduetaskscount_d);
		  TaskData.lv_todaytasks=(ListView)v.findViewById(R.id.todaytasks_lv);
		//  TextView textView = ViewFinder.findViewById(R.id.my_textview); 
		 Button btn_addrecord=(Button)v.findViewById(R.id.task_addrecord_bt);
		 Button btn_taskdelete=(Button)v.findViewById(R.id.task_delete_bt);
		 Button btn_taskupdate=(Button)v.findViewById(R.id.task_update_bt);
		 Button btn_taskclear=(Button)v.findViewById(R.id.task_clear_bt);
		 final Button btn_detail=(Button)v.findViewById(R.id.taskdetails_btn);
		  // final TextView tv1=(TextView)findViewById(R.id.textView2);
		 // final TextView tv2=(TextView)findViewById(R.id.textView3);
		   // final Cursor c = TaskData.db_TdDB.query(TaskData.TdDB.TABLE_NAME_TaskMain, null, null, null, null, null, null);
		  //  Log.d("cursor",String.valueOf(c.getCount())+"position"+String.valueOf(c.getPosition()));
		    final ContentValues cv = new ContentValues();
		  final int click_position;
		  int opentaskscount_t = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+TaskData.TdDB.TASK_STATUS+"=?", new String[]{TaskData.user,"open"}).getCount();
		  TaskData.tv_opentaskscount_t.setText(String.valueOf(opentaskscount_t));
		  int opentaskscount_a= TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+TaskData.TdDB.TASK_STATUS+"=? and "+TaskData.TdDB.TASK_IMPORTANCE+">=? and "+TaskData.TdDB.TASK_URGENCY+">=?", new String[]{TaskData.user,"open","2","2"}).getCount();
		  TaskData.tv_opentaskscount_a.setText(String.valueOf(opentaskscount_a));
		  int opentaskscount_c= TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+TaskData.TdDB.TASK_STATUS+"=? and "+TaskData.TdDB.TASK_IMPORTANCE+">=? and "+TaskData.TdDB.TASK_URGENCY+"<?", new String[]{TaskData.user,"open","2","2"}).getCount();
		  TaskData.tv_opentaskscount_c.setText(String.valueOf(opentaskscount_c));
		  int opentaskscount_b= TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+TaskData.TdDB.TASK_STATUS+"=? and "+TaskData.TdDB.TASK_IMPORTANCE+"<? and "+TaskData.TdDB.TASK_URGENCY+">=?", new String[]{TaskData.user,"open","2","2"}).getCount();
		  TaskData.tv_opentaskscount_b.setText(String.valueOf(opentaskscount_b));
		  int opentaskscount_d= TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+TaskData.TdDB.TASK_STATUS+"=? and "+TaskData.TdDB.TASK_IMPORTANCE+"<? and "+TaskData.TdDB.TASK_URGENCY+"<?", new String[]{TaskData.user,"open","2","2"}).getCount();
		  TaskData.tv_opentaskscount_d.setText(String.valueOf(opentaskscount_d));
		  
		  //java.util.Date currentdate = new java.util.Date();
		  //String todaydate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		  //Toast.makeText(getActivity(), todaydate, Toast.LENGTH_LONG);
		  //Toast.makeText(getActivity(), todaydate, Toast.LENGTH_LONG);
		  
		  Cursor cursor_todaytasks_t = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+TaskData.TdDB.TASK_STATUS+"=? and "+
		                               TaskData.TdDB.TASK_DEADLINEDATE+"=? and "+
		                               TaskData.TdDB.TASK_DEADLINETIMEDATA+">=?", 
		                               new String[]{TaskData.user,"open",
		                               new SimpleDateFormat ("yy-MM-dd").format(new Date()),
		                       		   String.valueOf(new Date().getTime()/(1000*60))});
		  int todaytaskscount_t = cursor_todaytasks_t.getCount(); 
		  TaskData.tv_todaytaskscount_t.setText(String.valueOf(todaytaskscount_t));
		  Cursor cursor_todaytasks_a = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+TaskData.TdDB.TASK_STATUS+"=? and "+
                  TaskData.TdDB.TASK_DEADLINEDATE+"=? and "+
                  TaskData.TdDB.TASK_DEADLINETIMEDATA+">=? and "+
                  TaskData.TdDB.TASK_IMPORTANCE+">=? and "+
                  TaskData.TdDB.TASK_URGENCY+">=? ", 
                  new String[]{TaskData.user,"open",
                  new SimpleDateFormat ("yy-MM-dd").format(new Date()),
          		   String.valueOf(new Date().getTime()/(1000*60)),"2","2"});
		  int todaytaskscount_a = cursor_todaytasks_a.getCount(); 
		  TaskData.tv_todaytaskscount_a.setText(String.valueOf(todaytaskscount_a));
		  Cursor cursor_todaytasks_c = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+TaskData.TdDB.TASK_STATUS+"=? and "+
                  TaskData.TdDB.TASK_DEADLINEDATE+"=? and "+
                  TaskData.TdDB.TASK_DEADLINETIMEDATA+">=? and "+
                  TaskData.TdDB.TASK_IMPORTANCE+">=? and "+
                  TaskData.TdDB.TASK_URGENCY+"<? ", 
                  new String[]{TaskData.user,"open",
                  new SimpleDateFormat ("yy-MM-dd").format(new Date()),
          		   String.valueOf(new Date().getTime()/(1000*60)),"2","2"});
		  int todaytaskscount_c = cursor_todaytasks_c.getCount(); 
		  TaskData.tv_todaytaskscount_c.setText(String.valueOf(todaytaskscount_c));
		  Cursor cursor_todaytasks_b = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+TaskData.TdDB.TASK_STATUS+"=? and "+
                  TaskData.TdDB.TASK_DEADLINEDATE+"=? and "+
                  TaskData.TdDB.TASK_DEADLINETIMEDATA+">=? and "+
                  TaskData.TdDB.TASK_IMPORTANCE+"<? and "+
                  TaskData.TdDB.TASK_URGENCY+">=? ", 
                  new String[]{TaskData.user,"open",
                  new SimpleDateFormat ("yy-MM-dd").format(new Date()),
          		   String.valueOf(new Date().getTime()/(1000*60)),"2","2"});
		  int todaytaskscount_b = cursor_todaytasks_b.getCount(); 
		  TaskData.tv_todaytaskscount_b.setText(String.valueOf(todaytaskscount_b));
		  Cursor cursor_todaytasks_d = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+TaskData.TdDB.TASK_STATUS+"=? and "+
                  TaskData.TdDB.TASK_DEADLINEDATE+"=? and "+
                  TaskData.TdDB.TASK_DEADLINETIMEDATA+">=? and "+
                  TaskData.TdDB.TASK_IMPORTANCE+"<? and "+
                  TaskData.TdDB.TASK_URGENCY+"<? ", 
                  new String[]{TaskData.user,"open",
                  new SimpleDateFormat ("yy-MM-dd").format(new Date()),
          		   String.valueOf(new Date().getTime()/(1000*60)),"2","2"});
		  int todaytaskscount_d = cursor_todaytasks_d.getCount(); 
		  TaskData.tv_todaytaskscount_d.setText(String.valueOf(todaytaskscount_d));
		  
		  Cursor cursor_overduetasks_t = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+
		                                 TaskData.TdDB.TASK_STATUS+"=? and "+
		                                 TaskData.TdDB.TASK_DEADLINETIMEDATA+"<? "
				                         , new String[]{TaskData.user,"open",String.valueOf(new Date().getTime()/(1000*60))});
		  int overduetaskscount_t = cursor_overduetasks_t.getCount(); 
		  TaskData.tv_overduetaskscount_t.setText(String.valueOf(overduetaskscount_t));
		  Cursor cursor_overduetasks_a =  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+
		          TaskData.TdDB.TASK_STATUS+"=? and "+
                  TaskData.TdDB.TASK_DEADLINETIMEDATA+"<? and "+
                  TaskData.TdDB.TASK_IMPORTANCE+">=? and "+
                  TaskData.TdDB.TASK_URGENCY+">=? ", 
                  new String[]{TaskData.user,"open",    
          		   String.valueOf(new Date().getTime()/(1000*60)),"2","2"});
		  int overduetaskscount_a = cursor_overduetasks_a.getCount(); 
		  TaskData.tv_overduetaskscount_a.setText(String.valueOf(overduetaskscount_a));
		  Cursor cursor_overduetasks_c =  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+
				  TaskData.TdDB.TASK_STATUS+"=? and "+
                  TaskData.TdDB.TASK_DEADLINETIMEDATA+"<? and "+
                  TaskData.TdDB.TASK_IMPORTANCE+">=? and "+
                  TaskData.TdDB.TASK_URGENCY+"<? ", 
                  new String[]{TaskData.user,"open",    
          		   String.valueOf(new Date().getTime()/(1000*60)),"2","2"});
		  int overduetaskscount_c = cursor_overduetasks_c.getCount(); 
		  TaskData.tv_overduetaskscount_c.setText(String.valueOf(overduetaskscount_c));
		  Cursor cursor_overduetasks_b =  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+
				  TaskData.TdDB.TASK_STATUS+"=? and "+
                  TaskData.TdDB.TASK_DEADLINETIMEDATA+"<? and "+
                  TaskData.TdDB.TASK_IMPORTANCE+"<? and "+
                  TaskData.TdDB.TASK_URGENCY+">=? ", 
                  new String[]{TaskData.user,"open",    
          		   String.valueOf(new Date().getTime()/(1000*60)),"2","2"});
		  int overduetaskscount_b = cursor_overduetasks_b.getCount(); 
		  TaskData.tv_overduetaskscount_b.setText(String.valueOf(overduetaskscount_b));
		  Cursor cursor_overduetasks_d =  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+" =? and "+
				  TaskData.TdDB.TASK_STATUS+"=? and "+
                  TaskData.TdDB.TASK_DEADLINETIMEDATA+"<? and "+
                  TaskData.TdDB.TASK_IMPORTANCE+"<? and "+
                  TaskData.TdDB.TASK_URGENCY+"<? ", 
                  new String[]{TaskData.user,"open",    
          		   String.valueOf(new Date().getTime()/(1000*60)),"2","2"});
		  int overduetaskscount_d = cursor_overduetasks_d.getCount(); 
		  TaskData.tv_overduetaskscount_d.setText(String.valueOf(overduetaskscount_d));
		  
		  
		  
		  
		  SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 ");
			 Date curDate = new Date();//获取当前时间
			
			 String curTime = formatter.format(curDate);
	      Calendar calendar = Calendar.getInstance();  
	            int day = calendar.get(Calendar.DAY_OF_WEEK);  
	            String weekday= null;  
	            if (day == 2) {  
	                weekday = "星期一";  
	            } else if (day == 3) {  
	                weekday = "星期二";  
	            } else if (day == 4) {  
	            	weekday = "星期三";  
	            } else if (day == 5) {  
	            	weekday = "星期四";  
	            } else if (day == 6) {  
	            	weekday = "星期五";  
	            } else if (day == 7) {  
	            	weekday = "星期六";  
	            } else if (day == 1) {  
	            	weekday = "星期日";  
	            }  
	            String FullcurTime = curTime+" "+weekday;
		  tv_todaydate.setText(FullcurTime);
		  
		  /*
		  SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
		   java.util.Date now = df.parse("2004-03-26 13:31:40");
		   java.util.Date date=df.parse("2004-01-02 11:30:24");
		   long l=now.getTime()-date.getTime();
		   long day=l/(24*60*60*1000);
		   long hour=(l/(60*60*1000)-day*24);
		   long min=((l/(60*1000))-day*24*60-hour*60);
		   long s=(l/1000-day*24*60*60-hour*60*60-min*60);
		   System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
		  */
		  String[] itemlist_todaytasks={
				  TaskData.TdDB.TASK_NAME, 
				  TaskData.TdDB.TASK_IMPORTANCE,
				  TaskData.TdDB.TASK_URGENCY,
				  TaskData.TdDB.TASK_ASSESSMENT,
				  TaskData.TdDB.TASK_DEADLINE,
				  TaskData.TdDB.TASK_PROGRESS,
				  //TaskData.TdDB.TASK_STATUS,
				  //TaskData.TdDB.TASK_FINISHED,
				  //TaskData.TdDB.TASK_DELAYED    		
		  };
			int[] controllist_todaytasks={
					R.id.task_item1_name_tv,
					R.id.task_item4_importance_tv,
					R.id.task_item5_urgency_tv,
					R.id.task_item6_assess_tv,
					R.id.task_item15_deadline_tv,
					R.id.task_item16_progress_tv,
					//R.id.task_item18_status_t,
					//R.id.task_item19_finished_et,
					//R.id.task_item20_delayed_et,					
			};
		 // TaskData.tv_todaytaskscount.setText(String.valueOf(TaskData.cursor_todaytasks.getCount()));
		   String a = TaskData.TdDB.TABLE_NAME_TaskMain;
	       String b = TaskData.TdDB.TABLE_NAME_TaskRecord;
	   //  TaskData.cursor_todaytasks=TaskData.db_TdDB.rawQuery("select * from "+a+" where "+"df2.parse("+TaskData.TdDB.TASK_DEADLINEDATE+")>?", new String[]{curTime});
	      TaskData.cursor_todaytasks = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+"=? and "+TaskData.TdDB.TASK_STATUS+"=? and "+
	                 TaskData.TdDB.TASK_DEADLINETIMEDATA+"<=?"+
	                 " order by "+TaskData.TdDB.TASK_SEQUENCENO+" asc", 
	                 new String[]{TaskData.user,"open",
	         		  String.valueOf(TimeData.todayEndTimeData())});
	      TaskData.todaycontext=getActivity();
	      
	      TaskData.adapter_todaytasks = new mcAdapter_opentasks(TaskData.todaycontext,R.layout.lv_todaytasks,TaskData.cursor_todaytasks);
		  //Toast.makeText(getActivity(), String.valueOf(TaskData.cursor_todaytasks.getCount()),Toast.LENGTH_SHORT).show();
	       Log.d("adapter_todaytasks",TaskData.adapter_todaytasks.toString() );
		  // todayshow();
	       TaskData.lv_todaytasks.setAdapter(TaskData.adapter_todaytasks);
		 
  	    Log.d(Tags,"lv todaytasks show data ok "+TaskData.adapter_todaytasks.toString());
  	  
  	    
  	    
  	  /*  subfg_todaytasks=new FragmentTodayTasks();
  	  FragmentTransaction ft =getFragmentManager().beginTransaction();
		ft.add(R.id.sublayout_todaytask, subfg_todaytasks).commit(); 
	    Log.d("tab selected","set frag0");*/
		 // Toast.makeText(getActivity(), "todaytakscount"+String.valueOf(TaskData.cursor_todaytasks.getCount()),Toast.LENGTH_SHORT).show();
		  
		  //lv_todaytasks.setFocusable(true);
		  //lv_todaytasks.setItemsCanFocus(true);
		  //lv_todaytasks.setSelected(true);
		 /* final Bundle bundle=null;
		  final Handler hd=new Handler(){
			  public void handleMessage(android.os.Message msg) {

				  super.handleMessage(msg); 
			  if(msg.what==1){
				  Bundle no = msg.getData();
				  selectedID=no.getInt("number");
				  Log.d("click at select", String.valueOf(no.getInt("number")));
			  }
			  } 
		  };
		 
		  final Message msg=null;
		  Thread tr=new Thread(){
			  
			  public void run(){
				  
				  m_listview.setOnItemClickListener(new OnItemClickListener(){
			             int ccc;
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							// TODO Auto-generated method stub
			                 ccc = position;
			                 m_listview.setSelection(ccc);
			                 Log.d("Thread click select", String.valueOf(m_listview.getSelectedItemPosition()));
			                 view.setBackgroundColor(Color.RED);
							Log.d("click at", String.valueOf(position)+String.valueOf(ccc));
					     	Log.d("click at select", String.valueOf(m_listview.getSelectedItemPosition()));
					     	msg.what=1;
							 					  
							bundle.putInt("number", ccc);
							  hd.sendMessage(msg);
						
						}
					  });		  
			  }
		  };
		  tr.start();*/
		
		  
		 TaskData.lv_todaytasks.setOnItemClickListener(new OnItemClickListener(){
             int newPos=0;
             int oldPos=0;
              
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				//TaskData.cursor_opentasks.moveToPosition(position);
				
				if (TaskData.cursor_todaytasks.getCount()>0){ 
	              
				     try{ 
				    	 TaskData.selTaskID=String.valueOf(TaskData.cursor_todaytasks.getString(TaskData.cursor_todaytasks.getColumnIndex(TaskData.TdDB.TASK_ID)));
	            	 }catch(Exception e){
	            		 Log.d(Tags, e.toString());
	            		 e.printStackTrace();
	            	 }
				} 
				if (view!=newview){
 					oldview=newview; 
 					newview=view;
 					newview.setBackgroundColor(getResources().getColor(R.color.gray));
 					if (oldview!=null){
 					oldview.setBackgroundColor(getResources().getColor(R.color.mTextColor2));
 					} 
 				}
			}   
		 });	
			
		 TaskData.lv_todaytasks.setOnItemLongClickListener(new OnItemLongClickListener(){
	            int newPos=0;
	            int oldPos=0;
	            
				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long id) {
					// TODO Auto-generated method stub
					
					if (TaskData.cursor_todaytasks.getCount()>0){ 
		                 //TaskData.selTaskID=String.valueOf(TaskData.cursor_alltasks.getString(TaskData.cursor_alltasks.getColumnIndex(TaskData.TdDB.TASK_ID)));
		                 //TaskData.selTaskSN=String.valueOf(TaskData.cursor_alltasks.getString(TaskData.cursor_alltasks.getColumnIndex(TaskData.TdDB.TASK_SN)));
					    
					     try{ 
					    	 TaskData.selTaskID=String.valueOf(TaskData.cursor_todaytasks.getString(TaskData.cursor_todaytasks.getColumnIndex(TaskData.TdDB.TASK_ID)));
						     TaskData.selTaskSN=String.valueOf(TaskData.cursor_todaytasks.getString(TaskData.cursor_todaytasks.getColumnIndex(TaskData.TdDB.TASK_SN)));	
		            	 }catch(Exception e){
		            		 Log.d(Tags, e.toString());
		            		 e.printStackTrace();
		            	 }
					} 
					DialogFragment_TaskDetail dg_taskdetail=new DialogFragment_TaskDetail();
	    	    	TaskTool.showDialog(dg_taskdetail);
		
					return false;
				}
	            
			 });
		 
		 
				//Log.d("click at", String.valueOf(position)+String.valueOf(ccc));
		     	//Log.d("click at select", String.valueOf(m_listview.getSelectedItemPosition()));
		     	/* new Handler().postDelayed(new Runnable() {
		             @Override
		             public void run() {
		            	 m_listview.setSelection(ccc);
		            	 Log.d("Thread click select", String.valueOf(m_listview.getSelectedItemPosition()));
		             }
		         }, 200);
			
			
		  }
		 });
		
		 m_listview.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Log.d("touch show", v.toString()+event.toString());
				return true;
			}
		Object view = null;
				switch (event.getAction()) {
	            case MotionEvent.ACTION_DOWN:
	                //clear();
	                int x = (int) event.getX();
	                int y = (int) event.getY();
	                int position = m_listview.pointToPosition(x, y);
	                int firstVisiblePosition = m_listview.getFirstVisiblePosition();
	                view = m_listview.getChildAt(position-firstVisiblePosition);
	                if(view==null) return false;
	                if(((View) view).isFocusable()){
	                    Log.i("touch show", "ItemView is focusable ");
	                }
	                if(((View) view).isFocusableInTouchMode()){
	                    Log.i("touch show", "ItemView is focusable in touchMode");
	                }
	                if(((View) view).isInTouchMode()){
	                    Log.i("touch show", "device is in touch mode.");
	                }
	                if(m_listview.isFocusable()){
	                    Log.i("touch show", "mListView is focusable ");
	                }
	                if(m_listview.isFocusableInTouchMode()){
	                    Log.i("touch show", "mListView isFocusableInTouchMode in touchMode");
	                }
	                 
	                if(((View) view).isFocused()){
	                    Log.i("touch show", "ItemView have get focus ");
	                }
	                if(m_listview.isFocused()){
	                    Log.i("touch show", "mListView have get focus ");
	                }
	                if(((View) view).isPressed()){
	                    Log.i("touch show", "ItemView have get pressed ");
	                }
	                break;
	            case MotionEvent.ACTION_UP:
	                if(view==null) return false;
	                Log.i("touch show", "OnTouchListener: up is working ");
	                if(((View) view).isFocused()){
	                    Log.i("touch show", "ItemView have get focus ");
	                }
	                break;
	            default:
	                break;
	            }
	            return false;
			}
		
		  });
		  
		  
		 	  
		  TaskData.TaskData.lv_todaytasks.setOnItemSelectedListener(new OnItemSelectedListener(){
			  int ccc;
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				ccc = position;
				
				Log.d("select at111", String.valueOf(position));
		
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			    
		  });
		  
		  btn_taskdelete.setOnClickListener(new OnClickListener(){

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TaskData.cursor_todaytasks.moveToPosition(TaskData.cursor_todaytasks.getPosition());
				int p=TaskData.cursor_todaytasks.getInt(0);
				Log.d("delete row", String.valueOf(p));
				String where=TaskData.TdDB.TASK_ID+"=?";
				String[] whereArgs={Integer.toString(p)};		
				TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain,where, whereArgs);
				Log.d("delete row", "delete OK");
				 Log.d("cursor",String.valueOf(TaskData.cursor_todaytasks.getCount())+"position"+String.valueOf(TaskData.cursor_todaytasks.getPosition()));
				 //AysnTask_AdapterUpdate task = new AysnTask_AdapterUpdate();  
					//Log.d("task create", "new");		
					//task.execute(); 
					TaskData.adapterUpdate();
			
			
			}
	  
		  });
		  btn_taskupdate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TaskData.adapterUpdate();
				
				 Log.d("TdDB adapter update", "all done");
				 Log.d("update cursor",String.valueOf(TaskData.cursor_todaytasks.getCount())+"position"+String.valueOf(TaskData.cursor_todaytasks.getPosition()));
				
				
			} 
		  });
		  
		  btn_addrecord.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					
					// TODO Auto-generated method stub
					 //Log.d("from_fg", from_fg.toString()); 
					DialogFragment_Comment dialogfrag_comment=new DialogFragment_Comment();
						showDialog(dialogfrag_comment);
						Log.d("dialogfrag", dialogfrag_comment.toString());
					
					
					//TaskData.from_fg=showFrag(TaskData.from_fg,R.id.sublayout_task,subfrag_task,3);
				//	Log.d("task tab", "choice3");
				}
			  });
		  
		  btn_taskclear.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					for (int i=0;i<TaskData.cursor_todaytasks.getCount();i++){
				    TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain, null, null);	
					};
					//TaskData.adapter_todaytasks.notifyDataSetChanged();
					TaskData.adapterUpdate();
					
					
					 Log.d("TdDB adapter update", "all done");  
					 Log.d("clear",String.valueOf(TaskData.cursor_todaytasks.getCount())+"position"+String.valueOf(TaskData.cursor_todaytasks.getPosition()));
					
					
				} 
			  });
		 */
		return v;
	 
	 }

	 
	 private void todayshow() {
		// TODO Auto-generated method stub
		 TaskData.cursor_todaytasks = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_STATUS+"=? and "+
                 TaskData.TdDB.TASK_DEADLINEDATE+"=? and "+
                 TaskData.TdDB.TASK_DEADLINETIMEDATA+">?", 
                 new String[]{"open",
                 new SimpleDateFormat ("yy-MM-dd").format(new Date()),
         		   String.valueOf(new Date().getTime()/(1000*60))});
	       TaskData.adapter_todaytasks = new mcAdapter_opentasks(getActivity(),R.layout.lv_todaytasks,TaskData.cursor_todaytasks);
	}


	public Fragment showFrag(Fragment from_fg,int viewframe,Fragment[] frag,int i){
		  FragmentTransaction ft = getFragmentManager().beginTransaction();

	       if (from_fg==null){
	    	   ft.add(viewframe, frag[i]).commit(); 
	    	      
	       }else{
	    	   if (!frag[i].isAdded()){
	    		  
	    		// ft.hide(from_fg);
	    	
	    		 ft.hide(from_fg).add(viewframe,frag[i]).commit(); 
	    		 
	    	   }
	    	   else{
	    		   ft.hide(from_fg).show(frag[i]).commit();
	    		   
	    	   }
	         };  
	       from_fg=frag[i];
		   return from_fg;
	 }
		  
	 protected void showDialog(DialogFragment dialogFragment) {  
         
	        // Create and show the dialog.   
	    if(dialogFragment == null)  
	          //  dialogFragment = new Fragment_aearch();  
	    	   dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);  
	           dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0); 
	         dialogFragment.show(getFragmentManager(), "dialog");  
	    }  
	
	 public int compareDate(String time1,String time2){
			 SimpleDateFormat formatter1 = new SimpleDateFormat ("yy-MM-dd");
				// Date curDate = new Date();//获取当前时间
		     //SimpleDateFormat formatter2 = new SimpleDateFormat ("yy-MM-dd HH:mm");
			Date date1 = null;
			Date date2 = null;
			int result=0;
			try {
				date1 = formatter1.parse(time1);
				date2 = formatter1.parse(time2);
				result=date1.compareTo(date2);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			 
		     return result;
			}
		
	 public int samedaycount(Cursor c){
		 c.moveToFirst();
		  String tskdl;
		  int samedaycount=0;
		  int overduedaycount=0;
		  String todaydate;
		  while(c.moveToNext()){
			  tskdl = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
			 // String datestr_tskdl = convertTime(tskdl);
			 todaydate = new SimpleDateFormat("yy-MM-dd").format(new Date());
		     
			
			 //Toast.makeText(getActivity(), todaydate, Toast.LENGTH_LONG).show();;
			  switch (compareDate(tskdl,todaydate)) {
		      case 0: samedaycount=samedaycount+1;
		              break;
		      case -1:overduedaycount=overduedaycount+1; 
		              break;
		      default:break;        		    	  
		      }
		  }
		  return samedaycount;	 
	 }
	 
	 public int overduedaycount(Cursor c){
		 c.moveToFirst();
		  String tskdl;
		  int overduedaycount=0;
		  String todaydate;
		  while(c.moveToNext()){
			  tskdl = c.getString(c.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
			 // String datestr_tskdl = convertTime(tskdl);
			 todaydate = new SimpleDateFormat("yy-MM-dd").format(new Date());
		     
			  
			 //Toast.makeText(getActivity(), todaydate, Toast.LENGTH_LONG).show();;
			  switch (compareDate(tskdl,todaydate)) {
		      case -1:overduedaycount=overduedaycount+1; 
		              break;
		      default:break;        		    	  
		      }
		  }
		  return overduedaycount;	 
	 }
	 
	 
	 public String convertTime(String time){
		 SimpleDateFormat formatter2 = new SimpleDateFormat ("yy-MM-dd");
			// Date curDate = new Date();//获取当前时间
	     SimpleDateFormat formatter1 = new SimpleDateFormat ("yy-MM-dd HH:mm");
	     Date date1 = null;
			try {
				
				try {
					date1 = formatter1.parse(time);
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     return formatter2.format(date1);
		}
		
	 public double TimeSpace(String str_time1,String str_time2){
	    	
	    	//String str2 = et15_taskdeadline.getText().toString();  //"yyyyMMdd"格式 如 20131022
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//输入日期的格式 
			Date date1 = null;
			Date date2 = null;
			try {
			date2 = simpleDateFormat.parse(str_time2);
			date1= simpleDateFormat.parse(str_time1);
			} catch (ParseException e) {
			e.printStackTrace();
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			//GregorianCalendar cal1 = new GregorianCalendar();  
			GregorianCalendar cal2 = new GregorianCalendar(); 
			GregorianCalendar cal1 = new GregorianCalendar();
			//cal1.setTime(date1);  
			cal2.setTime(date2);  
			cal1.setTime(date1);
			double dayCount = (cal2.getTimeInMillis()-cal1.getTimeInMillis())/(1000*60*60*24);
						
	    	return dayCount;
	    };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 
		
		 /*  if (TaskData.TdDB==null) {
			   TaskData.TdDB = new ToDoDB(getActivity(), TaskData.db_TdDBname,null, 1);
			 //  TaskData.db_TdDB = TaskData.TdDB.getWritableDatabase();
			   TaskData.TdDB.onCreate(TaskData.db_TdDB);
			 
			    //textView = (TextView) findViewById(R.id.text_view); 
				Log.d("task execute", "task execute");
			Log.d("create TdDB", TaskData.db_TdDB.toString());
		   }else{
			   Log.d("TdDB open", "go");
		   }	*/	 
		}
   
	
}
