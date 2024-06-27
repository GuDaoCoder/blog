package com.blog.biz.support;

import cn.hutool.core.io.FileUtil;
import com.blog.biz.model.context.PostParserContext;
import com.blog.common.util.MarkdownUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;
import org.commonmark.node.Paragraph;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class MarkdownParser {

    private static final String THEMATIC_BREAK_SYMBOL = "---\n";

    private static final int DEFAULT_ORDER_NO = 999;

    private static final String DEFAULT_COVER_PICTURE_URL = "https://imgapi.cn/api.php";

    /**
     * 解析文件
     * @param file
     * @param home
     * @return PostParserContext
     **/
    public static PostParserContext parse(File file, String home) {
        if (file == null || !file.exists() || !file.canRead()) {
            throw new IllegalArgumentException("File is null, does not exist, or cannot be read.");
        }

        String fileContent = FileUtil.readString(file, StandardCharsets.UTF_8);
        PostParserContext context = new PostParserContext();
        context.setTitle(parseTitle(file))
            .setCategories(parseCategories(home, file.getAbsoluteFile()))
            .setContent(parseContent(fileContent))
            .setSummary(parseSummary(fileContent))
            .setFileLastUpdate(getFileLastUpdate(file));
        parseExtraYaml(fileContent)
            .ifPresent(yaml -> context.setTags(yaml.getTags()).setCoverPictureUrl(yaml.getCoverPictureUrl()));
        if (StringUtils.isBlank(context.getCoverPictureUrl())) {
            context.setCoverPictureUrl(DEFAULT_COVER_PICTURE_URL + "?t=" + UUID.randomUUID());
        }
        return context;
    }

    /**
     * 解析文章分类
     * @param home
     * @param file
     * @return List<Category>
     **/
    public static List<PostParserContext.Category> parseCategories(String home, File file) {
        if (file == null || !file.isAbsolute()) {
            throw new IllegalArgumentException("File must be an absolute path.");
        }

        Path relativizePath = Paths.get(home).relativize(Paths.get(file.getAbsolutePath()));
        // -1是因为不包括文件名
        int count = relativizePath.getNameCount() - 1;
        if (count <= 0) {
            return new ArrayList<>();
        }
        List<PostParserContext.Category> categories = new ArrayList<>();
        for (int level = 0; level < count; level++) {
            int orderNo = DEFAULT_ORDER_NO;
            String categoryName = relativizePath.getName(level).toString();
            int firstDashIndex = categoryName.indexOf("-");
            if (firstDashIndex != -1) {
                try {
                    orderNo = Integer.parseInt(categoryName.substring(0, firstDashIndex));
                }
                catch (Exception e) {
                    log.error("parse order no error", e);
                }
                categoryName = categoryName.substring(firstDashIndex + 1);
            }
            PostParserContext.Category category = new PostParserContext.Category();
            category.setCategoryName(categoryName).setOrderNo(orderNo).setLevel(level);
            categories.add(category);
        }
        return categories;
    }

    /**
     * 解析额外的yaml信息
     * @param fileContent
     * @return Optional<PostExtraYaml>
     **/
    public static Optional<MarkdownParser.PostExtraYaml> parseExtraYaml(String fileContent) {
        if (StringUtils.isNotBlank(fileContent)) {
            int firstIndex = fileContent.indexOf(THEMATIC_BREAK_SYMBOL);
            if (firstIndex != -1) {
                int secondIndex = fileContent.indexOf(THEMATIC_BREAK_SYMBOL, firstIndex + 1);
                if (secondIndex != -1) {
                    String extraStr = fileContent.substring(firstIndex + 4, secondIndex);
                    Yaml yaml = new Yaml();
                    try {
                        return Optional.of(yaml.loadAs(extraStr, MarkdownParser.PostExtraYaml.class));
                    }
                    catch (Exception e) {
                        log.error("convert PostExtraYaml error", e);
                        return Optional.empty();
                    }
                }
            }
        }
        return Optional.empty();
    }

    /**
     * 解析文章内容
     * @param fileContent
     * @return String
     **/
    public static String parseContent(String fileContent) {
        if (StringUtils.isNotBlank(fileContent)) {
            int firstIndex = fileContent.indexOf(THEMATIC_BREAK_SYMBOL);
            if (firstIndex != -1) {
                int secondIndex = fileContent.indexOf(THEMATIC_BREAK_SYMBOL, firstIndex + 1);
                if (secondIndex != -1) {
                    return fileContent.substring(secondIndex + 4);
                }
            }
        }
        return fileContent;
    }

    /**
     * 解析文章简介
     * @param fileContent
     * @return String
     **/
    public static String parseSummary(String fileContent) {
        if (StringUtils.isBlank(fileContent)) {
            return fileContent;
        }

        Parser parser = Parser.builder().build();
        Node document;
        try {
            document = parser.parse(fileContent);
        }
        catch (Exception e) {
            log.error("parse summary error", e);
            return null;
        }

        Heading firstSecondTitle = MarkdownUtil.findFirstSecondTitle(document.getFirstChild());
        if (firstSecondTitle != null && firstSecondTitle.getNext() instanceof Paragraph paragraph) {
            if (paragraph.getFirstChild() instanceof Text text) {
                return text.getLiteral();
            }
        }
        return null;
    }

    /**
     * 解析标题，即文件名称
     * @param file
     * @return String
     **/
    public static String parseTitle(File file) {
        if (file != null) {
            String fileName = file.getName();
            if (fileName.isEmpty()) {
                return null;
            }
            int lastDotIndex = fileName.lastIndexOf(".");
            if (lastDotIndex != -1) {
                return fileName.substring(0, lastDotIndex);
            }
            return fileName;
        }
        return null;
    }

    /**
     * 获取文件的最后更新时间
     * @param file
     * @return LocalDateTime
     **/
    public static LocalDateTime getFileLastUpdate(File file) {
        if (file == null) {
            throw new IllegalArgumentException("File can not be null.");
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), ZoneId.systemDefault());
    }

    @Data
    public static class PostExtraYaml {

        private String coverPictureUrl;

        private List<String> tags;

    }

}
