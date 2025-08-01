package com.nowopen.packages.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BaseResponseVO {

     private boolean result;
     private String message;

}
