package com.example.jack.musicdemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jack.musicdemo.db.dao.CollectionDao;
import com.example.jack.musicdemo.db.dao.CollectionShipDao;
import com.example.jack.musicdemo.db.dao.SongDao;


/**
 * 数据库帮助类
 * 运用的知识点是 SQLite，使用要创建一个类去继承 SQLiteOpenHelper
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASENAME = "MusicDatabase.db";
    private static final int DATABASEVERSION = 1;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    //执行建表语句
    @Override
    public void onCreate(SQLiteDatabase db) {
        //用户表
//        db.execSQL(AccountDao.createTable());
//        db.execSQL(AccountDao.createIndex());
        //歌曲表
        db.execSQL(SongDao.createTable());
        db.execSQL(SongDao.createIndex());
        //收藏夹表
        db.execSQL(CollectionDao.createTable());
        //收藏夹关联表
        db.execSQL(CollectionShipDao.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("ALTER TABLE note ADD COLUMN marktes integer");//增减一项 保存用户数据
        //onCreate(db);
    }
}
