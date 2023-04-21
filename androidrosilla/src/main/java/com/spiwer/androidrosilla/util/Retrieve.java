package com.spiwer.androidrosilla.util;

import android.database.Cursor;
import android.util.Log;

import com.spiwer.androidrosilla.database.DatabaseManager;
import com.spiwer.androidrosilla.exception.JdbcException;
import com.spiwer.androidrosilla.lasting.EMessageRosilla;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


@SuppressWarnings({"unchecked", "UnnecessaryBoxing", "WrapperTypeMayBePrimitive"})
public class Retrieve {

    private Cursor cursor;
    private String alias;

    public Retrieve(Cursor cursor) {
        this.cursor = cursor;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public Retrieve setCursor(Cursor cursor) {
        this.cursor = cursor;
        return this;
    }

    public String getAlias() {
        return alias == null ? DatabaseManager.EMPTY : alias;
    }

    public Retrieve setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public <T> T getObject(String name, Class<T> type) throws JdbcException {
        try {
            int pos = cursor.getColumnIndexOrThrow(getAlias() + name);
            return getObject(pos, type);
        } catch (JdbcException ex) {
            throw ex;
        } catch (Exception e) {
            Log.e("getObject", e.getMessage(), e);
            throw new JdbcException(EMessageRosilla.ERROR_DATABASE_COLUMN_NO_FOUND_NAME, name);
        }
    }


    public <T> T getObject(int pos, Class<T> type) throws JdbcException {
        try {
            String value = cursor.getString(pos);
            if (value == null) {
                return null;
            }
            if (String.class == type) {
                return (T) value;
            }
            if (Short.class == type) {
                Short shortValue = cursor.getShort(pos);
                return (T) shortValue;
            }
            if (Integer.class == type) {
                Integer integer = cursor.getInt(pos);
                return (T) integer;
            }
            if (Long.class == type) {
                Long valueLong = cursor.getLong(pos);
                return (T) valueLong;
            }
            if (Float.class == type) {
                Float floatValue = cursor.getFloat(pos);
                return (T) floatValue;
            }
            if (Double.class == type) {
                Double doubleValue = cursor.getDouble(pos);
                return (T) doubleValue;
            }
            throw new JdbcException(EMessageRosilla.ERROR_DATABASE_PARSE);
        } catch (JdbcException e) {
            throw e;
        } catch (Exception e) {
            Log.e("getObject", e.getMessage(), e);
            throw new JdbcException(EMessageRosilla.ERROR_DATABASE_COLUMN_NO_FOUND);
        }
    }


    public <T> T getObjectOptional(int pos, Class<T> type) {
        try {
            return getObject(pos, type);
        } catch (JdbcException e) {
            return null;
        }
    }

    public <T> T getObjectOptional(String name, Class<T> type) {
        try {
            return getObject(name, type);
        } catch (JdbcException e) {
            return null;
        }
    }


    public byte[] getBlob(String name) throws JdbcException {
        int position = cursor.getColumnIndex(getAlias() + name);
        return getBlob(position);
    }

    public byte[] getBlob(int position) throws JdbcException {
        try {
            return cursor.getBlob(position);
        } catch (Exception e) {
            Log.e("getBlob", e.getMessage(), e);
            throw new JdbcException(EMessageRosilla.ERROR_DATABASE_COLUMN_NO_FOUND);
        }
    }

    public Date getDate(String name, String format) throws JdbcException {
        int position = cursor.getColumnIndex(getAlias() + name);
        return getDate(position, format);
    }

    public Date getDate(int position, String format) throws JdbcException {
        String value = getObject(position, String.class);
        return parseDate(value, format);
    }

    public Date getDateOptional(int position, String format) throws JdbcException {
        String value = getObjectOptional(position, String.class);
        return parseDate(value, format);
    }

    public Date getDateOptional(String position, String format) throws JdbcException {
        String value = getObjectOptional(position, String.class);
        return parseDate(value, format);
    }

    public Integer getInt(String name) throws JdbcException {
        int position = cursor.getColumnIndex(getAlias() + name);
        return getObject(position, Integer.class);
    }

    public Integer getInt(int position) throws JdbcException {
        return getObject(position, Integer.class);
    }

    public Integer getIntOptional(String name) {
        int position = cursor.getColumnIndex(getAlias() + name);
        return getIntOptional(position);
    }

    public Integer getIntOptional(int position) {
        return getObjectOptional(position, Integer.class);
    }

    public String getString(String name) throws JdbcException {
        int position = cursor.getColumnIndex(getAlias() + name);
        return getString(position);
    }

    public String getString(int position) throws JdbcException {
        return getObject(position, String.class);
    }

    public String getStringOptional(String name) {
        int position = cursor.getColumnIndex(getAlias() + name);
        return getStringOptional(position);
    }

    public String getStringOptional(int position) {
        return getObjectOptional(position, String.class);
    }

    public Float getFloat(String name) throws JdbcException {
        int position = cursor.getColumnIndex(getAlias() + name);
        return getFloat(position);
    }

    public Float getFloat(int position) throws JdbcException {
        return getObject(position, Float.class);
    }

    public Float getFloatOptional(String name) {
        int position = cursor.getColumnIndex(getAlias() + name);
        return getFloatOptional(position);
    }

    public Float getFloatOptional(int position) {
        return getObjectOptional(position, Float.class);
    }

    public Double getDouble(String name) throws JdbcException {
        int position = cursor.getColumnIndex(getAlias() + name);
        return getDouble(position);
    }

    public Double getDouble(int position) throws JdbcException {
        return getObject(position, Double.class);
    }

    public Double getDoubleOptional(String name) {
        int position = cursor.getColumnIndex(getAlias() + name);
        return getDoubleOptional(position);
    }

    public Double getDoubleOptional(int position) {
        return getObjectOptional(position, Double.class);
    }

    public Long getLong(String name) throws JdbcException {
        int position = cursor.getColumnIndex(getAlias() + name);
        return getLong(position);
    }

    public Long getLong(int position) throws JdbcException {
        return getObject(position, Long.class);
    }

    public Long getLongOptional(String name) {
        int position = cursor.getColumnIndex(getAlias() + name);
        return getLongOptional(position);
    }

    public Long getLongOptional(int position) {
        return getObjectOptional(position, Long.class);
    }


    public Short getShort(String name) throws JdbcException {
        int position = cursor.getColumnIndex(getAlias() + name);
        return getShort(position);
    }

    public Short getShort(int position) throws JdbcException {
        return getObject(position, Short.class);
    }

    public Short getShortOptional(String name) {
        int position = cursor.getColumnIndex(getAlias() + name);
        return getShortOptional(position);
    }

    public Short getShortOptional(int position) {
        return getObjectOptional(position, Short.class);
    }


    private Date parseDate(String value, String format) throws JdbcException {
        if (value == null) {
            return null;
        }
        try {
            SimpleDateFormat simpleFormat = new SimpleDateFormat(format, Locale.getDefault());
            return simpleFormat.parse(value);
        } catch (ParseException e) {
            Log.e("getDate", e.getMessage(), e);
            throw new JdbcException(EMessageRosilla.ERROR_DATABASE_PARSE);
        }
    }


}
