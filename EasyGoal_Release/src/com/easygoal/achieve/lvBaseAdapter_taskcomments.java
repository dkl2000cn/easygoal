package com.easygoal.achieve;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class lvBaseAdapter_taskcomments extends BaseAdapter {

	  //用来接收传递过来的Context上下文对象
    private Context context;

    //构造函数
    public lvBaseAdapter_taskcomments(Context context)
    {   Log.d("Context", context.toString());
        this.context = context;
    }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub  
		  //LinearLayout pageLayout = ((LinearLayout) inflater).inflate(context, LayoutId, null);  
		 // convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_find_listview, null);
		 Log.d("view", "show view ready"); 
		convertView =LayoutInflater.from(context).inflate(R.layout.lvitemlist_taskcomments, null);
		
		// TextView tv1=(TextView)convertView.findViewById(R.id.record_item1_recordID_tv);
		 Log.d("view", "show view lvitemlistcomments");
		
		return convertView;
	}

	

	
}
