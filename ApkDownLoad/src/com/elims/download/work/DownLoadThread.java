package com.elims.download.work;

import com.elims.download.bean.AppUrlInfo;
import com.elims.download.common.INetWork;
import com.elims.download.common.NetWork;
import com.elims.download.common.NetWorkDownLoad;
import com.elims.download.constant.DownLoadConstant;

/**
 * 
 * 下载线程类
 * @author elims
 *
 */
public class DownLoadThread implements INetWork {

	private NetWork netWork;
	private AppUrlInfo appUrlInfo;
	
	public DownLoadThread(AppUrlInfo appUrlInfo){
		this.appUrlInfo = appUrlInfo;
		this.netWork = new NetWorkDownLoad(this, this.appUrlInfo);
	}
	
	public void run() {
		this.netWork.work();
	}

	@Override
	public void onSuccess(String success) {
		if(appUrlInfo.getUrl().matches(".*down_.*do")){
			System.out.println(appUrlInfo.getName() + "---下载完成");
		}
	}

	@Override
	public void onFail(String fail) {
		System.err.println(appUrlInfo.getName() + "---下载失败");
	}
}
