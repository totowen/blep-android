package com.tos.blepdemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.tos.Application.MyApplication;
import com.tos.pojo.App_package;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class AppInfo extends Activity {
    Button mbtnBack, mbtn_setup;
    ImageView miv_appIcon, miv_1, miv_2;
    TextView mtv_appName, mtv_developer, mtv_desc;
    Handler mHandler;
    Bitmap mbitmap0, mbitmap1, mbitmap2;
    RatingBar mratingBar;
    MyApplication application;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_info);
        application = (MyApplication) getApplication();
        initView();
        getParcelable();
        action();

    }

    private void action() {
        //返回
        mbtnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //获取链接中的图片
        new Thread() {
            @Override
            public void run() {
                super.run();
                try{
                    String attachmentId = mApp_package.getAttachmentId();
                    if (!"".equals(attachmentId) && null != attachmentId) {
//                        String tag = attachmentId.replace("\\", "/");
                        mbitmap0 = getBitMap(application.getUrl_half() + attachmentId);
                    }
                    Message msg = new Message();
                    msg.what = 0;
                    mHandler.sendMessage(msg);//向主线程发送消息
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                super.run();
                List<String> attachmentIds = mApp_package.getAttachmentIds();
                if (null!= attachmentIds && attachmentIds.size()>0){
                    String attachmentId_1 = attachmentIds.get(attachmentIds.size()-1);
                    if (!"".equals(attachmentId_1) && null != attachmentId_1) {
//                        String tag = attachmentId_1.replace("\\", "/");
                        mbitmap1 = getBitMap(application.getUrl_half() + attachmentId_1);

                    }
                    Message msg = new Message();
                    msg.what = 1;
                    mHandler.sendMessage(msg);//向主线程发送消息
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                super.run();
                List<String> attachmentIds = mApp_package.getAttachmentIds();
                if (null != attachmentIds && attachmentIds.size() > 1) {
                    String attachmentId_2 = attachmentIds.get(attachmentIds.size()-2);
                    if (!"".equals(attachmentId_2) && null != attachmentId_2) {
//                        String tag = attachmentId_2.replace("\\", "/");
                        mbitmap2 = getBitMap(application.getUrl_half() + attachmentId_2);
                    }
                    Message msg = new Message();
                    msg.what = 2;
                    mHandler.sendMessage(msg);//向主线程发送消息
                }
            }
        }.start();

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0 && null!=mbitmap0) {
                    miv_appIcon.setImageBitmap(mbitmap0);
                }
                if (msg.what == 1 && null!=mbitmap1) {
                    miv_1.setImageBitmap(mbitmap1);
                }
                if (msg.what == 2 && null!=mbitmap2) {
                    miv_2.setImageBitmap(mbitmap2);
                }
            }
        };
        //安装按钮
        mbtn_setup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String attachmentApkId = mApp_package.getAttachmentApkId();
                if (!"".equals(attachmentApkId) && null != attachmentApkId) {
//                    String tag = attachmentApkId.replace("\\", "/");
//                    Log.e("attachmentApkId....", attachmentApkId);
                    new MyAsyncTask().execute(application.getUrl_half()+attachmentApkId);
                }else {
                    Toast.makeText(AppInfo.this,"无法获取安装包信息", Toast.LENGTH_LONG).show();
                }
            }
        });
        //app名字
        mtv_appName.setText(mApp_package.getName());
        //开发商
        mtv_developer.setText(mApp_package.getDeveloper());
        //App描述
        mtv_desc.setText(mApp_package.getDescription());
        //星级
        mratingBar.setNumStars(4);
    }

    public void initView() {
        mbtnBack = (Button) findViewById(R.id.btn_back);
        miv_appIcon = (ImageView) findViewById(R.id.iv_appIcon);
        mbtn_setup = (Button) findViewById(R.id.btn_setup);
        mratingBar = (RatingBar) findViewById(R.id.app_ratingbar);
        miv_1 = (ImageView) findViewById(R.id.iv_1);
        miv_2 = (ImageView) findViewById(R.id.iv_2);
        mtv_appName = (TextView) findViewById(R.id.tv_appName);
        mtv_developer = (TextView) findViewById(R.id.tv_developer);
        mtv_desc = (TextView) findViewById(R.id.tv_desc);
        //    弹出要给ProgressDialog
        progressDialog = new ProgressDialog(AppInfo.this);
        progressDialog.setTitle("提示下载");
        progressDialog.setMessage("正在下载中，请稍后。。。。");
        //    设置setCancelable(false); 表示我们不能取消这个弹出框，等下载完成之后再让弹出框消失
        progressDialog.setCancelable(false);
        //    设置ProgressDialog样式为水平的样式
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

    }

    App_package mApp_package;

    private void getParcelable() {
        Intent intent = this.getIntent();
        mApp_package = (App_package) intent.getParcelableExtra("appInfo");
        Log.e("tag", mApp_package + "");
    }

    /**
     * 定义一个类，让其继承AsyncTask这个类
     * Params: String类型，表示传递给异步任务的参数类型是String，通常指定的是URL路径
     * Progress: Integer类型，进度条的单位通常都是Integer类型
     * Result：byte[]类型，表示我们下载好的图片以字节数组返回
     */
    private ProgressDialog progressDialog;
    //    private final String IMAGE_PATH = "http://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-331673.jpg";
//    private static final String APK_PATH = "http://163.177.158.81/down.myapp.com/myapp/smart_ajax/com.tencent.android.qqdownloader/996390_19215087_1410453248838.apk?mkey=5456c61b9ed1ea89&f=2534&p=.apk";

    public class MyAsyncTask extends AsyncTask<String, Integer, File> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //    在onPreExecute()中我们让ProgressDialog显示出来
            progressDialog.show();
        }

        @Override
        protected File doInBackground(String... params) {

            String path = Environment.getExternalStorageDirectory().getPath();
            //建立本地文件夹
            final String fileName = "updata.apk";
            File tmpFile = new File(path+"/update");
            if (!tmpFile.exists()) {
                tmpFile.mkdir();
            }
            final File file = new File(path+"/update/" + fileName);

            //    通过Apache的HttpClient来访问请求网络中的一张图片
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(params[0]);
//            byte[] image = new byte[]{};
            try {
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                InputStream inputStream = null;
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                FileOutputStream fos = new FileOutputStream(file);
                if (httpEntity != null && httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    //    得到文件的总长度
                    long file_length = httpEntity.getContentLength();
                    //    每次读取后累加的长度
                    long total_length = 0;
                    int length = 0;
                    //    每次读取1024个字节
                    byte[] data = new byte[1024 * 2];
                    inputStream = httpEntity.getContent();
                    while (-1 != (length = inputStream.read(data))) {
                        //    每读一次，就将total_length累加起来
                        total_length += length;
                        //    边读边写到FileOutputStream当中
                        fos.write(data, 0, length);
                        //    边读边写到ByteArrayOutputStream当中
//                        byteArrayOutputStream.write(data, 0, length);
                        //    得到当前图片下载的进度
                        Log.e("tag", total_length + "    " + file_length);
                        float progress = (total_length / (float) file_length) * 100;
                        int pro = (int) Math.floor(progress);
                        //    时刻将当前进度更新给onProgressUpdate方法
                        Log.e("tag", pro + "");
                        publishProgress(pro);
                    }
                }
//                image = byteArrayOutputStream.toByteArray();
                inputStream.close();
                fos.close();
//                byteArrayOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                httpClient.getConnectionManager().shutdown();
            }
            return file;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //更新ProgressDialog的进度条
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(File result) {
            super.onPostExecute(result);
            //    将doInBackground方法返回的byte[]解码成要给Bitmap
//            Bitmap bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
            //    更新我们的ImageView控件
//            imageView.setImageBitmap(bitmap);

            //打开安装包安装
            openFile(result);
            //    使ProgressDialog框消失
            progressDialog.dismiss();
        }
    }

    //打开APK程序代码
    private void openFile(File file) {
        // TODO Auto-generated method stub
        Log.e("OpenFile", file.getName());
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }


    public Bitmap getBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}