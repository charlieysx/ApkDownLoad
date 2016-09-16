com.elims.download包下的Main.java为程序入口
其中page为下载的页面
例如，page为1，就是下载http://app.cnmo.com/android/software/l2.html的第一页中的app


其他类不用管，修改Main.java中的page就可以

如果想一次性下载多页，可以给new SearchApkUrl().search(page);加一层for循环，不过不建议这样，
因为每一页的app就已经很多了，同时下载太多一方面网速不好，另一方面怕那边服务器压力大导致下载失败等
可以下载一页完成后再修改page为2下载第二页...


我开启的线程个数为4，也就是说同时下载的apk个数为4


下载的文件放在download文件夹中