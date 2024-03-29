package com.venson.aclservice.mapper;

import com.venson.aclservice.entity.AdminPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Mapper
public interface AdminPermissionMapper extends BaseMapper<AdminPermission> {


    List<String> selectPermissionValueByUserId(Long id);

    List<String> selectAllPermissionValue();

    List<AdminPermission> selectPermissionByUserId(Long userId);
}
