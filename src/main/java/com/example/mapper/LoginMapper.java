package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface LoginMapper {


    @Select("select * from user where user_name = #{userName} and pass_word = #{passWord} ")
    Map<String, Object> login(@Param("userName") String userName, @Param("passWord") String passWord);

    @Insert("insert into `token` (userid, `systime`, `token`) VALUES (#{userId}, #{sysTime}, #{token})")
    boolean addToken(@Param("userId") String userId, @Param("token") String token, @Param("sysTime") String sysTime);
}
