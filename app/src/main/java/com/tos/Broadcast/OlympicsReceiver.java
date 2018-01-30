package com.tos.Broadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.tos.service.ApplicationService;
import com.tos.service.WatchDogService;


/**
 * 开机自启服务广播
 * Created by admin on 2016/3/18.
 */
public class OlympicsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context,
                ApplicationService.class));//启动倒计时服务

        context.startService(new Intent(context,WatchDogService.class));//启动看门狗服务

//        Toast.makeText(context, "系统服务已启动", Toast.LENGTH_LONG).show();
        Log.e("xitongfuwuyiqidong", "onReceive: ");
//        Log.e("tagger","ApplicationService service has started!");
        //这边可以添加开机自动启动的应用程序代码
       /*intent = context.getPackageManager().getLaunchIntentForPackage("com.tos.blepphone");
        context.startActivity(intent);*/
        //开机后开启alarmManager管理器进行维护ApplicationService
        Intent intent2 = new Intent(context, ApplicationService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP , System.currentTimeMillis(),15*1000, pendingIntent);

        Intent intent3 = new Intent(context, WatchDogService.class);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, 0, intent3, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager2 = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager2.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP , System.currentTimeMillis(),15*1000, pendingIntent2);
    }
}
