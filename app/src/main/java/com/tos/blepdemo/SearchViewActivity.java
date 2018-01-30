package com.tos.blepdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.tos.Application.MyApplication;
import com.tos.Dto.AppPackageDto;
import com.tos.applock.domain.EventAppInfo;
import com.tos.pojo.App_package;
import com.tos.util.RestTemplateUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.springframework.http.HttpMethod;

import static org.greenrobot.eventbus.EventBus.TAG;

public class SearchViewActivity extends Activity {

    private MyApplication application;

    private SearchView searchView;

    private Integer flag = 0;

    private String app_serach_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);
        application = (MyApplication) getApplication();
        EventBus.getDefault().register(this);

        Log.e(TAG, "event----------> 创建");

        searchView = (SearchView) findViewById(R.id.searchView);

        if(flag==1){
            searchView.startSearch();
            getSingleAppRest();
        }
    }

    private void getSingleAppRest() {
        new Thread(){
            @Override
            public void run() {
                if(null!=app_serach_name&&!"".equals(app_serach_name)){
                    Log.e(TAG, "event---------->"+ app_serach_name);
                    String singleAppPackageDto = null;
                   try{
                       Thread.sleep(5000);
                       Log.e(TAG, "event---------->"+ application.getUrl_single_app_search());
                       singleAppPackageDto = RestTemplateUtil.getSingleAppPackageDto(application.getUrl_single_app_search(), HttpMethod.POST, app_serach_name);

                   }catch (Exception e){
                       //搜索失败，网络异常请坚持网络
                       intentAppListNew();
                   }

                    if (null!=singleAppPackageDto&&!"".equals(singleAppPackageDto)){
                        AppPackageDto app = null;
                        try{
                            app = JSON.parseObject(singleAppPackageDto, AppPackageDto.class);
                        }catch (Exception e) {
                            //跳转到应用市场，搜索失败
                            intentAppListNew();
                        }
                        if(null!=app){

                            String name = app.getName();

                            if(!"404".equals(name)){
                                //搜索成功，跳转到AppInfo.class
                                //将数据传输给详情Activity

                                App_package appPackage = new App_package();
                                appPackage.setId(app.getId());
                                appPackage.setName(app.getName());
                                appPackage.setDeveloper(app.getDeveloper());
                                appPackage.setDescription(app.getDescription());
                                appPackage.setCreate_time(app.getCreateTime().toString());
                                appPackage.setStar(app.getStar());
                                appPackage.setAttachmentIds(app.getAttachmentIds());
                                appPackage.setAttachmentId(app.getAttachmentId());
                                appPackage.setAttachmentApkId(app.getAttachmentApkId());
                                appPackage.setAppPackageName(app.getAppPackageName());

                                Intent intent = new Intent(SearchViewActivity.this, AppInfo.class);
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("appInfo",appPackage);
//                        Log.e("appInfoggg",((App_package) getItem(flag)).toString());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }else {
                                //跳转到应用市场，搜索失败
                                intentAppListNew();
                            }

                        }else{
                            //跳转到应用市场，搜索失败
                            intentAppListNew();
                        }

                    }else {
                        //跳转到应用市场，搜索失败
                        intentAppListNew();
                    }

                }

            }
        }.start();
    }

    private void intentAppListNew() {

        EventAppInfo eventAppInfo = new EventAppInfo();
        eventAppInfo.setFlag(2);
        eventAppInfo.setApp_name("搜索失败");
        EventBus.getDefault().postSticky(eventAppInfo);

        Intent intent = new Intent(SearchViewActivity.this, App_list_new.class);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) //在主线程执行
    public void onDataSynEvent(EventAppInfo event) {

        Log.e(TAG, "event---------->" + event.getFlag() +" "+event.getApp_name());

        if (event.getFlag()==1) {
            flag = event.getFlag();
            app_serach_name = event.getApp_name();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "event----------> onStop");
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
