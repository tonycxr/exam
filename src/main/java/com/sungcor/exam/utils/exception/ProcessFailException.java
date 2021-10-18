package com.sungcor.exam.utils.exception;


public class ProcessFailException extends BaseException {

    public ProcessFailException() {
    }

    public ProcessFailException(String message) {
        super(message);
    }

    public ProcessFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessFailException(Throwable cause) {
        super(cause);
    }

    public ProcessFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ProcessFailException(String message, String... msgParam) {
        super(message, msgParam);
    }

    public ProcessFailException(String message, Object[] params) {
        super(message, params);
    }
}
