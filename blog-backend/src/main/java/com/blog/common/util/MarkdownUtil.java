package com.blog.common.util;

import com.blog.common.markdown.HeadingVisitor;
import org.apache.commons.lang3.StringUtils;
import org.commonmark.node.Node;
import org.commonmark.node.Paragraph;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;

import java.util.concurrent.atomic.AtomicReference;

public class MarkdownUtil {

    public static String parseSummary(Node node){
        AtomicReference<String> summary = new AtomicReference<>(null);
        final boolean[] find = {false};
        HeadingVisitor headingVisitor = new HeadingVisitor(heading -> {
            if (!find[0] && heading.getLevel() == 2 && heading.getNext() instanceof Paragraph paragraph) {
                if (paragraph.getFirstChild() instanceof Text text){
                    String literal = text.getLiteral();
                    if (StringUtils.isNotBlank(literal)){
                        summary.set(literal);
                        find[0] = true;
                    }
                }
            }
        });

        // 遍历AST
        node.accept(headingVisitor);
        return summary.get();
    }

    public static void main(String[] args) {
        // 要解析的Markdown文本
        String markdown = "# 标题1\n" +
                "## 二级标题1\n" +
                "这是二级标题1下的内容\n" +
                "## 二级标题2\n" +
                "这是二级标题2下的内容\n" +
                "# 标题2\n" +
                "这是标题2下的内容";
        System.out.println(parseSummary(Parser.builder().build().parse(markdown)));
    }
}
