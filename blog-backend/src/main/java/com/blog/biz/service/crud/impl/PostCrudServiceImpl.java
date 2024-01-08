package com.blog.biz.service.crud.impl;

import com.blog.biz.model.entity.PostEntity;
import com.blog.biz.model.entity.QPostEntity;
import com.blog.biz.repository.PostRepository;
import com.blog.biz.service.crud.PostCrudService;
import com.blog.common.base.service.impl.BaseCrudServiceImpl;
import com.blog.common.util.JpaUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@Service
public class PostCrudServiceImpl extends BaseCrudServiceImpl<PostEntity, PostRepository> implements PostCrudService {
    public PostCrudServiceImpl(PostRepository repository) {
        super(repository);
    }

    @Override
    public Page<PostEntity> page(PostEntity postEntity, Boolean encrypt, Pageable pageable) {
        return JpaUtil.query(QPostEntity.postEntity)
            .like(StringUtils.isNotBlank(postEntity.getTitle()), QPostEntity.postEntity.title, postEntity.getTitle())
            .eq(postEntity.getCategoryId() != null, QPostEntity.postEntity.postId, postEntity.getPostId())
            .page(pageable);
    }

}
