package com.sopt.seminar.controller;

import com.sopt.seminar.auth.PrincipalHandler;
import com.sopt.seminar.dto.BlogCreateRequest;
import com.sopt.seminar.common.dto.SuccessMessage;
import com.sopt.seminar.common.dto.SuccessStatusResponse;
import com.sopt.seminar.dto.BlogTitleUpdateRequest;
import com.sopt.seminar.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;
    private final PrincipalHandler principalHandler;

    @PostMapping("/blog")
    @Operation(summary = "블로그 생성 API", description = "블로그를 생성하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "블로그 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<Void> createBlog(
            @Parameter(description = "블로그 생성 요청 데이터") @Valid @RequestBody BlogCreateRequest blogCreateRequest
    ) {
        return ResponseEntity.created(URI.create(blogService.create(
                principalHandler.getUserIdFromPrincipal(), blogCreateRequest
        ))).build();
    }

    @PatchMapping("/blog/{blogId}/title")
    @Operation(summary = "블로그 제목 업데이트 API", description = "블로그 제목을 업데이트하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "블로그 제목 업데이트 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "404", description = "블로그를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<Void> updateBlogTitle(
            @Parameter(description = "블로그 ID", example = "1") @PathVariable Long blogId,
            @Parameter(description = "블로그 제목 업데이트 요청 데이터") @Valid @RequestBody BlogTitleUpdateRequest blogTitleUpdateRequest
    ) {
        blogService.updateTitle(blogId, blogTitleUpdateRequest);
        return ResponseEntity.noContent().build();
    }
}
