package com.elan.cloud.erp.api.base.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface UserMapper {

    @Select("SELECT " +
            "d.name,d.url,'icon0.png' as pic,d.id,d.pid " +
            "FROM " +
            "sys_user AS a " +
            "LEFT JOIN sys_user_role AS b ON a.id = b.user_id " +
            "LEFT JOIN sys_permission_role as C ON C .role_id = b.role_id " +
            "LEFT JOIN sys_permission as d ON C .permission_id = d.id " +
            "WHERE " +
            "d.req_type = 'menu' " +
            "AND a.id = #{userId} " +
            "order by d.id")
    List<Map<String,Object>> getUserMenu(@Param("userId") int userId);
}
