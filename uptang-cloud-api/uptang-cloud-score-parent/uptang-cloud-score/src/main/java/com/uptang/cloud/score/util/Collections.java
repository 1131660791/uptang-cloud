package com.uptang.cloud.score.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-08 19:01
 * @mailTo: webb.lee.cn@gmail.com
 * @summary: FIXME
 */
public class Collections {

    /**
     * 实现java 中 list集合中有几十万条数据,每100条为一组取出
     *
     * @param list 可穿入几十万条数据的List
     * @return map 每一Kye中有100条数据的List
     */
    public static <T> Map<Integer, List<T>> groupList(List<T> list) {
        int listSize = list.size();
        int toIndex = 100;

        // 用map存起来新的分组后数据
        Map<Integer, List<T>> map = new HashMap();
        int keyToken = 0;

        for (int i = 0; i < list.size(); i += 100) {
            // 作用为toIndex最后没有100条数据则剩余几条newList中就装几条
            if (i + 100 > listSize) {
                toIndex = listSize - i;
            }

            List<T> newList = list.subList(i, i + toIndex);
            map.put(keyToken, newList);
            keyToken++;
        }

        return map;
    }
}
