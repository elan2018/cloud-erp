package com.elan.cloud.erp.gateway.permission.mapper;

import com.elan.cloud.erp.gateway.permission.bean.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PermissionMapper {
    @Select("SELECT * from sys.tb_permission ")
    public List<Permission> findAll();

    @Select("SELECT \n" +
            "            d.name,d.value as url,d.id,d.parent_id \n" +
            "            FROM \n" +
            "            sys.tb_operator AS a \n" +
            "            LEFT JOIN sys.tb_r_operator_role AS b ON a.id = b.operator_id\n" +
            "            LEFT JOIN sys.tb_r_permission_role as C ON C .role_id = b.role_id \n" +
            "            LEFT JOIN sys.tb_permission as d ON C .permission_id = d.id \n" +
            "            WHERE 1=1\n" +
            //"            d.type = 'menu' \n" +
            "            AND a.id = #{userId}\n" +
            "            order by d.id")
    public List<Permission> findByUserId( int userId);
}
