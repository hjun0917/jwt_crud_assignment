package com.sparta.basic_crud.repository;

import com.sparta.basic_crud.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
//    List<Comment> findAllByOrderByModifiedAtDesc();

    List<Comment> findAllByPost_IdOrderByModifiedAtDesc(Long id);

}