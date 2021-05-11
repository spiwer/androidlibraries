package com.spiwer.androidrosilla.lasting;

import com.spiwer.androidstandard.template.IGenericMessage;

/**
 * This class save all messages from the library
 *
 * @author spiwer.com - Herman Leonardo Rey Baquero - leoreyb@gmail.com
 */
public enum EMessageRosilla implements IGenericMessage {

    /**
     * Persistence
     */
    ERROR_DATABASE_CONNECTION(-2, "Connection error with the database "),
    ERROR_DATABASE_COLUMN_NO_FOUND(-3, "Column not found "),
    ERROR_DATABASE_COLUMN_NO_FOUND_NAME(-3, "Column __COMPLEMENT__ not found "),
    ERROR_DATABASE_PARSE(-4, "Error converting the data"),
    ERROR_DATABASE_COMMIT(-5, "Error confirming the data"),
    ERROR_DATABASE_CONNECTION_MANAGER(-6, "The connection manager not specified"),
    ERROR_EDIT(-7, "Error editing or inserting the record"),
    ERROR_FINDBYID(-8, "Record not found "),
    ERROR_NO_FOUND_RECORD(-9, "Record not found"),
    ERROR_QUERY(-10, "Error executing the query"),
    ERROR_QUERY_NOT_FOUND(-11, "Error query not found __COMPLEMENT__"),
    ERROR_INSERT(-12, "Error inserting the record"),
    ERROR_EDIT_MANY(-13, "Error, more than one record was edited"),
    ERROR_PRIMARY_KEY_EMPTY(-14, "The primary key can not be null"),
    ERROR_PRIMARY_KEY_COMPOSITE(-15, "Editing with a composite primary key is not allowed,"
            + " to edit entities without primary key please see "
            + "the method DatabaseManager.edit(GenericEntity entity, Map<String, Object> conditions)"),
    ERROR_NO_PRIMARY_KEY(-16, "The entity does not have a primary key"),
    ERROR_DELETE(-17, "Error deleting the record"),
    ERROR_ENTITY_EMPTY(-18, "Error the entity is empty"),
    ERROR_QUERY_PARAMS_NOT_FOUND(-19, "Params not found"),
    ERROR_QUERY_ENTITY(-20, "Error consulting entity information"),
    ERROR_DELETE_MANY(-21, "Error, more than one record was deleted"),
    ERROR_QUERY_MANY(-22, "The query returns many results");

    private final int code;
    private final String message;

    private EMessageRosilla(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
