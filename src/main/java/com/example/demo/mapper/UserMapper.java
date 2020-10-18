package com.example.demo.mapper;

import com.example.demo.domain.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert({
            "insert into user(username,password,salt)",
            "value(#{username},#{password},#{salt})"
    })
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insertUser(User user);

    @Select({
            "select id,username,password,salt",
            "from user where username=#{username}"
    })
    User selectByUsername(String username);

}
