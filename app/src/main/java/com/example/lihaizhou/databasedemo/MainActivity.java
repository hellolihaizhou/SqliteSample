package com.example.lihaizhou.databasedemo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity{
    private EditText editCalendarDiplay;
    private EditText editCalculatorDiplay;
    private EditText editNoteDiplay;
    private EditText editRadioDiplay;
    private Button insertBtn,updateDisplayBtn;

    private ContentResolver contentResolver;

    private static final String AUTHORITY = "displayapp";
    //appinfo数据改变后指定通知的Uri
    public static final Uri APP_CONTENT_URL = Uri.parse("content://"+AUTHORITY+"/appinfo");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        contentResolver = getContentResolver();
        editCalendarDiplay = (EditText) findViewById(R.id.editText);
        editCalculatorDiplay = (EditText) findViewById(R.id.editText2);
        editNoteDiplay = (EditText) findViewById(R.id.editText3);
        editRadioDiplay = (EditText) findViewById(R.id.editText4);
        updateDisplayBtn = (Button) findViewById(R.id.updateAppBtn);
        insertBtn = (Button) findViewById(R.id.insertBtn);

        String [] column = new String[]{"_id", "calendar", "calculator","note", "radio"};
        Cursor appInfoCursor =contentResolver.query(APP_CONTENT_URL, column, null, null, null);//这一步会走到AppContentProvider中的query方法
        if(appInfoCursor.moveToFirst()){
            do{
                editCalendarDiplay.setText(appInfoCursor.getString(appInfoCursor.getColumnIndex("calendar")));
                editCalculatorDiplay.setText(appInfoCursor.getString(appInfoCursor.getColumnIndex("calculator")));
                editNoteDiplay.setText(appInfoCursor.getString(appInfoCursor.getColumnIndex("note")));
                editRadioDiplay.setText(appInfoCursor.getString(appInfoCursor.getColumnIndex("radio")));
            }while(appInfoCursor.moveToNext());
        }
        if (!appInfoCursor.isClosed())  {
            appInfoCursor.close();
        }


        updateDisplayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("calendar",editCalendarDiplay.getText().toString());
                contentValues.put("calculator",editCalculatorDiplay.getText().toString());
                contentValues.put("note",editNoteDiplay.getText().toString());
                contentValues.put("radio",editRadioDiplay.getText().toString());
                contentResolver.update(APP_CONTENT_URL,contentValues,null,null);
            }
        });

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("calendar",editCalendarDiplay.getText().toString());
                contentValues.put("calculator",editCalculatorDiplay.getText().toString());
                contentValues.put("note",editNoteDiplay.getText().toString());
                contentValues.put("radio",editRadioDiplay.getText().toString());
                contentResolver.insert(APP_CONTENT_URL,contentValues);
            }
        });

    }
}
