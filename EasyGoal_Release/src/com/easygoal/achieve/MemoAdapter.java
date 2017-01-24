package com.easygoal.achieve;


import android.app.AlarmManager;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MemoAdapter extends BaseAdapter {
	  private Context context;
		public MemoAdapter(Context context) {
		super();
		this.context = context;
	}
		 final class ViewHolder {  
		        public TextView name;  
		        public TextView content;  
		        public TextView createdtime;    
		        public ImageView imageview; 
		        public TextView file;  
		    }  
	
		@Override
		public int getCount() {
			return TaskData.memolist.size();
		}

		@Override
		public Object getItem(int position) {
			return TaskData.memolist.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		
			if(convertView == null){
				convertView = View.inflate(context, R.layout.lv_memo,null);
			}
			//Memo memo = DataSupport.find(Memo.class, position);
			ViewHolder holder = new ViewHolder();
			Memo memo=TaskData.memolist.get(position);
			if (memo!=null){
			holder.name = (TextView)convertView.findViewById(R.id.tv_memoname);
			holder.name.setText(memo.getName());
			holder.content= (TextView)convertView.findViewById(R.id.tv_memocontent);
			holder.content.setText(memo.getContent());
			holder.createdtime= (TextView)convertView.findViewById(R.id.tv_memocreatedtime);
			holder.createdtime.setText(memo.getCreatedtime());
			holder.imageview= (ImageView)convertView.findViewById(R.id.iv_memopic);
			holder.imageview.setImageURI(Uri.parse(memo.getPicUriStr()));
			holder.file= (TextView)convertView.findViewById(R.id.tv_file);
			//holder.file.setText(memo.getFile().getAbsolutePath());
			}else{
			 Toast.makeText(context, "empty", Toast.LENGTH_SHORT).show();	
			}
			
			return convertView;
		}
		
	}

