package com.venson.commonutils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.HashMap;
import java.util.Map;

public  class PageUtil {
    public static Map<String,Object> toMap(Page<?> page){
        HashMap<String, Object> map = new HashMap<>();
        map.put("records", page.getRecords());
        map.put("pages", page.getPages());
        map.put("current", page.getCurrent());
        map.put("size",page.getSize());
        map.put("total",page.getTotal());
        map.put("hasNext",page.hasNext());
        map.put("hasPrevious",page.hasPrevious());
        return map;
    }
}
