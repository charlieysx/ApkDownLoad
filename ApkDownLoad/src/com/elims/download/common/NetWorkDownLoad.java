package com.elims.download.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import com.elims.download.bean.AppUrlInfo;
import com.elims.download.constant.DownLoadConstant;

/**
 * 
 * 文件下载类
 * 
 * @author elims
 * 
 */
public class NetWorkDownLoad extends NetWork {

	private AppUrlInfo appUrlInfo;

	public NetWorkDownLoad(INetWork iNetWork, AppUrlInfo appUrlInfo) {
		super(iNetWork, appUrlInfo.getUrl());
		this.appUrlInfo = appUrlInfo;
	}

	@Override
	public void work() {

		// 创建文件夹及文件
		File file = new File(DownLoadConstant.DOWNLOAD_PATH
				+ appUrlInfo.getAppName() + "/" + appUrlInfo.getName());

		HttpURLConnection conn = null;
		InputStream is = null;
		RandomAccessFile raf = null;
		try {
			// 判断是否开始下载apk
			if (appUrlInfo.getUrl().matches(".*down_.*do")) {
				System.out.println(appUrlInfo.getName() + "---下载开始");
			}
			URL url = new URL(appUrlInfo.getUrl());
			conn = (HttpURLConnection) url.openConnection();
			// 设置连接超时时间
			conn.setConnectTimeout(3000);
			// 设置请求方法
			conn.setRequestMethod("GET");
			// 请求相应成功
			if (conn.getResponseCode() == 200) {

				// 读取数据
				is = conn.getInputStream();
				byte[] buffer = new byte[1024];
				int len = -1;
				int lenght = 0;
				int aLen = conn.getContentLength();
				raf = new RandomAccessFile(file, "rwd");

				while ((len = is.read(buffer)) != -1) {
					// 将数据写入文件
					raf.write(buffer, 0, len);

					// 判断是否开始下载apk
					if (appUrlInfo.getUrl().matches(".*down_.*do")) {
						lenght += len;
						System.out.println(appUrlInfo.getName() + "---下载中..."
								+ lenght / 1024f / 1024f + "M---" + aLen
								/ 1024f / 1024f + "M");
					}
				}
				iNetWork.onSuccess("download success");
			} else {
				iNetWork.onFail("network fail");
			}

		} catch (FileNotFoundException e) {
			iNetWork.onFail("network fail");
		} catch (IOException e) {
			iNetWork.onFail("network fail");
		} finally {
			try {
				if (raf != null) {
					raf.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

}
