package com.swjtu;

import com.swjtu.util.SensitiveFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FilterTest {
    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void filterTest(){
        String s = "♫开♫票♫哈哈";
        System.out.println(sensitiveFilter.filter(s));

        String s1 = "这里可以开♫票";
        System.out.println(sensitiveFilter.filter(s1));
    }
}
