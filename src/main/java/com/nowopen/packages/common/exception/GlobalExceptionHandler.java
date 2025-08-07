package com.nowopen.packages.common.exception;

import com.nowopen.packages.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> serviceExceptionHandler(ServiceException e) {
        log.error("[ERROR_MESSAGE]: " + e.getMessage());
        log.error(e.getErrorSource());
        loggingRequestParams();

        return ResponseEntity.status(HttpStatus.OK).body(
                ErrorResponse.builder()
                        .result(false)
                        .message(e.getMessage())
                        .status(e.getHttpStatus())
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        log.error("[ERROR_MESSAGE]: " + e.getMessage());
        loggingRequestParams();

        return ResponseEntity.status(HttpStatus.OK).body(
                ErrorResponse.builder()
                        .result(false)
                        .message(e.getMessage())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .build()
        );
    }

    private static void loggingRequestParams() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Enumeration<String> parameterList = request.getParameterNames();
        StringBuilder result = new StringBuilder();
        result.append("\n[parameter]: {\n");
        String format = "%s: %s,\n";
        while(parameterList.hasMoreElements()){
            String parameter = parameterList.nextElement();
            result.append(String.format(format, parameter, request.getParameter(parameter)));
        }
        result.append("}");
        log.error(result.toString());
    }

}
