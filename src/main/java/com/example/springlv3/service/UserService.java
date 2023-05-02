package com.example.springlv3.service;

import com.example.springlv3.dto.LoginRequestDto;
import com.example.springlv3.dto.SignupRequestDto;
import com.example.springlv3.dto.StatusDto;
import com.example.springlv3.entity.StatusEnum;
import com.example.springlv3.entity.Users;
import com.example.springlv3.entity.UserRoleEnum;
import com.example.springlv3.exception.CustomException;
import com.example.springlv3.jwt.JwtUtil;
import com.example.springlv3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.lang.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    //회원가입
    @Transactional
    public StatusDto signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        // 회원 중복 확인
        Optional<Users> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new CustomException(StatusEnum.DUPLICATE_USER);
        }

        //사용자 Role 확인
        UserRoleEnum roleEum = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new CustomException(StatusEnum.NON_ADMIN);
            }
            roleEum = UserRoleEnum.ADMIN;
        }
        Users users = new Users(username, password, roleEum);
        userRepository.save(users);

        return StatusDto.setSuccess(HttpStatus.OK.value(), "회원가입 완료", null);
    }

    //로그인
    @Transactional(readOnly = true)
    public StatusDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 유저 아이디 확인
        Users users = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(StatusEnum.USER_NOT_FOUND));

        // 비밀번호 확인
        if (!passwordEncoder.matches(password, users.getPassword())) {
            throw new CustomException(StatusEnum.NOT_PASSWORD);
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(users.getUsername(), users.getRole()));


        if (users.getRole().equals(UserRoleEnum.ADMIN)) {
            return StatusDto.setSuccess(HttpStatus.OK.value(),"관리자 로그인 완료", null);
        }
        return StatusDto.setSuccess(HttpStatus.OK.value(), "사용자 로그인 완료", null);
    }
}


