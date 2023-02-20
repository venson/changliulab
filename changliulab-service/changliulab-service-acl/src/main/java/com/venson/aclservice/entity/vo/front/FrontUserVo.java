package com.venson.aclservice.entity.vo.front;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class FrontUserVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 9384592345L;

    private Long id;
    private String email;
    private String username;


}
