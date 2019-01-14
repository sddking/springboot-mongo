package com.test.greedemo.redisrepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.greedemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class UserRedis {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void AddUser(String key,Long time,User user){
        Gson gson=new Gson();
        redisTemplate.opsForValue().set(key,gson.toJson(user),time,TimeUnit.MINUTES);
    }

    public void AddListUser(String key,Long time,List<User> users){
        Gson gson=new Gson();
        redisTemplate.opsForValue().set(key,gson.toJson(users),time,TimeUnit.MINUTES);
    }
    public User getUser(String key){
        Gson gson=new Gson();
        User user=null;
        String userJson=redisTemplate.opsForValue().get(key);
        if(!StringUtils.isEmpty(userJson)){
            user=gson.fromJson(userJson,User.class);
        }
        return user;
    }
    public List<User> getList(String key){
        Gson gson=new Gson();
        List<User> users=null;
        String usersJson=redisTemplate.opsForValue().get(key);
        if(!StringUtils.isEmpty(usersJson)){
            users=gson.fromJson(usersJson,new TypeToken<List<User>>(){}.getType());
        }
        return  users;
    }

    public void delete(String key){
        redisTemplate.opsForValue().getOperations().delete(key);
    }
}
