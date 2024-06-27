package com.sopt.seminar.service;

import com.sopt.seminar.domain.Member;
import com.sopt.seminar.common.dto.ErrorMessage;
import com.sopt.seminar.dto.MemberCreateDto;
import com.sopt.seminar.dto.MemberFindDto;
import com.sopt.seminar.exception.NotFoundException;
import com.sopt.seminar.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /*
    1. Member 객체 생성
    2. MemberRepository 내부의 메서드 통해 객체 DB에 반영
    */
    @Transactional //변경사항 DB에 반영해주기 위한 어노테이션
    public String createMember(MemberCreateDto memberCreateDto){
        Member member = Member.create(memberCreateDto.name(),memberCreateDto.part(),memberCreateDto.age()); //1번
        memberRepository.save(member); //2번
        return member.getId().toString();
    }

    public MemberFindDto findMemberById(Long memberId) {
        return MemberFindDto.of(memberRepository.findById(memberId).orElseThrow(
                () -> new EntityNotFoundException("ID에 해당하는 사용자가 존재하지 않습니다.")
        ));
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.MEMBER_NOT_FOUND));
    }

    public void deleteMemberById(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new EntityNotFoundException("ID에 해당하는 사용자가 존재하지 않습니다.")
        );
        memberRepository.delete(member);
    }
}
