package com.tos.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

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
 * Created by admin on 2016/3/19.
 */
public class Service_getOnePackage extends IntentService {
    public Service_getOnePackage() {
        super("Service_getOnePackage");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("tag", "springForAndroid");

        //创建一个 HttpHeaders 对象，它表示 HTTP 请求和响应头。
        HttpHeaders requestHeaders = new HttpHeaders();

        //将主体的媒体类型设置为跟 Content-Type 头指定的一样。媒体类型应该匹配
        requestHeaders.setContentType(new MediaType("application", "json"));

        //创建一个包含请求头的 HTTP 请求实体。
        HttpEntity<String> requestEntity = new HttpEntity<String>(requestHeaders);

        //使用构造函数，利用默认设置，创建 RestTemplate 的一个新实例。
        RestTemplate restTemplate = new RestTemplate();

        //替换String的原有ISO-8859-1为utf-8
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
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
        String url = "http://10.5.9.25:8080/admin/api/pc/getOneAppPackageDetails/40288089538355ee015383563a430000";
        //通过使用 exchange 方法将请求实体发送到请求，调用到指定 URI 模板的 HTTP 方法。 exchange 方法返回响应为 ResponseEntity 。
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        //使用 getBody 从 ResponseEntity 检索响应字符串。
        String result = responseEntity.getBody();
        Log.i("tag", result);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("tag", "Service_getOnePackage onDestroy...");
    }
}
