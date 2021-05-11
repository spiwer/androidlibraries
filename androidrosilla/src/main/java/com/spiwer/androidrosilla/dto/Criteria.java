package com.spiwer.androidrosilla.dto;

import com.spiwer.androidrosilla.database.DatabaseManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author spiwer.com - Herman Leonardo Rey Baquero - leoreyb@gmail.com
 */
public class Criteria {

    private final List<Condition> conditionsList = new ArrayList<>();

    public Criteria add(String name, String operator, Object value) {
        conditionsList.add(new Condition(name, operator, value));
        return this;
    }

    public List<Condition> getConditionsList() {
        return conditionsList;
    }

    public Param<String, Object> params() {
        Param<String, Object> params = new Param<>();
        for (Condition condition : conditionsList) {
            params.add(condition.getName(), condition.getValue());
        }
        return params;
    }

    public Set<String> keySet() {
        Set<String> keys = new HashSet<>();

        for (Condition condition : conditionsList) {
            keys.add(condition.getName());
        }
        return keys;
    }

    public String getConditionSelect() {
        if (conditionsList.isEmpty()) {
            return DatabaseManager.EMPTY;
        }

        StringBuilder sql = new StringBuilder();
        for (Condition condition : conditionsList) {
            sql.append(condition.getConditionSelect()).append(" AND ");
        }
        return sql.substring(0, sql.length() - 4);

    }

    public String getConditionUpdate() {
        if (conditionsList.isEmpty()) {
            return DatabaseManager.EMPTY;
        }

        StringBuilder sql = new StringBuilder();
        for (Condition condition : conditionsList) {
            sql.append(condition.getConditionUpdate()).append(" AND ");
        }
        return sql.substring(0, sql.length() - 4);

    }

}
