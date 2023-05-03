package com.example.springlv3.service;

import com.example.springlv3.dto.*;
import com.example.springlv3.entity.*;
import com.example.springlv3.exception.CustomException;
import com.example.springlv3.jwt.JwtUtil;
import com.example.springlv3.repository.CommentRepository;
import com.example.springlv3.repository.CrudRepository;
import com.example.springlv3.repository.UserRepository;
import com.example.springlv3.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CrudRepository crudRepository;
    private final JwtUtil jwtUtil;


    //댓글 생성
    @Transactional
    public StatusDto createComment(Long CrudId, CommentRequestDto requestDto, Users users) {
        //게시글 존재 확인
       // Crud crud = checkCrud(requestDto.getCrudId());
        Crud crud = checkCrud(CrudId);
        Comment comment = new Comment(requestDto, users, crud);
        commentRepository.save(comment);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
        return StatusDto.setSuccess(HttpStatus.OK.value(),"댓글 등록 완료", commentResponseDto);
    }

    //수정
    @Transactional
    public StatusDto updateComment(Long commentId, CommentRequestDto requestDto, Users users) {

        Comment comment = checkComment(commentId);
        isCommentUsers(users, comment);
        comment.update(requestDto);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
        return StatusDto.setSuccess(HttpStatus.OK.value(), "수정 성공", commentResponseDto);
    }

    //삭제
    @Transactional
    public StatusDto deleteComment(Long commentId,Users users) {
        Comment comment = checkComment(commentId);
        //권한 체크
        isCommentUsers(users, comment);
        commentRepository.deleteById(commentId);
        return StatusDto.setSuccess(HttpStatus.OK.value(), "삭제 성공 완료", null);
    }

    //글 존재 여부 확인
    private Crud checkCrud(Long id) {
        Crud crud = crudRepository.findById(id).orElseThrow(
                () -> new CustomException(StatusEnum.NOT_EXIST_CRUD)
        );
        return crud;
    }

    //댓글 존재 여부 확인
    private Comment checkComment(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomException(StatusEnum.NOT_EXIST_COMMENT)
        );
        return comment;
    }

    //권한 여부
    private void isCommentUsers(Users users, Comment comment){
        if(!comment.getUsers().getUsername().equals(users.getUsername()) && !users.getRole().equals(UserRoleEnum.ADMIN)){
            throw new CustomException(StatusEnum.NOT_AUTHENTICATION);
        }
    }
}
