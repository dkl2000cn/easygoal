package com.easygoal.achieve;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class tvBaseAdapter extends BaseAdapter {

	  //用来接收传递过来的Context上下文对象
    private Context context;
    private String temp;

    //构造函数
    public tvBaseAdapter(Context context,String value)
    {   Log.d("Context", context.toString());
        this.context = context;
        this.temp=value;
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
		convertView =LayoutInflater.from(context).inflate(R.layout.tvheader, null);
		
		TextView tv1=(TextView)convertView.findViewById(R.id.header_tv);
		tv1.setText(temp);
		Log.d("view value", "show value");
		
		return convertView;
	}

	

	
}
