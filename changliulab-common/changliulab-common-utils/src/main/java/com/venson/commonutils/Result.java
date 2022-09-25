package com.venson.commonutils;

import lombok.Data;
import net.sf.jsqlparser.expression.operators.relational.OldOracleJoinBinaryExpression;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class Result implements Serializable {
    @Serial
    private static final long serialVersionUID = 1123124421L;
    private Boolean success;
    private Integer code;
    private String message;
    private Map<String,Object> data = new HashMap<>();

    private Result(){
    }
    private Result(Boolean success, Integer code, String msg, Object data){
        this.success = success;
        this.code = code;
        this.message = msg;
        this.data.put("data",data);
    }


    public static  Result success(Object data,String msg){
        return new Result(true, ResultCode.SUCCESS.getValue(), msg, data);
    }
    public static  Result success(String label, Object data){
        return new Result(true, ResultCode.SUCCESS.getValue(), ResultCode.SUCCESS.getDesc(),null).data(label,data);

    }
    public static  Result success(Object data){
        return new Result(true, ResultCode.SUCCESS.getValue(), ResultCode.SUCCESS.getDesc(), data);
    }
    public static  Result success(){
        return new Result(true, ResultCode.SUCCESS.getValue(), ResultCode.SUCCESS.getDesc(), null);
    }
    public static  Result error(String msg, Object data){
        return new Result(true, ResultCode.ERROR.getValue(), msg, data);
    }
    public static  Result error(Object data){
        return new Result(true, ResultCode.ERROR.getValue(), ResultCode.ERROR.getDesc(),data);
    }
    public static  Result error(){
        return new Result(true, ResultCode.ERROR.getValue(), ResultCode.ERROR.getDesc(), null);
    }
    public static  Result otherLogin(){
        return new Result(false, ResultCode.OTHER_LOGIN.getValue(), ResultCode.OTHER_LOGIN.getDesc(), null);
    }
    public static  Result tokenExpire(){
        return new Result(false, ResultCode.TOKEN_EXPIRE.getValue(), ResultCode.TOKEN_EXPIRE.getDesc(), null);
    }
    public static  Result unAuthorized(){
        return new Result(false, ResultCode.UNAUTHORIZED.getValue(), ResultCode.UNAUTHORIZED.getDesc(), null);
    }
    public static  Result illegalToken(){
        return new Result(false, ResultCode.ILLEGAL_TOKEN.getValue(), ResultCode.ILLEGAL_TOKEN.getDesc(), null);
    }




    public Result success(Boolean success){
        this.setSuccess(success);
        return this;
    }
    public Result data(String label,Object data){
        this.getData().put(label,data);
        return this;
    }
    public Result data(Object data){
        this.getData().put("item",data);
        return this;
    }
    public Result data(Map<String,Object> map){
        this.getData().putAll(map);
        return this;
    }
    public Result message(String message){
        this.setMessage(message);
        return this;
    }
    public Result code(Integer code){
        this.setCode(code);
        return this;
    }
}
