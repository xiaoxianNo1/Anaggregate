package com.example.xiaoxian.autoredpackhelper.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 简介：
 * 作者：郑现文
 * 创建时间：2019/1/28/0028 21:49
 **/
public class SqlHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "autoredpack";
    private static final int VERSION = 1;

    public SqlHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "create table user(_id integer primary key autoincrement,code text,create_time text)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
