package com.easygoal.achieve;





import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

public class mcAdapter_grouprecorddetails extends CursorAdapter {

	    private Context context;
	    private Cursor c;
	    private int layout_listitem;
	  
	   // ListAdapter lvBaseAdapter_taskcomments;
	    //lvBaseAdapter_taskcomments lvBaseAdapter_taskcomments; 
	   
		 final class ViewHolder { 
				       
		    	public TextView taskid_no;
		    	public TextView username;
		    	public TextView record_time;   
		    	public TextView record_progress;
		    	public CheckBox record_comments;		    
		    	public TextView record_done;
		    	
		    }    
	    
	public mcAdapter_grouprecorddetails(Context context, Cursor c,int layout) {
		super(context, c);
		// TODO Auto-generated constructor stub
		this.c=c;
		this.context=context;
		this.layout_listitem=layout;
	
	Log.d("create mcAapter_taskrecord",context.toString());
	}


	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		Log.d("madapter bind view","ready");
		
		ViewHolder holder = (ViewHolder) view.getTag(); 
		cursor=this.c;
		Log.d("madapter bindview cursor",String.valueOf(cursor.getPosition())+"row"+String.valueOf(cursor.getCount()));
		//Log.d("mcAapter_taskrecord bindview cursor",cursor.toString()+"ok");
		//Log.d("madapter bindview cursor",cursor.getString(cursor  
           //     .getColumnIndex("task_name"))+cursor.getString(cursor  
          //      .getColumnIndex("task_importance"))+cursor.getString(cursor  
           //     .getColumnIndex("record_recordtime"))+cursor.getString(cursor  
          //      .getColumnIndex("record_comments"))); 
		//setViewText(holder.taskid_no, cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_TASKID_NO))); 
		//setViewText(holder.taskid_no, cursor.getString(3));      
		//holder.record_comments.setText(cursor.getString(5));       
       // setViewText(holder.name, cursor.getString(cursor  
             //   .getColumnIndex("task_importance"))); 
		
		Log.d("madapter bindview cursor",cursor.getString(cursor  
		                .getColumnIndex(TaskData.TdDB.RECORD_TASKID))+
		                cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_TASKID_NO))+
		                		cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_COMMENTS))); 
		if (holder.taskid_no!=null){
		holder.taskid_no.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_TASKID)));
		}
		if (holder.record_comments!=null){
		holder.taskid_no.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_TASKID_NO)));	
		holder.record_comments.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_COMMENTS)));
		holder.record_comments.setChecked(Boolean.valueOf(cursor.getString((cursor.getColumnIndex(TaskData.TdDB.RECORD_DONE)))));
		holder.record_time.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_TIME)));
        holder.record_progress.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_PROGRESS))); 
        String done=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.RECORD_DONE));
        holder.taskid_no.setTextColor(Color.BLACK);
        holder.record_comments.setTextColor(Color.BLACK);
        holder.record_time.setTextColor(Color.BLACK);
        holder.record_progress.setTextColor(Color.BLACK);
        Log.d("record done", done);
        if (done.equals("true")){
         holder.username.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_USER)));    
         holder.username.setTextColor(Color.BLACK);
        }
       }
		 
	}

	
	
	
	
	private void setViewText(TextView name, String string) {
		// TODO Auto-generated method stub
		name.setText(string);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		//final View view = super.newView(context, cursor, parent);  
		
		ViewHolder holder= new ViewHolder(); 
		
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
       
        View view=inflater.inflate(layout_listitem ,parent,false);
        Log.d("new view", view.toString());
        holder.taskid_no = (TextView) view.findViewById(R.id.tv_taskid_no); 
        holder.record_time=(TextView) view.findViewById(R.id.tv_recordtime); 
        holder.username=(TextView) view.findViewById(R.id.task_item_user_tv); 
        holder.record_progress=(TextView) view.findViewById(R.id.tv_recordprocess);
        holder.record_comments = (CheckBox) view.findViewById(R.id.cb_recordcomments); 
        //Log.d("holder", holder.taskid_no.toString());
        //Log.d("holder", holder.record_comments.toString());
        //Log.d("holder", holder.record_progress.toString());
        //Log.d("holder", holder.record_time.toString());
        view.setTag(holder);
        return view;  
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.d("mcAapter_taskrecord cursor count", String.valueOf(c.getCount()));
		return c.getCount();
	}

	@Override
	public Cursor getCursor() {
		// TODO Auto-generated method stub
		return super.getCursor();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return super.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return super.getItemId(position);
	}

}
