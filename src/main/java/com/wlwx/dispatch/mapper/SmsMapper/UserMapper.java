package com.wlwx.dispatch.mapper.SmsMapper;

import com.wlwx.dispatch.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    /**
     * 获取用户凭证
     * @param username 账号
     * @return
     */
    User getUser( @Param("username") String username);
}
