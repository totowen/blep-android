package com.tos.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.greenDao.AppInstance;
import com.example.greenDao.AppInstanceDao;
import com.example.greenDao.AppRunConfigDetail;
import com.example.greenDao.AppRunConfigDetailDao;
import com.example.greenDao.DaoMaster;
import com.example.greenDao.DaoSession;
import com.tos.util.MacUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2016/3/30.
 */
public class BootReceiver extends BroadcastReceiver {
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    @Override
    public void onReceive(Context context, Intent intent) {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "notes-db", null);
        db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        //接收安装广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            //获取安装的包名
            String packageName = intent.getDataString();
            String msg = packageName.split(":")[1];
            System.out.println("安装了:" + packageName + " 包名的程序");
            //获取mac地址
            String mac = MacUtil.getMacAddress();

            //建立本地文件夹
            String path = Environment.getExternalStorageDirectory().getPath();
            final String fileName = "mac_address.txt";
            File tmpFile = new File(path);
            if (!tmpFile.exists()) {
                tmpFile.mkdir();
                final File file = new File(path+"/" + fileName);
                FileWriter writer;
                try {
                    writer = new FileWriter(path+"/" + fileName);
                    writer.write(mac);
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


            AppInstance instance = null;

            try {
                //获取dao对象，插入新建数据，如果更新应用需要更新
                instance = getAppInstanceDao().queryBuilder().where(AppInstanceDao.Properties.AppPackageName.eq(msg)).list().get(0);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //增加前先删除和包名一致的记录
            try {
                List<AppRunConfigDetail> detailList = getAppRunConfigDetail().queryBuilder().where(AppRunConfigDetailDao.Properties.DetailShelf_id.eq(msg)).list();
                for (AppRunConfigDetail detail : detailList) {
                    getAppRunConfigDetail().delete(detail);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (null == instance) {
                //构建一条新的安装信息
                AppInstance appInstance = new AppInstance();
                appInstance.setInstallTime(new Date());
                appInstance.setMac(mac);
                appInstance.setAppPackageName(msg);
                //给Detil表添加包名对应的星期
                for (int i = 0; i < 7; i++) {
                    switch (i) {
                        case 0:
                            insertMethod("周一", appInstance);
                            break;
                        case 1:
                            insertMethod("周二", appInstance);
                            break;
                        case 2:
                            insertMethod("周三", appInstance);
                            break;
                        case 3:
                            insertMethod("周四", appInstance);
                            break;
                        case 4:
                            insertMethod("周五", appInstance);
                            break;
                        case 5:
                            insertMethod("周六", appInstance);
                            break;
                        case 6:
                            insertMethod("周日", appInstance);
                            break;
                    }
                }

                getAppInstanceDao().insert(appInstance);
            }
        }
        //接收卸载广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            String packageName = intent.getDataString();
            String msg = packageName.split(":")[1];
            System.out.println("卸载了:" + packageName + "包名的程序");

            AppInstance instance = null;

            try {
                //获取dao对象
                instance = getAppInstanceDao().queryBuilder().where(AppInstanceDao.Properties.AppPackageName.eq(msg)).list().get(0);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (null != instance) {
                //构建一条新的安装信息
                getAppInstanceDao().delete(instance);
            }

        }
    }

    /**
     * 插入星期和对应appInstance
     *
     * @param day
     * @param appInstance
     */
    private void insertMethod(String day, AppInstance appInstance) {


        AppRunConfigDetail appRunConfigDetail = new AppRunConfigDetail();
        appRunConfigDetail.setDay(day);
        appRunConfigDetail.setState("stop");
        appRunConfigDetail.setTag(1);
        appRunConfigDetail.setDetailShelf_id(appInstance.getAppPackageName());
        getAppRunConfigDetail().insert(appRunConfigDetail);
    }

    private AppInstanceDao getAppInstanceDao() {
        return daoSession.getAppInstanceDao();
    }

    private AppRunConfigDetailDao getAppRunConfigDetail() {
        return daoSession.getAppRunConfigDetailDao();
    }

}
