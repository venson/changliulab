package com.venson.aclservice.entity.dto;

import lombok.Data;

@Data
public class passwordDTO {
    private String oldPassword;
    private String newPassword;
}
