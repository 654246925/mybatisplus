package com.example.controller.auth;

import com.example.Aop.TokenAnnotation;
import com.example.entity.User;
import com.example.result.Result;
import com.example.result.ResultCode;
import com.example.service.UserService;
import com.example.tool.PageBen;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "用户")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户列表")
    @PostMapping("/findAll")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum",value="页数",dataType="Integer", paramType = "query", required = true),
            @ApiImplicitParam(name="pageSize",value="条数",dataType="Integer", paramType = "query", required = true)
    })
    @TokenAnnotation
    public Result<User> findAll(@RequestParam Integer pageNum,
                          @RequestParam Integer pageSize){
        PageBen pageBen = userService.findAll(pageNum, pageSize);
        return Result.genSuccessResult(pageBen);
    }

    @ApiOperation(value = "单个用户")
    @PostMapping("/findById")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="用户id",dataType="Long", paramType = "query", required = true)
    })
    @TokenAnnotation
    public Result<User> findById(@RequestParam Long id){
        User user = userService.findById(id);
        return Result.genSuccessResult(user);
    }

    @ApiOperation(value = "新增用户")
    @PostMapping("/save")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="用户id",dataType="Long", paramType = "query", required = false),
            @ApiImplicitParam(name="name",value="名称",dataType="String", paramType = "query", required = true),
            @ApiImplicitParam(name="age",value="年龄",dataType="Long", paramType = "query", required = true)
    })
    @TokenAnnotation
    public Result<ResultCode> save(@ModelAttribute @Valid User user){
        userService.save(user);
        return Result.genSuccessResult();
    }

    @ApiOperation(value = "修改用户")
    @PostMapping("/update")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="用户id",dataType="Long", paramType = "query", required = true),
            @ApiImplicitParam(name="name",value="名称",dataType="String", paramType = "query", required = true),
            @ApiImplicitParam(name="age",value="年龄",dataType="Long", paramType = "query", required = true)
    })
    @TokenAnnotation
    public Result<ResultCode> update(@ModelAttribute @Valid User user){
        userService.update(user);
        return Result.genSuccessResult();
    }

    @ApiOperation(value = "删除单个用户")
    @PostMapping("/deleteById")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="用户id",dataType="Long", paramType = "query", required = true)
    })
    @TokenAnnotation
    public Result<ResultCode> deleteById(@RequestParam Long id){
        userService.deleteById(id);
        return Result.genSuccessResult();
    }

}
