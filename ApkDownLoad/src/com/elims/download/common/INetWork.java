package com.elims.download.common;

/**
 * 
 * 网络请求的接口类
 * 
 * @author elims
 *
 */
public interface INetWork {
	
	/**
	 * 网络请求成功的回调
	 * @param success
	 */
	void onSuccess(String success);
	
	/**
	 * 网络请求失败的回调
	 * @param fail
	 */
	void onFail(String fail);
}
