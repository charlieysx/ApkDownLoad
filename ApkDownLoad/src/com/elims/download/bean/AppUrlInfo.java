package com.elims.download.bean;

/**
 * 
 * 网址信息
 * @author elims
 *
 */
public class AppUrlInfo {
	//下载的文件的名称
	private String name;
	//下载的文件的网址
	private String url;
	//属于哪个app的
	private String appName;
	
	public AppUrlInfo(String name, String url, String appName) {
		super();
		this.name = name;
		this.url = url;
		this.appName = appName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	
}
