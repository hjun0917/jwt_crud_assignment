package com.sparta.basic_crud.entity;

import com.sparta.basic_crud.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

//데이터 베이스의 테이블과 직접 연관되는 객체 Entity 를 선언
@Entity
@Getter
// 매개변수를 가지지 않는 기본 생성자를 만들어 준다.
@NoArgsConstructor
//@RequiredArgsConstructor
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private Long userId;

    public Post(PostRequestDto requestDto, Long userId, String username) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.userId = userId;
        this.author = username;
    }

    public void update(PostRequestDto postRequestDto, String username) {
        this.title = postRequestDto.getTitle();
        this.author = username;
        this.content = postRequestDto.getContent();
    }
}