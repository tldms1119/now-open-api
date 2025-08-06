package com.nowopen.packages.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String message;
    private final String errorSource;

    public ServiceException(HttpStatus status) {
        super();
        this.httpStatus = status;
        this.message = "";
        this.errorSource = buildErrorSource();
    }

    public ServiceException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = status;
        this.message =message;
        this.errorSource = buildErrorSource();
    }

    /**
     * logging a position of error
     * @return class, method name and line number of error
     */
    private String buildErrorSource(){
        StackTraceElement topStackTrace = this.getStackTrace()[0];
        String errorSource = "[CLASS_NAME]: %s, [METHOD_NAME]: %s, [LINE_NUMBER]: %s";
        return String.format(errorSource, topStackTrace.getClassName(), topStackTrace.getMethodName(), topStackTrace.getLineNumber());
    }
}
