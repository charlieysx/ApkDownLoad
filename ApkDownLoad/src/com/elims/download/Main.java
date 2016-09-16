package com.elims.download;

import com.elims.download.work.SearchApkUrl;

public class Main {

	public static void main(String[] args) {
		
		//下载的页面
		int page = 1;
		
		new SearchApkUrl().search(page);
	}

}
