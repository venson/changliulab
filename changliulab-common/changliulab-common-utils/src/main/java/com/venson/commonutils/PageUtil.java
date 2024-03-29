package com.venson.commonutils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


public  class PageUtil {
    private PageUtil(){}
    public static <T> PageResponse<T> toBean(Page<T> page){
        PageResponse<T> res = new PageResponse<>();
        res.setRecords(page.getRecords());
        res.setPages(Math.toIntExact(page.getPages()));
        res.setSize(Math.toIntExact(page.getSize()));
        res.setCurrent(Math.toIntExact(page.getCurrent()));
        res.setTotal(Math.toIntExact(page.getTotal()));
        res.setHasNext(page.hasNext());
        res.setHasPrevious(page.hasPrevious());
        return res;
    }
}
