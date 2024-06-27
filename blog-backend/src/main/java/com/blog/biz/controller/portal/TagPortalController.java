package com.blog.biz.controller.portal;

import com.blog.biz.model.request.TagSearchRequest;
import com.blog.biz.model.response.TagDetailResponse;
import com.blog.biz.service.manager.TagManagerService;
import com.blog.common.base.response.PageResponse;
import com.blog.common.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "标签管理")
@RestController
@RequestMapping("/portal/tags")
public class TagPortalController {

	private final TagManagerService tagManagerService;

	@Operation(summary = "查询标签列表")
	@GetMapping
	public Result<PageResponse<TagDetailResponse>> search(@ParameterObject TagSearchRequest request) {
		return Result.success(tagManagerService.search(request));
	}

}
