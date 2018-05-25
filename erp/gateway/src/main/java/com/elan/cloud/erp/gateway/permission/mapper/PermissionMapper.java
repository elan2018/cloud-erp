package com.elan.cloud.erp.gateway.permission.mapper;

import com.elan.cloud.erp.gateway.permission.bean.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PermissionMapper {
    @Select("SELECT * from sys_permission ")
    public List<Permission> findAll();

    @Select("select p.*\n" +
            "        from sys_user u\n" +
            "        LEFT JOIN sys_user_role sru on u.id= sru.user_id\n" +
            "        LEFT JOIN sys_role r on sru.role_id=r.id\n" +
            "        LEFT JOIN sys_permission_role spr on spr.role_id=r.id\n" +
            "        LEFT JOIN sys_permission p on p.id =spr.permission_id\n" +
            "        where u.id=#{userId}")
    public List<Permission> findByUserId( int userId);
}
