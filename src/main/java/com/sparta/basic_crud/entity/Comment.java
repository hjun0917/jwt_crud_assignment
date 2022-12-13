package com.sparta.basic_crud.entity;

import com.sparta.basic_crud.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    // N : 1 관점에서 단방향 주는 방법을 알아보자.

    /*
    * 객체로 맵핑이된다. -> 따라서 Long 타입이 올 수 없다!!!!!!!!!!!!
    * @ManyToOne
    * private Long postId;
    * */

//    @JoinColumn(name = "post_id")
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Post post;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    public Comment(CommentRequestDto commentRequestDto, String username, Post post) {
        this.username = username;
        this.content = commentRequestDto.getContent();
        this.post = post;
    }

    public void updateComment(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }
}
