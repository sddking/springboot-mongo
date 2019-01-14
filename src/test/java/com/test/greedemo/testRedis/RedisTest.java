package com.test.greedemo.testRedis;

import com.test.greedemo.model.Department;
import com.test.greedemo.model.Role;
import com.test.greedemo.model.User;
import com.test.greedemo.redisrepository.UserRedis;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RedisConfiguration.class,UserRedis.class})
public class RedisTest {
    private static Logger logger=LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    private  UserRedis userRedis;

    @Before
    public  void setUp(){
        Department department=new Department();
        department.setDepartmentname("开发部");
        Role role=new Role();
        role.setRolename("admin");
        User user=new User();
        user.setUsername("贾晨阳");
        user.setBirthdate(new Date());
        user.setDepartment(department);
        List<Role> roles=new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        userRedis.delete("UserByName:jcy");
        userRedis.AddUser("UserByName:jcy",10L,user);
    }

    @Test
    public void get(){
        User user=userRedis.getUser("UserByName:jcy");
        logger.info("===user===name{},department{},role{}"+user.getUsername()+user.getDepartment().getDepartmentname()+user.getRoles().get(0).getRolename());
    }
}
