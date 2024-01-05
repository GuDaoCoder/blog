package com.blog.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.apache.commons.collections4.CollectionUtils;

import com.blog.common.base.response.NodeResponse;

/**
 * @author zouzhangpeng
 * @desc
 */
public class TreeUtil {

    /**
     * 构建树
     * 
     * @param data List<T>
     * @param parentId Long
     * @param idFunction Function<T
     * @param labelFunction Long>
     * @param parentIdFunction Function<T
     * @return java.util.List<com.blog.common.base.response.NodeResponse<T>>
     */
    public static <T> List<NodeResponse<T>> build(List<T> data, Long parentId, Function<T, Long> idFunction,
        Function<T, String> labelFunction, Function<T, Long> parentIdFunction) {
        if (CollectionUtils.isEmpty(data)) {
            return new ArrayList<>();
        }
        List<NodeResponse<T>> list = new ArrayList<>();
        data.forEach(item -> {
            if (Objects.equals(parentId, parentIdFunction.apply(item))) {
                NodeResponse<T> node = new NodeResponse<>();
                node.setId(idFunction.apply(item));
                node.setLabel(labelFunction.apply(item));
                node.setParentId(parentIdFunction.apply(item));
                node.setData(item);
                node.setChildren(build(data, idFunction.apply(item), idFunction, labelFunction, parentIdFunction));
                list.add(node);
            }
        });
        return list;
    }
}
