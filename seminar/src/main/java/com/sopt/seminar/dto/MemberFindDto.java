package com.sopt.seminar.dto;

import com.sopt.seminar.domain.Member;
import com.sopt.seminar.domain.Part;

public record MemberFindDto(
        String name,
        Part part,
        int age
) {

    // 정적 팩토리 메서드로 객체 생성할 수 있게끔 하는 of 메서드
    public static MemberFindDto of(Member member) {
        return new MemberFindDto(member.getName(), member.getPart(), member.getAge());
    }
}
