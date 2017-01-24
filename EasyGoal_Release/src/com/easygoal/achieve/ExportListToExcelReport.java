package com.easygoal.achieve;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.litepal.crud.DataSupport;

import android.database.Cursor;
import android.os.Environment;
import android.util.Log;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;  
  
public class ExportListToExcelReport {  

    private String mTb;
    private Cursor cur; 
    private Cursor cur2;
    private String[] titlename;
    private String[] columnname;
    WritableWorkbook wwb ;  
    public ExportListToExcelReport(String tablename,Cursor cursor,String[] titlename,String[] columnname) {  
       // mDb = db;  
       // mDestXmlFilename = destXml; 
        mTb=tablename;
        cur=cursor;
       // cur2=cursor2;
        this.titlename=titlename;
        this.columnname=columnname;
        wwb=null;
    }  
  
    public void exportmemo(WritableWorkbook wwb) { 
    
        
     	 final String[] recorditemlist_m={
     			
   	    	 
   	    	  //TaskData.TdDB.RECORD_TASKNAME,
   			  
   			  "username",
   			  "name",
   			  "content",
   			  "createdtime",
   			  "deadlinetime"  		
   	       };
   	    
     	 final String[] titlenamelist_m={
   			  "������","������","��������","����ʱ��","�������"  
   	     };
   	  
           
           int r_m = 0;  
           //String seekSN = cur.getString(cur.getColumnIndex(TaskData.TdDB.TASK_SN));
           //writeExcel(mTb);
          
           Cursor cur_m = DataSupport.findBySQL("select * from memo");
           cur_m.moveToFirst();  
           Log.d("cur_m", ""+cur_m.getCount()+" col "+cur_m.getColumnCount());
            int numcols_m = recorditemlist_m.length;  
            int numrows_m = cur_m.getCount();  
            
            int colWidth_m[]={1,1,1,1,1};
            String records_m[][] = new String[numrows_m + 1][numcols_m];// ��Ŵ𰸣���һ�б�����  
            
            do{  
               /*
               	 String tasksn= cur2.getString(cur2.getColumnIndex(TaskData.TdDB.TASK_SN));
                    
                    Cursor cur_t=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain +" where "+
                      	    TaskData.TdDB.TASK_SN+"='"+tasksn+"';", null);
                    //Log.d("task_sn", tasksn+"count:"+cur_t.getCount());
               */
                    for (int c_m = 0; c_m < numcols_m; c_m++) {  
                        if (r_m == 0) {  
                            records_m[r_m][c_m] = titlenamelist_m[c_m];  
                            records_m[r_m + 1][c_m] = cur_m.getString(cur_m.getColumnIndex(recorditemlist_m[c_m])); 
                            //Log.d("pos", "row"+r_m+" col "+c_m);
                            int cw = titlenamelist_m[c_m].length()+getChineseNum(titlenamelist_m[c_m]);
   	                        if (cw>colWidth_m[c_m]){
   	                        	colWidth_m[c_m]=cw+1;
   	                        }
                        } else {  
                       	    String value=cur_m.getString(cur_m.getColumnIndex(recorditemlist_m[c_m]));          
   	                        records_m[r_m + 1][c_m] = value;           
   	                        //Log.d("pos", "row"+r_m+" col "+c_m+" value:"+value);
                       	   //records_m[r_m + 1][c_m] = cur_m.getString(cur_m.getColumnIndex(recorditemlist_m[c_m]));    
   	                       if (value!=null){
   	                          int cw = value.length()+getChineseNum(value)+2;
   	                          if (cw>colWidth_m[c_m]){
   	                        	colWidth_m[c_m]=cw;
   	                          }
   	                       }  
                        }  
   	                       
                    //  Log.i("value" + r + " " + c, records[r][c]);  
                  }  
                     
                    r_m++;  
                 
                //cur_m.close();  
            } while (cur_m.moveToNext()); 
          
            if (wwb != null) {  
                // ����һ����д��Ĺ�����  
                // Workbook��createSheet������������������һ���ǹ���������ƣ��ڶ����ǹ������ڹ������е�λ��  
                WritableSheet ws = wwb.createSheet("����¼", 2);  
      
                // ���濪ʼ��ӵ�Ԫ��  
                for (int i = 0; i < numrows_m + 1; i++) {  
                    for (int j = 0; j < numcols_m; j++) {  
                        // ������Ҫע����ǣ���Excel�У���һ��������ʾ�У��ڶ�����ʾ��  
                        Label labelC = new Label(j, i, records_m[i][j]);  
                //      Log.i("Newvalue" + i + " " + j, records[i][j]);  
                        try {  
                            // �����ɵĵ�Ԫ����ӵ���������  
                            ws.addCell(labelC);  
                            ws.setColumnView(j, colWidth_m[j]);
                          
                        } catch (RowsExceededException e) {  
                            e.printStackTrace();  
                        } catch (WriteException e) {  
                            e.printStackTrace();  
                        }  
      
                    }  
                }
                Log.i("ws memo","rows:"+ws.getRows()+"cols:"+ws.getColumns());    
            }
    }
    
    
    public void exportRecord(WritableWorkbook wwb){
    	
    	 final String[] recorditemlist2={
    	  			
    	    	 
   	    	  //TaskData.TdDB.RECORD_TASKNAME,
   			  
   			  TaskData.TdDB.TASK_USER,
   			  TaskData.TdDB.TASK_NAME,
   			  TaskData.TdDB.TASK_DEADLINE,
   			  TaskData.TdDB.TASK_PROGRESS,
   			  TaskData.TdDB.RECORD_TASKID_NO,
   			  TaskData.TdDB.RECORD_COMMENTS,
   			  //TaskData.TdDB.TASK_SEQUENCENO,
   			  //TaskData.TdDB.TASK_STARTEDTIME,
   			  //TaskData.TdDB.TASK_DEADLINE,
   			  TaskData.TdDB.RECORD_WEIGHT,
   			  TaskData.TdDB.RECORD_DEADLINE,
   			  TaskData.TdDB.RECORD_PROGRESS,
   			  TaskData.TdDB.RECORD_DONE,
   			  //TaskData.TdDB.RECORD_MODIFIED
   			  //TaskData.TdDB.TASK_STATUS,
   			  //TaskData.TdDB.TASK_FINISHED,
   			  //TaskData.TdDB.TASK_DELAYED    		
   	       };
   	    
   	   final String[] titlenamelist2={
   			  "������","������","��������","�������","��Ŀ�����","��Ŀ��/����","��Ŀ��/����Ȩ��%","��Ŀ��/��������","�������%","���״̬"	  
   	     };
   	  
           int r2 = 0;  
           //String seekSN = cur.getString(cur.getColumnIndex(TaskData.TdDB.TASK_SN));
           //writeExcel(mTb);
           String a = TaskData.TdDB.TABLE_NAME_TaskMain;
           String b = TaskData.TdDB.TABLE_NAME_TaskRecord;
           cur2=TaskData.db_TdDB.rawQuery("select * from "+
           		TaskData.TdDB.TABLE_NAME_TaskMain +" as a ,"+
           		TaskData.TdDB.TABLE_NAME_TaskRecord+" as b "+
           		" where "+"a._sn=b._sn",null);
          if (cur2.getCount()>0){
        	  cur2.moveToFirst();  
        	  Log.d("cur2", ""+cur2.getCount()+" col "+cur2.getColumnCount());
        	  // String sql = "select * from " + tableName;  
        	  // Cursor cur = mDb.rawQuery(sql, new String[0]);  
        	  int numcols2 = recorditemlist2.length;  
        	  int numrows2 = cur2.getCount();  
            // Log.i("row", numrows + "");  
            // Log.i("col", numcols + "");  
      
        	  int colWidth[]={1,1,1,1,1,1,1,1,1,1};
        	  String records2[][] = new String[numrows2 + 1][numcols2];// ��Ŵ𰸣���һ�б�����  
            
        	  do{  
               /*
               	 String tasksn= cur2.getString(cur2.getColumnIndex(TaskData.TdDB.TASK_SN));
                    
                    Cursor cur_t=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain +" where "+
                      	    TaskData.TdDB.TASK_SN+"='"+tasksn+"';", null);
                    //Log.d("task_sn", tasksn+"count:"+cur_t.getCount());
               */
                    for (int c2 = 0; c2 < numcols2; c2++) {  
                        if (r2 == 0) {  
                            records2[r2][c2] = titlenamelist2[c2];  
                            records2[r2 + 1][c2] = cur2.getString(cur2.getColumnIndex(recorditemlist2[c2])); 
                            //Log.d("pos", "row"+r2+" col "+c2);
                            int cw = titlenamelist2[c2].length()+getChineseNum(titlenamelist2[c2]);
   	                        if (cw>colWidth[c2]){
   	                        	colWidth[c2]=cw+1;
   	                        }
                        } else {  
                       	 String value=cur2.getString(cur2.getColumnIndex(recorditemlist2[c2]));          
   	                        records2[r2 + 1][c2] = value;           
   	                        //Log.d("pos", "row"+r2+" col "+c2+" value:"+value);
                       	   //records2[r2 + 1][c2] = cur2.getString(cur2.getColumnIndex(recorditemlist2[c2]));    
   	                        int cw = value.length()+getChineseNum(value)+2;
   	                        if (cw>colWidth[c2]){
   	                        	colWidth[c2]=cw;
   	                        }
                        }  
                    //  Log.i("value" + r + " " + c, records[r][c]);  
                  }  
                     
                    r2++;  
                 
                //cur2.close();  
        	  } while (cur2.moveToNext()); 
           
            if (wwb != null) {  
                // ����һ����д��Ĺ�����  
                // Workbook��createSheet������������������һ���ǹ���������ƣ��ڶ����ǹ������ڹ������е�λ��  
                WritableSheet ws = wwb.createSheet("�������", 1);  
      
                // ���濪ʼ��ӵ�Ԫ��  
                for (int i = 0; i < numrows2 + 1; i++) {  
                    for (int j = 0; j < numcols2; j++) {  
                        // ������Ҫע����ǣ���Excel�У���һ��������ʾ�У��ڶ�����ʾ��  
                        Label labelC = new Label(j, i, records2[i][j]);  
                //      Log.i("Newvalue" + i + " " + j, records[i][j]);  
                        try {  
                            // �����ɵĵ�Ԫ����ӵ���������  
                            ws.addCell(labelC);  
                            ws.setColumnView(j, colWidth[j]);
                            
                        } catch (RowsExceededException e) {  
                            e.printStackTrace();  
                        } catch (WriteException e) {  
                            e.printStackTrace();  
                        }  
      
                    }  
                }  
                Log.i("ws record","rows:"+ws.getRows()+"cols:"+ws.getColumns());  
            }  
          }
    }
   
    
    int getChineseNum(String context){    ///ͳ��context���Ǻ��ֵĸ���
        int lenOfChinese=0;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");    //���ֵ�Unicode���뷶Χ
        Matcher m = p.matcher(context);
        while(m.find()){
            lenOfChinese++;
        }
        return lenOfChinese;
    }
   

	public void exportTask(WritableWorkbook wwb) {  
		
        	int r = 0;  
        	int numcols = titlename.length;  
        	int numrows = cur.getCount();  
        
        	int taskcolWidth[]={1,1,1,1,1,1,1,1,1,1};
        	String records[][] = new String[numrows + 1][numcols];// ��Ŵ𰸣���һ�б�����  
        	Log.d("cur", ""+cur.getCount()+" col "+cur.getColumnCount());
        	
            if (cur.getCount()>0){
            	cur.moveToFirst();	

             
            	do{ 
                     for (int c = 0; c < numcols; c++) {  
                         if (r == 0) {  
                             records[r][c] = titlename[c];  
                             records[r + 1][c] = cur.getString(cur.getColumnIndex(columnname[c])); 
                             int cw =  titlename[c].length()+getChineseNum( titlename[c]);
                             if (cw>taskcolWidth[c]){
                             	taskcolWidth[c]=cw+1;
                             }
                         } else {  
                         	String valuestr=cur.getString(cur.getColumnIndex(columnname[c]));
                             records[r + 1][c] = valuestr; 
                             int cw =  valuestr.length()+getChineseNum(valuestr)+2;
                             if (cw>taskcolWidth[c]){
                             	taskcolWidth[c]=cw;
                             }
                         }  
                     //  Log.i("value" + r + " " + c, records[r][c]);  
                     }   
                     r++;  
                 }while (cur.moveToNext());
       
            	if (wwb != null) {  
                 // ����һ����д��Ĺ�����  
                 // Workbook��createSheet������������������һ���ǹ���������ƣ��ڶ����ǹ������ڹ������е�λ��  
            		WritableSheet ws = wwb.createSheet("������ϸ", 0);  
       
                 // ���濪ʼ��ӵ�Ԫ��  
                 for (int i = 0; i < numrows + 1; i++) {  
                     for (int j = 0; j < numcols; j++) {  
                         // ������Ҫע����ǣ���Excel�У���һ��������ʾ�У��ڶ�����ʾ��  
                         Label labelC = new Label(j, i, records[i][j]);
                         ws.setColumnView(j, taskcolWidth[j]);
                 //      Log.i("Newvalue" + i + " " + j, records[i][j]);  
                         try {  
                             // �����ɵĵ�Ԫ����ӵ���������  
                             ws.addCell(labelC);  
                         } catch (RowsExceededException e) {  
                             e.printStackTrace();  
                         } catch (WriteException e) {  
                             e.printStackTrace();  
                         }  
       
                     } 
                     Log.i("ws task","rows:"+ws.getRows()+"cols:"+ws.getColumns());   
                 }
               }  
             }   
    } 
	
	
    public File writeExcel(String tableName) {  
        
        String fileName; 
        File sdFileDir=Environment.getExternalStorageDirectory();
        fileName = sdFileDir+"/DCIM/" + tableName + ".xls";  
        File outexcelfile=new File(fileName);
       // File outputfile = new File(fileName);
        
        try {  
            // ����Ҫʹ��Workbook��Ĺ�����������һ����д��Ĺ�����(Workbook)����  
            
        	wwb = Workbook.createWorkbook(outexcelfile); 
            
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        
        exportTask(wwb);
        exportRecord(wwb);
        exportmemo(wwb);
        
        if (wwb != null) {
             try {  
                 // ���ڴ���д���ļ���  
                 wwb.write();  
                 // �ر���Դ���ͷ��ڴ�  
                 wwb.close();  
             } catch (IOException e) {  
                 e.printStackTrace();  
             } catch (WriteException e) {  
                 e.printStackTrace();  
             }    
        }
        Log.i("outfile",""+outexcelfile.exists());   
        return outexcelfile;
    }  
} 