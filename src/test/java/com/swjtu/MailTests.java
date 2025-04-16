package com.swjtu;

import com.swjtu.util.MailClient;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@SpringBootTest
public class MailTests {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;


    @Test
    public void testTextMail(){
        mailClient.sendMail("1782039336@qq.com", "轻松通过大学生英语四级考试，提升英语能力！", "报考我们的课程，为你的英语四级考试打下坚实基础，提升职场竞争力，轻松通过考试！联系电话155****2176");
    }

    @Test
    public void testHtmlMail(){
        Context context = new Context();
        context.setVariable("username", "Sundy");
        String content = templateEngine.process("/mail/demo", context);
        mailClient.sendMail("1142281601@qq.com", "HTML", content);

    }
}
