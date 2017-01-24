package com.easygoal.achieve;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v4.widget.CursorAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class mcAdapter_opentasks extends CursorAdapter {

	
	private Context context;
	private Cursor c;
	private String deadline_str; 
	private String curTime_str; 
	private int lvitemlist;
	
    final class ViewHolder {  
		        public TextView taskname;   
		        public TextView taskdeadline;    
		        public TextView taskprogress; 
		        public TextView tasksnno; 
		        public Button taskdetails;
				public TextView taskpriority;
		    }    
   
    String Tags="mcAdapter_opentasks";
    
	public mcAdapter_opentasks(Context context, int lvitemlist,Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
		this.c=c;
		this.context=context;
		this.lvitemlist= lvitemlist;
		deadline_str=null;
		curTime_str=TaskTool.getCurTime();
	}


	@Override
	public void bindView(final View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
	
		ViewHolder holder = (ViewHolder) view.getTag();
		
		mContext=context;
		final Cursor c1 = cursor;
	    //cursor.moveToFirst();
	    
    
    	deadline_str=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_DEADLINE));
        if (TimeData.TimeGap_YYMMDDHHSS(curTime_str, deadline_str)<0){;
    	    SpannableString spanStr_time=new SpannableString("(¹ýÆÚ)");
    	    AbsoluteSizeSpan span_size = new AbsoluteSizeSpan(TaskData.overduetextsize); 
 		    ForegroundColorSpan span_color=new ForegroundColorSpan(TaskData.overduetextcolor);
 		    StyleSpan span_style = new StyleSpan(Typeface.ITALIC); 
		    spanStr_time.setSpan(span_style, 0,spanStr_time.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
 		    spanStr_time.setSpan(span_size, 0,spanStr_time.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
 		    spanStr_time.setSpan(span_color, 0,spanStr_time.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
 		    holder.taskname.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_NAME)));
 		    holder.taskname.append(spanStr_time);
        }else{
            holder.taskname.setText(cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_NAME)));
        }
    	holder.taskpriority.setText( cursor.getString(cursor  
                .getColumnIndex(TaskData.TdDB.TASK_PRIORITY))); 
    	holder.taskdeadline.setText(cursor.getString(cursor  
                .getColumnIndex(TaskData.TdDB.TASK_DEADLINE)));
    	holder.tasksnno.setText(cursor.getString(cursor  
                .getColumnIndex(TaskData.TdDB.TASK_SEQUENCENO)));
    	holder.taskprogress.setText( cursor.getString(cursor  
                .getColumnIndex(TaskData.TdDB.TASK_PROGRESS)));
    	final int p=cursor.getPosition();
    	final String click_Id2=cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_ID));
    	holder.taskdetails.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TaskData.selTaskID=click_Id2;
				//Log.d("selTaskID", ""+TaskData.selTaskID +"click" );
				//view.setBackgroundColor(color.green);
				ViewGroup parent = (ViewGroup) view.getParent();
				for(int i=0;i<parent.getChildCount();i++){
                    View childview=((ViewGroup) view.getParent()).getChildAt(i);
                    if (p == i) {
      
						//childview.requestFocus();
                        childview.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
                    } else {
                        childview.setBackgroundColor(mContext.getResources().getColor(R.color.mTextColor2));
                    }
				 }  
			//	DialogFragment_TaskDetail dialogfrag_taskdetail=new DialogFragment_TaskDetail();
				//TaskData.showDialog(dialogfrag_taskdetail);
					//Log.d("dialogfrag", dialogfrag_taskdetail.toString());
				
				DialogFragment_TaskDetail dialogfrag_taskdetail=new DialogFragment_TaskDetail();
		
				TaskTool.showDialog(dialogfrag_taskdetail);
				
			}
        });
      
       // super.bindView(view, context, cursor); 
	}

	public void setViewText(TextView name, String string) {
		// TODO Auto-generated method stub
		name.setText(string);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		//final View view = super.newView(context, cursor, parent);
		ViewHolder holder1= new ViewHolder();
		LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View view=inflater.inflate(lvitemlist ,parent,false);
      
        holder1.taskname = (TextView) view.findViewById(R.id.task_item1_name_tv);  
        holder1.tasksnno = (TextView) view.findViewById(R.id.task_item36_snno_tv); 
        holder1.taskpriority = (TextView) view.findViewById(R.id.task_item7_priority_tv); 
        holder1.taskdeadline = (TextView) view.findViewById(R.id.task_item15_deadline_tv);  
        holder1.taskprogress = (TextView) view.findViewById(R.id.task_item16_progress_tv);  
        holder1.taskdetails=(Button)view.findViewById(R.id.taskdetails_btn);
        view.setTag(holder1); 
       
        return view;  
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//Log.d(Tags,"cursor count"+String.valueOf(c.getCount()));
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
