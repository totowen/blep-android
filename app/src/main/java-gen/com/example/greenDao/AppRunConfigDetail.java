package com.example.greenDao;

import java.util.List;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

/**
 * Entity mapped to table APP_RUN_CONFIG_DETAIL.
 */
public class AppRunConfigDetail {

    private Long id;
    private String day;
    private String appStatus;
    private String padAppStatus;
    private String statusStartTime;
    private String statusEndTime;
    private Integer tag;
    private String state;
    /** Not-null value. */
    private String detailShelf_id;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient AppRunConfigDetailDao myDao;

    private AppInstance appInstance;
    private String appInstance__resolvedKey;

    private List<Time> timeList;

    public AppRunConfigDetail() {
    }

    public AppRunConfigDetail(Long id) {
        this.id = id;
    }

    public AppRunConfigDetail(Long id, String day, String appStatus, String padAppStatus, String statusStartTime, String statusEndTime, Integer tag, String state, String detailShelf_id) {
        this.id = id;
        this.day = day;
        this.appStatus = appStatus;
        this.padAppStatus = padAppStatus;
        this.statusStartTime = statusStartTime;
        this.statusEndTime = statusEndTime;
        this.tag = tag;
        this.state = state;
        this.detailShelf_id = detailShelf_id;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAppRunConfigDetailDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    public String getPadAppStatus() {
        return padAppStatus;
    }

    public void setPadAppStatus(String padAppStatus) {
        this.padAppStatus = padAppStatus;
    }

    public String getStatusStartTime() {
        return statusStartTime;
    }

    public void setStatusStartTime(String statusStartTime) {
        this.statusStartTime = statusStartTime;
    }

    public String getStatusEndTime() {
        return statusEndTime;
    }

    public void setStatusEndTime(String statusEndTime) {
        this.statusEndTime = statusEndTime;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /** Not-null value. */
    public String getDetailShelf_id() {
        return detailShelf_id;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDetailShelf_id(String detailShelf_id) {
        this.detailShelf_id = detailShelf_id;
    }

    /** To-one relationship, resolved on first access. */
    public AppInstance getAppInstance() {
        String __key = this.detailShelf_id;
        if (appInstance__resolvedKey == null || appInstance__resolvedKey != __key) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AppInstanceDao targetDao = daoSession.getAppInstanceDao();
            AppInstance appInstanceNew = targetDao.load(__key);
            synchronized (this) {
                appInstance = appInstanceNew;
            	appInstance__resolvedKey = __key;
            }
        }
        return appInstance;
    }

    public void setAppInstance(AppInstance appInstance) {
        if (appInstance == null) {
            throw new DaoException("To-one property 'detailShelf_id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.appInstance = appInstance;
            detailShelf_id = appInstance.getAppPackageName();
            appInstance__resolvedKey = detailShelf_id;
        }
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<Time> getTimeList() {
        if (timeList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TimeDao targetDao = daoSession.getTimeDao();
            List<Time> timeListNew = targetDao._queryAppRunConfigDetail_TimeList(id);
            synchronized (this) {
                if(timeList == null) {
                    timeList = timeListNew;
                }
            }
        }
        return timeList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetTimeList() {
        timeList = null;
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

    @Override
    public String toString() {
        return "AppRunConfigDetail{" +
                "id=" + id +
                ", day='" + day + '\'' +
                ", appStatus='" + appStatus + '\'' +
                ", padAppStatus='" + padAppStatus + '\'' +
                ", statusStartTime='" + statusStartTime + '\'' +
                ", statusEndTime='" + statusEndTime + '\'' +
                ", tag=" + tag +
                ", state='" + state + '\'' +
                ", detailShelf_id='" + detailShelf_id + '\'' +
                ", daoSession=" + daoSession +
                ", myDao=" + myDao +
                ", appInstance=" + appInstance +
                ", appInstance__resolvedKey='" + appInstance__resolvedKey + '\'' +
                ", timeList=" + timeList +
                '}';
    }
}
