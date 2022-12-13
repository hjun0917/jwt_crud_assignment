package com.sparta.basic_crud.entity;

import com.sparta.basic_crud.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    private String username;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
//    private List<Comment> comments = new ArrayList<>();

    /*
    * JPA 1 : N 관점에서 진정한 단방향 -> FK 를 가지는 Entity 에 아무처리 안해줘도 값이 생김.
    * @OneToMany(cascade = CascadeType.ALL)
    * @JoinColumn(name = "post_id")
    * private List<Comment> comments = new ArrayList<>();
    * */

    public Post(PostRequestDto requestDto,String username) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.username = username;
    }

    public void update(PostRequestDto postRequestDto, String username) {
        this.title = postRequestDto.getTitle();
        this.username = username;
        this.content = postRequestDto.getContent();
    }

    public void adminUpdate(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
    }

}