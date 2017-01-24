package com.easygoal.achieve;

import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

public class DialogFragment_Feedback extends DialogFragment {

	
	public DialogFragment_Feedback() {
		// TODO Auto-generated constructor stub
	  
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=inflater.inflate(R.layout.leftmenu_feedback, container);
	    Button btn_closewin = (Button) v.findViewById(R.id.btn_closewin);  
		 
	    btn_closewin.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View arg0) {  
	                // �رնԻ���  
	                dismiss();  
	            }  
	        });  
	    Button btn_cancel = (Button) v.findViewById(R.id.btn_cancel);  
		 
	    btn_cancel.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View arg0) {  
	                // �رնԻ���  
	                dismiss();  
	            }  
	        }); 
	    Button btn_confirm = (Button) v.findViewById(R.id.btn_confirm);  
	    final MultiAutoCompleteTextView body = (MultiAutoCompleteTextView)v.findViewById(R.id.mactv_body);
		final EditText mailaddress = (EditText)v.findViewById(R.id.et_address);
		final EditText title = (EditText)v.findViewById(R.id.et_title);
       // mactv.setBackgroundResource(R.drawable.shape_line_rectangle);	
		body.setOnFocusChangeListener(new View.OnFocusChangeListener() {
	    	@Override
	    	public void onFocusChange(View v, boolean hasFocus) {
	    	if (hasFocus) {
	    		//InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);  
	    		//imm.showSoftInput(v,InputMethodManager.SHOW_FORCED);
	    	   }
	    	 //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	    	 }
	    });
	    
	    btn_confirm.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View arg0) {  
	                // �رնԻ���  
	            	Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);  
		     		//�����ı���ʽ
	            	emailIntent.setData(Uri.parse("mailto:"+mailaddress.getText().toString()));
	            	emailIntent.setType("text/plain");  
		     		//emailIntent.setType("application/octet-stream");
		     		//���öԷ��ʼ���ַ  
		     		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[]{mailaddress.getText().toString()});  
		     		//���ñ�������  
		     		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,title.getText().toString());  
		     		//�����ʼ��ı�����  
		     		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,body.getText().toString());  
		     		startActivity(Intent.createChooser(emailIntent,"Send mail..."));  
		     		//Toast.makeText(getActivity(), "�ʼ��ѷ���,лл���ķ���!", Toast.LENGTH_SHORT).show();
		     		dismiss();
		     		//Toast.makeText(getActivity(), "�ʼ��ѷ���,лл���ķ���!", Toast.LENGTH_SHORT).show();
	            }  
	        });  
 		return v;	
    }
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
		getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height );
	}  
}
