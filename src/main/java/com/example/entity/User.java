package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value= "User", description="用户")
public class User implements Serializable {

    @ApiModelProperty(value = "用户id")
    private Long id;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "年龄")
    private Integer age;
    @ApiModelProperty(value = "账号")
    @TableField(value = "user_name")
    private String userName;
    @ApiModelProperty(value = "密码")
    @TableField(value = "pass_word")
    private String passWord;
}
