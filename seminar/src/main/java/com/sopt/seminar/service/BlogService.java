package com.sopt.seminar.service;

import com.sopt.seminar.common.dto.ErrorMessage;
import com.sopt.seminar.domain.Blog;
import com.sopt.seminar.domain.Member;
import com.sopt.seminar.dto.BlogCreateRequest;
import com.sopt.seminar.dto.BlogTitleUpdateRequest;
import com.sopt.seminar.exception.NotFoundException;
import com.sopt.seminar.repository.BlogRepository;
import jakarta.transaction.Transactional;
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

    private Blog findById(Long blogId){
        return blogRepository.findById(blogId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.BLOG_NOT_FOUND)
        );
    }

    @Transactional
    public void updateTitle(Long blogId, BlogTitleUpdateRequest blogTitleUpdateRequest) {
        Blog blog = findById(blogId);
        blog.updateTitle(blogTitleUpdateRequest);
    }
}
