package com.venson.sheetservice.db;

import com.venson.sheetservice.entity.GridRecordDataModel;

import java.util.List;

/**
 * 删除
 * @author Administrator
 */
public interface IRecordDelHandle {
    /**
     * 删除sheet（非物理删除）
     */
    boolean updateDataForReDel(GridRecordDataModel model);

    /**
     * 按ID 删除多个文档 （物理删除）
     */
    String delDocuments(List<String> ids);

    /**
     * 按list_id 删除记录 （物理删除）
     */
    int[] delete(List<String> listIds);




}
