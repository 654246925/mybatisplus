package com.example.controller.pub;

import com.example.result.Result;
import com.example.service.LoginService;
import com.example.service.UserService;
import com.example.tool.MD5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "登录")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;


    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userName",value="账号",dataType="String", paramType = "query", required = true),
            @ApiImplicitParam(name="passWord",value="密码",dataType="String", paramType = "query", required = true)
    })
    public Result login(@RequestParam String userName,
                        @RequestParam String passWord) {
        // MD5.getMD5(pwd.getBytes())
        String token = loginService.login(userName, passWord);
        return Result.genSuccessResult(token);
    }


}
