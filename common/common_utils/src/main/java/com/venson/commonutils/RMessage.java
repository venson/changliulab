package com.venson.commonutils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RMessage {
    private Boolean success;
    private Integer code;
    private String message;
    private Map<String, Object> data = new HashMap<>();

    private RMessage(){}


    public static RMessage ok(){
        RMessage rMessage = new RMessage();
        rMessage.setSuccess(true);
        rMessage.setCode(ResultCode.SUCCESS.getValue());
        return rMessage;
    }

    public static RMessage error(){
        RMessage rMessage = new RMessage();
        rMessage.setSuccess(false);
        rMessage.setCode(ResultCode.ERROR.getValue());
        return rMessage;
    }



    public RMessage success(Boolean succ){
        this.setSuccess(succ);
        return this;
    }
    public RMessage message(String message){
        this.setMessage(message);
        return this;
    }
    public RMessage code(ResultCode code){
        this.setCode(code.getValue());
        return this;
    }
    public RMessage data(HashMap<String, Object> data){
        this.setData(data);
        return this;
    }
    public RMessage data(String key, Object obj){
        this.data.put(key, obj);
        return this;
    }
}
