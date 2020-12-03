package com.spiwer.androidrosilla.proxy;

import com.spiwer.androidrosilla.database.ConnectionManager;
import com.spiwer.androidrosilla.database.DatabaseManager;
import com.spiwer.androidrosilla.exception.JdbcException;
import com.spiwer.androidrosilla.lasting.EMessageRosilla;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static com.spiwer.androidrosilla.database.DatabaseManager.db;


@SuppressWarnings("SynchronizeOnNonFinalField")
class ProxyHandlerUtil implements InvocationHandler {

    public Object object;

    protected ProxyHandlerUtil(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (DatabaseManager.connectionManager == null) {
            throw new JdbcException(EMessageRosilla.ERROR_DATABASE_CONNECTION_MANAGER);
        }
        ConnectionManager connection = DatabaseManager.connectionManager;
        if (db == null || !db.isOpen()) {
            db = connection.open();
        }
        synchronized (db) {
            try {
                db.beginTransaction();
                Object response = method.invoke(object, args);
                ConnectionManager.commit(db);
                return response;
            } catch (Throwable e) {
                e.printStackTrace();
                throw e;
            } finally {
                ConnectionManager.close(db);
            }
        }
    }
}
