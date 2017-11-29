package com.meiji.toutiao.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.database.table.MediaChannelTable;
import com.meiji.toutiao.database.table.NewsChannelTable;
import com.meiji.toutiao.database.table.SearchHistoryTable;

/**
 * Created by Meiji on 2017/3/10.
 * 说明：
 * 虽然数据库的名字和版本都是在该类的构造函数中传入，但是数据库实际被创建是在该类的getWritableDatabase()或
 * getReadableDatabase()方法第一次被调用时。
 * 当调用SQLiteOpenHelper的getWritableDatabase()或者getReadableDatabase()方法获取用于操作数据库
 * 的SQLiteDatabase实例的时候，如果数据库不存在，Android系统会自动生成一个数据库，接着调用onCreate()方法。
 * onCreate()方法在初次生成数据库时才会被调用，在onCreate()方法里可以生成数据库表结构及添加一些应用使用到的
 * 初始化数据。onUpgrade()方法在数据库的版本发生变化时会被调用，一般在软件升级时才需改变版本号，而数据库的版
 * 本是由程序员控制的。
 *
 * SQLiteDatabase类：
 * execSQL()方法可以执行insert、delete、update和CREATE TABLE之类有更改行为的SQL语句；
 * rawQuery()方法用于执行select语句。
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Toutiao";
    private static final int DB_VERSION = 5;
    private static final String CLEAR_TABLE_DATA = "delete from ";
    private static final String DROP_TABLE = "drop table if exists ";
    private static DatabaseHelper instance = null;
    private static SQLiteDatabase db = null;

    /**
     * @param context 上下文环境
     * @param name 数据库名字
     * @param factory 游标工厂（可选）
     * @param version 数据库模型版本号
     */
    private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private static synchronized DatabaseHelper getInstance() {
        if (instance == null) {
            // 数据库实际被创建是在getWritableDatabase()或getReadableDatabase()方法调用时
            instance = new DatabaseHelper(InitApp.AppContext, DB_NAME, null, DB_VERSION);
        }
        return instance;
    }

    public static synchronized SQLiteDatabase getDatabase() {
        if (db == null) {
            //用SQLiteOpenHelper 类中的 getWritableDatabase()和getReadableDatabase()方法可以获得数据库的引用。
            db = getInstance().getWritableDatabase();
        }
        return db;
    }

    public static synchronized void closeDatabase() {
        if (db != null) {
            db.close();
        }
    }

    //onCreate()方法在初次生成数据库时才会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NewsChannelTable.CREATE_TABLE);
        db.execSQL(MediaChannelTable.CREATE_TABLE);
        db.execSQL(SearchHistoryTable.CREATE_TABLE);
    }

    //onUpgrade()方法在数据库的版本发生变化时会被调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL(MediaChannelTable.CREATE_TABLE);
                break;
            case 2:
                db.execSQL(CLEAR_TABLE_DATA + NewsChannelTable.TABLENAME);
                break;
            case 3:
                ContentValues values = new ContentValues();
                values.put(NewsChannelTable.ID, "");
                values.put(NewsChannelTable.NAME, "推荐");
                values.put(NewsChannelTable.IS_ENABLE, 0);
                values.put(NewsChannelTable.POSITION, 46);
                db.insert(NewsChannelTable.TABLENAME, null, values);
                break;
            case 4:
                db.execSQL(SearchHistoryTable.CREATE_TABLE);
                break;
        }
    }
}
