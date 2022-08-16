package com.venson.aclservice.mapper;

import com.venson.aclservice.entity.Permission;
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
public interface PermissionMapper extends BaseMapper<Permission> {


    List<String> selectPermissionValueByUserId(Long id);

    List<String> selectAllPermissionValue();

    List<Permission> selectPermissionByUserId(Long userId);
}
