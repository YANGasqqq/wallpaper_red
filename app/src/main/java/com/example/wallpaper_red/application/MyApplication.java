package com.example.wallpaper_red.application;

import android.app.Application;

import com.example.wallpaper_red.sql.DatabaseHelper;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;


public class MyApplication extends Application {

    private DatabaseHelper dbHelper;
    @Override
    public void onCreate() {
        super.onCreate();
         dbHelper=new DatabaseHelper(this);
        SQLiteStudioService.instance().start(this);

    }
    public DatabaseHelper getDbHelper() {
        return dbHelper;
    }
}
