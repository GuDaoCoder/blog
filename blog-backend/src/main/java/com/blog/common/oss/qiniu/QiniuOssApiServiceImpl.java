package com.blog.common.oss.qiniu;

import com.blog.common.exception.BusinessException;
import com.blog.common.oss.OssApiService;
import com.blog.common.oss.OssProperties;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;

public class QiniuOssApiServiceImpl implements OssApiService {

	private final OssProperties ossProperties;

	private final UploadManager uploadManager;

	private final Auth auth;

	public QiniuOssApiServiceImpl(OssProperties ossProperties, UploadManager uploadManager, Auth auth) {
		this.ossProperties = ossProperties;
		this.uploadManager = uploadManager;
		this.auth = auth;
	}

	@SneakyThrows
	@Override
	public String uploadFile(InputStream is, String directory, String fileName) {
		String token = auth.uploadToken(ossProperties.getBucketName());
		if (StringUtils.isNotBlank(directory)) {
			fileName = directory + "/" + fileName;
		}
		Response res = uploadManager.put(is, fileName, token, null, null);
		if (!res.isOK()) {
			throw new BusinessException("文件上传OSS失败：{}", res.error);
		}
		return ossProperties.getDomain() + "/" + fileName;
	}

}
