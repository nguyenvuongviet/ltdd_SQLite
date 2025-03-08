package com.example.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {

    //khoi tao co so du lieu
    public DatabaseHandler(@Nullable Context context, @Nullable String name,
                           @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // truy van khong tra ve ket qua
    public void QueryData(String sql) {
        SQLiteDatabase database = getWritableDatabase(); // Mở database ở chế độ ghi
        database.execSQL(sql); // Thực thi câu lệnh SQL
    }

    // truy van co tra ve ket qua
    public Cursor GetData(String sql) {
        SQLiteDatabase database = getReadableDatabase(); // Mở database ở chế độ đọc
        return database.rawQuery(sql, null); // Thực thi truy vấn và trả về Cursor chứa dữ liệu
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
       }
}
