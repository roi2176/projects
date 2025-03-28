package com.swjtu;

import com.swjtu.entity.DiscussPost;

import com.swjtu.entity.User;
import com.swjtu.mapper.DiscussPostMapper;
import com.swjtu.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MapperTests {
    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        List<DiscussPost> page = discussPostMapper.page(149, 0, 10);
        for(DiscussPost d : page){
            System.out.println(d);
        }
    }

    @Test
    void getUser(){
        String username = "roi";
        User user = userMapper.selectByName(username);
        System.out.println(user);
    }
}
