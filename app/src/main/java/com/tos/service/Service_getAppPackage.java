package com.tos.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tos.Dto.AppPackageDto;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * Created by admin on 2016/3/18.
 */
public class Service_getAppPackage extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public Service_getAppPackage() {
        super("Service_getAppPackage");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("tag", "springForAndroid");
        //创建一个 HttpHeaders 对象，它表示 HTTP 请求和响应头。
        HttpHeaders requestHeaders = new HttpHeaders();
        //将主体的媒体类型设置为跟 Content-Type 头指定的一样。媒体类型应该匹配
        requestHeaders.setContentType(new MediaType("application", "json"));
        //构建json实体
        AppPackageDto appPackage = new AppPackageDto();
//        appPackage.setName("掌上图书馆");
        String json = JSON.toJSONString(appPackage);
        Log.i("tag", json);

        //创建一个包含请求头的 HTTP 请求实体。
        HttpEntity<String> requestEntity = new HttpEntity<String>(json,requestHeaders);
        //使用构造函数，利用默认设置，创建 RestTemplate 的一个新实例。
        RestTemplate restTemplate = new RestTemplate();

        List<HttpMessageConverter<?>> converterList=restTemplate.getMessageConverters();
        HttpMessageConverter<?> converterTarget = null;
        for (HttpMessageConverter<?> item : converterList) {
            if (item.getClass() == StringHttpMessageConverter.class) {
                converterTarget = item;
                break;
            }
        }

        if (converterTarget != null) {
            converterList.remove(converterTarget);
        }
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converterList.add(converter);


        //指定到宿主在 URI 路径 /helloworld 上的资源的 URL。
        String url = "http://10.5.9.79:8080/admin/api/pc/getAllAppPackage/1/99999";
        //通过使用 exchange 方法将请求实体发送到请求，调用到指定 URI 模板的 HTTP 方法。 exchange 方法返回响应为 ResponseEntity 。
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        //使用 getBody 从 ResponseEntity 检索响应字符串。
        String result = responseEntity.getBody();
        Log.i("tag", result);
        JSONObject jsonObject = JSON.parseObject(result);
        JSONArray  jsonArray= (JSONArray)jsonObject.get("data");
        List<AppPackageDto> apps = JSON.parseArray(jsonArray.toJSONString(), AppPackageDto.class);
        Log.e("tagg",apps.toString());

        for(AppPackageDto app : apps){
            Log.e("tagger",app.toString());
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("tag", "Service_getAppPackage onDestroy...");
    }
}
