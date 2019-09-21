package com.uptang.cloud.starter.common.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Slf4j
public class CollectionUtils extends org.apache.commons.collections.CollectionUtils {

    /**
     * 带Index的遍历
     *
     * @param elements 需要遍历的集合
     * @param action   带索引的消费者
     * @param <E>
     */
    public static <E> void forEach(Iterable<? extends E> elements, BiConsumer<Integer, ? super E> action) {
        Objects.requireNonNull(elements);
        Objects.requireNonNull(action);

        int index = 0;
        for (E element : elements) {
            action.accept(index++, element);
        }
    }
}
