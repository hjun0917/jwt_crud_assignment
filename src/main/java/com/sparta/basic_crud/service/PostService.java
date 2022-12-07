package com.sparta.basic_crud.service;

import com.sparta.basic_crud.dto.DeleteResponseDto;
import com.sparta.basic_crud.dto.PostRequestDto;
import com.sparta.basic_crud.dto.PostResponseDto;
import com.sparta.basic_crud.entity.Post;
import com.sparta.basic_crud.entity.User;
import com.sparta.basic_crud.jwt.JwtUtil;
import com.sparta.basic_crud.repository.PostRepository;
import com.sparta.basic_crud.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public Post postContent(PostRequestDto postRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            return postRepository.saveAndFlush(new Post(postRequestDto, user.getId(), user.getUsername()));
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getContent() {
        List<Post> contents = postRepository.findAllByOrderByModifiedAtDesc();
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for (Post content : contents) {
            postResponseDtoList.add(new PostResponseDto(content, 200, "OK!"));
        }
        return postResponseDtoList;
    }

    @Transactional(readOnly = true)
    public PostResponseDto getIdContent(Long id) {
        Post post = checkPost(id);
        return new PostResponseDto(post, 200, "OK!");
    }

    @Transactional
    public PostResponseDto updateContent(Long id, PostRequestDto postRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Post post = postRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new NullPointerException("해당 상품을 존재하지 않습니다.")
            );

            post.update(postRequestDto, user.getUsername());
            return new PostResponseDto(post, 200, "OK!");
        } else {
            return null;
        }
    }

    @Transactional
    public DeleteResponseDto deleteContent(Long id, PostRequestDto postRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Post post = postRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new NullPointerException("해당 상품을 존재하지 않습니다.")
            );

            postRepository.delete(post);
            return new DeleteResponseDto(200,"OK!");
        } else {
            return new DeleteResponseDto(400, "false");
        }
    }

    private Post checkPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("No Post Found")
        );
    }
}
