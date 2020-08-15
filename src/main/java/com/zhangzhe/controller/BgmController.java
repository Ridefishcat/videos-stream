package com.zhangzhe.controller;

import com.zhangzhe.pojo.Bgm;
import com.zhangzhe.service.BgmService;
import com.zhangzhe.utils.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("bgm")
public class BgmController {

    @Autowired
    BgmService bgmService;

    @GetMapping("/list")
    public ApiResult queryBgmList(){
        List<Bgm> bgms = bgmService.queryBgmList();
        return ApiResult.ok(bgms);
    }
}
