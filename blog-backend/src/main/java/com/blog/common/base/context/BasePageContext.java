package com.blog.common.base.context;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zouzhangpeng
 * @desc
 */
@AllArgsConstructor
@Data
public class BasePageContext<Entity> {

    /**
     * 分页信息
     */
    private IPage<Entity> pageable;

}
