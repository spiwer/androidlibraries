package com.spiwer.androidstandard.dto;


import com.spiwer.androidstandard.exception.AppException;

/**
 * @param <T> Object to be answered
 * @author spiwer.com - Herman Leonardo Rey Baquero - leoreyb@gmail.com
 */
public class Answer<T> {

    private int code;
    private String message;
    private T info;

    public Answer() {

    }

    public Answer(AppException ex) {
        this.code = ex.getCode();
        this.message = ex.getMessage();
    }


    public Answer(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Answer(int code, String message, T info) {
        this.code = code;
        this.message = message;
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public Answer<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Answer<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getInfo() {
        return info;
    }

    public Answer<T> setInfo(T info) {
        this.info = info;
        return this;
    }

}
