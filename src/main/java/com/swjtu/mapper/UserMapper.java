package com.swjtu.mapper;

import com.swjtu.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findUserById(Integer id);
}

