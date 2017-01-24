package com.easygoal.achieve;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.litepal.crud.DataSupport;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
  
public class Activity_Team extends AppCompatActivity {  
  
	Cursor c;
	View oldview=null;
	View newview=null;
	Toolbar toolbar;
	final String Tags="Activity_Team";
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_group);  
        
        //ActionBar actionBar = getActionBar();  
        //actionBar.setDisplayHomeAsUpEnabled(true);  
        //actionBar.setHomeAsUpIndicator(R.drawable.back_48px); 
      
        toolbar = (Toolbar) findViewById(R.id.toolbar); 
		//toolbar.setTitle("Title");
	    //toolbar.setSubtitle("SubTitle");
	    //toolbar.setLogo(R.drawable.ic_launcher);
       
		setSupportActionBar(toolbar); 
	    toolbar.setNavigationIcon(R.drawable.back_48px);
		//mDrawerLayout = (CustomDrawerLayout) findViewById(R.id.main_drawer);        
		//Button mLvDrawer = (Button) findViewById(R.id.bt_drawer);  
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss ");
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间
	    String str = formatter.format(curDate);
	    //Log.d("create database", "start"+TaskData.d.toString());
	    final EditText et1=(EditText)findViewById(R.id.record_item2_recordTime_et);
		  final EditText et3=(EditText)findViewById(R.id.record_item4_progress_et);
		  final EditText et4=(EditText)findViewById(R.id.record_item5_comment_et);
		 
		  final ListView lv_taskrecord=(ListView)findViewById(R.id.taskrecord_lv);
		  final ListView lv_taskcomments=(ListView)findViewById(R.id.taskcomments_lv);
		  
		 //Button btn_confirm=(Button)findViewById(R.id.btn_confirm);
		  Button btn_update=(Button)findViewById(R.id.btn_update);
		  Button btn_addrecord = (Button) findViewById(R.id.btn_addrecord);  
		  Button btn_cancel = (Button) findViewById(R.id.btn_cancel);  
		  Button btn_close = (Button) findViewById(R.id.btn_close); 
		  Button btn_taskdelete=(Button)findViewById(R.id.btn_delete);
		  Button btn_export=(Button)findViewById(R.id.export_bt);
		  
		  // final TextView tv1=(TextView)findViewById(R.id.textView2);
		 // final TextView tv2=(TextView)findViewById(R.id.textView3);
		 
		  
		    //final Cursor c = TaskData.db_TdDB.query(TaskData.TdDB.TABLE_NAME_TaskMain, null, null, null, null, null, null);
		    //Log.d("cursor",String.valueOf(c.getCount())+"position"+String.valueOf(c.getPosition()));
		 //  final Cursor c2 = TaskData.db_TdDB.query(TaskData.TdDB.TABLE_NAME_TaskRecord, null, null, null, null, null, null);
		//   Log.d("cursor2",String.valueOf(c2.getCount())+"position"+String.valueOf(c2.getPosition()));
		 //   final ContentValues cv = new ContentValues();
		/*  final int click_position;
		    String[] itemlist_taskrecord={
		            TaskData.TdDB.TASK_NAME, 
		            TaskData.TdDB.TASK_DESCRIPTION,
		            TaskData.TdDB.TASK_IMPORTANCE,
		            TaskData.TdDB.TASK_URGENCY,
		            TaskData.TdDB.TASK_DEADLINE,
		            TaskData.taskRecord.RECORD_TIME,
		            TaskData.taskRecord.RECORD_PROGRESS,		            
		              		
};
	int[] controllist_taskrecord={
			R.id.task_item1_name_tv,
			R.id.task_item11_achieved_tv,
			R.id.task_item12_enjoyment_tv,
			R.id.task_item13_experience_tv,
			R.id.task_item16_progress_tv,
			R.id.					
	};*/
		// lvBaseAdapter_taskcomments lvBaseAdapter_taskcomments = new lvBaseAdapter_taskcomments(getActivity()); 
		// mCursorAdapter_taskrecord adapter_record=new mCursorAdapter_taskrecord(getActivity(), c);  
		 //SimpleCursorAdapter adapter_record = new SimpleCursorAdapter(getActivity(), R.layout.lv_taskscore, c,itemlist_taskscore,controllist_taskscore, 0);
		// Log.d("adapter_record",adapter_record.toString() );
	//	lv_taskrecord.setAdapter(adapter_record); 
	   //  Log.d("show data","ok" +lv_taskrecord.toString());
	 //  mCursorAdapter_taskcomment mcAdapter_taskcomment = new mCursorAdapter_taskcomment(getActivity(),c2);
		// lv_taskcomments.setAdapter(lvBaseAdapter_taskcomments);
	// Log.d("adapter_record",mcAdapter_taskcomment.toString());
		/*
	        btn_cancel.setOnClickListener(new OnClickListener() {  
		      	  
	            @Override  
	            public void onClick(View arg0) {  
	                // 关闭对话框  
	                finish();  
	            }  
	        });   
		 */
		  
		lv_taskrecord.setAdapter(query());
		   
		//lv_taskrecord.setClickable(true);
		
		  
	    toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){

			@Override
			public boolean onMenuItemClick(MenuItem menuItem) {
				// TODO Auto-generated method stub
				String msg = "";
			    String curTime;
				switch (menuItem.getItemId()) {
			      case R.id.action_settings1:
			 		 DialogFragment_Task dialogfrag_task=new DialogFragment_Task();
			 		  TaskTool.showDialog(dialogfrag_task);
			 		  msg="add goal";
			        break;
			      case R.id.action_settings4:  File sdFileDir=Environment.getExternalStorageDirectory();
			         SimpleDateFormat formatter = new SimpleDateFormat ("yyMMddHHmm");
					 Date curDate = new Date(System.currentTimeMillis());//获取当前时间
					 curTime = formatter.format(curDate);
				     File file1=new File(sdFileDir.getAbsoluteFile()+"/DCIM/"+curTime+".png");
					   System.out.println(file1.getPath());
					   System.out.println(file1.getAbsolutePath());
					   System.out.println(file1.toString());
						try {
							file1.createNewFile();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
				
			            ScreenShot screenshot = new ScreenShot();
			            // BitmapDrawable bd= new BitmapDrawable(screenshot.takeScreenShot(MainActivity_1.this));  
			            screenshot.shoot(Activity_Team.this,file1);			      
			            Intent intent = new Intent(Intent.ACTION_SEND);
			            intent.setType("image/*"); 
			            Uri u;
			            if (file1 != null && file1.exists()) {  
			    	     
				         u = Uri.fromFile(file1);  
				         Log.d(Tags, u.toString());
				         intent.putExtra(Intent.EXTRA_STREAM, u);  
				        } else{
				    	  Log.d(Tags,"file1 not exist");
				        }
			       //intent.putExtra("BITMAP", screenshot.takeScreenShot(MainActivity_1.this));
			            intent.putExtra(Intent.EXTRA_SUBJECT, "fddff");  
				        intent.putExtra(Intent.EXTRA_TEXT, "ddd");  
				        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				        startActivity(Intent.createChooser(intent, "love you")); 
				        break;
			      case R.id.action_settings5: 
			    	      Intent intent1 = new Intent();  
	                      intent1.setClass(Activity_Team.this, SearchActivity.class);  
	                      startActivity(intent1); 
			    	  
			    	     //DialogFragment_Search dg_search=new DialogFragment_Search();
					     //showDialog(dg_search);    
			      /* 
			      case R.id.action_settings5: 
			    	     DialogFragment_UserSetting dg_usersetting=new DialogFragment_UserSetting(getApplication());
					     showDialog(dg_usersetting);
					          
			      case R.id.action_settings6: 
			    	     DialogFragment_Preferences dg_preferences=new DialogFragment_Preferences();
	                     showDialog(dg_preferences);
	                     break; 
			      case R.id.action_settings7: 
			    	    
						 Editor alarmEditor=alarmSettingSP.edit();
						 alarmEditor.putBoolean("alarmclock", true);
						 alarmEditor.putBoolean("vibration", false);
						 alarmEditor.putBoolean("alarmring", false);
						 alarmEditor.putBoolean("notification", false);
					      DialogFragment_AlarmSetting dg_alarmsetting=new DialogFragment_AlarmSetting();
					     showDialog(dg_alarmsetting);
				      break;  
				  */    
			            default:break;
			          }
			 
			        if(!msg.equals("")) {
			      //Toast.makeText(MainActivity_1.this, msg, Toast.LENGTH_SHORT).show();
			          }
				     return false;
		         	}
			  
		  });
		
		
		
		 lv_taskrecord.setOnItemClickListener(new OnItemClickListener(){
           int newPos=0;
           int oldPos=0;
            
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
               TaskData.selTaskID=String.valueOf(TaskData.cursor_group.getString(TaskData.cursor_group.getColumnIndex(TaskData.TdDB.TASK_ID)));
               TaskData.selTaskSN=String.valueOf(TaskData.cursor_group.getString(TaskData.cursor_group.getColumnIndex(TaskData.TdDB.TASK_SN)));
               if (view!=newview){
					oldview=newview; 
					newview=view;
					newview.setBackgroundResource(R.color.gray);
				
					if (oldview!=null){
					oldview.setBackgroundResource(R.color.mTextColor2);
				
					} 
				}
                 
		  }
		 });
			
		
		btn_addrecord.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				 //Log.d("from_fg", from_fg.toString()); 
				DialogFragment_GroupRecord dialogfrag_grouprecord=new DialogFragment_GroupRecord();
					TaskTool.showDialog(dialogfrag_grouprecord);
					
				
				
				//TaskData.from_fg=showFrag(TaskData.from_fg,R.id.sublayout_task,subfrag_task,3);
			//	Log.d("task tab", "choice3");
			}
		  });	
		
		 btn_update.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					TaskData.adapterUpdate();
					 
				} 
			  });
		 
		 final String[] taskitemlist={
				  TaskData.TdDB.TASK_NAME,
				  TaskData.TdDB.TASK_ASSESSMENT,
				  TaskData.TdDB.TASK_SEQUENCENO,
				  TaskData.TdDB.TASK_DEADLINE,
				  TaskData.TdDB.TASK_PROGRESS,
				  //TaskData.TdDB.TASK_STATUS,
				  //TaskData.TdDB.TASK_FINISHED,
				  //TaskData.TdDB.TASK_DELAYED    		
		  };	  
		  final String[] titlenamelist={
			"任务","重要紧急","智能排序","期限","完成%"	  
		  };
		  
		 
		  btn_export.setOnClickListener(new OnClickListener(){

				
				@Override
				public void onClick(View v) {
			    	Cursor c=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_STATUS+"=?"+" order by "+TaskData.TdDB.TASK_SEQUENCENO+" asc", new String[]{"open"});
					// TODO Auto-generated method stub
			       
			  	  // Date curDate = new Date();//获取当前时间
			  	   //String curTime = formatter.format(curDate);	
			  	   /*
			  	   final Calendar cal=Calendar.getInstance();
			  	   int year = cal.get(Calendar.YEAR);
			         int month = cal.get(Calendar.MONTH);
			         int day=cal.get(Calendar.DAY_OF_MONTH);
			        */ 
			       SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd mm:ss"); 
			  	   String reportname  ="teamtaskslist"+formatter.format(new Date()); 
			    	
			    if (c.getCount()>0){
			       
					 File outfile=new ExportListToExcel(getApplication(),TaskData.TdDB.TABLE_NAME_TaskMain,
							c,
							titlenamelist,
							taskitemlist)
					        .writeExcel(reportname);
					 Intent intent2 = new Intent(Intent.ACTION_SEND);
				       intent2.setType("text/*"); 
				      
				       if (outfile != null && outfile.exists()) {  
				    	   
					        
					        Uri u = Uri.fromFile(outfile);  
					     
					       intent2.putExtra(Intent.EXTRA_STREAM, u);  
					      } else{
					    	
					      }
				       //intent.putExtra("BITMAP", screenshot.takeScreenShot(MainActivity_1.this));
				      // intent2.putExtra(Intent.EXTRA_SUBJECT, "opentasks");  
					   //  intent2.putExtra(Intent.EXTRA_TEXT, "opentasks");  
					    // intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				          
					     startActivity(Intent.createChooser(intent2, "opentasks list")); 
					    
			       
				}else{   
					  Toast.makeText(getApplication(), "记录为空", Toast.LENGTH_SHORT).show();	 
				  }
				}	
		 });
		  
		  btn_taskdelete.setOnClickListener(new OnClickListener(){

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				if (TaskData.selTaskID!=null){
				
						/*TaskData.cursor_opentasks.moveToPosition(TaskData.cursor_opentasks.getPosition());
						int p=TaskData.cursor_opentasks.getInt(0);
						Log.d("delete row", String.valueOf(p));
						String where=TaskData.TdDB.TASK_ID+"=?";
						String[] whereArgs={Integer.toString(p)};		
						TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain,where, whereArgs);
						Log.d("delete row", "delete OK");
						 Log.d("cursor",String.valueOf(TaskData.cursor_opentasks.getCount())+"position"+String.valueOf(TaskData.cursor_opentasks.getPosition()));
						 */
						 
						 new ConnMySQL(getApplication()).DelByGsonArrayRequestPost("TaskmainServlet",TaskData.TdDB.TABLE_NAME_TaskMain,TaskData.selTaskSN);
						 Log.d(Tags,"del by Mysql done");
						 TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
					             TaskData.TdDB.TASK_SN+"="+"'"+TaskData.selTaskSN+"'"+" and "+
					             TaskData.TdDB.TASK_STATUS+"="+"'open'"+" and "+
							     TaskData.TdDB.TASK_USER+"="+"'"+TaskData.user+"';");					 
						 Cursor cr=DataSupport.findBySQL("select id as _id from reminder where sourceId="+"'"+"T"+TaskData.selTaskID+"';");
						
						 if (cr.getCount()>0){
							   cr.moveToFirst();
							   do{
							   int reminderno = cr.getInt(0);
							   DataSupport.delete(Reminder.class, reminderno);
							   Log.d(Tags,"delete reminder"+reminderno);   
							   
							   }while(cr.moveToNext());
						   } 
						 
						 //Toast.makeText(getActivity(), "已删除TaskID"+TaskData.selTaskID,Toast.LENGTH_SHORT).show(); 
					    TaskData.adapterUpdate();
						TaskData.selTaskID=null;
						TaskData.selTaskSN=null;
						
						
				
				    }else{
						Toast.makeText(getApplication(), "请先选定任务", Toast.LENGTH_SHORT).show();	 
					  }
					
				
			
			}
	  
		  }); 
		 
    }
    
   
    public mcAdapter_group query(){
		 //Log.d("start join query","OK");
	     
		   String a = TaskData.TdDB.TABLE_NAME_TaskMain;
	       String b = TaskData.TdDB.TABLE_NAME_TaskRecord;
	      // Log.d("start join query","a"+a+"b"+b);
	       //Log.d("start join query",TaskData.TdDB.getDatabaseName());
	    //  Cursor cursor = db.rawQuery("SELECT a.*,b*b FROM a,b INNER JOIN a.TASK_ID=b.TASK_NO;",new String[]{});
	       //String TASK_NAME = "task_name";
	     
		//Cursor c1=TaskData.db_TdDB.rawQuery("select * from "+a+","+b+ " where "+a+"."+TASK_NAME+" like ?", new String[]{"name"});
	    TaskData.cursor_group=TaskData.db_TdDB.rawQuery("select * from "+a, null);
	    //Log.d("rawquery","row"+c1.getCount()+"column"+c1.getColumnCount()+"position"+c1.getPosition());
		//c1.moveToFirst();
		TaskData.mcAdapter_group = new mcAdapter_group(getApplication(),TaskData.cursor_group);
		
		return TaskData.mcAdapter_group;
		// lv_taskrecord.setAdapter(adapter_record); 
	 
	 }
    
    @Override  
    public boolean onCreateOptionsMenu(Menu menu) {  
        // Inflate the menu; this adds items to the action bar if it is present.  
        getMenuInflater().inflate(R.menu.main_menugroup, menu);  
        return true;  
    };  
  
    	
}
