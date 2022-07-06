package com.venson;

import com.venson.aclservice.service.PermissionService;
import com.venson.aclservice.service.impl.PermissionServiceImpl;
import com.venson.aclservice.service.impl.RolePermissionServiceImpl;
import com.venson.aclservice.service.impl.UserDetailsServiceImpl;
import com.venson.aclservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;

public class MapperTest {
    @Test
    public void MapperTest1(){
        PermissionService permissionService = new PermissionServiceImpl(new RolePermissionServiceImpl(), new UserServiceImpl());
    }
}
