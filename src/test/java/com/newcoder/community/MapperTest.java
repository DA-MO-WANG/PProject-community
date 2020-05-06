package com.newcoder.community;

import com.newcoder.community.dao.LoginTicketMapper;
import com.newcoder.community.entity.LoginTicket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)//这个注解是一个运行器，意思是让测试在Spring环境下进行
@SpringBootTest//SpringBoot中的测试注解
@ContextConfiguration(classes = CommunityApplication.class)//作用是引入配置文件
public class MapperTest {
    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Test
    public void testInsert() {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(101);
        ticket.setTicket("45sfd5f5sad");
        ticket.setStatus(0);
        ticket.setExpired(new Date(System.currentTimeMillis()+1000*60*10));
        int i = loginTicketMapper.insertLoginTicket(ticket);
        System.out.println(i);
    }

    @Test
    public void testSelect() {
        loginTicketMapper.updateLoginTicket("45sfd5f5sad",1);
        LoginTicket ticket = loginTicketMapper.selectLoginTicket("45sfd5f5sad");
        System.out.println(ticket);

    }
}
