package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    public List<User> findAll();
    public User findById(@Param("id") Long id);
    public void save(@Param("user") User user);
    public void update(@Param("user") User user);
    public void deleteById(@Param("id") long id);
}
