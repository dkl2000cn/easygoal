package com.easygoal.achieve;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.widget.Toast;
public class DoubleClickExitApp {

	   private Context mcontext;
	   /**�Ƿ����ٴε��back��*/
	   private boolean isOnKeyBack;
	   private Handler mHandler;
	   /**�˳���ʾToast*/
	   private Toast mExitToast;

	   public DoubleClickExitApp(Context context) {
	      this.mcontext = context;
	      this.mHandler = new Handler(Looper.getMainLooper());
	   }

	   /**����Activity�еĵ�������¼�����*/
	   public boolean onKeyDown(int keyCode, KeyEvent event) {
	      if(keyCode != KeyEvent.KEYCODE_BACK) {//������ǵ���ķ��ؼ����򷵻�false
	         return false;
	      }
	      if(isOnKeyBack) {
	         mHandler.removeCallbacks(onBackExitRunnable);
	         if(mExitToast != null){
	            mExitToast.cancel();
	         }
	         /**ֱ���˳�*/
	       //  ActivityManager am = (ActivityManager)getSystemService (Context.ACTIVITY_SERVICE);
	         //AppManager.getAppManager().AppExit(mcontext);
	         return true;
	      } else {
	         isOnKeyBack = true;
	         if(mExitToast == null) {
	            mExitToast = Toast.makeText(mcontext, "�ٰ�һ�η��ؼ��˳�Ӧ��", Toast.LENGTH_SHORT);
	         }
	         mExitToast.show();
	         mHandler.postDelayed(onBackExitRunnable, 2000);
	         return true;
	      }
	   }

	   private Runnable onBackExitRunnable = new Runnable() {

	      @Override
	      public void run() {
	         isOnKeyBack = false;
	         if(mExitToast != null){
	            mExitToast.cancel();
	         }
	      }
	   };
	}