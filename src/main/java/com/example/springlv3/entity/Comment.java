package com.example.springlv3.entity;


import com.example.springlv3.dto.CommentRequestDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId")
    private long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int likeCount;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crudId", nullable = false)
    @JsonBackReference //순환참조 막아줌
    private Crud crud;

    @OneToMany(mappedBy="comment",cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<CommentLike> commentLikeList = new ArrayList<>();

    public Comment(CommentRequestDto commentRequestDto, Users users, Crud crud){

        this.content = commentRequestDto.getContent();
        this.users = users;
        this.crud = crud;
    }
    public void update(CommentRequestDto requestDto) {

        this.content = requestDto.getContent();
        this.users = users;
    }

    public void commentLike () {
        ++likeCount;
    }
    public void minusLike()
    {
        --likeCount;
    }

}