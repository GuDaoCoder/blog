package com.blog.biz.controller.admin;

import com.blog.biz.model.response.UploadImageResponse;
import com.blog.common.domain.Result;
import com.blog.common.oss.OssApiService;
import com.blog.common.oss.OssProperties;
import com.blog.common.util.FileUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Tag(name = "OSS管理")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/oss")
public class OssAdminController {

    private final OssApiService ossApiService;

    private final OssProperties ossProperties;

    @Operation(summary = "上传文件")
    @PostMapping("/upload")
    public Result<UploadImageResponse> upload(@RequestParam("file") MultipartFile file) throws IOException {
        String url;
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            url = ossApiService.uploadFile(inputStream, ossProperties.getDirectory(),
                    FileUtil.uuidFileName(file.getOriginalFilename()));
        }
        finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return Result.success(new UploadImageResponse(url));
    }

}
