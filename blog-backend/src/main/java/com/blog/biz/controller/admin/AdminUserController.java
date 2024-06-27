package com.blog.biz.controller.admin;

import com.blog.biz.model.request.AdminUserRequest;
import com.blog.biz.model.response.AdminUserResponse;
import com.blog.biz.service.manager.AdminUserManagerService;
import com.blog.common.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "管理员用户管理")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/adminUsers")
public class AdminUserController {

	private final AdminUserManagerService adminUserManagerService;

	@Operation(summary = "查询所有管理员用户")
	@GetMapping
	public Result<List<AdminUserResponse>> list() {
		return Result.success(adminUserManagerService.list());
	}

	@Operation(summary = "新增或更新管理员用户")
	@PostMapping("/saveOrUpdate")
	public Result<Void> saveOrUpdate(@RequestBody AdminUserRequest request) {
		adminUserManagerService.saveOrUpdate(request);
		return Result.success();
	}

}
