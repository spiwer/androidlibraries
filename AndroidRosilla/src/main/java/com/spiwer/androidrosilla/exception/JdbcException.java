/*
 * To change this license app_header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spiwer.androidrosilla.exception;

import com.spiwer.androidstandard.exception.AppException;
import com.spiwer.androidstandard.template.IGenericMessage;

import java.sql.SQLException;


/**
 * Class responsible for controlling errors in the database
 *
 * @author spiwer.com - Herman Leonardo Rey Baquero - leoreyb@gmail.com
 */
@SuppressWarnings("serial")
public class JdbcException extends AppException {

    private transient SQLException error;

    /**
     * Class constructor
     *
     * @param code    Error code, if possible, a negative value
     * @param message Error message
     */
    public JdbcException(int code, String message) {
        super(code, message);

    }

    /**
     * Class constructor
     *
     * @param code       Error code, if possible, a negative value
     * @param message    Error message
     * @param complement
     */
    public JdbcException(int code, String message, String complement) {
        super(code, message, complement);

    }

    /**
     * Class constructor
     *
     * @param code    Error code, if possible, a negative value
     * @param message Error message
     * @param ex      Error SqlException
     */
    public JdbcException(int code, String message, SQLException ex) {
        super(code, message);
        this.error = ex;

    }

    /**
     * Class constructor
     *
     * @param message Custom message
     */
    public JdbcException(IGenericMessage message) {
        super(message);
    }

    /**
     * Class constructor
     *
     * @param message Custom message
     * @param ex      Error SQlException
     */
    public JdbcException(IGenericMessage message, SQLException ex) {
        super(message);
        this.error = ex;
    }

    /**
     * Class constructor
     *
     * @param message    Custom message
     * @param complement That will replaced in the original message
     */
    public JdbcException(IGenericMessage message, String complement) {
        super(message, complement);
    }

    /**
     * Class constructor
     *
     * @param message    Custom message
     * @param complement That will replaced in the original message
     * @param ex         SqlException
     */
    public JdbcException(IGenericMessage message, String complement, SQLException ex) {
        super(message, complement);
        this.error = ex;
    }

    public SQLException getError() {
        return error;
    }

}
