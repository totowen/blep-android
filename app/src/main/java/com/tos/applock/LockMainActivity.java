package com.tos.applock;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tos.Application.MyApplication;
import com.tos.applock.dao.AppLockDBDao;
import com.tos.applock.domain.AppInfo;
import com.tos.applock.utils.AppInfoProvider;
import com.tos.applock.utils.MD5Utils;
import com.tos.applock.utils.ServiceUtils;
import com.tos.blepdemo.App_list_new;
import com.tos.blepdemo.R;
import com.tos.service.WatchDogService;
import com.tos.update_version.NetworkUtil;
import com.tos.util.MacUtil;
import com.tos.util.RestTemplateUtil;

import java.util.ArrayList;
import java.util.List;



public class LockMainActivity extends Activity {
	private SharedPreferences sp;
	private AppInfo appInfo;
	private ListView lv_app;
	private LinearLayout ll_proBar;//加载页面布局
	private List<AppInfo> appInfos;//所有应用
	private List<AppInfo> userAppInfos;//用户应用
	private Button bt_setting;//设置锁屏开启和关闭
	private List<AppInfo> systemAppInfos;//系统应用
	private TextView tv_status;//显示应用类别
	private MyAdapter adapter;
	private AppLockDBDao dao;//被锁屏的应用信息数据库
	private static String TAG = "MainActivity";
	private Button change_password;
	private MyApplication application;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		application = (MyApplication) getApplication();
		setContentView(R.layout.lock_main);
		dao=new AppLockDBDao(this);

		change_password=(Button)findViewById(R.id.change_password);
		bt_setting=(Button) findViewById(R.id.bt_setting);

		lv_app=(ListView) findViewById(R.id.lv_app);
		ll_proBar=(LinearLayout) findViewById(R.id.ll_probar);
		tv_status=(TextView) findViewById(R.id.tv_status);
		final Intent intent=new Intent(this,WatchDogService.class);
		sp=getSharedPreferences("config", MODE_PRIVATE);
		boolean b = NetworkUtil.checkedNetWork(this);

		//判断网络连接情况
		if (b){
			Log.e("App_list_new", "onCreate1: ");
			if(!isPaswordSet()){ //如果设置了就初始化本地密码

				Log.e("App_list_new", "onCreate2: "+application.getUrl_get_lock_pwd());
				//网络正常则获取密码初始化接口
				new Thread(
						new Runnable() {
							@Override
							public void run() {
								String pwd = RestTemplateUtil.getPwd(application.getUrl_get_lock_pwd() + "/" + MacUtil.getMacAddress() + "/" + "testpassworddonttouse"+"/"+0);

								Log.e("App_list_new", "onCreate3: "+pwd );

								if (null != pwd && "1".equals(pwd)){ //密码正确不进行初始化

								}else if(null == pwd || "".equals(pwd)){


								}else{
									//初始化密码
									SharedPreferences.Editor editor = sp.edit();
									editor.putString("applockpassword",pwd);
									editor.commit();
								}
							}
						}
				).start();
			}
		}




		change_password.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(LockMainActivity.this, ChangePassage.class);
				startActivity(intent);
			}
		});

		bt_setting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (bt_setting.getText().equals("开启")) {
					startService(intent);//开启看门狗服务

					bt_setting.setText("关闭");
				} else {
					stopService(intent);//关闭看门狗服务

					bt_setting.setText("开启");
				}

			}
		});

		//填充数据
		fillData();

		lv_app.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				if(position == 0 || position == userAppInfos.size()+1){ //如果是"用户程序" 或者 "系统程序" 的小标签则直接返回
					return;
				}else if(position <= userAppInfos.size()){
					int newPosition = position - 1;
					appInfo = userAppInfos.get(newPosition);
				}else{
					int newPosition = position-1-userAppInfos.size()-1;
					appInfo = systemAppInfos.get(newPosition);
				}
				ViewHolder holder=(ViewHolder) view.getTag();//获取缓存
				ImageView iv_applock=holder.iv_applock;


				if(dao.find(appInfo.getPackName())){
						dao.delete(appInfo.getPackName());
						iv_applock.setImageResource(R.drawable.unlock);
					} else {
						dao.add(appInfo.getPackName());
						iv_applock.setImageResource(R.drawable.lock);
					}

			}

		});
		lv_app.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
								 int visibleItemCount, int totalItemCount) {
				if(userAppInfos!=null&&systemAppInfos!=null){//解决空指针问题就是在前面加上条件语句，判断其不等于空
					if(firstVisibleItem>userAppInfos.size()){
						tv_status.setText("系统应用"+systemAppInfos.size()+"个");
						tv_status.setBackgroundColor(getResources().getColor(R.color.bac));
						tv_status.setVisibility(View.VISIBLE);
					}else{
						tv_status.setText("用户应用"+userAppInfos.size()+"个");
						tv_status.setBackgroundColor(getResources().getColor(R.color.bac));
						tv_status.setVisibility(View.VISIBLE);
					}}
			}
		});
	}


	private void fillData() {
		new Thread(){
			public void run(){
				//获取系统中所有的应用信息
				appInfos=AppInfoProvider.getAppInfos(LockMainActivity.this);
				userAppInfos=new ArrayList<AppInfo>();
				systemAppInfos=new ArrayList<AppInfo>();
				for(AppInfo info:appInfos){
					if(info.isUserApp()){
						userAppInfos.add(info);
					}else{
						systemAppInfos.add(info);
					}
				}
				//创建UI线程进行更新UI界面
				runOnUiThread(new Runnable(){

					@Override
					public void run() {
						//关闭加载页面
						ll_proBar.setVisibility(View.INVISIBLE);
						//显示listView
						if(adapter==null){
							adapter=new MyAdapter();
							lv_app.setAdapter(adapter);
						}else{
							adapter.notifyDataSetChanged();//刷新
						}
					}});

			}
		}.start();
	}

	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return appInfos.size()+2;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			ViewHolder holder;
			if(position==0){
				TextView textView=new TextView(LockMainActivity.this);
				textView.setText("用户应用"+userAppInfos.size()+"个");
				textView.setTextColor(Color.WHITE);
				textView.setBackgroundColor(getResources().getColor(R.color.bac));
				return textView;
			}
			if(position==userAppInfos.size()+1){
				TextView textView=new TextView(LockMainActivity.this);
				textView.setText("系统应用"+systemAppInfos.size()+"个");
				textView.setTextColor(Color.WHITE);
				textView.setBackgroundColor(getResources().getColor(R.color.bac));
				return textView;
			}
			//单行ListView 设置布局界面
			if(convertView!=null&&convertView instanceof RelativeLayout){
				view=convertView;
				holder=(ViewHolder) view.getTag();
			}else{
				//获取单行listView的布局
				view =View.inflate(LockMainActivity.this, R.layout.list_item_applock, null);
				holder=new ViewHolder();
				holder.iv_app_icon=(ImageView) view.findViewById(R.id.iv_app_icon);
				holder.tv_app_name=(TextView) view.findViewById(R.id.tv_app_name);
				holder.tv_app_location=(TextView) view.findViewById(R.id.tv_app_location);
				holder.iv_applock=(ImageView) view.findViewById(R.id.iv_applock);
				//将单行布局是指给ListView
				view.setTag(holder);
			}
			if(position<userAppInfos.size()+1){
				holder.iv_app_icon.setImageDrawable(userAppInfos.get(position-1).getAppIcon());
				holder.tv_app_name.setText(userAppInfos.get(position-1).getName());
				if(userAppInfos.get(position-1).isInRom()){
					holder.tv_app_location.setText("手机内存");
				}else{
					holder.tv_app_location.setText("外部存储");
				}
				if(dao.find(userAppInfos.get(position-1).getPackName())){
					holder.iv_applock.setImageResource(R.drawable.lock);
				}else{
					holder.iv_applock.setImageResource(R.drawable.unlock);
				}
			}
			if(position>userAppInfos.size()+1){
				holder.iv_app_icon.setImageDrawable(systemAppInfos.get(position-userAppInfos.size()-2).getAppIcon());
				holder.tv_app_name.setText(systemAppInfos.get(position-userAppInfos.size()-2).getName());
				if(systemAppInfos.get(position-userAppInfos.size()-2).isInRom()){
					holder.tv_app_location.setText("手机内存");
				}else{
					holder.tv_app_location.setText("外部存储");
				}
				if(dao.find(systemAppInfos.get(position-userAppInfos.size()-2).getPackName())){
					holder.iv_applock.setImageResource(R.drawable.lock);
				}else{
					holder.iv_applock.setImageResource(R.drawable.unlock);
				}
			}

			return view;
		}

	}
	//缓存类
	static class ViewHolder{
		ImageView iv_app_icon;
		TextView tv_app_name;
		TextView tv_app_location;
		ImageView iv_applock;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}
	protected void showPasswordInputDialog() {
		View view =View.inflate(LockMainActivity.this, R.layout.dialog_password_input, null);
		AlertDialog.Builder builder=new AlertDialog.Builder(LockMainActivity.this);
		et_password=(EditText) view.findViewById(R.id.et_password);
		bt_ok=(Button) view.findViewById(R.id.ok);
		bt_cancle=(Button) view.findViewById(R.id.cancle);
		dialog=builder.create();
		dialog.setCancelable(false);
		bt_cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				finish();
			}
		});
		bt_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String password=sp.getString("applockpassword", "");
				if(password.equals(MD5Utils.md5Encode(et_password.getText().toString().trim()))){
					dialog.dismiss();
				}else{
					Toast.makeText(LockMainActivity.this, "密码输入错误", Toast.LENGTH_SHORT).show();
					et_password.setText("");
				}
			}
		});
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();

	}
	AlertDialog dialog;
	EditText et_password;
	EditText et_password_confirm;
	Button bt_ok;
	Button bt_cancle;
	protected void showPasswordSetDialog() {
		View view =View.inflate(LockMainActivity.this, R.layout.dialog_password_set, null);
		AlertDialog.Builder builder=new AlertDialog.Builder(LockMainActivity.this);
		et_password=(EditText) view.findViewById(R.id.et_password);
		et_password_confirm=(EditText) view.findViewById(R.id.et_password_confirm);
		bt_ok=(Button) view.findViewById(R.id.ok);
		bt_cancle=(Button) view.findViewById(R.id.cancle);

		bt_cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				finish();
			}
		});
		bt_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String password=et_password.getText().toString().trim();
				String password_confirm=et_password_confirm.getText().toString().trim();
				if(TextUtils.isEmpty(password)||TextUtils.isEmpty(password_confirm)){
					Toast.makeText(LockMainActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				if(password.equals(password_confirm)){
					SharedPreferences.Editor editor=sp.edit();
					editor.putString("applockpassword", MD5Utils.md5Encode(password));
					editor.commit();
					dialog.dismiss();
					Toast.makeText(LockMainActivity.this, "密码设置成功", Toast.LENGTH_SHORT).show();
					boolean b = NetworkUtil.checkedNetWork(LockMainActivity.this);
					//判断网络连接情况
					if (b){
						new Thread(new Runnable() {
							@Override
							public void run() {
								//网络正常则获取密码初始化接口
								RestTemplateUtil.getPwd(application.getUrl_get_lock_pwd()+"/"+ MacUtil.getMacAddress()+"/"+password+"/"+0);
							}
						}).start();
					}
				}else{
					Toast.makeText(LockMainActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
					et_password.setText("");
					et_password_confirm.setText("");

				}

			}
		});
		dialog=builder.create();
		dialog.setCancelable(false);
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();

	}
	protected boolean isPaswordSet() {
		String password=sp.getString("applockpassword", "");
		return TextUtils.isEmpty(password);
	}
	@Override
	protected void onStart() {
		if(isPaswordSet()){
			//进入密码设置对话框
			showPasswordSetDialog();
		}else{
			//进入密码输入对话框
			showPasswordInputDialog();
		}
		if(ServiceUtils.isRunningService(this, "WatchDogService")){

			bt_setting.setText("关闭");
		}else{
			bt_setting.setText("开启");
		}
		super.onStart();
	}

}