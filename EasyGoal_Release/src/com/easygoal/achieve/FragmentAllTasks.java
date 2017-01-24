package com.easygoal.achieve;


import java.text.SimpleDateFormat;
import java.util.Date;
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
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import net.sf.json.JSONArray;

public class FragmentAllTasks extends Fragment {
	
	
	
	public SimpleCursorAdapter adapter;
	int selectedID=0;
    int pageNum=1;
    int pageCount=10;
    ListView lv_alltasks=null;
    JSONArray jolist;
    View oldview=null;
    View newview = null;
    String Tags="FragmentAllTasks";
	 //final ToDoDB TdDB = new ToDoDB(getActivity(), db_name,null, 1);
	 
	 @Override  
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {  
		View v=inflater.inflate(R.layout.task_subfg_toptab2_alltasks, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 
		
		
		  TaskData.tv_alltaskscount=(TextView)v.findViewById(R.id.tv_alltaskscount);
		  final TextView tv1=(TextView)v.findViewById(R.id.task_item1_name_tv);
		  final TextView tv4=(TextView)v.findViewById(R.id.task_item4_importance_tv);
		  final TextView tv5=(TextView)v.findViewById(R.id.task_item5_urgency_tv);
		  final TextView tv6=(TextView)v.findViewById(R.id.task_item6_assess_tv);
		  final TextView tv15=(TextView)v.findViewById(R.id.task_item15_deadline_tv);
		  final TextView tv16=(TextView)v.findViewById(R.id.task_item16_progress_tv);
		  final TextView tv18=(TextView)v.findViewById(R.id.task_item18_status_tv);
		  final TextView tv19=(TextView)v.findViewById(R.id.task_item19_finished_tv);
		  final TextView tv20=(TextView)v.findViewById(R.id.task_item20_delayed_tv);
		  lv_alltasks=(ListView)v.findViewById(R.id.alltasks_lv);
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
		
	      TaskData.cursor_alltasks=  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain +" where "+TaskData.TdDB.TASK_USER+"=?", new String[]{TaskData.user});
	      TaskData.tv_alltaskscount.setText(String.valueOf(TaskData.cursor_alltasks.getCount()));
	      TaskData.adapter_alltasks_pagedivided = new mcAdapter_tasks_pagedivided(getActivity(),R.layout.lv_alltasks,TaskData.cursor_alltasks,pageNum,pageCount);
		 // Log.d("adapter_alltasks",TaskData.adapter_alltasks.toString() );
		  lv_alltasks.setAdapter(TaskData.adapter_alltasks_pagedivided); 
		  //Toast.makeText(getActivity(), "第"+pageNum+"页",Toast.LENGTH_SHORT).show();
		  // Log.d("lv alltasks show data","ok "+lv_alltasks.toString());
  	      
		 
  	    
   
  	     
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
		
		
		  
		  lv_alltasks.setOnItemClickListener(new OnItemClickListener(){
             int newPos=0;
             int oldPos=0;
              
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
			
              if (TaskData.cursor_alltasks.getCount()>0){ 
            	 try{ 
            		 TaskData.selTaskID=String.valueOf(TaskData.cursor_alltasks.getString(TaskData.cursor_alltasks.getColumnIndex(TaskData.TdDB.TASK_ID)));
            		 TaskData.selTaskSN=String.valueOf(TaskData.cursor_alltasks.getString(TaskData.cursor_alltasks.getColumnIndex(TaskData.TdDB.TASK_SN)));
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
		  
		 /* m_listview.setOnTouchListener(new OnTouchListener(){

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
		
		  });*/
		  		
		  lv_alltasks.setOnItemLongClickListener(new OnItemLongClickListener(){
	           
				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long id) {
					// TODO Auto-generated method stub
					
					 try{ 
	            		 TaskData.selTaskID=String.valueOf(TaskData.cursor_alltasks.getString(TaskData.cursor_alltasks.getColumnIndex(TaskData.TdDB.TASK_ID)));
	            		 TaskData.selTaskSN=String.valueOf(TaskData.cursor_alltasks.getString(TaskData.cursor_alltasks.getColumnIndex(TaskData.TdDB.TASK_SN)));
	            	 }catch(Exception e){
	            		 Log.d(Tags, e.toString());
	            		 e.printStackTrace();
	            	 }
					DialogFragment_TaskDetail dg_taskdetail=new DialogFragment_TaskDetail();
	    	    	TaskTool.showDialog(dg_taskdetail);
		
					return false;
				}
	            
			 });
		
		  btn_taskupdate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 TaskData.adapterUpdate();
				
				 //Log.d("TdDB adapter update", "all done");
				 //Log.d("update cursor",String.valueOf(TaskData.cursor_alltasks.getCount())+"position"+String.valueOf(TaskData.cursor_alltasks.getPosition()));
				
			} 	
		  });
		 
		 
		  
		  Button btn_export=(Button)v.findViewById(R.id.export_bt);
		  btn_export.setOnClickListener(new OnClickListener(){

				
				@Override
				public void onClick(View v) {
			    	Cursor c= TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain +" where "+TaskData.TdDB.TASK_USER+"=?", new String[]{TaskData.user});
					// TODO Auto-generated method stub
			    	 
			    	SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd mm:ss"); 
					String reportname  ="alltaskslist"+formatter.format(new Date()); 			    	
	
			    	
			     if (c.getCount()>0){
			       
			    	 ExportUtils.exportListToFile(getActivity(),c,"alltaskslist");
			       
				}else{   
					  Toast.makeText(getActivity(), "记录为空", Toast.LENGTH_SHORT).show();	 
				  }
				}	
		 });
		  
		  
		  
		  btn_addrecord.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					
					// TODO Auto-generated method stub
					 //Toast.makeText(getActivity(), "TaskID"+TaskData.selTaskID,Toast.LENGTH_SHORT).show();
						if (TaskData.selTaskID!=null){
						DialogFragment_Comment dialogfrag_comment=new DialogFragment_Comment();
							showDialog(dialogfrag_comment);
							Log.d(Tags,"dialogfrag"+dialogfrag_comment.toString());
						  }else{
							Toast.makeText(getActivity(), "请先选定任务", Toast.LENGTH_LONG).show();	 
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
					      builder.setTitle("确认");
						  builder.setMessage("真要删除任务T"+TaskData.selTaskID+"吗？");
						  builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){

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
							             //TaskData.TdDB.TASK_STATUS+"="+"'open'"+" and "+
									     TaskData.TdDB.TASK_USER+"="+"'"+TaskData.user+"';");					 
								    
								
								
								 Cursor cr=DataSupport.findBySQL("select id as _id,sourceId from reminder where sourceId="+"'"+"T"+TaskData.selTaskID+"';");
								
								 if (cr.getCount()>0){
									   cr.moveToFirst();
									   do{
										   String remindersn = cr.getString(1);
									       Alarm.alarmCancel(getActivity(), remindersn);	   
									       int reminderno = cr.getInt(0);
									       DataSupport.delete(Reminder.class, reminderno);
									       Log.d(Tags,"delete reminder "+reminderno);   
									   
									   }while(cr.moveToNext());
	 
								   } 
								  
								 TaskData.adapterUpdate();
								 TaskData.selTaskID=null;
							     TaskData.selTaskSN=null;
							} 
						  } );
						  builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
							}	  
						  });
						  builder.create().show();	    
					  } else{
							Toast.makeText(getActivity(), "请先选定任务", Toast.LENGTH_SHORT).show();	 
					  };
				 }	  
					
			  });			
					
			  btn_taskclear.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						 //new ConnMySQL(getActivity()).ClearByGsonArrayRequestPost("TaskmainServlet",TaskData.TdDB.TABLE_NAME_TaskMain);
						 //Toast.makeText(getActivity(), "已全部清除",Toast.LENGTH_SHORT).show();
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
						      builder.setTitle("确认");
							  builder.setMessage("真要清除吗？");
							  builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){

								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									// TODO Auto-generated method stub
									//TextView show=(TextView)findViewById(R.layout.tv_alertdialog_pos);
								    //show.setText("");
									TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+"  where "+TaskData.TdDB.TASK_USER+"="+"'"+TaskData.user+"';");	
									//TaskData.adapter_opentasks.notifyDataSetChanged();
									 TaskTool.clearlinkedReminder(getActivity(),"T");
									 /*
									 Cursor cr=DataSupport.findBySQL("select id as _id from reminder where sourceId like"+"'"+"T%"+"';");
										
									 if (cr.getCount()>0){
										   cr.moveToFirst();
										   do{
										   int reminderno = cr.getInt(0);
										   DataSupport.delete(Reminder.class, reminderno);
										   Log.d(Tags,"clear reminder"+reminderno);   
										   
										   }while(cr.moveToNext());
									   } 
									*/
									 if (TaskData.privilege>TaskData.entryright){
								    	 Map params = new HashMap();
								    	
								    	 params.put("username", TaskData.user);
										 new ConnMySQL(getActivity()).ClearByGsonArrayRequestPost("TaskmainServlet",params); 
										 Log.d(Tags,"clear all taks by mysql");  
								    }
									 
									 
									TaskData.adapterUpdate();
									
									
									 
									 Log.d(Tags,"clear"+String.valueOf(TaskData.cursor_alltasks.getCount())+"position"+String.valueOf(TaskData.cursor_alltasks.getPosition()));
								    TaskData.selTaskID=null;
								} 
							  } );
							  builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){

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
					
					
					 Log.d(Tags,"update cursor"+String.valueOf(TaskData.cursor_opentasks.getCount())+"position"+String.valueOf(TaskData.cursor_opentasks.getPosition()));
					
					 
				
					 
				} 
			  });
		  
		  /*
		  btn_taskclear.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 new ConnMySQL(getActivity()).ClearByGsonArrayRequestPost("TaskmainServlet",TaskData.TdDB.TABLE_NAME_TaskMain);
					 //Toast.makeText(getActivity(), "已全部清除",Toast.LENGTH_SHORT).show();
					 Log.d("clear by Mysql","done");
					
				    TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+"  where "+TaskData.TdDB.TASK_USER+"="+TaskData.user+";"); 
				
					 Cursor cr=DataSupport.findBySQL("select id as _id from reminder where sourceId like"+"'"+"T%"+"';");
						
					 if (cr.getCount()>0){
						   cr.moveToFirst();
						   do{
						   int reminderno = cr.getInt(0);
						   DataSupport.delete(Reminder.class, reminderno);
						   Log.d("clear reminder", "clear "+reminderno);   
						   
						   }while(cr.moveToNext());
					   } 
					 
					
					
					
					TaskData.adapterUpdate();
					TaskData.selTaskID=null;
					TaskData.selTaskSN=null;
					
					 Log.d("TdDB adapter update", "all done");  
					 Log.d("clear",String.valueOf(TaskData.cursor_alltasks.getCount())+"position"+String.valueOf(TaskData.cursor_alltasks.getPosition()));
					
					
				} 
			  });
	
		  btn_taskdelete.setOnClickListener(new OnClickListener(){

				@SuppressWarnings("deprecation")
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					
					if (TaskData.selTaskID!=null){
					
							
							 
							 new ConnMySQL(getActivity()).DelByGsonArrayRequestPost("TaskmainServlet",TaskData.TdDB.TABLE_NAME_TaskMain, TaskData.selTaskSN);
							 Log.d("del by Mysql","done");
							 TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
							             TaskData.TdDB.TASK_SN+"="+"'"+TaskData.selTaskSN+"'"+" and "+
									     TaskData.TdDB.TASK_USER+"="+"'"+TaskData.user+"';");					 
							 Cursor cr=DataSupport.findBySQL("select id as _id from reminder where sourceId="+"'"+"T"+TaskData.selTaskID+"';");
							
							 if (cr.getCount()>0){
								   cr.moveToFirst();
								   do{
								   int reminderno = cr.getInt(0);
								   DataSupport.delete(Reminder.class, reminderno);
								   Log.d("delete reminder", "delete "+reminderno);   
								   
								   }while(cr.moveToNext());
							   } 
							 
							 //Toast.makeText(getActivity(), "已删除TaskID"+TaskData.selTaskID,Toast.LENGTH_SHORT).show(); 
							 TaskData.adapterUpdate();
							 TaskData.selTaskID=null;
						     TaskData.selTaskSN=null;
							
							
					
					    }else{
							Toast.makeText(getActivity(), "请先选定任务", Toast.LENGTH_SHORT).show();	 
						  }
						
					
				
				}
		  
			  });
		   */
		   
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

 class mScrollListener implements OnScrollListener {  
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totaltemCount) {
		// TODO Auto-generated method stub
		Log.d(Tags,"scorll to"+firstVisibleItem+"count"+visibleItemCount);
	}


	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (scrollState==SCROLL_STATE_IDLE){
			if (view.getLastVisiblePosition()==(view.getCount()-1)){
				int aaa=TaskData.cursor_alltasks.getCount()-pageCount*pageNum;
			  if ((aaa<=pageCount)&&(aaa>0))
			     { 
				  pageNum++;
				    TaskData.adapter_alltasks_pagedivided = new mcAdapter_tasks_pagedivided(getActivity(),R.layout.lv_alltasks,TaskData.cursor_alltasks,pageNum,pageCount);
				    //Log.d("adapter_alltasks",TaskData.adapter_alltasks.toString() );
				    TaskData.adapter_alltasks_pagedivided.notifyDataSetChanged();
				    lv_alltasks.setAdapter(TaskData.adapter_alltasks_pagedivided); 
				    //Toast.makeText(getActivity(), "最后第"+pageNum+"页",Toast.LENGTH_SHORT).show();
			     
			     }else{ if (aaa>pageCount){
						pageNum++;
					    TaskData.adapter_alltasks_pagedivided = new mcAdapter_tasks_pagedivided(getActivity(),R.layout.lv_alltasks,TaskData.cursor_alltasks,pageNum,pageCount);
					    //Log.d("adapter_alltasks",TaskData.adapter_alltasks.toString() );
					    TaskData.adapter_alltasks_pagedivided.notifyDataSetChanged();
					    lv_alltasks.setAdapter(TaskData.adapter_alltasks_pagedivided); 
						
						 //Toast.makeText(getActivity(), "第"+pageNum+"页",Toast.LENGTH_SHORT).show();
			          }	
			     }
			  }
		}
	}
  }


}
