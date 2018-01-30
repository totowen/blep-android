package com.tos.test;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tos.blepdemo.R;
import com.tos.service.Service_getAppPackage;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by admin on 2016/3/26.
 */
public class ProgressBar_test extends Activity {


    private Button button;
//    private ImageView imageView;
    private ProgressDialog progressDialog;
//    private final String IMAGE_PATH = "http://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-331673.jpg";
    private static final String APK_PATH = "http://163.177.158.81/down.myapp.com/myapp/smart_ajax/com.tencent.android.qqdownloader/996390_19215087_1410453248838.apk?mkey=5456c61b9ed1ea89&f=2534&p=.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress);
        button = (Button)findViewById(R.id.button);
//        imageView = (ImageView)findViewById(R.id.imageView);
        //    弹出要给ProgressDialog
        progressDialog = new ProgressDialog(ProgressBar_test.this);
        progressDialog.setTitle("提示下载");
        progressDialog.setMessage("正在下载中，请稍后。。。。");
        //    设置setCancelable(false); 表示我们不能取消这个弹出框，等下载完成之后再让弹出框消失
        progressDialog.setCancelable(false);
        //    设置ProgressDialog样式为水平的样式
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new MyAsyncTask().execute(APK_PATH);
                Intent intent = new Intent(ProgressBar_test.this, Service_getAppPackage.class);
                startService(intent);
            }
        });
    }

    /**
     * 定义一个类，让其继承AsyncTask这个类
     * Params: String类型，表示传递给异步任务的参数类型是String，通常指定的是URL路径
     * Progress: Integer类型，进度条的单位通常都是Integer类型
     * Result：byte[]类型，表示我们下载好的图片以字节数组返回
     *
     */
    public class MyAsyncTask extends AsyncTask<String, Integer, File>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            //    在onPreExecute()中我们让ProgressDialog显示出来
            progressDialog.show();
        }
        @Override
        protected File doInBackground(String... params)
        {
            //建立本地文件夹
            final String fileName = "updata.apk";
            File tmpFile = new File("/sdcard/update");
            if (!tmpFile.exists()) {
                tmpFile.mkdir();
            }
            final File file = new File("/sdcard/update/" + fileName);

            //    通过Apache的HttpClient来访问请求网络中的一张图片
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(params[0]);
//            byte[] image = new byte[]{};
            try
            {
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                InputStream inputStream = null;
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                FileOutputStream fos = new FileOutputStream(file);
                if(httpEntity != null && httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                {
                    //    得到文件的总长度
                    long file_length = httpEntity.getContentLength();
                    //    每次读取后累加的长度
                    long total_length = 0;
                    int length = 0;
                    //    每次读取1024个字节
                    byte[] data = new byte[1024*4];
                    inputStream = httpEntity.getContent();
                    while(-1 != (length = inputStream.read(data)))
                    {
                        //    每读一次，就将total_length累加起来
                        total_length += length;
                        //    边读边写到FileOutputStream当中
                        fos.write(data, 0, length);
                        //    边读边写到ByteArrayOutputStream当中
//                        byteArrayOutputStream.write(data, 0, length);
                        //    得到当前图片下载的进度
                        Log.e("tag",total_length+"    "+file_length);
                        float progress = (total_length/(float)file_length)* 100;
                        int pro = (int)Math.floor(progress);
                        //    时刻将当前进度更新给onProgressUpdate方法
                        Log.e("tag", pro + "");
                        publishProgress(pro);
                    }
                }
//                image = byteArrayOutputStream.toByteArray();
                inputStream.close();
                fos.close();
//                byteArrayOutputStream.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                httpClient.getConnectionManager().shutdown();
            }
            return file;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
            //    更新ProgressDialog的进度条
            progressDialog.setProgress(values[0]);
        }
        @Override
        protected void onPostExecute(File result)
        {
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


}