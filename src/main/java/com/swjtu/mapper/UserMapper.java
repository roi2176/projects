package com.swjtu.mapper;

import com.swjtu.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    User selectById(Integer id);

    int insertUser(User user);

    @Select("SELECT * FROM user WHERE username = 'roi'")
    User selectByName(String username);

    User selectByEmail(String email);

    void updateStatus(int id, int status);

}

