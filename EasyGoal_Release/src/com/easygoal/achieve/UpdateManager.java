package com.easygoal.achieve;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class UpdateManager {
	 // Ӧ�ó���Context
	 private Context mContext;
	 // ��ʾ��Ϣ
	 private String updateMsg = "�����µ�������������أ�";
	 // ���ذ�װ��������·��
	 private String apkUrl = TaskData.hostname+"easygoalV0.06.apk";
	 private Dialog noticeDialog;// ��ʾ��������µĶԻ���
	 private Dialog downloadDialog;// ���ضԻ���
	 private final String xmlPath = "/DCIM/";// ����apk���ļ���
	 private final String savePath = "/sdcard/updatedemo/";// ����apk���ļ���
	 private final String saveFileName = savePath + "UpdateDemoRelease.apk";
	 private final String xmlFileName = xmlPath + "update.xml";
	 // ��������֪ͨUIˢ�µ�handler��msg���� 
	 private ProgressBar mProgress;
	 private static final int DOWN_UPDATE = 1;
	 private static final int DOWN_OVER = 2;
	 private int progress;// ��ǰ����
	 private Thread downLoadThread; // �����߳�
	 private boolean interceptFlag = false;// �û�ȡ������
	 // ֪ͨ����ˢ�½����handler
	 private Handler mHandler = new Handler() {
	  @SuppressLint("HandlerLeak")
	  @Override
	  public void handleMessage(Message msg) {
	   switch (msg.what) {
	   case DOWN_UPDATE:
	    mProgress.setProgress(progress);
	    break;
	   case DOWN_OVER:
		//unstallApk();
	    installApk();
	    break;
	   }
	   super.handleMessage(msg);
	  }
	 };
	 public UpdateManager(Context context) {
	  this.mContext = context;
	 }
	 // ��ʾ���³���Ի��򣬹����������
	 public void checkUpdateInfo() {
	  showNoticeDialog();
	 }
	 private void showNoticeDialog() {
	  android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(
	    mContext);// Builder������ͨ����builder���øı�AleartDialog��Ĭ�ϵ�������ʽ�����������Ϣ
	  builder.setTitle("����汾����");
	  builder.setMessage(updateMsg);
	  builder.setPositiveButton("����", new OnClickListener() {
	   @Override
	   public void onClick(DialogInterface dialog, int which) {
	    dialog.dismiss();// ��ȡ���Ի������в���һ���Ĵ��룿ȡ���Ի���
	    showDownloadDialog();
	   }
	  });
	  builder.setNegativeButton("�Ժ���˵", new OnClickListener() {
	   @Override
	   public void onClick(DialogInterface dialog, int which) {
	    dialog.dismiss();
	   }
	  });
	  noticeDialog = builder.create();
	  noticeDialog.show();
	 }
	 /*
	 public static void inputstreamtofile(InputStream ins,File file) {
		  try {
			
			 if (file==null){ 
		       
		        Log.d("file null","not created");
			 }else{
				  OutputStream os = new FileOutputStream(file);
			 
		   int bytesRead = 0;
		   byte[] buffer = new byte[8192];
		   while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
		    os.write(buffer, 0, bytesRead);
		   }
		   os.close();
		   ins.close();
		   Log.d("file have",""+file.getTotalSpace());
			}
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		 }*/
		 
	 public void inputstreamtofile(InputStream ins,File file) throws IOException{
		 OutputStream os = new FileOutputStream(file);
		 int bytesRead = 0;
		 byte[] buffer = new byte[8192];
		 while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
		 os.write(buffer, 0, bytesRead);
		 }
		 os.close();
		 ins.close();
		 }
	 /**
		 * ����Ƿ���Ҫ���³���
		 * 
		 * @throws NameNotFoundException
		 */
	 
	 public static File getFilePath(String filePath,String fileName) {
            File file = null;
            makeRootDirectory(filePath);
          try {
                file = new File(filePath + fileName);
          } catch (Exception e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
         return file;
      }

    public static void makeRootDirectory(String filePath) {
         File file = null;
      try {
            file = new File(filePath);
           if (!file.exists()) {
          file.mkdir();
       }
      } catch (Exception e) {

      }
    }
    
    Handler myHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				Toast.makeText(mContext, "�汾�Ѹ���,��Ҫ����!", Toast.LENGTH_SHORT).show();
				showNoticeDialog();
				break;
			case 2:
				//Toast.makeText(mContext, "�汾�Ѹ���,��Ҫ����!", Toast.LENGTH_SHORT).show();
				//showDownloadDialog();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
    
    
    
	private Runnable xmlRunnable = new Runnable() {
		 
		 UpdateInfo updateInfo = new UpdateInfo();
			URL url;
		  @Override
		  public void run() { 
			  try {
					url = new URL(TaskData.hostname+"update.xml");
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					// connection.setConnectTimeout(5000);
					connection.connect();
					updateInfo = ParseXmlUtils.parseXml(connection.getInputStream());
					//connection.connect();
					/*
					 File file = new File(xmlPath);
					    if (!file.exists()) {
					     file.mkdir();
					     Log.d("file", "new"+file.getAbsolutePath());	
					    }else{
					      Log.d("file","have"+file.getAbsolutePath());	
					    }
					File xmlFile = new File(xmlFileName);
					Log.d("xmlfile", xmlFile.getAbsolutePath());	
					String curVer=VersionUtil.getVersionName(mContext);
					Log.d("curVer", curVer);
			        
					File xmlFile = getFilePath("/DCIM/","update.xml");
					
					 inputstreamtofile(connection.getInputStream(),xmlFile); 
					 
					// Log.d("xml to file", xmlFile.getAbsolutePath());
					 try {   
				            //1.��ȡfactory   
				            SAXParserFactory factory = SAXParserFactory.newInstance();   
				            //2.��ȡparser   
				            SAXParser parser = factory.newSAXParser();   
				            //3.��ȡ����ʱ�ļ���������   
				            SaxUtil su = new SaxUtil();   
				            //4.��ʼ����   
				            //parser.parse(new File("src/user-params.xml"), su);   
				            parser.parse(xmlFile, su);   
				            //System.out.println(su.getUser());   
				            updateInfo=su.getUpdateInfo();
				            Log.d("su updateInfo",updateInfo.getVersion());
				           // Log.d("versionutil",VersionUtil.getVersionName(mContext));
				        } catch (ParserConfigurationException e) {   
				            e.printStackTrace();   
				        } catch (SAXException e) {   
				            e.printStackTrace();   
				        } catch (IOException e) {   
				            e.printStackTrace();   
				        }   		
					*/
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					if (updateInfo.getVersion().equals(VersionUtil.getVersionName(mContext))) {
						//Toast.makeText(mContext, "�汾��ͬ,����Ҫ����!", Toast.LENGTH_SHORT).show();
						 Message msg = new Message();
						  msg.what = 2;						  
						  myHandler.sendMessage(msg);
					
					} else {
						//Toast.makeText(mContext, "�汾��ͬ,����Ҫ����!", Toast.LENGTH_SHORT).show();
						//checkUpdateInfo();
						 Message msg = new Message();
						  msg.what = 1;						  
						  myHandler.sendMessage(msg);
                    }
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	  
	 }
};	  
	 
		  void findVersion() {
			  downLoadThread = new Thread(xmlRunnable);
			  downLoadThread.start();
			 }
  
		  
	 protected void showDownloadDialog() {
	  android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
	  builder.setTitle("����汾����");
	  final LayoutInflater inflater = LayoutInflater.from(mContext);
	  View v = inflater.inflate(R.layout.progress, null);
	  mProgress = (ProgressBar) v.findViewById(R.id.progress);
	  builder.setView(v);// ���öԻ��������Ϊһ��View
	  builder.setNegativeButton("ȡ��", new OnClickListener() {
	   @Override
	   public void onClick(DialogInterface dialog, int which) {
	    dialog.dismiss();
	    interceptFlag = true;
	   }
	  });
	  downloadDialog = builder.create();
	  downloadDialog.show();
	  downloadApk();
	 }
	 private void downloadApk() {
	  downLoadThread = new Thread(mdownApkRunnable);
	  downLoadThread.start();
	 }
	 protected void installApk() {
	  File apkfile = new File(saveFileName);
	  if (!apkfile.exists()) {
	   return;
	  }
	  /*
	  Intent i = new Intent(Intent.ACTION_VIEW);
	  i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
	    "application/vnd.android.package-archive");// File.toString()�᷵��·����Ϣ
	  mContext.startActivity(i);
	  */
	  Intent i = new Intent(Intent.ACTION_VIEW);
	  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	  i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
	  "application/vnd.android.package-archive");
	  mContext.startActivity(i);
	  
	  
	 }
	 
	 public static String execCommand(String ...command)  {  
			Process process=null;  
			InputStream errIs=null;  
			InputStream inIs=null;  
			String result="";  

			try {  
				process=new ProcessBuilder().command(command).start();  
				ByteArrayOutputStream baos = new ByteArrayOutputStream();  
				int read = -1;  
				errIs=process.getErrorStream();           
				while((read=errIs.read())!=-1){  
					baos.write(read);  
				}  
				inIs=process.getInputStream();  
				while((read=inIs.read())!=-1){  
					baos.write(read);  
				}  
				result=new String(baos.toByteArray());  
				if(inIs!=null)  
					inIs.close();  
				if(errIs!=null)  
					errIs.close();  
				process.destroy();  
			} catch (IOException e) {  
				result = e.getMessage();  
			}  
			return result;  
		}  
	 
	 protected void unstallApk() {
		 String result = execCommand("pm","uninstall", "com.example.easygoal");
		 Toast.makeText(mContext, "ж�ؽ��:"+result, Toast.LENGTH_LONG).show();
		 }
	 
	 private Runnable mdownApkRunnable = new Runnable() {
	  @Override
	  public void run() {
	   URL url;
	   try {
	    url = new URL(apkUrl);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.connect();
	    int length = conn.getContentLength();
	    InputStream ins = conn.getInputStream();
	    File file = new File(savePath);
	    if (!file.exists()) {
	     file.mkdir();
	    }
	    String apkFile = saveFileName;
	    File ApkFile = new File(apkFile);
	    FileOutputStream outStream = new FileOutputStream(ApkFile);
	    int count = 0;
	    byte buf[] = new byte[1024];
	    do {
	     int numread = ins.read(buf);
	     count += numread;
	     progress = (int) (((float) count / length) * 100);
	     // ���ؽ���
	     mHandler.sendEmptyMessage(DOWN_UPDATE);
	     if (numread <= 0) {
	      // �������֪ͨ��װ
	      mHandler.sendEmptyMessage(DOWN_OVER);
	      break;
	     }
	     outStream.write(buf, 0, numread);
	    } while (!interceptFlag);// ���ȡ��ֹͣ����
	    outStream.close();
	    ins.close();
	   } catch (Exception e) {
	    e.printStackTrace();
	   }
	  }
	 };
}
