package com.zz.psmback.common.utils;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * @author zouzan
 * @date 2024/03/02
 */
@Component
public class PaginationUtils<T> {
    /**
     * @param list
     * @param pageNum
     * @param pageSize
     * @return {@code LinkedList<T>}
     */
    public LinkedList<T> pagination(List<T> list, int pageNum, int pageSize){
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, list.size());
        return new LinkedList<T>(list.subList(startIndex, endIndex));
    }
}
