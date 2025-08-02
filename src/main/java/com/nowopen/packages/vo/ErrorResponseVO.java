package com.nowopen.packages.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

@Getter
@Setter
@Builder
public class ErrorResponseVO {

     private boolean result;
     private String message;
     private HttpStatus status;
     private String details;

}
