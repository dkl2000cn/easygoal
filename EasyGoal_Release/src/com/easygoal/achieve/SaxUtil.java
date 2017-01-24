package com.easygoal.achieve;

import java.text.SimpleDateFormat;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;   
    

/**  
 * ����xml����ʱ�ļ�����  
 *   
 * ʵ�ַ�ʽ�кܶ࣬����ʵ�ֽӿڣ�ContentHandler��DTDHandler�� EntityResolver �� ErrorHandler   
 * �����ǳ��õļ̳У�DefaultHandler   
 */   
public class SaxUtil extends DefaultHandler {   
    
    private UpdateInfo updateinfo;   
    private String content;
   
    @Override   
    public void characters(char[] ch, int start, int length)   
            throws SAXException {   
        content = new String(ch, start, length);   
    }   
        
    //���������ı���ʼʱ����   
    @Override   
    public void startDocument() throws SAXException {   
        super.startDocument();   
    }   
        
    //���������ı�����ʱ����   
    @Override   
    public void endDocument() throws SAXException {   
        super.endDocument();   
    }   
        
    //��������Ԫ�ؿ�ʼʱ����   
    @Override   
    public void startElement(String uri, String localName, String name,   
            Attributes attributes) throws SAXException    
    {   
        if("version".equals(name))   
        {   
        	updateinfo = new UpdateInfo();   
        }   
        
    }   
        
    //��������Ԫ�ؽ���ʱ����   
    @Override   
    public void endElement(String uri, String localName, String name)   
            throws SAXException    
    {   
        if("version".equals(name))   
        {   
        	updateinfo.setVersion(content);   
        }   
    
            
    }   
    
    public UpdateInfo getUpdateInfo(){   
        return updateinfo;   
    }   
        
}