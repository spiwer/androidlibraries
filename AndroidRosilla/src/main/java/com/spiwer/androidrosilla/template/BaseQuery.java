/*
 * To change this license app_header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spiwer.androidrosilla.template;


import com.spiwer.androidrosilla.exception.JdbcException;
import com.spiwer.androidrosilla.lasting.EMessageRosilla;
import com.spiwer.androidrosilla.util.Retrieve;

/**
 * @param <T>
 * @author spiwer.com - Herman Leonardo Rey Baquero - leoreyb@gmail.com
 */
public abstract class BaseQuery<T> implements GenericStatement {

    public abstract T next(Retrieve retrieve)
            throws JdbcException;

    @Override
    public void noResults() throws JdbcException {
    }

    @Override
    public void error(Exception ex) throws JdbcException {
        ex.printStackTrace();
        throw new JdbcException(EMessageRosilla.ERROR_QUERY);
    }
}
