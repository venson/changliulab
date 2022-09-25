package com.venson.aclservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.aclservice.entity.FrontPermission;
import com.venson.aclservice.mapper.FrontPermissionMapper;
import com.venson.aclservice.service.FrontPermissionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FrontPermissionServiceImpl extends ServiceImpl<FrontPermissionMapper, FrontPermission> implements FrontPermissionService {
    @Override
    public List<String> selectPermissionValueByUserId(Long id) {
        List<String> empty = new ArrayList<>();
        empty.add("user");
        // TODO fullfill detail permission
        return empty;
    }
}
