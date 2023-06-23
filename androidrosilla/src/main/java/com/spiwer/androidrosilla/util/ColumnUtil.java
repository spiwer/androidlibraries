package com.spiwer.androidrosilla.util;

import android.database.Cursor;

import java.util.HashMap;
import java.util.Map;

public class ColumnUtil {

    private final Cursor cursor;

    public ColumnUtil(Cursor cursor) {
        this.cursor = cursor;
    }

    public Map<String, Integer> process() {
        Map<String, Integer> columns = new HashMap<>();
        String[] columnsName = cursor.getColumnNames();
        String previousPrefix = "";
        int index = 0;
        for (int i = 0; i < columnsName.length; i++) {
            String columnName = columnsName[i];
            String prefix = getPrefix(columnName);
            String newColumnName = "table" + index + "." + columnName;
            Integer position = columns.get(newColumnName);
            boolean equals = previousPrefix.equalsIgnoreCase(prefix);
            if (!equals || (position != null)) {
                previousPrefix = prefix;
                index++;
            }
            columns.put("table" + index + "." + columnName, i);
        }
        return columns;
    }

    private String getPrefix(String columnName) {
        String[] parts = columnName.split("_");
        return parts[0];
    }

}
