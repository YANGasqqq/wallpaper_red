package com.example.wallpaper_red.sql;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "picSql1.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE =
            "CREATE TABLE pic_tab (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "img_url TEXT," +
                    "like_Num INTEGER," +
                    "is_like INTEGER," +
                    "is_collect INTEGER," +
                    "is_history INTEGER" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public List<Picture> getHistoryPictures() {
        List<Picture> pictureList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {"id", "img_url", "like_Num", "is_like", "is_collect", "is_history"};
        String selection = "is_history = ?";
        String[] selectionArgs = {"1"}; // 1 表示 true

        Cursor cursor = db.query(
                "pic_tab",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range")      String imgUrl = cursor.getString(cursor.getColumnIndex("img_url"));
                @SuppressLint("Range")   int likeNum = cursor.getInt(cursor.getColumnIndex("like_Num"));
                @SuppressLint("Range")  boolean isLike = cursor.getInt(cursor.getColumnIndex("is_like")) == 1;
                @SuppressLint("Range")  boolean isCollect = cursor.getInt(cursor.getColumnIndex("is_collect")) == 1;
                @SuppressLint("Range")   boolean isHistory = cursor.getInt(cursor.getColumnIndex("is_history")) == 1;
                @SuppressLint("Range")  Picture picture = new Picture(imgUrl, likeNum, isLike, isCollect, isHistory);
                pictureList.add(picture);
            }
            cursor.close();
        }

        db.close();
        return pictureList;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }
    public Cursor executeRawQuery(String sql, String[] selectionArgs) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        return cursor;
    }
    public boolean isLikeByUrl(String imgUrl) {
        boolean isLike = false;
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {"is_like"};
        String selection = "img_url = ?";
        String[] selectionArgs = {imgUrl};

        Cursor cursor = db.query(
                "pic_tab",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int isLikeValue = cursor.getInt(cursor.getColumnIndex("is_like"));
            isLike = (isLikeValue != 0); // 将整数值转换为布尔值
            cursor.close();
        }

        db.close();
        return isLike;
    }
    public int updateIsLikeToFalse(int id) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("is_like", 0); // 0 表示 false

        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};

        // 执行更新操作，并返回受影响的行数
        int updatedRows = db.update("pic_tab", values, whereClause, whereArgs);

        db.close();
        return updatedRows;
    }
    @SuppressLint("Range")
    public int getLikeNumByImgUrl(String imgUrl) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {"like_Num"};
        String selection = "img_url = ?";
        String[] selectionArgs = {imgUrl};

        Cursor cursor = db.query(
                "pic_tab",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int likeNum = 0; // 默认值，表示没有找到符合条件的记录

        if (cursor.moveToFirst()) {
            // 只处理第一条找到的记录
            likeNum = cursor.getInt(cursor.getColumnIndex("like_Num"));
        }

        cursor.close();
        db.close();
        return likeNum;
    }
    public void insertOrUpdatePicture(Picture picture) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("like_Num", picture.getLikeNum());
        values.put("is_like", picture.isLike() ? 1 : 0);
        values.put("is_collect", picture.isCollect() ? 1 : 0);
        values.put("is_history", picture.isHistory() ? 1 : 0);

        // 查询数据库中是否已存在相同 img_url 的记录
        Cursor cursor = db.query("pic_tab", null, "img_url=?", new String[]{picture.getImgUrl()}, null, null, null);

        if (cursor.moveToFirst()) {
            // 记录已存在，只更新该记录的其他字段
            @SuppressLint("Range") int rowId = cursor.getInt(cursor.getColumnIndex("id"));
            String whereClause = "id=?";
            String[] whereArgs = {String.valueOf(rowId)};
            db.update("pic_tab", values, whereClause, whereArgs);
        } else {
            // 记录不存在，插入新的记录
            values.put("img_url", picture.getImgUrl());
            db.insert("pic_tab", null, values);
        }

        cursor.close();
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 如果数据库版本升级，可以在这里进行数据迁移操作
    }
    public long insertPicture(Picture picture) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("img_url", picture.getImgUrl());
        values.put("like_Num", picture.getLikeNum());
        values.put("is_like", picture.isCollect() ? 1 : 0);
        values.put("is_collect", picture.isCollect() ? 1 : 0);
        values.put("is_history", picture.isHistory() ? 1 : 0);

        // 插入数据并返回插入行的 ID
        long insertedId = db.insert("pic_tab", null, values);

        db.close();
        return insertedId;
    }
}
