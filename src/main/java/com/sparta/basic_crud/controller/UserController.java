package com.sparta.basic_crud.controller;

import com.sparta.basic_crud.dto.StatusDto;
import com.sparta.basic_crud.dto.UserLoginDto;
import com.sparta.basic_crud.dto.UserSignupDto;
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
    public StatusDto signup(@RequestBody UserSignupDto userSignupDto) {
        return userService.signup(userSignupDto);
    }

    @PostMapping("/login")
    public StatusDto login(@RequestBody UserLoginDto userLoginDto, HttpServletResponse response) {
        return userService.login(userLoginDto, response);

    }
}
