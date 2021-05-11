package com.spiwer.androidstandard.lasting;


import com.spiwer.androidstandard.template.IGenericMessage;

/**
 * @author spiwer.com - Herman Leonardo Rey Baquero - leoreyb@gmail.com
 */
public enum EMessageStandard implements IGenericMessage {
    OK(1, "Request executed correctly"),
    NO_RESULTS(0, "No results found"),
    ERROR_FATAL(-1, "Unexpected error ask the administrator"),
    ERROR_READ_FILE(-500, "Error reading the query file"),
    ERROR_CONNECT_EMAIL(-501, "Error connecting to the mail server"),
    ERROR_ENCRYPTION_FAILED(-502, "Error when encrypting"),
    ERROR_DECIPHER_FAILED(-503, "Error when deciphering "),
    ERROR_REQUEST(-504, "Error making the request"),
    ERROR_JSON_DESEREALIZATION(-505, "Failed to deserialize the JSON "),
    ERROR_FILE_DOWNLOAD(-506, "Error downloading the file"),
    ERROR_VALUES(-507, "You must specify the values"),
    ERROR_PARAMETER_TYPE(-508, "Error the data type is incorrect"),
    ERROR_PARAMETER_PARSE(-509, "Error converting value"),
    ERROR_VALUES_MULTIPLE(-510, "Only one value can be validated"),
    ERROR_VALUES_NOT_FOUND(-511, "Value not found"),
    ERROR_CONNECTION_NOT_FOUND(-512, "Connection not found with name __COMPLEMENT__");

    private final int code;
    private final String message;

    private EMessageStandard(int code, String message) {
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
