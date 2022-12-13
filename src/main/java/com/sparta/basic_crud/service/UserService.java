package com.sparta.basic_crud.service;

import com.sparta.basic_crud.dto.ResponseDto;
import com.sparta.basic_crud.dto.UserLoginRequestDto;
import com.sparta.basic_crud.dto.UserSignupRequestDto;
import com.sparta.basic_crud.entity.User;
import com.sparta.basic_crud.entity.UserRoleEnum;
import com.sparta.basic_crud.jwt.JwtUtil;
import com.sparta.basic_crud.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public ResponseDto signup(UserSignupRequestDto userSignupRequestDto) {
        String username = userSignupRequestDto.getUsername();
        String password = userSignupRequestDto.getPassword();
        UserRoleEnum role = userSignupRequestDto.getRole();

        // 유효성 검증에 실패할 때, 상황에 맞게 알맞은 msg를 어떻게 보내줘야할지 모르겠음...
//        if (!StringUtils.hasText(username)) {
//            throw new IllegalArgumentException("username은 알파벳 소문자와 숫자로 구성된 4~10자리로 작성해주세요.");
//        }
//
//        if (!StringUtils.hasText(password)) {
//            throw new IllegalArgumentException("password는 알파벳 대/소문자와 숫자, 특수문자로 구성된 10~15자리로 작성해주세요.");
//        }

        // 중복된 회원인지 검사
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 username 입니다.");
        }

        User user = new User(username, password, role);
        userRepository.save(user);

        return new ResponseDto(200,"OK!");
    }

    @Transactional
    public ResponseDto login(UserLoginRequestDto userLoginRequestDto, HttpServletResponse response) {
        String username = userLoginRequestDto.getUsername();
        String password = userLoginRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));

        return new ResponseDto(200, "로그인 완료!");
    }
}
