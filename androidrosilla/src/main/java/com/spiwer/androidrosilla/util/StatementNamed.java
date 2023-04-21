package com.spiwer.androidrosilla.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author spiwer.com - Herman Leonardo Rey Baquero - leoreyb@gmail.com
 */
@SuppressWarnings({"rawtypes", "UnnecessaryUnboxing"})
public class StatementNamed {

    private final SQLiteDatabase db;
    private final Map indexMap;
    private final Map<Integer, Object> parameters = new HashMap<>();
    private String sql;

    public StatementNamed(SQLiteDatabase db, String query) {
        this.db = db;
        this.indexMap = new HashMap<>();
        query += "  ";
        sql = parse(query);
    }

    private String parse(String query) {
        // I was originally using regular expressions, but they didn't work well for
        // ignoring
        // parameter-like strings inside quotes.
        int length = query.length();
        StringBuilder parsedQuery = new StringBuilder();
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        int index = 1;
        for (int i = 0; i < length - 1; i++) {
            char c = query.charAt(i);
            if (inSingleQuote) {
                if (c == '\'') {
                    inSingleQuote = false;
                }
            } else if (inDoubleQuote) {
                if (c == '"') {
                    inDoubleQuote = false;
                }
            } else {
                char cn = query.charAt(i + 1);
                if (c == ':' && cn == ':') {
                    parsedQuery.append("::");
                    i++;
                    continue;
                }
                if (c == '\'') {
                    inSingleQuote = true;
                } else if (c == '"') {
                    inDoubleQuote = true;
                } else if (c == ':' && i + 1 < length && Character.isJavaIdentifierStart(query.charAt(i + 1))) {
                    int j = i + 2;
                    while (j < length && Character.isJavaIdentifierPart(query.charAt(j))) {
                        j++;
                    }
                    String name = query.substring(i + 1, j);
                    c = '?'; // replace the parameter with a question mark
                    i += name.length(); // skip past the end if the parameter

                    List indexList = (List) indexMap.get(name);
                    if (indexList == null) {
                        indexList = new LinkedList();
                        indexMap.put(name, indexList);
                    }
                    indexList.add(index);

                    index++;
                }
            }
            parsedQuery.append(c);
        }

        // replace the lists of Integer objects with arrays of ints
        for (Iterator itr = indexMap.entrySet().iterator(); itr.hasNext(); ) {
            Map.Entry entry = (Map.Entry) itr.next();
            List list = (List) entry.getValue();
            int[] indexes = new int[list.size()];
            int i = 0;
            for (Iterator itr2 = list.iterator(); itr2.hasNext(); ) {
                Integer x = (Integer) itr2.next();
                indexes[i++] = x.intValue();
            }
            //noinspection unchecked
            entry.setValue(indexes);
        }

        return parsedQuery.toString();
    }

    private int[] getIndexes(String name) {
        int[] indexes = (int[]) indexMap.get(name);
        if (indexes == null) {
            throw new IllegalArgumentException("Parameter not found: " + name);
        }
        return indexes;
    }

    public void setObject(String name, Object value) {
        int[] indexes = getIndexes(name);
        for (int index : indexes) {
            parameters.put(index, value);
        }
    }

    public Cursor executeQuery() {
        String[] params = new String[parameters.size()];
        Set<Integer> indexes = parameters.keySet();
        for (Integer index : indexes) {
            params[index - 1] = String.valueOf(parameters.get(index));
        }
        return db.rawQuery(sql, params);
    }

    public void executeUpdate() {
        Object[] params = new Object[parameters.size()];
        Set<Integer> indexes = parameters.keySet();
        for (Integer index : indexes) {
            params[index - 1] = parameters.get(index);
        }
        db.execSQL(sql, params);
    }
}