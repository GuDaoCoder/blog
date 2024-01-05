package com.blog.common.redis.util;

import com.blog.BlogBackendApplication;
import com.blog.common.domain.UserDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@SpringBootTest(classes = BlogBackendApplication.class)
public class RedisUtilTest {

    @Test
    public void testCacheString(){
        RedisUtil.setCacheObject("test","hello world");
    }

    @Test
    public void testGetString(){
        String value = RedisUtil.getCacheObject("test");
        assert "hello world".equals(value);
    }

    @Test
    public void testCacheObject(){
        UserDetail userDetail = new UserDetail();
        userDetail.setUserId(1L);
        userDetail.setUsername("zouzhangpeng");
        RedisUtil.setCacheObject("userDetail",userDetail);
    }

    @Test
    public void testGetObject(){
        UserDetail userDetail = RedisUtil.getCacheObject("userDetail");
        System.out.println(userDetail);
    }

    @Test
    public void testGetTimeToLive(){
        final long time = RedisUtil.getTimeToLive("test");
        System.out.println(time);
    }
}
