package com.zhangzhe.service;

import com.zhangzhe.pojo.Users;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
class UsersServiceTest {

    @Autowired
    UsersService usersService;

    @Test
    void selectUserByUsername() {
        Users imooc11 = Users.builder().username("imooc11").build();
        boolean b = usersService.selectUserByUsername(imooc11);
        System.out.println(b);
    }

    @Test
    void addUser() {
        Users build = Users.builder().username("zhangzhes").password("zhangzhe1238").nickname("zhangzhe").build();
        usersService.addUser(build);
    }

    @Test
    void isUserLikeVideo(){
        boolean userLikeVideo = usersService.isUserLikeVideo("180518CKMAAM5TXP", "180510CCX05TABHH");
        System.out.println(userLikeVideo);
    }
}