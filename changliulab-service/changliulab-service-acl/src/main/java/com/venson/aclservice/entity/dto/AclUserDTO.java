package com.venson.aclservice.entity.dto;

import com.venson.servicebase.valid.AddGroup;
import com.venson.servicebase.valid.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class AclUserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2341289238L;

    private Long id;

    @Pattern(regexp = "^(?=[a-zA-Z0-9._]{6,20}$)(?!.*[_.]{2})[^_.].*[^_.]$\n", groups = {AddGroup.class, UpdateGroup.class})
    private String username;

    @Email(groups = {AddGroup.class, UpdateGroup.class})
    private String email;

    private String nickName;

    private Boolean randomPassword;

    @NotNull(groups = {AddGroup.class, UpdateGroup.class})
    private List<Long> roleIds;
}
