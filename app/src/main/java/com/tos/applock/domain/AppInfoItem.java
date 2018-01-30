package com.tos.applock.domain;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/**
 * Created by admin on 2016/3/18.
 */
public class AppInfoItem {
    private String appLabel;
    private Drawable appIcon ;
    private Intent intent ;
    private String pkgName ;

    public AppInfoItem(){}

    public String getAppLabel() {
        return appLabel;
    }
    public void setAppLabel(String appName) {
        this.appLabel = appName;
    }
    public Drawable getAppIcon() {
        return appIcon;
    }
    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }
    public Intent getIntent() {
        return intent;
    }
    public void setIntent(Intent intent) {
        this.intent = intent;
    }
    public String getPkgName(){
        return pkgName ;
    }
    public void setPkgName(String pkgName){
        this.pkgName=pkgName ;
    }
}
