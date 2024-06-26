package com.sopt.seminar.controller;

import com.sopt.seminar.dto.MemberCreateDto;
import com.sopt.seminar.dto.MemberFindDto;
import com.sopt.seminar.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController // Java 객체로 반환되는 값을 JSON으로 변환하여 리턴. 이 클래스는 API 엔드포인트로 작동!
@RequiredArgsConstructor //클라이언트에게 받은 요청을 특정 메서드와 매핑
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity createMember(
            @RequestBody MemberCreateDto memberCreate
    ){
        return ResponseEntity.created(URI.create(memberService.createMember(memberCreate))).build();
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberFindDto> findMemberById(
            @PathVariable Long memberId
    ){
        return ResponseEntity.ok(memberService.findMemberById(memberId));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity deleteMemberById(
            @PathVariable Long memberId
    ){
        memberService.deleteMemberById(memberId);
        return ResponseEntity.noContent().build();
    }
}
