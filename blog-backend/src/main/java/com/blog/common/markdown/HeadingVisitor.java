package com.blog.common.markdown;

import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Heading;

import java.util.function.Consumer;

public class HeadingVisitor extends AbstractVisitor {

    private Consumer<Heading> consumer;

    public HeadingVisitor(Consumer<Heading> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void visit(Heading heading) {
        super.visit(heading);
        consumer.accept(heading);
    }
}
