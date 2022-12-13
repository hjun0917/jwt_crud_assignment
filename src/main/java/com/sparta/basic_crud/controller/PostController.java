package com.sparta.basic_crud.controller;

import com.sparta.basic_crud.dto.*;
import com.sparta.basic_crud.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  해당 클래스가 컨트롤러의 역할을 수행함을 명시해준다.
 * Controller VS RestController 의 차이점을 알아보도록 하자.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/post")
    public  ResponseDto postContent(@RequestBody PostRequestDto postRequestDto, HttpServletRequest request) {
        ResponseDto responseDto;
        try {
            responseDto = postService.postContent(postRequestDto, request);
            return responseDto;
        } catch (IllegalArgumentException e) {
            return new ResponseDto(400, e.getMessage());
        }
    }

    @GetMapping("/posts")
    public List<PostResponseDto> getContents() {
        return postService.getContent();
    }

    @GetMapping("/post/{id}")
    public PostResponseDto getIdContent(@PathVariable Long id) {
        return postService.getIdContent(id);
    }

    @PutMapping("/post/{id}")
    public ResponseDto updateContent(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, HttpServletRequest request) {
        // role 에 대한 검증을 체크하고, 알맞게 구현된 메서드로 요청하기.
        // 시큐리티를 사용하여 컨트롤러가 데이터를 받기 전에 검증이 이루어질 수 있다.
        // 시큐리티를 사용하지 않기 때문에 데이터를 받는 가장 앞 단인 컨트롤러에서 처리해주려고.

        // 그런데 이렇게 처리하면 Exception 메세지를 전달할 수가 없지 않나...?
//        String token = jwtUtil.resolveToken(request);
//        Claims claims;
//
//        if (token != null) {
//            if (jwtUtil.validateToken(token)) {
//                claims = jwtUtil.getUserInfoFromToken(token);
//            } else {
//                throw new IllegalArgumentException("Token Error");
//            }
//        }
        return postService.updateContent(id, postRequestDto, request);
    }

    @DeleteMapping("/post/{id}")
    public ResponseDto deleteContent(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, HttpServletRequest request) {
        return postService.deleteContent(id, postRequestDto, request);
    }

    @PostMapping("/comment/{post_id}")
    public ResponseDto postComment(@PathVariable Long post_id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return postService.postComment(post_id, commentRequestDto, request);
    }

    @PutMapping("/post/{post_id}/comment/{comment_id}")
    public ResponseDto updateComment(@PathVariable Long post_id, @PathVariable Long comment_id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return postService.updateComment(post_id, comment_id, commentRequestDto, request);
    }

    @DeleteMapping("/post/{post_id}/comment/{comment_id}")
    public ResponseDto deleteComment(@PathVariable Long post_id, @PathVariable Long comment_id, HttpServletRequest request) {
        return postService.deleteComment(post_id, comment_id, request);
    }
}

// ResponseDto -> 상태코드와 메세지를 담은 DTO 를 만들어, 다른 세부 DTO 에서 상속을 받게 하자.