package com.example.springlv3.controller;

import com.example.springlv3.dto.CrudRequestDto;
import com.example.springlv3.dto.CrudResponseDto;
import com.example.springlv3.dto.StatusDto;
import com.example.springlv3.security.UserDetailsImpl;
import com.example.springlv3.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CrudController {

    private final CrudService crudService;

    //글 생성하기
    @PostMapping("/post")
    public StatusDto createCrud(@RequestBody CrudRequestDto requestDto,
                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return crudService.createCrud(requestDto, userDetails.getUser());
    }

    //전체 조회
    @GetMapping("/posts")
    public List<CrudResponseDto> list(){
        return crudService.getCrudList();
    }

    // 선태 조회
    @GetMapping("/post/{id}")
    public CrudResponseDto getCrud(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return crudService.getCrud(id, userDetails.getUser());
    }


    //수정하기
    @PutMapping("/post/{id}")
    public StatusDto updateCrud(@PathVariable Long id, @RequestBody CrudRequestDto requestDto,
                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return crudService.updateCrud(id,requestDto, userDetails.getUser());
    }

    //삭제
    @DeleteMapping("/post/{id}")
    public StatusDto deleteCrud(@PathVariable Long id,
                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return crudService.deleteCrud(id, userDetails.getUser());

    }
}
