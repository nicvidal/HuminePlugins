package com.aypi.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Download {

   public Download(String url, File file) {
	   
	   BufferedInputStream bufferedIS = null;
	    FileOutputStream fileOS = null;
	    
	    try {
	      URL urlObj = new URL(url);
	      bufferedIS = new BufferedInputStream(urlObj.openStream());
	      fileOS = new FileOutputStream(file);

	      int data = bufferedIS.read();
	      while(data != -1){
	        fileOS.write(data);
	        data = bufferedIS.read();
	      }
	    } catch (MalformedURLException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }finally{
	      try {
	        if(fileOS != null){
	          fileOS.close();
	        }
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	      try {
	        if(bufferedIS != null){
	          bufferedIS.close();
	        }
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	      System.out.println("Download finish");
	    }
	  }	
   
   
}
		


