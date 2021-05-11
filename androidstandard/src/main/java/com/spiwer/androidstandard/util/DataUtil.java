
package com.spiwer.androidstandard.util;


import android.util.Log;

import com.spiwer.androidstandard.exception.AppException;
import com.spiwer.androidstandard.lasting.EMessageStandard;
import com.spiwer.androidstandard.template.IGenericMessage;

import java.util.List;

/**
 * @author spiwer.com - Herman Leonardo Rey Baquero - leoreyb@gmail.com
 */


public class DataUtil {

    private Object[] values;
    private IGenericMessage message;
    private static final String REGEX_EMAIL = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$";

    private DataUtil() {

    }

    public static DataUtil init() {
        return new DataUtil();

    }

    public DataUtil field(IGenericMessage message, Object entity, Object value) {
        return field(message, new Object[]{entity, value});
    }

    public DataUtil field(IGenericMessage message, Object value) {
        return field(message, new Object[]{value});
    }

    private DataUtil field(IGenericMessage message, Object[] value) {
        this.message = message;
        this.values = value;
        return this;
    }

    public static String valueOrDefault(String value, String strDefault) {
        return (value == null || value.trim().isEmpty()) ? strDefault : value;
    }

    public static boolean isEmpty(String value) {
        return (value == null || value.trim().isEmpty());
    }

    public static boolean isFull(String value) {
        return !isEmpty(value);
    }

    public static boolean isFull(Object value) {
        return !isEmpty(value);
    }

    @SuppressWarnings("UseSpecificCatch")
    public static boolean isNumber(String value) {
        try {
            Double.valueOf(value);
            return true;
        } catch (Exception e) {
            Log.i("isNumber", String.valueOf(e));
            return false;
        }
    }

    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof String) {
            return isEmpty(value.toString());
        }
        if (value instanceof List) {
            return ((List) value).isEmpty();
        }
        return false;
    }

    public DataUtil required()
            throws AppException {
        for (Object value : values) {
            if (isEmpty(value)) {
                throw new AppException(message);
            }
        }
        return this;
    }

    @SuppressWarnings("UseSpecificCatch")
    public DataUtil isInteger()
            throws AppException {
        if (values == null || values.length <= 0) {
            return this;
        }
        int pos = values.length - 1;
        if (values[pos] == null) {
            return this;
        }
        try {
            Integer.valueOf(values[pos].toString());
            return this;
        } catch (Exception e) {
            throw new AppException(message);
        }
    }

    public DataUtil email()
            throws AppException {
        if (values == null) {
            return this;
        }

        int pos = values.length - 1;
        if (values[pos] == null) {
            return this;
        }
        String email = values[pos].toString().toLowerCase();
        if (!email.matches(REGEX_EMAIL)) {
            throw new AppException(message);
        }

        return this;
    }

    public DataUtil values(Object... options)
            throws AppException {
        if (values == null) {
            return this;
        }
        if (options == null) {
            throw new AppException(EMessageStandard.ERROR_VALUES);
        }
        for (Object valueRequired : options) {
            if (valueRequired == null) {
                throw new AppException(EMessageStandard.ERROR_VALUES);
            }
            if (valueRequired.equals(values[values.length - 1])) {
                return this;
            }
        }
        throw new AppException(EMessageStandard.ERROR_VALUES_NOT_FOUND);
    }

}
