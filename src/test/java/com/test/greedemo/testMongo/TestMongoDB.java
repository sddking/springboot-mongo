package com.test.greedemo.testMongo;

import com.test.greedemo.mongo.model.User;
import com.test.greedemo.mongo.repository.UserRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestMongoDBConfig.class})
@FixMethodOrder
public class TestMongoDB {
    private static Logger logger = LoggerFactory.getLogger(TestMongoDB.class);

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    UserRepository userRepository;

    @Before
    public void setup(){
        Set<String> roles = new HashSet<>();
        roles.add("manage");
        User user = new User("1","user","12345678","name","email@com.cn",new Date(), roles);
        userRepository.save(user);
    }

    @Test
    public void findAll(){
        List<User> users = userRepository.findAll();
        Assert.notNull(users);
        for(User user : users){
            logger.info("===user=== userid:{}, username:{}, pass:{}, registrationDate:{}",
                    user.getUserId(), user.getName(), user.getPassword(), user.getRegistrationDate());
        }
    }
}
