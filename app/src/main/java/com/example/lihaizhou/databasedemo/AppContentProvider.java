package com.example.lihaizhou.databasedemo;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;


/**
 * Created by lihaizhou on 2017/9/4.
 */

public class AppContentProvider extends ContentProvider {

    private static final String AUTHORITY = "displayapp";
    private static final String TABLE_APP = "appinfo";
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int APP = 0;

    static
    {
        uriMatcher.addURI(AUTHORITY,TABLE_APP,APP);
        //这里暂时只添加一个Uri
    }


    @Override
    public boolean onCreate() {
        DatabaseManager.getInstance(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case APP://操作数据库app表 返回 查询到所有集合
                cursor = DatabaseManager.getInstance(getContext()).query(TABLE_APP,
                        projection,selection,selectionArgs,null,null,sortOrder,null);
                break;
            default:
                break;

        }
        if(cursor != null){
            notifyChange(uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = uriMatcher.match(uri);
        String type = "";
        switch (match)
        {
            case APP:
                type = "appinfo";
                break;
            //...
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return type;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if(uriMatcher.match(uri) == APP)
        {
            int rowId = DatabaseManager.getInstance(getContext()).insert(TABLE_APP,null,values);
            if(rowId >= 0)
            {
                notifyChange(uri);
                return ContentUris.withAppendedId(uri,rowId);
            }
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int resultCode = -1;
        switch (uriMatcher.match(uri))
        {
            case APP:
                resultCode = DatabaseManager.getInstance(getContext()).delete(TABLE_APP,selection,selectionArgs);
                break;
            default:
                break;
        }
        if(resultCode>=0)
        {
            notifyChange(uri);
        }
        return resultCode;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int resultCode = -1;
        switch (uriMatcher.match(uri))
        {
            case APP:
                resultCode = DatabaseManager.getInstance(getContext()).update(TABLE_APP,values,selection,selectionArgs);
                break;
            default:
                break;
        }
        if(resultCode>=0)
        {
            notifyChange(uri);
        }
        return resultCode;
    }

    private void notifyChange(Uri uri)
    {
        if(getContext()!=null)
        {
            ContentResolver contentResolver = getContext().getContentResolver();
            if(contentResolver!=null)
            {
                contentResolver.notifyChange(uri,null);//第二个参数表示指定的通知接收者，当输入null时表示不指定，所有接收者都可接收通知
            }
        }
    }

}
