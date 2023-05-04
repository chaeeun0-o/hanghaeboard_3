package com.example.springlv3.service;


import antlr.Utils;
import com.example.springlv3.dto.StatusDto;
import com.example.springlv3.entity.*;
import com.example.springlv3.exception.CustomException;
import com.example.springlv3.repository.CommentLikeRepository;
import com.example.springlv3.repository.CommentRepository;
import com.example.springlv3.repository.CrudLikeRepository;
import com.example.springlv3.repository.CrudRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Optional;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {

    private final Utils utils;
    private final CommentLikeRepository commentLikeRepository;
    private final CrudLikeRepository crudLikeRepository;
    private final CommentRepository commentRepository;
    private final CrudRepository crudRepository;

    //게시글 좋아요
    @Transactional
    public StatusDto crudlike(Long crudId, Users users){
        Crud crud = checkCrud(crudId); //게시글 체크

        if(CrudLikeCheck(users,crud)){//좋아요가 있으면 DB에서 삭제, 없으면 생성
            crudLikeRepository.deleteByUserIdAndCrudId(users.getId(), crudId);
        } else{
            CrudLike crudLike = new CrudLike(crud, users);
            crudLikeRepository.save(crudLike);
            return StatusDto.setSuccess(HttpStatus.OK.value(), "게시글 좋아요 성공", crudLike);
        }
        long crudLikeCount = crudLikeRepository.countByCrudId(crud.getId());
        crud.crudLike(crudLikeCount);
       crudRepository.save(crud);
        return StatusDto.setSuccess(HttpStatus.OK.value(), "게시글 좋아요 추가", crudLikeCount);
    }

    //댓글 좋아요
    @Transactional
    public StatusDto commentlike(Long commentId, Users users) {
        Comment comment = checkComment(commentId); //댓글확인

        if(commentLikeCheck(users, comment))  {//좋아요가 있으면 DB에서 삭제, 없으면 생성
            commentLikeRepository.deleteByUserIdAndCommentId(users.getId(), commentId);
        } else {
            CommentLike commentLikes = new CommentLike(comment, users);
            commentLikeRepository.save(commentLikes);
            return StatusDto.setSuccess(HttpStatus.OK.value(), "댓글 좋아요 성공", commentLikes);
        }
        long commentLikeCount  = commentLikeRepository.countByCommentId(commentId);
        comment.commentLike(commentLikeCount );
        commentRepository.save(comment);
        return StatusDto.setSuccess(HttpStatus.OK.value(), "댓글 좋아요 추가", commentLikeCount );
    }

    //글 존재 여부 확인
    private Crud checkCrud(Long id) {
        Crud crud = crudRepository.findById(id).orElseThrow(
                () -> new CustomException(StatusEnum.NOT_EXIST_CRUD)
        );
        return crud;
    }

    //게시글 권한 여부
    private void isCrudUsers(Users users, Crud crud){
        if(!crud.getUsers().getUsername().equals(users.getUsername()) && !users.getRole().equals(UserRoleEnum.ADMIN)){
            throw new CustomException(StatusEnum.NOT_AUTHENTICATION);
        }
    }

    //댓글 존재 여부 확인
    private Comment checkComment(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomException(StatusEnum.NOT_EXIST_COMMENT)
        );
        return comment;
    }
    //댓글 권한 여부
    private void isCommentUsers(Users users, Comment comment){
        if(!comment.getUsers().getUsername().equals(users.getUsername()) && !users.getRole().equals(UserRoleEnum.ADMIN)){
            throw new CustomException(StatusEnum.NOT_AUTHENTICATION);
        }
    }

    // 게시글 좋아요 여부 확인
    private boolean CrudLikeCheck(Users users, Crud crud){
        Optional<CrudLike> like = crudLikeRepository.findByUserIdAndCrudId(users.getId(), crud.getId());
        if(like.isPresent()){
            return true;
        }
        return false;
    }

    //댓글 좋아요 여부 확인
    protected boolean commentLikeCheck(Users users, Comment comment) {
        Optional<CommentLike> like = commentLikeRepository.findByUserIdAndCommentId(users, comment);
        if (like.isPresent()) {
            return true;
        }
        return false;
    }
}