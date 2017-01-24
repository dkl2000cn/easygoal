package com.easygoal.achieve;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import android.util.Log;

public class mJsonObjectRequest extends Request {

	
    private final String url;
    private ErrorListener errorlistener=null;
    private final JsonObject jo;
	
	public mJsonObjectRequest(String url,JsonObject jo,ErrorListener errorlistener) {
		super(url, errorlistener);
		// TODO Auto-generated constructor stub
		this.errorlistener=errorlistener;
		this.url=url;
		this.jo=jo;
	}



	
    private static Map<String, String> mHeader = new HashMap<String, String>();  
    /** 
     * ���÷����Լ�������ʱ���봫�ݵĲ�������Կ�� 
     */  
    static  
    {  
    	mHeader.put("Accept", "application/json");
    	mHeader.put("Content-Type", "application/json; charset=UTF-8");  
    }  
  
	
	

	

	@Override
	protected Response parseNetworkResponse(NetworkResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void deliverResponse(Object response) {
		// TODO Auto-generated method stub
		
	}


	// ���ڶ������������,�������ȼ��ͼ��뵽���е���Ž�������  
    @Override  
    public int compareTo(Request another) {  
        Priority myPriority = this.getPriority();  
        Priority anotherPriority = another.getPriority();  
        // ������ȼ����,��ô������ӵ����е����к�˳����ִ��  
        return myPriority.equals(another) ? this.getSequence() - another.getSequence()  
                : myPriority.ordinal() - anotherPriority.ordinal();  
    }  


	@Override
	public Map getHeaders() throws AuthFailureError {
		// TODO Auto-generated method stub
		//return super.getHeaders();
		return mHeader; 
	}

	
	 @Override  
	    public byte[] getBody() {  
	  
	        try {  
	        	Log.d("get body", jo.toString());
	        	
	            return jo.toString().getBytes("UTF-8");  
	              
	        } catch (Exception e) {  
	        }  
	        return null;  
	    }

	@Override
	public byte[] getPostBody() {
		// TODO Auto-generated method stub
		try {  
     	Log.d("get post body", jo.toString());
         return jo.toString().getBytes("UTF-8");  
           
     } catch (Exception e) {  
     }  
     return null; 
	}

	@Override
	public int getMethod() {
		// TODO Auto-generated method stub
		
		return Request.Method.POST;
	}  
	

}
