package com.example.greenDao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table APP_RUN_CONFIG_DETAIL.
*/
public class AppRunConfigDetailDao extends AbstractDao<AppRunConfigDetail, Long> {

    public static final String TABLENAME = "APP_RUN_CONFIG_DETAIL";

    /**
     * Properties of entity AppRunConfigDetail.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Day = new Property(1, String.class, "day", false, "DAY");
        public final static Property AppStatus = new Property(2, String.class, "appStatus", false, "APP_STATUS");
        public final static Property PadAppStatus = new Property(3, String.class, "padAppStatus", false, "PAD_APP_STATUS");
        public final static Property StatusStartTime = new Property(4, String.class, "statusStartTime", false, "STATUS_START_TIME");
        public final static Property StatusEndTime = new Property(5, String.class, "statusEndTime", false, "STATUS_END_TIME");
        public final static Property Tag = new Property(6, Integer.class, "tag", false, "TAG");
        public final static Property State = new Property(7, String.class, "state", false, "STATE");
        public final static Property DetailShelf_id = new Property(8, String.class, "detailShelf_id", false, "DETAIL_SHELF_ID");
    };

    private DaoSession daoSession;

    private Query<AppRunConfigDetail> appInstance_AppRunConfigDetailListQuery;

    public AppRunConfigDetailDao(DaoConfig config) {
        super(config);
    }
    
    public AppRunConfigDetailDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'APP_RUN_CONFIG_DETAIL' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'DAY' TEXT," + // 1: day
                "'APP_STATUS' TEXT," + // 2: appStatus
                "'PAD_APP_STATUS' TEXT," + // 3: padAppStatus
                "'STATUS_START_TIME' TEXT," + // 4: statusStartTime
                "'STATUS_END_TIME' TEXT," + // 5: statusEndTime
                "'TAG' INTEGER," + // 6: tag
                "'STATE' TEXT," + // 7: state
                "'DETAIL_SHELF_ID' TEXT NOT NULL );"); // 8: detailShelf_id
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'APP_RUN_CONFIG_DETAIL'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, AppRunConfigDetail entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String day = entity.getDay();
        if (day != null) {
            stmt.bindString(2, day);
        }
 
        String appStatus = entity.getAppStatus();
        if (appStatus != null) {
            stmt.bindString(3, appStatus);
        }
 
        String padAppStatus = entity.getPadAppStatus();
        if (padAppStatus != null) {
            stmt.bindString(4, padAppStatus);
        }
 
        String statusStartTime = entity.getStatusStartTime();
        if (statusStartTime != null) {
            stmt.bindString(5, statusStartTime);
        }
 
        String statusEndTime = entity.getStatusEndTime();
        if (statusEndTime != null) {
            stmt.bindString(6, statusEndTime);
        }
 
        Integer tag = entity.getTag();
        if (tag != null) {
            stmt.bindLong(7, tag);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(8, state);
        }
        stmt.bindString(9, entity.getDetailShelf_id());
    }

    @Override
    protected void attachEntity(AppRunConfigDetail entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public AppRunConfigDetail readEntity(Cursor cursor, int offset) {
        AppRunConfigDetail entity = new AppRunConfigDetail( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // day
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // appStatus
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // padAppStatus
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // statusStartTime
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // statusEndTime
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // tag
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // state
            cursor.getString(offset + 8) // detailShelf_id
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, AppRunConfigDetail entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDay(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAppStatus(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPadAppStatus(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setStatusStartTime(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setStatusEndTime(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setTag(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        entity.setState(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setDetailShelf_id(cursor.getString(offset + 8));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(AppRunConfigDetail entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(AppRunConfigDetail entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "appRunConfigDetailList" to-many relationship of AppInstance. */
    public List<AppRunConfigDetail> _queryAppInstance_AppRunConfigDetailList(String detailShelf_id) {
        synchronized (this) {
            if (appInstance_AppRunConfigDetailListQuery == null) {
                QueryBuilder<AppRunConfigDetail> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.DetailShelf_id.eq(null));
                appInstance_AppRunConfigDetailListQuery = queryBuilder.build();
            }
        }
        Query<AppRunConfigDetail> query = appInstance_AppRunConfigDetailListQuery.forCurrentThread();
        query.setParameter(0, detailShelf_id);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getAppInstanceDao().getAllColumns());
            builder.append(" FROM APP_RUN_CONFIG_DETAIL T");
            builder.append(" LEFT JOIN APP_INSTANCE T0 ON T.'DETAIL_SHELF_ID'=T0.'APP_PACKAGE_NAME'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected AppRunConfigDetail loadCurrentDeep(Cursor cursor, boolean lock) {
        AppRunConfigDetail entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        AppInstance appInstance = loadCurrentOther(daoSession.getAppInstanceDao(), cursor, offset);
         if(appInstance != null) {
            entity.setAppInstance(appInstance);
        }

        return entity;    
    }

    public AppRunConfigDetail loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<AppRunConfigDetail> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<AppRunConfigDetail> list = new ArrayList<AppRunConfigDetail>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<AppRunConfigDetail> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<AppRunConfigDetail> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
