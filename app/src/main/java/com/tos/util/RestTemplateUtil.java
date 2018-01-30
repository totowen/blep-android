package com.tos.util;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tos.Dto.AppInstanceDto;
import com.tos.Dto.AppPackageDto;
import com.tos.Dto.AppPackageLevelDto;
import com.tos.Dto.AppPackageTypeDto;
import com.tos.Dto.AppRunRecordDto;
import com.tos.Dto.SerialPortADto;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;


/**
 * Created by admin on 2016/3/31.
 */
public class RestTemplateUtil {

    public static String getResToPost(String URL, HttpMethod method, String type, String level){
        //Log.i("tag", "springForAndroid");
        //创建一个 HttpHeaders 对象，它表示 HTTP 请求和响应头。
        HttpHeaders requestHeaders = new HttpHeaders();
        //将主体的媒体类型设置为跟 Content-Type 头指定的一样。媒体类型应该匹配
        requestHeaders.setContentType(new MediaType("application", "json"));
        //构建json实体
        AppPackageDto appPackageDto = new AppPackageDto();
        AppPackageTypeDto typeDto = new AppPackageTypeDto();
        typeDto.setName(type);
        AppPackageLevelDto levelDto = new AppPackageLevelDto();
        levelDto.setName(level);
        appPackageDto.setTypeDto(typeDto);
        appPackageDto.setLevelDto(levelDto);
        String json = JSON.toJSONString(appPackageDto);
        //创建一个包含请求头的 HTTP 请求实体。
        HttpEntity<String> requestEntity = new HttpEntity<String>(json, requestHeaders);
        //使用构造函数，利用默认设置，创建 RestTemplate 的一个新实例。
        RestTemplate restTemplate = new RestTemplate();

        //将String的编码格式改为UTF_8
        setEncoding(restTemplate);

        //通过使用 exchange 方法将请求实体发送到请求，调用到指定 URI 模板的 HTTP 方法。 exchange 方法返回响应为 ResponseEntity 。
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(URL,method, requestEntity, String.class);
        //使用 getBody 从 ResponseEntity 检索响应字符串。
        String result = responseEntity.getBody();
        return result;
    }


    public static String getSingleAppPackageDto(String URL, HttpMethod method,String app_name){
        //Log.i("tag", "springForAndroid");
        //创建一个 HttpHeaders 对象，它表示 HTTP 请求和响应头。
        HttpHeaders requestHeaders = new HttpHeaders();
        //将主体的媒体类型设置为跟 Content-Type 头指定的一样。媒体类型应该匹配
        requestHeaders.setContentType(new MediaType("application", "json"));
        //构建json实体
        AppPackageDto appPackageDto = new AppPackageDto();
        appPackageDto.setName(app_name);
        String json = JSON.toJSONString(appPackageDto);
        //创建一个包含请求头的 HTTP 请求实体。
        HttpEntity<String> requestEntity = new HttpEntity<String>(json, requestHeaders);
        //使用构造函数，利用默认设置，创建 RestTemplate 的一个新实例。
        RestTemplate restTemplate = new RestTemplate();

        //将String的编码格式改为UTF_8
        setEncoding(restTemplate);

        //通过使用 exchange 方法将请求实体发送到请求，调用到指定 URI 模板的 HTTP 方法。 exchange 方法返回响应为 ResponseEntity 。
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(URL,method, requestEntity, String.class);
        //使用 getBody 从 ResponseEntity 检索响应字符串。
        String result = responseEntity.getBody();
        return result;
    }

    private static void setEncoding(RestTemplate restTemplate) {
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
    }

    /**
     * 获取允许app运行时间段
     * @param URL
     * @param method
     * @return
     */

    public static String getResToGet(String URL, HttpMethod method){
        //Log.i("tag", "springForAndroid");
        //创建一个 HttpHeaders 对象，它表示 HTTP 请求和响应头。
        HttpHeaders requestHeaders = new HttpHeaders();
        //将主体的媒体类型设置为跟 Content-Type 头指定的一样。媒体类型应该匹配
        requestHeaders.setContentType(new MediaType("application", "json"));

        //创建一个包含请求头的 HTTP 请求实体。
        HttpEntity<String> requestEntity = new HttpEntity<String>(requestHeaders);
        //使用构造函数，利用默认设置，创建 RestTemplate 的一个新实例。
        RestTemplate restTemplate = new RestTemplate();

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

        //通过使用 exchange 方法将请求实体发送到请求，调用到指定 URI 模板的 HTTP 方法。 exchange 方法返回响应为 ResponseEntity 。
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(URL,method, requestEntity, String.class);
        //使用 getBody 从 ResponseEntity 检索响应字符串。
        String result = responseEntity.getBody();
        return result;
    }

    /**
     * 上传app运行记录
     */
    public static void uploadAppRunRecord(String URL, List<AppRunRecordDto> appRunRecordDtos) {
        //创建一个 HttpHeaders 对象，它表示 HTTP 请求和响应头。
        HttpHeaders requestHeaders = new HttpHeaders();
        //将主体的媒体类型设置为跟 Content-Type 头指定的一样。媒体类型应该匹配
        requestHeaders.setContentType(new MediaType("application", "json"));
        //构建实体
        String json = JSON.toJSONString(appRunRecordDtos);
        //创建一个包含请求头的 HTTP 请求实体。
        HttpEntity<String> requestEntity = new HttpEntity<String>(json, requestHeaders);
        //使用构造函数，利用默认设置，创建 RestTemplate 的一个新实例。
        RestTemplate restTemplate = new RestTemplate();

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

        //通过使用 exchange 方法将请求实体发送到请求，调用到指定 URI 模板的 HTTP 方法。 exchange 方法返回响应为 ResponseEntity 。
        ResponseEntity<String> responseEntity =
                null;
        String result = "";
        try {
            responseEntity = restTemplate.exchange(URL, HttpMethod.POST, requestEntity, String.class);
            result = responseEntity.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        //使用 getBody 从 ResponseEntity 检索响应字符串。
        //Log.i("tagRecord", result);

    }

    /**
     * 上传app安装实例
     */
    public static void uploadAppInstances(String URL, List<AppInstanceDto> appInstanceDtos) {
        //创建一个 HttpHeaders 对象，它表示 HTTP 请求和响应头。
        HttpHeaders requestHeaders = new HttpHeaders();
        //将主体的媒体类型设置为跟 Content-Type 头指定的一样。媒体类型应该匹配
        requestHeaders.setContentType(new MediaType("application", "json"));
        //构建实体
        String json = JSON.toJSONString(appInstanceDtos);

//        Log.e("json-appInstanceDtos", json);

        //创建一个包含请求头的 HTTP 请求实体。
        HttpEntity<String> requestEntity = new HttpEntity<String>(json, requestHeaders);
        //使用构造函数，利用默认设置，创建 RestTemplate 的一个新实例。
        RestTemplate restTemplate = new RestTemplate();

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
        String result = null;

        try {
            //通过使用 exchange 方法将请求实体发送到请求，调用到指定 URI 模板的 HTTP 方法。 exchange 方法返回响应为 ResponseEntity 。
            ResponseEntity<String> responseEntity =
                    restTemplate.exchange(URL, HttpMethod.POST, requestEntity, String.class);
            //使用 getBody 从 ResponseEntity 检索响应字符串。
            result = responseEntity.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
        }
//        Log.i("tagInstance", result);

    }

    /**
     * 上传SerialPort数据
     */
    public static void uploadSerialProtData(String URL, SerialPortADto serialPortADto) {
        //创建一个 HttpHeaders 对象，它表示 HTTP 请求和响应头。
        HttpHeaders requestHeaders = new HttpHeaders();
        //将主体的媒体类型设置为跟 Content-Type 头指定的一样。媒体类型应该匹配
        requestHeaders.setContentType(new MediaType("application", "json"));
        //构建实体
        String json = JSON.toJSONString(serialPortADto);

//        Log.e("json-appInstanceDtos", json);

        //创建一个包含请求头的 HTTP 请求实体。
        HttpEntity<String> requestEntity = new HttpEntity<String>(json, requestHeaders);
        //使用构造函数，利用默认设置，创建 RestTemplate 的一个新实例。
        RestTemplate restTemplate = new RestTemplate();

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
        String result = null;

        try {
            //通过使用 exchange 方法将请求实体发送到请求，调用到指定 URI 模板的 HTTP 方法。 exchange 方法返回响应为 ResponseEntity 。
            ResponseEntity<String> responseEntity =
                    restTemplate.exchange(URL, HttpMethod.POST, requestEntity, String.class);
            //使用 getBody 从 ResponseEntity 检索响应字符串。
            result = responseEntity.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        Log.i("tagInstance_serial", result);

    }

    /**
     * 获取允许app运行时间段
     * @param URL
     * @return
     */

    public static String getPwd(String URL){
        //Log.i("tag", "springForAndroid");
        //创建一个 HttpHeaders 对象，它表示 HTTP 请求和响应头。
        HttpHeaders requestHeaders = new HttpHeaders();
        //将主体的媒体类型设置为跟 Content-Type 头指定的一样。媒体类型应该匹配
        requestHeaders.setContentType(new MediaType("application", "json"));

        //创建一个包含请求头的 HTTP 请求实体。
        HttpEntity<String> requestEntity = new HttpEntity<String>(requestHeaders);
        //使用构造函数，利用默认设置，创建 RestTemplate 的一个新实例。
        RestTemplate restTemplate = new RestTemplate();

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

        //通过使用 exchange 方法将请求实体发送到请求，调用到指定 URI 模板的 HTTP 方法。 exchange 方法返回响应为 ResponseEntity 。
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(URL,HttpMethod.GET, requestEntity, String.class);
        //使用 getBody 从 ResponseEntity 检索响应字符串。
        String result = responseEntity.getBody();

        JSONObject jsonObject = (JSONObject) JSON.parse(result);

        String hint = (String)jsonObject.get("hint");

        Log.e("ReatTemplateUtil", hint);

        return hint;
    }

}
