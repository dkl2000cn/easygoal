package com.easygoal.achieve;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
  
public class BaseActivity_Search extends AppCompatActivity implements SearchView.OnQueryTextListener {
  
  
    String username;
    String password;
    String phoneNo;
    String loginresult;
    int CONN_FLAG=-1;
    SearchView searchView;
    Cursor cursor_search;
    mcAdapter_group mcAdapter_search;
    ListView lv_taskrecord;
    TextView tv_searchresult;
    LinearLayout ll_searchresult;
    String Tags="SearchActivity";
    
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);  
        //getWindow().setBackgroundDrawable(null);
        //ActionBar actionBar = getActionBar();  
        //actionBar.setDisplayHomeAsUpEnabled(true);  
        //actionBar.setHomeAsUpIndicator(R.drawable.back_48px); 
      
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); 
		//toolbar.setTitle("Title");
	    //toolbar.setSubtitle("SubTitle");
	    //toolbar.setLogo(R.drawable.ic_launcher);
       
		setSupportActionBar(toolbar); 
	    toolbar.setNavigationIcon(R.drawable.back_48px);
		//mDrawerLayout = (CustomDrawerLayout) findViewById(R.id.main_drawer);        
		//Button mLvDrawer = (Button) findViewById(R.id.bt_drawer);  
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy��MM��dd�� HH:mm:ss ");
		Date curDate = new Date(System.currentTimeMillis());//��ȡ��ǰʱ��
	
	    //Log.d("create database", "start"+TaskData.d.toString());
		  final EditText et_user=(EditText)findViewById(R.id.et_user);
		  final EditText et_taskname=(EditText)findViewById(R.id.et_taskname);
		
		  tv_searchresult=(TextView)findViewById(R.id.tv_searchresult);
		  ll_searchresult=(LinearLayout)findViewById(R.id.ll_searchresult);
		  lv_taskrecord=(ListView)findViewById(R.id.taskrecord_lv);
		 
		 //Button btn_confirm=(Button)findViewById(R.id.btn_confirm);
		 
		  Button btn_search= (Button) findViewById(R.id.btn_search);  
		  
		  Button btn_export=(Button)findViewById(R.id.export_bt);
		  
		 
		  String str_wherestart=" where ";
	      String str_where = null;
		  String conn_where=str_wherestart+str_where;
	     
	      cursor_search=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain +" where "+TaskData.TdDB.TASK_USER+"=?", new String[]{TaskData.user});

		  mcAdapter_search = new mcAdapter_group(getApplication(),cursor_search);	 
		  //lv_taskrecord.setAdapter(mcAdapter_search);	
          
		  if (TaskData.privilege>TaskData.entryright){
			  btn_export.setVisibility(View.VISIBLE);
		  }else{
			  btn_export.setVisibility(View.GONE);
		  }
		
		   ll_searchresult.setVisibility(View.GONE);
		 
		  toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){

			@Override
			public boolean onMenuItemClick(MenuItem menuItem) {
				
				// TODO Auto-generated method stub
				switch (menuItem.getItemId()) {
			      
			      case android.R.id.home:
		               finish();
		              break;
		              /*  
			      case R.id.action_search:
		              
			    	  String a = TaskData.TdDB.TABLE_NAME_TaskMain;
				      String b = TaskData.TdDB.TABLE_NAME_TaskRecord;
					 String str_wherestart=" where ";
				      String str_where = null;
				      if (!TextUtils.isEmpty(et_user.getText().toString().trim())){
				    	  str_where=TaskData.TdDB.TASK_USER+" like "+ "'%"+et_user.getText().toString().trim()+"%'"+" and ";
				      }else{
				          str_where=TaskData.TdDB.TASK_USER+" == "+ "'"+TaskData.user+"'"+" and ";
				      }
				      if (!TextUtils.isEmpty(et_taskname.getText().toString().trim())){
				    	  str_where=str_where+TaskData.TdDB.TASK_NAME+" like "+"'%"+et_taskname.getText().toString().trim()+"%'"+" and ";
				      }
				     
				      if (!TextUtils.isEmpty(et_deadline.getText().toString().trim())){
				    	  str_where=str_where+TaskData.TdDB.TASK_DEADLINETIMEDATA+" < "+ et_deadline.getText().toString().trim()+" and ";
				      }
				      
				      if (!TextUtils.isEmpty(et_taskstatus.getText().toString().trim())){
				    	  str_where=str_where+TaskData.TdDB.TASK_STATUS+" == "+ et_taskstatus.getText().toString().trim()+" and ";
				      }
				      
				      String conn_where=str_wherestart+str_where;
				      String str_wheretotal = (conn_where).substring(0,conn_where.length()-5)+";";
				      
				      Cursor cursor_search=TaskData.db_TdDB.rawQuery("select * from "+a +
				    		  str_wheretotal, null);
				    //Log.d("rawquery","row"+c1.getCount()+"column"+c1.getColumnCount()+"position"+c1.getPosition());
					//c1.moveToFirst();
				    Log.d("cursor_search count", ""+cursor_search.getCount());
					mcAdapter_group mcAdapter_search = new mcAdapter_group(getApplication(),cursor_search);
					//Log.d("adapter_search",cursor_search.toString());
					lv_taskrecord.setAdapter(mcAdapter_search);			
					*/
				//TaskData.from_fg=showFrag(TaskData.from_fg,R.id.sublayout_task,subfrag_task,3);
			//	Log.d("task tab", "choice3");  
		            
		              
		          default:break;    
				
			    }
			  return false;
			}	
				
		 });
		  
		  
	      // Log.d("start join query","a"+a+"b"+b);
	       //Log.d("start join query",TaskData.TdDB.getDatabaseName());
	    //  Cursor cursor = db.rawQuery("SELECT a.*,b*b FROM a,b INNER JOIN a.TASK_ID=b.TASK_NO;",new String[]{});
	       //String TASK_NAME = "task_name";
	     
		//Cursor c1=TaskData.db_TdDB.rawQuery("select * from "+a+","+b+ " where "+a+"."+TASK_NAME+" like ?", new String[]{"name"});
	    
	     
		  
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
		  
		  
		   
		//lv_taskrecord.setClickable(true);
		
		 lv_taskrecord.setOnItemClickListener(new OnItemClickListener(){
           int newPos=0;
           int oldPos=0;
            
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
	   
		  }
		 });
		 
		
		  
		  btn_export.setOnClickListener(new OnClickListener(){

				
				@Override
				public void onClick(View v) {
					
					
			    	Cursor c=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+"=? and "+TaskData.TdDB.TASK_STATUS+"=?"+" order by "+TaskData.TdDB.TASK_SEQUENCENO+" asc", new String[]{TaskData.user,"open"});
					// TODO Auto-generated method stub
			       
			    	exportListToFile_NewApplication(c,"searchresult");
			  
				}	
		 });
		  
		btn_search.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				 //Log.d("from_fg", from_fg.toString()); 
                     
				
				    //Toast.makeText(getApplicationContext(), intent.getStringExtra(SearchManager.QUERY), Toast.LENGTH_SHORT).show();
					 String a = TaskData.TdDB.TABLE_NAME_TaskMain;
				      String b = TaskData.TdDB.TABLE_NAME_TaskRecord;
					 String str_wherestart=" where ";
				      String str_where = null;
				      if (!TextUtils.isEmpty(et_user.getText().toString().trim())){
				    	  str_where=TaskData.TdDB.TASK_USER+" like "+ "'%"+et_user.getText().toString().trim()+"%'"+" and ";
				      }else{
				          str_where=TaskData.TdDB.TASK_USER+" == "+ "'"+TaskData.user+"'"+" and ";
				      }
				      if (!TextUtils.isEmpty(et_taskname.getText().toString().trim())){
				    	  str_where=str_where+TaskData.TdDB.TASK_NAME+" like "+"'%"+et_taskname.getText().toString().trim()+"%'"+" and ";
				      }
				      /*
				      if (!TextUtils.isEmpty(et_deadline.getText().toString().trim())){
				    	  str_where=str_where+TaskData.TdDB.TASK_DEADLINETIMEDATA+" < "+ et_deadline.getText().toString().trim()+" and ";
				      }
				      
				      if (!TextUtils.isEmpty(et_taskstatus.getText().toString().trim())){
				    	  str_where=str_where+TaskData.TdDB.TASK_STATUS+" == "+ et_taskstatus.getText().toString().trim()+" and ";
				      }
				      */
				      String conn_where=str_wherestart+str_where;
				      String str_wheretotal = (conn_where).substring(0,conn_where.length()-5)+";";
				      
				      Cursor cursor_search=TaskData.db_TdDB.rawQuery("select * from "+a +
				    		  str_wheretotal, null);
				    //Log.d("rawquery","row"+c1.getCount()+"column"+c1.getColumnCount()+"position"+c1.getPosition());
					//c1.moveToFirst();
				    Log.d(Tags,"cursor_search count"+cursor_search.getCount());
					mcAdapter_group mcAdapter_search = new mcAdapter_group(getApplication(),cursor_search);
					//Log.d("adapter_search",cursor_search.toString());
					lv_taskrecord.setAdapter(mcAdapter_search);			
					
				//TaskData.from_fg=showFrag(TaskData.from_fg,R.id.sublayout_task,subfrag_task,3);
			//	Log.d("task tab", "choice3");
			}
		    });	
    }
    
  
    
    @Override  
    public boolean onCreateOptionsMenu(Menu menu) {  
        // Inflate the menu; this adds items to the action bar if it is present.  
        getMenuInflater().inflate(R.menu.main_search, menu);  
        
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuItem = menu.findItem(R.id.action_search);//�ڲ˵����ҵ���Ӧ�ؼ���item 
        searchView =(SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        /*
        searchView.setIconifiedByDefault(true);  
        searchView.setFocusable(true);  
        searchView.setIconified(false);  
        searchView.requestFocusFromTouch(); 
        */
        if (searchView!= null) {
            searchView = (SearchView) searchView;
            //4.����SearchView �Ĳ�ѯ�ص��ӿ�
            AppCompatImageView button=( AppCompatImageView)searchView.findViewById(android.support.v7.appcompat.R.id.search_button);

            button.setImageResource(R.drawable.search_48px);

            searchView.setOnQueryTextListener(this);

            //�����������û����ʾ��ʱ�� ���Action ,�ص�����ӿڣ�������ʾ�����
//            searchView.setOnSearchClickListener();
            //���Զ���ȫ�����ݱ�ѡ�е�ʱ��ص��ӿ�
//            searchView.setOnSuggestionListener();

            //���������������Զ���ȫ������ʵ��������ʷ
//            searchView.setSuggestionsAdapter();

        }
        
        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {//���ô򿪹رն�������  
            @Override  
            public boolean onMenuItemActionExpand(MenuItem item) {  
                //Toast.makeText(SearchActivity.this, "onExpand", Toast.LENGTH_SHORT).show();  
                return true;  
            }  
  
            @Override  
            public boolean onMenuItemActionCollapse(MenuItem item) {  
                //Toast.makeText(SearchActivity.this, "Collapse", Toast.LENGTH_SHORT).show();  
                return true;  
            }  
        });  
        return super.onCreateOptionsMenu(menu);  
       
    };  
    
   
   public boolean onQueryTextSubmit(String query) {
       //Toast.makeText(SearchActivity.this, "Submit" + query, Toast.LENGTH_SHORT).show();

       searchView.clearFocus();
       
       //Toast.makeText(getApplicationContext(), intent.getStringExtra(SearchManager.QUERY), Toast.LENGTH_SHORT).show();
		 String a = TaskData.TdDB.TABLE_NAME_TaskMain;
	    
		 String str_wherestart=" where ";
	      String str_where = "";
	      /*
	      if (!TextUtils.isEmpty(query)){
	    	  str_where=TaskData.TdDB.TASK_USER+" like "+ "'%"+query+"%'"+" and ";
	      }else{
	          str_where=TaskData.TdDB.TASK_USER+" == "+ "'"+TaskData.user+"'"+" and ";
	      }*/
	      if (!TextUtils.isEmpty(query.trim())){
	    	  str_where=str_where+TaskData.TdDB.TASK_NAME+" like "+"'%"+query+"%'"+" and ";
	    	  String conn_where=str_wherestart+str_where;
		      String str_wheretotal = (conn_where).substring(0,conn_where.length()-5)+";";
		      Log.d(Tags,"cursor_search string "+str_wheretotal);
		     cursor_search=TaskData.db_TdDB.rawQuery("select * from "+a +
		    		  str_wheretotal, null);
		    //Log.d("rawquery","row"+c1.getCount()+"column"+c1.getColumnCount()+"position"+c1.getPosition());
			//c1.moveToFirst();
		     mcAdapter_search = new mcAdapter_group(getApplication(),cursor_search);
				//Log.d("adapter_search",cursor_search.toString());
		     lv_taskrecord.setAdapter(mcAdapter_search);	
		    Log.d(Tags,"cursor_search count"+cursor_search.getCount());
			//mcAdapter_group mcAdapter_search = new mcAdapter_group(getApplication(),cursor_search);
			//Log.d("adapter_search",cursor_search.toString());
		    tv_searchresult.setText("�ҵ�"+cursor_search.getCount()+"����¼");
		    if (cursor_search!=null&&cursor_search.getCount()>0){
				 ll_searchresult.setVisibility(View.VISIBLE);
			}else{
				 ll_searchresult.setVisibility(View.GONE);
			}
	      
	      }
	      /*
	      if (!TextUtils.isEmpty(et_deadline.getText().toString().trim())){
	    	  str_where=str_where+TaskData.TdDB.TASK_DEADLINETIMEDATA+" < "+ et_deadline.getText().toString().trim()+" and ";
	      }
	      
	      if (!TextUtils.isEmpty(et_taskstatus.getText().toString().trim())){
	    	  str_where=str_where+TaskData.TdDB.TASK_STATUS+" == "+ et_taskstatus.getText().toString().trim()+" and ";
	      }
	      */
	      
       return true;
   }

   /**
    * ÿһ�������ַ�������������������ʵ�����������빦��
    * @param newText
    * @return
    */
   public boolean onQueryTextChange(String newText) {
       //Toast.makeText(SearchActivity.this, "" + newText, Toast.LENGTH_SHORT).show();
       return true;
   }
   
  void exportListToFile_NewApplication(Cursor c,String listname){
		
		final String[] taskitemlist={
	  			  TaskData.TdDB.TASK_NAME,
	  			  //TaskData.TdDB.TASK_USER,
	  			  TaskData.TdDB.TASK_PRIORITY,
	  			  TaskData.TdDB.TASK_SEQUENCENO,
	  			  TaskData.TdDB.TASK_CREATEDTIME,
	  			  TaskData.TdDB.TASK_STARTEDTIME,
	  			  TaskData.TdDB.TASK_DEADLINE,
	  			  TaskData.TdDB.TASK_DURATION,
	  			  TaskData.TdDB.TASK_PROGRESS,
	  			  TaskData.TdDB.TASK_STATUS,
	  			  TaskData.TdDB.TASK_REMARKS
	  			  //TaskData.TdDB.TASK_STATUS,
	  			  //TaskData.TdDB.TASK_FINISHED,
	  			  //TaskData.TdDB.TASK_DELAYED    		
	  	       };
	  	    
	  	   final String[] titlenamelist={
	  		     //"������","������","���ܷ���","��������","����ʱ��","��ʼʱ��","�������","Ԥ����ʱ(hr)","���%","���״̬","��ע"	 
	  		     "������","���ܷ���","��������","����ʱ��","��ʼʱ��","�������","Ԥ����ʱ(hr)","���%","���״̬","��ע"	  
	  	     };

  	      SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd mm:ss"); 
	  	  String reportname  =listname+formatter.format(new Date()); 
  
       if (c.getCount()>0){
     
		 File outfile=new ExportListToExcel(getApplication(),TaskData.TdDB.TABLE_NAME_TaskMain,
				c,
				titlenamelist,
				taskitemlist)
		        .writeExcel(reportname);
		   Intent intent2 = new Intent(Intent.ACTION_SEND);
	       intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	       intent2.setType("text/*");
	       if (outfile != null && outfile.exists()) {  
	    	  
		        Uri u = Uri.fromFile(outfile);  
		        Log.d(Tags,"uri"+u.toString());
		       intent2.putExtra(Intent.EXTRA_STREAM, u);  
		      } else{
		    	  Log.d(Tags,"file1 not exist");
		      }
	       
		   startActivity(Intent.createChooser(intent2, listname)); 
		    
       }
	}
   
}
