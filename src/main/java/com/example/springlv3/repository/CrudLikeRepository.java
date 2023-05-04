package com.example.springlv3.repository;

import com.example.springlv3.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CrudLikeRepository extends JpaRepository<CrudLike, Long> {
    Optional<CrudLike> findByUserIdAndCrudId(Long userId, Long crudId); //좋아요 확인용
    void deleteByUserIdAndCrudId(Long userId, Long crudId); //좋아요 취소
    long countByCrudId(Long crudId); //좋아요 갯수 카운트
}