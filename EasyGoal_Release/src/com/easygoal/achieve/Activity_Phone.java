package com.easygoal.achieve;

import org.apache.http.util.TextUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_Phone extends Activity {
   
	public Activity_Phone() {
		// TODO Auto-generated constructor stub
	   //this.context=context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		  setContentView(R.layout.dialogfg_phoneinput);  
	 	  
	   	      final EditText et_userphone = (EditText)findViewById(R.id.et_userphone);
	   	   
	             Button btn_confirm=(Button)findViewById(R.id.confirm_bt);
	             Button btn_cancel=(Button)findViewById(R.id.cancel_bt);
	           
	             btn_confirm.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						
					    String phoneNo=et_userphone.getText().toString().trim();
					    
					    if (!TextUtils.isEmpty(phoneNo)&&TaskTool.isMobileNO(phoneNo)){
				    		TaskData.user=phoneNo;
				    		Toast.makeText(getApplication(),"ȷ�ϳɹ�", Toast.LENGTH_SHORT).show(); 
				    		finish();
				    	}else{	
				    		Toast.makeText(getApplication(),"��������ȷ���ֻ�����", Toast.LENGTH_SHORT).show();               	     
				    	}  
		              
					   
					}
	             });	
	             
				btn_cancel.setOnClickListener(new OnClickListener(){

			 				@Override
			 				public void onClick(View arg0) {
			 					// TODO Auto-generated method stub
			 					finish();
			 				}
			             	 
			              });	
	}  
}
