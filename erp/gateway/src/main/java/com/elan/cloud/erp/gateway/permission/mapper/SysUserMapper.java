package com.elan.cloud.erp.gateway.permission.mapper;

import com.elan.cloud.erp.gateway.permission.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SysUserMapper {

    @Select("select id,username,password from sys_user where username=#{username}")
    User findByUsername(@Param("username") String username);

}
