package com.sparta.basic_crud.dto;

import com.sparta.basic_crud.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto extends ResponseDto {

    private Long id;

    private String username;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment, int statusCode, String msg) {
        super(statusCode, msg);
        this.id = comment.getId();
        this.username = comment.getUsername();
        this.content = comment.getContent();
        this. createdAt = comment.getCreatedAt();
        this. modifiedAt = comment.getModifiedAt();
    }
}
