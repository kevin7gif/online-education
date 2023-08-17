package com.kevin.acl.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kevin.acl.entity.Permission;
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


    List<String> selectPermissionValueByUserId(String id);

    List<String> selectAllPermissionValue();

    List<Permission> selectPermissionByUserId(String userId);
}
