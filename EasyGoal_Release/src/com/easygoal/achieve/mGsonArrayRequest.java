package com.easygoal.achieve;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import android.text.TextUtils;
import android.util.Log;

public class mGsonArrayRequest extends Request {


    private final String url;
    private final String doCode;
    private ErrorListener errorlistener=null;
    private Listener listener;
    private JsonArray jolist=null;
    private int SOCKET_TIMEOUT=15000;
    
	public mGsonArrayRequest(String url,JsonArray jolist,String doCode ,Listener listener,ErrorListener errorlistener) {
		super(url, errorlistener);
		// TODO Auto-generated constructor stub
		this.errorlistener=errorlistener;
		this.listener=listener;
		this.url=url;
		this.jolist=jolist;
		this.doCode=doCode;
	}



	
    private static Map<String, String> mHeader = new HashMap<String, String>();  
    /** 
     * ���÷����Լ�������ʱ���봫�ݵĲ�������Կ�� 
     */  
    static  
    {  
    	//mHeader.put("Accept", "application/json");
    	//mHeader.put("Content-Type", "text/html;charset=UTF-8"); 
    	
        mHeader.put("Charset", "UTF-8");  
    	mHeader.put("Content-Type", "text/html;charset=UTF-8");  
    	//mHeader.put("Accept-Encoding", "gzip,deflate");  
    }  
  
	
	@Override
	protected Response parseNetworkResponse(NetworkResponse response) {
		// TODO Auto-generated method stub
	  if (response.headers!=null){
		try {
            String jsonString =new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            //Log.d("json back", jsonString.toString());
            JSONArray gsonArrayback;
            if (!TextUtils.isEmpty(jsonString)){
                gsonArrayback = new JSONArray(jsonString);
            }else{
                gsonArrayback=new JSONArray("[]");
            }
            return Response.success(gsonArrayback,HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.error(new ParseError(e));
		}
	   }else{
		   return null;
	   }
	}

	@Override
	protected void deliverResponse(Object response) {
		// TODO Auto-generated method stub
		//Log.d("json back", "deliver"+response.toString());
		 listener.onResponse(response); 
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
	        	
	        	String reqString = doCode+jolist.toString();
	        	Log.d("get body", reqString.toString());
	            return reqString.getBytes("UTF-8");  
	              
	        } catch (Exception e) {  
	        }  
	        return null;  
	    }

	@Override
	public byte[] getPostBody() {
		// TODO Auto-generated method stub
		try {  
     	
     	String reqString = doCode+jolist.toString();
     	Log.d("get post body", reqString.toString());
        return reqString.getBytes("UTF-8");   
           
     } catch (Exception e) {  
     }  
     return null; 
	}

	@Override
	public int getMethod() {
		// TODO Auto-generated method stub
		
		return Request.Method.POST;
	}


	@Override
	public RetryPolicy getRetryPolicy() {
		// TODO Auto-generated method stub
		//RetryPolicy retryPolicy = new DefaultRetryPolicy(SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		RetryPolicy retryPolicy=new DefaultRetryPolicy(SOCKET_TIMEOUT, 1, 1.0f);
		return retryPolicy;
	}  
	

}
