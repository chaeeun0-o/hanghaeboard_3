package com.example.springlv3.controller;

import com.example.springlv3.dto.*;
import com.example.springlv3.security.UserDetailsImpl;
import com.example.springlv3.service.CommentService;
import com.example.springlv3.service.CrudService;
import com.example.springlv3.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//Json형태의 객체반환
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //댓글 작성
    @PostMapping("/comment/{crudId}")
    public StatusDto createComment(@PathVariable Long crudId, @RequestBody CommentRequestDto requestDto,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(crudId, requestDto, userDetails.getUser());
    }

    //댓글 수정

    @PutMapping("/comment/{commentId}")
    public StatusDto updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(commentId, requestDto, userDetails.getUser());
    }

    //댓글 삭제

    @DeleteMapping("/comment/{commentId}")
    public StatusDto deleteComment(@PathVariable Long commentId,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(commentId, userDetails.getUser());
    }
}
