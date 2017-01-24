package com.easygoal.achieve;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.jakewharton.disklrucache.DiskLruCache;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public class FileUtils {

	public File getDiskCacheDir(Context context, String uniqueName) {  
	    String cachePath;  
	    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())  
	            || !Environment.isExternalStorageRemovable()) {  
	        cachePath = context.getExternalCacheDir().getPath();  
	    } else {  
	        cachePath = context.getCacheDir().getPath();  
	    }  
	    return new File(cachePath + File.separator + uniqueName);  
	}  
	
	public static String getDiskCacheDirPath(Context context) {  
	    String cachePath;  
	    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())  
	            || !Environment.isExternalStorageRemovable()) {  
	        cachePath = context.getExternalCacheDir().getPath();  
	    } else {  
	        cachePath = context.getCacheDir().getPath();  
	    }  
	    return cachePath+ File.separator;  
	}  
	
	public DiskLruCache openDishCache(Context context,String dirname, int valueCount, long maxSize){
		DiskLruCache mDiskLruCache = null;  
		try {  
			File cacheDir = getDiskCacheDir(context, dirname);  
	      if (!cacheDir.exists()) {  
	         cacheDir.mkdirs();  
	      }  
	         mDiskLruCache = DiskLruCache.open(cacheDir, Integer.parseInt(AppUtils.getVersionName(context)), valueCount, maxSize);  
	         return mDiskLruCache;
		} catch (IOException e) {  
			e.printStackTrace();  
	    }  
	   
	  return null;
    }  
	
	public String hashKeyForDisk(String key) {  
	    String cacheKey;  
	    try {  
	        final MessageDigest mDigest = MessageDigest.getInstance("MD5");  
	        mDigest.update(key.getBytes());  
	        cacheKey = bytesToHexString(mDigest.digest());  
	    } catch (NoSuchAlgorithmException e) {  
	        cacheKey = String.valueOf(key.hashCode());  
	    }  
	    return cacheKey;  
	}  
	  
	private String bytesToHexString(byte[] bytes) {  
	    StringBuilder sb = new StringBuilder();  
	    for (int i = 0; i < bytes.length; i++) {  
	        String hex = Integer.toHexString(0xFF & bytes[i]);  
	        if (hex.length() == 1) {  
	            sb.append('0');  
	        }  
	        sb.append(hex);  
	    }  
	    return sb.toString();  
	}  
	

	   public void writeBmp2File(Bitmap b,String outfilepath){
		 String Tags="Bmp2File";	
		 FileOutputStream fos = null;  
	     try {  
	         fos = new FileOutputStream(outfilepath);  
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
		} 
	  
	   public byte[] Bitmap2Bytes(Bitmap bm) {
		      ByteArrayOutputStream baos = new ByteArrayOutputStream();
		      bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		      return baos.toByteArray();
		  }	 
	   
		public Bitmap getBitmapFromUri(Context context,Uri uri){
			  try
			  {
			   // ��ȡuri���ڵ�ͼƬ
			   Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
			   return bitmap;
			  }
			  catch (Exception e)
			  {
			   Log.e("[Android]", e.getMessage());
			   Log.e("[Android]", "Ŀ¼Ϊ��" + uri);
			   e.printStackTrace();
			   return null;
			  }
			}
	   
		 public static void savePic(Bitmap b, File filePath) {  
		        FileOutputStream fos = null;  
		        try {  
		            fos = new FileOutputStream(filePath);  
		            if (null != fos) {  
		                b.compress(Bitmap.CompressFormat.PNG, 100, fos);  
		                fos.flush();  
		                fos.close();  
		                Log.d("|fos|", fos.toString());
		            }  
		        } catch (FileNotFoundException e) {  
		            // e.printStackTrace();  
		        	Log.d("fos error", "Filenotfound");
		        } catch (IOException e) {  
		            // e.printStackTrace();
		        	Log.d("fos error", "IOException");
		        }  
		    }  
		
	   public void writeBmp2Cache(Context context,Bitmap b){
			 String Tags="Bmp2File";	
			 FileOutputStream fos = null; 
			 String outfilepath=getDiskCacheDirPath(context);
		     try {  
		         fos = new FileOutputStream(outfilepath);  
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
			}    
	   /*
	   public void writeBmp2Cache(Bitmap b,DiskLruCache mDiskLruCache){
			 String Tags="writeBmp2Cache";
			 //File file=new File(filename);
			 //FileOutputStream fos = null; 
			 //String key=hashKeyForDisk(filename);
			  FileOutputStream fos = null; 
			  final DiskLruCache  mdc= mDiskLruCache;
			  
					 new Thread(new Runnable() {  
						    @Override  
						    public void run() {  
						        try {  
						            //String imageUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";  
						            //String key = hashKeyForDisk(fn);  
						            //DiskLruCache.Editor editor = mdc.edit(key);  
						           // if (editor != null) {  
						                //OutputStream outputStream = editor.newOutputStream(0);
						                //FileInputStream in = new FileInputStream(fn);
						                /*
						                while ((outputStream=in.read())!=-1) {  
						                    editor.commit();  
						                } else {  
						                    editor.abort();  
						                }  
						                
						            }  
						            mdc.flush();  
						        } catch (IOException e) {  
						            e.printStackTrace();  
						        }  
						    }  
						}).start();  	 
			} 
	      */
	      
   public void writeFile2Cache(String filename,DiskLruCache mDiskLruCache){
		 String Tags="writeFile2Cache";
		 //File file=new File(filename);
		 //FileOutputStream fos = null; 
		 //String key=hashKeyForDisk(filename);
		  final String fn=filename;
		  final DiskLruCache  mdc= mDiskLruCache;
		  
				 new Thread(new Runnable() {  
					    @Override  
					    public void run() {  
					        try {  
					            //String imageUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";  
					            String key = hashKeyForDisk(fn);  
					            DiskLruCache.Editor editor = mdc.edit(key);  
					            if (editor != null) {  
					                OutputStream outputStream = editor.newOutputStream(0);
					                FileInputStream in = new FileInputStream(fn);
					                /*
					                while ((outputStream=in.read())!=-1) {  
					                    editor.commit();  
					                } else {  
					                    editor.abort();  
					                }  
					                */
					            }  
					            mdc.flush();  
					        } catch (IOException e) {  
					            e.printStackTrace();  
					        }  
					    }  
					}).start();  	 
		} 
   
   
   public static void readFileByBytes(String fileName) {
       File file = new File(fileName);
       InputStream in = null;
       try {
           System.out.println("���ֽ�Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���ֽڣ�");
           // һ�ζ�һ���ֽ�
           in = new FileInputStream(file);
           int tempbyte;
           while ((tempbyte = in.read()) != -1) {
               System.out.write(tempbyte);
           }
           in.close();
       } catch (IOException e) {
           e.printStackTrace();
           return;
       }
       try {
           System.out.println("���ֽ�Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�����ֽڣ�");
           // һ�ζ�����ֽ�
           byte[] tempbytes = new byte[100];
           int byteread = 0;
           in = new FileInputStream(fileName);
            //ReadFromFile.showAvailableBytes(in);
           // �������ֽڵ��ֽ������У�bytereadΪһ�ζ�����ֽ���
           while ((byteread = in.read(tempbytes)) != -1) {
               System.out.write(tempbytes, 0, byteread);
           }
       } catch (Exception e1) {
           e1.printStackTrace();
       } finally {
           if (in != null) {
               try {
                   in.close();
               } catch (IOException e1) {
               }
           }
       }
   }

   /**
    * ���ַ�Ϊ��λ��ȡ�ļ��������ڶ��ı������ֵ����͵��ļ�
    */
   public void readFileByChars(String fileName) {
       File file = new File(fileName);
       Reader reader = null;
       try {
           System.out.println("���ַ�Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���ֽڣ�");
           // һ�ζ�һ���ַ�
           reader = new InputStreamReader(new FileInputStream(file));
           int tempchar;
           while ((tempchar = reader.read()) != -1) {
               // ����windows�£�\r\n�������ַ���һ��ʱ����ʾһ�����С�
               // ������������ַ��ֿ���ʾʱ���ỻ�����С�
               // ��ˣ����ε�\r����������\n�����򣬽������ܶ���С�
               if (((char) tempchar) != '\r') {
                   System.out.print((char) tempchar);
               }
           }
           reader.close();
       } catch (Exception e) {
           e.printStackTrace();
       }
       try {
           System.out.println("���ַ�Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�����ֽڣ�");
           // һ�ζ�����ַ�
           char[] tempchars = new char[30];
           int charread = 0;
           reader = new InputStreamReader(new FileInputStream(fileName));
           // �������ַ����ַ������У�charreadΪһ�ζ�ȡ�ַ���
           while ((charread = reader.read(tempchars)) != -1) {
               // ͬ�����ε�\r����ʾ
               if ((charread == tempchars.length)
                       && (tempchars[tempchars.length - 1] != '\r')) {
                   System.out.print(tempchars);
               } else {
                   for (int i = 0; i < charread; i++) {
                       if (tempchars[i] == '\r') {
                           continue;
                       } else {
                           System.out.print(tempchars[i]);
                       }
                   }
               }
           }

       } catch (Exception e1) {
           e1.printStackTrace();
       } finally {
           if (reader != null) {
               try {
                   reader.close();
               } catch (IOException e1) {
               }
           }
       }
   }

   
   
   /**
    * ����Ϊ��λ��ȡ�ļ��������ڶ������еĸ�ʽ���ļ�
    */
   public void readFileByLines(String fileName) {
       File file = new File(fileName);
       BufferedReader reader = null;
       try {
           System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");
           reader = new BufferedReader(new FileReader(file));
           String tempString = null;
           int line = 1;
           // һ�ζ���һ�У�ֱ������nullΪ�ļ�����
           while ((tempString = reader.readLine()) != null) {
               // ��ʾ�к�
               System.out.println("line " + line + ": " + tempString);
               line++;
           }
           reader.close();
       } catch (IOException e) {
           e.printStackTrace();
       } finally {
           if (reader != null) {
               try {
                   reader.close();
               } catch (IOException e1) {
               }
           }
       }
   }

   /**
    * �����ȡ�ļ�����
    */
   public void readFileByRandomAccess(String fileName) {
       RandomAccessFile randomFile = null;
       try {
           System.out.println("�����ȡһ���ļ����ݣ�");
           // ��һ����������ļ�������ֻ����ʽ
           randomFile = new RandomAccessFile(fileName, "r");
           // �ļ����ȣ��ֽ���
           long fileLength = randomFile.length();
           // ���ļ�����ʼλ��
           int beginIndex = (fileLength > 4) ? 4 : 0;
           // �����ļ��Ŀ�ʼλ���Ƶ�beginIndexλ�á�
           randomFile.seek(beginIndex);
           byte[] bytes = new byte[10];
           int byteread = 0;
           // һ�ζ�10���ֽڣ�����ļ����ݲ���10���ֽڣ����ʣ�µ��ֽڡ�
           // ��һ�ζ�ȡ���ֽ�������byteread
           while ((byteread = randomFile.read(bytes)) != -1) {
               System.out.write(bytes, 0, byteread);
           }
       } catch (IOException e) {
           e.printStackTrace();
       } finally {
           if (randomFile != null) {
               try {
                   randomFile.close();
               } catch (IOException e1) {
               }
           }
       }
   }

   /**
    * ��ʾ�������л�ʣ���ֽ���
    */
   private  void showAvailableBytes(InputStream in) {
       try {
           System.out.println("��ǰ�ֽ��������е��ֽ���Ϊ:" + in.available());
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
   
}
