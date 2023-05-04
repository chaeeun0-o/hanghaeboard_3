package com.example.springlv3.controller;

import com.example.springlv3.dto.LoginRequestDto;
import com.example.springlv3.dto.SignupRequestDto;
import com.example.springlv3.dto.StatusDto;
import com.example.springlv3.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")

public class UserController {

    private final UserService userService;
    @PostMapping("/signup")
    public StatusDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto ) {
        return userService.signup(signupRequestDto);
    }

    @ResponseBody
    @PostMapping("/login")
    public StatusDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }

}