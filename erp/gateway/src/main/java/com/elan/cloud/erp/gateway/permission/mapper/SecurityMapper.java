package com.elan.cloud.erp.gateway.permission.mapper;

import com.elan.cloud.erp.gateway.permission.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SecurityMapper {

    @Select("select id,user_name as username,password_hash as password from sys.tb_operator where user_name=#{username}")
    User findByUsername(@Param("username") String username);
}
