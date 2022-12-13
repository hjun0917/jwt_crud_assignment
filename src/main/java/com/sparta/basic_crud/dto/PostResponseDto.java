package com.sparta.basic_crud.dto;

import com.sparta.basic_crud.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostResponseDto extends ResponseDto {
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long id;
    private String username;
    private String title;
    private String content;

//    private List<CommentResponseDto> comments = new ArrayList<>();

    private List<CommentResponseDto> comments = new ArrayList<>();

    public PostResponseDto(Post post, int statusCode, String msg) {
        super(statusCode, msg);
        this.id = post.getId();
        this.username = post.getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }

//    public PostResponseDto(Post post, List<Comment> comments, int statusCode, String msg) {
//        super(statusCode, msg);
//        this.id = post.getId();
//        this.username = post.getUsername();
//        this.title = post.getTitle();
//        this.content = post.getContent();
//        this.createdAt = post.getCreatedAt();
//        this.modifiedAt = post.getModifiedAt();
//        this.comments = comments;
//    }

    public PostResponseDto(Post post, List<CommentResponseDto> comments , int statusCode, String msg) {
        super(statusCode, msg);
        this.id = post.getId();
        this.username = post.getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.comments = comments;
    }
}
