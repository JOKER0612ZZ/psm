package com.zz.psmback.common.utils;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zouzan
 * @date 2024/28/02
 */
@Component
public class PaginationUtils<T> {
    /**
     * @param list
     * @param pageNum
     * @param pageSize
     * @return {@code List<T>}
     */
    public List<T> pagination(List<T> list, int pageNum, int pageSize){
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, list.size());
        return list.subList(startIndex, endIndex);
    }
}
