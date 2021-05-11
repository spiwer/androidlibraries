package com.spiwer.androidstandard.exception;


import com.spiwer.androidstandard.template.IGenericMessage;

/**
 * @author spiwer.com - Herman Leonardo Rey Baquero - leoreyb@gmail.com
 */
@SuppressWarnings("serial")
public class AppException extends Exception implements GenericException {

    private int code;
    private String complement;

    /**
     * Class constructor
     *
     * @param code    Error code, if possible, a negative value
     * @param message Error message
     */
    public AppException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * Class constructor
     *
     * @param code       Error code, if possible a negative value
     * @param message    Error message
     * @param complement String to replace in the message
     */
    public AppException(int code, String message, String complement) {
        super(message);
        this.code = code;
        this.complement = complement;
    }

    /**
     * Class constructor
     *
     * @param message Custom message
     */
    public AppException(IGenericMessage message) {
        super(message.getMessage());
        this.code = message.getCode();

    }

    /**
     * Class constructor
     *
     * @param message    Custom message
     * @param complement That will replaced in the original message
     */
    public AppException(IGenericMessage message, String complement) {
        super(message.getMessage());
        this.code = message.getCode();
        this.complement = complement;
    }


    /**
     * @return code
     */
    @Override
    public int getCode() {
        return code;

    }

    /**
     * @param code Error code, if possible, a negative value
     */
    public void setCode(int code) {
        this.code = code;
    }

    public String getComplement() {
        return complement;
    }

    public AppException setComplement(String complement) {
        this.complement = complement;
        return this;
    }
}
