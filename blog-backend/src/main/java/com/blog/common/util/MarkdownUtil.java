package com.blog.common.util;

import com.blog.common.markdown.HeadingVisitor;
import org.apache.commons.lang3.StringUtils;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;
import org.commonmark.node.Paragraph;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;

import java.util.concurrent.atomic.AtomicReference;

public class MarkdownUtil {

    public static String parseSummary(Node node) {
        AtomicReference<String> summary = new AtomicReference<>(null);
        AtomicReference<Boolean> find = new AtomicReference<>(false);
        HeadingVisitor headingVisitor = new HeadingVisitor(heading -> {
            if (!find.get() && heading.getLevel() == 2 && heading.getNext() instanceof Paragraph paragraph) {
                if (paragraph.getFirstChild() instanceof Text text) {
                    String literal = text.getLiteral();
                    if (StringUtils.isNotBlank(literal)) {
                        summary.set(literal);
                        find.set(true);
                    }
                }
            }
        });

        // 遍历AST
        node.accept(headingVisitor);
        return summary.get();
    }

    public static void main(String[] args) {
        String markdown = "---\n" + "aa:\n" + "---\n" + "## 二级标题2";

        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);

        // 获取ThematicBreak中的内容
    }

    public static Heading findFirstSecondTitle(Node node) {
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

}
