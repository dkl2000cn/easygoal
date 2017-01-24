package com.easygoal.achieve;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;  
  
public class ExportListToExcel {  

    private String mTb;
    private Cursor cur; 
    private Context mContext;
    private String[] titlename;
    private String[] columnname;
  
    public ExportListToExcel(Context context,String tablename,Cursor cursor,String[] titlename,String[] columnname) {  
       // mDb = db;  
       // mDestXmlFilename = destXml;
    	mContext=context;
        mTb=tablename;
        cur=cursor;
       // cur2=cursor2;
        this.titlename=titlename;
        this.columnname=columnname;
    }  
  
	
    public File writeExcel(String tableName) {  
        WritableWorkbook wwb = null;  
        String fileName; 
        //File sdFileDir=Environment.getExternalStorageDirectory();
        //fileName = sdFileDir+"/DCIM/" + tableName + ".xls";  
        fileName = FileUtils.getDiskCacheDirPath(mContext)+ tableName + ".xls"; 
        File outexcelfile=new File(fileName);
       // File outputfile = new File(fileName);
        int r = 0;  
  
       // String sql = "select * from " + tableName;  
       // Cursor cur = mDb.rawQuery(sql, new String[0]);  
        int numcols = titlename.length;  
        int numrows = cur.getCount();  
        // Log.i("row", numrows + "");  
        // Log.i("col", numcols + "");  
  
        String records[][] = new String[numrows + 1][numcols];// ��Ŵ𰸣���һ�б�����  
  
        if (cur.moveToFirst()) {  
            while (cur.getPosition() < cur.getCount()) {  
                for (int c = 0; c < numcols; c++) {  
                    if (r == 0) {  
                        records[r][c] = titlename[c];  
                        records[r + 1][c] = cur.getString(cur.getColumnIndex(columnname[c]));  
                    } else {  
                        records[r + 1][c] = cur.getString(cur.getColumnIndex(columnname[c]));  
                    }  
                //  Log.i("value" + r + " " + c, records[r][c]);  
                }  
                cur.moveToNext();  
                r++;  
            }  
  
            cur.close();  
        }  
        
        try {  
            // ����Ҫʹ��Workbook��Ĺ�����������һ����д��Ĺ�����(Workbook)����  
            
        	wwb = Workbook.createWorkbook(outexcelfile); 
            
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
       
        if (wwb != null) {  
            // ����һ����д��Ĺ�����  
            // Workbook��createSheet������������������һ���ǹ���������ƣ��ڶ����ǹ������ڹ������е�λ��  
            WritableSheet ws = wwb.createSheet("�����嵥", 0);  
  
            // ���濪ʼ��ӵ�Ԫ��  
            for (int i = 0; i < numrows + 1; i++) {  
                for (int j = 0; j < numcols; j++) {  
                    // ������Ҫע����ǣ���Excel�У���һ��������ʾ�У��ڶ�����ʾ��  
                    Label labelC = new Label(j, i, records[i][j]);  
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
            } 
        }
        
      
   
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
           
        return outexcelfile;
    }  
} 