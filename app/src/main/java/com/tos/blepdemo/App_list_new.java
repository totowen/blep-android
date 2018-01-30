package com.tos.blepdemo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.tos.Application.MyApplication;
import com.tos.Dto.AppPackageDto;
import com.tos.applock.domain.EventAppInfo;
import com.tos.applock.utils.MD5Utils;
import com.tos.pojo.App_package;
import com.tos.update_version.NetworkUtil;
import com.tos.update_version.UpdateStatus;
import com.tos.update_version.UpdateVersionUtil;
import com.tos.update_version.VersionInfo;
import com.tos.util.MacUtil;
import com.tos.util.RestTemplateUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;

import static org.greenrobot.eventbus.EventBus.TAG;


public class App_list_new extends FragmentActivity implements OnClickListener {

    private SearchView mSearchView;

    private SharedPreferences sp;
    private MyApplication application;
    Button mbtnBack, change_password;
    ToggleButton buttonOne;
    ToggleButton buttonTwo;
    ToggleButton buttonThree;
    ToggleButton buttonFour;

    ToggleButton button1;
    ToggleButton button2;
    ToggleButton button3;
    ToggleButton button4;
    ToggleButton button5;
    ToggleButton button6;
    ToggleButton button7;
    //数据获取条件
    private static final String GAME = "游戏";
    private static final String EDUCATION = "教育";
    private static final String MUSIC = "音乐";
    private static final String PHYSICAL = "体育";
    private static final String TOOLS = "工具";
    private static final String ENTERTAINMENT = "娱乐";
    private static final String BOOKS = "图书";

    private static final String PRESCHOOL = "学前";
    private static final String PRIMARY = "小学";
    private static final String JUNIOR = "初中";
    private static final String HIGH = "高中";
    public static final Integer GET_SINGLE_APP_FLAG = 1;
    public static final Integer GET_SINGLE_APP_FILD_FLAG = 2;


    private ArrayList<App_package> app_packages0 = null;
    private ArrayList<App_package> app_packages1 = null;
    private ArrayList<App_package> app_packages2 = null;
    private ArrayList<App_package> app_packages3 = null;
    private ArrayList<App_package> app_packages4 = null;
    private ArrayList<App_package> app_packages5 = null;
    private ArrayList<App_package> app_packages6 = null;
    private List<AppPackageDto> apps;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case 0:
                    createAppPackages();
                    try {
                        for (AppPackageDto app : apps) {
                            setAppPackages(app, "学前");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        finish();
                        Toast.makeText(App_list_new.this, "请检查网络是否正常", Toast.LENGTH_LONG).show();
                    }
                    clearAndSetAppPackages();
                    mAdapter = new MyAdapter(getSupportFragmentManager(), list);
                    mPager = (CustomViewPager) findViewById(R.id.viewpager);
                    mPager.setAdapter(mAdapter);
                    // ViewPager滑动监听器
                    mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageSelected(int position) {
                            // 当滑动时，顶部的imageView是通过animation缓慢的滑动
                            switch (position) {
                                case 0:
                                    button1.setChecked(true);
                                    button1.setClickable(false);
                                    button2.setClickable(true);
                                    button2.setChecked(false);
                                    button3.setClickable(true);
                                    button3.setChecked(false);
                                    button4.setClickable(true);
                                    button4.setChecked(false);
                                    button5.setClickable(true);
                                    button5.setChecked(false);
                                    button6.setClickable(true);
                                    button6.setChecked(false);
                                    button7.setClickable(true);
                                    button7.setChecked(false);
                                    break;
                                case 1:
                                    button1.setChecked(false);
                                    button1.setClickable(true);
                                    button2.setClickable(false);
                                    button2.setChecked(true);
                                    button3.setClickable(true);
                                    button3.setChecked(false);
                                    button4.setClickable(true);
                                    button4.setChecked(false);
                                    button5.setClickable(true);
                                    button5.setChecked(false);
                                    button6.setClickable(true);
                                    button6.setChecked(false);
                                    button7.setClickable(true);
                                    button7.setChecked(false);
                                    break;
                                case 2:
                                    button1.setChecked(false);
                                    button1.setClickable(true);
                                    button2.setClickable(true);
                                    button2.setChecked(false);
                                    button3.setClickable(false);
                                    button3.setChecked(true);
                                    button4.setClickable(true);
                                    button4.setChecked(false);
                                    button5.setClickable(true);
                                    button5.setChecked(false);
                                    button6.setClickable(true);
                                    button6.setChecked(false);
                                    button7.setClickable(true);
                                    button7.setChecked(false);
                                    break;
                                case 3:
                                    button1.setChecked(false);
                                    button1.setClickable(true);
                                    button2.setClickable(true);
                                    button2.setChecked(false);
                                    button3.setClickable(true);
                                    button3.setChecked(false);
                                    button4.setClickable(false);
                                    button4.setChecked(true);
                                    button5.setClickable(true);
                                    button5.setChecked(false);
                                    button6.setClickable(true);
                                    button6.setChecked(false);
                                    button7.setClickable(true);
                                    button7.setChecked(false);
                                    break;
                                case 4:
                                    button1.setChecked(false);
                                    button1.setClickable(true);
                                    button2.setClickable(true);
                                    button2.setChecked(false);
                                    button3.setClickable(true);
                                    button3.setChecked(false);
                                    button4.setClickable(true);
                                    button4.setChecked(false);
                                    button5.setClickable(false);
                                    button5.setChecked(true);
                                    button6.setClickable(true);
                                    button6.setChecked(false);
                                    button7.setClickable(true);
                                    button7.setChecked(false);
                                    break;
                                case 5:
                                    button1.setChecked(false);
                                    button1.setClickable(true);
                                    button2.setClickable(true);
                                    button2.setChecked(false);
                                    button3.setClickable(true);
                                    button3.setChecked(false);
                                    button4.setClickable(true);
                                    button4.setChecked(false);
                                    button5.setClickable(true);
                                    button5.setChecked(false);
                                    button6.setClickable(false);
                                    button6.setChecked(true);
                                    button7.setClickable(true);
                                    button7.setChecked(false);
                                    break;
                                case 6:
                                    button1.setChecked(false);
                                    button1.setClickable(true);
                                    button2.setClickable(true);
                                    button2.setChecked(false);
                                    button3.setClickable(true);
                                    button3.setChecked(false);
                                    button4.setClickable(true);
                                    button4.setChecked(false);
                                    button5.setClickable(true);
                                    button5.setChecked(false);
                                    button6.setClickable(true);
                                    button6.setChecked(false);
                                    button7.setClickable(false);
                                    button7.setChecked(true);
                                    break;
                            }

                        }

                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                    //downLoad(0);
                    break;
                case 1:
                    createAppPackages();
                    try {
                        for (AppPackageDto app : apps) {
                            setAppPackages(app, "小学");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        finish();
                        Toast.makeText(App_list_new.this, "请检查网络是否正常", Toast.LENGTH_LONG).show();
                    }
                    clearAndSetAppPackages();
                    mAdapter = new MyAdapter(getSupportFragmentManager(), list);
                    mPager = (CustomViewPager) findViewById(R.id.viewpager);
                    mPager.setAdapter(mAdapter);
                    //downLoad(0);
                    break;
                case 2:
                    createAppPackages();
                    try {
                        for (AppPackageDto app : apps) {
                            setAppPackages(app, "初中");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        finish();
                        Toast.makeText(App_list_new.this, "请检查网络是否正常", Toast.LENGTH_LONG).show();
                    }
                    clearAndSetAppPackages();
                    mAdapter = new MyAdapter(getSupportFragmentManager(), list);
                    mPager = (CustomViewPager) findViewById(R.id.viewpager);
                    mPager.setAdapter(mAdapter);
                    //downLoad(0);
                    break;
                case 3:
                    createAppPackages();
                    try {
                        for (AppPackageDto app : apps) {
                            setAppPackages(app, "高中");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        finish();
                        Toast.makeText(App_list_new.this, "请检查网络是否正常", Toast.LENGTH_LONG).show();
                    }
                    clearAndSetAppPackages();
                    mAdapter = new MyAdapter(getSupportFragmentManager(), list);
                    mPager = (CustomViewPager) findViewById(R.id.viewpager);
                    mPager.setAdapter(mAdapter);
                    //downLoad(0);
                    break;
            }
        }

        private void clearAndSetAppPackages() {
            //清空list
            list.clear();
//                    //7个碎片的数据
            list.add(app_packages0);
            list.add(app_packages1);
            list.add(app_packages2);
            list.add(app_packages3);
            list.add(app_packages4);
            list.add(app_packages5);
            list.add(app_packages6);
        }

        private void createAppPackages() {
            app_packages0 = new ArrayList<App_package>();
            app_packages1 = new ArrayList<App_package>();
            app_packages2 = new ArrayList<App_package>();
            app_packages3 = new ArrayList<App_package>();
            app_packages4 = new ArrayList<App_package>();
            app_packages5 = new ArrayList<App_package>();
            app_packages6 = new ArrayList<App_package>();
        }


        private void setAppPackages(AppPackageDto app, String grade) {
            App_package appPackage = new App_package();
            appPackage.setId(app.getId());
            appPackage.setName(app.getName());
            appPackage.setDeveloper(app.getDeveloper());
            appPackage.setDescription(app.getDescription());
            appPackage.setCreate_time(app.getCreateTime().toString());
            appPackage.setStar(app.getStar());
            appPackage.setType(app.getTypeDto().getName());
            appPackage.setLevel(app.getLevelDto().getName());
            appPackage.setAttachmentIds(app.getAttachmentIds());
            appPackage.setAttachmentId(app.getAttachmentId());
            appPackage.setAttachmentApkId(app.getAttachmentApkId());
            appPackage.setAppPackageName(app.getAppPackageName());
            if (EDUCATION.equals(appPackage.getType()) && grade.equals(appPackage.getLevel())) {
                app_packages0.add(appPackage);
            } else if (GAME.equals(appPackage.getType()) && grade.equals(appPackage.getLevel())) {
                app_packages1.add(appPackage);
            } else if (TOOLS.equals(appPackage.getType()) && grade.equals(appPackage.getLevel())) {
                app_packages2.add(appPackage);
            } else if (PHYSICAL.equals(appPackage.getType()) && grade.equals(appPackage.getLevel())) {
                app_packages3.add(appPackage);
            } else if (MUSIC.equals(appPackage.getType()) && grade.equals(appPackage.getLevel())) {
                app_packages4.add(appPackage);
            } else if (ENTERTAINMENT.equals(appPackage.getType()) && grade.equals(appPackage.getLevel())) {
                app_packages5.add(appPackage);
            } else if (BOOKS.equals(appPackage.getType()) && grade.equals(appPackage.getLevel())) {
                app_packages6.add(appPackage);
            }
        }
    };


    //这个是有多少个 fragment页面
    private MyAdapter mAdapter;
    private CustomViewPager mPager;
    private List<List<App_package>> list = new ArrayList<List<App_package>>();
    private List<App_package> list2;

    // 在这里你可以传 list<Fragment>  也可以传递  list<Object>数据
    public class MyAdapter extends FragmentStatePagerAdapter {
        List<List<App_package>> list_ee;

        public MyAdapter(FragmentManager fm, List<List<App_package>> ee) {
            super(fm);
            this.list_ee = ee;
        }

        @Override
        public int getCount() {
//            Log.e("count",""+list_ee.size());
            return list_ee.size();
        }

        // 初始化每个页卡选项
        @Override
        public Object instantiateItem(ViewGroup arg0, int position) {

            ArrayFragment ff = (ArrayFragment) super.instantiateItem(arg0, position);
            ff.setThings(list_ee.get(position), position);
            return ff;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            System.out.println("position Destory" + position);
            super.destroyItem(container, position, object);
        }


        @Override
        public Fragment getItem(int arg0) {
            // TODO Auto-generated method stub
            return new ArrayFragment();
        }

    }

    /**
     * 所有的  每个Fragment
     */
    public static class ArrayFragment extends ListFragment {
        private List<App_package> ee;
        private int position;
        private AppPackagesAdapter appPackagesAdapter;
        private ArrayList<App_package> app_packages;


        public void setThings(List<App_package> ee, int position) {
            this.ee = ee;
            this.position = position;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            System.out.println("onCreateView = ");
            //在这里加载每个 fragment的显示的 View
            View v = inflater.inflate(R.layout.fglayout, container, false);
            appPackagesAdapter = new AppPackagesAdapter((ArrayList<App_package>) ee, getActivity());
            setListAdapter(appPackagesAdapter);
            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            System.out.println("onActivityCreated = ");
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public void onDestroyView() {
            System.out.println("onDestroyView = " + position);
            super.onDestroyView();
        }

        @Override
        public void onDestroy() {
            System.out.println("onDestroy = " + position);
            super.onDestroy();
        }

        class AppPackagesAdapter extends BaseAdapter {
            ArrayList<App_package> apps;
            Activity context;
            ViewHolder holder = null;
            MyApplication application;

            public AppPackagesAdapter(ArrayList<App_package> apps, Activity context) {
                super();
                this.apps = apps;
                this.context = context;
                this.application = (MyApplication) getActivity().getApplication();
            }

            @Override
            public int getCount() {
                return apps.size();
            }

            @Override
            public Object getItem(int position) {
                return apps.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                //缓存
                final int flag = position;
                if (convertView == null) {
                    convertView = View.inflate(context, R.layout.application_app_item, null);
                    holder = new ViewHolder();
                    holder.ivThumb = (NetworkImageView) convertView.findViewById(R.id.application_app_icon);
                    holder.tvName = (TextView) convertView.findViewById(R.id.application_app_name);
                    holder.tvCompany = (TextView) convertView.findViewById(R.id.application_app_company);
                    holder.ivStar = (RatingBar) convertView.findViewById(R.id.application_star);
                    holder.btnDetail = (Button) convertView.findViewById(R.id.application_setup);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                App_package app = apps.get(position);
                final String attachmentId = app.getAttachmentId();
                holder.ivThumb.setDefaultImageResId(R.drawable.potu);
                if (!"".equals(attachmentId) && null != attachmentId) {
                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(20);
                    ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
                        @Override
                        public Bitmap getBitmap(String key) {
                            return lruCache.get(key);
                        }

                        @Override
                        public void putBitmap(String key, Bitmap value) {
                            lruCache.put(key, value);
                        }
                    };
                    ImageLoader imageLoader = new ImageLoader(requestQueue, imageCache);
                    holder.ivThumb.setTag("url");
                    holder.ivThumb.setErrorImageResId(R.drawable.potu);
                    holder.ivThumb.setImageUrl(application.getUrl_half() + attachmentId, imageLoader);
                }

                holder.tvName.setText(app.getName());
                holder.tvCompany.setText(app.getDeveloper());
                //通过简易算法，拦截数字之外的字符
                String star = app.getStar();
                Pattern p = Pattern.compile("[0-5]");
                Matcher m = p.matcher(star);
                while (m.find()) {
                    holder.ivStar.setNumStars(Integer.parseInt(m.group()));
                }
                holder.btnDetail.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //将数据传输给详情Activity
                        Intent intent = new Intent(context, AppInfo.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("appInfo", (App_package) getItem(flag));
//                        Log.e("appInfoggg",((App_package) getItem(flag)).toString());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                return convertView;
            }


            class ViewHolder {
                NetworkImageView ivThumb;
                RatingBar ivStar;
                TextView tvName;
                TextView tvCompany;
                Button btnDetail;
            }
        }
    }


    /**
     * 是否已经打开“查看App使用情况” 设置。
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean isStatAccessPermissionSet(Context c) {

        try {
            PackageManager pm = c.getPackageManager();
            ApplicationInfo info = pm.getApplicationInfo(c.getPackageName(), 0);
            AppOpsManager aom = (AppOpsManager) c.getSystemService(Context.APP_OPS_SERVICE);
            aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, info.uid, info.packageName);
            return aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, info.uid, info.packageName)
                    == AppOpsManager.MODE_ALLOWED;
        } catch (Exception e) {

        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_list_new);
        EventBus.getDefault().register(this);
        //判断是否有应用权限
        boolean statAccessPermissionSet = isStatAccessPermissionSet(this);
        if (!statAccessPermissionSet) {

            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        }

        application = (MyApplication) getApplication();

        update_version();


        //获取账号信息
        sp = getSharedPreferences("config", MODE_PRIVATE);

        boolean b = NetworkUtil.checkedNetWork(this);
        //判断网络连接情况
        if (b) {
            Log.e("App_list_new", "onCreate1: ");
            if (!isPaswordSet()) { //如果设置了就初始化本地密码

                Log.e("App_list_new", "onCreate2: " + application.getUrl_get_app_pwd());
                //网络正常则获取密码初始化接口
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                String pwd = RestTemplateUtil.getPwd(application.getUrl_get_app_pwd() + "/" + MacUtil.getMacAddress() + "/" + "testpassworddonttouse" + "/" + 0);

                                if (null != pwd && "1".equals(pwd)) { //密码正确不进行初始化

                                } else if (null == pwd || "".equals(pwd)) {


                                } else {
                                    //初始化密码
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("applockpassword2", pwd);
                                    editor.commit();
                                }
                            }
                        }
                ).start();
            }
        }

        initView();
        downLoad(0);
        //
    }

    private void update_version() {

        //访问服务器 试检测是否有新版本发布
        UpdateVersionUtil.checkVersion(application, App_list_new.this, new UpdateVersionUtil.UpdateListener() {

            @Override
            public void onUpdateReturned(int updateStatus, VersionInfo versionInfo) {
                //判断回调过来的版本检测状态
                switch (updateStatus) {
                    case UpdateStatus.YES:
                        //直接进入更新
                        Log.e("UpdateStatus", "YES ");
                        Log.e("UpdateStatus", versionInfo.toString());
                        UpdateVersionUtil.update(App_list_new.this, versionInfo);
                        break;
                    case UpdateStatus.NO:
                        Log.e("UpdateStatus", "NO");
                        //没有新版本
//							ToastUtils.showToast(getApplicationContext(), "已经是最新版本了!");
                        break;
                    case UpdateStatus.NOWIFI:
                        Log.e("UpdateStatus", "NOWIFI");
                        //当前是非wifi网络
//							ToastUtils.showToast(getApplicationContext(), "只有在wifi下更新！");
//							DialogUtils.showDialog(MainActivity.this, "温馨提示","当前非wifi网络,下载会消耗手机流量!", "确定", "取消",new DialogOnClickListenner() {
//								@Override
//								public void btnConfirmClick(Dialog dialog) {
//									dialog.dismiss();
//									//点击确定之后弹出更新对话框
//									UpdateVersionUtil.showDialog(SystemActivity.this,versionInfo);
//								}
//
//								@Override
//								public void btnCancelClick(Dialog dialog) {
//									dialog.dismiss();
//								}
//							});
                        break;
                    case UpdateStatus.ERROR:
                        Log.e("UpdateStatus", "ERROR: ");
                        //检测失败
//				        	ToastUtils.showToast(getApplicationContext(), "检测失败，请稍后重试！");
                        break;
                    case UpdateStatus.TIMEOUT:
                        Log.e("UpdateStatus", "TIMEOUT: ");
                        //链接超时
//				        	ToastUtils.showToast(getApplicationContext(), "链接超时，请检查网络设置!");
                        break;
                }
            }
        });

    }

    public void downLoad(final int position) {
//        Log.e("tag",position+"");
        new Thread() {
            @Override
            public void run() {
                super.run();
                switch (position) {
                    case 0:
                        try {
                            getRes("", PRESCHOOL);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        try {
                            getRes("", PRIMARY);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        try {
                            getRes("", JUNIOR);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        try {
                            getRes("", HIGH);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
                //模拟数据
//               apps = new ArrayList<AppPackageDto>();
//               for(int i = 0;i<7;i++){
//                   AppPackageDto packageDto = new AppPackageDto();
//                   packageDto.setId("" + i);
//                   packageDto.setName("" + i);
//                   packageDto.setDeveloper("" + i);
//                   packageDto.setDescription("" + i);
//                   packageDto.setCreateTime(new Date());
//                   packageDto.setStar("" + i);
//                   AppPackageTypeDto appPackageTypeDto = new AppPackageTypeDto();
//                   appPackageTypeDto.setName("" + i);
//                   packageDto.setTypeDto(appPackageTypeDto);
//                   AppPackageLevelDto appPackageLevelDto = new AppPackageLevelDto();
//                   appPackageLevelDto.setName("" + i);
//                   packageDto.setLevelDto(appPackageLevelDto);
//                   List<String> ss = new ArrayList<String>();
//                   ss.add("qqq"+i);
//                   ss.add("sss" +i);
//                   packageDto.setAttachmentIds(ss);
//                   packageDto.setAttachmentId("qweqwe"+i);
//                   packageDto.setAppPackageName(""+i);
//                   apps.add(packageDto);
//               }

                Message message = Message.obtain();

                message.what = position;

                handler.sendMessage(message);//向主线程发送消息
            }
        }.start();

    }

    /**
     * 获取网络数据
     */
    public void getRes(String type, String level) {
        String result = RestTemplateUtil.getResToPost(application.getUrl(), HttpMethod.POST, type, level);
//        Log.i("tag", result);
        JSONObject jsonObject = JSON.parseObject(result);
        JSONArray jsonArray = (JSONArray) jsonObject.get("data");
        apps = JSON.parseArray(jsonArray.toJSONString(), AppPackageDto.class);
//        Log.e("tagggg", apps.size()+"");
    }


    public void initView() {
        change_password = (Button) findViewById(R.id.change_password);

        change_password.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(App_list_new.this, ChangePassageApp.class);
                startActivity(intent);
            }
        });

        //学生等级
        buttonOne = (ToggleButton) findViewById(R.id.btn_1);
        buttonTwo = (ToggleButton) findViewById(R.id.btn_2);
        buttonThree = (ToggleButton) findViewById(R.id.btn_3);
        buttonFour = (ToggleButton) findViewById(R.id.btn_4);
        //app分类
        button1 = (ToggleButton) findViewById(R.id.btn_one);
        button2 = (ToggleButton) findViewById(R.id.btn_two);
        button3 = (ToggleButton) findViewById(R.id.btn_three);
        button4 = (ToggleButton) findViewById(R.id.btn_four);
        button5 = (ToggleButton) findViewById(R.id.btn_five);
        button6 = (ToggleButton) findViewById(R.id.btn_six);
        button7 = (ToggleButton) findViewById(R.id.btn_seven);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);


        buttonOne.setOnClickListener(this);
        buttonTwo.setOnClickListener(this);
        buttonThree.setOnClickListener(this);
        buttonFour.setOnClickListener(this);

        buttonOne.setChecked(true);
        button1.setChecked(true);
        buttonOne.setClickable(false);
        button1.setClickable(false);
        //返回按钮
        mbtnBack = (Button) findViewById(R.id.back);
        mbtnBack.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                finish();
            }

        });

        mSearchView = (SearchView) findViewById(R.id.search);
        /**
         * 默认情况下, search widget是"iconified“的，只是用一个图标 来表示它(一个放大镜),
         * 当用户按下它的时候才显示search box . 你可以调用setIconifiedByDefault(false)让search
         * box默认都被显示。 你也可以调用setIconified()让它以iconified“的形式显示。
         */
        mSearchView.setIconifiedByDefault(true);
        /**
         * 默认情况下是没提交搜索的按钮，所以用户必须在键盘上按下"enter"键来提交搜索.你可以同过setSubmitButtonEnabled(
         * true)来添加一个提交按钮（"submit" button)
         * 设置true后，右边会出现一个箭头按钮。如果用户没有输入，就不会触发提交（submit）事件
         */
        mSearchView.setSubmitButtonEnabled(true);
        /**
         * 初始是否已经是展开的状态
         * 写上此句后searchView初始展开的，也就是是可以点击输入的状态，如果不写，那么就需要点击下放大镜，才能展开出现输入框
         */
        mSearchView.onActionViewExpanded();
        // 设置search view的背景色
//        mSearchView.setBackgroundColor(Color.parseColor("#E1FFFF"));
        /**
         * 默认情况下, search widget是"iconified“的，只是用一个图标 来表示它(一个放大镜),
         * 当用户按下它的时候才显示search box . 你可以调用setIconifiedByDefault(false)让search
         * box默认都被显示。 你也可以调用setIconified()让它以iconified“的形式显示。
         */
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            private String TAG = getClass().getSimpleName();

            /*
             * 在输入时触发的方法，当字符真正显示到searchView中才触发，像是拼音，在舒服法组词的时候不会触发
             *
             * @param queryText
             *
             * @return false if the SearchView should perform the default action
             * of showing any suggestions if available, true if the action was
             * handled by the listener.
             */
            @Override
            public boolean onQueryTextChange(String queryText) {
                Log.d(TAG, "onQueryTextChange = " + queryText);


                return true;
            }

            /*
             * 输入完成后，提交时触发的方法，一般情况是点击输入法中的搜索按钮才会触发。表示现在正式提交了
             *
             * @param queryText
             *
             * @return true to indicate that it has handled the submit request.
             * Otherwise return false to let the SearchView handle the
             * submission by launching any associated intent.
             */
            @Override
            public boolean onQueryTextSubmit(String queryText) {
                Log.d(TAG, "onQueryTextSubmit = " + queryText);

                EventAppInfo eventAppInfo = new EventAppInfo();
                eventAppInfo.setFlag(GET_SINGLE_APP_FLAG);
                eventAppInfo.setApp_name(queryText);
                EventBus.getDefault().postSticky(eventAppInfo);

                Intent intent = new Intent(App_list_new.this, SearchViewActivity.class);
                startActivity(intent);


                if (mSearchView != null) {
                    // 得到输入管理对象
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        // 这将让键盘在所有的情况下都被隐藏，但是一般我们在点击搜索按钮后，输入法都会乖乖的自动隐藏的。
                        imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0); // 输入法如果是显示状态，那么就隐藏输入法
                    }
                    mSearchView.clearFocus(); // 不获取焦点
                }
                return true;
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1://幼儿园
                downLoad(0);
                button1.setChecked(true);
                button1.setClickable(false);
                button2.setClickable(true);
                button2.setChecked(false);
                button3.setClickable(true);
                button3.setChecked(false);
                button4.setClickable(true);
                button4.setChecked(false);
                button5.setClickable(true);
                button5.setChecked(false);
                button6.setClickable(true);
                button6.setChecked(false);
                button7.setClickable(true);
                button7.setChecked(false);
                if (buttonOne.isChecked()) {
                    buttonOne.setClickable(false);
                    buttonTwo.setClickable(true);
                    buttonTwo.setChecked(false);
                    buttonThree.setClickable(true);
                    buttonThree.setChecked(false);
                    buttonFour.setClickable(true);
                    buttonFour.setChecked(false);
                    if (button1.isChecked()) {
                        //幼儿园+教育
                        changeView(0);
                    } else if (button2.isChecked()) {
                        //幼儿园+游戏
                        changeView(1);
                    } else if (button3.isChecked()) {
                        //幼儿园+工具
                        changeView(2);
                    } else if (button4.isChecked()) {
                        //幼儿园+体育
                        changeView(3);
                    } else if (button5.isChecked()) {
                        //幼儿园+音乐
                        changeView(4);
                    } else if (button6.isChecked()) {
                        //幼儿园+娱乐
                        changeView(5);
                    } else {
                        //幼儿园+图书
                        changeView(6);
                    }

                }
                break;

            case R.id.btn_2://小学
                downLoad(1);
                button1.setChecked(true);
                button1.setClickable(false);
                button2.setClickable(true);
                button2.setChecked(false);
                button3.setClickable(true);
                button3.setChecked(false);
                button4.setClickable(true);
                button4.setChecked(false);
                button5.setClickable(true);
                button5.setChecked(false);
                button6.setClickable(true);
                button6.setChecked(false);
                button7.setClickable(true);
                button7.setChecked(false);
                if (buttonTwo.isChecked()) {
                    buttonTwo.setClickable(false);
                    buttonOne.setClickable(true);
                    buttonOne.setChecked(false);
                    buttonThree.setClickable(true);
                    buttonThree.setChecked(false);
                    buttonFour.setClickable(true);
                    buttonFour.setChecked(false);
                    if (button1.isChecked()) {
                        //小学+教育
                        changeView(0);
                    } else if (button2.isChecked()) {
                        //小学+游戏
                        changeView(1);
                    } else if (button3.isChecked()) {
                        //小学+工具
                        changeView(2);
                    } else if (button4.isChecked()) {
                        //小学+体育
                        changeView(3);
                    } else if (button5.isChecked()) {
                        //小学+音乐
                        changeView(4);
                    } else if (button6.isChecked()) {
                        //小学+娱乐
                        changeView(5);
                    } else {
                        //小学+图书
                        changeView(6);
                    }

                }
                break;
            case R.id.btn_3://初中
                downLoad(2);
                button1.setChecked(true);
                button1.setClickable(false);
                button2.setClickable(true);
                button2.setChecked(false);
                button3.setClickable(true);
                button3.setChecked(false);
                button4.setClickable(true);
                button4.setChecked(false);
                button5.setClickable(true);
                button5.setChecked(false);
                button6.setClickable(true);
                button6.setChecked(false);
                button7.setClickable(true);
                button7.setChecked(false);
                if (buttonThree.isChecked()) {
                    buttonThree.setClickable(false);
                    buttonOne.setClickable(true);
                    buttonOne.setChecked(false);
                    buttonTwo.setClickable(true);
                    buttonTwo.setChecked(false);
                    buttonFour.setClickable(true);
                    buttonFour.setChecked(false);
                    if (button1.isChecked()) {
                        //初中+教育
                        changeView(0);
                    } else if (button2.isChecked()) {
                        //初中+游戏
                        changeView(1);
                    } else if (button3.isChecked()) {
                        //初中+工具
                        changeView(2);
                    } else if (button4.isChecked()) {
                        //初中+体育
                        changeView(3);
                    } else if (button5.isChecked()) {
                        //初中+音乐
                        changeView(4);
                    } else if (button6.isChecked()) {
                        //初中+娱乐
                        changeView(5);
                    } else {
                        //初中+图书
                        changeView(6);
                    }

                }
                break;
            case R.id.btn_4://高中
                downLoad(3);
                button1.setChecked(true);
                button1.setClickable(false);
                button2.setClickable(true);
                button2.setChecked(false);
                button3.setClickable(true);
                button3.setChecked(false);
                button4.setClickable(true);
                button4.setChecked(false);
                button5.setClickable(true);
                button5.setChecked(false);
                button6.setClickable(true);
                button6.setChecked(false);
                button7.setClickable(true);
                button7.setChecked(false);
                if (buttonFour.isChecked()) {
                    buttonFour.setClickable(false);
                    buttonOne.setClickable(true);
                    buttonOne.setChecked(false);
                    buttonThree.setClickable(true);
                    buttonThree.setChecked(false);
                    buttonTwo.setClickable(true);
                    buttonTwo.setChecked(false);
                    if (button1.isChecked()) {
                        //高中+教育
                        changeView(0);
                    } else if (button2.isChecked()) {
                        //高中+游戏
                        changeView(1);
                    } else if (button3.isChecked()) {
                        //高中+工具
                        changeView(2);
                    } else if (button4.isChecked()) {
                        //高中+体育
                        changeView(3);
                    } else if (button5.isChecked()) {
                        //高中+音乐
                        changeView(4);
                    } else if (button6.isChecked()) {
                        //高中+娱乐
                        changeView(5);
                    } else {
                        //高中+图书
                        changeView(6);
                    }
                }
                break;

            case R.id.btn_one://教育
                if (button1.isChecked()) {
                    button1.setClickable(false);
                    button2.setClickable(true);
                    button2.setChecked(false);
                    button3.setClickable(true);
                    button3.setChecked(false);
                    button4.setClickable(true);
                    button4.setChecked(false);
                    button5.setClickable(true);
                    button5.setChecked(false);
                    button6.setClickable(true);
                    button6.setChecked(false);
                    button7.setClickable(true);
                    button7.setChecked(false);
                    if (buttonOne.isChecked()) {
                        //教育+幼儿园
                        changeView(0);
                    } else if (buttonTwo.isChecked()) {
                        //教育+小学
                        changeView(0);
                    } else if (buttonThree.isChecked()) {
                        //教育+初中
                        changeView(0);
                    } else if (buttonFour.isChecked()) {
                        //教育+高中
                        changeView(0);
                    }


                }
                break;

            case R.id.btn_two://游戏
                if (button2.isChecked()) {
                    button2.setClickable(false);
                    button1.setClickable(true);
                    button1.setChecked(false);
                    button3.setClickable(true);
                    button3.setChecked(false);
                    button4.setClickable(true);
                    button4.setChecked(false);
                    button6.setClickable(true);
                    button6.setChecked(false);
                    button7.setClickable(true);
                    button7.setChecked(false);
                    if (buttonOne.isChecked()) {
                        //游戏+幼儿园
                        changeView(1);
                    } else if (buttonTwo.isChecked()) {
                        //游戏+小学
                        changeView(1);
                    } else if (buttonThree.isChecked()) {
                        //游戏+初中
                        changeView(1);
                    } else if (buttonFour.isChecked()) {
                        //游戏+高中
                        changeView(1);
                    }


                }
                break;
            case R.id.btn_three://工具
                if (button3.isChecked()) {
                    button3.setClickable(false);
                    button1.setClickable(true);
                    button1.setChecked(false);
                    button2.setClickable(true);
                    button2.setChecked(false);
                    button4.setClickable(true);
                    button4.setChecked(false);
                    button5.setClickable(true);
                    button5.setChecked(false);
                    button6.setClickable(true);
                    button6.setChecked(false);
                    button7.setClickable(true);
                    button7.setChecked(false);
                    if (buttonOne.isChecked()) {
                        //工具+幼儿园
                        changeView(2);
                    } else if (buttonTwo.isChecked()) {
                        //工具+小学
                        changeView(2);
                    } else if (buttonThree.isChecked()) {
                        //工具+初中
                        changeView(2);
                    } else if (buttonFour.isChecked()) {
                        //工具+高中
                        changeView(2);
                    }
                }
                break;
            case R.id.btn_four://体育
                if (button4.isChecked()) {
                    button4.setClickable(false);
                    button1.setClickable(true);
                    button1.setChecked(false);
                    button3.setClickable(true);
                    button3.setChecked(false);
                    button2.setClickable(true);
                    button2.setChecked(false);
                    button5.setClickable(true);
                    button5.setChecked(false);
                    button6.setClickable(true);
                    button6.setChecked(false);
                    button7.setClickable(true);
                    button7.setChecked(false);
                    if (buttonOne.isChecked()) {
                        //体育+幼儿园
                        changeView(3);
                    } else if (buttonTwo.isChecked()) {
                        //体育+小学
                        changeView(3);
                    } else if (buttonThree.isChecked()) {
                        //体育+初中
                        changeView(3);
                    } else if (buttonFour.isChecked()) {
                        //体育+高中
                        changeView(3);
                    }
                }
                break;
            case R.id.btn_five://音乐
                if (button5.isChecked()) {
                    button5.setClickable(false);
                    button1.setClickable(true);
                    button1.setChecked(false);
                    button3.setClickable(true);
                    button3.setChecked(false);
                    button4.setClickable(true);
                    button4.setChecked(false);
                    button2.setClickable(true);
                    button2.setChecked(false);
                    button6.setClickable(true);
                    button6.setChecked(false);
                    button7.setClickable(true);
                    button7.setChecked(false);
                    if (buttonOne.isChecked()) {
                        //音乐+幼儿园
                        changeView(4);
                    } else if (buttonTwo.isChecked()) {
                        //音乐+小学
                        changeView(4);
                    } else if (buttonThree.isChecked()) {
                        //音乐+初中
                        changeView(4);
                    } else if (buttonFour.isChecked()) {
                        //音乐+高中
                        changeView(4);
                    }
                }
                break;
            case R.id.btn_six://娱乐
                if (button6.isChecked()) {
                    button6.setClickable(false);
                    button1.setClickable(true);
                    button1.setChecked(false);
                    button3.setClickable(true);
                    button3.setChecked(false);
                    button4.setClickable(true);
                    button4.setChecked(false);
                    button2.setClickable(true);
                    button2.setChecked(false);
                    button5.setClickable(true);
                    button5.setChecked(false);
                    button7.setClickable(true);
                    button7.setChecked(false);
                    if (buttonOne.isChecked()) {
                        //娱乐+幼儿园
                        changeView(5);
                    } else if (buttonTwo.isChecked()) {
                        //娱乐+小学
                        changeView(5);
                    } else if (buttonThree.isChecked()) {
                        //娱乐+初中
                        changeView(5);
                    } else if (buttonFour.isChecked()) {
                        //娱乐+高中
                        changeView(5);
                    }

                }
                break;
            case R.id.btn_seven://图书
                if (button7.isChecked()) {
                    button7.setClickable(false);
                    button1.setClickable(true);
                    button1.setChecked(false);
                    button3.setClickable(true);
                    button3.setChecked(false);
                    button4.setClickable(true);
                    button4.setChecked(false);
                    button2.setClickable(true);
                    button2.setChecked(false);
                    button5.setClickable(true);
                    button5.setChecked(false);
                    if (buttonOne.isChecked()) {
                        //图书+幼儿园
                        changeView(6);
                    } else if (buttonTwo.isChecked()) {
                        //图书+小学
                        changeView(6);
                    } else if (buttonThree.isChecked()) {
                        //图书+初中
                        changeView(6);
                    } else if (buttonFour.isChecked()) {
                        //图书+高中
                        changeView(6);
                    }

                }
                break;
            default:
                break;
        }
    }

    private void changeView(int desTab) {
        mPager.setCurrentItem(desTab);
    }


    protected void showPasswordInputDialog() {
        View view = View.inflate(App_list_new.this, R.layout.dialog_password_input, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(App_list_new.this);
        et_password = (EditText) view.findViewById(R.id.et_password);
        bt_ok = (Button) view.findViewById(R.id.ok);
        bt_cancle = (Button) view.findViewById(R.id.cancle);
        dialog = builder.create();
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
                String password = sp.getString("applockpassword2", "");
                if (password.equals(MD5Utils.md5Encode(et_password.getText().toString().trim()))) {
                    dialog.dismiss();
                } else {
                    Toast.makeText(App_list_new.this, "密码输入错误", Toast.LENGTH_SHORT).show();
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
        View view = View.inflate(App_list_new.this, R.layout.dialog_password_set, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(App_list_new.this);
        et_password = (EditText) view.findViewById(R.id.et_password);
        et_password_confirm = (EditText) view.findViewById(R.id.et_password_confirm);
        bt_ok = (Button) view.findViewById(R.id.ok);
        bt_cancle = (Button) view.findViewById(R.id.cancle);

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
                final String password = et_password.getText().toString().trim();
                String password_confirm = et_password_confirm.getText().toString().trim();
                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(password_confirm)) {
                    Toast.makeText(App_list_new.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.equals(password_confirm)) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("applockpassword2", MD5Utils.md5Encode(password));
                    editor.commit();
                    dialog.dismiss();
                    Toast.makeText(App_list_new.this, "密码设置成功", Toast.LENGTH_SHORT).show();


                    boolean b = NetworkUtil.checkedNetWork(App_list_new.this);
                    //判断网络连接情况
                    if (b) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //网络正常则获取密码初始化接口
                                RestTemplateUtil.getPwd(application.getUrl_get_app_pwd() + "/" + MacUtil.getMacAddress() + "/" + password + "/" + 0);
                            }
                        }).start();
                    }

                } else {
                    Toast.makeText(App_list_new.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                    et_password.setText("");
                    et_password_confirm.setText("");

                }

            }
        });
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();

    }

    protected boolean isPaswordSet() {
        String password = sp.getString("applockpassword2", "");
        return TextUtils.isEmpty(password);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在异步线程执行
    public void onDataSynEvent(EventAppInfo event) {
        Log.e(TAG, "event---------->");
        if (GET_SINGLE_APP_FILD_FLAG==event.getFlag()){
            Toast.makeText(App_list_new.this,event.getApp_name(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        if (isPaswordSet()) {
            //进入密码设置对话框
            showPasswordSetDialog();
        } else {
            //进入密码输入对话框
            showPasswordInputDialog();
        }
        super.onStart();



    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


}
