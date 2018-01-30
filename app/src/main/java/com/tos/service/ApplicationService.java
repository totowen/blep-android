package com.tos.service;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ClearCacheRequest;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.Volley;
import com.example.greenDao.AppInstance;
import com.example.greenDao.AppInstanceDao;
import com.example.greenDao.AppRunConfigDetail;
import com.example.greenDao.AppRunConfigDetailDao;
import com.example.greenDao.AppRunRecord;
import com.example.greenDao.AppRunRecordDao;
import com.example.greenDao.DaoSession;
import com.example.greenDao.Time;
import com.example.greenDao.TimeDao;
import com.tos.Application.MyApplication;
import com.tos.Dto.AppInstanceDto;
import com.tos.Dto.AppRunConfigDetailDto;
import com.tos.Dto.AppRunRecordDto;
import com.tos.applock.LockScreenActivity;
import com.tos.applock.dao.AppLockDBDao;
import com.tos.applock.utils.AppInfoProvider;
import com.tos.util.MacUtil;
import com.tos.util.RestTemplateUtil;
import com.tos.util.VolleyUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.springframework.http.HttpMethod;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class ApplicationService extends Service {
    private ActivityManager am;
    private String packName;
    private DaoSession daoSession;
    private MyApplication mApplication;
    private AppLockDBDao dao;//被锁屏的应用信息数据库
    List<AppRunRecord> appRunRecords;
    List<AppRunRecordDto> appRunRecordDtos;
    List<AppInstance> appInstances;
    String mac;

    private String cur_packName;
    private Date curDate;
    private long x, y, h, m, s;

    private Integer flag = 0;
    private Integer flag_net = 2;

    private final Timer timer1 = new Timer();
    private TimerTask task1;

    private final Timer timer2 = new Timer();
    private TimerTask task2;

    private String url_switch;
    //转化目标格式
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", java.util.Locale.getDefault());
    List<Time> saveTime = null;

    private RequestQueue mRequestQueue;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //获取全局变量的SQLiteDatabase对象和DaoSession对象
        mApplication = (MyApplication) getApplication();
        //获取系统服务
        am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        EventBus.getDefault().register(this);
        daoSession = mApplication.getDaoSession();
        url_switch = mApplication.getUrl_switch();
        dao = new AppLockDBDao(this);
//        Log.i("tag", "ApplicationService onCreate...");
        saveTime = getTime().loadAll();
    }

    private AppRunRecordDao getAppRunRecordDao() {
        return daoSession.getAppRunRecordDao();
    }

    private AppInstanceDao getAppInstanceDao() {
        return daoSession.getAppInstanceDao();
    }

    private AppRunConfigDetailDao getAppRunConfigDetail() {
        return daoSession.getAppRunConfigDetailDao();
    }

    private TimeDao getTime() {
        return daoSession.getTimeDao();
    }

    @Subscribe(threadMode = ThreadMode.POSTING) //在异步线程执行
    public void onDataSynEvent(Integer event) {
//        Log.e(EventBus.TAG, "event---->" + event);
        switch (event) {
            case 0:
                flag = event;
                break;
            case 1:
                flag = event;
                break;
            case 2:
                flag_net = event;
                break;
            case 3:
                flag_net = event;
                break;

        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.i("tag", "ApplicationService onStartCommand..." + startId);

        task2 = new TimerTask() {
            @Override
            public void run() {
                if (2 == flag_net) {
                    long l = System.currentTimeMillis();
                    EventBus.getDefault().post(3);
                    uploadAndDownLoadDates();
                    EventBus.getDefault().post(2);
                    long l1 = System.currentTimeMillis();
                    Log.e("yunxingshijian", "task2: " + (l1 - l));
                }
            }

        };
        timer2.schedule(task2, 2000, 20000);

        task1 = new TimerTask() {

            @Override
            public void run() {
                if (0 == flag) {
                    long l = System.currentTimeMillis();
                    EventBus.getDefault().post(1);
                    saveControlTime();
                    EventBus.getDefault().post(0);
                    long l1 = System.currentTimeMillis();
                    Log.e("yunxingshijian", "task1: " + (l1 - l));
                }
            }

        };

        timer1.schedule(task1, 2000, 1000);

        return START_STICKY;//系统有足够多资源的时候，就会重新开启service。
    }

    private void uploadAndDownLoadDates() {

        ///////////////////////////////////////////定时监测运行app//////////////////////////////////////////////
        getTopPackage();
        SharedPreferences share = getSharedPreferences("PACKAGENAME", Activity.MODE_PRIVATE);
        String packageName1 = share.getString("packageName", "");

//        Log.e("ApplicationService1", "top app = " + packageName1);
//        Log.e("ApplicationService1", "cur_packName: " + cur_packName);
        if (null == cur_packName) {//当前包名为空标记
            curDate = new Date(System.currentTimeMillis());
//                    packName = runningAct.getPackageName();//获取当前包名
            packName = packageName1;
            cur_packName = packName;//将当前包名赋值给当前包名标记
            boolean flag = AppInfoProvider.getAppInfo(ApplicationService.this, packName);//判断是否为用户应用
//            Log.e("flag.......", flag + "  " + packName);
            if (flag) {
                AppRunRecord record = null;
                //添加开始时间和包名
                record = new AppRunRecord(null, curDate, null, "", "", "", null, null, packName);
                //把记录添加到数据库中  (注：如果为系统应用不做添加)
                long hh = getAppRunRecordDao().insert(record);
//                Log.e("hh", hh + "");
                try {
                    //在instance中找到该包名数据
                    List<AppRunConfigDetail> list = getAppRunConfigDetail().queryBuilder()
                            .where(AppRunConfigDetailDao.Properties.DetailShelf_id.eq(packName)).list();
                    for (int i = 0; i < list.size(); i++
                            ) {
//                        Log.e("detail", "state:start ");
                        list.get(i).setState("start");
                        getAppRunConfigDetail().update(list.get(i));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (!cur_packName.equals(packageName1)) {//当前应用为上一个运行的应用
//            Log.e("cur_packName", "run: " + cur_packName);
            //保存APP启动时间和关闭时间等信息到数据库
            curDate = new Date(System.currentTimeMillis());
            packName = packageName1;
//                    boolean flag = AppInfoProvider.getAppInfo(ApplicationService.this, packName);//判断是否为用户应用
//                    if (flag) {
            AppRunRecord record = null;
            //保存新记录前，查找到第一条关闭的应用，并添加退出时间
            search();

            //在这里插入应用状态在本地数据库，插入对象是AppRunConfigDetailDto
            try {
                //在instance中找到该包名数据
                List<AppRunConfigDetail> list = getAppRunConfigDetail().queryBuilder()
                        .where(AppRunConfigDetailDao.Properties.DetailShelf_id.eq(cur_packName)).list();
                for (int i = 0; i < list.size(); i++
                        ) {
//                    Log.e("state: ", "stop ");
                    list.get(i).setState("stop");

                    getAppRunConfigDetail().update(list.get(i));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //新的应用开启
            cur_packName = packName;
//            Log.e("cur_packName: ", "start " + cur_packName);
            try {
                //在instance中找到该包名数据
                List<AppRunConfigDetail> list = getAppRunConfigDetail().queryBuilder()
                        .where(AppRunConfigDetailDao.Properties.DetailShelf_id.eq(cur_packName)).list();

                if (null != list && list.size() > 0) {

                    for (int i = 0; i < list.size(); i++
                            ) {
//                        Log.e("state: ", "start ");

//                        Log.e("cur_packName: ", "List " + list.get(i).getAppInstance().getAppPackageName());

                        list.get(i).setState("start");

                        getAppRunConfigDetail().update(list.get(i));
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //添加开始时间和包名
            record = new AppRunRecord(null, curDate, null, "", "", "", null, null, packName);
            //把记录添加到数据库中
            getAppRunRecordDao().insert(record);
//                    }

        }
///////////////////////////////////////////////////////////////////////////////////////////////////////
        appRunRecords = getAppRunRecordDao().queryBuilder().orderDesc(AppRunRecordDao.Properties.Id).list();
        appRunRecordDtos = new ArrayList<AppRunRecordDto>();
        for (AppRunRecord dao : appRunRecords) {
            AppRunRecordDto appRunRecordDto = new AppRunRecordDto();
            appRunRecordDto.setStartTime(dao.getStartTime());
            appRunRecordDto.setExitTime(dao.getExitTime());
            appRunRecordDto.setAppInstancePackageName(dao.getAppInstancePackageName());
            try {
                if (null != appRunRecordDto.getExitTime()) {
                    appRunRecordDtos.add(appRunRecordDto);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//                Log.e("tag",appRunRecords.toString());
        // 在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
        //QueryBuilder.LOG_SQL = true;
        //QueryBuilder.LOG_VALUES = true;
        //获取mac地址
        mac = MacUtil.getMacAddress();
        //上传app运行记录到服务器
        //Log.e("appRunRecordsss",appRunRecordDtos.toString());
        RestTemplateUtil.uploadAppRunRecord(mApplication.getUrl_add_app_run_record() + mac, appRunRecordDtos);
        //上传app运行记录到服务器之后就将记录删除
        try {
            if (null != appRunRecordDtos && appRunRecordDtos.size() > 0) {
                if (null != appRunRecordDtos.get(0).getExitTime()) {
                    getAppRunRecordDao().delete(appRunRecords.get(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //上传app安装实例到服务器
        appInstances = getAppInstanceDao().queryBuilder().list();
        List<AppInstanceDto> appInstanceDtos = new ArrayList<AppInstanceDto>();
        for (AppInstance dao : appInstances) {
            AppInstanceDto appInstanceDto = new AppInstanceDto();
            appInstanceDto.setAppPackageName(dao.getAppPackageName());
            appInstanceDto.setInstallTime(dao.getInstallTime());
            appInstanceDto.setMac(dao.getMac());
            appInstanceDtos.add(appInstanceDto);
        }
        //QueryBuilder.LOG_SQL = true;
        //QueryBuilder.LOG_VALUES = true;
        try {
//                    Log.e("macaddress", appInstanceDtos.get(0).getMac());
            RestTemplateUtil.uploadAppInstances(mApplication.getUrl_app_instances(), appInstanceDtos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //上传app开启/关闭状态
        try {
            for (int i = 0; i < appInstanceDtos.size(); i++) {
                List<AppRunConfigDetail> appRunConfigDetails = getAppRunConfigDetail().queryBuilder()
                        .where(AppRunConfigDetailDao.Properties.State.eq("start")
                                , AppRunConfigDetailDao.Properties.DetailShelf_id.eq(appInstanceDtos.get(i).getAppPackageName())).list();
                if (appRunConfigDetails.size() <= 0) {
                    try {
                        uploadAppState(appInstanceDtos, i, "stop");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        uploadAppState(appInstanceDtos, i, "start");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            saveTime = getTime().loadAll();
            getTime().deleteAll();
            for (int i = 0; i < appInstanceDtos.size(); i++) {
                //获取家长控制信息
                getRunConfig(appInstanceDtos.get(i).getMac(), appInstanceDtos.get(i).getAppPackageName());
            }
            saveTime = getTime().loadAll();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void saveControlTime() {
        try {
            //获取当前运行应用
            getTopPackage();
            SharedPreferences share1 = getSharedPreferences("PACKAGENAME", Activity.MODE_PRIVATE);
            String packageName2 = share1.getString("packageName", "");
            boolean packageNameFlag = false;
            List<AppRunConfigDetail> appRunConfigDetails = getAppRunConfigDetail().loadAll();

            //当前应用是否在数据库中，不在则关闭当前应用
            for (AppRunConfigDetail record2 : appRunConfigDetails) {
                String detailShelf_id = record2.getDetailShelf_id();
                if ((null != detailShelf_id && packageName2.equals(detailShelf_id)) || (record2.getStatusStartTime() != null && record2.getStatusEndTime() != null)) {
                    packageNameFlag = true;
                }
            }

            boolean appInfo1 = AppInfoProvider.getAppInfo(ApplicationService.this, packageName2);
            if (!appInfo1) {
                packageNameFlag = true;
            }

            if ("com.tos.blepphone".equals(packageName2)) {
                packageNameFlag = true;
            }

//            Log.e("AppRunConfigDetai", "正在执行啥程序" + packageName2 + "   " + packageNameFlag);

            if (!"".equals(packageName2) && !packageNameFlag) {
//                Log.e("guanbi1", "
                boolean b = dao.find(packageName2);
                if(!b){
                    dao.add(packageName2);
                }
//                goMainActivity();
                return;
            }

            //此标记是判断时间是否在允许范围内
            boolean flag = false;
            //此标记是判断是否进入当前应用的判断
            boolean flag_two = false;

            Date now = new Date();
            //按照当前定义时间格式截取当前时间
            String nowDate_string = dateFormat.format(now);
//                Log.e("AppRunConfigDetaildd", nowDate_string);
            Date nowDate = dateFormat.parse(nowDate_string);
            //获取数据库的时间
            long l = System.currentTimeMillis();

            int temp = 0;

            String current_package = "";
            for (AppRunConfigDetail record : appRunConfigDetails) {
                temp++;
                //record 中有day，appStatus，padAppStatus，statusStartTime，statusEndTime
                String appStatus = record.getAppStatus();
                String padAppStatus = record.getPadAppStatus();
                String statusStartTime = record.getStatusStartTime();
                String statusEndTime = record.getStatusEndTime();
                String packageName = record.getDetailShelf_id();//包名
                current_package = packageName;

                long id = record.getId();
                List<Time> timeList = new ArrayList<Time>();
                for (Time time :
                        saveTime) {
                    if (time.getTimeShelf_id() == id) {
                        timeList.add(time);
                    }
                }
//                Log.e("guanbi2", "flag :" + times.toString());

                //获取instance上的tag状态
                List<AppRunConfigDetail> details = getAppRunConfigDetail().queryBuilder()
                        .where(AppRunConfigDetailDao.Properties.DetailShelf_id.eq(packageName)).list();

                Long details_size = getAppRunConfigDetail().queryBuilder()
                        .where(AppRunConfigDetailDao.Properties.DetailShelf_id.eq(packageName), AppRunConfigDetailDao.Properties.StatusStartTime.isNotNull()).count();

//                Log.e(TAG, "saveControlTime: jinif " + details_size);

                if (statusStartTime != null && statusEndTime != null) {//除了开启+关闭有时间，其他状态组合都没有时间
//                    Log.e(TAG, "saveControlTime: jinif " + packageName);
                    Date time_one = dateFormat.parse(statusStartTime);
                    Date time_two = dateFormat.parse(statusEndTime);
                    flag = compareTime(flag, nowDate, time_one, time_two);//是否在当前时间段内
//                    Log.e("timeflag", flag + "" + statusStartTime + "  " + statusEndTime + " " + nowDate_string);
                    if (flag) {//在当前时间段内
                        if ("start".equals(appStatus) && "stop".equals(padAppStatus)) {
                            //获取星期
                            int today = getDay(now) - 1;
                            if (today == 0) {
                                today = 7;
                            }
                            for (AppRunConfigDetail detail : details
                                    ) {
                                int weekDay_int = getWeekDay_int(detail);
                                //如果tag为“关闭”
                                if (today == weekDay_int && 1 == detail.getTag()) {
                                    Log.e("lock", "" + current_package + "  delete1");
                                    boolean b = dao.find(current_package);
                                    if(b){
                                        dao.delete(current_package);
                                    }
                                    //app的tag改为“开启”
                                    detail.setTag(0);
                                    //这里比服务器同步数据先行
                                    detail.setPadAppStatus("start");

                                    getAppRunConfigDetail().update(detail);
                                    //控制当前应用开启
                                    openCLD(packageName, ApplicationService.this);

                                }
                            }
                        }
                        flag = false;
                    }

                } else {
                    if (details_size == 0) {
//                        Log.e(TAG, "saveControlTime: jinelse " + packageName);
//                    Log.e("ComponentName", "run: 现在走这");
//                            appStatus_start_time不为null 就不更新tag为1 ，否则反之
                        try {
                            for (AppRunConfigDetail detail : details
                                    ) {
                                if (null == detail.getStatusStartTime()) {
                                    detail.setTag(1);
                                    getAppRunConfigDetail().update(detail);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //获取当前运行应用
//                        getTopPackage();
//                        SharedPreferences share2 = getSharedPreferences("PACKAGENAME", Activity.MODE_PRIVATE);
//                        String packageName3 = share2.getString("packageName", "");
//                        packName = packageName3;
//                    Log.e("AppRunConfigDetail2+", "正在执行啥程序" + packName + "   " + packageName);
                        //如果当前packageName包含运行packName就执行如下判断
//                        if (packageName.equals(packName)) {
                        //当包名相同的时候先设置flag_two = true;保证后面执行的确定性
//                            flag_two = true;
//                        Log.e("AppRunConfigDetail2", "正在执行啥程序" + packName + "  " + times.toString() + " " + times.size());

//                        if (packageName2.equals(current_package)) {
                        /******* 以上获取到对应appPackage-某星期-某时间段集合 ******/
                        for (int j = 0; j < timeList.size(); j++
                                ) {
//                            Log.e("AppRunConfigDetail2++", "循环" + j);
                            //跟多个目标时间进行比较

                            //比较的时间
                            String str = timeList.get(j).getTime();

                            int weekDay_id = (int) timeList.get(j).getTimeShelf_id();

                            AppRunConfigDetail detail_2 = null;

                            try {
                                detail_2 = getAppRunConfigDetail().queryBuilder().where(AppRunConfigDetailDao.Properties.Id.eq(weekDay_id)).list().get(0);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            int weekDay_int = getWeekDay_int(detail_2);

                            //获取星期
                            int today = getDay(now) - 1;

                            if (today == 0) {
                                today = 7;
                            }

//                            Log.e("AppRunConfigDetail3", str + " " + today + " " + weekDay_int);
//                            Log.e("AppRunConfigDetail6", "" + flag);
                            if (weekDay_int == today) {
                                //对时间进行第一步分段 原始格式为 “11:00-11:30,12:00-12:30,13:00-13:30”
                                String[] array = str.split(",");
//                                Log.e("AppRunConfigDetail4", array[0]);
                                //此标记为程序是否为可执行
//                                Log.e("AppRunConfigDetail5", "开始遍历时间");
                                for (int i = 0; i < array.length; i++) {
                                    String[] array_s = array[i].split("-");
                                    Date time_one_2 = dateFormat.parse(array_s[0]);
                                    Date time_two_2 = dateFormat.parse(array_s[1]);
//                                    Log.e("AppRunConfigDetail5+", "run: " + time_one_2 + " " + time_two_2 + " " + nowDate);
//                                    Log.e("AppRunConfigDetail5+", "run: " + time_one_2.getTime() + " " + time_two_2.getTime() + " " + nowDate.getTime());
                                    //当第一时间比第二时间小，当前时间大于第一时间且小于第二时间
                                    flag = compareTime(flag, nowDate, time_one_2, time_two_2);
                                }
                            }
                        }


//                        }
//                        }
                    }

                }

                if (details_size == 0&&temp%7==0) {
                    //以上循环只要存在一次true 就可以不关闭
                    Log.e("lock", "flag :" + flag);
                    boolean b = dao.find(current_package);
                    if (!flag) {
                        Log.e("lock", "" + current_package + "  add2");

                        if(!b){
                            dao.add(current_package);
                        }

                    } else if (flag) {
                        Log.e("lock", "" + current_package + "  delete2");
                        if(b){
                            dao.delete(current_package);
                        }
                    }
                    flag = false;
                }
            }
//            long l1 = System.currentTimeMillis();
//            Log.e("xunhuanshijian", " " + (l1 - l));
//                    Log.e("realtiem", "删掉");

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private boolean compareTime(boolean flag, Date nowDate, Date time_one, Date time_two) {
        //当第一时间比第二时间小，当前时间大于第一时间且小于第二时间
        if (time_one.getTime() < time_two.getTime() && nowDate.getTime() >= time_one.getTime() && nowDate.getTime() < time_two.getTime()) {
//            System.out.println("true1");
//            Log.e("AppRunConfigDetail6", "flag 为 true");
            flag = true;
        }//当第一时间比第二时间大，当前时间小于第一和第二时间
        else if (time_one.getTime() > time_two.getTime() && nowDate.getTime() < time_one.getTime() && nowDate.getTime() < time_two.getTime()) {
//            System.out.println("true2");
//            Log.e("AppRunConfigDetail7", "flag 为 true");
            flag = true;
        }
        else if (time_one.getTime() > time_two.getTime() && nowDate.getTime() > time_one.getTime() && nowDate.getTime() > time_two.getTime()) {
//            System.out.println("true2");
//            Log.e("AppRunConfigDetail7", "flag 为 true");
            flag = true;
        }
        return flag;
    }

    private void getRunConfig(String mac, String packageName) {
        try {
            /**
             * 获取控制时间json-appInstanceDtos
             * 1、将安装的instance获取（上一步已做到）
             * 2、然后将instance的包名和mac填入链接，获取家长控制的信息
             */
//            Log.e("appPackageName", "packageName: " + packageName);
            String result = RestTemplateUtil.getResToGet(mApplication.getUrl_app_runconfig_and_record() + "/" + packageName + "/" + mac + "/" + "1/" + "9999", HttpMethod.GET);
//            Log.e("BBBBBB", packageName + "  " + mac + "  " + result);

            if (null != result) {
                JSONObject app = JSON.parseObject(result);
                JSONArray cdjJsonArray = (JSONArray) app.get("appRunConfigDetailDtos");
                List<AppRunConfigDetailDto> cds = JSON.parseArray(cdjJsonArray.toJSONString(), AppRunConfigDetailDto.class);
                String appPackageName = (String) app.get("appPackageName");
//            Log.e("AppRunConfigDetail3", appPackageName);
//                Log.e("guanbi", cds.toString());
                //将解析的数据放到本地数据库
                if (null != cds && cds.size() > 0) {

                    for (AppRunConfigDetailDto appRunConfigDetailDto : cds) {

                        configDetialDao(appPackageName, appRunConfigDetailDto, appRunConfigDetailDto.getDay());

//                Log.e("AppRunConfigDetail10", appRunConfigDetailDto.getTimes().size() + "");
                        for (String time : appRunConfigDetailDto.getTimes()) {

                            int flag = 0;
                            switch (appRunConfigDetailDto.getDay()) {
                                case "周一":
                                    flag = 0;
                                    break;
                                case "周二":
                                    flag = 1;
                                    break;
                                case "周三":
                                    flag = 2;
                                    break;
                                case "周四":
                                    flag = 3;
                                    break;
                                case "周五":
                                    flag = 4;
                                    break;
                                case "周六":
                                    flag = 5;
                                    break;
                                case "周日":
                                    flag = 6;
                                    break;
                            }

//                    Log.e("AppRunConfigDetail11", time + flag);
                            //根据包名找到对应的包的所有数据
                            switch (flag) {
                                case 0:
                                    try {
                                        //查询到包名对应的星期
//                                Log.e("appPackageName", "packageName+: " + appPackageName);
                                        AppRunConfigDetail appRunConfigDetail = getAppInstance(appPackageName, "周一");
                                        //将关联的时间放到Time表中
                                        Time time1 = createTime(time, appRunConfigDetail);
                                        //insert
                                        if (null != time1) {
                                            getTime().insert(time1);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 1:
                                    try {
                                        AppRunConfigDetail appRunConfigDetail2 = getAppInstance(appPackageName, "周二");
                                        //将关联的时间放到Time表中
                                        Time time1 = createTime(time, appRunConfigDetail2);
                                        if (null != time1) {
                                            getTime().insert(time1);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 2:
                                    try {
                                        AppRunConfigDetail appRunConfigDetail3 = getAppInstance(appPackageName, "周三");
                                        //将关联的时间放到Time表中
                                        Time time1 = createTime(time, appRunConfigDetail3);
                                        if (null != time1) {
                                            getTime().insert(time1);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 3:
                                    try {
                                        AppRunConfigDetail appRunConfigDetail4 = getAppInstance(appPackageName, "周四");
                                        //将关联的时间放到Time表中
                                        Time time1 = createTime(time, appRunConfigDetail4);
                                        if (null != time1) {
                                            getTime().insert(time1);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 4:
                                    try {
                                        AppRunConfigDetail appRunConfigDetail5 = getAppInstance(appPackageName, "周五");
                                        //将关联的时间放到Time表中
                                        Time time1 = createTime(time, appRunConfigDetail5);
                                        if (null != time1) {
                                            getTime().insert(time1);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 5:
                                    try {
                                        AppRunConfigDetail appRunConfigDetail6 = getAppInstance(appPackageName, "周六");
                                        //将关联的时间放到Time表中
                                        Time time1 = createTime(time, appRunConfigDetail6);
                                        if (null != time1) {
                                            getTime().insert(time1);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 6:
                                    try {
                                        AppRunConfigDetail appRunConfigDetail7 = getAppInstance(appPackageName, "周日");
                                        //将关联的时间放到Time表中
                                        Time time1 = createTime(time, appRunConfigDetail7);
                                        if (null != time1) {
                                            getTime().insert(time1);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Time createTime(String time, AppRunConfigDetail appRunConfigDetail) {
        try {
//            List<Time> list = getTime().queryBuilder().where(TimeDao.Properties.TimeShelf_id.eq(appRunConfigDetail.getId())).list();
//            if(null!=list&&list.size()>0){
//                for (Time time1:
//                list) {
//                    Log.e("guanbi",""+ time1.toString());
//                    getTime().delete(time1);
//                }
//            }
            Time time2 = new Time();
            time2.setTime(time);
            time2.setTimeShelf_id(appRunConfigDetail.getId());
            return time2;
        } catch (Exception e) {
            return null;
        }
    }

    private int getWeekDay_int(AppRunConfigDetail detail_2) {
        String weekDay_str = "";
        int weekDay_int = 0;
        if (null != detail_2) {
            weekDay_str = detail_2.getDay();
        }


        switch (weekDay_str) {
            case "周一":
                weekDay_int = 1;
                break;
            case "周二":
                weekDay_int = 2;
                break;
            case "周三":
                weekDay_int = 3;
                break;
            case "周四":
                weekDay_int = 4;
                break;
            case "周五":
                weekDay_int = 5;
                break;
            case "周六":
                weekDay_int = 6;
                break;
            case "周日":
                weekDay_int = 7;
                break;
        }
        return weekDay_int;
    }

    private void configDetialDao(String appPackageName, AppRunConfigDetailDto appRunConfigDetailDto, String week) {
        try {

//           Log.e("configDetialDao ： ", "configDetialDao: "+ appPackageName +" ");
            //找到包名和星期对应的数据
            List<AppRunConfigDetail> list = getAppRunConfigDetail().queryBuilder()
                    .where(AppRunConfigDetailDao.Properties.DetailShelf_id.eq(appPackageName)
                            , AppRunConfigDetailDao.Properties.Day.eq(week)).list();

            if (null != list && list.size() > 0) {
                AppRunConfigDetail detail = list.get(0);
                if (null != appRunConfigDetailDto) {
                    if (null != appRunConfigDetailDto.getStatusStartTime()) {//如果及时控制时间不为空，更新本地数据库数据
                        detail.setStatusStartTime(appRunConfigDetailDto.getStatusStartTime());
                        detail.setStatusEndTime(appRunConfigDetailDto.getStatusEndTime());
                        detail.setAppStatus(appRunConfigDetailDto.getAppStatus());
                        detail.setPadAppStatus(appRunConfigDetailDto.getPadAppStatus());
//            detail.setTag(1);
                        getAppRunConfigDetail().update(detail);
                    } else {//如果及时控制时间为空，更新本地数据库数据，并将tag设置为1
                        detail.setStatusStartTime(null);
                        detail.setStatusEndTime(null);
                        detail.setAppStatus(appRunConfigDetailDto.getAppStatus());
                        detail.setPadAppStatus(appRunConfigDetailDto.getPadAppStatus());
//            detail.setTag(1);
                        getAppRunConfigDetail().update(detail);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadAppState(List<AppInstanceDto> appInstanceDtos, int i, String state) {
        String mac = MacUtil.getMacAddress();
        String package_name = appInstanceDtos.get(i).getAppPackageName();
//                              String url_switch = "http://139.129.109.113:8080/admin/api/parentApp/instance/";
        RequestQueue requestQueue = getRequestQueue();
        VolleyUtil.getJSONVolley(url_switch + mac + "/" + package_name + "/" + state, ApplicationService.this,requestQueue);
//        Log.e("VolleyUtil", package_name + "  " + state);
    }

    public static void openCLD(String packageName, Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo pi = null;

        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        try {
            pi = packageManager.getPackageInfo(packageName, 0);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(pi.packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        List<ResolveInfo> apps = packageManager.queryIntentActivities(resolveIntent, 0);

        ResolveInfo ri = apps.iterator().next();
        if (ri != null) {
            String className = ri.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            context.startActivity(intent);
        }
    }

    public static void closeCLD(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(intent);
    }

    private void goMainActivity() {
//        Log.e("goMainActivity", "goMainActivity: ");
        Intent intent = new Intent(getBaseContext(), LockScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(intent);
    }

    private AppRunConfigDetail getAppInstance(String appPackageName, String week) {
        AppRunConfigDetail detail = null;
        try {
            AppInstance appInstance = getAppInstanceDao().queryBuilder().where(AppInstanceDao.Properties.AppPackageName.eq(appPackageName)).list().get(0);
            detail = getAppRunConfigDetail()
                    .queryBuilder()
                    .where(AppRunConfigDetailDao.Properties.Day.eq(week))
                    .where(AppRunConfigDetailDao.Properties.DetailShelf_id.eq(appInstance.getAppPackageName()))
                    .list().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    /**
     * 计算app运行时间
     */
    private void search() {
        // Query 类代表了一个可以被重复执行的查询
        List<AppRunRecord> item = getAppRunRecordDao().queryBuilder()
                .orderDesc(AppRunRecordDao.Properties.StartTime)
                .list();
        if (null != item && item.size() > 0) {
            AppRunRecord appRunRecord = item.get(0);
//            Log.e("tag", appRunRecord.toString());
            appRunRecord.setExitTime(curDate);
            //计算运行时间
            h = m = s = 0;
            x = appRunRecord.getStartTime().getTime();
            y = curDate.getTime();
            h = (y - x) / 3600 / 1000;
            m = ((y - x) - h * 3600 * 1000) / 1000 / 60;
            s = ((y - x) - h * 1000 * 3600 - m * 60 * 1000) / 1000;
            appRunRecord.setRunTime(h + "小时" + m + "分" + s + "秒");
            //更新数据库
            getAppRunRecordDao().update(appRunRecord);
        }
    }

    /**
     * UsageStats比较器
     */
    static class RecentUseComparator implements Comparator<UsageStats> {

        @Override

        public int compare(UsageStats lhs, UsageStats rhs) {

            return (lhs.getLastTimeUsed() > rhs.getLastTimeUsed()) ? -1 : (lhs

                    .getLastTimeUsed() == rhs.getLastTimeUsed()) ? 0 : 1;

        }

    }

    /**
     * 获取正在运行app应用包名
     */
    private void getTopPackage() {

//        Log.d(TAG, "===getTopPackage=");
        String packageName = "";

        long ts = System.currentTimeMillis();

        RecentUseComparator mRecentComp = new RecentUseComparator();

        UsageStatsManager mUsageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);

        List<UsageStats> usageStats = mUsageStatsManager.queryUsageStats(

                UsageStatsManager.INTERVAL_BEST, ts - 10000, ts);  //查询ts-10000 到ts这段时间内的UsageStats，由于要设定时间限制，所以有可能获取不到

        if (null != usageStats && usageStats.size() > 0) {
            Collections.sort(usageStats, mRecentComp);

//            Log.d(TAG, "====usageStats.get(0).getPackageName()" + usageStats.get(0).getPackageName());

            packageName = usageStats.get(0).getPackageName();

            if (null != packageName && !"".equals(packageName)) {
                SharedPreferences sharedPreferences = getSharedPreferences("PACKAGENAME", Context.MODE_PRIVATE); //私有数据
                SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                editor.putString("packageName", packageName);
                editor.commit();//提交修改
            }
        }

    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mApplication);
        }
        File cacheDir = new File(this.getCacheDir(), "volley");
        DiskBasedCache cache = new DiskBasedCache(cacheDir);
        mRequestQueue.start();

        // clear all volley caches.
        mRequestQueue.add(new ClearCacheRequest(cache, null));
        return mRequestQueue;
    }

    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        timer1.cancel();
        timer2.cancel();
        Log.i("tag", "ApplicationService onDestroy...");
    }
}
