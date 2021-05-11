package com.spiwer.androidrosilla.database;

import android.database.sqlite.SQLiteDatabase;

import com.spiwer.androidrosilla.exception.JdbcException;
import com.spiwer.androidrosilla.lasting.EMessageRosilla;

public abstract class ConnectionManager {

    public abstract SQLiteDatabase open() throws JdbcException;

    public static void commit(SQLiteDatabase db) throws JdbcException {
        try {
            if (!db.inTransaction()) {
                return;
            }
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new JdbcException(EMessageRosilla.ERROR_DATABASE_COMMIT);
        }
    }


    public static void close(SQLiteDatabase db) {
        try {
            if (db == null) {
                return;
            }
            if (db.inTransaction()) {
                db.endTransaction();
            }
            db.close();
            db.releaseReference();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
