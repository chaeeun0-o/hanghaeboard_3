package com.example.springlv3.controller;

import com.example.springlv3.dto.StatusDto;
import com.example.springlv3.security.UserDetailsImpl;
import com.example.springlv3.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikeController{
    private final LikeService likeService;


    //게시글 좋아요
    @PutMapping ("/crud/like/{crud-id}")
        public StatusDto crudlike(@PathVariable (name = "crud-id") Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
            return likeService.crudlike(id, userDetails.getUser());
        }

    //댓글 좋아요
    @PutMapping("/comment/like/{commentId}")
    public StatusDto commentlike(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.commentlike(commentId, userDetails.getUser());
    }
}

