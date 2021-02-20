package com.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MemberInfo extends SQLiteOpenHelper {
    final static String TAG = "Memberinfo";

    public MemberInfo(@Nullable Context context) { // null = 써도 되고 안써도 됨
        super(context, "MemberInfo.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(TAG, "onCreate()");
        String query = "CREATE TABLE member(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, userid TEXT, passwd INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(TAG, "upgrade()");
        String query = "DROP TABLE IF EXISTS member;";
        db.execSQL(query);
        onCreate(db);
    }
}
