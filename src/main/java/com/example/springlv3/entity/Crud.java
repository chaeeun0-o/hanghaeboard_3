package com.example.springlv3.entity;
import com.example.springlv3.dto.CrudRequestDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@Table
@NoArgsConstructor
public class Crud extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crudId")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @ColumnDefault("0")
    private long likeCount;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Users users;

    @OneToMany(mappedBy = "crud", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY) //fetch지연로딩(LAZY), 즉시로딩(EAGER)
    @OrderBy("createdAt desc")
    @JsonManagedReference
    private List<Comment> commentList = new ArrayList<>();

    public Crud(CrudRequestDto requestDto, Users users)  {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.users = users;
    }
    public void update(CrudRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public void crudLike(long crudLikeCount) {
        this.likeCount = crudLikeCount;
    }

}

