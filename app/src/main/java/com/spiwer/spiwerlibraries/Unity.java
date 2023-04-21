
package com.spiwer.spiwerlibraries;


import com.spiwer.androidrosilla.exception.JdbcException;
import com.spiwer.androidrosilla.lasting.EMessageRosilla;
import com.spiwer.androidrosilla.template.EntityManager;
import com.spiwer.androidrosilla.util.Retrieve;
import com.spiwer.androidstandard.column.PrimaryKey;
import com.spiwer.androidstandard.exception.AppException;
import com.spiwer.androidstandard.lasting.ETypePrimaryKey;

import java.io.Serializable;

/**
 * @author spiwer.com - Herman Leonardo Rey Baquero - leoreyb@gmail.com
 */

public class Unity extends EntityManager<Unity> implements Serializable {


    private static final long serialVersionUID = 5916226195305600713L;
    public static final String TABLE_NAME = "unity";

    public static final String COL_UNT_CODE = "unt_code";
    public static final String COL_UNT_NAME = "unt_name";


    private Integer untCode;
    private String untName;


    public Unity() {
        super(TABLE_NAME);
    }

    public Unity(Integer untCode) {
        this();
        super.set(COL_UNT_CODE);
        this.untCode = untCode;
    }


    @Override
    public PrimaryKey primaryKey() {
        return new PrimaryKey(
                COL_UNT_CODE,
                Integer.class, ETypePrimaryKey.AUTOINCREMENT);
    }

    public Integer getUntCode() {
        return untCode;
    }

    public Unity setUntCode(Integer untCode) {
        super.set(COL_UNT_CODE);
        this.untCode = untCode;
        return this;
    }

    public String getUntName() {
        return untName;
    }

    public Unity setUntName(String untName) {
        super.set(COL_UNT_NAME);
        this.untName = untName;
        return this;
    }


    @Override
    public Unity validate() throws AppException {
        return this;
    }

    @Override
    public Unity getRegister(Retrieve retrieve)
            throws JdbcException {

        Integer untCodeCol = retrieve.getObjectOptional(COL_UNT_CODE, Integer.class);
        if (untCodeCol != null) {
            setUntCode(untCodeCol);
        }
        String untNameCol = retrieve.getObjectOptional(COL_UNT_NAME, String.class);
        if (untNameCol != null) {
            setUntName(untNameCol);
        }

        return this;
    }

    @Override
    public Object getValue(String columnName)
            throws AppException {
        switch (columnName) {
            case COL_UNT_CODE:
                return untCode;
            case COL_UNT_NAME:
                return untName;

            default:
                throw new AppException(EMessageRosilla.ERROR_DATABASE_COLUMN_NO_FOUND_NAME, columnName);
        }
    }


    public static Unity fill(Retrieve retrieve)
            throws JdbcException {
        return new Unity().getRegister(retrieve);
    }


}
