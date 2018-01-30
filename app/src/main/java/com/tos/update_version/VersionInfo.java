package com.tos.update_version;

import java.io.Serializable;

/**
 * Created by alienware on 2016/6/12.
 */
public class VersionInfo implements Serializable{
    private String downloadUrl;
    private int versionCode;

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }


}
