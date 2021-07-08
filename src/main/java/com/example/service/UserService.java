package com.example.service;

import com.example.MybatisplusApplication;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.tool.PageBen;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Cacheable(value = "expiretimeDefault", keyGenerator = "commonKey")
//    @Cacheable(value = "aaa")
    public PageBen<User> findAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.findAll();
        PageInfo<User> pageInfo = new PageInfo<>(list);
        PageBen<User> pageBen = new PageBen(pageInfo);
        return pageBen;
    }

    public User findById(Long id) {
        return userMapper.findById(id);
    }

    public void save(User user) {
        userMapper.save(user);
    }

    public void update(User user) {
        userMapper.update(user);
    }

    public void deleteById(long id) {
        userMapper.deleteById(id);
    }

}
