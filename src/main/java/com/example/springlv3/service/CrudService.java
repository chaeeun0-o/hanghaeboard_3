package com.example.springlv3.service;

import com.example.springlv3.dto.*;
import com.example.springlv3.entity.*;
import com.example.springlv3.exception.CustomException;
import com.example.springlv3.jwt.JwtUtil;
import com.example.springlv3.repository.CommentRepository;
import com.example.springlv3.repository.CrudRepository;
import com.example.springlv3.repository.UserRepository;
import com.example.springlv3.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrudService {

    private final CrudRepository crudRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    //글 생성하기
    @Transactional
    public StatusDto createCrud(CrudRequestDto requestDto, Users users) {
        Crud crud = new Crud(requestDto, users);
        crudRepository.save(crud);
        CrudResponseDto crudResponseDto = new CrudResponseDto(crud);
        return StatusDto.setSuccess(HttpStatus.OK.value(),"게시글 등록 완료", crudResponseDto);
    }

    //전체조회
    @Transactional(readOnly = true)
    public List<CrudResponseDto> getCrudList() {
        List<Crud> crudList = crudRepository.findAllByOrderByCreatedAtDesc();
        return crudList.stream().map(CrudResponseDto::new).collect(Collectors.toList());
    }


    //선택조회
    @Transactional(readOnly = true)
    public CrudResponseDto getCrud(Long id, Users users) {
        Crud crud = checkCrud(id);
        isCrudUsers(users, crud);
        return new CrudResponseDto(crud);
    }

    //수정하기
    @Transactional
    public StatusDto updateCrud(Long id, CrudRequestDto requestDto, Users users) {
        Crud crud = checkCrud(id);
        // 권한 체크
        isCrudUsers(users, crud);
        crud.update(requestDto);
        CrudResponseDto crudResponseDto = new CrudResponseDto(crud);
        return StatusDto.setSuccess(HttpStatus.OK.value(), "수정 성공", crudResponseDto);
    }

    //삭제
    @Transactional
    public StatusDto deleteCrud(Long id, Users users) {
        // 게시글 체크
        Crud crud = checkCrud(id);
        // 권한 체크
        isCrudUsers(users, crud);
        crudRepository.deleteById(id);
        return StatusDto.setSuccess(HttpStatus.OK.value(), "삭제 성공 완료", null);
    }



    //글 존재 여부 확인
    private Crud checkCrud(Long id) {
        Crud crud = crudRepository.findById(id).orElseThrow(
                () -> new CustomException(StatusEnum.NOT_EXIST_CRUD)
        );
        return crud;
    }

    //권한 여부
    private void isCrudUsers(Users users, Crud crud){
        if(!crud.getUsers().getUsername().equals(users.getUsername()) && !users.getRole().equals(UserRoleEnum.ADMIN)){
            throw new CustomException(StatusEnum.NOT_AUTHENTICATION);
        }
    }
}



