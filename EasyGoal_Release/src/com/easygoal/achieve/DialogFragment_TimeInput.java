package com.easygoal.achieve;

import java.util.Calendar;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TimePicker;

public class DialogFragment_TimeInput extends DialogFragment {

	 String date1;
	 String date2;
	 int hour;
	 int min;
	 int year;
	 int month;
	 int day;
	 int dayofweek;
	 String weekday;
	 String time1;
	 String deadline;
	 private TextView tv;
	 int p;
	public DialogFragment_TimeInput(TextView tv,int pos) {
		// TODO Auto-generated constructor stub
	   this.tv=tv;
	   this.p=pos;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
 	   View view = View.inflate(getActivity(), R.layout.date_time_picker, null);  
 	   final TabHost tabhost=(TabHost)view.findViewById(android.R.id.tabhost);
 	   tabhost.setup();
 	   tabhost.addTab(tabhost.newTabSpec("1").setIndicator("����").setContent(R.id.tab1));
 	   tabhost.addTab(tabhost.newTabSpec("2").setIndicator("ʱ��").setContent(R.id.tab2));
 	   tabhost.setCurrentTab(p);
 	   
 	  TabWidget tabWidget = (TabWidget) view.findViewById(android.R.id.tabs);  
 	  final TextView[] tabtv = new TextView[2];
      for (int i=0; i<tabhost.getTabWidget().getChildCount(); i++){                         //ѭ��ÿ��tabView
          View tabview = tabWidget.getChildAt(i);                                 //��ȡtabView��           
          //view.setContentDescription(Integer.toString(i+1));
          //view.getLayoutParams().height = (int) (view.getLayoutParams().height / 1.2);
       
        
          tabtv[i] = (TextView) tabhost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);  
          //tv.setTextColor(R.drawable.selector_bottomtab1_textcolor);
          //tv.setText("XXXX");  
          //iv.setImageDrawable(getResources().getDrawable(R.drawable.icon));  
           Log.d("tv"+i,"tv"+ tv.toString());
           tabtv[i].setTextSize(20);
           tabtv[i].setTextColor(R.drawable.selector_bottomtab1_textcolor);
      }
 	   
 	   
 	   CalendarView calendarView = (CalendarView)view.findViewById(R.id.calendarView1);
 	   //final DatePicker datePicker = (DatePicker)view.findViewById(R.id.new_act_date_picker);  
        final TimePicker timePicker = (TimePicker)view.findViewById(R.id.new_act_time_picker);  
   	    final Calendar cal=Calendar.getInstance();
   	   final TextView tv_date = (TextView)view.findViewById(R.id.tv_date);
   	   final TextView tv_day = (TextView)view.findViewById(R.id.tv_day);
   	   final TextView tv_time = (TextView)view.findViewById(R.id.tv_time);
   	  final TextView tv_time2 = (TextView)view.findViewById(R.id.tv_time2);
   	   year=cal.get(Calendar.YEAR);
          month=cal.get(Calendar.MONTH);
          day=cal.get(Calendar.DAY_OF_MONTH);
          dayofweek=cal.get(Calendar.DAY_OF_WEEK);
       weekday= null;  
       if (dayofweek == 2) {  
           weekday = "����һ";  
       } else if (dayofweek == 3) {  
           weekday = "���ڶ�";  
       } else if (dayofweek == 4) {  
       	weekday = "������";  
       } else if (dayofweek == 5) {  
       	weekday = "������";  
       } else if (dayofweek == 6) {  
       	weekday = "������";  
       } else if (dayofweek == 7) {  
       	weekday = "������";  
       } else if (dayofweek == 1) {  
       	weekday = "������";  
       }  
      
       timePicker.setIs24HourView(true);
      
 	hour = cal.get(Calendar.HOUR_OF_DAY); 
    min = cal.get(Calendar.MINUTE);
    date1 = year + "-" + (month+1) + "-" + day;
    time1=" "+hour+":"+min;
        tv_date.setText(String.valueOf(date1));        
        tv_day.setText(String.valueOf(weekday)); 
        tv_time.setText(String.valueOf(time1)); 
       
          
    //Toast.makeText(getActivity(), date1+hour+min, Toast.LENGTH_LONG).show();
     //hour = new Date(System.currentTimeMillis()).getHours();  
    //min = new Date(System.currentTimeMillis()).getMinutes();  
     /*int year=cal.get(Calendar.YEAR);
     int month=cal.get(Calendar.MONTH)+1;
     int day=cal.get(Calendar.DAY_OF_MONTH);
     String date = year + "-" + month + "-" + day;
    */
 
             calendarView.setOnDateChangeListener(new OnDateChangeListener() {
                 
                 public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                     date1 = year + "-" + (month+1) + "-" + dayOfMonth;
                     //date2 = year  +""+(month+1)+""+ dayOfMonth;
                     //Toast.makeText(getActivity(), date1, Toast.LENGTH_LONG).show();
                     tv_date.setText(String.valueOf(date1));        
                     tv_day.setText(String.valueOf(weekday)); 
                    
                 }
             });
            
             timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener(){

					@Override
					public void onTimeChanged(TimePicker view, int hr, int m) {
						// TODO Auto-generated method stub
						hour =timePicker.getCurrentHour();  
                     min=timePicker.getCurrentMinute(); 
                     time1=""+hour+":"+min;
                    
                     //Log.d("1",String.valueOf("s1"+time1));
                    
                     //Toast.makeText(getActivity(),"h"+time1, Toast.LENGTH_LONG).show();
					}		        		                	
             });
         
           tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener()
             {
					@Override
					public void onTabChanged(String arg0) {
						// TODO Auto-generated method stub
					switch (tabhost.getCurrentTab()){
					case 0:	
				  		   tv_date.setText(String.valueOf(date1));        
                        tv_day.setText(String.valueOf(weekday)); 
                        tv_time.setText(""+timePicker.getCurrentHour()+":"+timePicker.getCurrentMinute()); 
                        break;
					case 1:	
						   break;
					default:break;	   
					}
				 }	
             });
             
             Button btn_confirm=(Button)view.findViewById(R.id.confirm_bt);
             Button btn_cancel=(Button)view.findViewById(R.id.cancel_bt);
           
             btn_confirm.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					hour =timePicker.getCurrentHour();  
                    min=timePicker.getCurrentMinute(); 
                    time1=" "+hour+":"+min;
                	String deadline =date1+time1;
                	tv.setText(TimeData.convertTime_YYYYTIMEtoYYTIME(deadline));
                	//Toast.makeText(getActivity(),String.valueOf(deadline), Toast.LENGTH_SHORT).show();               	                       
				    dismiss();
				}
            	 
             });
             
             btn_cancel.setOnClickListener(new OnClickListener(){

 				@Override
 				public void onClick(View arg0) {
 					// TODO Auto-generated method stub
 					dismiss();
 				}
             	 
              });
			return view;	
      } 
	  
	public String GetDeadline(){
		return deadline;
	}	
	public String GetDate(){
			return date1;			
	}
	public String GetTime(){
		return time1;			
    }
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
		getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height );
	}  
}
