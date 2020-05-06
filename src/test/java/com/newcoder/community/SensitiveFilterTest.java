package com.newcoder.community;

import com.newcoder.community.util.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)//这个注解是一个运行器，意思是让测试在Spring环境下进行
@SpringBootTest//SpringBoot中的测试注解
@ContextConfiguration(classes = CommunityApplication.class)//作用是引入配置文件
public class SensitiveFilterTest {
    @Autowired
    public SensitiveFilter sensitiveFilter;
    @Test
    public void filterText() {
        String text = "➕习＋近平里出去开房老师的";
        String newText = sensitiveFilter.filter(text);
        System.out.println(newText);
    }

}
