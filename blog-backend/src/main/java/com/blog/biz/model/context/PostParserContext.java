package com.blog.biz.model.context;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Accessors(chain = true)
@Data
public class PostParserContext {

    private String title;

    private List<Category> categories;

    private String coverPictureUrl;

    private String summary;

    private String content;

    private List<String> tags;

    private LocalDateTime fileLastUpdate;

    @Accessors(chain = true)
    @Data
    public static class Category {

        private String categoryName;

        private Integer orderNo;

        private Integer level;

    }

}
