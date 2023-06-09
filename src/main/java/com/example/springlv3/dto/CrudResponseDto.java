package com.example.springlv3.dto;

import com.example.springlv3.entity.Comment;
import com.example.springlv3.entity.Crud;
import com.example.springlv3.entity.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class CrudResponseDto {
    private Long id;
    private String title;
    private String username;
    private UserRoleEnum role;
    private String content;
    private long likeCount;
    private List<Comment> commentList;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public CrudResponseDto(Crud crud) {
        this.id = crud.getId();
        this.title = crud.getTitle();
        this.username = crud.getUsers().getUsername();
        this.role = crud.getUsers().getRole();
        this.content = crud.getContent();
        this.likeCount = crud.getLikeCount();
        this.commentList = crud.getCommentList();
        this.createAt = crud.getCreatedAt();
        this.modifiedAt = crud.getModifiedAt();
    }
}
