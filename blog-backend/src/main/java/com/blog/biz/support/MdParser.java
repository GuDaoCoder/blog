package com.blog.biz.support;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.io.Files;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public final class MdParser {

    /**
     * markdown文件后缀
     */
    private final static String MD_SUFFIX = "md";

    /**
     * 文章额外信息标识符
     */
    private final static String THEMATIC_BREAK_SYMBOL = "---\n";

    /**
     * 文章摘要分隔符，获取文章简介时只获取该标识之前的内容
     */
    private final static String SUMMARY_SYMBOL = "<!-- more -->";

    /**
     * 图片正则
     */
    private final static Pattern patten = Pattern.compile("!\\[([^\\]]*)\\]\\(([^\\)]+)\\)");

    /**
     * markdown文件
     */
    private final File file;

    /**
     * 替换图片url
     */
    private final Function<String, String> replaceImageUrl;

    /**
     * 原始文件内容
     */
    private final String originalContent;

    /**
     * commonmark解析的文档
     */
    private final Node document;

    public MdParser(Builder builder) throws IOException {
        this.file = builder.file;
        this.replaceImageUrl = builder.replaceImageUrl;
        this.originalContent = Files.asCharSource(file, Charsets.UTF_8).read();
        this.document = Parser.builder().build().parse(originalContent);
    }

    /**
     * 解析markdown
     * @param
     * @return Context
     **/
    public Context parse() {
        return new Context().setTitle(parseTitle())
            .setSummary(parseSummary())
            .setContent(parseContent())
            .setFileLastUpdate(parseLastUpdate())
            .setExtra(parseExtra());
    }

    /**
     * 解析文章标题，即文件名称
     * @param
     * @return String
     **/
    private String parseTitle() {
        return Files.getNameWithoutExtension(file.getName());
    }

    /**
     * 解析文章内容
     * @param
     * @return String
     **/
    private String parseContent() {
        String content = originalContent;
        if (content != null) {
            // 文章可能会通过---添加一些额外信息，需要去除
            int firstIndex = content.indexOf(THEMATIC_BREAK_SYMBOL);
            if (firstIndex != -1) {
                int secondIndex = content.indexOf(THEMATIC_BREAK_SYMBOL, firstIndex + 1);
                if (secondIndex != -1) {
                    content = content.substring(secondIndex + 4);
                }
            }
            // 替换图片url
            if (replaceImageUrl != null) {
                StringBuilder result = new StringBuilder();
                Matcher matcher = patten.matcher(content);
                while (matcher.find()) {
                    String originalImageUrl = matcher.group(2);
                    String newImageUrl = replaceImageUrl.apply(originalImageUrl);
                    String replacement = "![" + matcher.group(1) + "](" + newImageUrl + ")";
                    matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
                }
                matcher.appendTail(result);
                content = result.toString();
            }
        }
        return content;
    }

    /**
     * 解析文章简介
     * @param
     * @return String
     **/
    private String parseSummary() {
        if (document != null) {
            Heading firstSecondTitle = findFirstSecondTitle(document.getFirstChild());
            if (firstSecondTitle != null && firstSecondTitle.getNext() instanceof Paragraph paragraph) {
                if (paragraph.getFirstChild() instanceof Text text) {
                    String literal = text.getLiteral();
                    int index = literal.indexOf(SUMMARY_SYMBOL);
                    if (index != -1) {
                        return literal.substring(0, index).trim();
                    }
                    else {
                        return literal;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 解析文章的最后修改时间
     * @param
     * @return LocalDateTime
     **/
    public LocalDateTime parseLastUpdate() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), ZoneId.systemDefault());
    }

    /**
     * 找到文档中的第一个二级标题
     * @param node
     * @return Heading
     **/
    private Heading findFirstSecondTitle(Node node) {
        if (node == null) {
            return null;
        }
        if (node instanceof Heading heading && heading.getLevel() == 2) {
            return heading;
        }
        else {
            return findFirstSecondTitle(node.getNext());
        }
    }

    /**
     * 解析文章额外信息
     * @param
     * @return Extra
     **/
    public Extra parseExtra() {
        if (StringUtils.isNotBlank(originalContent)) {
            int firstIndex = originalContent.indexOf(THEMATIC_BREAK_SYMBOL);
            if (firstIndex != -1) {
                int secondIndex = originalContent.indexOf(THEMATIC_BREAK_SYMBOL, firstIndex + 1);
                if (secondIndex != -1) {
                    String extraStr = originalContent.substring(firstIndex + 4, secondIndex);
                    Yaml yaml = new Yaml();
                    try {
                        return yaml.loadAs(extraStr, Extra.class);
                    }
                    catch (Exception e) {
                        log.warn("convert PostExtraYaml error", e);
                        return null;
                    }
                }
            }
        }
        return null;
    }

    public static class Builder {

        private File file;

        private Function<String, String> replaceImageUrl;

        public Builder() {
        }

        public Builder file(File file) {
            if (file == null) {
                throw new IllegalArgumentException("markdown file is null");
            }
            if (!StringUtils.equals(MD_SUFFIX, Files.getFileExtension(file.getName()))) {
                throw new IllegalArgumentException("file is not markdown");
            }
            this.file = file;
            return this;
        }

        public Builder replaceImageUrl(Function<String, String> replaceImageUrl) {
            this.replaceImageUrl = replaceImageUrl;
            return this;
        }

        public MdParser build() throws IOException {
            return new MdParser(this);
        }

    }

    @Accessors(chain = true)
    @Data
    public static class Context {

        /**
         * 文章标题
         */
        private String title;

        /**
         * 文章简介
         */
        private String summary;

        /**
         * 文章内容
         */
        private String content;

        /**
         * 文件最后修改时间
         */
        private LocalDateTime fileLastUpdate;

        /**
         * 额外信息
         */
        private Extra extra;

    }

    @Data
    public static class Extra {

        /**
         * 封面图片
         */
        private String coverPictureUrl;

        /**
         * 标签
         */
        private List<String> tags;

    }

}
