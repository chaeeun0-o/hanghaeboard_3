package com.example.springlv3.entity;


import com.example.springlv3.dto.CommentRequestDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId")
    private long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crudId", nullable = false)
    @JsonBackReference //순환참조 막아줌
    private Crud crud;

    public Comment(CommentRequestDto commentRequestDto, Users users, Crud crud){

        this.content = commentRequestDto.getContent();
        this.users = users;
        this.crud = crud;
    }
    public void update(CommentRequestDto requestDto) {

        this.content = requestDto.getContent();
        this.users = users;
    }
//    public void addUser(Users users){ this.users = users; }
//    public void addCrud(Crud crud){ this.crud = crud; }
}