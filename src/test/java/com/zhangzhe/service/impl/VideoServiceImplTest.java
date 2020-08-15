package com.zhangzhe.service.impl;

import com.zhangzhe.pojo.Videos;
import com.zhangzhe.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
class VideoServiceImplTest {

    @Autowired
    VideoService videoService;
    @Test
    void saveVideo() {
        Videos build = Videos.builder().likeCounts(0L)
                .status(1)
                .coverPath("/sss")
                .videoPath("/ddd")
                .videoDesc("desc")
                .videoWidth(100)
                .videoHeight(200)
                .videoSeconds(10.0F)
                .userId("zz").audioId("ss")
                .id("107")
                .createTime(new Date())
                .build();
        System.out.println(build);
        String saveVideo = videoService.saveVideo(build);
    }
}