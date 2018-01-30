package com.tos.blepdemo;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tos.Application.MyApplication;

public class DialogActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mip;
    private Button mset;
    private MyApplication application;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        init();

    }

    private void init() {
        application = (MyApplication) getApplication();
        preferences = getSharedPreferences("test",MODE_PRIVATE);

        mip = (TextView) findViewById(R.id.ip);
        mip.setText(preferences.getString("IP_ADDRESS",""));
        mset = (Button) findViewById(R.id.set_ip);
        mset.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.set_ip){
            CustomDialog.Builder builder = new CustomDialog.Builder(this);
            final EditText et = new EditText(this);
            et.setHint("请输入IP地址和端口");
            et.setSingleLine(false);
            et.setTextColor(Color.BLACK);

            builder.setTitle("设置网址");
            builder.setContentView(et);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    //设置你的操作事项
                    String getEt = et.getText().toString().trim();
                    if (!"".equals(getEt)){
                        mip.setText(getEt);
                        application = (MyApplication) getApplication();
                        preferences = getSharedPreferences("test",MODE_PRIVATE);
                        //实例化SharedPreferences.Editor对象（第二步）
                        SharedPreferences.Editor editor = preferences.edit();
                        //用putString的方法保存数据
                        editor.putString("IP_ADDRESS",getEt);
                        //提交当前数据
                        editor.apply();

                        application.setIp_address(getEt);
                        application.setUrl(application.getIp_address());
                        application.setUrl_half(application.getIp_address());
                        application.setUrl_app_instances(application.getIp_address());
                        application.setUrl_add_app_run_record(application.getIp_address());
                        application.setUrl_app_runconfig_and_record(application.getIp_address());
                        application.setUrl_get_app_pwd(application.getIp_address());
                        application.setUrl_half(application.getIp_address());
                        application.setUrl_serial_port(application.getIp_address());
                        application.setUrl_switch(application.getIp_address());
                        application.setUrl_update(application.getIp_address());
                        application.setUrl_get_lock_pwd(application.getIp_address());
                        Toast.makeText(DialogActivity.this,""+application.getIp_address(), Toast.LENGTH_LONG).show();
                    }
                }
            });

            builder.setNegativeButton("取消",
                    new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            builder.create().show();
        }
    }
}
