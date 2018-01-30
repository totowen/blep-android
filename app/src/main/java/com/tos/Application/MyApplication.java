package com.tos.Application;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.example.greenDao.DaoMaster;
import com.example.greenDao.DaoSession;
import com.tos.service.ApplicationService;
import com.tos.util.MacUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by admin on 2016/3/17.
 */
public class MyApplication extends Application {
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private String ip_address;
    private String url_add_app_run_record;
    private String url_app_instances;
    private String url_app_runconfig_and_record;
    private String url;
    private String url_half;
    private String url_switch;
    private String url_update;
    private String url_serial_port;
    private String url_get_app_pwd;
    private String url_get_lock_pwd;
    private String url_single_app_search;

    @Override
    public void onCreate() {
        super.onCreate();
        //实例化SharedPreferences对象（第一步）
        SharedPreferences mySharedPreferences= getSharedPreferences("test",
                Activity.MODE_PRIVATE);
        String ip_address = mySharedPreferences.getString("IP_ADDRESS", "");
        if("".equals(ip_address)){
            //实例化SharedPreferences.Editor对象（第二步）
            SharedPreferences.Editor editor = mySharedPreferences.edit();
            //用putString的方法保存数据
            editor.putString("IP_ADDRESS", "139.129.109.113:8080");
            //提交当前数据
            editor.apply();
        }
        //初始化IP_ADDRESS
        this.setIp_address(mySharedPreferences.getString("IP_ADDRESS","139.129.109.113:8080"));
        this.setUrl(this.getIp_address());
        this.setUrl_half(this.getIp_address());
        this.setUrl_app_instances(this.getIp_address());
        this.setUrl_add_app_run_record(this.getIp_address());
        this.setUrl_app_runconfig_and_record(this.getIp_address());
        this.setUrl_switch(this.getIp_address());
        this.setUrl_update(this.getIp_address());
        this.setUrl_serial_port(this.getIp_address());
        this.setUrl_get_app_pwd(this.getIp_address());
        this.setUrl_get_lock_pwd(this.getIp_address());
        this.setUrl_single_app_search(this.getIp_address());

        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = helper.getWritableDatabase();
        setDb(db);
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        setDaoSession(daoSession);

        //获取mac地址
        String mac = MacUtil.getMacAddress();
        //建立本地文件夹
        String path = Environment.getExternalStorageDirectory().getPath();
        final String fileName = "macAddress.txt";
        Log.e("path", "onCreate: "+path+"/"+fileName+"  "+mac);
        File tmpFile = new File(path);
        if (tmpFile.exists()&&null!=mac) {
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


        //检测桌面系统服务是否开启
        boolean is = isServiceWork(this, "ApplicationService");
        Log.e("isServiceWork", is + "");
        if (!is){
//           Intent intent = new Intent(this, ApplicationService.class);
//           startService(intent);
            Intent intent2 = new Intent("ELITOR_CLOCK");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent2, 0);
            AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP , System.currentTimeMillis(),60*1000, pendingIntent);
        }






    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName
     *            是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getUrl_add_app_run_record() {
        return url_add_app_run_record;
    }

    public void setUrl_add_app_run_record(String ip) {
        this.url_add_app_run_record = "http://"+ip+"/admin/api/pad/addAppRunRecord/";//+mac
    }

    public String getUrl_app_instances() {
        return url_app_instances;
    }

    public void setUrl_app_instances(String ip) {
        this.url_app_instances = "http://"+ip+"/admin/api/pad/appInstances";
    }

    public String getUrl_app_runconfig_and_record() {
        return url_app_runconfig_and_record;
    }

    public void setUrl_app_runconfig_and_record(String ip) {
        this.url_app_runconfig_and_record = "http://"+ip+"/admin/api/parentApp/AppRunConfigAndRecord/";//+{appInstanceId}/{mac}/{pageNo}/{pageSize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String ip) {
        this.url = "http://"+ip+"/admin/api/pc/getAllAppPackage/1/99999";;
    }

    public String getUrl_half() {
        return url_half;
    }

    public void setUrl_half(String ip) {
        this.url_half = "http://"+ip+"/admin/";
    }

    public String getUrl_switch() {
        return url_switch;
    }

    public void setUrl_switch(String ip) {
        this.url_switch = "http://"+ip+"/admin/api/parentApp/instance/";//{mac}/{packageName}/{status}
    }

    public String getUrl_update() {
        return url_update;
    }

    public void setUrl_update(String ip) {
        this.url_update = "http://"+ip+"/admin/api/pad/package/com.tos.blepphone/one";
    }

    public String getUrl_serial_port() {
        return url_serial_port;
    }

    public void setUrl_serial_port(String ip) {
        this.url_serial_port = "http://"+ip+"/admin/api/pad/package/upload_serial_port";
//        this.url_serial_port = "http://139.129.109.113:8088/admin/api/pad/package/upload_serial_port";
    }

    public String getUrl_get_app_pwd() {
        return url_get_app_pwd;
    }

    public void setUrl_get_app_pwd(String ip) {
        this.url_get_app_pwd = "http://"+ip+"/admin/api/pad/package/url_get_app_pwd";
    }

    public String getUrl_get_lock_pwd() {
        return url_get_lock_pwd;
    }

    public void setUrl_get_lock_pwd(String ip) {
        this.url_get_lock_pwd = "http://"+ip+"/admin/api/pad/package/url_get_lock_pwd";
    }

    public String getUrl_single_app_search() {
        return url_single_app_search;
    }

    public void setUrl_single_app_search(String ip) {
        this.url_single_app_search = "http://"+ip+"/admin/api/pc/url_single_app_search";
    }
}
