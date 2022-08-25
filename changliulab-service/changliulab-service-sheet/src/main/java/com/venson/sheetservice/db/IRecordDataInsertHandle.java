package com.venson.sheetservice.db;

import com.alibaba.fastjson.JSONObject;
import com.venson.sheetservice.entity.GridRecordDataModel;

import java.util.List;

/**
 * 插入数据
 * @author Administrator
 */
public interface IRecordDataInsertHandle {
    /**
     * 新增Sheet页,并返回刚刚插入的_id
     */
    String insert(GridRecordDataModel pgModel);
    /**
     * 批量添加 添加jsonb
     */
    String InsertIntoBatch(List<GridRecordDataModel> models);

    /**
     * 批量添加 添加jsonb
     */
    String InsertBatchDb(List<JSONObject> models);
}
