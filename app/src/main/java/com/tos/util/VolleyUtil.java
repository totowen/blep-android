package com.tos.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by alienware on 2016/5/25.
 */
public class VolleyUtil {




    //获取Json字符串
    public static void getJSONVolley(String JSONDateUrl, Context context,RequestQueue requestQueue){
        final JSONObject[] json = {null};
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSONDateUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.e("response:",jsonObject+"");
                        json[0] = jsonObject;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("response","对不起，有问题");
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }


    //获取Json字符串
    public static void getPassword(String JSONDateUrl, Context context,final SharedPreferences sp,RequestQueue requestQueue){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSONDateUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        String pwd = "";
                        try {
                            pwd = jsonObject.getString("hint");
                            Log.e("VolleyUtil:",pwd);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (null != pwd && "1".equals(pwd)){ //密码正确不进行初始化

                        }else if(null == pwd || "".equals(pwd)){


                        }else{
                            //初始化密码
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("applockpassword2",pwd);
                            editor.commit();
                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("VolleyUtil","对不起，有问题");
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }


}
