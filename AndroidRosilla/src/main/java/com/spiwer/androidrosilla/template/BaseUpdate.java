package com.spiwer.androidrosilla.template;


import com.spiwer.androidrosilla.exception.JdbcException;

@SuppressWarnings("RedundantThrows")
public abstract class BaseUpdate implements GenericStatement {
    @Override
    public void noResults() throws JdbcException {
    }

    @Override
    public void error(Exception ex) throws JdbcException {
    }
}
