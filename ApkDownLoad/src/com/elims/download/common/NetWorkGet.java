package com.elims.download.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 * 网络请求类
 * @author elims
 *
 */
public class NetWorkGet extends NetWork {

	public NetWorkGet(INetWork iNetWork, String downloadUrl) {
		super(iNetWork, downloadUrl);
	}

	@Override
	public void work() {
		
		HttpURLConnection conn = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			URL url = new URL(downloadUrl);
            conn = (HttpURLConnection) url.openConnection();
            //设置连接超时时间
            conn.setConnectTimeout(3000);
            //设置请求方法
            conn.setRequestMethod("GET");
            //请求相应成功
            if(conn.getResponseCode() == 200){
            	isr = new InputStreamReader(conn.getInputStream(), "gbk");
            	br = new BufferedReader(isr);
            
            	StringBuffer buffer = new StringBuffer();
            	String line;
            	while((line = br.readLine()) != null){
            		buffer.append(line);
            	}
            
            	iNetWork.onSuccess(buffer.toString());
            } else {
    			iNetWork.onFail("network fail");
            }
			
		} catch (Exception e) {
			iNetWork.onFail("network fail");
		} finally {
			try {
				if(br != null){
					br.close();
				}
				if(isr != null){
					isr.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(conn != null){
				conn.disconnect();
			}
		}
		
	}

}
