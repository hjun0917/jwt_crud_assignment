package com.sparta.basic_crud.dto;

import com.sparta.basic_crud.entity.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSignupRequestDto {
    private String username;
    private String password;
    private UserRoleEnum role;
}
