package com.sparta.basic_crud.dto;

import com.sparta.basic_crud.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor // 모든 필드가 받을 인자를 각각 보내줘 -> 내가 모든 필드를 파라미터로 받는 생성자 만들어 놓을 때.
public class PostResponseDto {
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long id;
    private String author;
    private String title;
    private String content;
    private int statusCode;
    private String msg;

    public PostResponseDto(Post post, int statusCode, String msg) {
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.id = post.getId();
        this.author = post.getAuthor();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.statusCode = statusCode;
        this.msg = msg;
    }

    public PostResponseDto(int statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }
}
