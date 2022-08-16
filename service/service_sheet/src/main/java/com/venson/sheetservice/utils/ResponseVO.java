package com.venson.sheetservice.utils;


import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 返回对象
 * @author Administrator
 */
@Data
//@ApiModel(value = "请求返回类",description = "请求响应参数")
public class ResponseVO implements Serializable {

    private static final long serialVersionUID = 1l;


    /**
     * 消息状态
     */
    @NotNull
//    @ApiModelProperty(value = "status",example = StringUtils.EMPTY)
    private String status = StringUtils.EMPTY;

    /**
     * 交互结果消息描述
     */
    @NotNull
//    @ApiModelProperty(value = "msg",example = StringUtils.EMPTY)
    private String msg = StringUtils.EMPTY;

    /**
     * 消息体
     */
    @NotNull
//    @ApiModelProperty(value = "data",example = "{}")
    private Object data;

    /**
     * 消息代码
     */
    @NotNull
//    @ApiModelProperty(value = "code",example = StringUtils.EMPTY)
    private String code = StringUtils.EMPTY;

    /**
     * 成功默认消息
     * @return
     */
    private ResponseVO() {
        this.status = SysConstant.STATUS.Valid;
        this.msg = SysConstant.MSG.SUCCESS;
        this.code = SysConstant.SYS_CODE.STATUS_SUCCESS;
        this.data="{}";
    }

    /**
     * 成功默认消息
     * @return
     */
    public static ResponseVO successInstance() {
        return new ResponseVO();
    }

    /**
     * 成功-返回数据
     * @param data
     * @return
     */
    public static ResponseVO successInstance(Object data) {
        if(data==null){
            data="";
        }
        ResponseVO res = successInstance();
        res.setData(data);
        return res;
    }


    /**
     * 失败错误消息
     * @param msg
     * @return
     */
    public static ResponseVO errorInstance(String msg) {
        ResponseVO res = successInstance();

        res.setStatus(SysConstant.STATUS.Invalid);
        res.setCode(SysConstant.SYS_CODE.STATUS_ERROR);
        res.setMsg(StringUtils.isBlank(msg) ? SysConstant.MSG.ERROR : msg);

        return res;
    }



}
