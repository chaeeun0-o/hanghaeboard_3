package com.example.springlv3.repository;

import com.example.springlv3.entity.Crud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
//spring data JPA
public interface CrudRepository extends JpaRepository<Crud, Long> {
    //내림차순 정렬
    List<Crud> findAllByOrderByCreatedAtDesc();
}
