package com.nowopen.packages.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BaseResponse<T> {

     private boolean result;
     private String message;
     private T payload;

}
