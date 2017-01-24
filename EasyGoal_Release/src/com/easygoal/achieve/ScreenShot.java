package com.easygoal.achieve;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;  
   
public class ScreenShot {  
	
    public static Bitmap takeScreenShot(final Activity activity,File filePath) {
    	
    	
    	Bitmap bmshot = null;
    	final String Tags="ScreenShot";
		   class myAsynTask extends AsyncTask<File,Integer,Bitmap>{
			   
			   @Override
		        protected void onPreExecute() {
				  
		        }   
			   
			@Override
			protected Bitmap doInBackground(File... filePath) {
				// TODO Auto-generated method stub
				    View view = activity.getWindow().getDecorView();
			        view.setDrawingCacheEnabled(true);  
			        view.buildDrawingCache();  
			        Bitmap b1 = view.getDrawingCache();  
			   
			        // 获取状态栏高度  
			        //Rect frame = new Rect();  
			        //activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);  
			        //int statusBarHeight = frame.top;  
			   
			        // 获取屏幕长和高  
			        //int width = activity.getWindowManager().getDefaultDisplay().getWidth();  
			        //int height =activity.getWindowManager().getDefaultDisplay().getHeight();  
			        // 去掉标题栏  
			        int width = view.getWidth();  
			        int height =view.getHeight();  
			        //Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height-statusBarHeight);  
			        Bitmap b = Bitmap.createBitmap(b1, 0, 0, width, height); 
			        //Log.d(Tags, "statusheight"+statusBarHeight+"width"+width+"height"+height+"final height"+(height-statusBarHeight));
			        Log.d(Tags, "statusheight"+"width"+width+"height"+height);
			        view.destroyDrawingCache();  
			        
			        FileOutputStream fos = null;  
			        try {  
			            fos = new FileOutputStream(filePath[0]);  
			            if (fos!=null) {  
			                b.compress(Bitmap.CompressFormat.PNG, 100, fos);  
			                fos.flush();  
			                fos.close();  
			                Log.d(Tags,"fos"+fos.toString());
			            }  
			        } catch (FileNotFoundException e) {  
			            // e.printStackTrace();  
			        	Log.d(Tags,"fos error Filenotfound");
			        } catch (IOException e) {  
			            // e.printStackTrace();
			        	Log.d(Tags,"fos error IOException");
			        }  
			        
			        
				return b;
			}
  		
			@Override
				protected void onPostExecute(Bitmap bm) {
				// TODO Auto-generated method stub
					
				if (bm != null) {
					
					return ;
				}
			
				return;
				}
			
    	};
       
		try {
			bmshot = new myAsynTask().execute(filePath).get();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
        // View是你需要截图的View  
        /*
    	View view = activity.getWindow().getDecorView();  
        view.setDrawingCacheEnabled(true);  
        view.buildDrawingCache();  
        Bitmap b1 = view.getDrawingCache();  
   
        // 获取状态栏高度  
        Rect frame = new Rect();  
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);  
        int statusBarHeight = frame.top;  
   
        // 获取屏幕长和高  
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();  
        int height = activity.getWindowManager().getDefaultDisplay()  
                .getHeight();  
        // 去掉标题栏  
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height  
                - statusBarHeight);  
        view.destroyDrawingCache();  
        Log.d("Bitmap", b.toString());
        return b;  */
    	return bmshot;
    }  
   
   static void savePic(Bitmap b, File filePath) {  
        FileOutputStream fos = null;  
        final String Tags="ScreenShot|savePic|";
        try {  
            fos = new FileOutputStream(filePath);  
            if (null != fos) {  
                b.compress(Bitmap.CompressFormat.PNG, 100, fos);  
                fos.flush();  
                fos.close();  
                Log.d(Tags,"fos"+fos.toString());
            }  
        } catch (FileNotFoundException e) {  
            // e.printStackTrace();  
        	Log.d(Tags,"fos error"+"Filenotfound");
        } catch (IOException e) {  
            // e.printStackTrace();
        	Log.d(Tags,"fos error"+"IOException");
        }  
    }  
   
    public static void shoot(Activity a, File filePath) {  
    	final String Tags="ScreenShot|shoot|";
        if (filePath == null) {  
        	 Log.d(Tags,"filepath"+"null");
            return;  
        }  
        /*if (!filePath.getParentFile().exists()) {  
            filePath.getParentFile().mkdirs();  
            Log.d("filepath","make dirs");
        }  */
       
        ScreenShot.takeScreenShot(a, filePath);
        Log.d(Tags,"begin shoot"+filePath.toString()+filePath.getTotalSpace());
    }

}