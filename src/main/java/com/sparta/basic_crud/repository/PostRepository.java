package com.sparta.basic_crud.repository;

import com.sparta.basic_crud.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByModifiedAtDesc();

//    Optional<Post> findByIdAndUserId(Long id, Long userId);

}