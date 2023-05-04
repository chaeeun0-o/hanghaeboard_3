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
@RequiredArgsConstructor
public class LikeService {

//    private final Utils utils;
    private final CommentLikeRepository commentLikeRepository;
    private final CrudLikeRepository crudLikeRepository;
    private final CommentRepository commentRepository;
    private final CrudRepository crudRepository;

    //게시글 좋아요
    @Transactional
    public StatusDto crudlike(Long crudId, Users users){
        Crud crud = checkCrud(crudId); //게시글 체크

        if(CrudLikeCheck(users,crud)){//좋아요가 있으면 삭제, 없으면 생성
            CrudLike crudLike = new CrudLike(crud, users);
            crudLikeRepository.deleteByUsersIdAndCrudId(users.getId(), crudId);
            crud.minusLike();
            return StatusDto.setSuccess(HttpStatus.OK.value(), "게시글 좋아요 취소", crud.getLikeCount());
        } else{
            CrudLike crudLike = new CrudLike(crud, users);
            crudLikeRepository.save(crudLike);
            crud.crudLike();
            return StatusDto.setSuccess(HttpStatus.OK.value(), "게시글 좋아요 성공", crud.getLikeCount());
        }

    }

    //댓글 좋아요
    @Transactional
    public StatusDto commentlike(Long commentId, Users users) {
        Comment comment = checkComment(commentId); //댓글확인

        if(commentLikeCheck(users, comment)){//좋아요가 있으면  삭제, 없으면 생성
            CommentLike commentLikes = new CommentLike(comment, users);
            commentLikeRepository.deleteByUsersIdAndCommentId(users.getId(), commentId);
            comment.minusLike();
            return StatusDto.setSuccess(HttpStatus.OK.value(), "댓글 좋아요 삭제 성공", comment.getLikeCount());
        } else {
            CommentLike commentLikes = new CommentLike(comment, users);
            commentLikeRepository.save(commentLikes);
            comment.commentLike();
            return StatusDto.setSuccess(HttpStatus.OK.value(), "댓글 좋아요 성공", comment.getLikeCount());
        }
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

    // 게시글 좋아요 여부 확인
    public boolean CrudLikeCheck(Users users, Crud crud){
        Optional<CrudLike> like = crudLikeRepository.findByUsersAndCrud(users, crud);
        if(like.isPresent()){
            return true;
        }
        return false;
    }

    //댓글 좋아요 여부 확인
    public boolean commentLikeCheck(Users users, Comment comment) {
        Optional<CommentLike> like = commentLikeRepository.findByUsersAndComment(users, comment);
        if (like.isPresent()) {
            return true;
        }
        return false;
    }
}
