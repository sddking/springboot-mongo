package com.test.greedemo.testjpa;

import com.test.greedemo.jparepository.DepartmentRepository;
import com.test.greedemo.jparepository.RoleRepository;
import com.test.greedemo.jparepository.UserReporsitory;
import com.test.greedemo.model.Department;
import com.test.greedemo.model.Role;
import com.test.greedemo.model.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfiguration.class})
public class MysqlTest {

    private static Logger logger=LoggerFactory.getLogger(MysqlTest.class);
    @Autowired
    UserReporsitory userReporsitory;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    RoleRepository roleRepository;

    @Before
    public void initData(){
        userReporsitory.deleteAll();
        departmentRepository.deleteAll();
        roleRepository.deleteAll();

        Department department=new Department();
        department.setDepartmentname("计算机中心");
        departmentRepository.save(department);
        Assert.notNull(department.getDepartmentid());

        Role role=new Role();
        role.setRolename("admin");
        roleRepository.save(role);
        Assert.notNull(role.getRoleid());

        User user=new User();
        user.setUsername("欧俊廷");
        user.setBirthdate(new Date());
        user.setDepartment(department);
        List<Role> roles=roleRepository.findAll();
        Assert.notNull(roles);
        user.setRoles(roles);

        userReporsitory.save(user);
        Assert.notNull(user.getUserid());
    }

    @Test
    public void findPage(){
        Pageable pageable=new PageRequest(0,10,new Sort(Sort.Direction.ASC,"userid"));

        Page<User> page=userReporsitory.findAll(pageable);
        Assert.notNull(page);
        for(User user:page.getContent()){
            logger.info("====user=====username,department,role"+user.getUsername()+user.getDepartment().getDepartmentname()+user.getRoles().get(0).getRolename());
        }
    }
}
