package com.example.lihaizhou.databasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by lihaizhou on 2017/9/4.
 */

public class DatabaseManager {

    private static volatile DatabaseManager instance = null;

    private DatabaseOpenHelper databaseOpenHelper;

    private DatabaseManager(Context context)
    {
        databaseOpenHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseManager getInstance(Context context)
    {
        if(instance == null)
        {
            synchronized (DatabaseManager.class)
            {
                if(instance == null)
                {

                    instance = new DatabaseManager(context);
                }
            }
        }
        return instance;
    }

    public int insert(String table, String nullColumnhack,ContentValues values)
    {
        SQLiteDatabase sqLiteDatabase = getDatabase();
        if(sqLiteDatabase!=null)
        {
            return (int) sqLiteDatabase.insert(table,nullColumnhack,values);
        }
        return -1;
    }

    public int delete(String table,String whereclause,String[] whereArgs)
    {
        SQLiteDatabase sqLiteDatabase = getDatabase();
        if(sqLiteDatabase!=null)
        {
            return sqLiteDatabase.delete(table,whereclause,whereArgs);
        }
        return -1;
    }

    public Cursor query(String table, String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy, String limit){
        SQLiteDatabase database = getDatabase();
        if(database != null) {
            return  database.query(table, columns, selection,
                    selectionArgs, groupBy, having,
                    orderBy, limit);
        }
        return null;
    }

    public int update(String table, ContentValues values, String whereClause, String[] whereArgs){
        SQLiteDatabase database = getDatabase();
        if(database!=null) {
            return database.update(table, values, whereClause, whereArgs);
        }
        return -1;
    }

    public Cursor queryAll(String table,String[] columns){
        SQLiteDatabase database = getDatabase();
        if(database != null) {
            return  database.query(table, columns, null,
                    null, null, null,
                    null, null);
        }
        return null;
    }

    private SQLiteDatabase getDatabase()
    {
        if(databaseOpenHelper != null)
        {
            Log.d("DatabaseManager", "databaseOpenHelper != null");
            return databaseOpenHelper.getWritableDatabase();
        }
        return null;
    }

}
