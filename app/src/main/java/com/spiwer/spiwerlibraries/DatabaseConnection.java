package com.spiwer.spiwerlibraries;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseConnection extends SQLiteOpenHelper {

    public DatabaseConnection(@Nullable Context context) {
        super(context, "SpiwerRosilla", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public SQLiteDatabase open() {
        return getWritableDatabase();
    }
}
