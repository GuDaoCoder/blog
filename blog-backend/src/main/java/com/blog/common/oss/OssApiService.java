package com.blog.common.oss;

import java.io.InputStream;

public interface OssApiService {

	/**
	 * 上传文件
	 * @param is
	 * @param directory
	 * @param fileName
	 * @return String
	 **/
	String uploadFile(InputStream is, String directory, String fileName);

}
