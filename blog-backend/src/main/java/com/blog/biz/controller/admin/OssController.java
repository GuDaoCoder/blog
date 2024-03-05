package com.blog.biz.controller.admin;

import com.blog.biz.model.response.UploadImageResponse;
import com.blog.common.domain.Result;
import com.blog.common.oss.OssApiService;
import com.blog.common.oss.OssProperties;
import com.blog.common.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/oss")
public class OssController {

    private final OssApiService ossApiService;

    private final OssProperties ossProperties;

    @PostMapping("/uploadImageBed")
    public Result<UploadImageResponse> uploadImageBed(@RequestParam("file") MultipartFile file) throws IOException {
        String url;
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            url = ossApiService.uploadFile(inputStream, ossProperties.getImageBedDirectory(),
                    FileUtil.resetFileName(file.getOriginalFilename()));
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return Result.success(new UploadImageResponse(url));
    }
}
