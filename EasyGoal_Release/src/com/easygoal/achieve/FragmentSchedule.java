package com.easygoal.achieve;


import java.util.Calendar;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.ListView;

public class FragmentSchedule extends Fragment {
	
	 String date1;
	 String date2;
	 String taskdeadlineDate;
	 int year;
	 int month;
	 int day;
	 CalendarView calendarView;
	 Cursor cursor_calendartasks;
	 mcAdapter_taskscalendar adapter_calendartasks;
	 String Tags="FragmentShedule";
	 
	 //final ToDoDB TdDB = new ToDoDB(getActivity(), db_name,null, 1);
	 
	 @Override  
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {  
		View v=inflater.inflate(R.layout.subfg_home3_calendar, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 
	
		 calendarView = (CalendarView)v.findViewById(R.id.calendarView1);
		 final ListView opentasks_lv=(ListView)v.findViewById(R.id.opentasks_lv); 
	     
		 final Calendar cal=Calendar.getInstance();
    	   year=cal.get(Calendar.YEAR);
           month=cal.get(Calendar.MONTH);
           day=cal.get(Calendar.DAY_OF_MONTH);
         
           date1 = year + "-" + (month+1) + "-" + (day);
		 
           taskdeadlineDate = TimeData.convertDate_YYYYMtoYYMM(date1);
  		   String a = TaskData.TdDB.TABLE_NAME_TaskMain;
  	       String b = TaskData.TdDB.TABLE_NAME_TaskRecord;
  	     TaskData.cursor_calendartasks = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_STATUS+"=? and "+
                 TaskData.TdDB.TASK_DEADLINEDATE+"=?  and "+
                 TaskData.TdDB.TASK_USER+"=?  "+
                 " order by "+TaskData.TdDB.TASK_DEADLINETIMEDATA+" asc",
                 new String[]{"open",taskdeadlineDate,TaskData.user});
  	      
  	     
  	     TaskData.adapter_calendartasks = new mcAdapter_taskscalendar(getActivity(),R.layout.lvitem_calendartasks,TaskData.cursor_calendartasks);
  		  opentasks_lv.setAdapter(TaskData.adapter_calendartasks); 
           
  		  
		 calendarView.setOnDateChangeListener(new OnDateChangeListener() {
			 
             public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                 date1 = year + "-" + (month+1) + "-" + dayOfMonth;
                
              
        		 taskdeadlineDate = TimeData.convertDate_YYYYMtoYYMM(date1);
        		
        		 String a = TaskData.TdDB.TABLE_NAME_TaskMain;
        	       String b = TaskData.TdDB.TABLE_NAME_TaskRecord;
        	      TaskData.cursor_calendartasks =  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_STATUS+"=? and "+
        	                 TaskData.TdDB.TASK_DEADLINEDATE+"=?  and "+
        	                 TaskData.TdDB.TASK_USER+"=?  "+
        	                 " order by "+TaskData.TdDB.TASK_DEADLINETIMEDATA+" asc",
        	                 new String[]{"open",taskdeadlineDate,TaskData.user});
        	       
        	           
        	       TaskData.adapter_calendartasks = new mcAdapter_taskscalendar(getActivity(),R.layout.lvitem_calendartasks,TaskData.cursor_calendartasks);
        	  	   opentasks_lv.setAdapter(TaskData.adapter_calendartasks);
       
        		
             }
         });
		 
		 opentasks_lv.setOnItemClickListener(new OnItemClickListener(){
	             int newPos=0;
	             int oldPos=0;
	             View oldview;
	             View newview;
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					//Log.d("click position ", "item "+String.valueOf(id));
	                 //Log.d("click and move to", String.valueOf(TaskData.cursor_calendartasks.getPosition()));
	                 //view.setBackgroundColor(Color.RED);
					 try{ 
	            		 TaskData.selTaskID=String.valueOf(TaskData.cursor_calendartasks.getString(TaskData.cursor_calendartasks.getColumnIndex(TaskData.TdDB.TASK_ID)));
	            		 TaskData.selTaskSN=String.valueOf(TaskData.cursor_calendartasks.getString(TaskData.cursor_calendartasks.getColumnIndex(TaskData.TdDB.TASK_SN)));
	            	 }catch(Exception e){
	            		 Log.d(Tags, e.toString());
	            		 e.printStackTrace();
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
		 
		 opentasks_lv.setOnItemLongClickListener(new OnItemLongClickListener(){
	            int newPos=0;
	            int oldPos=0;
	            
				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long id) {
					// TODO Auto-generated method stub
					
					 try{ 
						    TaskData.selTaskID=String.valueOf(TaskData.cursor_calendartasks.getString(TaskData.cursor_calendartasks.getColumnIndex(TaskData.TdDB.TASK_ID)));
			                TaskData.selTaskSN=String.valueOf(TaskData.cursor_calendartasks.getString(TaskData.cursor_calendartasks.getColumnIndex(TaskData.TdDB.TASK_SN)));	
	            	 }catch(Exception e){
	            		 	Log.d(Tags, e.toString());
	            		 	e.printStackTrace();
	            	 }
					
					DialogFragment_TaskDetail dg_taskdetail=new DialogFragment_TaskDetail();
	    	    	TaskTool.showDialog(dg_taskdetail);
		
					return false;
				}
	            
			 });
		return v;
	 
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
	          //  dialogFragment = new Fragment_Search();  
	    	   dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);  
	           dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0); 
	         dialogFragment.show(getFragmentManager(), "dialog");  
	    }  
	 
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
	}

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

			
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	
		  
	}	  
}
