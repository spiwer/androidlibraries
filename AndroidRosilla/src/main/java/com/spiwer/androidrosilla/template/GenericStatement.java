/*
 * To change this license app_header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spiwer.androidrosilla.template;

import com.spiwer.androidrosilla.exception.JdbcException;
import com.spiwer.androidrosilla.exception.JdbcException;

/**
 * @author spiwer.com - Herman Leonardo Rey Baquero - leoreyb@gmail.com
 */
public interface GenericStatement {

    public void noResults()
            throws JdbcException, JdbcException;

    public void error(Exception ex)
            throws JdbcException;
}
