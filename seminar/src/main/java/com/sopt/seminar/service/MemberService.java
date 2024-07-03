package com.sopt.seminar.service;

import com.sopt.seminar.auth.UserAuthentication;
import com.sopt.seminar.auth.redis.domain.Token;
import com.sopt.seminar.auth.redis.repository.RedisTokenRepository;
import com.sopt.seminar.common.jwt.JwtTokenProvider;
import com.sopt.seminar.domain.Member;
import com.sopt.seminar.common.dto.ErrorMessage;
import com.sopt.seminar.dto.MemberCreateDto;
import com.sopt.seminar.dto.MemberFindDto;
import com.sopt.seminar.dto.UserJoinResponse;
import com.sopt.seminar.exception.NotFoundException;
import com.sopt.seminar.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTokenRepository redisTokenRepository;

    /*
    1. Member 객체 생성
    2. MemberRepository 내부의 메서드 통해 객체 DB에 반영
    */
//    @Transactional //변경사항 DB에 반영해주기 위한 어노테이션
//    public String createMember(MemberCreateDto memberCreateDto){
//        Member member = Member.create(memberCreateDto.name(),memberCreateDto.part(),memberCreateDto.age()); //1번
//        memberRepository.save(member); //2번
//        return member.getId().toString();
//    }

    @Transactional
    public UserJoinResponse createMember(
            MemberCreateDto memberCreate
    ) {
        Member member = memberRepository.save(Member.create(memberCreate.name(), memberCreate.part(), memberCreate.age()));
        Long memberId = member.getId();
        String accessToken = jwtTokenProvider.issueAccessToken(UserAuthentication.createUserAuthentication(memberId));
        String refreshToken = jwtTokenProvider.issueRefreshToken(UserAuthentication.createUserAuthentication(memberId));

        //redis에 refreshToken 저장 -> 이걸로 accessToken 다시 발급 받아야 하니까!
        redisTokenRepository.save(Token.of(memberId, refreshToken));
        return UserJoinResponse.of(accessToken, refreshToken, memberId.toString());
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
