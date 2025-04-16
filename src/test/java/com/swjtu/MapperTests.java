package com.swjtu;

import com.swjtu.entity.DiscussPost;

import com.swjtu.entity.Message;
import com.swjtu.entity.User;
import com.swjtu.mapper.DiscussPostMapper;
import com.swjtu.mapper.MessageMapper;
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

    @Autowired
    private MessageMapper messageMapper;

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

    @Test
    public void testSelectLetters(){
        List<Message> messages = messageMapper.selectConversations(111, 0, 20);
        for (Message message : messages) {
            System.out.println(message);
        }

        int count = messageMapper.selectConversationCount(111);
        System.out.println(count);

        List<Message> messages1 = messageMapper.selectLetters("111_112", 0, 10);
        int i = messageMapper.selectLetterCount("111_112");
        for (Message message : messages1) {
            System.out.println(message);
        }

        count = messageMapper.selectLetterCount("111_112");
        System.out.println(count);

        int count1 = messageMapper.selectLetterUnreadCount(131, "111_131");
        System.out.println(count1);
    }
}
