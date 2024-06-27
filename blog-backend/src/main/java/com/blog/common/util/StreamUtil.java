package com.blog.common.util;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zouzhangpeng
 * @desc
 */
public class StreamUtil {

    public static <D, T> List<D> mapField(Collection<T> coll, Function<T, D> function) {
        if (CollectionUtils.isEmpty(coll)) {
            return new ArrayList<>();
        }
        return coll.stream().map(function).filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

}
