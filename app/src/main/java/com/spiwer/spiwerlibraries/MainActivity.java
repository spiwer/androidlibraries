package com.spiwer.spiwerlibraries;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.spiwer.androidrosilla.database.ConnectionManager;
import com.spiwer.androidrosilla.database.DatabaseManager;
import com.spiwer.androidrosilla.dto.Param;
import com.spiwer.androidstandard.util.FileUtil;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        copyDatabase(true);
        open();
    }

    public void copyDatabase(boolean force) {
        try {
            InputStream inputStream = getAssets().open("SpiwerRosilla.db");
            FileUtil.validateDatabase(this, "SpiwerRosilla", inputStream, force);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void open() {
        try {
            DatabaseConnection cnn = new DatabaseConnection(this);
            SQLiteDatabase db = cnn.open();
            db.beginTransaction();
            DatabaseManager.db = db;
            Param<String, Object> params = DatabaseManager.params()
                    .add("name", "Power")
                    .add("code", 1);
            DatabaseManager.executeUpdate(
                    "UPDATE entity SET ent_name = :name WHERE ent_code = :code  "
                    , params);
            ConnectionManager.commit(db);
        } catch (Exception ex) {
            Log.e("open", ex.getMessage(), ex);
        }
    }
}