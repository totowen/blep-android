package com.tos.applock.utils;

import java.util.ArrayList;
import java.util.List;

import com.tos.applock.domain.AppInfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

/**
 * 获取
 */

public class AppInfoProvider {
	public static List<AppInfo> getAppInfos(Context context){
		List<AppInfo> appInfos=new ArrayList<AppInfo>();
		//获取已安装的应用程序信息
		PackageManager manager=context.getPackageManager();
		//返回所有已安装应用程序的安装包信息
		 List<PackageInfo> infos=manager.getInstalledPackages(0);
		 for(PackageInfo info:infos){
			 String packName=info.packageName;//包名
			 if(!packName.equals(context.getPackageName())){//包名不等于当前应用的包名
			 String name=(String) info.applicationInfo.loadLabel(manager);//给回当前应用的名称
			 Drawable appIcon=info.applicationInfo.loadIcon(manager);//给回应用当前的图标
			 boolean inRom;//存储地址
			 boolean userApp;//用户app
			 int flag=info.applicationInfo.flags;
			 if((flag&ApplicationInfo.FLAG_SYSTEM)==0){
				 userApp=true;
			 }else{
				 userApp=false;
			 }
			 if((flag&ApplicationInfo.FLAG_EXTERNAL_STORAGE)==0){
				 inRom=true;
			 }else{
				 inRom=false;
			 }
			 
			 AppInfo appInfo=new AppInfo(name, packName, appIcon, inRom, userApp);
			 
			 appInfos.add(appInfo);}
		 }
		 return appInfos;
	}


	public static boolean getAppInfo(Context context,String pack_Name){
		boolean temp = false;
		//获取已安装的应用程序信息
		PackageManager manager=context.getPackageManager();
		//返回所有已安装应用程序的安装包信息
		List<PackageInfo> infos=manager.getInstalledPackages(0);
		for(PackageInfo info:infos){
			String packName=info.packageName;//包名
			if(packName.equals(pack_Name)){//包名等于当前应用的包名
				int flag=info.applicationInfo.flags;
				if((flag&ApplicationInfo.FLAG_SYSTEM)==0){
					temp=true;
				}
			}
		}
		return temp;
	}

}
