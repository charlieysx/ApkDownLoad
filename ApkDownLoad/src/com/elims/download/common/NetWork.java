package com.elims.download.common;

/**
 * 
 * 网络请求类
 * @author elims
 *
 */
public abstract class NetWork {
	//请求的接口
	protected INetWork iNetWork;
	//请求的网络地址
	protected String downloadUrl;
	
	public NetWork(INetWork iNetWork, String downloadUrl){
		this.iNetWork = iNetWork;
		this.downloadUrl = downloadUrl;
	}
	
	/**
	 * 请求方法(可以是下载也可以是访问网页)
	 */
	public abstract void work();
	
}
