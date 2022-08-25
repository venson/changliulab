package com.venson.sheetservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.venson.sheetservice.db.server.JfGridFileGetService;
import com.venson.sheetservice.entity.LuckySheetGridModel;
import com.venson.sheetservice.entity.enummodel.OperationTypeEnum;
import com.venson.sheetservice.utils.JsonUtil;
import com.venson.sheetservice.utils.Pako_GzipUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *  图表格页面调用
 * @author Administrator
 */
@Slf4j
@RestController
@RequestMapping(value = {"/api"})
public class JfGridFileController {
    /*
        /load       加载默认表格   （）
        /loadsheet  加载指定表格   （）

        打开表格
        /tu/api?id=listid
    */


    @Autowired
    private JfGridFileGetService jfGridFileGetService;

    /**
     * 默认加载表格 分块
     * @param request
     * @param response
     * @param gridKey
     * @return
     */
    @PostMapping("/load")
    public String load(HttpServletRequest request, HttpServletResponse response, @RequestParam(defaultValue = "") String gridKey) {
        //告诉浏览器，当前发送的是gzip格式的内容
        response.setHeader("Content-Encoding", "gzip");
        response.setContentType("text/html");
        String resultStr="";
        if(gridKey.trim().length()!=0){
            try {
                String _checkStr=check(request,gridKey.toString(),null, OperationTypeEnum.Read);
                if(_checkStr.length()>0){
                    return null;
                }
                List<JSONObject> dbObject=null;
                dbObject=jfGridFileGetService.getDefaultByGridKey(gridKey);
                if(dbObject!=null){
                    delErrorKey(dbObject);
                    resultStr= JsonUtil.toJson(dbObject);
                }
            } catch (Exception e) {
                log.error(gridKey+e.getMessage());
            }


        }
        log.info("load");
        try {
            byte dest[]= Pako_GzipUtils.compress2(resultStr);
            OutputStream out=response.getOutputStream();
            out.write(dest);
            out.close();
            out.flush();
        } catch (Exception e) {
            log.error("load---ioerror:"+e);
        }
        return null;
    }

    /**
     * 加载指定表格
     * @param map
     * @param request
     * @param response
     * @param gridKey
     * @param index
     * @return
     */
//    @ApiOperation(value = "加载指定表格",notes = "加载指定表格")
    @PostMapping("/loadsheet")
    public byte[] loadsheet(Map map, HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(defaultValue = "") String gridKey,
                            @RequestParam(defaultValue = "") String[] index) {
        log.info("loadsheet--gridKey:"+gridKey+" index:"+ Arrays.toString(index));
        ////告诉浏览器，当前发送的是gzip格式的内容
        response.setHeader("Content-Encoding", "gzip");
        response.setContentType("text/html");
        String resultStr="";
        if(gridKey.trim().length()!=0){
            try {
                String _id=gridKey;
                String _checkStr=check(request,_id,null,OperationTypeEnum.Read);
                log.info(_checkStr);
                if(_checkStr.length()>0){
                    return null;
                }
                LinkedHashMap dbObject=null;
                dbObject=jfGridFileGetService.getByGridKeys(_id, Arrays.asList(index));
                log.info("loadsheet--dbObject--");
                if(dbObject!=null){
                    resultStr=JsonUtil.toJson(dbObject);
                }
            } catch (Exception e) {
                log.info(gridKey+e.getMessage());
            }
        }

        byte dest[]= Pako_GzipUtils.compress2(resultStr);
        log.info("loadsheet");
        OutputStream out=null;
        try {
            out = response.getOutputStream();
            out.write(dest);
            out.close();
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error("loadsheet---ioerror:"+e);
        }catch (Exception ex){
            log.error("loadsheet---error:"+ex);
        }

        return null;
    }

    /**
     * 文档权限的检查
     * @param request
     * @param _id
     * @param curModel
     * @param operationTypeEnum
     * @return
     */
    private String check(HttpServletRequest request, String _id, LuckySheetGridModel curModel, OperationTypeEnum operationTypeEnum){
        //校验代码
        return "";
    }



    /**
     * 数据返回时，去掉数组变字符串，引发错误的key
     * 删除会发生错误的对象
     */
    private void delErrorKey(List<JSONObject> dbObject){
        if(dbObject!=null){
            for(JSONObject obj :dbObject){
                delErrorKeyByCheck(obj,"calcChain");
                delErrorKeyByCheck(obj,"luckysheet_alternateformat_save");
                delErrorKeyByCheck(obj,"luckysheet_conditionformat_save");
            }
        }
    }
    private void delErrorKeyByCheck(JSONObject obj,String key){
        if(obj.containsKey(key) && obj.get(key) instanceof String){
            obj.remove(key);
        }
    }
}
