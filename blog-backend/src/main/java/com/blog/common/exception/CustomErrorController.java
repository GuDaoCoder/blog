package com.blog.common.exception;

/**
 * @author zouzhangpeng
 * @desc
 */

import com.blog.common.domain.Result;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zouzhangpeng
 * @desc 处理404异常
 */
@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result<Void> handleNotFound() {
        return Result.fail("资源不存在或已被删除");
    }

}
