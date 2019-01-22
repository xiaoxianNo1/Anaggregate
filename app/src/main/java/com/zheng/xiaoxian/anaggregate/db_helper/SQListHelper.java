package com.zheng.xiaoxian.anaggregate.db_helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQListHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Anaggregate";
    private static final int VERSION = 1;


    public SQListHelper(Context context){
        super(context, DB_NAME, null, VERSION);
    }
    public SQListHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "create table mobileHistory(_id integer primary key autoincrement,telString text,province text,catName text,carrier text,click INTEGER)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
