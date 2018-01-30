package com.tos.service;

import android.app.IntentService;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.greenDao.AppRunRecord;
import com.example.greenDao.AppRunRecordDao;
import com.example.greenDao.DaoSession;
import com.tos.Application.MyApplication;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;


/**
 * Created by admin on 2016/3/10.
 */
public class Service_one extends IntentService {

    private MyApplication myApplication;
    private SQLiteDatabase db;
    private DaoSession daoSession;
    private QueryBuilder qb ;
    private AppRunRecordDao recordDao;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public Service_one() {
        super("Service_one");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("tag", "springForAndroid");
        //创建一个 HttpHeaders 对象，它表示 HTTP 请求和响应头。
        HttpHeaders requestHeaders = new HttpHeaders();
        //将主体的媒体类型设置为跟 Content-Type 头指定的一样。媒体类型应该匹配
        requestHeaders.setContentType(new MediaType("application", "json"));
        //构建json实体

        //获取全局变量的SQLiteDatabase对象和DaoSession对象
        myApplication = (MyApplication)getApplication();
        db = myApplication.getDb();
        daoSession = myApplication.getDaoSession();

        // 获取 AppRunRecordDao 对象
        recordDao = getAppRunRecordDao();
        //构建查询生成器
        qb = recordDao.queryBuilder();
        List<AppRunRecord> records= qb.list();

        String json = JSON.toJSONString(records);
        Log.i("tag",json);

        //创建一个包含请求头的 HTTP 请求实体。
        HttpEntity<String> requestEntity = new HttpEntity<String>(json,requestHeaders);
        //使用构造函数，利用默认设置，创建 RestTemplate 的一个新实例。
        RestTemplate restTemplate = new RestTemplate();
        //指定到宿主在 URI 路径 /helloworld 上的资源的 URL。
        String url = "http://10.5.9.79:8080/admin/api/pad/addAppRunRecord";
        //通过使用 exchange 方法将请求实体发送到请求，调用到指定 URI 模板的 HTTP 方法。 exchange 方法返回响应为 ResponseEntity 。
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        //使用 getBody 从 ResponseEntity 检索响应字符串。
        String result = responseEntity.getBody();
        Log.i("tag", result);
    }

    private AppRunRecordDao getAppRunRecordDao() {
        return daoSession.getAppRunRecordDao();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("tag","onDestroy...");
    }
}

