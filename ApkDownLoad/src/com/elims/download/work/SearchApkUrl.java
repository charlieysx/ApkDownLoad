package com.elims.download.work;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.elims.download.bean.AppInfo;
import com.elims.download.common.INetWork;
import com.elims.download.common.NetWork;
import com.elims.download.common.NetWorkGet;
import com.elims.download.constant.DownLoadConstant;

/**
 * 获取app信息类（部分信息）
 * 
 * @author elims
 * 
 */
public class SearchApkUrl implements INetWork {

	private NetWork netWork = null;
	private List<AppInfo> appInfos;

	public SearchApkUrl() {
	}

	public void search(int page) {
		if (netWork == null) {
			netWork = new NetWorkGet(this, String.format(
					DownLoadConstant.DOWNLOAD_URL, page));
			netWork.work();
		}
	}

	private void analysis(String result) {
		Document html = Jsoup.parse(result);
		Elements modDlas = html.getElementsByClass("modDla");
		if (modDlas.size() == 0) {
			System.err.println("获取apk信息失败");
			return;
		}
		for (Element element : modDlas) {
			try {
				// 打开app页面的地址
				String apkUrl = element.children().get(0).select("a")
						.attr("href");
				// icon下载地址
				String iconUrl = element.children().get(0).select("img")
						.attr("src");
				// app名称
				String name = element.children().get(1).text();

				StringBuffer buffer = new StringBuffer(apkUrl);
				buffer.delete(buffer.lastIndexOf("/"), buffer.length());
				buffer.delete(0, buffer.lastIndexOf("/") + 1);
				// app下载地址
				String downloadurl = String.format(
						DownLoadConstant.DOWNLOAD_APK_URL, buffer.toString());

				AppInfo appInfo = new AppInfo(name, apkUrl, iconUrl,
						downloadurl);
				appInfos.add(appInfo);
			} catch (Exception e) {
				System.err
						.println("------------------------------------------");
				System.err.println(element.html());
				System.err.println("-----------------信息获取失败--------------");
			}
		}
		// 开启线程池
		ExecutorService pool = Executors
				.newFixedThreadPool(DownLoadConstant.DOWNLOAD_THREAD_NUM);
		// 多线程获取app信息
		for (AppInfo info : appInfos) {
			pool.submit(new OpenApkUrlThread(info));
		}
		pool.shutdown();
	}

	@Override
	public void onSuccess(String success) {
		appInfos = new ArrayList<AppInfo>();
		analysis(success);
		// System.out.println(success);
	}

	@Override
	public void onFail(String fail) {
		System.err.println(fail);
	}

}
