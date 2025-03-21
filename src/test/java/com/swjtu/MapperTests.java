package com.swjtu;

import com.swjtu.entity.DiscussPost;

import com.swjtu.mapper.DiscussPostMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MapperTests {
    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Test
    void contextLoads() {
        List<DiscussPost> page = discussPostMapper.page(149, 0, 10);
        for(DiscussPost d : page){
            System.out.println(d);
        }
    }
}
