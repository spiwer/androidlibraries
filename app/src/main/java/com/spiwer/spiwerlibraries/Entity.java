
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

public class Entity extends EntityManager<Entity> implements Serializable {


    private static final long serialVersionUID = -6251743235669575929L;
    public static final String TABLE_NAME = "entity";

    public static final String COL_ENT_CODE = "ent_code";
    public static final String COL_ENT_NAME = "ent_name";
    public static final String COL_ENT_TYPE = "ent_type";
    public static final String COL_ENT_TAX = "ent_tax";


    private Integer entCode;
    private String entName;
    private Integer entType;
    private Integer entTax;


    public Entity() {
        super(TABLE_NAME);
    }

    public Entity(Integer entCode) {
        this();
        super.set(COL_ENT_CODE);
        this.entCode = entCode;
    }


    @Override
    public PrimaryKey primaryKey() {
        return new PrimaryKey(
                COL_ENT_CODE,
                Integer.class, ETypePrimaryKey.AUTOINCREMENT);
    }

    public Integer getEntCode() {
        return entCode;
    }

    public Entity setEntCode(Integer entCode) {
        super.set(COL_ENT_CODE);
        this.entCode = entCode;
        return this;
    }

    public String getEntName() {
        return entName;
    }

    public Entity setEntName(String entName) {
        super.set(COL_ENT_NAME);
        this.entName = entName;
        return this;
    }

    public Integer getEntType() {
        return entType;
    }

    public Entity setEntType(Integer entType) {
        super.set(COL_ENT_TYPE);
        this.entType = entType;
        return this;
    }

    public Integer getEntTax() {
        return entTax;
    }

    public Entity setEntTax(Integer entTax) {
        super.set(COL_ENT_TAX);
        this.entTax = entTax;
        return this;
    }


    @Override
    public Entity validate() throws AppException {
        return this;
    }

    @Override
    public Entity getRegister(Retrieve retrieve)
            throws JdbcException {

        Integer entCodeCol = retrieve.getObjectOptional(COL_ENT_CODE, Integer.class);
        if (entCodeCol != null) {
            setEntCode(entCodeCol);
        }
        String entNameCol = retrieve.getObjectOptional(COL_ENT_NAME, String.class);
        if (entNameCol != null) {
            setEntName(entNameCol);
        }
        Integer entTypeCol = retrieve.getObjectOptional(COL_ENT_TYPE, Integer.class);
        if (entTypeCol != null) {
            setEntType(entTypeCol);
        }
        Integer entTaxCol = retrieve.getObjectOptional(COL_ENT_TAX, Integer.class);
        if (entTaxCol != null) {
            setEntTax(entTaxCol);
        }

        return this;
    }

    @Override
    public Object getValue(String columnName)
            throws AppException {
        switch (columnName) {
            case COL_ENT_CODE:
                return entCode;
            case COL_ENT_NAME:
                return entName;
            case COL_ENT_TYPE:
                return entType;
            case COL_ENT_TAX:
                return entTax;

            default:
                throw new AppException(EMessageRosilla.ERROR_DATABASE_COLUMN_NO_FOUND_NAME, columnName);
        }
    }


    public static Entity fill(Retrieve retrieve)
            throws JdbcException {
        return new Entity().getRegister(retrieve);
    }


}
