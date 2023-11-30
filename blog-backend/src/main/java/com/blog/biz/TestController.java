package com.blog.biz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * @author zouzhangpeng
 */
@Slf4j
@RestController
public class TestController {

    /**
     *
     * @param   
 * @return java.lang.String
     */
    @GetMapping("/test")
    public String test() {
        CompletableFuture.runAsync(() -> {
            log.info("i am test");
        });
        return "Hello World";
    }
}
