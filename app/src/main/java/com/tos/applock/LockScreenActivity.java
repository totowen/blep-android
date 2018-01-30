package com.tos.applock;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.tos.blepdemo.R;

import java.util.Calendar;

public class LockScreenActivity extends Activity {
	private EditText et_password;
	private SharedPreferences sp;
	private String packName;
	private Calendar c = Calendar.getInstance();
	private int hour;
	@Override
	protected void onCreate(Bundle savedInstanceState) {


		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp=getSharedPreferences("config", MODE_PRIVATE);
		setContentView(R.layout.activity_lock_screen);
		
		et_password=(EditText) findViewById(R.id.et_password);
		
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
//		<action android:name="android.intent.action.MAIN" />
//        <category android:name="android.intent.category.HOME" />
//        <category android:name="android.intent.category.DEFAULT" />
//        <category android:name="android.intent.category.MONKEY"/>
		Intent intent=new Intent();
		intent.setAction("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.HOME");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addCategory("android.intent.category.MONKEY");
		startActivity(intent);
	}
	public void click(View view){

//			packName = getIntent().getStringExtra("packName");
//			String password = et_password.getText().toString().trim();
//
//			if (TextUtils.isEmpty(password)) {
//				Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
//				return;
//			}
//			String lockapppassword = sp.getString("applockpassword", "");
//			if (MD5Utils.md5Encode(password).equals(lockapppassword)) {
//				Intent intent = new Intent();
//				intent.setAction("com.xiong.applock");
//				intent.putExtra("packName", packName);
//				sendBroadcast(intent);
//				finish();
//			} else {
//				Toast.makeText(this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
//				et_password.setText("");
//				return;
//			}

		Intent home = new Intent(Intent.ACTION_MAIN);
		home.addCategory(Intent.CATEGORY_HOME);
		startActivity(home);
		this.finish();
		}

	@Override
	protected void onDestroy() {
		this.finish();
		super.onDestroy();
	}
}
