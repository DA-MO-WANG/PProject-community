package com.newcoder.community;

import com.newcoder.community.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)//这个注解是一个运行器，意思是让测试在Spring环境下进行
@SpringBootTest//SpringBoot中的测试注解
@ContextConfiguration(classes = CommunityApplication.class)//作用是引入配置文件
public class MailTest {
    @Autowired
    private MailClient mailClient;
    @Test
    public void testTextMail() {
        mailClient.sendMail("2956850408@qq.com","Test","第一次发送邮件");
    }

}
