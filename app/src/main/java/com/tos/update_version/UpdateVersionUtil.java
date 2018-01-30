package com.tos.update_version;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.tos.Application.MyApplication;

import java.io.File;
import java.lang.reflect.Method;

/**
 * 版本更新的工具类
 */
public class UpdateVersionUtil {

    /**
     * 接口回调
     *
     * @author wenjie
     */
    public interface UpdateListener {
        void onUpdateReturned(int updateStatus, VersionInfo versionInfo);
    }

    public UpdateListener updateListener;

    public void setUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    /**
     * 网络测试 检测版本
     *
     * @param context 上下文
     */
    public static void checkVersion(final MyApplication application, final Context context, final UpdateListener updateListener) {
        HttpRequest.get(application.getUrl_update(), new HttpRequest.RequestCallBackListener() {

            @Override
            public void onSuccess(String resultData) {
                try {
//					JSONObject jsonObject = JsonUtil.stringToJson(resultData);
//					JSONArray array = jsonObject.getJSONArray("data");
//					VersionInfo mVersionInfo = JsonUtil.jsonToBean(array.getJSONObject(0).toString(), VersionInfo.class);
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(resultData);
                    Log.e("jsonrequest", "onSuccess: " + jsonObject);
                    String attachmentApkId ="http://"+application.getIp_address()+"/admin/"+jsonObject.getString("attachmentApkId");
//					"http://192.168.0.126:8080/admin/ATTACHMENT/APP_APK/402880fe554258120155425ddf590000";
                    //"http://192.168.0.126:8080/admin/"+jsonObject.getString("attachmentApkId");
//					String attachmentApkId = "http://139.129.109.113:8080/admin/ATTACHMENT/APP_APK/8aad611b5514bb4401554cfb0d5c00fc";
                    int appCode = jsonObject.getInteger("appCode");
					Log.e("jsonrequest", "onSuccess: "+attachmentApkId+" "+appCode );

                    VersionInfo mVersionInfo = new VersionInfo();
                    mVersionInfo.setDownloadUrl(attachmentApkId);
                    mVersionInfo.setVersionCode(appCode);

                    //获取当前apk版本
                    int clientVersionCode = ApkUtils.getVersionCode(context);
                    Log.e("jsonrequest", "clientVersionCode: "+clientVersionCode);
                    //获取服务器上apk版本
                    int serverVersionCode = mVersionInfo.getVersionCode();
                    //有新版本
                    if (clientVersionCode < serverVersionCode) {
                        int i = NetworkUtil.checkedNetWorkType(context);
                        if (i == NetworkUtil.NOWIFI) {
                            updateListener.onUpdateReturned(UpdateStatus.YES, mVersionInfo);
                        } else if (i == NetworkUtil.WIFI) {
                            updateListener.onUpdateReturned(UpdateStatus.YES, mVersionInfo);
                        }
                    } else {
                        //无新本
                        updateListener.onUpdateReturned(UpdateStatus.NO, null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("UpdateStatus.ERROR", e+"");
                    updateListener.onUpdateReturned(UpdateStatus.ERROR, null);
                }
            }

            @Override
            public void onFailure(String error) {
                updateListener.onUpdateReturned(UpdateStatus.TIMEOUT, null);
            }
        });
    }


    /**
     * 弹出新版本提示
     *
     * @param context     上下文
     * @param versionInfo 更新内容
     */
    public static void update(final Context context, final VersionInfo versionInfo) {
        final File file = new File(Environment.getExternalStorageDirectory().getPath() + "/update/updatablep.apk");
        if (file.exists() && file.getName().equals("updatablep.apk")) {
			Intent intent = ApkUtils.getInstallIntent(file);
			context.startActivity(intent);
            //静默安装
            /*Log.e("UpdateStatus", "file: " + file.getAbsolutePath());
//            int so_install = PackageUtils.installSilent(context, file.getAbsolutePath());
            Log.e("apkFile", "onClick_install: "+file);
            try
            {
                Class<?> clazz = Class.forName("android.os.ServiceManager");
                Method method_getService = clazz.getMethod("getService",
                        String.class);
                IBinder bind = (IBinder) method_getService.invoke(null, "package");

                IPackageManager iPm = IPackageManager.Stub.asInterface(bind);
                iPm.installPackage(Uri.fromFile(file), null, 2,
                        file.getName());
            } catch (Exception e)
            {
                e.printStackTrace();
            }*/

        } else {
            //没有下载，则开启服务下载新版本
            Log.e("downloadUrl", "downloadUrl: ");
            Intent intent = new Intent(context, UpdateVersionService.class);
            intent.putExtra("downloadUrl", versionInfo.getDownloadUrl());
            context.startService(intent);
        }

    }

}
