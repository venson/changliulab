package com.venson.sheetservice.service.impl;

import com.venson.sheetservice.db.IRecordDelHandle;
import com.venson.sheetservice.db.JfGridConfigModel;
import com.venson.sheetservice.entity.GridRecordDataModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;

/**
 * 删除
 * @author Administrator
 */
@Slf4j
@Repository(value = "mysqlRecordDelHandle")
public class RecordDelHandle extends BaseHandle implements IRecordDelHandle {
    /**
     * 删除sheet（非物理删除）
     *
     */
    @Override
    public boolean updateDataForReDel(GridRecordDataModel model) {
        try{
            String sql1="update "+ JfGridConfigModel.TABLENAME+"  set is_delete=?  where  list_id=? and index=? ";
            log.info("updateSql1:"+sql1);
            luckySheetJdbcTemplate.update(sql1,new Object[]{model.getIs_delete(),model.getList_id(),model.getIndex()});
            return true;
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 按ID 删除多个文档 （物理删除）
     *
     * @param ids
     * @return
     */
    @Override
    public String delDocuments(List<String> ids) {
        try{
            String id="";
            for (String str : ids) {
                id=id+str+",";
            }
            id=id.substring(0, id.length()-1);
            String delsql="DELETE from "+JfGridConfigModel.TABLENAME+" where id in ('+id+')";
            luckySheetJdbcTemplate.update(delsql);
            return "";
        }catch (Exception ex){
            log.error(ex.toString());
            return ex.toString();
        }
    }

    /**
     * 按list_id 删除记录 （物理删除）
     *
     * @param listIds
     */
    @Override
    public int[] delete(List<String> listIds) {
        if(listIds==null || listIds.size()==0){
            return new int[]{};
        }
        DataSource ds = luckySheetJdbcTemplate.getDataSource();
        BatchSqlUpdate bsu = new BatchSqlUpdate(ds, " delete  from "+JfGridConfigModel.TABLENAME +" where list_id = ? ");
        bsu.setBatchSize(4);
        bsu.setTypes(new int[]{Types.VARCHAR});
        for (String listId : listIds) {
            log.info(bsu.update(new Object[]{listId}) + "");
        }
        return bsu.flush();
    }
}
