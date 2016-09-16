package com.elims.download.bean;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * 
 * app信息
 * 使用注解，方便在生成json时只取需要的部分信息
 * @author elims
 *
 */
public class AppInfo {
	// 名字
	@Expose(serialize = true, deserialize = false) 
	private String app_name;
	// apk详细信息地址
	@Expose(serialize = false) 
	private String app_apkUrl;
	// apk下载地址
	@Expose(serialize = false) 
	private String app_downloadUrl;
	// icon地址
	@Expose(serialize = false) 
	private String app_iconUrl;
	// 应用分类
	@Expose(serialize = true, deserialize = false) 
	private String app_classification;
	// 版本
	@Expose(serialize = true, deserialize = false) 
	private String app_version;
	// 更新时间
	@Expose(serialize = true, deserialize = false) 
	private String app_updateTime;
	// 应用简介
	@Expose(serialize = true, deserialize = false) 
	private String app_introduction;
	// 应用截图地址
	@Expose(serialize = false) 
	private List<String> app_pictureUrl;

	public AppInfo(String app_name, String app_apkUrl, String app_iconUrl, String app_downloadUrl) {
		super();
		this.app_name = app_name;
		this.app_apkUrl = app_apkUrl;
		this.app_iconUrl = app_iconUrl;
		this.app_downloadUrl = app_downloadUrl;
		this.app_classification = "";
		this.app_version = "";
		this.app_updateTime = "";
		this.app_introduction = "";
		this.app_pictureUrl = new ArrayList<String>();
	}

	public String getApp_name() {
		return app_name;
	}

	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}

	public String getApp_apkUrl() {
		return app_apkUrl;
	}

	public void setApp_apkUrl(String app_apkUrl) {
		this.app_apkUrl = app_apkUrl;
	}

	public String getApp_iconUrl() {
		return app_iconUrl;
	}

	public void setApp_iconUrl(String app_iconUrl) {
		this.app_iconUrl = app_iconUrl;
	}

	public String getApp_classification() {
		return app_classification;
	}

	public void setApp_classification(String app_classification) {
		this.app_classification = app_classification;
	}

	public String getApp_version() {
		return app_version;
	}

	public void setApp_version(String app_version) {
		this.app_version = app_version;
	}

	public String getApp_updateTime() {
		return app_updateTime;
	}

	public void setApp_updateTime(String app_updateTime) {
		this.app_updateTime = app_updateTime;
	}

	public String getApp_introduction() {
		return app_introduction;
	}

	public void setApp_introduction(String app_introduction) {
		this.app_introduction = app_introduction;
	}

	public List<String> getApp_pictureUrl() {
		return app_pictureUrl;
	}

	public void setApp_pictureUrl(List<String> app_pictureUrl) {
		this.app_pictureUrl = app_pictureUrl;
	}
	
	public String getApp_downloadUrl() {
		return app_downloadUrl;
	}

	public void setApp_downloadUrl(String app_downloadUrl) {
		this.app_downloadUrl = app_downloadUrl;
	}

	@Override
	public String toString() {
		String result = this.app_name + "\n" + this.app_apkUrl + "\n" + this.app_iconUrl
				+ "\n" + this.app_classification + "\n" + this.app_updateTime
				+ "\n" + this.app_version + "\n" + this.app_downloadUrl + "\n"
				+ this.app_introduction + "\n";
		
		for(String url : app_pictureUrl){
			result += url + "\n";
		}
		
		return result;
	}

}
