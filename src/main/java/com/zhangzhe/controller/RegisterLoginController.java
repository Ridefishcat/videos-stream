package com.zhangzhe.controller;

import com.zhangzhe.pojo.Users;
import com.zhangzhe.pojo.response.UserRp;
import com.zhangzhe.service.UsersService;
import com.zhangzhe.utils.ApiResult;
import com.zhangzhe.utils.MD5Utils;
import com.zhangzhe.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class RegisterLoginController extends BasicController {

    @Autowired
    UsersService usersService;

    @Autowired
    RedisOperator redisOperator;

    @Autowired
    Sid sid;

    @PostMapping("/register")
    public ApiResult register(@RequestBody Users users) {
        try {
            if(StringUtils.isBlank(users.getUsername()) || StringUtils.isBlank(users.getPassword())){
                return ApiResult.errorMsg("用户名密码为空,请重新输入！！！");
            }
            boolean result = usersService.selectUserByUsername(users);
            if(result){
                users.setPassword(MD5Utils.getMD5Str(users.getPassword()));

                usersService.addUser(users);
                UserRp userRp = getUserRp(users);

                return ApiResult.ok(userRp);
            }else {
                return ApiResult.errorMsg("用户名已经存在,请换一个再试!!!");
            }
        } catch (Exception e) {
            return ApiResult.errorMsg(e.getMessage());
        }
    }

    private UserRp getUserRp(Users users) {
        String userToken = UUID.randomUUID().toString();
        redisOperator.set(USER_REDIS_SESSION+":"+users.getId(),userToken,3600000);
        return UserRp.builder().id(users.getId()).userName(users.getUsername()).userToken(userToken).build();
    }

    @PostMapping("/login")
    public ApiResult login(@RequestBody Users user){
        try {
            if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())){
                return ApiResult.errorMsg("用户名密码为空,请重新输入！！！");
            }
            Users login = usersService.login(user);
            if(login != null){
                user.setPassword("");
                UserRp userRp = getUserRp(login);
                return ApiResult.ok(userRp);
            }else {
                return ApiResult.errorMsg("用户名或密码不正确, 请重试...");
            }
        } catch (Exception e) {
            return ApiResult.errorMsg(e.getMessage());
        }
    }

    @GetMapping("/logout")
    public ApiResult logout(String userId){
        redisOperator.del(USER_REDIS_SESSION+":"+userId);
        return ApiResult.ok("注销成功");
    }

}
