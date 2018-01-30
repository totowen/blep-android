package com.example.greenDao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table APP_PACKAGE.
*/
public class AppPackageDao extends AbstractDao<AppPackage, Long> {

    public static final String TABLENAME = "APP_PACKAGE";

    /**
     * Properties of entity AppPackage.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Developer = new Property(2, String.class, "developer", false, "DEVELOPER");
        public final static Property Description = new Property(3, String.class, "description", false, "DESCRIPTION");
        public final static Property CreateTime = new Property(4, java.util.Date.class, "createTime", false, "CREATE_TIME");
        public final static Property Star = new Property(5, String.class, "star", false, "STAR");
        public final static Property TypeDto = new Property(6, String.class, "typeDto", false, "TYPE_DTO");
        public final static Property LevelDto = new Property(7, String.class, "levelDto", false, "LEVEL_DTO");
        public final static Property AppPackageName = new Property(8, String.class, "appPackageName", false, "APP_PACKAGE_NAME");
    };


    public AppPackageDao(DaoConfig config) {
        super(config);
    }
    
    public AppPackageDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'APP_PACKAGE' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'NAME' TEXT," + // 1: name
                "'DEVELOPER' TEXT," + // 2: developer
                "'DESCRIPTION' TEXT," + // 3: description
                "'CREATE_TIME' INTEGER," + // 4: createTime
                "'STAR' TEXT," + // 5: star
                "'TYPE_DTO' TEXT," + // 6: typeDto
                "'LEVEL_DTO' TEXT," + // 7: levelDto
                "'APP_PACKAGE_NAME' TEXT);"); // 8: appPackageName
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'APP_PACKAGE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, AppPackage entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String developer = entity.getDeveloper();
        if (developer != null) {
            stmt.bindString(3, developer);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(4, description);
        }
 
        java.util.Date createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindLong(5, createTime.getTime());
        }
 
        String star = entity.getStar();
        if (star != null) {
            stmt.bindString(6, star);
        }
 
        String typeDto = entity.getTypeDto();
        if (typeDto != null) {
            stmt.bindString(7, typeDto);
        }
 
        String levelDto = entity.getLevelDto();
        if (levelDto != null) {
            stmt.bindString(8, levelDto);
        }
 
        String appPackageName = entity.getAppPackageName();
        if (appPackageName != null) {
            stmt.bindString(9, appPackageName);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public AppPackage readEntity(Cursor cursor, int offset) {
        AppPackage entity = new AppPackage( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // developer
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // description
            cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)), // createTime
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // star
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // typeDto
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // levelDto
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // appPackageName
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, AppPackage entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDeveloper(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDescription(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCreateTime(cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)));
        entity.setStar(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setTypeDto(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setLevelDto(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setAppPackageName(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(AppPackage entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(AppPackage entity) {
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
    
}
