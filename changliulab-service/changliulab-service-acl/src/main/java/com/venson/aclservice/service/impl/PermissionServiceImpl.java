package com.venson.aclservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.venson.aclservice.entity.Permission;
import com.venson.aclservice.entity.RolePermission;
import com.venson.aclservice.entity.AclUser;
import com.venson.aclservice.helper.MenuHelper;
import com.venson.aclservice.helper.PermissionHelper;
import com.venson.aclservice.mapper.PermissionMapper;
import com.venson.aclservice.service.PermissionService;
import com.venson.aclservice.service.RolePermissionService;
import com.venson.aclservice.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
@Slf4j
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    private final RolePermissionService rolePermissionService;

    private final UserService userService;

    public PermissionServiceImpl(RolePermissionService rolePermissionService, UserService userService) {
        this.rolePermissionService = rolePermissionService;
        this.userService = userService;
    }

    //获取全部菜单
    @Override
    public List<Permission> queryAllMenu() {

        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> permissionList = baseMapper.selectList(wrapper);

        return build(permissionList);

    }

    //根据角色获取菜单
    @Override
    public List<Permission> selectAllMenu(Long roleId) {
        List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));

        //根据角色id获取角色权限
        List<RolePermission> rolePermissionList = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id",roleId));
        //转换给角色id与角色权限对应Map对象
//        List<String> permissionIdList = rolePermissionList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
//        allPermissionList.forEach(permission -> {
//            if(permissionIdList.contains(permission.getId())) {
//                permission.setSelect(true);
//            } else {
//                permission.setSelect(false);
//            }
//        });
        for (Permission permission : allPermissionList) {
            for (RolePermission rolePermission : rolePermissionList) {
                if (rolePermission.getPermissionId().equals(permission.getId())) {
                    permission.setSelect(true);
                }
            }
        }


        return build(allPermissionList);
    }

    //给角色分配权限
    @Override
    public void saveRolePermissionRelationShip(Long roleId,Long[] permissionIds) {

        rolePermissionService.remove(new QueryWrapper<RolePermission>().eq("role_id", roleId));



        List<RolePermission> rolePermissionList = new ArrayList<>();
        for(Long permissionId : permissionIds) {
            if(ObjectUtils.isEmpty(permissionId)) continue;

            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissionList.add(rolePermission);
        }
        rolePermissionService.saveBatch(rolePermissionList);
    }

    //递归删除菜单
    @Override
    public void removeChildById(Long id) {
        List<Long> idList = new ArrayList<>();
        this.selectChildListById(id, idList);

        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }

    //根据用户id获取用户菜单
    @Override
    public List<String> selectPermissionValueByUserId(Long id) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();


        List<String> selectPermissionValueList = null;
        List<String> test = null;
        if(this.isSysAdmin(id)) {
            //如果是系统管理员，获取所有权限
//            wrapper.select("permission_value");
//            wrapper.eq("type", 2);
//            wrapper.eq("is_deleted",0);
            return baseMapper.selectAllPermissionValue();
//            log.info(selectPermissionValueList.toString());
        } else {
//            wrapper.select("permission_value");
//            wrapper.eq("type", 2);
//            wrapper.eq("is_deleted",0);
//            wrapper.eq("id", id);
            return baseMapper.selectPermissionValueByUserId(id);
        }
//        List<Permission> permissionList = baseMapper.selectList(wrapper);
//        for (Permission permission :
//                permissionList) {
//            String permissionValue = permission.getPermissionValue();
//            System.out.println(permissionValue);
//            selectPermissionValueList.add();
//            System.out.println(selectPermissionValueList.toString());
//        }
//        for (int i = 0; i < permissionList.size(); i++) {
//            String permissionValue = permissionList.get(i).getPermissionValue();
//            test.add(Integer.toString(i));
//            System.out.println(test.toString());
//            selectPermissionValueList.add(permissionValue);
//
//
//        }
//        log.info(selectPermissionValueList.toString());
//        return selectPermissionValueList;
    }

    @Override
    public List<JSONObject> selectPermissionByUserId(Long userId) {
        List<Permission> selectPermissionList;
        if(this.isSysAdmin(userId)) {
            //如果是超级管理员，获取所有菜单
            selectPermissionList = baseMapper.selectList(null);
        } else {
            selectPermissionList = baseMapper.selectPermissionByUserId(userId);
        }
        List<Permission> permissionList = PermissionHelper.build(selectPermissionList);
//        List<Permission> permissionList = PermissionHelper.build(finalPermissionList);
        return MenuHelper.build(permissionList);
    }

    private Set<Permission> getParentPermission(Permission permission, Map<Long, Permission> allPermissionMap){
        Set<Permission> set = new HashSet<>();

        set.add(permission);
        Permission parentPermission = allPermissionMap.get(permission.getPid());
        if(parentPermission ==null){
            return set;
        }else{
            set.add(parentPermission);
        }
        if(parentPermission.getId() ==1){
            return set;
        }else{
            set.addAll(getParentPermission(parentPermission,allPermissionMap));
        }
        return set;
    }

    /**
     * 判断用户是否系统管理员
     */
    private boolean isSysAdmin(Long userId) {
        AclUser user = userService.getById(userId);

        return null != user && "admin".equals(user.getUsername());
    }

    /**
     *	递归获取子节点
     * @param id id
     * @param idList idlist
     */
    private void selectChildListById(Long id, List<Long> idList) {
        List<Permission> childList = baseMapper.selectList(new QueryWrapper<Permission>().eq("pid", id).select("id"));
        childList.forEach(item -> {
            idList.add(item.getId());
            this.selectChildListById(item.getId(), idList);
        });
    }

    /**
     * 使用递归方法建菜单
     * @param treeNodes treenode
     * @return List<Permission>
     */
    private static List<Permission> build(List<Permission> treeNodes) {
        List<Permission> trees = new ArrayList<>();
        for (Permission treeNode : treeNodes) {
            if (0 ==treeNode.getPid()) {
                treeNode.setLevel(1);
                trees.add(findChildren(treeNode,treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param treeNodes tree node
     * @return Permission statue
     */
    private static Permission findChildren(Permission treeNode,List<Permission> treeNodes) {
        treeNode.setChildren(new ArrayList<>());

        for (Permission it : treeNodes) {
            if(treeNode.getId().equals(it.getPid())) {
                int level = treeNode.getLevel() + 1;
                it.setLevel(level);
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return treeNode;
    }


    //========================递归查询所有菜单================================================
    //获取全部菜单
    @Override
    public List<Permission> queryAllMenuLab() {
        //1 查询菜单表所有数据
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> permissionList = baseMapper.selectList(wrapper);
        //2 把查询所有菜单list集合按照要求进行封装
        return buildPermission(permissionList);
    }

    //把返回所有菜单list集合进行封装的方法
    public static List<Permission> buildPermission(List<Permission> permissionList) {

        //创建list集合，用于数据最终封装
        List<Permission> finalNode = new ArrayList<>();
        //把所有菜单list集合遍历，得到顶层菜单 pid=0菜单，设置level是1
        for(Permission permissionNode : permissionList) {
            //得到顶层菜单 pid=0菜单
            if(permissionNode.getPid()==0) {
                //设置顶层菜单的level是1
                permissionNode.setLevel(1);
                //根据顶层菜单，向里面进行查询子菜单，封装到finalNode里面
                finalNode.add(selectChildren(permissionNode,permissionList));
            }
        }
        return finalNode;
    }

    private static Permission selectChildren(Permission permissionNode, List<Permission> permissionList) {
        //1 因为向一层菜单里面放二层菜单，二层里面还要放三层，把对象初始化
        permissionNode.setChildren(new ArrayList<>());

        //2 遍历所有菜单list集合，进行判断比较，比较id和pid值是否相同
        for(Permission it : permissionList) {
            //判断 id和pid值是否相同
            if(permissionNode.getId().equals(it.getPid())) {
                //把父菜单的level值+1
                int level = permissionNode.getLevel()+1;
                it.setLevel(level);
                //如果children为空，进行初始化操作
                if(permissionNode.getChildren() == null) {
                    permissionNode.setChildren(new ArrayList<>());
                }
                //把查询出来的子菜单放到父菜单里面
                permissionNode.getChildren().add(selectChildren(it,permissionList));
            }
        }
        return permissionNode;
    }

    //============递归删除菜单==================================
    @Override
    public void removeChildByIdLab(Long id) {
        //1 创建list集合，用于封装所有删除菜单id值
        List<Long> idList = new ArrayList<>();
        //2 向idList集合设置删除菜单id
        this.selectPermissionChildById(id,idList);
        //把当前id封装到list里面
        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }

    //2 根据当前菜单id，查询菜单里面子菜单id，封装到list集合
    private void selectPermissionChildById(Long id, List<Long> idList) {
        //查询菜单里面子菜单id
        QueryWrapper<Permission>  wrapper = new QueryWrapper<>();
        wrapper.eq("pid",id);
        wrapper.select("id");
        List<Permission> childIdList = baseMapper.selectList(wrapper);
        //把childIdList里面菜单id值获取出来，封装idList里面，做递归查询
        childIdList.forEach(item -> {
            //封装idList里面
            idList.add(item.getId());
            //递归查询
            this.selectPermissionChildById(item.getId(),idList);
        });
    }

    //=========================给角色分配菜单=======================
    @Override
    public void saveRolePermissionRelationShipLab(Long roleId,Long[] permissionIds) {
        //roleId角色id
        //permissionId菜单id 数组形式
        //1 创建list集合，用于封装添加数据
        List<RolePermission> rolePermissionList = new ArrayList<>();
        //遍历所有菜单数组
        for(Long perId : permissionIds) {
            //RolePermission对象
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(perId);
            //封装到list集合
            rolePermissionList.add(rolePermission);
        }
        //添加到角色菜单关系表
        LambdaQueryWrapper<RolePermission> wrapper = new QueryWrapper<RolePermission>().lambda();
        wrapper.eq(RolePermission::getRoleId, roleId);
        rolePermissionService.remove(wrapper);
        rolePermissionService.saveBatch(rolePermissionList);
    }
}
