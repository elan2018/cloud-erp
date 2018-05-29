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

    @Select("SELECT \n" +
            "            d.name,d.value as url,d.img_url as pic,d.id,d.parent_id as pid\n" +
            "            FROM \n" +
            "            sys.tb_operator AS a \n" +
            "            LEFT JOIN sys.tb_r_operator_role AS b ON a.id = b.operator_id\n" +
            "            LEFT JOIN sys.tb_r_permission_role as C ON C .role_id = b.role_id \n" +
            "            LEFT JOIN sys.tb_permission as d ON C .permission_id = d.id \n" +
            "            WHERE \n" +
            "            d.type = 'menu' \n" +
            "            AND a.id = #{userId}\n" +
            "            order by d.id")
    List<Map<String,Object>> getUserMenu(@Param("userId") int userId);
}
