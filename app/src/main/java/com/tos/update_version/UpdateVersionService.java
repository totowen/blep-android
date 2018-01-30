package com.tos.update_version;

import android.app.Service;
import android.content.Intent;
import android.content.pm.IPackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;
import java.lang.reflect.Method;
/**
 * 
 *	下载新版本的服务类
 */
public class UpdateVersionService extends Service {

	//安装文件
	private File updateFile;
	
	private static HttpHandler<File> httpHandler;
	private HttpUtils httpUtils;

	@Override
	public void onCreate() {
		super.onCreate();
		
		httpUtils = new HttpUtils();
		String path = Environment.getExternalStorageDirectory().getPath();
		//建立本地文件夹
		final String fileName = "updatablep.apk";
		File tmpFile = new File(path+"/update");
		if (!tmpFile.exists()) {
			tmpFile.mkdir();
		}
		updateFile = new File(path+"/update/" + fileName);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
//		VersionInfo versionInfo = (VersionInfo) intent.getSerializableExtra("versionInfo");
//		String url = versionInfo.getDownloadUrl();
		Bundle bundle = intent.getExtras();

		String url = bundle.getString("downloadUrl");
		
		PreferenceUtils.setString(UpdateVersionService.this, "apkDownloadurl", url);
		
		downLoadFile(url);
		return super.onStartCommand(intent, flags, startId);
	}

	
	
	public void downLoadFile(String url){
		
		httpHandler = httpUtils.download(url,updateFile.getAbsolutePath(), true, false, new RequestCallBack<File>() {

			@Override
			public void onSuccess(ResponseInfo<File> response) {
				//关闭服务
                stopSelf();
                //自动安装新版本
                Intent installIntent = ApkUtils.getInstallIntent(updateFile);
                startActivity(installIntent);
				//静默安装
				Log.e("so_install", "file: "+updateFile.getAbsolutePath());
//				int so_install = PackageUtils.installSilent(UpdateVersionService.this,updateFile.getAbsolutePath());
				/*try
				{
					Class<?> clazz = Class.forName("android.os.ServiceManager");
					Method method_getService = clazz.getMethod("getService",
							String.class);
					IBinder bind = (IBinder) method_getService.invoke(null, "package");

					IPackageManager iPm = IPackageManager.Stub.asInterface(bind);
					iPm.installPackage(Uri.fromFile(updateFile), null, 2,
							updateFile.getName());
				} catch (Exception e)
				{
					e.printStackTrace();
				}*/
			}
			
			@Override
			public void onFailure(HttpException error, String msg) {

			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {

			}

			@Override
			public void onStart() {

			}
			
		});
	}
	
	
	public static HttpHandler<File> getHandler(){
		return httpHandler;
	}
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	
}
