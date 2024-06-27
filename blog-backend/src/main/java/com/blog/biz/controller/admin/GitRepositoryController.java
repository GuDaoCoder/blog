package com.blog.biz.controller.admin;

import com.blog.biz.model.request.GitRepositoryRequest;
import com.blog.biz.model.response.GitRepositoryResponse;
import com.blog.biz.service.manager.GitRepositoryManagerService;
import com.blog.common.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Git仓库管理")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/gitRepositories")
public class GitRepositoryController {

	private final GitRepositoryManagerService gitRepositoryManagerService;

	@Operation(summary = "查询Git仓库信息")
	@GetMapping
	public Result<List<GitRepositoryResponse>> list() throws InterruptedException {
		return Result.success(gitRepositoryManagerService.list());
	}

	@Operation(summary = "新增Git仓库信息")
	@PostMapping
	public Result<Long> save(@RequestBody GitRepositoryRequest request) {
		return Result.success(gitRepositoryManagerService.save(request));
	}

}
