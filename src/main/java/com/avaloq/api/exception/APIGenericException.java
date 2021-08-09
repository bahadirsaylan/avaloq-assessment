package com.avaloq.api.exception;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class APIGenericException extends Exception{
    private int code;
    public APIGenericException(String errorMessage, int code){
        super(errorMessage);
        this.code = code;
    }
}
