package com.sparta.basic_crud.service;

import com.sparta.basic_crud.dto.*;
import com.sparta.basic_crud.entity.Comment;
import com.sparta.basic_crud.entity.Post;
import com.sparta.basic_crud.entity.User;
import com.sparta.basic_crud.entity.UserRoleEnum;
import com.sparta.basic_crud.jwt.JwtUtil;
import com.sparta.basic_crud.repository.CommentRepository;
import com.sparta.basic_crud.repository.PostRepository;
import com.sparta.basic_crud.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private final JwtUtil jwtUtil;

    @Transactional
    public ResponseDto postContent(PostRequestDto postRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

//            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
//                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
//            );

            User user = checkUser(claims.getSubject());

            // saveAndFlush?? why?? -> save 로도 원하는 결과를 얻을 수 있었음
            Post post = postRepository.saveAndFlush(new Post(postRequestDto, user.getUsername()));

            return new PostResponseDto(post, 200, "OK!");
        }
        return new ResponseDto(400, "토큰이 존재하지 않습니다.");
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getContent() {
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();

        List<Post> contents = postRepository.findAllByOrderByModifiedAtDesc();

        // CommentResponseDto 를 만들어 전달한다.
        for (Post content : contents) {
            List<CommentResponseDto> commentList = new ArrayList<>();
            Long id = content.getId();
            List<Comment> comments = commentRepository.findAllByPost_IdOrderByModifiedAtDesc(id);

            for (Comment comment : comments) {
                CommentResponseDto commentResponseDto = new CommentResponseDto(comment, 200, "OK!");
                commentList.add(commentResponseDto);
            }
            postResponseDtoList.add(new PostResponseDto(content, commentList, 200, "OK!"));
        }

//         //참조순환루프
//        for (Post content : contents) {
//            Long PostId = content.getId();
//            List<Comment> comments = commentRepository.findAllByPost_IdOrderByModifiedAtDesc(PostId);
//            postResponseDtoList.add(new PostResponseDto(content, comments, 200, "OK!"));
//        }

         // String 타입의 List로 원하는 값만 담아서 보내는 방법.
//        for (int i = 0; i < contents.size(); i++) {
//            Post post = contents.get(i);
//            List<Comment> comments = commentRepository.findAllByPost_IdOrderByModifiedAtDesc(post.getId());
//            List<String> commentList = new ArrayList<>();
//            for (Comment comment : comments) {
//                commentList.add(comment.getContent());
//            }
//            postResponseDtoList.add(new PostResponseDto(post, commentList, 200, "OK!"));
//        }

        return postResponseDtoList;
    }

    @Transactional(readOnly = true)
    public PostResponseDto getIdContent(Long id) {
        Post post = checkPost(id);
//        List<Comment> comments = commentRepository.findAllByPost_IdOrderByModifiedAtDesc();

//        List<Comment> comments = commentRepository.findAllByPost_IdOrderByModifiedAtDesc(post.getId());
        List<Comment> comments = commentRepository.findAllByPost_IdOrderByModifiedAtDesc(id);

        List<CommentResponseDto> commentList = new ArrayList<>();

        for (Comment comment : comments) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment, 200, "OK!");
            commentList.add(commentResponseDto);
        }

        return new PostResponseDto(post, commentList, 200, "OK!");
    }

    @Transactional
    public ResponseDto updateContent(Long id, PostRequestDto postRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

//            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
//                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
//            );

            User user = checkUser(claims.getSubject());

//            Post post = postRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
//                    () -> new NullPointerException("해당 게시물은 존재하지 않습니다.")
//            );

            Post post = checkPost(id);

            // ADIMIN 권한 확인 시, 모든 게시물 수정 가능
            UserRoleEnum userRoleEnum = user.getRole();
            if (userRoleEnum == UserRoleEnum.ADMIN) {
                post.adminUpdate(postRequestDto);
                return new PostResponseDto(post, 200, "OK!");
            }

            // USER 는 본인이 작성한 게시물만 수정 가능
            if (post.getUsername().equals(user.getUsername())) {
                post.update(postRequestDto, user.getUsername());
                return new PostResponseDto(post, 200, "OK!");
            }
        }
        return new ResponseDto(400, "게시글 작성자만 수정할 수 있습니다.");
    }

    @Transactional
    public ResponseDto deleteContent(Long id, PostRequestDto postRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

//            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
//                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
//            );

//            Post post = postRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
//                    () -> new NullPointerException("해당 상품을 존재하지 않습니다.")
//            );

            User user = checkUser(claims.getSubject());

            Post post = checkPost(id);

            // ADIMIN 권한 확인 시, 모든 게시물 삭제 가능
            UserRoleEnum userRoleEnum = user.getRole();
            if (userRoleEnum == UserRoleEnum.ADMIN) {
                postRepository.delete(post);
            }

            // USER 는 본인이 작성한 게시물만 삭제 가능
            if (post.getUsername().equals(user.getUsername())) {
                postRepository.delete(post);
                return new ResponseDto(200, "OK!");
            }
        }
        return new ResponseDto(400, "게시글 작성자만 삭제할 수 있습니다.");
    }

    public ResponseDto postComment(Long postId, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = checkUser(claims.getSubject());

            Post post = postRepository.findById(postId).orElseThrow(
                    () -> new NullPointerException("해당 게시물은 존재하지 않습니다.")
                    );

            commentRepository.saveAndFlush(new Comment(commentRequestDto, user.getUsername(), post));
            return new ResponseDto(200, "OK!");
        }

        return new ResponseDto(400, "로그인이 필요한 기능입니다.");
    }

    @Transactional
    public ResponseDto updateComment(Long postId, Long commentId, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error.");
            }

            checkPost(postId);
            Comment comment = checkComment(commentId);
            User user = checkUser(claims.getSubject());

            if(!user.getRole().equals(UserRoleEnum.ADMIN)) {
                if(!user.getUsername().equals(comment.getUsername())) {
                    throw new IllegalArgumentException("댓글에 접근 권한 이없습니다.");
                }
            }

            comment.updateComment(commentRequestDto);
            return new ResponseDto(200, "OK!");
        }

        return new ResponseDto(400, "로그인이 필요한 기능입니다.");
    }

    @Transactional
    public ResponseDto deleteComment(Long postId, Long commentId, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error.");
            }

            checkPost(postId);
            Comment comment = checkComment(commentId);
            User user = checkUser(claims.getSubject());

            if(!user.getRole().equals(UserRoleEnum.ADMIN)) {
                if(!user.getUsername().equals(comment.getUsername())) {
                    throw new IllegalArgumentException("댓글에 접근 권한 이없습니다.");
                }
            }

            commentRepository.delete(comment);
            return new ResponseDto(200, "OK!");
        }

        return new ResponseDto(400, "로그인이 필요한 기능입니다.");
    }

    private Comment checkComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("해당 댓글은 존재하지 않습니다.")
        );
    }

    private Post checkPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 게시물은 존재하지 않습니다.")
        );
    }

    private User checkUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
    }
}
