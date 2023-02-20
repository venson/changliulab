package com.venson.servicebase.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomizedException extends RuntimeException {
    private Integer code;
    private String msg;

    @Override
    public String getMessage() {
        return msg;
    }
}
