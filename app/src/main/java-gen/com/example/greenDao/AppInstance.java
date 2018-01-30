package com.example.greenDao;

import java.util.List;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

/**
 * Entity mapped to table APP_INSTANCE.
 */
public class AppInstance {

    private java.util.Date installTime;
    private String mac;
    private String appPackageName;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient AppInstanceDao myDao;

    private List<AppRunConfigDetail> appRunConfigDetailList;

    public AppInstance() {
    }

    public AppInstance(String appPackageName) {
        this.appPackageName = appPackageName;
    }

    public AppInstance(java.util.Date installTime, String mac, String appPackageName) {
        this.installTime = installTime;
        this.mac = mac;
        this.appPackageName = appPackageName;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAppInstanceDao() : null;
    }

    public java.util.Date getInstallTime() {
        return installTime;
    }

    public void setInstallTime(java.util.Date installTime) {
        this.installTime = installTime;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getAppPackageName() {
        return appPackageName;
    }

    public void setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<AppRunConfigDetail> getAppRunConfigDetailList() {
        if (appRunConfigDetailList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AppRunConfigDetailDao targetDao = daoSession.getAppRunConfigDetailDao();
            List<AppRunConfigDetail> appRunConfigDetailListNew = targetDao._queryAppInstance_AppRunConfigDetailList(appPackageName);
            synchronized (this) {
                if(appRunConfigDetailList == null) {
                    appRunConfigDetailList = appRunConfigDetailListNew;
                }
            }
        }
        return appRunConfigDetailList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetAppRunConfigDetailList() {
        appRunConfigDetailList = null;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}