package com.sopt.seminar.service;

import com.sopt.seminar.domain.Blog;
import com.sopt.seminar.domain.Member;
import com.sopt.seminar.dto.BlogCreateRequest;
import com.sopt.seminar.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final MemberService memberService;

    public String create(Long memberId, BlogCreateRequest blogCreateRequest) {
        Member member = memberService.findById(memberId);
        Blog blog = blogRepository.save(Blog.create(member,blogCreateRequest));
        return blog.getId().toString();
    }


}
