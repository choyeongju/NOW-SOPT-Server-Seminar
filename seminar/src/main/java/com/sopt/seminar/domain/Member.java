package com.sopt.seminar.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // 객체와 테이블을 매핑
@Getter
@NoArgsConstructor // @Entity가 붙은 클래스는 기본 생성자 필수로 가져야 하므로
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 직접 할당하는 대신, db가 생성해주는 값 사용
    private Long id;

    private String name;

    // Java의 enum 타입 매핑 때 사용하는 어노테이션
    @Enumerated(EnumType.STRING)
    private Part part;

    private int age;

    @Builder
    public Member(String name, Part part, int age) {
        this.name = name;
        this.part = part;
        this.age = age;
    }

    public static Member create(String name, Part part, int age){
        return Member.builder()
                .name(name)
                .part(part)
                .age(age)
                .build();
    }
}
