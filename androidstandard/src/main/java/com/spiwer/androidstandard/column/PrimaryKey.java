package com.spiwer.androidstandard.column;


import com.spiwer.androidstandard.lasting.ETypePrimaryKey;

/**
 *
 */
public class PrimaryKey {
    private final transient String columnName;
    private final transient Class<?> classPrimaryKey;
    private final transient ETypePrimaryKey type;

    public PrimaryKey(String columnName, Class<?> classPrimaryKey, ETypePrimaryKey type) {
        this.columnName = columnName;
        this.classPrimaryKey = classPrimaryKey;
        this.type = type;
    }

    public String getColumnName() {
        return columnName;
    }

    public ETypePrimaryKey getType() {
        return type;
    }

    public Class<?> getClassPrimaryKey() {
        return classPrimaryKey;
    }

}
