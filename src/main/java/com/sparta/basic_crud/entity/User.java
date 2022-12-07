package com.sparta.basic_crud.entity;

import jakarta.persistence.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)[a-z\\d]{4,10}$", message = "username은 알파벳 소문자와 숫자로 구성된 4~10자리로 작성해주세요.")
    @Column(nullable = false)
    private String username;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{10,15}$", message = "알파벳 대/소문자와 숫자, 특수문자로 구성된 10~15자리로 작성해주세요.")
    @Column(nullable = false)
    private String password;


    @Enumerated(value = EnumType.STRING)
    private EnumRole role;

    public User(String username, String password, EnumRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
