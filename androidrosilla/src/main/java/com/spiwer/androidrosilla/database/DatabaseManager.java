package com.spiwer.androidrosilla.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.spiwer.androidrosilla.dto.Criteria;
import com.spiwer.androidrosilla.dto.Param;
import com.spiwer.androidrosilla.exception.JdbcException;
import com.spiwer.androidrosilla.lasting.EMessageRosilla;
import com.spiwer.androidrosilla.template.BaseQuery;
import com.spiwer.androidrosilla.template.EntityManager;
import com.spiwer.androidrosilla.template.SQLName;
import com.spiwer.androidrosilla.util.Retrieve;
import com.spiwer.androidrosilla.util.StatementNamed;
import com.spiwer.androidstandard.exception.AppException;
import com.spiwer.androidstandard.lasting.ETypePrimaryKey;
import com.spiwer.androidstandard.template.IGenericMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class DatabaseManager {
    public final static int ZERO = 0;
    public final static int ONE = 1;
    public final static int NO_RESULTS = ZERO;
    public final static String EMPTY = "";
    public final static Object BLOCK = new Object();
    private static final Map<String, String> STATEMENTS = new HashMap<>();
    public volatile static SQLiteDatabase db;
    public volatile static ConnectionManager connectionManager;
    public static boolean showLog = true;


    public static void addStatements(Map<String, String> statements) {
        STATEMENTS.putAll(statements);
    }

    public static Param<String, Object> params() {
        return new Param<>();
    }


    public static Criteria criteria() {
        return new Criteria();
    }

    private static <T> void noResults(BaseQuery<T> baseQuery, IGenericMessage message) throws JdbcException {
        if (message == null) {
            baseQuery.noResults();
            return;
        }
        throw new JdbcException(message);
    }

    private static String getSql(SQLName sql) throws JdbcException {
        String infoSql = STATEMENTS.get(sql.getName());
        if (infoSql == null) {
            throw new JdbcException(EMessageRosilla.ERROR_QUERY_NOT_FOUND);
        }

        return infoSql;
    }

    public static <T> List<T> executeList(String sql, BaseQuery<T> baseQuery)
            throws JdbcException {
        return executeList(sql, null, baseQuery, null);
    }

    public static <T> List<T> executeList(SQLName sql, BaseQuery<T> baseQuery)
            throws JdbcException {
        return executeList(getSql(sql), null, baseQuery, null);
    }

    public static <T> List<T> executeList(String sql, BaseQuery<T> baseQuery, IGenericMessage message)
            throws JdbcException {
        return executeList(sql, null, baseQuery, message);
    }

    public static <T> List<T> executeList(SQLName sql, BaseQuery<T> baseQuery, IGenericMessage message)
            throws JdbcException {
        return executeList(getSql(sql), null, baseQuery, message);
    }

    public static <T> List<T> executeList(String sql, Param<String, Object> params, BaseQuery<T> baseQuery)
            throws JdbcException {
        return executeList(sql, params, baseQuery, null);
    }

    public static <T> List<T> executeList(SQLName sql, Param<String, Object> params, BaseQuery<T> baseQuery)
            throws JdbcException {
        return executeList(getSql(sql), params, baseQuery, null);
    }

    public static <T> List<T> executeList(SQLName sql, Param<String, Object> params, BaseQuery<T> baseQuery, IGenericMessage message)
            throws JdbcException {
        return executeList(getSql(sql), params, baseQuery, message);
    }

    public static <T> List<T> executeList(String sql, Param<String, Object> params, BaseQuery<T> baseQuery, IGenericMessage message)
            throws JdbcException {
        if (showLog) {
            Log.i("SQL:", sql + " params: " + params);
        }
        List<T> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            StatementNamed statementNamed = new StatementNamed(db, sql);
            if (params != null) {
                Set<String> keys = params.keySet();
                for (String key : keys) {
                    statementNamed.setObject(key, params.get(key));
                }
            }
            cursor = statementNamed.executeQuery();
            if (cursor == null || cursor.getCount() == NO_RESULTS) {
                noResults(baseQuery, message);
                return list;
            }
            cursor.moveToFirst();
            Retrieve retrieve = new Retrieve(cursor);
            do {
                T row = baseQuery.next(retrieve);
                list.add(row);
            } while (cursor.moveToNext());

            return list;
        } catch (JdbcException ex) {
            baseQuery.error(ex);
            return list;
        } catch (Exception e) {
            Log.e("executeList", e.getMessage(), e);
            baseQuery.error(e);
            return list;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static <T> T executeSingle(String sql, BaseQuery<T> baseQuery)
            throws JdbcException {
        return executeSingle(sql, null, baseQuery, null);
    }

    public static <T> T executeSingle(SQLName sql, BaseQuery<T> baseQuery)
            throws JdbcException {
        return executeSingle(getSql(sql), null, baseQuery, null);
    }

    public static <T> List<T> executeSingle(String sql, BaseQuery<T> baseQuery, IGenericMessage message)
            throws JdbcException {
        return executeList(sql, null, baseQuery, message);
    }

    public static <T> T executeSingle(SQLName sql, BaseQuery<T> baseQuery, IGenericMessage message)
            throws JdbcException {
        return executeSingle(getSql(sql), null, baseQuery, message);
    }

    public static <T> T executeSingle(String sql, Param<String, Object> params, BaseQuery<T> baseQuery)
            throws JdbcException {
        return executeSingle(sql, params, baseQuery, null);
    }

    public static <T> T executeSingle(SQLName sql, Param<String, Object> params, BaseQuery<T> baseQuery)
            throws JdbcException {
        return executeSingle(getSql(sql), params, baseQuery, null);
    }

    public static <T> T executeSingle(SQLName sql, Param<String, Object> params, BaseQuery<T> baseQuery, IGenericMessage message)
            throws JdbcException {
        return executeSingle(getSql(sql), params, baseQuery, message);
    }

    public static <T> T executeSingle(String sql, Param<String, Object> params, BaseQuery<T> baseQuery, IGenericMessage message)
            throws JdbcException {
        List<T> list = executeList(sql, params, baseQuery, message);
        if (list.isEmpty()) {
            return null;
        }
        if (list.size() >= 2) {
            throw new JdbcException(EMessageRosilla.ERROR_QUERY_MANY);
        }
        return list.get(ZERO);
    }


    public static <T> T executeFree(String sql, BaseQuery<T> baseQuery)
            throws JdbcException {
        return executeFree(sql, null, baseQuery, null);
    }

    public static <T> T executeFree(SQLName sql, BaseQuery<T> baseQuery)
            throws JdbcException {
        return executeFree(getSql(sql), null, baseQuery, null);
    }

    public static <T> T executeFree(String sql, BaseQuery<T> baseQuery, IGenericMessage message)
            throws JdbcException {
        return executeFree(sql, null, baseQuery, message);
    }

    public static <T> T executeFree(SQLName sql, BaseQuery<T> baseQuery, IGenericMessage message)
            throws JdbcException {
        return executeFree(getSql(sql), null, baseQuery, message);
    }

    public static <T> T executeFree(String sql, Param<String, Object> params, BaseQuery<T> baseQuery)
            throws JdbcException {
        return executeFree(sql, params, baseQuery, null);
    }

    public static <T> T executeFree(SQLName sql, Param<String, Object> params, BaseQuery<T> baseQuery)
            throws JdbcException {
        return executeFree(getSql(sql), params, baseQuery, null);
    }

    public static <T> T executeFree(SQLName sql, Param<String, Object> params, BaseQuery<T> baseQuery, IGenericMessage message)
            throws JdbcException {
        return executeFree(getSql(sql), params, baseQuery, message);
    }

    public static <T> T executeFree(String sql, Param<String, Object> params, BaseQuery<T> baseQuery, IGenericMessage message)
            throws JdbcException {
        try {
            StatementNamed statementNamed = new StatementNamed(db, sql);
            if (params != null) {
                Set<String> keys = params.keySet();
                for (String key : keys) {
                    statementNamed.setObject(key, params.get(key));
                }
            }
            Cursor cursor = statementNamed.executeQuery();
            if (cursor == null || cursor.getCount() == NO_RESULTS) {
                noResults(baseQuery, message);
                return null;
            }
            cursor.moveToFirst();
            Retrieve retrieve = new Retrieve(cursor);
            return baseQuery.next(retrieve);
        } catch (JdbcException ex) {
            baseQuery.error(ex);
            throw ex;
        } catch (Exception e) {
            Log.e("executeFree", e.getMessage(), e);
            baseQuery.error(e);
            return null;
        }
    }

    private static void setParameters(ContentValues values, String name, Object value) {

        if (value instanceof Byte) {
            values.put(name, ((Byte) value));
            return;
        }
        if (value instanceof Short) {
            values.put(name, ((Short) value));
            return;
        }
        if (value instanceof Integer) {
            values.put(name, ((Integer) value));
            return;
        }
        if (value instanceof Long) {
            values.put(name, ((Long) value));
            return;
        }
        if (value instanceof Float) {
            values.put(name, ((Float) value));
            return;
        }
        if (value instanceof Double) {
            values.put(name, ((Double) value));
            return;
        }
        if (value instanceof byte[]) {
            values.put(name, ((byte[]) value));
            return;
        }
        String data = value == null ? null : String.valueOf(value);
        values.put(name, data);
    }

    public static void insert(EntityManager<?> entityManager) throws JdbcException {
        try {
            ContentValues values = new ContentValues();
            for (String field : entityManager.listFields()) {
                setParameters(values, field, entityManager.getValue(field));
            }
            long id = db.insert(entityManager.tableName(), null, values);
            if (id == -1) {
                throw new JdbcException(EMessageRosilla.ERROR_INSERT);
            }
            if (entityManager.primaryKey().getType() == ETypePrimaryKey.AUTOINCREMENT) {
                entityManager.primaryKey(id);
            }
        } catch (JdbcException ex) {
            throw ex;
        } catch (Throwable e) {
            Log.e("insert", e.getMessage(), e);
            throw new JdbcException(EMessageRosilla.ERROR_INSERT);
        }
    }

    public static void edit(EntityManager<?> entityManager, Param<String, Object> params) throws JdbcException {
        try {
            ContentValues values = new ContentValues();
            Set<String> fields = entityManager.listFields();
            for (String field : fields) {
                setParameters(values, field, entityManager.getValue(field));
            }
            String[] args = new String[params.size()];
            StringBuilder where = new StringBuilder();
            Set<String> keys = params.keySet();
            int index = 0;
            for (String key : keys) {
                where.append(key)
                        .append(" = ? AND ");
                args[index++] = String.valueOf(params.get(key));
            }
            where.append("1=1");
            int quantity = db.update(entityManager.tableName(), values, where.toString(), args);
            if (quantity > ONE) {
                throw new JdbcException(EMessageRosilla.ERROR_EDIT_MANY);
            }
        } catch (JdbcException ex) {
            throw ex;
        } catch (AppException e) {
            Log.e("edit", e.getMessage(), e);
            throw new JdbcException(EMessageRosilla.ERROR_EDIT);
        }
    }


    public static void delete(EntityManager<?> entityManager) throws JdbcException {
        try {
            String primaryKey = entityManager.primaryKey().getColumnName();
            String where = primaryKey + " = ? ";
            Object key = entityManager.getValue(primaryKey);
            if (key == null) {
                throw new JdbcException(EMessageRosilla.ERROR_PRIMARY_KEY_EMPTY);
            }
            String[] args = {String.valueOf(key)};
            int quantity = db.delete(entityManager.tableName(), where, args);
            if (quantity > ONE) {
                throw new JdbcException(EMessageRosilla.ERROR_EDIT_MANY);
            }
        } catch (JdbcException ex) {
            throw ex;
        } catch (AppException e) {
            Log.e("delete", e.getMessage(), e);
            throw new JdbcException(EMessageRosilla.ERROR_EDIT);
        }
    }

    public static void executeUpdate(String sql, Param<String, Object> params) throws JdbcException {
        try {
            StatementNamed statementNamed = new StatementNamed(db, sql);
            if (params != null) {
                Set<String> keys = params.keySet();
                for (String key : keys) {
                    statementNamed.setObject(key, params.get(key));
                }
            }
            statementNamed.executeUpdate();
        } catch (Exception e) {
            Log.e("executeUpdate", e.getMessage(), e);
            throw new JdbcException(EMessageRosilla.ERROR_EDIT);
        }
    }

    public static <T extends EntityManager<?>> T getEntity(SQLName sql, final EntityManager<?> manager) throws JdbcException {
        return getEntity(getSql(sql), null, manager, null);
    }

    public static <T extends EntityManager<?>> T getEntity(SQLName sql, final EntityManager<?> manager, IGenericMessage message) throws JdbcException {
        return getEntity(getSql(sql), null, manager, message);
    }

    public static <T extends EntityManager<?>> T getEntity(SQLName sql,
                                                           Param<String, Object> params,
                                                           final EntityManager<?> manager) throws JdbcException {
        return getEntity(getSql(sql), params, manager, null);
    }

    public static <T extends EntityManager<?>> T getEntity(SQLName sql, Param<String, Object> params,
                                                           final EntityManager<?> manager, IGenericMessage message) throws JdbcException {
        return getEntity(getSql(sql), params, manager, message);
    }

    public static <T extends EntityManager<?>> T getEntity(String sql, final EntityManager<?> manager) throws JdbcException {
        return getEntity(sql, null, manager, null);
    }

    public static <T extends EntityManager<?>> T getEntity(String sql, final EntityManager<?> manager, IGenericMessage message) throws JdbcException {
        return getEntity(sql, null, manager, message);
    }

    public static <T extends EntityManager<?>> T getEntity(String sql,
                                                           Param<String, Object> params,
                                                           final EntityManager<?> manager) throws JdbcException {
        return getEntity(sql, params, manager, null);
    }

    public static <T extends EntityManager<?>> T getEntity(String sql,
                                                           Param<String, Object> params,
                                                           final EntityManager<?> manager,
                                                           IGenericMessage message) throws JdbcException {
        return executeSingle(sql, params, new BaseQuery<T>() {
            @Override
            public T next(Retrieve retrieve) throws JdbcException {
                return (T) manager.getRegister(retrieve);
            }
        }, message);
    }

    public static <T extends EntityManager<?>> List<T> getEntityList(SQLName sql, final Class<T> className)
            throws JdbcException {
        return getEntityList(sql, null, className, null);
    }

    public static <T extends EntityManager<?>> List<T> getEntityList(SQLName sql, final Class<T> className,
                                                                     IGenericMessage message) throws JdbcException {
        return getEntityList(sql, null, className, message);
    }

    public static <T extends EntityManager<?>> List<T> getEntityList(SQLName sql,
                                                                     Param<String, Object> params,
                                                                     final Class<T> className) throws JdbcException {
        return getEntityList(sql, params, className, null);
    }

    public static <T extends EntityManager<?>> List<T> getEntityList(SQLName sql, Param<String, Object> params,
                                                                     final Class<T> className, IGenericMessage message) throws JdbcException {
        return getEntityList(getSql(sql), params, className, message);
    }

    public static <T extends EntityManager<?>> List<T> getEntityList(String sql, final Class<T> className) throws JdbcException {
        return getEntityList(sql, null, className, null);
    }

    public static <T extends EntityManager<?>> List<T> getEntityList(String sql, final Class<T> className, IGenericMessage message) throws JdbcException {
        return getEntityList(sql, null, className, message);
    }

    public static <T extends EntityManager<?>> List<T> getEntityList(String sql,
                                                                     Param<String, Object> params,
                                                                     final Class<T> className) throws JdbcException {
        return getEntityList(sql, params, className, null);
    }

    public static <T extends EntityManager<?>> List<T> getEntityList(String sql,
                                                                     Param<String, Object> params,
                                                                     final Class<T> className,
                                                                     IGenericMessage message) throws JdbcException {
        return executeList(sql, params, new BaseQuery<T>() {
            @Override
            public T next(Retrieve retrieve) throws JdbcException {
                try {
                    T entity = className.newInstance();
                    return (T) entity.getRegister(retrieve);
                } catch (JdbcException ex) {
                    throw ex;
                } catch (Exception e) {
                    Log.e("executeList", e.getMessage(), e);
                    throw new JdbcException(EMessageRosilla.ERROR_QUERY);
                }
            }
        }, message);
    }


}
