package com.tos.applock;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tos.Application.MyApplication;
import com.tos.applock.utils.MD5Utils;
import com.tos.blepdemo.R;
import com.tos.update_version.NetworkUtil;
import com.tos.util.MacUtil;
import com.tos.util.RestTemplateUtil;

/**
 * Created by admin on 2016/3/12.
 */
public class ChangePassage extends Activity{

    private SharedPreferences sp;
    private Button changePasswordOK;
    private Button changePasswordCancel;
    EditText changeFirstText;
    EditText changeSecondText1;
    EditText changeSecondText2;
    MyApplication application;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        sp=getSharedPreferences("config", MODE_PRIVATE);
        application = (MyApplication) getApplication();
        changePasswordOK=(Button)findViewById(R.id.change_password_ok);
        changePasswordCancel=(Button)findViewById(R.id.change_password_cancel);
        changeFirstText=(EditText)findViewById(R.id.change_firstText);
        changeSecondText1=(EditText)findViewById(R.id.change_secondText1);
        changeSecondText2=(EditText)findViewById(R.id.change_secondText2);

        changePasswordCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        changePasswordOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

               String password_old=changeFirstText.getText().toString().trim();
               final String password_new=changeSecondText1.getText().toString().trim();
                String password_confirm_new=changeSecondText2.getText().toString().trim();
                if(TextUtils.isEmpty(password_new)||TextUtils.isEmpty(password_confirm_new)||TextUtils.isEmpty(password_old)){
                    Toast.makeText(ChangePassage.this, "输入密码为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String lockapppassword=sp.getString("applockpassword", "");
                if(password_new.equals(password_confirm_new)&&MD5Utils.md5Encode(password_old).equals(lockapppassword)){
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("applockpassword", MD5Utils.md5Encode(password_new));
                    editor.commit();
                    boolean b = NetworkUtil.checkedNetWork(ChangePassage.this);
                    //判断网络连接情况
                    if (b){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //网络正常则获取密码初始化接口

                                Log.e("ChangePassage", "run: "+ application.getUrl_get_lock_pwd());
                                RestTemplateUtil.getPwd(application.getUrl_get_lock_pwd()+"/"+ MacUtil.getMacAddress()+"/"+password_new+"/"+1);
                            }
                        }).start();
                    }
                    Toast.makeText(ChangePassage.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                    finish();

                }
                else if(!MD5Utils.md5Encode(password_old).equals(lockapppassword)&&password_new.equals(password_confirm_new))
                {
                    Toast.makeText(ChangePassage.this, "原密码错误", Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(ChangePassage.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                    changeSecondText1.setText("");
                    changeSecondText2.setText("");

                }
            }
        });
    }
}
