package com.blog.biz.controller.portal;

import com.blog.biz.model.request.PostPortalSearchRequest;
import com.blog.biz.model.response.PostDetailResponse;
import com.blog.biz.model.response.PostResponse;
import com.blog.biz.service.manager.PostManagerService;
import com.blog.common.base.response.PageResponse;
import com.blog.common.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "文章管理")
@RestController
@RequestMapping("/portal/posts")
public class PostPortalController {

	private final PostManagerService postManagerService;

	@Operation(summary = "查询文章列表")
	@GetMapping
	public Result<PageResponse<PostResponse>> search(@ParameterObject @Validated PostPortalSearchRequest request) {
		return Result.success(postManagerService.blogSearch(request));
	}

	@Operation(summary = "查询文章详情")
	@GetMapping("/{postId}")
	public Result<PostDetailResponse> detail(@Parameter(description = "文章Id") @PathVariable Long postId) {
		return Result.success(postManagerService.detail(postId));
	}

}
