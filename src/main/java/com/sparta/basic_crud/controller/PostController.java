package com.sparta.basic_crud.controller;

import com.sparta.basic_crud.dto.DeleteResponseDto;
import com.sparta.basic_crud.dto.PostRequestDto;
import com.sparta.basic_crud.dto.PostResponseDto;
import com.sparta.basic_crud.entity.Post;
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
    public Post postContent(@RequestBody PostRequestDto postRequestDto, HttpServletRequest request) {
        return postService.postContent(postRequestDto, request);
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
    public PostResponseDto updateContent(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, HttpServletRequest request) {
        return postService.updateContent(id, postRequestDto, request);
    }

    @DeleteMapping("/post/{id}")
    public DeleteResponseDto deleteContent(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, HttpServletRequest request) {
        return postService.deleteContent(id, postRequestDto, request);
    }

}