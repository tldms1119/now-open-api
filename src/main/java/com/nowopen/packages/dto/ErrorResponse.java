package com.nowopen.packages.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class ErrorResponse {

     private boolean result;
     private String message;
     private HttpStatus status;
     private String details;

}
