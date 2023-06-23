package com.spiwer.spiwerlibraries;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.spiwer.androidrosilla.database.ConnectionManager;
import com.spiwer.androidrosilla.database.DatabaseManager;
import com.spiwer.androidrosilla.exception.JdbcException;
import com.spiwer.androidrosilla.template.BaseQuery;
import com.spiwer.androidrosilla.util.Retrieve;
import com.spiwer.androidstandard.util.FileUtil;

import java.io.InputStream;
import java.util.List;

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
            String sql = "SELECT ent.* " +
                    "FROM entity ent ";
            List<Entity> entityList = DatabaseManager.executeList(sql, new BaseQuery<Entity>() {
                @Override
                public Entity next(Retrieve retrieve) throws JdbcException {
                    return Entity.fill(retrieve);
                }
            });
            Log.i("List", "" + entityList);
            ConnectionManager.commit(db);
        } catch (Exception ex) {
            Log.e("open", ex.getMessage(), ex);
        }
    }
}