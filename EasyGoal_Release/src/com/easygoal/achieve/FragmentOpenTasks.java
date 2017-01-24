package com.easygoal.achieve;


import java.util.HashMap;
import java.util.Map;

import org.litepal.crud.DataSupport;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentOpenTasks extends Fragment {
	
	
	
	public SimpleCursorAdapter adapter;
	
	int selectedID=0;
	View oldview=null;
    View newview = null;
    ListView lv_opentasks;
    String Tags="FragmentOpenTasks";
    
	 //final ToDoDB TdDB = new ToDoDB(getActivity(), db_name,null, 1);
	 
	 @Override  
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {  
		View v=inflater.inflate(R.layout.task_subfg_toptab1_opentasks, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 
		
		 
		  TaskData.tv_opentaskscount=(TextView)v.findViewById(R.id.tv_opentaskscount);
		  final TextView tv1=(TextView)v.findViewById(R.id.task_item1_name_tv);
		  final TextView tv4=(TextView)v.findViewById(R.id.task_item4_importance_tv);
		  final TextView tv5=(TextView)v.findViewById(R.id.task_item5_urgency_tv);
		  final TextView tv6=(TextView)v.findViewById(R.id.task_item6_assess_tv);
		  final TextView tv15=(TextView)v.findViewById(R.id.task_item15_deadline_tv);
		  final TextView tv16=(TextView)v.findViewById(R.id.task_item16_progress_tv);
		  final TextView tv18=(TextView)v.findViewById(R.id.task_item18_status_tv);
		  final TextView tv19=(TextView)v.findViewById(R.id.task_item19_finished_tv);
		  final TextView tv20=(TextView)v.findViewById(R.id.task_item20_delayed_tv);
		  lv_opentasks=(ListView)v.findViewById(R.id.opentasks_lv);
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
		
		 
	      TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+"=?"+" and "+TaskData.TdDB.TASK_STATUS+"=?"+" order by "+TaskData.TdDB.TASK_SEQUENCENO+" asc", new String[]{TaskData.user,"open"});
		  TaskData.adapter_opentasks = new mcAdapter_opentasks(getActivity(),R.layout.lv_opentasks,TaskData.cursor_opentasks);
		 
		  if (lv_opentasks!=null){
			  lv_opentasks.setAdapter(TaskData.adapter_opentasks); 
		      Log.d(Tags,"lv opentasks show data ok ");
	      };
	      
	      TaskData.update_total(TaskData.tv_opentaskscount,TaskData.cursor_opentasks);
		  lv_opentasks.setFocusable(true);
		  lv_opentasks.setItemsCanFocus(true);
		  lv_opentasks.setSelected(true);
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
		
		  
		  lv_opentasks.setOnItemClickListener(new OnItemClickListener(){
             int newPos=0;
             int oldPos=0;
              
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
				try{ 
					TaskData.selTaskID=String.valueOf(TaskData.cursor_opentasks.getString(TaskData.cursor_opentasks.getColumnIndex(TaskData.TdDB.TASK_ID)));
	                TaskData.selTaskSN=String.valueOf(TaskData.cursor_opentasks.getString(TaskData.cursor_opentasks.getColumnIndex(TaskData.TdDB.TASK_SN)));	
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
                 
                 
                 
                 /*
                 for(int i=0;i<parent.getCount();i++){
                     View v=parent.getChildAt(i);
                     if (position == i) {
                    	 int horizontal = 1;
						//v.requestFocus();
                         v.setBackgroundColor( getResources().getColor(R.color.gray));
                     } else {
                    	 v.setBackgroundColor(getResources().getColor(R.color.white));
                     }
                
                     
                 }*/    
		  }
		 });
		  	 	  
		  lv_opentasks.setOnItemLongClickListener(new OnItemLongClickListener(){
	            int newPos=0;
	            int oldPos=0;
	            
				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long id) {
					// TODO Auto-generated method stub
					
					try{ 
						TaskData.selTaskID=String.valueOf(TaskData.cursor_opentasks.getString(TaskData.cursor_opentasks.getColumnIndex(TaskData.TdDB.TASK_ID)));
		                TaskData.selTaskSN=String.valueOf(TaskData.cursor_opentasks.getString(TaskData.cursor_opentasks.getColumnIndex(TaskData.TdDB.TASK_SN)));	
	            	 }catch(Exception e){
	            		 Log.d(Tags, e.toString());
	            		 e.printStackTrace();
	            	 }
	                
				
					DialogFragment_TaskDetail dg_taskdetail=new DialogFragment_TaskDetail();
	    	    	TaskTool.showDialog(dg_taskdetail);
		
					return false;
				}
	            
			 });
		  
		  Button btn_export=(Button)v.findViewById(R.id.export_bt);
		  btn_export.setOnClickListener(new OnClickListener(){

				
				@Override
				public void onClick(View v) {
			    	Cursor c=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+"=?"+" and "+TaskData.TdDB.TASK_STATUS+"=?"+" order by "+TaskData.TdDB.TASK_SEQUENCENO+" asc", new String[]{TaskData.user,"open"});
					// TODO Auto-generated method stub
			    	
				  if (c.getCount()>0){
			       
					  ExportUtils.exportListToFile(getActivity(),c,"opentaskslist");
			       
				}else{   
					  Toast.makeText(getActivity(), "��¼Ϊ��", Toast.LENGTH_SHORT).show();	 
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
						 
					  Builder builder=new AlertDialog.Builder(getActivity());
				      builder.setTitle("ȷ��");
					  builder.setMessage("��Ҫɾ������T"+TaskData.selTaskID+"��");
					  builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							//TextView show=(TextView)findViewById(R.layout.tv_alertdialog_pos);
						    //show.setText("");
							if (TaskData.privilege>TaskData.entryright){
							  new ConnMySQL(getActivity()).DelByGsonArrayRequestPost("TaskmainServlet",TaskData.TdDB.TABLE_NAME_TaskMain,TaskData.selTaskSN);
							  Log.d(Tags,"del by Mysql done");
							} 
							 TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
						             TaskData.TdDB.TASK_SN+"="+"'"+TaskData.selTaskSN+"'"+" and "+
						             TaskData.TdDB.TASK_STATUS+"="+"'open'"+" and "+
								     TaskData.TdDB.TASK_USER+"="+"'"+TaskData.user+"';");					 
							 
							 Cursor cr=DataSupport.findBySQL("select id as _id,sourceId from reminder where sourceId="+"'"+"T"+TaskData.selTaskID+"';");
							
							 if (cr.getCount()>0){
								   cr.moveToFirst();  
								   String remindersn = cr.getString(1);
								   Alarm.alarmCancel(getActivity(), remindersn);	 
								   int reminderno = cr.getInt(0);
								   DataSupport.delete(Reminder.class, reminderno);
								   Log.d(Tags,"delete reminder "+reminderno);
							   } 
							 TaskData.adapterUpdate();
							 TaskData.selTaskID=null;
						     TaskData.selTaskSN=null;
						} 
					  } );
					  builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}	  
					  });
					  builder.create().show();	    
				  } else{
						Toast.makeText(getActivity(), "����ѡ������", Toast.LENGTH_SHORT).show();	 
				  };
			 }	  
				
		  });			
				
		  btn_taskclear.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					 //new ConnMySQL(getActivity()).ClearByGsonArrayRequestPost("TaskmainServlet",TaskData.TdDB.TABLE_NAME_TaskMain);
					 //Toast.makeText(getActivity(), "��ȫ�����",Toast.LENGTH_SHORT).show();
					 //Log.d("clear by Mysql","done");
					
					//for (int i=0;i<TaskData.cursor_opentasks.getCount();i++){
				    //TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain, null, null);	
					//};
					/*
					if (TaskData.cursor_opentasks!=null&&TaskData.cursor_opentasks.getCount()>0){
					TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain, null, null);	
				    }
					*/
					 Builder builder=new AlertDialog.Builder(getActivity());
					      builder.setTitle("ȷ��");
						  builder.setMessage("��Ҫ�����");
						  builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								//TextView show=(TextView)findViewById(R.layout.tv_alertdialog_pos);
							    //show.setText("");
								TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+"  where "+TaskData.TdDB.TASK_USER+"="+"'"+TaskData.user+"' and "+TaskData.TdDB.TASK_STATUS+"="+"'"+"open"+"';");	
								//TaskData.adapter_opentasks.notifyDataSetChanged();
								
								TaskTool.clearlinkedReminder(getActivity(),"T");
								if (TaskData.privilege>TaskData.entryright){
								 
									Map params = new HashMap();
									params.put("username", TaskData.user);
									params.put(TaskData.TdDB.TASK_STATUS, "open");
						    	 
									new ConnMySQL(getActivity()).ClearByGsonArrayRequestPost("TaskmainServlet", params); 
								}
								/*
								 Cursor cr=DataSupport.findBySQL("select id as _id from reminder where sourceId like"+"'"+"T%"+"';");
									
								 if (cr.getCount()>0){
									   cr.moveToFirst();
									   do{
									   int reminderno = cr.getInt(0);
									   DataSupport.delete(Reminder.class, reminderno);
									   Log.d("clear reminder", "clear "+reminderno);   
									   String remindersn = cr.getString(cr.getColumnIndex("sourceId"));
									   Alarm.alarmCancel(getActivity(), remindersn);	
									   }while(cr.moveToNext());
								   } 
								*/
								
								TaskData.adapterUpdate();
								
								
								 //Log.d(Tags,"TdDB adapter update all done");  
								 Log.d(Tags,"cleared and new count is: "+String.valueOf(TaskData.cursor_opentasks.getCount())+"position"+String.valueOf(TaskData.cursor_opentasks.getPosition()));
							    TaskData.selTaskID=null;
							} 
						  } );
						  builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
							}	  
						  });
						  builder.create().show();	    
					  } ;
					
			  });
		  
		  btn_taskupdate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TaskData.adapterUpdate();
				
				 Log.d(Tags,"TdDB adapter update all done");
				 Log.d(Tags,"update cursor "+String.valueOf(TaskData.cursor_opentasks.getCount())+"position"+String.valueOf(TaskData.cursor_opentasks.getPosition()));
				
				 
			
				 
			} 
		  });
		 
		  btn_addrecord.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					
					// TODO Auto-generated method stub
					 //Log.d("from_fg", from_fg.toString()); 
					  //Toast.makeText(getActivity(), "TaskID"+TaskData.selTaskID,Toast.LENGTH_SHORT).show();
					if (TaskData.selTaskID!=null){
					DialogFragment_Comment dialogfrag_comment=new DialogFragment_Comment();
						showDialog(dialogfrag_comment);
						Log.d(Tags,"show addcomment dialogfrag"+dialogfrag_comment.toString());
					  }else{
						Toast.makeText(getActivity(), "����ѡ������", Toast.LENGTH_LONG).show();	 
					  }
					
					//TaskData.from_fg=showFrag(TaskData.from_fg,R.id.sublayout_task,subfrag_task,3);
				//	Log.d("task tab", "choice3");
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
	    	 //  dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);  
	           dialogFragment.setStyle(R.style.Dialog_Fullscreen, 0);   
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
		};
    
			
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	
		  
	}	  
}
