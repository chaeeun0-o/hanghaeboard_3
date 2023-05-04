package com.example.springlv3.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Table
@Entity
@NoArgsConstructor

public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentlikeId")
    private Long id;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "crudId")
    private Crud crud;

    @JsonBackReference // 순환참조 방지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Users users;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "commentId")
    private Comment comment;

    public CommentLike(Comment comment, Users users){
        this.users = users;
        this.comment = comment;
    }
}
