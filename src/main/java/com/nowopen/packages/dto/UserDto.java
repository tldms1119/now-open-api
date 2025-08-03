package com.nowopen.packages.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {

    private String email;
    private String password;
    private String username;

}
