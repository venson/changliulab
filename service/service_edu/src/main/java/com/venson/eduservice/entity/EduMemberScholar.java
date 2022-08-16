package com.venson.eduservice.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author venson
 * @since 2022-06-20
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("edu_member_scholar")
public class EduMemberScholar implements Serializable {
    public EduMemberScholar(Long id, String scholarId){
        this.id = id;
        this.scholarId= scholarId;
    }

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String scholarId;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
