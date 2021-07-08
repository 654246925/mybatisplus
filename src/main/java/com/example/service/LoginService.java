package com.example.service;

import com.example.Exception.BusinessException;
import com.example.mapper.LoginMapper;
import com.example.tool.TokenTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Transactional(rollbackFor = Exception.class)// 异常回滚
    public String login(String userName, String passWord) {
        Map<String, Object> map = loginMapper.login(userName, passWord);
        if (map == null || map.isEmpty()) {
            throw new BusinessException("账号或密码错误");
        }
        String userId = String.valueOf(map.get("id"));
        String sysTime = String.valueOf(System.currentTimeMillis());
        StringBuffer info = new StringBuffer().append(userId).append("-")
                .append(userName).append("-").append(sysTime);
        String token = TokenTools.encode(info.toString());
        boolean add = loginMapper.addToken(userId, token, sysTime);
        if (!add) {
            throw new BusinessException("登录失败");
        }
        return token;
    }
}
