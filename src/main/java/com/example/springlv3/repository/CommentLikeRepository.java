package com.example.springlv3.repository;

import com.example.springlv3.entity.Comment;
import com.example.springlv3.entity.CommentLike;
import com.example.springlv3.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByUsersAndComment(Users users, Comment comment); //좋아요 확인
    void deleteByUsersIdAndCommentId(Long userId, Long commentId); //좋아요 취소
    long countByCommentId(Long commentId); //좋아요 갯수 카운트
}
