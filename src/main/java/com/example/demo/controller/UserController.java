package com.example.demo.controller;

import com.example.demo.common.PasswordUtil;
import com.example.demo.domain.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.web.AjaxResult;
import com.example.demo.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(value = "注册登录")
public class UserController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMapper userMapper;

    @ApiOperation(value = "注册")
    @PostMapping(value = "/register")
    public AjaxResult register(@RequestBody User user){
        if(StringUtils.isBlank(user.getUsername())){
            return AjaxResult.error("用户名不能为空！");
        }
        if(StringUtils.isBlank(user.getPassword())){
            return AjaxResult.error("密码不能为空！");
        }

        User u = userMapper.selectByUsername(user.getUsername());
        if(u!=null){
            return AjaxResult.error("该用户名已被注册！");
        }

        String salt= PasswordUtil.generateUUID().substring(0,5);
        String passwordForDatabase=PasswordUtil.md5(user.getPassword()+salt);
        user.setSalt(salt);
        user.setPassword(passwordForDatabase);
        userMapper.insertUser(user);

        return AjaxResult.success("注册成功！");
    }

    @ApiOperation(value = "登录")
    @GetMapping(value = "/login")
    public AjaxResult login(@RequestParam String username,
                            @RequestParam String password) {
        if(StringUtils.isBlank(username)){
            return AjaxResult.error("用户名不能为空！");
        }
        if(StringUtils.isBlank(password)){
            return AjaxResult.error("密码不能为空！");
        }

        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return AjaxResult.error("该账号不存在！");
        }

        password = PasswordUtil.md5(password + user.getSalt());
        if (!user.getPassword().equals(password)) {
            return AjaxResult.error("密码不正确！");
        }

        return AjaxResult.success("登录成功！");
    }
}
