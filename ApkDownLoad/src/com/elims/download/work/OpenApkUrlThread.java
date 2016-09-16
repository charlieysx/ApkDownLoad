package com.elims.download.work;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.elims.download.bean.AppInfo;
import com.elims.download.bean.AppUrlInfo;
import com.elims.download.common.INetWork;
import com.elims.download.common.NetWork;
import com.elims.download.common.NetWorkGet;
import com.elims.download.constant.DownLoadConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 获取app信息类（所有需要的信息）
 * @author elims
 *
 */
public class OpenApkUrlThread implements Runnable, INetWork {

	private NetWork netWork;
	private AppInfo appInfo;

	public OpenApkUrlThread(AppInfo appInfo) {
		this.appInfo = appInfo;
		this.netWork = new NetWorkGet(this, this.appInfo.getApp_apkUrl());
	}

	@Override
	public void run() {
		this.netWork.work();
	}

	/**
	 * 从传入的参数中分离出app的各种信息
	 * 
	 * @param result
	 */
	private void analysis(String result) {
		Document html = Jsoup.parse(result);
		// 应用截图
		Elements basicInforL = html.getElementsByClass("imga");
		if (basicInforL.size() == 0) {
			System.err.println(appInfo.getApp_name() + "---信息获取失败");
			return;
		}
		List<String> urls = new ArrayList<>();
		for (Element element : basicInforL) {
			String url = element.select("img").attr("src");
			urls.add(url);
		}
		appInfo.setApp_pictureUrl(urls);

		// 简介
		basicInforL = html.getElementsByAttributeValue("id", "conIntro");
		if (basicInforL.size() == 0) {
			System.err.println(appInfo.getApp_name() + "---信息获取失败");
			return;
		}
		appInfo.setApp_introduction(basicInforL.get(0).html()
				.replaceAll("<br /><br />", "\n").replaceAll("<br />", "\n"));

		// 其他信息
		Elements li = html.getElementsByAttributeValue("class", "basicInforL")
				.get(0).getElementsByTag("li");
		if (li.size() == 0) {
			System.err.println(appInfo.getApp_name() + "---信息获取失败");
			return;
		}
		//分类
		appInfo.setApp_classification(li.get(2).select("a").text());
		//版本
		appInfo.setApp_version(li.get(5).text().replace("版本：", ""));
		//更新时间
		appInfo.setApp_updateTime(li.get(7).text().replace("更新时间：", ""));

		
		//将下载的图片地址跟apk地址另存起来
		List<AppUrlInfo> urlList = new ArrayList<>();
		StringBuffer buffer = new StringBuffer(appInfo.getApp_iconUrl());
		urlList.add(new AppUrlInfo("icon"
				+ buffer.delete(0, buffer.lastIndexOf(".")), appInfo
				.getApp_iconUrl(), appInfo.getApp_name()));
		for (String url : appInfo.getApp_pictureUrl()) {
			buffer = new StringBuffer(url);
			urlList.add(new AppUrlInfo(buffer.delete(0,
					buffer.lastIndexOf("/") + 1).toString(), url, appInfo
					.getApp_name()));
		}
		urlList.add(new AppUrlInfo(appInfo.getApp_name()
				+ appInfo.getApp_version() + ".apk", appInfo
				.getApp_downloadUrl(), appInfo.getApp_name()));

//		System.out.println(appInfo.getApp_downloadUrl());
		createDir(appInfo.getApp_name());
		// System.out.println(appInfo.toString());
		setAppInfo();
		// 信息获取成功，开始下载
		for (AppUrlInfo info : urlList) {
			new DownLoadThread(info).run();
		}

	}

	/**
	 * 将app的部分信息转为json格式保存在txt文件中
	 */
	private void setAppInfo() {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String info = gson.toJson(appInfo);
//		System.out.println(info);

		File file = new File(DownLoadConstant.DOWNLOAD_PATH
				+ appInfo.getApp_name() + "/简介.txt");
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(file, true);
			bw = new BufferedWriter(fw);
			bw.write(info);
			bw.close();
			fw.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
				if (fw != null) {
					fw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 创建路径
	 * @param pathName 传过来的参数为 xxx/xxx/xxx
	 */
	private void createDir(String pathName) {

		String path = DownLoadConstant.DOWNLOAD_PATH + pathName + "/";
		path = fixDir(path);
		File pathFile = new File(path);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
	}

	/**
	 * 将传过来的字符串修改为符合文件夹以及文件的命名格式
	 * @param dirName
	 * @return
	 */
	private String fixDir(String dirName) {
		dirName = dirName.replace(":", "：");
		dirName = dirName.replace("\\", "");
		dirName = dirName.replace("*", "");
		dirName = dirName.replace("?", "");
		dirName = dirName.replace("'", "");
		dirName = dirName.replace("<", "");
		dirName = dirName.replace(">", "");
		dirName = dirName.replace("|", "");
		return dirName;
	}

	@Override
	public void onSuccess(String success) {
		analysis(success);
	}

	@Override
	public void onFail(String fail) {
		System.err.println(appInfo.getApp_name() + "---信息获取失败");
	}

}
