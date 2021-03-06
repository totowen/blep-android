package com.example.greenDao;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

/**
 * Entity mapped to table TIME.
 */
public class Time {

    private Long id;
    private String time;
    private long timeShelf_id;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient TimeDao myDao;

    private AppRunConfigDetail appRunConfigDetail;
    private Long appRunConfigDetail__resolvedKey;


    public Time() {
    }

    public Time(Long id) {
        this.id = id;
    }

    public Time(Long id, String time, long timeShelf_id) {
        this.id = id;
        this.time = time;
        this.timeShelf_id = timeShelf_id;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTimeDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getTimeShelf_id() {
        return timeShelf_id;
    }

    public void setTimeShelf_id(long timeShelf_id) {
        this.timeShelf_id = timeShelf_id;
    }

    /** To-one relationship, resolved on first access. */
    public AppRunConfigDetail getAppRunConfigDetail() {
        long __key = this.timeShelf_id;
        if (appRunConfigDetail__resolvedKey == null || !appRunConfigDetail__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AppRunConfigDetailDao targetDao = daoSession.getAppRunConfigDetailDao();
            AppRunConfigDetail appRunConfigDetailNew = targetDao.load(__key);
            synchronized (this) {
                appRunConfigDetail = appRunConfigDetailNew;
            	appRunConfigDetail__resolvedKey = __key;
            }
        }
        return appRunConfigDetail;
    }

    public void setAppRunConfigDetail(AppRunConfigDetail appRunConfigDetail) {
        if (appRunConfigDetail == null) {
            throw new DaoException("To-one property 'timeShelf_id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.appRunConfigDetail = appRunConfigDetail;
            timeShelf_id = appRunConfigDetail.getId();
            appRunConfigDetail__resolvedKey = timeShelf_id;
        }
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
        return "Time{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", timeShelf_id=" + timeShelf_id +
                '}';
    }
}
