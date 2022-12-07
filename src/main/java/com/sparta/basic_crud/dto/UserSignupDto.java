package com.sparta.basic_crud.dto;

import com.sparta.basic_crud.entity.EnumRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupDto {
    private String username;
    private String password;
    private EnumRole role;
}
