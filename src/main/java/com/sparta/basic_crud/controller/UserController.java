package com.sparta.basic_crud.controller;

import com.sparta.basic_crud.dto.ResponseDto;
import com.sparta.basic_crud.dto.UserLoginRequestDto;
import com.sparta.basic_crud.dto.UserSignupRequestDto;
import com.sparta.basic_crud.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody UserSignupRequestDto userSignupRequestDto) {
        ResponseDto responseDto;
        try {
            responseDto = userService.signup(userSignupRequestDto);
            return responseDto;
        } catch (IllegalArgumentException e) {
            return new ResponseDto(400,  e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseDto login(@RequestBody UserLoginRequestDto userLoginRequestDto, HttpServletResponse response) {
        ResponseDto responseDto;
        try {
            responseDto = userService.login(userLoginRequestDto, response);
            return responseDto;
            } catch (IllegalArgumentException e) {
            return new ResponseDto(400, e.getMessage());
        }
    }
}
