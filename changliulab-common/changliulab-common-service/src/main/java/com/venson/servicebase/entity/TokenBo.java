package com.venson.servicebase.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenBo implements Serializable {
    @Serial
    private static final long serialVersionUID = 239239L;
    private String token;
}
