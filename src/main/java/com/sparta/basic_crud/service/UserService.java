package com.sparta.basic_crud.service;

import com.sparta.basic_crud.dto.StatusDto;
import com.sparta.basic_crud.dto.UserLoginDto;
import com.sparta.basic_crud.dto.UserSignupDto;
import com.sparta.basic_crud.entity.EnumRole;
import com.sparta.basic_crud.entity.User;
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
    public StatusDto signup(UserSignupDto userSignupDto) {
        String username = userSignupDto.getUsername();
        String password = userSignupDto.getPassword();
        EnumRole role = userSignupDto.getRole();

        // 중복된 회원인지 검사
        Optional<User> found = userRepository.findByUsername(username);
        if(found.isPresent()) {
            throw new IllegalArgumentException("중복된 회원입니다.");
//            return new StatusDto("400", "중복된 회원입니다.");
        }

        User user = new User(username, password, role);
        userRepository.save(user);

        return new StatusDto("200", "OK!");
    }

    @Transactional
    public StatusDto login(UserLoginDto userLoginDto, HttpServletResponse response) {
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        if(!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));

        return new StatusDto("200", "로그인 완료!");
    }
}
