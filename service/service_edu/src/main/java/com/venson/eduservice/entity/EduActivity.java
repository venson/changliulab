package com.venson.eduservice.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author venson
 * @since 2022-07-04
 */
@Getter
@Setter
@TableName("edu_activity")
public class EduActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String title;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    private LocalDate activityDate;

    private String authorMemberId;

    private String authorMemberName;

    private Integer lastModifiedMemberId;

    private Boolean isPublished;

    private Boolean isModified;

    //publishRequest 0- ,1 request for publish, 2 request rejected
    private Integer publishRequest;

    @Version
    private Long version;


}
