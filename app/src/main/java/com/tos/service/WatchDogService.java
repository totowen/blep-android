package com.tos.service;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;


import com.tos.applock.LockScreenActivity;
import com.tos.applock.dao.AppLockDBDao;
import com.tos.util.ForegroundProcess;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.ContentValues.TAG;


public class WatchDogService extends Service {
    private boolean flag;
    private String packName;
    private AppLockDBDao dao;
    private ActivityManager am;
    private MyReceiver receiver;
    private DBChangedReceiver receiver4;
    private LockScreenReceiver receiver2;
    private UnLockScreenReceiver receiver3;
    private String className;
    private String tempPackName;
    private Intent intent;
    private List<String> packNames;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        receiver = new MyReceiver();
        receiver2 = new LockScreenReceiver();
        receiver3 = new UnLockScreenReceiver();
        receiver4 = new DBChangedReceiver();
        intent = new Intent(WatchDogService.this, LockScreenActivity.class);
        //无论是否存在都启动LockScreenAcitvity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        registerReceiver(receiver4, new IntentFilter("com.xiong.dbChanged"));
        registerReceiver(receiver2, new IntentFilter(Intent.ACTION_SCREEN_OFF));
        registerReceiver(receiver3, new IntentFilter(Intent.ACTION_SCREEN_ON));
        registerReceiver(receiver, new IntentFilter("com.xiong.applock"));
        dao = new AppLockDBDao(this);
        //////////////////////////////////////
        //锁住内置浏览器应用
        boolean b = dao.find("com.android.browser");
        if(!b){
            dao.add("com.android.browser");
        }
        //////////////////////////////////////
        packNames = dao.findAll();
        super.onCreate();
        flag = true;
        am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        lock();

    }


    static class RecentUseComparator implements Comparator<UsageStats> {

        @Override

        public int compare(UsageStats lhs, UsageStats rhs) {

            return (lhs.getLastTimeUsed() > rhs.getLastTimeUsed()) ? -1 : (lhs

                    .getLastTimeUsed() == rhs.getLastTimeUsed()) ? 0 : 1;

        }

    }


    private void getTopPackage() {

//        Log.d(TAG, "===getTopPackage=");

        long ts = System.currentTimeMillis();

        ApplicationService.RecentUseComparator mRecentComp = new ApplicationService.RecentUseComparator();


        UsageStatsManager mUsageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);

        List<UsageStats> usageStats = mUsageStatsManager.queryUsageStats(

                UsageStatsManager.INTERVAL_BEST, ts - 10000, ts);  //查询ts-10000 到ts这段时间内的UsageStats，由于要设定时间限制，所以有可能获取不到

        if (null != usageStats && usageStats.size() > 0) {
            Collections.sort(usageStats, mRecentComp);

            Log.d(TAG, "====usageStats.get(0).getPackageName()" + usageStats.get(0).getPackageName());

            String packageName = usageStats.get(0).getPackageName();

            if (null != packageName && !"".equals(packageName)) {
                SharedPreferences sharedPreferences = getSharedPreferences("PACKAGENAME2", Context.MODE_PRIVATE); //私有数据
                SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                editor.putString("packageName2", packageName);
                editor.commit();//提交修改
            }
        }


    }


    private void lock() {
        new Thread() {
            public void run() {
                while (flag) {
                    /*ComponentName runningActivity=am.getRunningTasks(1).get(0).topActivity;
					packName=runningActivity.getPackageName();//得到正在运行的activity包名*/

                    getTopPackage();
                    SharedPreferences share1 = getSharedPreferences("PACKAGENAME2", Activity.MODE_PRIVATE);
                    packName = share1.getString("packageName2", "");
                    //在集合packNames中遍历是否存在packName，存在且不是目标包名则开启锁屏
                    //不存在

                    try {
                        if (packNames.contains(packName)) {
                            if (packName.equals(tempPackName)) {

                            } else {
                                tempPackName = null;
                                intent.putExtra("packName", packName);
                                startActivity(intent);
                            }
                        } else if (!packName.equals(getPackageName())) {
                            tempPackName = null;//当activity为lockScreenActivity界面时，tempPackName不做改变
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//					try {
//						Thread.sleep(50);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
                }

            }
        }.start();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        flag = false;
        unregisterReceiver(receiver);
        unregisterReceiver(receiver2);
        unregisterReceiver(receiver3);
        unregisterReceiver(receiver4);

        receiver4 = null;
        receiver3 = null;
        receiver2 = null;
        receiver = null;
    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            tempPackName = intent.getStringExtra("packName");

            //System.out.println(tempPackName);
        }

    }

    private class LockScreenReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            tempPackName = null;
            flag = false;
            //System.out.println(tempPackName);
        }


    }

    private class UnLockScreenReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            flag = true;
            lock();
            //System.out.println(tempPackName);
        }
    }

    private class DBChangedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            packNames = dao.findAll();
            //System.out.println(tempPackName);
        }
    }
}
