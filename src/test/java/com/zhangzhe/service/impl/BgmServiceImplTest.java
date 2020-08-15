package com.zhangzhe.service.impl;

import com.zhangzhe.pojo.Bgm;
import com.zhangzhe.service.BgmService;
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
class BgmServiceImplTest {

    @Autowired
    BgmService bgmService;

    @Test
    void testA() {
        final Bgm build = Bgm.builder().id("0003")
                .author("zhangsan")
                .name("sss")
                .path("/usr/local")
                .build();
        bgmService.addBgm(build);
    }
}