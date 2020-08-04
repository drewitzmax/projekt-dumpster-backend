package com.cf.skipdiving.exception;

public class ActionNotExecutedException extends RuntimeException {
    public ActionNotExecutedException(String msg){
        super(msg);
    }
}
