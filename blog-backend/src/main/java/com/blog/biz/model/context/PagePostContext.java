package com.blog.biz.model.context;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.biz.enums.PostSource;
import com.blog.biz.enums.PostStatus;
import com.blog.biz.model.entity.PostEntity;
import com.blog.common.base.context.BasePageContext;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zouzhangpeng
 * @desc
 */
@Getter
@Setter
public class PagePostContext extends BasePageContext<PostEntity> {

    /**
     * 标题
     */
    private String title;

    /**
     * 状态
     */
    private PostStatus status;

    /**
     * 文章来源
     */
    private PostSource source;

    /**
     * 所属分类Id
     */
    private Long categoryId;

    /**
     * 发布开始时间
     */
    private LocalDateTime publishStartTime;

    /**
     * 发布结束时间
     */
    private LocalDateTime publishEndTime;

    /**
     * 创建开始时间
     */
    private LocalDateTime createStartTime;

    /**
     * 创建结束时间
     */
    private LocalDateTime createEndTime;

    /**
     * 是否加密
     */
    private Boolean encrypt;

    /**
     * 是否置顶
     */
    private Boolean top;

    /**
     * 是否开启评论
     */
    private Boolean enableComment;

    public PagePostContext(IPage<PostEntity> pageable) {
        super(pageable);
    }
}
