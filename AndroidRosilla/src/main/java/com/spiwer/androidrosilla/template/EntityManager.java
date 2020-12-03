/*
 * To change this license app_header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spiwer.androidrosilla.template;


import com.spiwer.androidrosilla.database.DatabaseManager;
import com.spiwer.androidrosilla.dto.Criteria;
import com.spiwer.androidrosilla.dto.Param;
import com.spiwer.androidrosilla.exception.JdbcException;
import com.spiwer.androidrosilla.lasting.EMessageRosilla;
import com.spiwer.androidrosilla.util.Retrieve;
import com.spiwer.androidstandard.column.PrimaryKey;
import com.spiwer.androidstandard.exception.AppException;
import com.spiwer.androidstandard.lasting.ETypePrimaryKey;
import com.spiwer.androidstandard.template.IGenericMessage;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.spiwer.androidrosilla.database.DatabaseManager.params;


/**
 * This class
 *
 * @param <T>
 * @author spiwer.com - Herman Leonardo Rey Baquero - leoreyb@gmail.com
 */
@SuppressWarnings({"rawtypes", "unchecked", "UseSpecificCatch"})

public abstract class EntityManager<T extends EntityManager> implements Serializable {

    /**
     * Name of the table
     */
    private final String tableName;
    private final Set<String> listFields;
    private Param<String, Object> params;
    private final StringBuilder sql = new StringBuilder("SELECT * FROM ");
    private Criteria criteria;

    public EntityManager(String tableName) {
        this.tableName = tableName;
        listFields = new HashSet<>();
        sql.append(tableName);
    }

    /**
     * Save the names of the columns when the value has changed, to take them into
     * account when creating the SQL statement
     *
     * @param fieldName Column name of the database
     */
    protected void set(String fieldName) {
        if (listFields.contains(fieldName)) {
            return;
        }
        listFields.add(fieldName);
    }

    /**
     * Returns the name of the primary key in the table
     *
     * @return Column primary key
     */
    public abstract PrimaryKey primaryKey();

    public void primaryKey(Object key) {
    }

    public abstract T validate()
            throws AppException;

    public String tableName() {
        return tableName;
    }

    public Set<String> listFields() {
        return listFields;
    }

    public abstract T getRegister(Retrieve retrieve)
            throws JdbcException;

    public abstract Object getValue(String columnName)
            throws AppException;

    public void insert()
            throws JdbcException {
        DatabaseManager.insert(this);
    }

    public void edit()
            throws JdbcException {
        try {
            PrimaryKey primaryKey = primaryKey();
            String columnName = primaryKey.getColumnName();
            if (primaryKey.getType() == ETypePrimaryKey.NO_PRIMARY_KEY) {
                throw new JdbcException(EMessageRosilla.ERROR_PRIMARY_KEY_EMPTY);
            }
            if (primaryKey.getType() == ETypePrimaryKey.MULTIPLE) {
                throw new JdbcException(EMessageRosilla.ERROR_PRIMARY_KEY_COMPOSITE);
            }
            Param<String, Object> param = params();
            param.add(columnName, getValue(columnName));
            edit(param);
        } catch (JdbcException ex) {
            throw ex;
        } catch (AppException e) {
            throw new JdbcException(e.getCode(), e.getMessage());
        }
    }

    public void edit(Param<String, Object> conditions)
            throws JdbcException {
        DatabaseManager.edit(this, conditions);
    }

    public void delete()
            throws JdbcException {
        DatabaseManager.delete(this);
    }

    public T where(String where, Param<String, Object> params) {
        sql.append(" WHERE ").append(where);
        this.params = params;
        return (T) this;
    }

    public T where(Param<String, Object> params) throws JdbcException {
        if (params == null || params.isEmpty()) {
            throw new JdbcException(EMessageRosilla.ERROR_QUERY_PARAMS_NOT_FOUND);
        }
        sql.append(" WHERE ");
        for (String key : params.keySet()) {
            sql.append(key)
                    .append(" = :")
                    .append(key)
                    .append(" AND ");
        }
        sql.append(" 1=1");
        this.params = params;
        return (T) this;
    }

    public T where(String name, String operator, Object value) {
        sql.append(" WHERE ");
        and(name, operator, value);
        return (T) this;
    }

    public T where(Criteria criteria) {
        sql.append(" WHERE ");
        this.criteria = criteria;
        return (T) this;
    }

    public T and(String name, String operator, Object value) {
        if (criteria == null) {
            criteria = new Criteria();
        }
        criteria.add(name, operator, value);
        return (T) this;
    }

    public T orderBy(String fields) {
        sql.append(" ORDER BY ").append(fields);
        return (T) this;
    }

    public List<T> list()
            throws JdbcException {
        return list(null);
    }

    public List<T> list(IGenericMessage noResults)
            throws JdbcException {
        try {
            if (criteria != null) {
                sql.append(criteria.getConditionSelect());
                params = criteria.params();
            }
            return (List<T>) DatabaseManager.getEntityList(sql.toString(), params, ((T) this).getClass(), noResults);
        } finally {
            params = null;
        }
    }

    public T find()
            throws JdbcException {
        return this.find(null);
    }


    public T find(IGenericMessage noResults)
            throws JdbcException {
        try {
            if (criteria != null) {
                sql.append(criteria.getConditionSelect());
                params = criteria.params();
            }
            if (params == null || params.isEmpty()) {
                throw new JdbcException(EMessageRosilla.ERROR_QUERY_PARAMS_NOT_FOUND);
            }
            return DatabaseManager.getEntity(sql.toString(), params, this, noResults);
        } finally {
            params = null;
        }
    }


    public static <T extends EntityManager> T fill(Class<T> classType, Retrieve retrieve) {
        try {
            T entity = classType.newInstance();
            return (T) entity.getRegister(retrieve);
        } catch (Exception ex) {
            Logger.getLogger(EntityManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
