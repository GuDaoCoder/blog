package com.blog.biz.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * @author zouzhangpeng
 */
@Tag(name = "测试", description = "测试")
@Slf4j
@RestController
public class TestController {

    /**
     *
     * @param
     * @return java.lang.String
     */
    @Operation(description = "测试")
    @GetMapping("/test")
    public String test() {
        CompletableFuture.runAsync(() -> {
            log.info("i am test");
        });
        return "Hello World";
    }
}
